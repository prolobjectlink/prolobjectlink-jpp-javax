/*-
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */
package io.github.prolobjectlink.web.application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;

import io.github.prolobjectlink.db.ContainerFactory;
import io.github.prolobjectlink.db.DatabaseField;
import io.github.prolobjectlink.db.DatabaseSchema;
import io.github.prolobjectlink.db.DatabaseUser;
import io.github.prolobjectlink.db.DynamicClassLoader;
import io.github.prolobjectlink.db.ObjectConverter;
import io.github.prolobjectlink.db.entity.EntityClass;
import io.github.prolobjectlink.db.etc.Settings;
import io.github.prolobjectlink.db.jdbc.dialect.DialectResolver;
import io.github.prolobjectlink.db.jdbc.embedded.DerbyDriver;
import io.github.prolobjectlink.db.jdbc.embedded.H2FileDriver;
import io.github.prolobjectlink.db.jdbc.embedded.HSQLDBFileDriver;
import io.github.prolobjectlink.db.jpa.spi.JPAPersistenceSchemaVersion;
import io.github.prolobjectlink.db.jpa.spi.JPAPersistenceUnitInfo;
import io.github.prolobjectlink.db.jpa.spi.JPAPersistenceVersion;
import io.github.prolobjectlink.db.util.JavaReflect;
import io.github.prolobjectlink.logging.LoggerConstants;
import io.github.prolobjectlink.logging.LoggerUtils;
import io.github.prolobjectlink.prolog.IndicatorError;
import io.github.prolobjectlink.prolog.PrologClause;
import io.github.prolobjectlink.prolog.PrologEngine;
import io.github.prolobjectlink.prolog.PrologList;
import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.PrologTerm;
import io.github.prolobjectlink.web.compiler.Compiler;
import io.github.prolobjectlink.web.compiler.ModelCompiler;

public abstract class AbstractModelGenerator extends AbstractWebApplication implements ModelGenerator {

	private static final String KEY = "key/2";
	private static final String FIELD = "field/2";
	private static final String CLASS = "entity/3";
	private static final String ONETOONE = "oneToOne/2";
	private static final String ONETOMANY = "oneToMany/2";
	private static final String MANYTOONE = "manyToOne/2";
	private static final String MANYTOMANY = "manyToMany/2";

	private final PrologProvider provider;
	private final ContainerFactory factory;
	private final ObjectConverter<PrologTerm> converter;
	private final DatabaseUser owner = new DatabaseUser("sa", "");
	private final DynamicClassLoader loader = new DynamicClassLoader();

	protected AbstractModelGenerator() {
		this(new Settings().load().getContainerFactory());
	}

	protected AbstractModelGenerator(ContainerFactory factory) {
		this.converter = factory.getObjectConverter();
		this.provider = factory.getProvider();
		this.factory = factory;
	}

	private void assertValidClassIndicator(PrologClause clause) {
//		if (!clause.hasIndicator("class", 3)) {
//			throw new IndicatorError("No valid class descriptor predicate " + clause);
//		}
		if (!clause.getIndicator().equals(CLASS)) {
			throw new IndicatorError("No valid class descriptor predicate " + clause);
		}
	}

	private void assertValidFieldIndicator(PrologTerm t) {
//		if (!t.hasIndicator("field", 2) &&
//
//				!t.hasIndicator("key", 2) &&
//
//				!t.hasIndicator("oneToOne", 2) &&
//
//				!t.hasIndicator("oneToMany", 2) &&
//
//				!t.hasIndicator("manyToOne", 2) &&
//
//				!t.hasIndicator("manyToMany", 2)) {
//			throw new IndicatorError("No valid field descriptor predicate " + t);
//		}
		String indicator = t.getIndicator();
		if (!indicator.equals(FIELD) && !indicator.equals(KEY) && !indicator.equals(ONETOONE)
				&& !indicator.equals(ONETOMANY) && !indicator.equals(MANYTOONE) && !indicator.equals(MANYTOMANY)) {
			throw new IndicatorError("No valid field descriptor predicate " + t);
		}
	}

	public final List<PersistenceUnitInfo> getPersistenceUnits() {
		String model = "model.pl";
		String database = "database.pl";

		Object hbm2ddl = DEFAULT_HBM2DDL;
		Object sqlshow = DEFAULT_SQLSHOW;
		Object sqlformat = DEFAULT_SQLFORMAT;
		Object loggingLevel = DEFAULT_LOGGING_LEVEL;
		Object ddlgeneration = DEFAULT_DDL_GENERATION;

		Object log = DEFAULT_LOG;
		Object datacache = DEFAULT_DATACACHE;
		Object remote_commit_provider = DEFAULT_REMOTE_COMMIT_PROVIDER;
		Object initialize_eagerly = DEFAULT_INITIALIZE_EAGERLY;
		Object dynamic_enhancement_agent = DEFAULT_DYNAMIC_ENHANCEMENT_AGENT;
		Object runtime_unenhanced_classes = DEFAULT_RUNTIME_UNENHANCED_CLASSES;
		Object synchronize_mappings = DEFAULT_SYNCHRONIZE_MAPPINGS;
		Object query_sql_cache = DEFAULT_QUERY_SQL_CACHE;

		List<PersistenceUnitInfo> l = new ArrayList<PersistenceUnitInfo>();
		File appRoot = getWebDirectory();
		File[] apps = appRoot.listFiles();
		for (File file : apps) {

			String appName = file.getName();
			String appPath = file.getPath();
			String separator = File.separator;

			String databasePath = appPath + separator + database;
			PrologEngine databaseEngine = provider.newEngine(databasePath);
			Object jpaProvider = databaseEngine.query("provider(X)").oneResult().get(0);
			Object jpaDriver = databaseEngine.query("driver(X)").oneResult().get(0);
			Object jpaUser = databaseEngine.query("user(X)").oneResult().get(0);
			Object jpaPwd = databaseEngine.query("password(X)").oneResult().get(0);
			Object jpaUrl = databaseEngine.query("url(X)").oneResult().get(0);

			// for embedded databases
			if (jpaUrl.toString().contains(HSQLDBFileDriver.prefix)) {
				String rectify = jpaUrl.toString().replace(HSQLDBFileDriver.prefix, "");
				if (runOnLinux() || runOnOsX()) {
					try {
						String str = getDBDirectory().getCanonicalPath();
						jpaUrl = new String(HSQLDBFileDriver.prefix + str + "/hsqldb" + rectify)
								.replace(File.separatorChar, '/');
					} catch (IOException e) {
						LoggerUtils.error(getClass(), LoggerConstants.IO, e);
					}
				} else if (runOnWindows()) {
					try {
						File[] roots = File.listRoots();
						for (File root : roots) {
							String str = getDBDirectory().getCanonicalPath();
							if (str.startsWith(root.getCanonicalPath())) {
								jpaUrl = new String(HSQLDBFileDriver.prefix + str + "/hsqldb" + rectify)
										.replace(root.getCanonicalPath(), "/").replace(File.separatorChar, '/');
							}
						}
					} catch (IOException e) {
						LoggerUtils.error(getClass(), LoggerConstants.IO, e);
					}
				}
			} else if (jpaUrl.toString().contains(H2FileDriver.prefix)) {
				String rectify = jpaUrl.toString().replace(H2FileDriver.prefix, "");
				if (runOnLinux() || runOnOsX()) {
					try {
						String str = getDBDirectory().getCanonicalPath();
						jpaUrl = new String(H2FileDriver.prefix + str + "/h2db" + rectify).replace(File.separatorChar,
								'/');
					} catch (IOException e) {
						LoggerUtils.error(getClass(), LoggerConstants.IO, e);
					}
				} else if (runOnWindows()) {
					try {
						File[] roots = File.listRoots();
						for (File root : roots) {
							String str = getDBDirectory().getCanonicalPath();
							if (str.startsWith(root.getCanonicalPath())) {
								jpaUrl = new String(H2FileDriver.prefix + str + "/h2db" + rectify)
										.replace(root.getCanonicalPath(), "/").replace(File.separatorChar, '/');
							}
						}
					} catch (IOException e) {
						LoggerUtils.error(getClass(), LoggerConstants.IO, e);
					}
				}
			}else if (jpaUrl.toString().contains(DerbyDriver.prefix)) {
				String rectify = jpaUrl.toString().replace(DerbyDriver.prefix, "");
				if (runOnLinux() || runOnOsX()) {
					try {
						String str = getDBDirectory().getCanonicalPath();
						jpaUrl = new String(DerbyDriver.prefix + str + "/derby" + rectify).replace(File.separatorChar,
								'/');
					} catch (IOException e) {
						LoggerUtils.error(getClass(), LoggerConstants.IO, e);
					}
				} else if (runOnWindows()) {
					try {
						File[] roots = File.listRoots();
						for (File root : roots) {
							String str = getDBDirectory().getCanonicalPath();
							if (str.startsWith(root.getCanonicalPath())) {
								jpaUrl = new String(DerbyDriver.prefix + str + "/derby" + rectify)
										.replace(root.getCanonicalPath(), "/").replace(File.separatorChar, '/');
							}
						}
					} catch (IOException e) {
						LoggerUtils.error(getClass(), LoggerConstants.IO, e);
					}
				}
			}

			String modelPath = appPath + separator + model;
			PrologEngine modelEngine = provider.newEngine(modelPath);
			DatabaseSchema schema = new DatabaseSchema(modelPath, provider, factory, owner);

			//
			String xmlVersion = "1.0";
			String xmlEncoding = "UTF-8";
			String xmlPersistenceVersion = "2.1";
			String xmlPersistenceXmlns = "http://java.sun.com/xml/ns/persistence";
			String xmlPersistenceXmlnsXsi = "http://www.w3.org/2001/XMLSchema-instance";
			String xmlPersistenceXsiSchemalocation = "http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd";
			JPAPersistenceSchemaVersion schemaVersion = new JPAPersistenceSchemaVersion(xmlVersion, xmlEncoding);
			JPAPersistenceVersion persistenceVersion = new JPAPersistenceVersion(xmlPersistenceVersion,
					xmlPersistenceXmlns, xmlPersistenceXmlnsXsi, xmlPersistenceXsiSchemalocation);
			PersistenceUnitTransactionType transactionType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
			JPAPersistenceUnitInfo info = new JPAPersistenceUnitInfo(null, schemaVersion, persistenceVersion, appName,
					transactionType);

			Class<?> ppc = JavaReflect.classForName("" + jpaProvider + "");
			Class<?> jdbc = JavaReflect.classForName("" + jpaDriver + "");
			String dialect = DialectResolver.resolve(ppc, jdbc);

			// persistence provider
			info.setPersistenceProviderClassName("" + jpaProvider + "");

			// openjpa
			info.setProperty(RUNTIME_UNENHANCED_CLASSES, "" + runtime_unenhanced_classes + "");
			info.setProperty(DYNAMIC_ENHANCEMENT_AGENT, "" + dynamic_enhancement_agent + "");
			info.setProperty(REMOTE_COMMIT_PROVIDER, "" + remote_commit_provider + "");
			info.setProperty(SYNCHRONIZE_MAPPINGS, "" + synchronize_mappings + "");
			info.setProperty(INITIALIZE_EAGERLY, "" + initialize_eagerly + "");
			info.setProperty(QUERY_SQL_CACHE, "" + query_sql_cache + "");
			info.setProperty(DATACACHE, "" + datacache + "");
			info.setProperty(LOG, "" + log + "");

			// eclipselink
			info.setProperty(DDL_GENERATION, "" + ddlgeneration + "");
			info.setProperty(LOGGING_LEVEL, "" + loggingLevel + "");
			info.setProperty(TARGET_DATABASE, dialect);

			// hibernate
			info.setProperty(SQLFORMAT, "" + sqlformat + "");
			info.setProperty(SQLSHOW, "" + sqlshow + "");
			info.setProperty(HBM2DDL, "" + hbm2ddl + "");
			info.setProperty(DRIVER, "" + jpaDriver + "");
			info.setProperty(USER, "" + jpaUser + "");
			info.setProperty(PWD, "" + jpaPwd + "");
			info.setProperty(URL, "" + jpaUrl + "");
			info.setProperty(DIALECT, dialect);

			Map<String, Class<?>> resolved = new HashMap<String, Class<?>>();
			Map<String, EntityClass> pending = new HashMap<String, EntityClass>();
			Map<String, EntityClass> processed = new HashMap<String, EntityClass>();

			for (PrologClause clause : modelEngine) {

				int position = 0;

				assertValidClassIndicator(clause);

				PrologTerm head = clause.getHead();
				PrologTerm className = head.getArgument(0);
				PrologTerm classFields = head.getArgument(2);
				String name = (String) converter.toObject(className);
				// we use the entity class comment to pass the
				// persistence unit name that match with application name
				EntityClass entity = new EntityClass(name, appName, schema, ppc);
				PrologList fields = (PrologList) classFields;

				for (PrologTerm field : fields) {

					String ftype = "";
					Class<?> c = null;
					Class<?> linkedClass = null;
					Class<?> linkedKeyClass = null;
					Class<?> linkedValueClass = null;

					assertValidFieldIndicator(field);

					PrologTerm fieldName = field.getArgument(0);
					PrologTerm fieldType = field.getArgument(1);

					String fname = (String) converter.toObject(fieldName);
					DatabaseField attribute = entity.getField(fname);

					if (fieldType.hasIndicator("set", 1)) {
						PrologTerm linkedType = fieldType.getArgument(0);
						String cn = converter.removeQuotes(linkedType.getFunctor());
						linkedClass = converter.toClass(cn);
						c = Set.class;
					} else if (fieldType.hasIndicator("list", 1)) {
						PrologTerm linkedType = fieldType.getArgument(0);
						String cn = converter.removeQuotes(linkedType.getFunctor());
						linkedClass = converter.toClass(cn);
						c = List.class;
					} else if (fieldType.hasIndicator("collection", 1)) {
						PrologTerm linkedType = fieldType.getArgument(0);
						String cn = converter.removeQuotes(linkedType.getFunctor());
						linkedClass = converter.toClass(cn);
						c = Collection.class;
					} else if (fieldType.hasIndicator("map", 2)) {
						PrologTerm linkedKeyType = fieldType.getArgument(0);
						PrologTerm linkedValueType = fieldType.getArgument(1);
						String kcn = converter.removeQuotes(linkedKeyType.getFunctor());
						String vcn = converter.removeQuotes(linkedValueType.getFunctor());
						linkedKeyClass = converter.toClass(kcn);
						linkedValueClass = converter.toClass(vcn);
						c = Map.class;
					} else {
						ftype = (String) converter.toObject(fieldType);
						c = converter.toClass(ftype);
					}

					if (c == null) {

						if (linkedClass != null) {
							attribute = entity.addField(fname, "", position++, c, linkedClass);
						} else {
							// we use the entity class comment to pass the persistence unit name
							// that match with application name
							EntityClass type = new EntityClass(ftype, appName, schema, ppc);
							attribute = entity.addField(fname, "", position++, Object.class);
							pending.put(fname, type);
						}

					} else {

						if (linkedClass != null) {
							attribute = entity.addField(fname, "", position++, c, linkedClass);
						} else {
							attribute = entity.addField(fname, "", position++, c);
						}

					}

					if (field.hasIndicator("key", 2)) {
						attribute.setPrimaryKey(true);
					}
					if (field.hasIndicator("oneToOne", 2)) {
						attribute.setOneToOne(true);
						attribute.setOneToMany(false);
						attribute.setManyToOne(false);
						attribute.setManyToMany(false);
					}
					if (field.hasIndicator("oneToMany", 2)) {
						attribute.setOneToOne(false);
						attribute.setOneToMany(true);
						attribute.setManyToOne(false);
						attribute.setManyToMany(false);
					}
					if (field.hasIndicator("manyToOne", 2)) {
						attribute.setOneToOne(false);
						attribute.setOneToMany(false);
						attribute.setManyToOne(true);
						attribute.setManyToMany(false);
					}
					if (field.hasIndicator("manyToMany", 2)) {
						attribute.setOneToOne(false);
						attribute.setOneToMany(false);
						attribute.setManyToOne(false);
						attribute.setManyToMany(true);
					}

				}

				String code = entity.generate();
				Compiler compiler = new ModelCompiler();
				try {

					Path javaFile = compiler.saveSource(code, entity.getShortName());
					Path classFile = compiler.compileSource(javaFile, entity.getShortName());
					byte[] bytecode = compiler.readBytecode(classFile);
					Class<?> cls = loader.lookupClass(name);
					if (cls == null) {
						cls = loader.defineClass(name, bytecode);
					}

					info.addManagedClassName(name);
					info.addManagedClass(bytecode);
					info.addManagedClass(cls);

					processed.put(name, entity);
					resolved.put(name, cls);

				} catch (IOException e) {
					LoggerUtils.error(getClass(), LoggerConstants.IO, e);
				}

			}

			// byte[] bytecode = entity.compile();
			// Class<?> cls = loader.defineClass(name, bytecode);
			// info.addManagedClassName(name);
			// info.addManagedClass(bytecode);
			// info.addManagedClass(cls);
			// processed.put(name, entity);
			// resolved.put(name, cls);

			for (Entry<String, EntityClass> entry : processed.entrySet()) {
				EntityClass entity = entry.getValue();
				Collection<DatabaseField> fields = entity.getFields();
				for (DatabaseField databaseField : fields) {
					if (databaseField.getType() == Object.class) {
						String fname = databaseField.getName();
						EntityClass ftype = pending.get(fname);
						Class<?> cls = resolved.get(ftype.getName());
						databaseField.setType(cls);
					}
				}
			}

			modelEngine.dispose();
			l.add(info);

		}

		return l;

	}

}

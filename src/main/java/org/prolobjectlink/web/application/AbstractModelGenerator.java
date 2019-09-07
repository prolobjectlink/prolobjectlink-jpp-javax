/*-
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
 * #L%
 */
package org.prolobjectlink.web.application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;

import org.prolobjectlink.db.ContainerFactory;
import org.prolobjectlink.db.DatabaseClass;
import org.prolobjectlink.db.DatabaseSchema;
import org.prolobjectlink.db.DatabaseUser;
import org.prolobjectlink.db.ObjectConverter;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.db.jpa.spi.JPAPersistenceSchemaVersion;
import org.prolobjectlink.db.jpa.spi.JPAPersistenceUnitInfo;
import org.prolobjectlink.db.jpa.spi.JPAPersistenceVersion;
import org.prolobjectlink.prolog.PrologClause;
import org.prolobjectlink.prolog.PrologEngine;
import org.prolobjectlink.prolog.PrologList;
import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.PrologTerm;

public abstract class AbstractModelGenerator extends AbstractWebApplication implements ModelGenerator {

	private static final String CLASS_INDICATOR = "class/3";
	private static final String FIELD_INDICATOR = "field/2";

	private final PrologProvider provider;
	private final ContainerFactory factory;
	private final ObjectConverter<PrologTerm> converter;
	private final DatabaseUser owner = new DatabaseUser("sa", "");

	protected AbstractModelGenerator() {
		this(new Settings().load().getContainerFactory());
	}

	protected AbstractModelGenerator(ContainerFactory factory) {
		this.converter = factory.getObjectConverter();
		this.provider = factory.getProvider();
		this.factory = factory;
	}

	private void assertValidClassIndicator(PrologClause clause) {
		if (!clause.getIndicator().equals(CLASS_INDICATOR)) {
			throw new RuntimeException("No valid class descriptor predicate " + clause);
		}
	}

	private void assertValidFieldIndicator(PrologTerm prologTerm) {
		if (!prologTerm.getIndicator().equals(FIELD_INDICATOR)) {
			throw new RuntimeException("No valid field descriptor predicate " + prologTerm);
		}
	}

	
	public final List<PersistenceUnitInfo> getPersistenceUnit() {
		String model = "model.pl";
		String database = "database.pl";

		Object jpaProvider = DEFAULT_PROVIDER;
		Object jpaDriver = DEFAULT_DRIVER;
		Object jpaUser = DEFAULT_USER;
		Object jpaPwd = DEFAULT_PWD;
		Object jpaUrl = "";

		List<PersistenceUnitInfo> l = new ArrayList<PersistenceUnitInfo>();
		File appRoot = getWebDirectory();
		File[] apps = appRoot.listFiles();
		for (File file : apps) {

			String appName = file.getName();
			String appPath = file.getPath();
			String separator = File.separator;

			String databasePath = appPath + separator + database;
			PrologEngine databaseEngine = provider.newEngine(databasePath);
			jpaProvider = databaseEngine.query("provider(X)").oneResult().get(0);
			jpaDriver = databaseEngine.query("driver(X)").oneResult().get(0);
			jpaUser = databaseEngine.query("user(X)").oneResult().get(0);
			jpaPwd = databaseEngine.query("password(X)").oneResult().get(0);
			jpaUrl = databaseEngine.query("url(X)").oneResult().get(0);

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

			info.setPersistenceProviderClassName("" + jpaProvider + "");
			info.setProperty(DRIVER, "" + jpaDriver + "");
			info.setProperty(USER, "" + jpaUser + "");
			info.setProperty(PWD, "" + jpaPwd + "");
			info.setProperty(URL, "" + jpaUrl + "");

			for (PrologClause clause : modelEngine) {

				assertValidClassIndicator(clause);

				PrologTerm head = clause.getHead();
				PrologTerm className = head.getArgument(0);
				PrologTerm classFields = head.getArgument(2);

				// class qualified name
				String name = (String) converter.toObject(className);

				// class short name
				int index = name.lastIndexOf('.') + 1;
				String shortName = name.substring(index);

				DatabaseClass dbclass = new DatabaseClass(shortName, "", schema);
				PrologList fields = (PrologList) classFields;

				int position = 0;

				for (PrologTerm field : fields) {

					assertValidFieldIndicator(field);

					PrologTerm fieldName = field.getArgument(0);
					PrologTerm fieldType = field.getArgument(1);

					String fname = (String) converter.toObject(fieldName);
					String ftype = (String) converter.toObject(fieldType);
					Class<?> c = converter.toClass(ftype);

					if (c == null) {
						// TODO We need generate the class
						System.out.println(className);
					}

					dbclass.addField(fname, "", position++, c);

				}

				info.addManagedClass(name);

			}

			l.add(info);

		}

		return l;

	}

}

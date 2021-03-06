/*
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package io.github.prolobjectlink.db.jpa.spi;

import static io.github.prolobjectlink.db.XmlParser.XML;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.AttributeNode;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.LockModeType;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.NamedSubgraph;
import javax.persistence.Parameter;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.QueryHint;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.ProviderUtil;

import io.github.prolobjectlink.db.DatabaseEngine;
import io.github.prolobjectlink.db.DatabaseProperties;
import io.github.prolobjectlink.db.DatabaseUser;
import io.github.prolobjectlink.db.ObjectConverter;
import io.github.prolobjectlink.db.etc.Settings;
import io.github.prolobjectlink.db.jpa.JpaAttributeNode;
import io.github.prolobjectlink.db.jpa.JpaColumnResult;
import io.github.prolobjectlink.db.jpa.JpaConstructorResult;
import io.github.prolobjectlink.db.jpa.JpaEntityGraph;
import io.github.prolobjectlink.db.jpa.JpaEntityManagerFactory;
import io.github.prolobjectlink.db.jpa.JpaEntityResult;
import io.github.prolobjectlink.db.jpa.JpaFieldResult;
import io.github.prolobjectlink.db.jpa.JpaNativeQuery;
import io.github.prolobjectlink.db.jpa.JpaParameter;
import io.github.prolobjectlink.db.jpa.JpaProviderUtil;
import io.github.prolobjectlink.db.jpa.JpaQuery;
import io.github.prolobjectlink.db.jpa.JpaResultSetMapping;
import io.github.prolobjectlink.db.jpa.JpaStoredProcedureQuery;
import io.github.prolobjectlink.db.prolog.PrologObjectConverter;
import io.github.prolobjectlink.db.util.JavaReflect;
import io.github.prolobjectlink.prolog.PrologEngine;
import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.PrologTerm;

/** @author Jose Zalacain @since 1.0 */
public abstract class JPAPersistenceProvider implements PersistenceProvider {

	public static final String URL_PREFIX = "jdbc:prolobjectlink:store:";
	public static final String JPI_PROVIDER_PROPERTY = "org.logicware.jpi.provider";
	public static final String JPD_DRIVER_PROPERTY = "org.logicware.jpd.driver";
	private static final ProviderUtil PROVIDER_UTIL = new JpaProviderUtil();

	private final Map<String, PersistenceUnitInfo> persistenceUnits;
	private DatabaseEngine database;

	public JPAPersistenceProvider() {
		URL persistenceXml = Thread.currentThread().getContextClassLoader().getResource(XML);
		persistenceUnits = new JPAPersistenceXmlParser().parsePersistenceXml(persistenceXml);
	}

	public EntityManagerFactory createEntityManagerFactory(String emName, Map map) {
		PersistenceUnitInfo info = persistenceUnits.get(emName);
		return createContainerEntityManagerFactory(info, info.getProperties());
	}

	public EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info, Map map) {

		assert info != null;

		DatabaseProperties properties = (DatabaseProperties) info.getProperties();

		if (map != null && !map.isEmpty()) {
			properties.putAll(map);
		}

		// retrieving persistence unit properties
		// String provider = properties.getPersistenceProviderClassName();
		String driver = String.valueOf(properties.get(DatabaseProperties.DRIVER));
		String url = String.valueOf(properties.get(DatabaseProperties.URL)).replace(URL_PREFIX, "");
		String username = String.valueOf(properties.get(DatabaseProperties.USER));
		String password = String.valueOf(properties.get(DatabaseProperties.PASSWORD));
		PrologProvider prologProvider = createJavaPrologIntefaceProvider();
		Class<?> objectDriverClass = JavaReflect.classForName(driver);
		DatabaseUser user = new DatabaseUser(username, password);

		database = createDatabase(info.getPersistenceProviderClassName(), info.getPersistenceUnitName(),
				new Settings(driver), url, user);

		EntityManagerFactory managerFactory = new JpaEntityManagerFactory(database, properties);

		List<String> managedClasNames = info.getManagedClassNames();
		for (String className : managedClasNames) {
			Class<?> clazz = JavaReflect.classForName(className);
			registerEntities(managerFactory, prologProvider, url, clazz);
			registerNamedQueries(managerFactory, prologProvider, url, clazz);
			registerNamedNativeQueries(managerFactory, prologProvider, url, clazz);
			registerNamedEntitiesGraph(managerFactory, prologProvider, url, clazz);
			registerSQLResultSetMapping(managerFactory, prologProvider, url, clazz);
			registerNamedStoredProcedureQueries(managerFactory, objectDriverClass, prologProvider, url, clazz);
		}
		return managerFactory;

	}

	protected abstract DatabaseEngine createDatabase(String provider, String name, Settings settings, String url,
			DatabaseUser user);

	public abstract String getJavaPrologIntefaceProviderName();

	public abstract PrologProvider createJavaPrologIntefaceProvider();

	public void generateSchema(PersistenceUnitInfo info, Map map) {
		// do nothing
	}

	public boolean generateSchema(String persistenceUnitName, Map map) {
		return false;
	}

	public ProviderUtil getProviderUtil() {
		return PROVIDER_UTIL;
	}

	private void registerEntities(EntityManagerFactory managerFactory, PrologProvider provider, String url,
			Class<?> clazz) {
		if (clazz.isAnnotationPresent(Entity.class)) {
			String name = clazz.getAnnotation(Entity.class).name();
			if (name != null && !name.isEmpty()) {
				managerFactory.unwrap(JpaEntityManagerFactory.class).addEntity(name, clazz);
			} else {
				managerFactory.unwrap(JpaEntityManagerFactory.class).addEntity(clazz.getSimpleName(), clazz);
			}
		}
	}

	private void registerNamedQueries(EntityManagerFactory managerFactory, PrologProvider provider, String url,
			Class<?> clazz) {
		if (clazz.isAnnotationPresent(NamedQuery.class)) {
			addNamedQuery(managerFactory, provider, url, clazz.getAnnotation(NamedQuery.class));
		} else if (clazz.isAnnotationPresent(NamedQueries.class)) {
			NamedQuery[] namedQueries = clazz.getAnnotation(NamedQueries.class).value();
			for (NamedQuery namedQuery : namedQueries) {
				addNamedQuery(managerFactory, provider, url, namedQuery);
			}
		}
	}

	private void registerNamedNativeQueries(EntityManagerFactory managerFactory, PrologProvider provider, String url,
			Class<?> clazz) {
		if (clazz.isAnnotationPresent(NamedNativeQuery.class)) {
			addNamedNativeQuery(managerFactory, provider, url, clazz.getAnnotation(NamedNativeQuery.class));
		} else if (clazz.isAnnotationPresent(NamedNativeQueries.class)) {
			NamedNativeQuery[] namedQueries = clazz.getAnnotation(NamedNativeQueries.class).value();
			for (NamedNativeQuery namedQuery : namedQueries) {
				addNamedNativeQuery(managerFactory, provider, url, namedQuery);
			}
		}
	}

	private void registerSQLResultSetMapping(EntityManagerFactory managerFactory, PrologProvider provider, String url,
			Class<?> clazz) {
		if (clazz.isAnnotationPresent(SqlResultSetMapping.class)) {
			addSqlResultSetMapping(managerFactory, provider, url, clazz.getAnnotation(SqlResultSetMapping.class),
					clazz);
		} else if (clazz.isAnnotationPresent(SqlResultSetMappings.class)) {
			SqlResultSetMapping[] resultSetMappings = clazz.getAnnotation(SqlResultSetMappings.class).value();
			for (SqlResultSetMapping sqlResultSetMapping : resultSetMappings) {
				addSqlResultSetMapping(managerFactory, provider, url, sqlResultSetMapping, clazz);
			}
		}
	}

	private void registerNamedEntitiesGraph(EntityManagerFactory managerFactory, PrologProvider provider, String url,
			Class<?> clazz) {
		if (clazz.isAnnotationPresent(NamedEntityGraph.class)) {
			addNamedEntityGraph(managerFactory, provider, url, clazz.getAnnotation(NamedEntityGraph.class), clazz);
		} else if (clazz.isAnnotationPresent(NamedEntityGraphs.class)) {
			NamedEntityGraph[] namedEntityGraphs = clazz.getAnnotation(NamedEntityGraphs.class).value();
			for (NamedEntityGraph namedEntityGraph : namedEntityGraphs) {
				addNamedEntityGraph(managerFactory, provider, url, namedEntityGraph, clazz);
			}
		}
	}

	private void registerNamedStoredProcedureQueries(EntityManagerFactory managerFactory, Class<?> driver,
			PrologProvider provider, String url, Class<?> clazz) {
		if (clazz.isAnnotationPresent(NamedStoredProcedureQuery.class)) {
			addNamedStoredProcedureQuery(managerFactory, driver, provider, url,
					clazz.getAnnotation(NamedStoredProcedureQuery.class), clazz);
		} else if (clazz.isAnnotationPresent(NamedStoredProcedureQueries.class)) {
			NamedStoredProcedureQuery[] namedStoredProcedureQueries = clazz
					.getAnnotation(NamedStoredProcedureQueries.class).value();
			for (NamedStoredProcedureQuery namedStoredProcedureQuery : namedStoredProcedureQueries) {
				addNamedStoredProcedureQuery(managerFactory, driver, provider, url, namedStoredProcedureQuery, clazz);
			}
		}

	}

	private void addNamedQuery(EntityManagerFactory managerFactory, PrologProvider provider, String url,
			NamedQuery namedQuery) {
		String name = namedQuery.name();
		String qlString = namedQuery.query();
		LockModeType lockModeType = namedQuery.lockMode();
		Query query = new JpaQuery(database, qlString);
		QueryHint[] hints = namedQuery.hints();
		for (QueryHint queryHint : hints) {
			query.setHint(queryHint.name(), queryHint.value());
		}
		managerFactory.addNamedQuery(name, query);
	}

	private void addNamedNativeQuery(EntityManagerFactory managerFactory, PrologProvider provider, String url,
			NamedNativeQuery namedQuery) {
		String name = namedQuery.name();
		String query = namedQuery.query();
		Class<?> resultClass = namedQuery.resultClass();
		String resultSetMapping = namedQuery.resultSetMapping();
		Query typedQuery = new JpaNativeQuery(database, query, resultClass);
		QueryHint[] hints = namedQuery.hints();
		for (QueryHint queryHint : hints) {
			typedQuery.setHint(queryHint.name(), queryHint.value());
		}
		managerFactory.addNamedQuery(name, typedQuery);
	}

	private void addSqlResultSetMapping(EntityManagerFactory managerFactory, PrologProvider provider, String url,
			SqlResultSetMapping resultSetMapping, Class<?> clazz) {
		String name = resultSetMapping.name();
		EntityResult[] entities = resultSetMapping.entities();
		ConstructorResult[] classes = resultSetMapping.classes();
		ColumnResult[] columns = resultSetMapping.columns();

		// entities
		JpaEntityResult[] entityResults = new JpaEntityResult[entities.length];
		for (int i = 0; i < entities.length; i++) {
			EntityResult entityResult = entities[i];
			Class<?> entityClass = entityResult.entityClass();
			FieldResult[] fields = entityResult.fields();
			JpaFieldResult[] fieldResults = new JpaFieldResult[fields.length];
			for (int j = 0; j < fields.length; j++) {
				FieldResult fieldResult = fields[j];
				String fname = fieldResult.name();
				String column = fieldResult.column();
				JpaFieldResult result = new JpaFieldResult(fname, column);
				fieldResults[j] = result;
			}
			JpaEntityResult result = new JpaEntityResult(entityClass, fieldResults, "");
			entityResults[i] = result;
		}

		// constructors
		JpaConstructorResult[] classesResults = new JpaConstructorResult[classes.length];
		for (int i = 0; i < classes.length; i++) {
			ConstructorResult classResult = classes[i];
			Class<?> targetClass = classResult.targetClass();
			ColumnResult[] columnResults = classResult.columns();
			JpaColumnResult[] jpaColumnResults = new JpaColumnResult[columnResults.length];
			for (int j = 0; j < columnResults.length; j++) {
				ColumnResult columnResult = columnResults[j];
				String columnName = columnResult.name();
				Class<?> type = columnResult.type();
				JpaColumnResult result = new JpaColumnResult(columnName, type);
				jpaColumnResults[j] = result;
			}
			JpaConstructorResult constructorResult = new JpaConstructorResult(targetClass, jpaColumnResults);
			classesResults[i] = constructorResult;
		}

		// columns
		JpaColumnResult[] jpaColumnResults = new JpaColumnResult[columns.length];
		for (int j = 0; j < columns.length; j++) {
			ColumnResult columnResult = columns[j];
			String columnName = columnResult.name();
			Class<?> type = columnResult.type();
			JpaColumnResult result = new JpaColumnResult(columnName, type);
			jpaColumnResults[j] = result;
		}

		JpaResultSetMapping mapping = new JpaResultSetMapping(name, entityResults, classesResults, jpaColumnResults);
		managerFactory.unwrap(JpaEntityManagerFactory.class).addResultSetMapping(mapping);
	}

	private void addNamedEntityGraph(EntityManagerFactory managerFactory, PrologProvider provider, String url,
			NamedEntityGraph namedEntityGraph, Class<?> clazz) {
		String name = namedEntityGraph.name();
		NamedAttributeNode[] nodes = namedEntityGraph.attributeNodes();
		boolean allAttributes = namedEntityGraph.includeAllAttributes();
		NamedSubgraph[] namedSubgraphs = namedEntityGraph.subgraphs();
		NamedSubgraph[] namedSubclassSubgraphs = namedEntityGraph.subclassSubgraphs();

		List<AttributeNode<?>> attributeNodes = new ArrayList<AttributeNode<?>>(nodes.length);
		for (NamedAttributeNode namedAttributeNode : nodes) {
			// TODO SUBGRAPH AND KEYSUBGRAPH
			AttributeNode<?> attributeNode = new JpaAttributeNode(namedAttributeNode.value());
			attributeNodes.add(attributeNode);
		}

		Map<Class, JpaEntityGraph> subgraphs = new HashMap<Class, JpaEntityGraph>();
		Map<Class, JpaEntityGraph> subclassSubgraphs = new HashMap<Class, JpaEntityGraph>();

		// TODO FILL THIS GRAPHS

		JpaEntityGraph<?> entityGraph = new JpaEntityGraph(name, subgraphs, subclassSubgraphs, allAttributes,
				attributeNodes, clazz);

		managerFactory.addNamedEntityGraph(name, entityGraph);
	}

	private void addNamedStoredProcedureQuery(EntityManagerFactory managerFactory, Class<?> driver,
			PrologProvider provider, String url, NamedStoredProcedureQuery namedStoredProcedureQuery, Class<?> clazz) {

		String name = namedStoredProcedureQuery.name();
		String procedureName = namedStoredProcedureQuery.procedureName();
		Class<?>[] resultClasses = namedStoredProcedureQuery.resultClasses();
		String[] resultSetMappings = namedStoredProcedureQuery.resultSetMappings();
		ObjectConverter<PrologTerm> factory = new PrologObjectConverter(provider);
		PrologEngine engine = provider.newEngine();
		StoredProcedureQuery storedProcedureQuery = new JpaStoredProcedureQuery(database, procedureName, resultClasses,
				resultSetMappings);

		QueryHint[] queryHints = namedStoredProcedureQuery.hints();
		for (QueryHint queryHint : queryHints) {
			storedProcedureQuery.setHint(queryHint.name(), queryHint.value());
		}

		StoredProcedureParameter[] storedProcedureParameters = namedStoredProcedureQuery.parameters();
		for (int i = 0; i < storedProcedureParameters.length; i++) {
			StoredProcedureParameter storedProcedureParameter = storedProcedureParameters[i];
			String parameterName = storedProcedureParameter.name();
			ParameterMode parameterMode = storedProcedureParameter.mode();
			Class<?> parameterType = storedProcedureParameter.type();
			Parameter<?> parameter = new JpaParameter(parameterName, i, parameterType);
			storedProcedureQuery.setParameter(parameter, null);
		}

		managerFactory.addNamedQuery(name, storedProcedureQuery);

	}
}

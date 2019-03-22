/*
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.prolobjectlink.db.jpa;

import static org.prolobjectlink.db.XmlParser.XML;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.persistence.spi.ProviderUtil;

import org.prolobjectlink.AbstractWrapper;
import org.prolobjectlink.db.DatabaseEngine;
import org.prolobjectlink.db.DatabaseUnitInfo;
import org.prolobjectlink.db.Protocol;
import org.prolobjectlink.db.memory.MemoryHierarchical;
import org.prolobjectlink.db.persistent.EmbeddedHierarchical;
import org.prolobjectlink.db.persistent.RemoteHierarchical;
import org.prolobjectlink.db.spi.PersistenceSchemaVersion;
import org.prolobjectlink.db.spi.PersistenceVersion;
import org.prolobjectlink.db.spi.PersistenceXmlParser;
import org.prolobjectlink.db.spi.PersistenceUnitInformation;
import org.prolobjectlink.logging.LoggerUtils;

/**
 * Implementation of {@link PersistenceProvider}. Derived classes just extend
 * from this abstract class.
 * 
 * @author Jose Zalacain
 * @version 1.0
 */
/** @author Jose Zalacain @since 1.0 */
public abstract class JpaAbstractProvider extends AbstractWrapper implements PersistenceProvider {

	protected final ProviderUtil providerUtil = new JpaProviderUtil();
//	protected final JPAPersistenceXmlParser p = new JPAPersistenceXmlParser();
	protected final PersistenceXmlParser p = new PersistenceXmlParser();

	protected static final String SECRET = "javax.persistence.jdbc.password";
	protected static final String DRIVER = "javax.persistence.jdbc.driver";
	protected static final String USER = "javax.persistence.jdbc.user";
	protected static final String URL = "javax.persistence.jdbc.url";
	protected static final String URL_PREFIX = "jdbc:prolobjectlink:";

	protected Map<String, EntityManagerFactory> entityManagerFactories = new LinkedHashMap<String, EntityManagerFactory>();

	protected final void assertPersistenceUnitExistenceInMapLoadedFromXml(String emName,
			Map<String, DatabaseUnitInfo> m) {
		if (m.get(emName) == null) {
			throw new PersistenceException("There are not persistence unit named " + emName);
		}
	}

	protected final DatabaseUnitInfo getPersistenceUnitInformation(PersistenceUnitInfo info) {
		String unitName = info.getPersistenceUnitName();
		URL unitRootUrl = info.getPersistenceUnitRootUrl();
		String versionXml = info.getPersistenceXMLSchemaVersion();
		PersistenceSchemaVersion xmlVersion = new PersistenceSchemaVersion(versionXml, "UTF-8");
		PersistenceUnitTransactionType transactionType = info.getTransactionType();
		String xmlPersistenceVersion = "2.0";
		String xmlPersistenceXmlns = "http://java.sun.com/xml/ns/persistence";
		String xmlPersistenceXmlnsXsi = "http://www.w3.org/2001/XMLSchema-instance";
		String xmlPersistenceXsiSchemalocation = "http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd";
		PersistenceVersion persistenceVersion = new PersistenceVersion(xmlPersistenceVersion, xmlPersistenceXmlns,
				xmlPersistenceXmlnsXsi, xmlPersistenceXsiSchemalocation);
		PersistenceUnitInformation unit = new PersistenceUnitInformation(unitRootUrl, xmlVersion,
				persistenceVersion, unitName, transactionType);

		// TODO add all persistence unit information
		unit.setValidationMode(info.getValidationMode().toString());

		return unit;
	}

	private DatabaseEngine createDatabaseEngine(DatabaseUnitInfo info, Map<?, ?> map) {
		try {
			DatabaseEngine database = null;
			String urlString = "" + info.getProperties().get(URL) + "";
			urlString = urlString.replace(URL_PREFIX, "");
			String url = new URL(urlString).getProtocol().toUpperCase();
			if (url.equals("" + Protocol.MEMPDB + "")) {
				database = MemoryHierarchical.newInstance(info, map);
			} else if (url.equals("" + Protocol.REMPDB + "")) {
				database = RemoteHierarchical.newInstance(info, map);
			} else if (url.equals("" + Protocol.FILE + "")) {
				database = EmbeddedHierarchical.newInstance(info, map);
			}
			return database;
		} catch (MalformedURLException e) {
			LoggerUtils.error(getClass(), e);
		}
		return null;
	}

	private DatabaseEngine generateSchema0(DatabaseUnitInfo info, Map map) {
		DatabaseEngine database = createDatabaseEngine(info, map);
		assert database != null;
		database.getSchema().flush();
		return database;
	}

	public final EntityManagerFactory createEntityManagerFactory(String emName, Map map) {
		EntityManagerFactory emf = entityManagerFactories.get(emName);
		if (emf == null) {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			Map<String, DatabaseUnitInfo> m = p.parsePersistenceXml(loader.getResource(XML));
			assertPersistenceUnitExistenceInMapLoadedFromXml(emName, m);
			emf = createContainerEntityManagerFactory(m.get(emName), map);
		}
		return emf;
	}

	public final EntityManagerFactory createContainerEntityManagerFactory(DatabaseUnitInfo info, Map map) {
		DatabaseEngine database = createDatabaseEngine(info, map);
		assert database != null;
		return new JpaEntityManagerFactory(database, info.getProperties());
	}

	public final EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info, Map map) {
		return createContainerEntityManagerFactory(getPersistenceUnitInformation(info), map);
	}

	public final void generateSchema(DatabaseUnitInfo info, Map map) {
		generateSchema0(info, map);
	}

	public final void generateSchema(PersistenceUnitInfo info, Map map) {
		generateSchema0(getPersistenceUnitInformation(info), map);
	}

	public final boolean generateSchema(String persistenceUnitName, Map map) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Map<String, DatabaseUnitInfo> m = p.parsePersistenceXml(loader.getResource(XML));
		assertPersistenceUnitExistenceInMapLoadedFromXml(persistenceUnitName, m);
		DatabaseUnitInfo unit = m.get(persistenceUnitName);
		DatabaseEngine database = generateSchema0(unit, map);
		return database.exist();
	}

	public final ProviderUtil getProviderUtil() {
		return providerUtil;
	}

}

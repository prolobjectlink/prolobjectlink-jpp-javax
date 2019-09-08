/*
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.db.em;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Cache;
import javax.persistence.EntityGraph;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;

import org.prolobjectlink.db.DatabaseEngine;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.db.jpa.JpaAttributeConverter;
import org.prolobjectlink.db.jpa.JpaCache;
import org.prolobjectlink.db.jpa.JpaEntityManagerFactory;
import org.prolobjectlink.db.jpa.JpaPersistenceUnitUtil;
import org.prolobjectlink.db.jpa.JpaResultSetMapping;
import org.prolobjectlink.db.jpa.criteria.JpaCriteriaBuilder;
import org.prolobjectlink.db.jpa.metamodel.JpaMetamodel;
import org.prolobjectlink.prolog.PrologTerm;

/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractEntityManagerFactory {

	// second level cache
	protected Cache cache;

	// container database engine
	protected final DatabaseEngine database;

	//
	protected final PersistenceUnitUtil persistenceUnitUtil;

	// user defined names for entities
	protected final Map<String, Class<?>> entityMap = new LinkedHashMap<String, Class<?>>();

	// user defined named queries container
	protected final Map<String, Query> namedQueries = new LinkedHashMap<String, Query>();

	// user defined named entity graphs container
	protected final Map<String, EntityGraph<?>> namedEntityGraphs = new LinkedHashMap<String, EntityGraph<?>>();

	// result set mappings for native queries result
	protected final Map<String, JpaResultSetMapping> resultSetMappings = new LinkedHashMap<String, JpaResultSetMapping>();

	public AbstractEntityManagerFactory(DatabaseEngine database) {
		this.persistenceUnitUtil = new JpaPersistenceUnitUtil();
		this.cache = new JpaCache(persistenceUnitUtil);
		this.database = database;
//		this.database.begin();
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return new JpaCriteriaBuilder(getMetamodel());
	}

	public Metamodel getMetamodel() {
		Schema s = database.getSchema();
		return new JpaMetamodel(s);
	}

	public boolean isOpen() {
		return database.isOpen();
	}

	public void close() {
		namedQueries.clear();
		if (database != null) {
			database.clear();
			database.close();
		}
		if (cache != null) {
			cache.evictAll();
		}
	}

	public Cache getCache() {
		return cache;
	}

	public PersistenceUnitUtil getPersistenceUnitUtil() {
		return persistenceUnitUtil;
	}

	public void addNamedQuery(String name, Query query) {
		namedQueries.put(name, query);
	}

	public <T> void addNamedEntityGraph(String graphName, EntityGraph<T> entityGraph) {
		namedEntityGraphs.put(graphName, entityGraph);
	}

	public <T> T unwrap(Class<T> cls) {
		if (cls.equals(JpaEntityManagerFactory.class)) {
			return (T) this;
		}
		throw new PersistenceException("Impossible  unwrap " + cls.getName());
	}

	public void addEntity(String name, Class<?> clazz) {
		entityMap.put(name, clazz);
	}

	public void addResultSetMapping(JpaResultSetMapping mapping) {
		resultSetMappings.put(mapping.getName(), mapping);
	}

	public AttributeConverter<Object, PrologTerm> getAttributeConverter() {
		return new JpaAttributeConverter(database.getProvider());
	}

	public final Map<String, EntityGraph<?>> getNamedEntityGraphs() {
		return namedEntityGraphs;
	}

	public final Map<String, Query> getNamedQueries() {
		return namedQueries;
	}

}

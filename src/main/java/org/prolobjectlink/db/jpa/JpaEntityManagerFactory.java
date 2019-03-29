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
package org.prolobjectlink.db.jpa;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SynchronizationType;

import org.prolobjectlink.db.DatabaseEngine;
import org.prolobjectlink.db.common.AbstractEntityManagerFactory;

/**
 * EntityManagerFactory implementation
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public final class JpaEntityManagerFactory extends AbstractEntityManagerFactory implements EntityManagerFactory {

	// property key-value map
	protected Map<String, Object> properties = new HashMap<String, Object>();

	public JpaEntityManagerFactory(DatabaseEngine database, Map<Object, Object> map) {
		super(database);
		for (Entry<Object, Object> e : map.entrySet()) {
			this.properties.put("" + e.getKey() + "", e.getValue());
		}
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public EntityManager createEntityManager() {
		return createEntityManager(properties);
	}

	public EntityManager createEntityManager(Map map) {
		return createEntityManager(SynchronizationType.SYNCHRONIZED, properties);
	}

	public EntityManager createEntityManager(SynchronizationType synchronizationType) {
		return createEntityManager(synchronizationType, properties);
	}

	public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {

		// TODO create database here

		return new JpaEntityManager(database, this, synchronizationType, map, entityMap, namedQueries,
				namedEntityGraphs, resultSetMappings);
	}

}

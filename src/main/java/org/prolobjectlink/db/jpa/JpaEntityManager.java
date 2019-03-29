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

import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;

import org.prolobjectlink.db.DatabaseEngine;
import org.prolobjectlink.db.common.AbstractEntityManager;

public final class JpaEntityManager extends AbstractEntityManager implements EntityManager {

	public JpaEntityManager(DatabaseEngine database, EntityManagerFactory managerFactory,
			SynchronizationType synchronizationType, Map properties, Map<String, Class<?>> entityMap,
			Map<String, Query> namedQueries, Map<String, EntityGraph<?>> namedEntityGraphs,
			Map<String, JpaResultSetMapping> resultSetMappings) {
		super(database, managerFactory, synchronizationType, properties, entityMap, namedQueries, namedEntityGraphs,
				resultSetMappings);
	}

}

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
package io.github.prolobjectlink.db.jpa;

import java.util.List;

import javax.persistence.PersistenceUnitUtil;

import io.github.prolobjectlink.db.DatabaseEngine;

/** @author Jose Zalacain @since 1.0 */
public abstract class JpaAbstractContainer {

	//
	protected final DatabaseEngine database;

	//
	protected final PersistenceUnitUtil persistenceUnitUtil;

	protected JpaAbstractContainer(DatabaseEngine database, PersistenceUnitUtil persistenceUnitUtil) {
		this.persistenceUnitUtil = persistenceUnitUtil;
		this.database = database;
	}

	protected final <O> List<O> findAll(Class<O> clazz) {
		return database.createQuery(clazz).getSolutions();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((database == null) ? 0 : database.hashCode());
		result = prime * result + ((persistenceUnitUtil == null) ? 0 : persistenceUnitUtil.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JpaAbstractContainer other = (JpaAbstractContainer) obj;
		if (database == null) {
			if (other.database != null)
				return false;
		} else if (!database.equals(other.database)) {
			return false;
		}
		if (persistenceUnitUtil == null) {
			if (other.persistenceUnitUtil != null)
				return false;
		} else if (!persistenceUnitUtil.equals(other.persistenceUnitUtil)) {
			return false;
		}
		return true;
	}

}

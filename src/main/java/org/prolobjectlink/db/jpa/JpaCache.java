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
package org.prolobjectlink.db.jpa;

import javax.persistence.Cache;
import javax.persistence.PersistenceUnitUtil;

public final class JpaCache implements Cache {

	// TODO implement a cache map and release database use

	public JpaCache(PersistenceUnitUtil persistenceUnitUtil) {
		// TODO Auto-generated method stub
	}

	void add(Object object) {
		// TODO Auto-generated method stub
	}

	public boolean contains(Class cls, Object primaryKey) {
		// TODO Auto-generated method stub
		return true;
	}

	public void evict(Class cls, Object primaryKey) {
		// TODO Auto-generated method stub
	}

	public void evict(Class cls) {
		// TODO Auto-generated method stub
	}

	public void evictAll() {
		// TODO Auto-generated method stub
	}

	public <T> T unwrap(Class<T> cls) {

		return null;
	}

}

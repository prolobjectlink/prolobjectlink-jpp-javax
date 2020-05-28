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
package io.github.prolobjectlink.db.jdbc;

import java.util.Properties;

import io.github.prolobjectlink.db.DatabaseDriver;

public abstract class MemoryDriver extends DatabaseDriver {

	protected String wildcard;

	public MemoryDriver(Properties properties) {
		super(properties);
	}

	public MemoryDriver(String dbdirver, String dbprefix, String wildcard, String dbname, String dbuser, String dbpwd) {
		super(dbdirver, dbprefix, dbname, dbuser, dbpwd);
		this.wildcard = wildcard;
	}

	public final String getWildcard() {
		return wildcard;
	}

	public final void setWildcard(String wildcard) {
		this.wildcard = wildcard;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((wildcard == null) ? 0 : wildcard.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemoryDriver other = (MemoryDriver) obj;
		if (wildcard == null) {
			if (other.wildcard != null)
				return false;
		} else if (!wildcard.equals(other.wildcard)) {
			return false;
		}
		return true;
	}

	public abstract String getDbURL();

}

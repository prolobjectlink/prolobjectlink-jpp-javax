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
package org.prolobjectlink.db.jdbc;

import org.prolobjectlink.db.DataBaseDriver;

public abstract class EmbeddedDriver extends DataBaseDriver {

	protected String dbpath;

	public EmbeddedDriver(String dbengine, String dbdirver, String dbprefix, String dbpath, String dbname,
			String dbuser, String dbpwd) {
		super(dbengine, dbdirver, dbprefix, dbname, dbuser, dbpwd);
		this.dbpath = dbpath;
	}

	public String getDbpath() {
		return dbpath;
	}

	public void setDbpath(String dbpath) {
		this.dbpath = dbpath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dbpath == null) ? 0 : dbpath.hashCode());
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
		EmbeddedDriver other = (EmbeddedDriver) obj;
		if (dbpath == null) {
			if (other.dbpath != null)
				return false;
		} else if (!dbpath.equals(other.dbpath))
			return false;
		return true;
	}

	public abstract String getDbURL();

}

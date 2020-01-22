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
package org.prolobjectlink.db.jdbc.remote;

import java.sql.SQLException;
import java.util.Properties;

import org.hsqldb.jdbcDriver;
import org.prolobjectlink.db.jdbc.RemoteDriver;

public final class HSQLDBDriver extends RemoteDriver {

	public static final String name = "HSQLDB (Server)";
	public static final String prefix = "jdbc:hsqldb:hsql:";
	private static final String driver = jdbcDriver.class.getName();

	public HSQLDBDriver(Properties properties) {
		super(properties);
		dbprefix = prefix;
	}

	public HSQLDBDriver(String dbhost, String dbport, String dbname, String dbuser, String dbpwd) {
		super(driver, prefix, dbhost, dbname, dbport, dbuser, dbpwd);
	}

	@Override
	public String getDbURL() {
		// jdbc:hsqldb:hsql:HOST/MYDATABASE
		return dbprefix + "//" + dbhost + "/" + dbname;
	}

	@Override
	public boolean createDatabase() throws SQLException {
		return true;
	}

}

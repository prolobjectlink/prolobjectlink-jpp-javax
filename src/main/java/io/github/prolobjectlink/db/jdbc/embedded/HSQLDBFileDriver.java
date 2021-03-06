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
package io.github.prolobjectlink.db.jdbc.embedded;

import java.sql.SQLException;
import java.util.Properties;

import org.hsqldb.jdbcDriver;

import io.github.prolobjectlink.db.jdbc.EmbeddedDriver;

public final class HSQLDBFileDriver extends EmbeddedDriver {

	public static final String name = "HSQLDB (File)";
	public static final String prefix = "jdbc:hsqldb:file:";
	private static final String driver = jdbcDriver.class.getName();

	public HSQLDBFileDriver(Properties properties) {
		super(properties);
		dbpath = properties.get("javax.persistence.jdbc.url").toString();
		dbprefix = prefix;
	}

	public HSQLDBFileDriver(String dbpath, String dbname, String dbuser, String dbpwd) {
		super(driver, prefix, dbpath, dbname, dbuser, dbpwd);
	}

	@Override
	public String getDbURL() {
		// jdbc:hsqldb:file:[PATH_TO_DB_FILES]/MYDATABASE
		return dbprefix + dbpath;
	}

	@Override
	public boolean createDatabase() throws SQLException {
		// do nothing
		return true;
	}

}

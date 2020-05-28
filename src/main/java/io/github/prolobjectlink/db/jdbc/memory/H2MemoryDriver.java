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
package io.github.prolobjectlink.db.jdbc.memory;

import java.sql.SQLException;
import java.util.Properties;

import org.h2.Driver;

import io.github.prolobjectlink.db.jdbc.MemoryDriver;

public final class H2MemoryDriver extends MemoryDriver {

	public static final String name = "H2DB (File)";
	public static final String prefix = "jdbc:h2:file:";
	private static final String driver = Driver.class.getName();

	public H2MemoryDriver(Properties properties) {
		super(properties);
//		dbpath = properties.get("javax.persistence.jdbc.url").toString();
		dbprefix = prefix;
	}

	public H2MemoryDriver(String dbpath, String dbname, String dbuser, String dbpwd) {
		super(driver, prefix, dbpath, dbname, dbuser, dbpwd);
	}

	@Override
	public String getDbURL() {
		// jdbc:h2:[file:][<path>]<databaseName>
		return dbprefix + wildcard + dbname;
		// jdbc:h2:~/test
	}

	@Override
	public boolean createDatabase() throws SQLException {
		// do nothing
		return true;
	}

}

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
package io.github.prolobjectlink.db.jdbc.remote;

import java.sql.SQLException;
import java.util.Properties;

import org.h2.Driver;

import io.github.prolobjectlink.db.jdbc.RemoteDriver;
import io.github.prolobjectlink.web.application.WebApplication;

public final class H2DBDriver extends RemoteDriver {

	public static final String name = "H2DB (Server)";
	public static final String prefix = "jdbc:h2:tcp:";
	private static final String driver = Driver.class.getName();

	public H2DBDriver(Properties properties) {
		super(properties);
		dbprefix = prefix;
		String url = properties.getProperty(WebApplication.URL);
		this.dbhost = url.substring(url.indexOf("//") + 2, url.lastIndexOf('/'));
	}

	public H2DBDriver(String dbhost, String dbport, String dbname, String dbuser, String dbpwd) {
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

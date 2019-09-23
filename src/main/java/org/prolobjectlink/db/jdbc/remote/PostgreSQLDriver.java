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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.postgresql.Driver;
import org.prolobjectlink.db.jdbc.RemoteDriver;

/**
 * jdbc:postgresql://localhost:5432/MYDATABASE
 * 
 * @author jose
 * 
 */
public class PostgreSQLDriver extends RemoteDriver {

	private static final String port = "5432";
	public static final String name = "PostgreSQL";
	public static final String prefix = "jdbc:postgresql:";
	private static final String driver = Driver.class.getName();

	public PostgreSQLDriver(Properties properties) {
		super(properties);
		this.dbport = port;
	}

	public PostgreSQLDriver(String dbhost, String dbport, String dbname, String dbuser, String dbpwd) {
		super(driver, prefix, dbhost, dbname, dbport, dbuser, dbpwd);
	}

	@Override
	public String getDbURL() {
		return dbprefix + "//" + dbhost + ":" + dbport + "/" + dbname;
	}

	@Override
	public boolean createDatabase() throws SQLException {
		try {
			Class.forName(getDbdirver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String url = dbprefix + "//" + dbhost + ":" + dbport + "/" + "postgres";
		Connection connection = DriverManager.getConnection(url, getDbuser(), getDbpwd());
		Statement statement = connection.createStatement();
		boolean connectionOccurs = connection != null;
		if (statement != null) {
			statement.executeUpdate("CREATE DATABASE " + getDbname());
			statement.close();
		}
		if (connection != null) {
			connection.close();
		}
		return connectionOccurs;
	}

}
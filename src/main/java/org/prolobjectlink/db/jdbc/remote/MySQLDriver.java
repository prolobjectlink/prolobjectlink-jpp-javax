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
package org.prolobjectlink.db.jdbc.remote;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.prolobjectlink.db.jdbc.RemoteDriver;

import com.mysql.jdbc.Driver;

/**
 * jdbc:mysql://localhost/MYDATABASE
 * 
 * @author jose
 * 
 */
public class MySQLDriver extends RemoteDriver {

	// private static final String port = ":3306";
	public static final String name = "MySQL";
	private static final String prefix = "jdbc:mysql:";
	private static final String driver = Driver.class.getName();

	public MySQLDriver(String dbhost, String dbport, String dbname, String dbuser, String dbpwd) {
		super(name, driver, prefix, dbhost, dbname, dbport, dbuser, dbpwd);
	}

	@Override
	public String getDbURL() {
		return dbprefix + "//" + dbhost + ":" + dbport + "/" + dbname;
	}

	@Override
	public boolean createDB() throws SQLException {
		try {
			Class.forName(getDbdirver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String url = dbprefix + "//" + dbhost + ":" + dbport + "/" + "mysql";
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

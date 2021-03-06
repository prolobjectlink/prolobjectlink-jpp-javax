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
package io.github.prolobjectlink.db;

import java.io.File;
import java.io.IOException;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import io.github.prolobjectlink.logging.LoggerConstants;
import io.github.prolobjectlink.logging.LoggerUtils;
import io.github.prolobjectlink.web.application.AbstractControllerGenerator;
import io.github.prolobjectlink.web.application.WebApplication;

public abstract class DatabaseDriver {

	protected String dbdirver;
	protected String dbprefix;
	protected String dbname;
	protected String dbuser;
	protected String dbpwd;

	public DatabaseDriver(Properties properties) {
		String url = properties.getProperty(WebApplication.URL);
		this.dbdirver = properties.getProperty(WebApplication.DRIVER);
		this.dbprefix = url.substring(0, url.lastIndexOf(':') + 1);
		this.dbname = url.substring(url.lastIndexOf('/') + 1);
		this.dbuser = properties.getProperty(WebApplication.USER);
		this.dbpwd = properties.getProperty(WebApplication.PWD);
	}

	public DatabaseDriver(String dbdirver, String dbprefix, String dbname, String dbuser, String dbpwd) {
		this.dbdirver = dbdirver;
		this.dbprefix = dbprefix;
		this.dbname = dbname;
		this.dbuser = dbuser;
		this.dbpwd = dbpwd;
	}

	public String getDbdirver() {
		return dbdirver;
	}

	public void setDbdirver(String dbdirver) {
		this.dbdirver = dbdirver;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getDbprefix() {
		return dbprefix;
	}

	public void setDbprefix(String dbprefix) {
		this.dbprefix = dbprefix;
	}

	public String getDbuser() {
		return dbuser;
	}

	public void setDbuser(String dbuser) {
		this.dbuser = dbuser;
	}

	public String getDbpwd() {
		return dbpwd;
	}

	public void setDbpwd(String dbpwd) {
		this.dbpwd = dbpwd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dbdirver == null) ? 0 : dbdirver.hashCode());
		result = prime * result + ((dbname == null) ? 0 : dbname.hashCode());
		result = prime * result + ((dbprefix == null) ? 0 : dbprefix.hashCode());
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
		DatabaseDriver other = (DatabaseDriver) obj;
		if (dbdirver == null) {
			if (other.dbdirver != null)
				return false;
		} else if (!dbdirver.equals(other.dbdirver))
			return false;
		if (dbname == null) {
			if (other.dbname != null)
				return false;
		} else if (!dbname.equals(other.dbname))
			return false;
		if (dbprefix == null) {
			if (other.dbprefix != null)
				return false;
		} else if (!dbprefix.equals(other.dbprefix))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getDbdirver() + " [" + getDbURL() + "]";
	}

	public abstract String getDbURL();

	public abstract boolean createDatabase() throws SQLException;

	public final boolean getDatabasePing() {
		return getDbConnection() != null;
	}

	public final Object getMetaData() throws SQLException {
		return getDbConnection().getMetaData().getSchemas().getMetaData();
	}

	private Connection getDbConnection() {
		Connection c = null;
		try {
			Class.forName(dbdirver);
			c = DriverManager.getConnection(getDbURL(), dbuser, dbpwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	public final File getDBDirectory() {
		File dbRoot = null;
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		File prt = pdk.getParentFile();
		try {
			if (!prt.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
				// production mode
				dbRoot = new File(prt.getCanonicalPath() + File.separator + "db");
			} else {
				// development mode
				dbRoot = new File("db");
			}
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return dbRoot;
	}

	public final String getCurrentPath() {
		Class<?> c = AbstractControllerGenerator.class;
		ProtectionDomain d = c.getProtectionDomain();
		CodeSource s = d.getCodeSource();
		return s.getLocation().getPath();
	}

}

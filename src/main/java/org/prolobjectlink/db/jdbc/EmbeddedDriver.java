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
package org.prolobjectlink.db.jdbc;

import java.io.File;
import java.io.IOException;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Properties;

import org.prolobjectlink.db.DatabaseDriver;
import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;
import org.prolobjectlink.web.application.AbstractControllerGenerator;

public abstract class EmbeddedDriver extends DatabaseDriver {

	protected String dbpath;

	public EmbeddedDriver(Properties properties) {
		super(properties);
	}

	public EmbeddedDriver(String dbdirver, String dbprefix, String dbpath, String dbname, String dbuser, String dbpwd) {
		super(dbdirver, dbprefix, dbname, dbuser, dbpwd);
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
		} else if (!dbpath.equals(other.dbpath)) {
			return false;
		}
		return true;
	}

	public final static File getPrtDirectory() {
		File appRoot = null;
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		File prt = pdk.getParentFile();
		try {
			if (!prt.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
				// production mode
				appRoot = new File(prt.getCanonicalPath() + File.separator + "db");
			} else {
				// development mode
				appRoot = new File("db");
			}
		} catch (IOException e) {
			LoggerUtils.error(EmbeddedDriver.class, LoggerConstants.IO, e);
		}
		return appRoot;
	}

	public final static String getCurrentPath() {
		Class<?> c = AbstractControllerGenerator.class;
		ProtectionDomain d = c.getProtectionDomain();
		CodeSource s = d.getCodeSource();
		return s.getLocation().getPath();
	}

	public abstract String getDbURL();

}

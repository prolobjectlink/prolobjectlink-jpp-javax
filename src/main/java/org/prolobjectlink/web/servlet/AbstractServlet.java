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
package org.prolobjectlink.web.servlet;

import java.io.File;
import java.io.IOException;
import java.security.CodeSource;
import java.security.ProtectionDomain;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;

import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;
import org.prolobjectlink.web.application.AbstractControllerGenerator;
import org.prolobjectlink.web.application.WebApplication;

public class AbstractServlet extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 4877024796708886136L;

	public final File getBinDirectory() {
		File appRoot = null;
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		File prt = pdk.getParentFile();
		try {
			if (!prt.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
				// production mode
				appRoot = new File(prt.getCanonicalPath() + File.separator + "bin");
			} else {
				// development mode
				appRoot = new File("lib");
			}
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return appRoot;
	}

	public final File getDBDirectory() {
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
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return appRoot;
	}
	
	public final File getETCDirectory() {
		File appRoot = null;
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		File prt = pdk.getParentFile();
		try {
			if (!prt.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
				// production mode
				appRoot = new File(prt.getCanonicalPath() + File.separator + "etc");
			} else {
				// development mode
				appRoot = new File("etc");
			}
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return appRoot;
	}

	public final File getLibDirectory() {
		File appRoot = null;
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		File prt = pdk.getParentFile();
		try {
			if (!prt.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
				// production mode
				appRoot = new File(prt.getCanonicalPath() + File.separator + "lib");
			} else {
				// development mode
				appRoot = new File("lib");
			}
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return appRoot;
	}

	public final File getMiscDirectory() {
		File appRoot = null;
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		File prt = pdk.getParentFile();
		try {
			if (!prt.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
				// production mode
				appRoot = new File(prt.getCanonicalPath() + File.separator + "misc");
			} else {
				// development mode
				appRoot = new File("misc");
			}
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return appRoot;
	}

	public final File getPrtDirectory() {
		File appRoot = null;
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		File prt = pdk.getParentFile();
		try {
			if (!prt.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
				// production mode
				appRoot = new File(prt.getCanonicalPath() + File.separator + "prt");
			} else {
				// development mode
				appRoot = new File("prt");
			}
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return appRoot;
	}

	public final File getWebDirectory() {
		File appRoot = null;
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		File prt = pdk.getParentFile();
		try {
			if (!prt.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
				// production mode
				appRoot = new File(prt.getCanonicalPath() + File.separator + WebApplication.ROOT);
			} else {
				// development mode
				appRoot = new File(WebApplication.ROOT);
			}
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return appRoot;
	}

	public final String getCurrentPath() {
		Class<?> c = AbstractControllerGenerator.class;
		ProtectionDomain d = c.getProtectionDomain();
		CodeSource s = d.getCodeSource();
		return s.getLocation().getPath();
	}

	protected final boolean isValidJarEntry(String name) {
		if (!name.endsWith(".class") || name.contains("$")) {
			return false;
		}
		return true;
	}

}

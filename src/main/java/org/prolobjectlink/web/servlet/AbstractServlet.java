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
import java.io.InputStream;
import java.io.OutputStream;
import java.security.CodeSource;
import java.security.ProtectionDomain;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;

import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;
import org.prolobjectlink.web.application.AbstractControllerGenerator;
import org.prolobjectlink.web.application.WebApplication;

public class AbstractServlet extends HttpServlet implements Servlet {

	protected static final String CONTENT_TYPE = "application/x-download";
	private static final long serialVersionUID = 4877024796708886136L;
	protected static final long MAX_IO_BUFFER_SIZE = Long.MAX_VALUE;
	protected static final int IO_BUFFER_SIZE = 4 * 1024;

	public final String getUserName() {
		return System.getProperty("user.name");
	}

	public final String getJavaVersion() {
		return System.getProperty("java.version");
	}

	public final String getJavaVendor() {
		return System.getProperty("java.vendor");
	}

	public final String getJavaName() {
		return System.getProperty("java.vm.name");
	}

	public final String getJavaHome() {
		return System.getProperty("java.home");
	}

	public final boolean runOnOsX() {
		return getOsName().equals("Mac OS X") || getOsName().equals("Darwin");
	}

	public final boolean runOnWindows() {
		return getOsName().startsWith("Windows");
	}

	public final boolean runOnLinux() {
		return getOsName().equals("Linux");
	}

	public final String getOsName() {
		String os = System.getProperty("os.name");
		if (os == null)
			return "unknow";
		return os;
	}

	public String getClassPath() {
		return System.getenv("java.class.path");
	}

	public final String getPath() {
		return System.getenv("Path");
	}

	public final String getPathSeparator() {
		return System.getProperty("path.separator");
	}

	public final String getPathExt() {
		return System.getenv("PATHEXT");
	}

	public final Runtime getRuntime() {
		return Runtime.getRuntime();
	}

	public final String getUserHome() {
		return System.getProperty("user.home");
	}

	public final String getUserDir() {
		return System.getProperty("user.dir");
	}

	public final String getWorkDir() {
		return System.getProperty("user.dir");
	}

	public final String getTempDir() {
		return System.getProperty("java.io.tmpdir");
	}

	public final String getArch() {
		return System.getProperty("os.arch");
	}

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
				appRoot = new File("bin");
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

	public final synchronized long copy(InputStream in, OutputStream out) {
		long copied = 0;
		try {
			long length = MAX_IO_BUFFER_SIZE;
			int len = (int) Math.min(length, IO_BUFFER_SIZE);
			byte[] buffer = new byte[len];
			while (length > 0) {
				len = in.read(buffer, 0, len);
				if (len < 0) {
					break;
				}
				if (out != null) {
					out.write(buffer, 0, len);
				}
				copied += len;
				length -= len;
				len = (int) Math.min(length, IO_BUFFER_SIZE);
			}
			return copied;
		} catch (Exception e) {
			LoggerUtils.error(getClass(), "Some error occurss on copy", e);
		}
		return copied;
	}

}

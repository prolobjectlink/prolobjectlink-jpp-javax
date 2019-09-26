/*-
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.web.platform;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.CodeSource;
import java.security.ProtectionDomain;

import org.prolobjectlink.AbstractPlatform;
import org.prolobjectlink.Platform;
import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;
import org.prolobjectlink.web.application.AbstractControllerGenerator;
import org.prolobjectlink.web.application.WebApplication;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class AbstractWebPlatform extends AbstractPlatform implements Platform {

	protected boolean started;
	protected final Runtime runtime;

	public AbstractWebPlatform() {
		runtime = Runtime.getRuntime();
	}

	public final Process run(String cmd) {
		try {
			return runtime.exec(cmd);
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		throw new RuntimeException("Can't run process " + cmd);
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

	public final String getCurrentPath() {
		Class<?> c = AbstractControllerGenerator.class;
		ProtectionDomain d = c.getProtectionDomain();
		CodeSource s = d.getCodeSource();
		return s.getLocation().getPath();
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

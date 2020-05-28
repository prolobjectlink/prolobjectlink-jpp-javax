/*-
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2020 Prolobjectlink Project
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
package io.github.prolobjectlink.web.faces.report;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.CodeSource;
import java.security.ProtectionDomain;

import io.github.prolobjectlink.AbstractPlatform;
import io.github.prolobjectlink.logging.LoggerConstants;
import io.github.prolobjectlink.logging.LoggerUtils;
import io.github.prolobjectlink.web.application.AbstractControllerGenerator;
import io.github.prolobjectlink.web.application.WebApplication;

public abstract class AbstractWebReport extends AbstractPlatform implements WebReport {

	private final String application;
	private final String destination;
	private final Runtime runtime;
	private boolean started;

	protected AbstractWebReport(String application, String destination) {
		this.runtime = Runtime.getRuntime();
		this.application = application;
		this.destination = destination;
	}

	public final File getWebDirectory() {
		File webRoot = null;
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		File prt = pdk.getParentFile();
		try {
			if (!prt.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
				// production mode
				webRoot = new File(prt.getCanonicalPath() + File.separator + WebApplication.ROOT);
			} else {
				// development mode
				webRoot = new File(WebApplication.ROOT);
			}
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return webRoot;
	}

	public final String getCurrentPath() {
		Class<?> c = AbstractControllerGenerator.class;
		ProtectionDomain d = c.getProtectionDomain();
		CodeSource s = d.getCodeSource();
		return s.getLocation().getPath();
	}

	@Override
	public void openBrowser(String url) {

		if (runOnLinux()) {

			// See if the default browser is Konqueror by resolving the symlink.
			boolean isDefaultKonqueror = false;
			try {
				// Find out the location of the x-www-browser link from path.
				Process process = runtime.exec("which x-www-browser");
				BufferedInputStream ins = new BufferedInputStream(process.getInputStream());
				BufferedReader bufreader = new BufferedReader(new InputStreamReader(ins));
				String defaultLinkPath = bufreader.readLine();
				ins.close();

				// The path is null if the link did not exist.
				if (defaultLinkPath != null) {
					// See if the default browser is Konqueror.
					File file = new File(defaultLinkPath);
					String canonical = file.getCanonicalPath();
					if (canonical.indexOf("konqueror") != -1) {
						isDefaultKonqueror = true;
					}
				}
			} catch (IOException e1) {
				// The symlink was probably not found, so this is ok.
			}

			// Try x-www-browser, which is symlink to the default browser,
			// except if we found that it is Konqueror.
			if (!started && !isDefaultKonqueror) {
				try {
					runtime.exec("x-www-browser " + url);
					started = true;
				} catch (final IOException e) {
				}
			}

			// Try firefox
			if (!started) {
				try {
					runtime.exec("firefox " + url);
					started = true;
				} catch (final IOException e) {
				}
			}

			// Try mozilla
			if (!started) {
				try {
					runtime.exec("mozilla " + url);
					started = true;
				} catch (final IOException e) {
				}
			}

			// Try konqueror
			if (!started) {
				try {
					runtime.exec("konqueror " + url);
					started = true;
				} catch (final IOException e) {
				}
			}

		} else if (runOnOsX()) {
			if (!started) {
				try {
					runtime.exec("open " + url);
					started = true;
				} catch (final IOException e) {
				}
			}
		} else if (runOnWindows()) {

			if (!started) {
				try {
					runtime.exec("cmd /c start " + url);
					started = true;
				} catch (final IOException e) {
				}
			}

		}

	}

	public String getApplication() {
		return application;
	}

	public String getDestination() {
		return destination;
	}

	@Override
	public String toString() {
		return "AbstractWebReport [application=" + application + ", destination=" + destination + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((application == null) ? 0 : application.hashCode());
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
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
		AbstractWebReport other = (AbstractWebReport) obj;
		if (application == null) {
			if (other.application != null)
				return false;
		} else if (!application.equals(other.application)) {
			return false;
		}
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination)) {
			return false;
		}
		return true;
	}

}

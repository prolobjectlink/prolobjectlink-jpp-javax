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
package org.prolobjectlink.web.application;

import java.io.File;
import java.io.IOException;
import java.security.CodeSource;
import java.security.ProtectionDomain;

import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;

public abstract class AbstractWebApplication implements WebApplication {

	public final File getWebDirectory() {
		File appRoot = null;
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		File prt = pdk.getParentFile();
		try {
			if (!prt.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
				// production mode
				appRoot = new File(prt.getCanonicalPath() + File.separator + ROOT);
			} else {
				// development mode
				appRoot = new File(ROOT);
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

}

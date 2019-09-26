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
package org.prolobjectlink.web.application;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;

public final class ApplicationGenerator extends AbstractWebApplication implements WebApplication {

	public void generate(String name) {

		PrintWriter a = null;
		PrintWriter b = null;
		PrintWriter c = null;
		PrintWriter d = null;

		try {

			String webdir = getWebDirectory().getCanonicalPath();
			File appdir = new File(webdir + File.separator + name);
			appdir.mkdir();

			// web folders
			File controllerFolder = new File(webdir + File.separator + name + "/controller");
			controllerFolder.mkdir();
			File modelFolder = new File(webdir + File.separator + name + "/model");
			modelFolder.mkdir();
			File viewFolder = new File(webdir + File.separator + name + "/view");
			viewFolder.mkdir();

			// web file
			a = new PrintWriter(new File(webdir + File.separator + name + "/controller.pl"));
			b = new PrintWriter(new File(webdir + File.separator + name + "/database.pl"));
			c = new PrintWriter(new File(webdir + File.separator + name + "/index.html"));
			d = new PrintWriter(new File(webdir + File.separator + name + "/model.pl"));

			a.write("");
			b.write("");
			c.write("");
			d.write("");

		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		} finally {
			if (a != null) {
				a.close();
			}
			if (b != null) {
				b.close();
			}
			if (c != null) {
				c.close();
			}
			if (d != null) {
				d.close();
			}
		}

	}

}

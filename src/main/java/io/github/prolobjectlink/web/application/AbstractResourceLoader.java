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
package io.github.prolobjectlink.web.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.github.prolobjectlink.logging.LoggerConstants;
import io.github.prolobjectlink.logging.LoggerUtils;
import io.github.prolobjectlink.web.servlet.ResourceServlet;

public abstract class AbstractResourceLoader extends AbstractWebApplication implements ResourceLoader {

	private final List<ServletUrlMapping> mappings = new ArrayList<ServletUrlMapping>();
	private static final Set<String> resources = new HashSet<String>();
	static {

		resources.add("/io/github/prolobjectlink/web/css/schemes/darcula.css");
		resources.add("/io/github/prolobjectlink/web/css/schemes/darcula.min.css");
		resources.add("/io/github/prolobjectlink/web/css/schemes/red-alert.css");
		resources.add("/io/github/prolobjectlink/web/css/schemes/red-alert.min.css");
		resources.add("/io/github/prolobjectlink/web/css/schemes/red-dark.css");
		resources.add("/io/github/prolobjectlink/web/css/schemes/red-dark.min.css");
		resources.add("/io/github/prolobjectlink/web/css/schemes/red-mirohost.css");
		resources.add("/io/github/prolobjectlink/web/css/schemes/red-mirohost.min.css");
		resources.add("/io/github/prolobjectlink/web/css/schemes/sky-net.css");
		resources.add("/io/github/prolobjectlink/web/css/schemes/sky-net.min.css");

		resources.add("/io/github/prolobjectlink/web/css/bootstrap.css");
		resources.add("/io/github/prolobjectlink/web/css/bootstrap.css.map");
		resources.add("/io/github/prolobjectlink/web/css/bootstrap.datatables.min.css");
		resources.add("/io/github/prolobjectlink/web/css/bootstrap.min.css");
		resources.add("/io/github/prolobjectlink/web/css/bootstrap.min.css.map");
		resources.add("/io/github/prolobjectlink/web/css/bootstrap-grid.css");
		resources.add("/io/github/prolobjectlink/web/css/bootstrap-grid.css.map");
		resources.add("/io/github/prolobjectlink/web/css/bootstrap-grid.min.css");
		resources.add("/io/github/prolobjectlink/web/css/bootstrap-grid.min.css.map");
		resources.add("/io/github/prolobjectlink/web/css/bootstrap-reboot.css");
		resources.add("/io/github/prolobjectlink/web/css/bootstrap-reboot.css.map");
		resources.add("/io/github/prolobjectlink/web/css/bootstrap-reboot.min.css");
		resources.add("/io/github/prolobjectlink/web/css/bootstrap-reboot.min.css.map");
		resources.add("/io/github/prolobjectlink/web/css/fontawesome-all.css");
		resources.add("/io/github/prolobjectlink/web/css/fontawesome-all.min.css");
		resources.add("/io/github/prolobjectlink/web/css/prolobjectlink.css");
		resources.add("/io/github/prolobjectlink/web/css/prolobjectlink.min.css");

		resources.add("/io/github/prolobjectlink/web/js/bootstrap.bundle.js");
		resources.add("/io/github/prolobjectlink/web/js/bootstrap.bundle.js.map");
		resources.add("/io/github/prolobjectlink/web/js/bootstrap.bundle.min.js");
		resources.add("/io/github/prolobjectlink/web/js/bootstrap.bundle.min.js.map");
		resources.add("/io/github/prolobjectlink/web/js/bootstrap.datatables.min.js");
		resources.add("/io/github/prolobjectlink/web/js/bootstrap.js");
		resources.add("/io/github/prolobjectlink/web/js/bootstrap.js.map");
		resources.add("/io/github/prolobjectlink/web/js/bootstrap.min.js");
		resources.add("/io/github/prolobjectlink/web/js/bootstrap.min.js.map");
		resources.add("/io/github/prolobjectlink/web/js/chart.bundle.js");
		resources.add("/io/github/prolobjectlink/web/js/chart.bundle.min.js");
		resources.add("/io/github/prolobjectlink/web/js/chart.js");
		resources.add("/io/github/prolobjectlink/web/js/chart.min.js");
		resources.add("/io/github/prolobjectlink/web/js/jquery.datatables.min.js");
		resources.add("/io/github/prolobjectlink/web/js/jquery.easing.min.js");
		resources.add("/io/github/prolobjectlink/web/js/jquery-3.4.1.js");
		resources.add("/io/github/prolobjectlink/web/js/jquery-3.4.1.min.js");
		resources.add("/io/github/prolobjectlink/web/js/jquery-3.4.1.min.map");
		resources.add("/io/github/prolobjectlink/web/js/popper.min.js");
		resources.add("/io/github/prolobjectlink/web/js/prolobjectlink.js");
		resources.add("/io/github/prolobjectlink/web/js/prolobjectlink.min.js");
		resources.add("/io/github/prolobjectlink/web/js/tooltip.min.js");
		resources.add("/io/github/prolobjectlink/web/js/ext-all.js");
		resources.add("/io/github/prolobjectlink/web/js/prolog-compiler.js");
		resources.add("/io/github/prolobjectlink/web/js/yieldprolog.js");

		resources.add("/io/github/prolobjectlink/web/webfonts/fa-brands-400.eot");
		resources.add("/io/github/prolobjectlink/web/webfonts/fa-brands-400.svg");
		resources.add("/io/github/prolobjectlink/web/webfonts/fa-brands-400.ttf");
		resources.add("/io/github/prolobjectlink/web/webfonts/fa-brands-400.woff");
		resources.add("/io/github/prolobjectlink/web/webfonts/fa-brands-400.woff2");
		resources.add("/io/github/prolobjectlink/web/webfonts/fa-regular-400.eot");
		resources.add("/io/github/prolobjectlink/web/webfonts/fa-regular-400.svg");
		resources.add("/io/github/prolobjectlink/web/webfonts/fa-regular-400.ttf");
		resources.add("/io/github/prolobjectlink/web/webfonts/fa-regular-400.woff");
		resources.add("/io/github/prolobjectlink/web/webfonts/fa-regular-400.woff2");
		resources.add("/io/github/prolobjectlink/web/webfonts/fa-solid-900.eot");
		resources.add("/io/github/prolobjectlink/web/webfonts/fa-solid-900.svg");
		resources.add("/io/github/prolobjectlink/web/webfonts/fa-solid-900.ttf");
		resources.add("/io/github/prolobjectlink/web/webfonts/fa-solid-900.woff");
		resources.add("/io/github/prolobjectlink/web/webfonts/fa-solid-900.woff2");

	}

	public AbstractResourceLoader() {

		for (String resource : resources) {
			StringBuilder scriptCode = new StringBuilder();
			String path = resource.replace("/io/github/prolobjectlink/web", "");
			InputStream stream = getClass().getResourceAsStream(resource);
			InputStreamReader reader = new InputStreamReader(stream);
			BufferedReader buffer = new BufferedReader(reader);
			try {
				String line = buffer.readLine();
				while (line != null) {
					scriptCode.append(line);
					line = buffer.readLine();
				}
			} catch (IOException e) {
				LoggerUtils.error(getClass(), LoggerConstants.IO, e);
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), LoggerConstants.IO, e);
				}
				try {
					buffer.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), LoggerConstants.IO, e);
				}
			}
			ResourceServlet s = new ResourceServlet("" + scriptCode + "");
			ServletUrlMapping m = new ServletUrlMapping(s, path);
			mappings.add(m);
		}
	}

	public List<ServletUrlMapping> getMappings() {
		return mappings;
	}

}

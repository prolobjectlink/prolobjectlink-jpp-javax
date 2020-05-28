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

import static io.github.prolobjectlink.logging.LoggerConstants.IO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.prolobjectlink.logging.LoggerUtils;
import io.github.prolobjectlink.web.servlet.ReportServlet;

public abstract class AbstractReportGenerator extends AbstractWebApplication implements ReportGenerator {

	private final List<ServletUrlMapping> mappings;

	public AbstractReportGenerator() {

		mappings = new ArrayList<ServletUrlMapping>();
		String report = "report";
		File appRoot = getWebDirectory();
		File[] apps = appRoot.listFiles();
		for (File file : apps) {
			String appName = file.getName();
			String appPath = file.getPath();
			String separator = File.separator;
			String path = appPath + separator + report;
			File reportFolder = new File(path);
			File[] reports = reportFolder.listFiles();
			for (File jrxml : reports) {
				try {
					String xml = jrxml.getCanonicalPath();
					String service = "/" + appName + "/" + report + "/" + jrxml.getName();
					System.out.println(service);
					ReportServlet s = new ReportServlet(xml);
					ServletUrlMapping m = new ServletUrlMapping(s, service);
					mappings.add(m);
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO, e);
				}
			}

		}
	}

	public List<ServletUrlMapping> getMappings() {
		return mappings;
	}

}

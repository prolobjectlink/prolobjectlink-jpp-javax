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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.prolobjectlink.logging.LoggerConstants;
import io.github.prolobjectlink.logging.LoggerUtils;
import io.github.prolobjectlink.web.servlet.ResourceServlet;

public abstract class AbstractDocumentLoader extends AbstractWebApplication implements ResourceLoader {

	private final List<ServletUrlMapping> mappings;

	public AbstractDocumentLoader() {

		mappings = new ArrayList<ServletUrlMapping>();

		String docs = getDocDirectory().getAbsolutePath();

		// css scripts
		File[] documents = new File(docs + "/docs").listFiles();
		for (File file : documents) {
			if (!file.isDirectory()) {
				String path = "/docs/" + file.getName();
				FileReader reader = null;
				BufferedReader buffer = null;
				StringBuilder scriptCode = new StringBuilder();
				String script = file.getAbsolutePath();
				try {
					script = file.getCanonicalPath();
					reader = new FileReader(script);
					buffer = new BufferedReader(reader);
					String line = buffer.readLine();
					while (line != null) {
						scriptCode.append(line);
						line = buffer.readLine();
					}
				} catch (IOException e) {
					LoggerUtils.error(getClass(), LoggerConstants.IO, e);
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							LoggerUtils.error(getClass(), LoggerConstants.IO, e);
						}
					}
					if (buffer != null) {
						try {
							buffer.close();
						} catch (IOException e) {
							LoggerUtils.error(getClass(), LoggerConstants.IO, e);
						}
					}
				}
				ResourceServlet s = new ResourceServlet("" + scriptCode + "");
				ServletUrlMapping m = new ServletUrlMapping(s, path);
				mappings.add(m);
			}
		}

	}

	public List<ServletUrlMapping> getMappings() {
		return mappings;
	}

}

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
package org.prolobjectlink.web.function;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.UnaryOperator;

public class RemoveFunction implements UnaryOperator<String> {

	private final String applicationDir;
	private final String databaseDir;
	private final String protocol;
	private final String host;

	public RemoveFunction(String applicationDir, String databaseDir, String protocol, String host) {
		this.applicationDir = applicationDir;
		this.databaseDir = databaseDir;
		this.protocol = protocol;
		this.host = host;
	}

	@Override
	public String apply(String arg0) {
		System.out.println("remove function ..." + arg0);
		Path appPath = FileSystems.getDefault().getPath(applicationDir + File.separator + arg0);
		try {
//			Files.delete(appPath);

			File[] dbs = new File(databaseDir).listFiles();
			for (File file : dbs) {
				if (file.isDirectory()) {
					if (file.getName().equals("hsqldb")) {
						File[] scripts = file.listFiles(new FileFilter() {

							@Override
							public boolean accept(File arg0) {
								return arg0.getName().endsWith(arg0 + ".");
							}

						});
						for (File x : scripts) {
							Path dbfilepath = FileSystems.getDefault().getPath(x.getCanonicalPath());
							Files.delete(dbfilepath);
						}
					} else if (file.getName().equals("pdb")) {

					} else if (file.getName().equals("odb")) {
						File[] scripts = file.listFiles(new FileFilter() {

							@Override
							public boolean accept(File arg0) {
								return arg0.getName().endsWith(arg0 + ".");
							}

						});
						for (File x : scripts) {
							Path dbfilepath = FileSystems.getDefault().getPath(x.getCanonicalPath());
							Files.delete(dbfilepath);
						}
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return protocol + "://" + host + "/pas/applications";
	}

}

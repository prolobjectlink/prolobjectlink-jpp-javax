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
package io.github.prolobjectlink.web.servlet.admin;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.prolobjectlink.db.file.HSQLFileFilter;
import io.github.prolobjectlink.db.file.ODBFileFilter;
import io.github.prolobjectlink.db.file.PDBFileFilter;
import io.github.prolobjectlink.web.entry.DatabaseEntry;
import io.github.prolobjectlink.web.function.AssetFunction;
import io.github.prolobjectlink.web.function.LaunchFunction;
import io.github.prolobjectlink.web.function.PathFunction;
import io.github.prolobjectlink.web.servlet.AbstractServlet;
import io.marioslab.basis.template.Template;
import io.marioslab.basis.template.TemplateContext;
import io.marioslab.basis.template.TemplateLoader;
import io.marioslab.basis.template.TemplateLoader.ClasspathTemplateLoader;

@WebServlet
public class DeleteDatabaseServlet extends AbstractServlet implements Servlet {

	private static final long serialVersionUID = 7313381101418470194L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// request information
		String protocol = req.getScheme();
		String host = req.getHeader("host");

		String pathInfo = req.getPathInfo();
		if (pathInfo != null) {
			pathInfo = pathInfo.substring(1);
		}

		// Engine and context
		TemplateLoader loader = new ClasspathTemplateLoader();
		Template template = loader.load("/io/github/prolobjectlink/web/html/databases.html");
		TemplateContext context = new TemplateContext();

		File database = getDBDirectory();

		File[] dbs = database.listFiles();
		for (File file : dbs) {
			if (file.isDirectory()) {
				if (file.getName().equals("hsqldb")) {
					HSQLFileFilter filter = new HSQLFileFilter(pathInfo);
					File[] scripts = file.listFiles(filter);
					for (File x : scripts) {
						Path dbfilepath = FileSystems.getDefault().getPath(x.getCanonicalPath());
						Files.delete(dbfilepath);
					}
				} else if (file.getName().equals("pdb")) {
					PDBFileFilter filter = new PDBFileFilter(pathInfo);
					File[] scripts = file.listFiles(filter);
					for (File x : scripts) {
						Path dbfilepath = FileSystems.getDefault().getPath(x.getCanonicalPath());
						Files.delete(dbfilepath);
					}
				} else if (file.getName().equals("odb")) {
					ODBFileFilter filter = new ODBFileFilter(pathInfo);
					File[] scripts = file.listFiles(filter);
					for (File x : scripts) {
						Path dbfilepath = FileSystems.getDefault().getPath(x.getCanonicalPath());
						Files.delete(dbfilepath);
					}
				}
			}
		}

		List<DatabaseEntry> databases = new ArrayList<DatabaseEntry>(dbs.length);
		for (File file : dbs) {
			if (file.isDirectory()) {
				if (file.getName().equals("hsqldb")) {
					File[] scripts = file.listFiles(new FileFilter() {

						@Override
						public boolean accept(File arg0) {
							return arg0.getName().endsWith(".script");
						}

					});
					for (File x : scripts) {
						String type = "HSQLDB";
						long size = x.length();
						long modified = x.lastModified();
						String name = x.getName().substring(0, x.getName().lastIndexOf(".script"));
						DatabaseEntry e = new DatabaseEntry(type, name, size, modified);
						databases.add(e);
					}
				} else if (file.getName().equals("pdb")) {

				} else if (file.getName().equals("odb")) {
					File[] scripts = file.listFiles(new FileFilter() {

						@Override
						public boolean accept(File arg0) {
							return arg0.getName().endsWith(".odb");
						}

					});
					for (File x : scripts) {
						String type = "ODB";
						long size = x.length();
						long modified = x.lastModified();
						String name = x.getName().substring(0, x.getName().lastIndexOf(".odb"));
						DatabaseEntry e = new DatabaseEntry(type, name, size, modified);
						databases.add(e);
					}
				}
			}
		}

		// variables
		context.set("databases", databases);

		// functions
		context.set("path", new PathFunction("pas", protocol, host));
		context.set("launch", new LaunchFunction(protocol, host));
		context.set("asset", new AssetFunction(protocol, host));

		// render
		template.render(context, resp.getOutputStream());

		// response
		resp.setStatus(HttpServletResponse.SC_OK);
	}

}

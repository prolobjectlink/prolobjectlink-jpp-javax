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

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.prolobjectlink.db.file.HSQLFileFilter;
import io.github.prolobjectlink.db.file.ODBFileFilter;
import io.github.prolobjectlink.db.file.PDBFileFilter;
import io.github.prolobjectlink.web.servlet.AbstractServlet;

@WebServlet
public class ExportDatabaseServlet extends AbstractServlet implements Servlet {

	private static final long serialVersionUID = 7313381101418470194L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String pathInfo = req.getPathInfo();
		if (pathInfo != null) {
			pathInfo = pathInfo.substring(1);
		}

		File db = getDBDirectory();
		File[] dbs = db.listFiles();
		File zipFile = new File(getTempDir() + pathInfo + ".zip");
		for (File file : dbs) {
			if (file.isDirectory()) {
				if (file.getName().equals("hsqldb")) {
					HSQLFileFilter filter = new HSQLFileFilter(pathInfo);
					File[] scripts = file.listFiles(filter);

					InputStream in = null;
					OutputStream out = null;
					ZipOutputStream zipOut = null;

					for (File x : scripts) {

						if (!x.getName().endsWith(".tmp")) {

							if (!zipFile.exists()) {
								File parent = zipFile.getParentFile();
								if (parent != null) {
									parent.mkdirs();
								}
							}

							//
							out = new FileOutputStream(zipFile);
							zipOut = new ZipOutputStream(out);
							zipOut.setComment(pathInfo + " database dackup");

							String path = x.getPath();
							String dbdir = db.getCanonicalPath();
							String relative = path.replace(dbdir + File.separator, "");
							relative = relative.replace(File.separator, "/");
							in = new FileInputStream(x);
							ZipEntry entry = new ZipEntry(relative);
							zipOut.putNextEntry(entry);
							copy(in, zipOut);
							zipOut.closeEntry();

						}

					}

					if (zipOut != null) {
						zipOut.close();
					}
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
					}

				} else if (file.getName().equals("pdb")) {
					PDBFileFilter filter = new PDBFileFilter(pathInfo);
					File[] scripts = file.listFiles(filter);
					for (File x : scripts) {
						System.out.println(x.getCanonicalPath());
					}
				} else if (file.getName().equals("odb")) {
					ODBFileFilter filter = new ODBFileFilter(pathInfo);
					File[] scripts = file.listFiles(filter);
					for (File x : scripts) {
						System.out.println(x.getCanonicalPath());
					}
				}
			}
		}

		ServletOutputStream outputStream = resp.getOutputStream();
		resp.setContentType(CONTENT_TYPE);
		resp.setContentLength((int) zipFile.length());
		resp.setHeader("Content-Disposition", "attachment; filename=" + pathInfo + ".zip");

		byte[] buffer = new byte[8 * 1024]; // 8k buffer
		FileInputStream input = new FileInputStream(zipFile);
		DataInputStream inputStream = new DataInputStream(input);
		int length = 0;

		try {
			while ((inputStream != null) && ((length = inputStream.read(buffer)) != -1)) {
				outputStream.write(buffer, 0, length);
			}
		} catch (Exception e) {
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			resp.setContentType("text/plain");
			resp.getOutputStream().print(stringWriter.toString());
		} finally {
			inputStream.close();
			outputStream.flush();
			outputStream.close();
		}

	}

}

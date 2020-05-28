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
package io.github.prolobjectlink.web.servlet;

import static io.github.prolobjectlink.logging.LoggerConstants.IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import io.github.prolobjectlink.db.prolog.PrologProgrammer;
import io.github.prolobjectlink.logging.LoggerUtils;
import io.github.prolobjectlink.web.introspection.IntrospectionProvider;
import io.github.prolobjectlink.web.prolog.PrologWebEngine;
import io.github.prolobjectlink.web.prolog.PrologWebProvider;

public abstract class UploadServlet extends AbstractServlet implements Servlet {

	private static final long serialVersionUID = 7313381101418470194L;

	protected void uploadApplication(HttpServletRequest req, HttpServletResponse resp, String absolutePath)
			throws IOException {

		ZipInputStream zipIn = null;

		try {

			// streams initialization
			zipIn = new ZipInputStream(new FileInputStream(absolutePath));
			ZipEntry entry = zipIn.getNextEntry();

			// while entries exist
			while (entry != null) {

				// retrieve entry name
				String fileName = entry.getName();

				// restoring windows backups on linux and vice versa
				fileName = fileName.replace('\\', File.separator.charAt(0));
				fileName = fileName.replace('/', File.separator.charAt(0));

				// root treatment
				if (fileName.startsWith(File.separator)) {
					fileName = fileName.substring(1);
				}

				// directories creation if needed
				File file = new File(getWebDirectory() + File.separator + fileName);
				if (!file.exists()) {
					File parent = file.getParentFile();
					if (parent != null) {
						parent.mkdirs();
					}
				}

				// extraction copy
				OutputStream out = new FileOutputStream(file, false);
				copy(zipIn, out);
				out.close();

				// close current entry
				zipIn.closeEntry();

				// retrieve next entry
				entry = zipIn.getNextEntry();

			}

		} catch (IOException e) {
			LoggerUtils.error(getClass(), IO + absolutePath, e);
		} finally {
			if (zipIn != null) {
				try {
					zipIn.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO, e);
				}
			}
		}

	}

	protected void uploadDatabase(HttpServletRequest req, HttpServletResponse resp, String absolutePath)
			throws IOException {

		ZipInputStream zipIn = null;

		try {

			// streams initialization
			zipIn = new ZipInputStream(new FileInputStream(absolutePath));
			ZipEntry entry = zipIn.getNextEntry();

			// while entries exist
			while (entry != null) {

				// retrieve entry name
				String fileName = entry.getName();

				// restoring windows backups on linux and vice versa
				fileName = fileName.replace('\\', File.separator.charAt(0));
				fileName = fileName.replace('/', File.separator.charAt(0));

				// root treatment
				if (fileName.startsWith(File.separator)) {
					fileName = fileName.substring(1);
				}

				// directories creation if needed
				File file = new File(getDBDirectory() + File.separator + fileName);
				if (!file.exists()) {
					File parent = file.getParentFile();
					if (parent != null) {
						parent.mkdirs();
					}
				}

				// extraction copy
				OutputStream out = new FileOutputStream(file, false);
				copy(zipIn, out);
				out.close();

				// close current entry
				zipIn.closeEntry();

				// retrieve next entry
				entry = zipIn.getNextEntry();

			}

		} catch (IOException e) {
			LoggerUtils.error(getClass(), IO + absolutePath, e);
		} finally {
			if (zipIn != null) {
				try {
					zipIn.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO, e);
				}
			}
		}

	}

	protected void uploadLibrary(HttpServletRequest req, HttpServletResponse resp, String absolutePath)
			throws IOException {

		InputStream in = null;
		OutputStream out = null;

		try {

			// copy file to libraries folder
			in = new FileInputStream(absolutePath);
			int index = absolutePath.lastIndexOf(File.separator);
			String file = absolutePath.substring(index + 1, absolutePath.length());
			out = new FileOutputStream(getLibDirectory() + File.separator + file, false);
			copy(in, out);

			// generate library to prolog runtime folder
			PrologWebProvider provider = new IntrospectionProvider();
			PrologWebEngine engine = provider.newEngine();
			PrologProgrammer programmer = engine.getProgrammer();
			JarFile jarFile = new JarFile(getLibDirectory() + File.separator + file);
			programmer.codingModel(new PrintWriter(System.out), jarFile, getPrtDirectory(), false);
			jarFile.close();

		} catch (IOException e) {
			LoggerUtils.error(getClass(), IO + absolutePath, e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO, e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO, e);
				}
			}
		}

	}

	protected void checkMultipartContent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (!ServletFileUpload.isMultipartContent(req)) {
			resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "A ocurrido un error.");
		}

	}

}

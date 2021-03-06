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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import io.github.prolobjectlink.web.entry.ApplicationEntry;
import io.github.prolobjectlink.web.function.AssetFunction;
import io.github.prolobjectlink.web.function.LaunchFunction;
import io.github.prolobjectlink.web.function.PathFunction;
import io.github.prolobjectlink.web.servlet.UploadServlet;
import io.marioslab.basis.template.Template;
import io.marioslab.basis.template.TemplateContext;
import io.marioslab.basis.template.TemplateLoader;
import io.marioslab.basis.template.TemplateLoader.ClasspathTemplateLoader;

@WebServlet
public class UploadApplicationServlet extends UploadServlet implements Servlet {

	private static final long serialVersionUID = 7313381101418470194L;

	protected void procccesfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		/////////////////////////////////////////////////////////////////

		checkMultipartContent(req, resp);
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items;
		try {
			items = upload.parseRequest(req);
			for (FileItem item : items) {
				if (item.isFormField())
					continue;
				String fileName = item.getName();
				if (fileName != null) {
					fileName = FilenameUtils.getName(fileName);
				}

				File tmpdir = new File(System.getProperty("java.io.tmpdir"));
				File uploadedFile = new File(tmpdir, fileName);
				if (!uploadedFile.exists()) {
					uploadedFile.createNewFile();
				}
				item.write(uploadedFile);

				uploadApplication(req, resp, uploadedFile.getAbsolutePath());

//				resp.setStatus(HttpServletResponse.SC_CREATED);
//				resp.getWriter().print("Archivo importado correctamente.");
				resp.flushBuffer();
				uploadedFile.delete();
			}
		} catch (FileUploadException e) {
			// resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, e
			// .getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, e
			// .getMessage());
			e.printStackTrace();
		}

		////////////////////////////////////////////////////////////////

		// request information
		String protocol = req.getScheme();
		String host = req.getHeader("host");

		// Engine and context
		TemplateLoader loader = new ClasspathTemplateLoader();
		Template template = loader.load("/io/github/prolobjectlink/web/html/applications.html");
		TemplateContext context = new TemplateContext();

		File webapps = getWebDirectory();
		File[] apps = webapps.listFiles();
		List<ApplicationEntry> applications = new ArrayList<ApplicationEntry>(apps.length);
		for (File file : apps) {
			// check application
			if (file.isDirectory()) {
				long size = file.length();
				String name = file.getName();
				long modified = file.lastModified();
				ApplicationEntry e = new ApplicationEntry(name, size, modified);
				applications.add(e);
			}
		}

		// variables
		context.set("applications", applications);

		// functions
		context.set("path", new PathFunction("pas", protocol, host));
		context.set("launch", new LaunchFunction(protocol, host));
		context.set("asset", new AssetFunction(protocol, host));

		// render
		template.render(context, resp.getOutputStream());

		// response
		resp.setStatus(HttpServletResponse.SC_OK);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		procccesfile(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		procccesfile(req, resp);
	}

}

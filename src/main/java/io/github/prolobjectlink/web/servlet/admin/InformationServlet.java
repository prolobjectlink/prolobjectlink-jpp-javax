/*-
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2020 Prolobjectlink Project
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

import java.io.IOException;

import javax.persistence.spi.PersistenceUnitInfo;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.prolobjectlink.web.function.AssetFunction;
import io.github.prolobjectlink.web.function.PathFunction;
import io.github.prolobjectlink.web.servlet.AbstractServlet;
import io.marioslab.basis.template.Template;
import io.marioslab.basis.template.TemplateContext;
import io.marioslab.basis.template.TemplateLoader;
import io.marioslab.basis.template.TemplateLoader.ClasspathTemplateLoader;

@WebServlet
public class InformationServlet extends AbstractServlet {

	private static final long serialVersionUID = -495204813202656904L;

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
		Template template = loader.load("/io/github/prolobjectlink/web/html/information.html");
		TemplateContext context = new TemplateContext();

		// list database tables from abstract servlet
		PersistenceUnitInfo unitInfo = listProperties(pathInfo);

		// variables
		context.set("unitName", unitInfo.getPersistenceUnitName());
		context.set("unitProvider", unitInfo.getPersistenceProviderClassName());
		context.set("transactionType", unitInfo.getTransactionType());
		context.set("managedClasses", unitInfo.getManagedClassNames());
		context.set("entries", unitInfo.getProperties().entrySet());

		// functions
		context.set("path", new PathFunction("pas", protocol, host));
		context.set("asset", new AssetFunction(protocol, host));

		// render
		template.render(context, resp.getOutputStream());

		// response
		resp.setStatus(HttpServletResponse.SC_OK);
	}

}

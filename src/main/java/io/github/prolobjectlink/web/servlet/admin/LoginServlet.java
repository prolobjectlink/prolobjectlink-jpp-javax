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

import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;

import io.github.prolobjectlink.web.function.AssetFunction;
import io.github.prolobjectlink.web.function.PathFunction;
import io.marioslab.basis.template.Template;
import io.marioslab.basis.template.TemplateContext;
import io.marioslab.basis.template.TemplateLoader;
import io.marioslab.basis.template.TemplateLoader.ClasspathTemplateLoader;

@WebServlet
public class LoginServlet extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 7313381101418470194L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// request information
		String protocol = req.getScheme();
		String host = req.getHeader("host");

		HttpSession session = req.getSession();
		String sessionKey = RandomStringUtils.random(64).toLowerCase();
		System.out.println(sessionKey);
		session.setAttribute("login.authenticated", sessionKey);

		// Engine and context
		TemplateLoader loader = new ClasspathTemplateLoader();
		Template template = loader.load("/io/github/prolobjectlink/web/html/manager.html");
		TemplateContext context = new TemplateContext();

		// java virtual machine
		context.set("jvm", System.getProperty("java.vm.name"));
		context.set("jvendor", System.getProperty("java.vendor"));
		context.set("jversion", System.getProperty("java.version"));

		// operating system
		context.set("osname", System.getProperty("os.name"));
		context.set("osversion", System.getProperty("os.version"));
		context.set("osarch", System.getProperty("os.arch"));

		// prolog engine
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("Prolog");
		ScriptEngineFactory factory = engine.getFactory();
		context.set("plversion", factory.getParameter(ScriptEngine.ENGINE_VERSION));
		context.set("plname", factory.getParameter(ScriptEngine.NAME));

		// servlet container
		context.set("serverversion", "4.1.1");
		context.set("servername", "Grizzly");

		// functions
		context.set("path", new PathFunction("pas", protocol, host));
		context.set("asset", new AssetFunction(protocol, host));

		// render
		template.render(context, resp.getOutputStream());

		// response
		resp.setStatus(HttpServletResponse.SC_OK);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}

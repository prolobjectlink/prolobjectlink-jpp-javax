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
package org.prolobjectlink.web.servlet.admin;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;
import org.prolobjectlink.prolog.PrologList;
import org.prolobjectlink.prolog.PrologStructure;
import org.prolobjectlink.prolog.PrologTerm;
import org.prolobjectlink.web.entry.ClassesEntry;
import org.prolobjectlink.web.entry.FieldEntry;
import org.prolobjectlink.web.function.AssetFunction;
import org.prolobjectlink.web.function.PathFunction;
import org.prolobjectlink.web.servlet.AbstractServlet;

import io.marioslab.basis.template.Template;
import io.marioslab.basis.template.TemplateContext;
import io.marioslab.basis.template.TemplateLoader;
import io.marioslab.basis.template.TemplateLoader.ClasspathTemplateLoader;

@WebServlet
public class ModelServlet extends AbstractServlet implements Servlet {

	private static final long serialVersionUID = 7313381101418470194L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// request information
		String protocol = req.getScheme();
		String host = req.getHeader("host");

		// Engine and context
		TemplateLoader loader = new ClasspathTemplateLoader();
		Template template = loader.load("/org/prolobjectlink/web/html/model.html");
		TemplateContext context = new TemplateContext();

		File webapps = getWebDirectory();
		File[] apps = webapps.listFiles();
		List<ClassesEntry> classes = new ArrayList<ClassesEntry>();
		for (File file : apps) {
			// check application
			if (file.isDirectory()) {
				String appname = file.getCanonicalPath();
				File model = new File(appname + "/model.pl");
				ScriptEngineManager manager = new ScriptEngineManager();
				ScriptEngine engine = manager.getEngineByName("Prolog");
				try {
					engine.eval(new FileReader(model));
					engine.eval("?-findall(Name/Type/Fields,entity(Name,Type,Fields),List)");
					Object object = engine.get("List");
					Object[] array = (Object[]) object;
					for (Object terna : array) {
						PrologTerm term = (PrologTerm) terna;
						String name = term.getArgument(0).getArgument(0).getFunctor();
						ClassesEntry e = new ClassesEntry(file.getName(), "" + name + "");
						PrologList list = (PrologList) term.getArgument(1);
						for (PrologTerm field : list) {
							PrologStructure s = (PrologStructure) field;
							String fieldName = s.getArgument(0).getFunctor();
							String typeName = s.getArgument(1).getFunctor();
							FieldEntry f = new FieldEntry(fieldName, typeName);
							e.addField(f);
						}
						classes.add(e);
					}
				} catch (ScriptException e) {
					LoggerUtils.error(getClass(), LoggerConstants.SYNTAX_ERROR, e);
				}
			}
		}

		// variables
		context.set("classes", classes);

		// functions
		context.set("path", new PathFunction("pas", protocol, host));
		context.set("asset", new AssetFunction(protocol, host));

		// render
		template.render(context, resp.getOutputStream());

		// response
		resp.setStatus(HttpServletResponse.SC_OK);
	}

}

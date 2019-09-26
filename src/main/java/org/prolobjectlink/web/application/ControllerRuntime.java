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
package org.prolobjectlink.web.application;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.List;
import java.util.Map;

import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.prolog.PrologClause;
import org.prolobjectlink.prolog.PrologEngine;
import org.prolobjectlink.prolog.PrologJavaConverter;
import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.PrologQuery;
import org.prolobjectlink.prolog.PrologTerm;
import org.prolobjectlink.prolog.PrologVariable;
import org.prolobjectlink.web.function.PathFuntion;

import io.marioslab.basis.template.Template;
import io.marioslab.basis.template.TemplateContext;
import io.marioslab.basis.template.TemplateLoader;
import io.marioslab.basis.template.TemplateLoader.FileTemplateLoader;

public class ControllerRuntime {

	public static void run(final String protocol, final String host, final int port, final String application,
			String procedure, Object[] arguments, OutputStream out) throws IOException {

		PrologProvider provider = new Settings().load().getProvider();
		File controllerFolder = getControllerFolder(application);
		File[] controllers = controllerFolder.listFiles();
		PrologEngine engine = provider.newEngine();
		for (File controller : controllers) {
			engine.include(controller.getCanonicalPath());
		}
		PrologJavaConverter converter = provider.getJavaConverter();
		PrologTerm[] parameters = converter.toTermsArray(arguments);
		PrologTerm[] finalArgs = new PrologTerm[parameters.length + 1];
		System.arraycopy(parameters, 0, finalArgs, 0, parameters.length);

		Map<String, List<PrologClause>> program = engine.getProgramMap();
		List<PrologClause> list = program.get(procedure + "/" + finalArgs.length);
		if (list != null && !list.isEmpty()) {
			PrologClause prologClause = list.get(0); // first only
			if (prologClause.getFunctor().equals(procedure)) {
				int index = prologClause.getArguments().length - 1;
				PrologVariable x = (PrologVariable) prologClause.getArgument(index);
				finalArgs[parameters.length] = x;
				PrologQuery query = engine.query(provider.newStructure(procedure, finalArgs));
				Map<String, Object> result = query.oneVariablesResult();
				PrologTerm[] body = prologClause.getBodyArray();
				PrologTerm render = body[body.length - 1];
				PrologTerm[] args = render.getArguments();
				Object view = converter.toObject(args[0]);
				String slash = File.separator;
				String page = WebApplication.ROOT + slash + application + slash + view;
				File viewPath = getViewFile(page);
				TemplateLoader loader = new FileTemplateLoader();
				Template template = loader.load(viewPath.getCanonicalPath());
				TemplateContext context = new TemplateContext();
				context.set(x.getName(), result.get(x.getName()));

				// to resolve template path
				context.set("path", new PathFuntion(application, protocol, host));

				template.render(context, out);
			}
		}
	}

	private static File getDistributionFolder() {
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		return pdk.getParentFile();
	}

	private static File getControllerFolder(String application) throws IOException {
		File controller = null;
		File dist = getDistributionFolder();
		String relative = AbstractControllerGenerator.ROOT + File.separator + application + "/controller";
		if (!dist.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
			// production mode
			controller = new File(dist.getCanonicalPath() + File.separator + relative);
		} else {
			// development mode
			controller = new File(relative);
		}
		return controller;
	}

	private static File getViewFile(String view) throws IOException {
		File viewfile = null;
		File dist = getDistributionFolder();
		if (!dist.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
			// production mode
			viewfile = new File(dist.getCanonicalPath() + File.separator + view);
		} else {
			// development mode
			viewfile = new File(view);
		}
		return viewfile;
	}

	private static String getCurrentPath() {
		Class<?> c = ControllerRuntime.class;
		ProtectionDomain d = c.getProtectionDomain();
		CodeSource s = d.getCodeSource();
		return s.getLocation().getPath();
	}

	private ControllerRuntime() {
	}

}

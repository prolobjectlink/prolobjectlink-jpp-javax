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
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.persistence.spi.PersistenceProvider;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.prolobjectlink.db.util.JarFileFilter;
import io.github.prolobjectlink.web.entry.ProviderEntry;
import io.github.prolobjectlink.web.function.AssetFunction;
import io.github.prolobjectlink.web.function.PathFunction;
import io.github.prolobjectlink.web.servlet.AbstractServlet;
import io.marioslab.basis.template.Template;
import io.marioslab.basis.template.TemplateContext;
import io.marioslab.basis.template.TemplateLoader;
import io.marioslab.basis.template.TemplateLoader.ClasspathTemplateLoader;

@WebServlet
public class PersistenceServlet extends AbstractServlet implements Servlet {

	private static final long serialVersionUID = 7313381101418470194L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// request information
		String protocol = req.getScheme();
		String host = req.getHeader("host");

		// Engine and context
		TemplateLoader loader = new ClasspathTemplateLoader();
		Template template = loader.load("/io/github/prolobjectlink/web/html/persistence.html");
		TemplateContext context = new TemplateContext();

		File lib = getLibDirectory();
		List<ProviderEntry> providers = new ArrayList<ProviderEntry>();
		ClassLoader l = Thread.currentThread().getContextClassLoader();
		File[] jars = lib.listFiles(new JarFileFilter());
		for (File file : jars) {
			JarFile jarFile = new JarFile(file);
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry jarEntry = entries.nextElement();
				if (isValidJarEntry(jarEntry.getName())) {
					String jarEntryName = jarEntry.getName();
					String className = jarEntryName.substring(0, jarEntryName.length() - 6);
					try {
						Class<?> runtimeClass = l.loadClass(className.replace('/', '.'));
						if (!runtimeClass.isInterface() && PersistenceProvider.class.isAssignableFrom(runtimeClass)) {
							ProviderEntry e = new ProviderEntry(runtimeClass.getName());
							providers.add(e);
						}
					} catch (ClassNotFoundException e) {
						// do nothing
					} catch (java.lang.NoClassDefFoundError e) {
						// do nothing
					} catch (java.lang.IllegalAccessError e) {
						// do nothing
					}
				}
			}
			jarFile.close();
		}

		// variables
		context.set("providers", providers);

		// functions
		context.set("path", new PathFunction("pas", protocol, host));
		context.set("asset", new AssetFunction(protocol, host));

		// render
		template.render(context, resp.getOutputStream());

		// response
		resp.setStatus(HttpServletResponse.SC_OK);
	}

}

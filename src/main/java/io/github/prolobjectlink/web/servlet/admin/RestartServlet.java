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
import java.sql.SQLException;
import java.util.List;

import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.prolobjectlink.db.DatabaseDriver;
import io.github.prolobjectlink.db.DatabaseDriverFactory;
import io.github.prolobjectlink.db.DatabaseServer;
import io.github.prolobjectlink.db.jpa.spi.JPAPersistenceUnitInfo;
import io.github.prolobjectlink.db.util.JavaReflect;
import io.github.prolobjectlink.web.application.ControllerGenerator;
import io.github.prolobjectlink.web.application.ModelGenerator;
import io.github.prolobjectlink.web.application.ServletUrlMapping;
import io.github.prolobjectlink.web.entry.ApplicationEntry;
import io.github.prolobjectlink.web.entry.DatabaseEntry;
import io.github.prolobjectlink.web.function.AssetFunction;
import io.github.prolobjectlink.web.function.PathFunction;
import io.github.prolobjectlink.web.platform.WebServer;
import io.github.prolobjectlink.web.servlet.AbstractServlet;
import io.marioslab.basis.template.Template;
import io.marioslab.basis.template.TemplateContext;
import io.marioslab.basis.template.TemplateLoader;
import io.marioslab.basis.template.TemplateLoader.ClasspathTemplateLoader;

@WebServlet
public class RestartServlet extends AbstractServlet implements Servlet {

	private static final long serialVersionUID = 7313381101418470194L;

	private final ControllerGenerator controllerGenerator;
	private final ModelGenerator modelGenerator;
	private final ServletContext servletContext;
	private final DatabaseServer dbServer;
	private final WebServer webserver;

	public RestartServlet(ModelGenerator modelGenerator, ControllerGenerator controllerGenerator,
			ServletContext context, DatabaseServer dbServer, WebServer webserver) {
		this.controllerGenerator = controllerGenerator;
		this.modelGenerator = modelGenerator;
		this.webserver = webserver;
		this.dbServer = dbServer;
		this.servletContext = context;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("Deploying ...");

		try {

			List<PersistenceUnitInfo> units = modelGenerator.getPersistenceUnits();
			for (PersistenceUnitInfo unit : units) {
				DatabaseDriver databaseDriver = DatabaseDriverFactory.createDriver(unit);
				if (!databaseDriver.getDatabasePing()) {
					databaseDriver.createDatabase();
					JPAPersistenceUnitInfo jpaUnit = (JPAPersistenceUnitInfo) unit;
					String name = jpaUnit.getPersistenceProviderClassName();
					Class<?> cls = JavaReflect.classForName(name);
					Object object = JavaReflect.newInstance(cls);
					PersistenceProvider provider = (PersistenceProvider) object;
					provider.generateSchema(unit, unit.getProperties());
				}
			}

			List<ServletUrlMapping> mappings = controllerGenerator.getMappings();
			for (ServletUrlMapping servletUrlMapping : mappings) {
				Servlet servlet = servletContext.createServlet(servletUrlMapping.getServlet().getClass());
				servletContext.addServlet(servletUrlMapping.getServlet().getClass().getName(), servlet)
						.addMapping(servletUrlMapping.getMappingUrl());
			}

			System.out.println("Deploy Done");

		} catch (SQLException e) {
			// do nothing
		}

		// request information
		String protocol = req.getScheme();
		String host = req.getHeader("host");

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
		context.set("serverversion", webserver.getVersion());
		context.set("servername", webserver.getName());

		// database server
		context.set("dbserverversion", dbServer.getVersion());
		context.set("dbservername", dbServer.getName());

		// runtime statistics
		Runtime runtime = Runtime.getRuntime();
		long maxMemory = runtime.maxMemory();
		long freeMemory = runtime.freeMemory();
		long totalMemory = runtime.totalMemory();
		long usedMemory = totalMemory - freeMemory;
		int processors = runtime.availableProcessors();
		long usedPercent = usedMemory * 100 / totalMemory;
		long freePercent = freeMemory * 100 / totalMemory;
		long maxUsedPercent = usedMemory * 100 / maxMemory;
		long maxFreePercent = freeMemory * 100 / maxMemory;
		context.set("maxMemory", maxMemory);
		context.set("freeMemory", freeMemory);
		context.set("totalMemory", totalMemory);
		context.set("usedMemory", usedMemory);
		context.set("processors", processors);
		context.set("usedPercent", usedPercent);
		context.set("freePercent", freePercent);
		context.set("maxUsedPercent", maxUsedPercent);
		context.set("maxFreePercent", maxFreePercent);

		// resources statistics
		long partitionTotalSize = 1;
		long partitionFreeSize = 0;
		long partitionUsableSize = 0;

		long databaseSize = 0;
		long applicationSize = 0;

		File ref = getBinDirectory();
		File[] roots = File.listRoots();
		for (File partition : roots) {
			String root = partition.toString();
			if (ref.getAbsolutePath().contains(root)) {
				partitionTotalSize = partition.getTotalSpace();
				partitionFreeSize = partition.getFreeSpace();
				partitionUsableSize = partition.getUsableSpace();
			}
		}
		List<ApplicationEntry> applications = listApplications();
		for (ApplicationEntry applicationEntry : applications) {
			applicationSize += applicationEntry.getSize();
		}
		List<DatabaseEntry> databases = listDatabases();
		for (DatabaseEntry databaseEntry : databases) {
			databaseSize += databaseEntry.getSize();
		}

		long usedSpacePercent = partitionUsableSize * 100 / partitionTotalSize;
		long freeSpacePercent = partitionFreeSize * 100 / partitionTotalSize;
		long appUsedPercent = applicationSize * 100 / partitionTotalSize;
		long dbUsedFreePercent = databaseSize * 100 / partitionTotalSize;

		context.set("usedSpacePercent", usedSpacePercent);
		context.set("freeSpacePercent", freeSpacePercent);
		context.set("appUsedPercent", appUsedPercent);
		context.set("dbUsedFreePercent", dbUsedFreePercent);

		// functions
		context.set("path", new PathFunction("pas", protocol, host));
		context.set("asset", new AssetFunction(protocol, host));

		// render
		template.render(context, resp.getOutputStream());

		// response
		resp.setStatus(HttpServletResponse.SC_OK);

		// response
		resp.setStatus(HttpServletResponse.SC_OK);
	}

}

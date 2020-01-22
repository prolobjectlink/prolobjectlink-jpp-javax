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
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;

public final class ApplicationGenerator extends AbstractWebApplication implements WebApplication {

	public void generate(String name) {

		PrintWriter a = null;
		PrintWriter b = null;
		PrintWriter c = null;
		PrintWriter d = null;
		PrintWriter i = null;

		try {

			String webdir = getWebDirectory().getCanonicalPath();
			File appdir = new File(webdir + File.separator + name);
			appdir.mkdir();

			// web folders
			File controllerFolder = new File(webdir + File.separator + name + "/controller");
			controllerFolder.mkdir();
			File modelFolder = new File(webdir + File.separator + name + "/model");
			modelFolder.mkdir();
			File viewFolder = new File(webdir + File.separator + name + "/view");
			viewFolder.mkdir();

			// web index controller file
			File index = new File(controllerFolder.getCanonicalPath() + File.separator + name + "_index_controller.pl");
			i = new PrintWriter(index);
			i.println(":-multifile(index/1).");
			i.println();
			i.println(":-consult('../../../misc/pl/http.pl').");
			i.println();
			i.println("index(_) :- ");
			i.print("	render('index.html').");

			// web file
			a = new PrintWriter(new File(webdir + File.separator + name + "/controller.pl"));
			a.print(name + "_controller('controller/" + name + "_index_controller.pl').");

			b = new PrintWriter(new File(webdir + File.separator + name + "/database.pl"));
			b.println("provider('org.hibernate.jpa.HibernatePersistenceProvider').");
			b.println("url('jdbc:hsqldb:file:/" + name + "').");
			b.println("driver('org.hsqldb.jdbc.JDBCDriver').");
			b.println("password('').");
			b.println("user('sa').");

			c = new PrintWriter(new File(webdir + File.separator + name + "/index.html"));
			c.write("Hello World");

			d = new PrintWriter(new File(webdir + File.separator + name + "/model.pl"));
			d.write("");

			String db = getDBDirectory().getCanonicalPath() + "/hsqldb";
			String url = "jdbc:hsqldb:file:" + db + "/" + name;
			Connection connection = DriverManager.getConnection(url, "sa", "");
			connection.close();

		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		} catch (SQLException e) {
			LoggerUtils.error(getClass(), LoggerConstants.RUNTIME_ERROR, e);
		} finally {
			if (a != null) {
				a.close();
			}
			if (b != null) {
				b.close();
			}
			if (c != null) {
				c.close();
			}
			if (d != null) {
				d.close();
			}
			if (i != null) {
				i.close();
			}
		}

	}

}

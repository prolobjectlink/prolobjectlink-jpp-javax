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
package io.github.prolobjectlink.db.server;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.sql.SQLException;

import org.h2.Driver;
import org.h2.tools.Server;

import io.github.prolobjectlink.db.DatabaseServer;
import io.github.prolobjectlink.db.DatabaseServerType;
import io.github.prolobjectlink.web.platform.AbstractWebPlatform;

public class H2Server extends AbstractWebPlatform implements DatabaseServer {

	private final Driver driver = new Driver();
	private final Server server = new Server();

	public H2Server() {

		File dbdir = getDBDirectory();
		File[] dbs = dbdir.listFiles();
		for (File file : dbs) {
			if (file.isDirectory() && file.getName().equals("h2db")) {
				File[] scripts = file.listFiles(new FileFilter() {

					@Override
					public boolean accept(File arg0) {
						return arg0.getName().endsWith(".script");
					}

				});
				for (int i = 0; i < scripts.length; i++) {
					File x = scripts[i];
					String name = x.getName().substring(0, x.getName().lastIndexOf(".script"));
					System.out.println(name);
//					hsqlProperties.setProperty("server.database." + i, "file:" + dbdir + "/hsqldb/" + name);
//					hsqlProperties.setProperty("server.dbname." + i, name);
				}
			}
		}

//			server.setProperties(hsqlProperties);

	}

	@Override
	public void startup() throws IOException, ClassNotFoundException, SQLException {
		Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
	}

	@Override
	public DatabaseServerType getType() {
		return DatabaseServerType.TCP;
	}

	@Override
	public boolean isRunning() {
		return server.isRunning(true);
	}

	@Override
	public String getVersion() {
		int major = driver.getMajorVersion();
		int minor = driver.getMinorVersion();
		return major + "." + minor;
	}

	@Override
	public String getName() {
		return "H2";
	}

	@Override
	public String getURL() {
		return server.getURL();
	}

	@Override
	public void shutdown() throws SQLException {
		Server.shutdownTcpServer("tcp://localhost:9092", "", true, true);
	}

}

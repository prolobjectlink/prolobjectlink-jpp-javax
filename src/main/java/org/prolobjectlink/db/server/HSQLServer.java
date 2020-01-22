/*-
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2012 - 2020 Prolobjectlink Project
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
package org.prolobjectlink.db.server;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.ServerAcl.AclFormatException;
import org.prolobjectlink.db.DatabaseServer;
import org.prolobjectlink.db.DatabaseServerType;
import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;
import org.prolobjectlink.web.platform.AbstractWebPlatform;

public class HSQLServer extends AbstractWebPlatform implements DatabaseServer {

	private final HsqlProperties hsqlProperties = new HsqlProperties();
	private final Server server = new Server();

	public HSQLServer() {
		File dbdir = getDBDirectory();
		File[] dbs = dbdir.listFiles();
		for (File file : dbs) {
			if (file.isDirectory() && file.getName().equals("hsqldb")) {
				File[] scripts = file.listFiles(new FileFilter() {

					@Override
					public boolean accept(File arg0) {
						return arg0.getName().endsWith(".script");
					}

				});
				for (int i = 0; i < scripts.length; i++) {
					File x = scripts[i];
					String name = x.getName().substring(0, x.getName().lastIndexOf(".script"));
					hsqlProperties.setProperty("server.database." + i, "file:" + dbdir + "/hsqldb/" + name);
					hsqlProperties.setProperty("server.dbname." + i, name);
				}
			}
		}

		try {
			server.setProperties(hsqlProperties);
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		} catch (AclFormatException e) {
			LoggerUtils.error(getClass(), LoggerConstants.RUNTIME_ERROR, e);
		}
		server.setTrace(false);

	}

	@Override
	public void startup() throws IOException, ClassNotFoundException {
		server.start();
	}

	@Override
	public DatabaseServerType getType() {
		return DatabaseServerType.TCP;
	}

	@Override
	public boolean isRunning() {
		return server.getState() == 1 && server.getState() == 4;
	}

	@Override
	public String getName() {
		return server.getProductName();
	}

	@Override
	public String getURL() {
		return server.getDefaultWebPage();
	}

	@Override
	public void shutdown() {
		server.stop();
	}

}

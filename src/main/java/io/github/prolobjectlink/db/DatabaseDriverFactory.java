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
package io.github.prolobjectlink.db;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.spi.PersistenceUnitInfo;

import io.github.prolobjectlink.db.jdbc.embedded.HSQLDBFileDriver;
import io.github.prolobjectlink.db.jdbc.remote.HSQLDBDriver;
import io.github.prolobjectlink.db.jdbc.remote.MySQLDriver;
import io.github.prolobjectlink.db.jdbc.remote.PostgreSQLDriver;
import io.github.prolobjectlink.web.application.WebApplication;

public class DatabaseDriverFactory {

	public static DatabaseDriver createDriver(PersistenceUnitInfo unit) throws SQLException {
		DatabaseDriver driver = null;
		Properties properties = unit.getProperties();
		String url = properties.getProperty(WebApplication.URL);
		String prefix = url.substring(0, url.lastIndexOf(':') + 1);
		if (prefix.matches(PostgreSQLDriver.prefix + ".*")) {
			driver = new PostgreSQLDriver(properties);
		} else if (prefix.matches(MySQLDriver.prefix + ".*")) {
			driver = new MySQLDriver(properties);
		} else if (prefix.matches(HSQLDBDriver.prefix + ".*")) {
			driver = new HSQLDBDriver(properties);
		} else if (prefix.matches(HSQLDBFileDriver.prefix + ".*")) {
			driver = new HSQLDBFileDriver(properties);
		} else {
			throw new SQLException("The specified prefix not have equivalent driver <" + prefix + ">");
		}
		return driver;
	}

	private DatabaseDriverFactory() {
	}

}

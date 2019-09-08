/*-
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
 * #L%
 */
package org.prolobjectlink.db;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.spi.PersistenceUnitInfo;

import org.prolobjectlink.db.jdbc.embedded.HSQLDBFileDriver;
import org.prolobjectlink.db.jdbc.remote.MySQLDriver;
import org.prolobjectlink.db.jdbc.remote.PostgreSQLDriver;
import org.prolobjectlink.web.application.WebApplication;

public class DatabaseDriverFactory {

	public static DatabaseDriver createDriver(PersistenceUnitInfo unit) throws SQLException {
		DatabaseDriver driver = null;
		Properties properties = unit.getProperties();
		String url = properties.getProperty(WebApplication.URL);
		String prefix = url.substring(0, url.lastIndexOf(':') + 1);
		if (prefix.equals(PostgreSQLDriver.prefix)) {
			driver = new PostgreSQLDriver(properties);
		} else if (prefix.equals(MySQLDriver.prefix)) {
			driver = new MySQLDriver(properties);
		} else if (prefix.equals(HSQLDBFileDriver.prefix)) {
			driver = new HSQLDBFileDriver(properties);
		} else {
			throw new SQLException("The specified prefix not have equivalent driver <" + prefix + ">");
		}
		return driver;
	}

	private DatabaseDriverFactory() {
	}

}

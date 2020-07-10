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
package io.github.prolobjectlink.db.jdbc.dialect;

public class DialectResolver {

	public static String resolve(Class<?> provider, Class<?> driver) {
		if (provider == org.eclipse.persistence.jpa.PersistenceProvider.class) {
			if (driver == org.h2.Driver.class) {
				return "org.eclipse.persistence.platform.database.H2Platform";
			} else if (driver == org.hsqldb.jdbc.JDBCDriver.class || driver == org.hsqldb.jdbcDriver.class) {
				return "HSQL";
			} else if (driver == com.microsoft.sqlserver.jdbc.SQLServerDriver.class) {

			} else if (driver == com.mysql.jdbc.Driver.class) {
				return "MySQL";
			} else if (driver == org.postgresql.Driver.class) {
				return "PostgreSQL";
			} else if (driver == org.apache.derby.jdbc.EmbeddedDriver.class
					|| driver == org.apache.derby.jdbc.ClientDriver.class) {
				return "Derby";
			}

		} else if (provider == org.hibernate.jpa.HibernatePersistenceProvider.class) {

			if (driver == org.h2.Driver.class) {
				return "org.hibernate.dialect.H2Dialect";
			} else if (driver == org.hsqldb.jdbc.JDBCDriver.class || driver == org.hsqldb.jdbcDriver.class) {
				return "org.hibernate.dialect.HSQLDialect";
			} else if (driver == com.microsoft.sqlserver.jdbc.SQLServerDriver.class) {

			} else if (driver == com.mysql.jdbc.Driver.class) {
				return "org.hibernate.dialect.MySQLInnoDBDialect";
			} else if (driver == org.postgresql.Driver.class) {
				return "org.hibernate.dialect.PostgreSQLDialect";
			} else if (driver == org.apache.derby.jdbc.EmbeddedDriver.class
					|| driver == org.apache.derby.jdbc.ClientDriver.class) {
				return "org.hibernate.dialect.DerbyDialect";
			}

		} else if (provider == org.apache.openjpa.persistence.PersistenceProviderImpl.class) {
			// do nothig
		}
		return "";
	}

	private DialectResolver() {
	}

}

/*
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.db.jpa;

import java.util.Map;
import java.util.Properties;

import org.prolobjectlink.db.DatabaseProperties;

/**
 * @deprecated Use {@link DatabaseProperties} instead
 * @author Jose Zalacain
 *
 */
@Deprecated
public final class JpaProperties extends Properties {

	private static final long serialVersionUID = -2013758241662724837L;

	public static final String DRIVER = "javax.persistence.jdbc.driver";
	public static final String URL = "javax.persistence.jdbc.url";
	public static final String USER = "javax.persistence.jdbc.user";
	public static final String PASSWORD = "javax.persistence.jdbc.password";

	public JpaProperties() {
	}

	/**
	 * Checks properties
	 * 
	 * @param properties
	 */
	public JpaProperties(final Map<String, Object> properties) {
		if (properties == null) {
			throw new IllegalStateException("Map properties for entity manager should not be null");
		} else if (!properties.containsKey(DRIVER)) {
			throw new IllegalStateException("DRIVER propertiy for entity manager should not be null or empty");
		} else if (!properties.containsKey(URL)) {
			throw new IllegalStateException("URL propertiy for entity manager should not be null or empty");
		} else if (!properties.containsKey(USER)) {
			throw new IllegalStateException("User propertiy for entity manager should not be null or empty");
		} else {
			putAll(properties);
		}
	}

	public String getDriver() {
		return getProperty(DRIVER);
	}

	public String getURL() {
		return getProperty(URL);
	}

	public String getUser() {
		return getProperty(USER);
	}

	public String getPassword() {
		return getProperty(PASSWORD);
	}

}

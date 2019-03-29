/*
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.db.jpa.spi;

import java.util.Map;
import java.util.Properties;

import org.prolobjectlink.db.DatabaseProperties;

public final class JPAPersistenceProperties extends Properties {

	private static final long serialVersionUID = -2013758241662724837L;

	public JPAPersistenceProperties() {
	}

	/**
	 * Checks properties
	 * 
	 * @param properties
	 */
	public JPAPersistenceProperties(final Map<String, Object> properties) {
		if (properties == null) {
			throw new IllegalStateException("Map properties for entity manager should not be null");
		} else if (!properties.containsKey(DatabaseProperties.DRIVER)) {
			throw new IllegalStateException("DRIVER propertiy for entity manager should not be null or empty");
		} else if (!properties.containsKey(DatabaseProperties.URL)) {
			throw new IllegalStateException("URL propertiy for entity manager should not be null or empty");
		} else if (!properties.containsKey(DatabaseProperties.USER)) {
			throw new IllegalStateException("User propertiy for entity manager should not be null or empty");
		}
		putAll(properties);
	}

	public String getDriver() {
		return getProperty(DatabaseProperties.DRIVER);
	}

	public String getURL() {
		return getProperty(DatabaseProperties.URL);
	}

	public String getUser() {
		return getProperty(DatabaseProperties.USER);
	}

	public String getPassword() {
		return getProperty(DatabaseProperties.PASSWORD);
	}

}

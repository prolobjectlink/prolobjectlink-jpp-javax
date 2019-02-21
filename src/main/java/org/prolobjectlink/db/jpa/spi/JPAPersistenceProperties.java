/*
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

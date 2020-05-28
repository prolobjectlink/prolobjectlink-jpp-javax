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
package io.github.prolobjectlink.web.etc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

import io.github.prolobjectlink.etc.AbstractConfiguration;
import io.github.prolobjectlink.etc.Configuration;
import io.github.prolobjectlink.logging.LoggerConstants;
import io.github.prolobjectlink.logging.LoggerUtils;

public class WebServerSettings extends AbstractConfiguration<Object, Object> implements Configuration<Object, Object> {

	public static final String CONFIG = "etc" + File.separator + "server-settings.xml";

	public WebServerSettings load() {
		try {
			File configuration = getConfiguration(CONFIG);
			properties.loadFromXML(new FileInputStream(configuration));
		} catch (FileNotFoundException e) {
			LoggerUtils.error(getClass(), LoggerConstants.FILE_NOT_FOUND, e);
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return this;
	}

	public WebServerSettings save() {
		try {
			File configuration = getConfiguration(CONFIG);
			properties.storeToXML(new FileOutputStream(configuration), null);
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return this;
	}

	@Override
	public Set<Entry<Object, Object>> entrySet() {
		return properties.entrySet();
	}

	@Override
	public Object put(Object arg0, Object arg1) {
		return properties.put(arg0, arg1);
	}

}

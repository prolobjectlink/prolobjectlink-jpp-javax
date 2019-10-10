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
package org.prolobjectlink.web.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.UnaryOperator;

import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;

public class AssetFuntion implements UnaryOperator<String> {

	private final File misc;

	public AssetFuntion(File misc) {
		this.misc = misc;
	}

	@Override
	public String apply(String arg0) {
		FileReader reader = null;
		BufferedReader buffer = null;
		StringBuilder scriptCode = new StringBuilder();
		String script = misc.getAbsolutePath() + "/" + arg0;
		try {
			script = misc.getCanonicalPath() + "/" + arg0;
			reader = new FileReader(script);
			buffer = new BufferedReader(reader);
			String line = buffer.readLine();
			while (line != null) {
				scriptCode.append(line);
				line = buffer.readLine();
			}
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), LoggerConstants.IO, e);
				}
			}
			if (buffer != null) {
				try {
					buffer.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), LoggerConstants.IO, e);
				}
			}
		}
		return scriptCode.toString();
	}

}

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
package io.github.prolobjectlink.web.function;

import static io.github.prolobjectlink.logging.LoggerConstants.IO;
import static io.github.prolobjectlink.prolog.PrologLogger.FILE_NOT_FOUND;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Function;

import io.github.prolobjectlink.logging.LoggerUtils;

public abstract class AbstractFunction<T, R> implements Function<T, R> {

	public final String getSourceCode(String fileName) {
		FileReader reader = null;
		BufferedReader buffer = null;
		StringBuilder b = new StringBuilder();
		try {
			File in = new File(fileName);
			reader = new FileReader(in);
			buffer = new BufferedReader(reader);
			String line = buffer.readLine();
			while (line != null) {
				b.append(line);
				line = buffer.readLine();
			}
		} catch (FileNotFoundException e) {
			LoggerUtils.error(getClass(), FILE_NOT_FOUND, e);
		} catch (IOException e) {
			LoggerUtils.error(getClass(), IO, e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO, e);
				}
			}
			if (buffer != null) {
				try {
					buffer.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO, e);
				}
			}
		}

		return b.toString();
	}

}

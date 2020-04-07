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
package io.github.prolobjectlink.web.compiler;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;

import io.github.prolobjectlink.Platform;

public interface Compiler extends Platform {

	byte[] readBytecode(Path javaClass) throws IOException;

	Class<?> createClass(Path javaClass, String name) throws ClassNotFoundException, MalformedURLException;

	Path compileSource(Path javaFile, String name);

	Path saveSource(String source, String name) throws IOException;

	String readCode(String sourcePath) throws IOException;

	String readCode(InputStream stream) throws IOException;

}

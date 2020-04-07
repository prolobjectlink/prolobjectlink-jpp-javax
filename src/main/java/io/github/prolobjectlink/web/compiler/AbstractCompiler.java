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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.objectweb.asm.ClassReader;

import io.github.prolobjectlink.AbstractPlatform;
import io.github.prolobjectlink.Platform;

public abstract class AbstractCompiler extends AbstractPlatform implements Platform {

	public final String readCode(InputStream stream) throws IOException {
		String separator = System.getProperty("line.separator");
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String source = reader.lines().collect(Collectors.joining(separator));
		reader.close();
		return source;
	}

	public final String readCode(String sourcePath) throws IOException {
		InputStream stream = new FileInputStream(sourcePath);
		return readCode(stream);
	}

	public final Path saveSource(String source, String name) throws IOException {
		String tmpProperty = System.getProperty("java.io.tmpdir");
		Path sourcePath = Paths.get(tmpProperty, name + ".java");
		Files.write(sourcePath, source.getBytes(Charset.defaultCharset()));
		return sourcePath;
	}

	public final Path compileSource(Path javaFile, String name) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		compiler.run(null, null, null, javaFile.toFile().getAbsolutePath());
		return javaFile.getParent().resolve(name + ".class");
	}

	public final byte[] readBytecode(Path javaClass) throws IOException {
		InputStream stream = new FileInputStream(javaClass.toFile().getAbsolutePath());
		ClassReader reader = new ClassReader(stream);
		return reader.b;
	}

}

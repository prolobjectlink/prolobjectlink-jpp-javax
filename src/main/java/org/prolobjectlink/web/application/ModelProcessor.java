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
package org.prolobjectlink.web.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import javax.persistence.spi.PersistenceUnitInfo;

import org.prolobjectlink.db.jpa.spi.JPAPersistenceUnitInfo;
import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;

public class ModelProcessor extends AbstractWebApplication {

	private final ModelGenerator modelGenerator;
	private final String temp = System.getProperty("java.io.tmpdir");

	public ModelProcessor(ModelGenerator modelGenerator) {
		this.modelGenerator = modelGenerator;
	}

	public void processModel() {

		List<PersistenceUnitInfo> units = modelGenerator.getPersistenceUnits();

		// write persistence.xml header
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append('\n');
		builder.append("<persistence version=\"1.0\"\r\n"
				+ "	xmlns=\"http://java.sun.com/xml/ns/persistence\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n"
				+ "	xsi:schemaLocation=\"http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_1_0.xsd\">");
		builder.append('\n');

		try {

			String prolobjectlinkJpxModelJar = "prolobjectlink-jpx-model.jar";
			FileOutputStream stream = new FileOutputStream(temp + prolobjectlinkJpxModelJar);
			JarOutputStream out = new JarOutputStream(stream, new Manifest());

			for (PersistenceUnitInfo unit : units) {
				JPAPersistenceUnitInfo jpaUnit = (JPAPersistenceUnitInfo) unit;
				jpaUnit.writePersistenceXml(builder);
				jpaUnit.writeByteCode(temp);
				jpaUnit.jar(out, temp);
			}

			// write persistence.xml footer
			builder.append('\n');
			builder.append("</persistence>");

			// write to persistence.xml file
			String persistenceXml = temp + "persistence.xml";
			FileWriter writer = new FileWriter(persistenceXml);
			writer.write("" + builder + "");
			writer.close();

			// copy persistence.xml into .jar
			byte[] buffer = new byte[1024];
			File file = new File(persistenceXml);
			JarEntry jarEntry = new JarEntry("META-INF/" + file.getName());
			jarEntry.setTime(file.lastModified());
			out.putNextEntry(jarEntry);
			FileInputStream in = new FileInputStream(file);
			while (true) {
				int nRead = in.read(buffer, 0, buffer.length);
				if (nRead <= 0)
					break;
				out.write(buffer, 0, nRead);
			}
			in.close();
			out.close();

			// copy from temp directory to lib folder
			String jarInLib = getLibDirectory() + File.separator + prolobjectlinkJpxModelJar;
			FileInputStream fips = new FileInputStream(temp + prolobjectlinkJpxModelJar);
			FileOutputStream fops = new FileOutputStream(jarInLib);
			copy(fips, fops);

		} catch (FileNotFoundException e) {
			LoggerUtils.error(getClass(), LoggerConstants.FILE_NOT_FOUND, e);
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
	}

}

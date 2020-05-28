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
package io.github.prolobjectlink.web.faces.report.html;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import io.github.prolobjectlink.Platform;
import io.github.prolobjectlink.web.faces.report.AbstractWebReport;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.query.JRJpaQueryExecuterFactory;

public class HyperTextMarkup extends AbstractWebReport implements Platform {

	protected HyperTextMarkup(String application, String destination) {
		super(application, destination);
	}

	public void write() throws IOException {

		FileOutputStream fileOutputStream = null;

		String appPath = getWebDirectory() + File.separator + getApplication();
		String realPath = appPath + File.separator + getDestination();
		File destinationDir = new File(realPath);

		if (!destinationDir.isDirectory()) {
			throw new IOException(getDestination() + " is not a directory");
		}

		EntityManager em = Persistence.createEntityManagerFactory("123456").createEntityManager();
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(JRJpaQueryExecuterFactory.PARAMETER_JPA_ENTITY_MANAGER, em);
		InputStream jrxmlReportStream = new FileInputStream(appPath + "/report/logs.jrxml");
		InputStream jasperReportStream = new FileInputStream(appPath + "/report/logs.jasper");

		try {
			fileOutputStream = new FileOutputStream(destinationDir);
			JasperCompileManager.compileReport(jrxmlReportStream);
			JasperRunManager.runReportToPdfStream(jasperReportStream, fileOutputStream, parameterMap);

		} catch (JRException ex) {
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			ex.printStackTrace(printWriter);
		} finally {
			if (fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
			}
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
	}

}

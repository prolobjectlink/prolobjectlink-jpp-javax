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
package io.github.prolobjectlink.web.application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.query.JRJpaQueryExecuterFactory;

public class ReportRuntime {

	public static void processRequest(HttpServletRequest request, HttpServletResponse response,
			String destination) throws IOException {

		String realPath = request.getServletContext().getRealPath(destination);
		File destinationDir = new File(realPath);

		if (!destinationDir.isDirectory()) {
			throw new IOException(destination + " is not a directory");
		}

		response.setContentType("application/pdf");
		EntityManager em = Persistence.createEntityManagerFactory("123456").createEntityManager();
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(JRJpaQueryExecuterFactory.PARAMETER_JPA_ENTITY_MANAGER, em);
		ServletOutputStream servletOutputStream = response.getOutputStream();
		InputStream jrxmlReportStream = request.getServletContext().getResourceAsStream("/report/logs.jrxml");
		InputStream jasperReportStream = request.getServletContext().getResourceAsStream("/report/logs.jasper");

		try {

			JasperCompileManager.compileReport(jrxmlReportStream);
			JasperRunManager.runReportToPdfStream(jasperReportStream, servletOutputStream, parameterMap);

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (JRException ex) {
			// display stack trace in the browser
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			ex.printStackTrace(printWriter);
			response.setContentType("text/plain");
			response.getOutputStream().print(stringWriter.toString());
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
	}

}

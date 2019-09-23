/*-
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.web.platform;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public interface WebServer {

	public final String TOMCAT = "Apache Tomcat";
	public final String GLASSFISH = "Glassfish";
	public final String JETTY = "Jetty";

	public String getVersion();

	public String getName();

	public int getPort();

	public void start();

	public void restart();

	public void stop();

}

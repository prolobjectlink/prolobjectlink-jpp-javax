/*-
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
 * #L%
 */
package org.prolobjectlink.db;

import java.util.Map;

import org.prolobjectlink.web.platform.WebServerControl;

/**
 * Represent the database system console.
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public interface DatabaseConsole {

	/**
	 * Return an integer that represent the default port for http web server control
	 * embedded in the platform.
	 * 
	 * @return default http port for web server listening
	 * @since 1.0
	 */
	public int getDefaultHttpPort();

	/**
	 * Return an instance of the http web server control embedded in the platform.
	 * The server control is showed in host system try after run invocation and from
	 * there the server can be started.
	 * 
	 * @param port port to web server listening
	 * @return an instance of the web server control
	 * @since 1.0
	 */
	public WebServerControl getWebServerControl(int port);

	/**
	 * Create a arguments map from a given string arguments array. Used for convert
	 * command line interface program arguments array to argument map.
	 * 
	 * @param args string arguments array
	 * @return arguments map
	 * @since 1.0
	 */
	public Map<String, String> getArguments(String[] args);

	/**
	 * <p>
	 * Command line interface program run method for this platform. Take the program
	 * arguments from main entry point and execute the job. Used like:
	 * </p>
	 * 
	 * <tt>
	 * public class Main{
	 * public static void main(String[] args) {
	 *	new Main().run(args);
	 *}
	 *
	 *}
	 * </tt>
	 * 
	 * @param args command line interface program arguments array
	 * @since 1.0
	 */
	public void run(String[] args);

	/**
	 * Used to print console usage.
	 * 
	 * @since 1.0
	 */
	public void printUsage();

}

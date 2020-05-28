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
package io.github.prolobjectlink.web.console;

import io.github.prolobjectlink.web.application.ModelGenerator;
import io.github.prolobjectlink.web.platform.WebServerControl;

/**
 * Represent the web server system console.
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public interface WebServerConsole {

	/**
	 * Create an instance of the model generator for the current platform.
	 * 
	 * @return an instance of the model generator
	 * @since 1.0
	 */
	public ModelGenerator getModelGeneratorInstance();

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

}

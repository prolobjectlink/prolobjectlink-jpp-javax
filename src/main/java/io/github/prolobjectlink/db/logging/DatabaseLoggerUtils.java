/*
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
package io.github.prolobjectlink.db.logging;

import java.util.logging.Level;

import io.github.prolobjectlink.logging.LoggerInterface;
import io.github.prolobjectlink.logging.ProlobjectlinkLogger;

public class DatabaseLoggerUtils {

	private static final LoggerInterface LOGGER = ProlobjectlinkLogger.getInstance();

	public static final void trace(Object sender, Object message) {
		LOGGER.log(sender, Level.FINEST, message);
	}

	public static final void trace(Object sender, Object message, Throwable throwable) {
		LOGGER.log(sender, Level.FINEST, message, throwable);
	}

	public static final void debug(Object sender, Object message) {
		LOGGER.log(sender, Level.FINE, message);
	}

	public static final void debug(Object sender, Object message, Throwable throwable) {
		LOGGER.log(sender, Level.FINE, message, throwable);
	}

	public static final void info(Object sender, Object message) {
		LOGGER.log(sender, Level.INFO, message);
	}

	public static final void info(Object sender, Object message, Throwable throwable) {
		LOGGER.log(sender, Level.INFO, message, throwable);
	}

	public static final void warn(Object sender, Object message) {
		LOGGER.log(sender, Level.WARNING, message);
	}

	public static final void warn(Object sender, Object message, Throwable throwable) {
		LOGGER.log(sender, Level.WARNING, message, throwable);
	}

	public static final void error(Object sender, Object message) {
		LOGGER.log(sender, Level.SEVERE, message);
	}

	public static final void error(Object sender, Object message, Throwable throwable) {
		LOGGER.log(sender, Level.SEVERE, message, throwable);
	}

	private DatabaseLoggerUtils() {
	}

}

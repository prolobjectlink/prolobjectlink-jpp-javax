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
package io.github.prolobjectlink.logging;

import java.util.logging.Level;
import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.LogManager;

import io.github.prolobjectlink.AbstractPlatform;
import io.github.prolobjectlink.logging.LoggerInterface;

public final class ProlobjectlinkLogger extends AbstractPlatform implements LoggerInterface {

	private static ProlobjectlinkLogger platform_logger;
	private File log4jXml;

	public static LoggerInterface getInstance() {
		if (platform_logger == null) {
			platform_logger = new ProlobjectlinkLogger();
		}
		return platform_logger;
	}

	public static LoggerInterface getInstance(File log4jXml) {
		if (platform_logger == null) {
			platform_logger = new ProlobjectlinkLogger(log4jXml);
		}
		return platform_logger;
	}

	private void log(Object sender, Priority priority, Object message) {
//		LoggerContext context = (LoggerContext) LogManager.getContext(false);
//		context.setConfigLocation(log4jXml.toURI());
		Logger logger = Logger.getLogger(sender.toString());
		logger.log(priority, message, null);
	}

	private void log(Object sender, Priority priority, Object message, Throwable throwable) {
//		LoggerContext context = (LoggerContext) LogManager.getContext(false);
//		context.setConfigLocation(log4jXml.toURI());
		Logger logger = Logger.getLogger(sender.toString());
		logger.log(priority, message, throwable);
	}

	@Override
	public void log(Object sender, Level level, Object message) {
		log(sender, getPriority(level), message);
	}

	@Override
	public void log(Object sender, Level level, Object message, Throwable throwable) {
		log(sender, getPriority(level), message, throwable);
	}

	@Override
	public void trace(Object sender, Object message) {
		log(sender, Level.FINEST, message);
	}

	@Override
	public void trace(Object sender, Object message, Throwable throwable) {
		log(sender, Level.FINEST, message, throwable);
	}

	@Override
	public void debug(Object sender, Object message) {
		log(sender, Level.FINE, message);
	}

	@Override
	public void debug(Object sender, Object message, Throwable throwable) {
		log(sender, Level.FINE, message, throwable);
	}

	@Override
	public void info(Object sender, Object message) {
		log(sender, Level.INFO, message);
	}

	@Override
	public void info(Object sender, Object message, Throwable throwable) {
		log(sender, Level.INFO, message, throwable);
	}

	@Override
	public void warn(Object sender, Object message) {
		log(sender, Level.WARNING, message);
	}

	@Override
	public void warn(Object sender, Object message, Throwable throwable) {
		log(sender, Level.WARNING, message, throwable);
	}

	@Override
	public void error(Object sender, Object message) {
		log(sender, Level.SEVERE, message);
	}

	@Override
	public void error(Object sender, Object message, Throwable throwable) {
		log(sender, Level.SEVERE, message, throwable);
	}

	private Priority getPriority(Level level) {
		if (level == Level.FINE || level == Level.FINEST || level == Level.FINER) {
			return Priority.DEBUG;
		} else if (level == Level.SEVERE) {
			return Priority.ERROR;
		} else if (level == Level.WARNING) {
			return Priority.WARN;
		} else if (level == Level.INFO) {
			return Priority.INFO;
		}
		return null;
	}

	private ProlobjectlinkLogger(File log4jXml) {
		this.log4jXml = log4jXml;
	}

	private ProlobjectlinkLogger() {
	}

}

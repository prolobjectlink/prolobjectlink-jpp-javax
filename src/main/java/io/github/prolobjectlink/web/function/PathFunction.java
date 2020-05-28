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
package io.github.prolobjectlink.web.function;

import java.util.function.UnaryOperator;

public class PathFunction extends AbstractFunction<String, String> implements UnaryOperator<String> {

	private final String application;
	private final String protocol;
	private final String host;

	public PathFunction(String application, String protocol, String host) {
		this.application = application;
		this.protocol = protocol;
		this.host = host;
	}

	@Override
	public String apply(String arg0) {
		return protocol + "://" + host + "/" + application + "/" + arg0;
	}

}

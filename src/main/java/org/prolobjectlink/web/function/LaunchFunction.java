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
package org.prolobjectlink.web.function;

import java.util.function.UnaryOperator;

public class LaunchFunction implements UnaryOperator<String> {

	private final String protocol;
	private final String host;

	public LaunchFunction(String protocol, String host) {
		this.protocol = protocol;
		this.host = host;
	}

	@Override
	public String apply(String application) {
		String arg0 = application.split(":")[0];
		String arg1 = application.split(":")[1];
		return protocol + "://" + host + "/" + arg0 + "/" + arg1;
	}

}

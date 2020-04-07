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

public class ImportFunction implements UnaryOperator<String> {

	private final String root;
	private final String application;

	public ImportFunction(String root, String application) {
		this.application = application;
		this.root = root;
	}

	@Override
	public String apply(String arg0) {
		System.out.println(root + "/" + application + "/" + arg0);
		return root + "/" + application + "/" + arg0;
	}

}

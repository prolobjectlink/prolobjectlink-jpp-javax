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
package io.github.prolobjectlink.web.util;

import java.util.Collection;
import java.util.Set;

public class LinkedHashSet<E> extends java.util.LinkedHashSet<E> implements Set<E> {

	private static final long serialVersionUID = 425590951671479535L;

	public LinkedHashSet() {
	}

	public LinkedHashSet(int arg0) {
		super(arg0);
	}

	public LinkedHashSet(Collection<? extends E> arg0) {
		super(arg0);
	}

	public LinkedHashSet(int arg0, float arg1) {
		super(arg0, arg1);
	}

}

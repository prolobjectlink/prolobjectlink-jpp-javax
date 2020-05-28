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
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;

public class TreeSet<E> extends java.util.TreeSet<E> implements Set<E> {

	private static final long serialVersionUID = 8765713053891211992L;

	public TreeSet() {
	}

	public TreeSet(Comparator<? super E> arg0) {
		super(arg0);
	}

	public TreeSet(Collection<? extends E> arg0) {
		super(arg0);
	}

	public TreeSet(SortedSet<E> arg0) {
		super(arg0);
	}

}

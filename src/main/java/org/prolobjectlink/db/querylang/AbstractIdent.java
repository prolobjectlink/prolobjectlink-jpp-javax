/*
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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

package org.prolobjectlink.db.querylang;

/**
 *
 * @author Jose Zalacain
 */
/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractIdent extends AbstractExp implements Ident {

	String name;
	int lineno;

	int index; // number of ident in environment
	boolean isInput; // is it an input variable?

	public AbstractIdent(String n) {
		name = n;
	}

	public AbstractIdent(String s, int line) {
		name = s;
		lineno = line;
	}

	@Override
	public final String toString() {
		return name;
	}

}

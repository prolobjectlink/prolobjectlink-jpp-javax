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
public class FunctionEntry extends SymbolEntry {
	int arity;
	Dekl dekl; // location of definition

	public FunctionEntry(int type, int line, int column, int left, int right, Object value, Dekl d, int a) {
		super(type, line, column, left, right, value);
		arity = a;
		dekl = d;
	}

	public FunctionEntry(int type, int line, int column, Object value, Dekl d, int a) {
		super(type, line, column, value);
		arity = a;
		dekl = d;
	}

	public FunctionEntry(int type, int line, int column, Dekl d, int a) {
		super(type, line, column);
		arity = a;
		dekl = d;
	}

	@Override
	public String toString() {
		return "function    " + value + ", arity " + arity;
	}

	public int arity() {
		return arity;
	}

	public Dekl getDekl() {
		return dekl;
	}
}

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

package org.prolobjectlink.db.querylang;

/**
 *
 * @author Jose Zalacain
 * @since 1.0
 */
public class SymbolEntry {

	public int sym;
	public int left;
	public int right;
	private int line;
	private int column;
	public Object value;

	public SymbolEntry(int type, int line, int column) {
		this(type, line, column, -1, -1, null);
	}

	public SymbolEntry(int type, int line, int column, Object value) {
		this(type, line, column, -1, -1, value);
	}

	public SymbolEntry(int type, int line, int column, int left, int right, Object value) {
		this.sym = type;
		this.left = left;
		this.right = right;
		this.value = value;
		this.line = line;
		this.column = column;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public String toString() {
		return "line " + line + ", column " + column + ", sym: " + sym
				+ (value == null ? "" : (", value: '" + value + "'"));
	}

}

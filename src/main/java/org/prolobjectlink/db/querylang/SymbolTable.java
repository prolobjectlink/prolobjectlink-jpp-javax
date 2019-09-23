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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Jose Zalacain
 */
public class SymbolTable {

	// predecessor table
	SymbolTable pred;
	Set<String> builtins;
	Map<String, SymbolEntry> table;

	public SymbolTable(Set<String> builtins) {
		this(builtins, null);
	}

	public SymbolTable(Set<String> builtins, SymbolTable p) {
		this.table = new HashMap<String, SymbolEntry>();
		this.builtins = builtins;
		this.pred = p;
	}

	public boolean enter(String s, SymbolEntry e) {
		Object value = lookup(s);
		table.put(s, e);
		return (value == null);
	}

	public SymbolEntry lookup(String s) {
		SymbolEntry value = table.get(s);
		if (value == null && pred != null)
			value = pred.lookup(s);
		return value;
	}

	@Override
	public String toString() { // for output with print
		String res = "symbol table\n=============\n";
		Iterator<String> e = table.keySet().iterator();
		String key;

		while (e.hasNext()) {
			key = e.next();
			res += key + "   \t" + table.get(key) + "\n";
		}

		if (pred != null)
			res += "++ predecessor!\n";
		return (res);
	}

	public int size() {
		return (table.size());
	}

	public void clear() {
		Map<?, ?> ptr = table;
		while (ptr != null) {
			table.clear();
			if (pred != null) {
				ptr = pred.table;
			}
		}
	}

	public boolean support(String functor, int arity) {
		return builtins.contains(functor + "/" + arity);
	}
}

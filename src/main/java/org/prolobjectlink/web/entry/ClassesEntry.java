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
package org.prolobjectlink.web.entry;

import java.util.ArrayList;
import java.util.List;

public class ClassesEntry {

	private final String unit;
	private final String name;
	private final List<FieldEntry> fields;

	public ClassesEntry(String unit, String name) {
		fields = new ArrayList<FieldEntry>();
		this.unit = unit;
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public String getName() {
		return name;
	}

	public List<FieldEntry> getFields() {
		return fields;
	}

	public void addField(FieldEntry field) {
		fields.add(field);
	}

}
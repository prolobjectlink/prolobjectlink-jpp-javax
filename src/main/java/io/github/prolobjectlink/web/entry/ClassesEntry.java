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
package io.github.prolobjectlink.web.entry;

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

	public void removeField(FieldEntry field) {
		fields.remove(field);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassesEntry other = (ClassesEntry) obj;
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields)) {
			return false;
		}
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ClassesEntry [unit=" + unit + ", name=" + name + ", fields=" + fields + "]";
	}

}

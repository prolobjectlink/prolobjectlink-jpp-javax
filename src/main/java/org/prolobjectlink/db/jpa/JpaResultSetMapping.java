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
package org.prolobjectlink.db.jpa;

import java.util.Arrays;

public final class JpaResultSetMapping {

	private final String name;
	private final JpaEntityResult[] entities;

	private JpaConstructorResult[] classes;
	private JpaColumnResult[] columns;

	public JpaResultSetMapping(String name, JpaEntityResult[] entities) {
		this(name, entities, new JpaConstructorResult[0], new JpaColumnResult[0]);
	}

	public JpaResultSetMapping(String name, JpaEntityResult[] entities, JpaConstructorResult[] classes,
			JpaColumnResult[] columns) {
		this.name = name;
		this.entities = entities;
		this.classes = classes;
		this.columns = columns;
	}

	public JpaConstructorResult[] getClasses() {
		return classes;
	}

	public void setClasses(JpaConstructorResult[] classes) {
		this.classes = classes;
	}

	public JpaColumnResult[] getColumns() {
		return columns;
	}

	public void setColumns(JpaColumnResult[] columns) {
		this.columns = columns;
	}

	public String getName() {
		return name;
	}

	public JpaEntityResult[] getEntities() {
		return entities;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(classes);
		result = prime * result + Arrays.hashCode(columns);
		result = prime * result + Arrays.hashCode(entities);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		JpaResultSetMapping other = (JpaResultSetMapping) obj;
		if (!Arrays.equals(classes, other.classes))
			return false;
		if (!Arrays.equals(columns, other.columns))
			return false;
		if (!Arrays.equals(entities, other.entities))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JPAResultSetMapping [name=" + name + ", entities=" + Arrays.toString(entities) + ", classes="
				+ Arrays.toString(classes) + ", columns=" + Arrays.toString(columns) + "]";
	}

}

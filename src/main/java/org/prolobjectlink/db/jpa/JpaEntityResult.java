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

public final class JpaEntityResult {

	private final Class<?> entityClass;
	private JpaFieldResult[] fieldResults;
	private String discriminatorColumn;

	public JpaEntityResult(Class<?> entityClass) {
		this(entityClass, new JpaFieldResult[0], "");
	}

	public JpaEntityResult(Class<?> entityClass, JpaFieldResult[] fieldResults, String discriminatorColumn) {
		this.entityClass = entityClass;
		this.fieldResults = fieldResults;
		this.discriminatorColumn = discriminatorColumn;
	}

	public JpaFieldResult[] getFieldResults() {
		return fieldResults;
	}

	public void setFieldResults(JpaFieldResult[] fieldResults) {
		this.fieldResults = fieldResults;
	}

	public String getDiscriminatorColumn() {
		return discriminatorColumn;
	}

	public void setDiscriminatorColumn(String discriminatorColumn) {
		this.discriminatorColumn = discriminatorColumn;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discriminatorColumn == null) ? 0 : discriminatorColumn.hashCode());
		result = prime * result + ((entityClass == null) ? 0 : entityClass.hashCode());
		result = prime * result + Arrays.hashCode(fieldResults);
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
		JpaEntityResult other = (JpaEntityResult) obj;
		if (discriminatorColumn == null) {
			if (other.discriminatorColumn != null)
				return false;
		} else if (!discriminatorColumn.equals(other.discriminatorColumn))
			return false;
		if (entityClass == null) {
			if (other.entityClass != null)
				return false;
		} else if (!entityClass.equals(other.entityClass))
			return false;
		if (!Arrays.equals(fieldResults, other.fieldResults))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JPAEntityResult [entityClass=" + entityClass + ", fieldResults=" + Arrays.toString(fieldResults)
				+ ", discriminatorColumn=" + discriminatorColumn + "]";
	}

}

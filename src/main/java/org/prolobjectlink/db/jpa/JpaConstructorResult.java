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
package org.prolobjectlink.db.jpa;

import java.util.Arrays;

public final class JpaConstructorResult {

	private final Class<?> targetClass;
	private JpaColumnResult[] columns;

	public JpaConstructorResult(Class<?> targetClass) {
		this(targetClass, new JpaColumnResult[0]);
	}

	public JpaConstructorResult(Class<?> targetClass, JpaColumnResult[] columns) {
		this.targetClass = targetClass;
		this.columns = columns;
	}

	public JpaColumnResult[] getColumns() {
		return columns;
	}

	public void setColumns(JpaColumnResult[] columns) {
		this.columns = columns;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(columns);
		result = prime * result + ((targetClass == null) ? 0 : targetClass.hashCode());
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
		JpaConstructorResult other = (JpaConstructorResult) obj;
		if (!Arrays.equals(columns, other.columns))
			return false;
		if (targetClass == null) {
			if (other.targetClass != null)
				return false;
		} else if (!targetClass.equals(other.targetClass))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JPAConstructorResult [targetClass=" + targetClass + ", columns=" + Arrays.toString(columns) + "]";
	}

}

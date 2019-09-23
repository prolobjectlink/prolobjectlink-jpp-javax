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

import javax.persistence.Parameter;
import javax.persistence.ParameterMode;

public final class JpaParameter<T> implements Parameter<T> {

	private Object value;
	private final String name;
	private final Integer position;
	private final Class<T> parameterType;
	private final ParameterMode parameterMode;

	public JpaParameter(String name, Integer position, Class<T> parameterType) {
		this(name, position, parameterType, ParameterMode.INOUT, null);
	}

	public JpaParameter(String name, Integer position, Class<T> parameterType, Object value) {
		this(name, position, parameterType, ParameterMode.INOUT, value);
	}

	public JpaParameter(String name, Integer position, Class<T> parameterType, ParameterMode parameterMode) {
		this(name, position, parameterType, parameterMode, null);
	}

	public JpaParameter(String name, Integer position, Class<T> parameterType, ParameterMode parameterMode,
			Object value) {
		this.name = name;
		this.value = value;
		this.position = position;
		this.parameterType = parameterType;
		this.parameterMode = parameterMode;
	}

	public String getName() {
		return name;
	}

	public Integer getPosition() {
		return position;
	}

	public Class<T> getParameterType() {
		return parameterType;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public ParameterMode getParameterMode() {
		return parameterMode;
	}

	@Override
	public String toString() {
		return "LogicParameter [value=" + value + ", name=" + name + ", position=" + position + ", parameterType="
				+ parameterType + ", parameterMode=" + parameterMode + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parameterMode == null) ? 0 : parameterMode.hashCode());
		result = prime * result + ((parameterType == null) ? 0 : parameterType.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		JpaParameter<?> other = (JpaParameter<?>) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parameterMode != other.parameterMode)
			return false;
		if (parameterType == null) {
			if (other.parameterType != null)
				return false;
		} else if (!parameterType.equals(other.parameterType))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}

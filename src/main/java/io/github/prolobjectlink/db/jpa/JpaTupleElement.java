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
package io.github.prolobjectlink.db.jpa;

import javax.persistence.TupleElement;

import io.github.prolobjectlink.db.jpa.criteria.JpaAbstractWrapper;

public class JpaTupleElement<X> extends JpaAbstractWrapper implements TupleElement<X> {

	protected String alias;
	protected final X value;
	protected final Class<? extends X> javaType;

	public JpaTupleElement(String alias, Class<? extends X> javaType) {
		this(alias, javaType, null);
	}

	public JpaTupleElement(String alias, Class<? extends X> javaType, X value) {
		this.javaType = javaType;
		this.value = value;
		this.alias = alias;
	}

	public Class<? extends X> getJavaType() {
		return javaType;
	}

	public String getAlias() {
		return alias;
	}

	public X getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value != null ? alias + "=" + value : alias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		result = prime * result + ((javaType == null) ? 0 : javaType.hashCode());
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
		JpaTupleElement<?> other = (JpaTupleElement<?>) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias)) {
			return false;
		}
		if (javaType == null) {
			if (other.javaType != null)
				return false;
		} else if (!javaType.equals(other.javaType)) {
			return false;
		}
		return true;
	}

}

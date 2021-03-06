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
package io.github.prolobjectlink.db.jpa.metamodel;

import javax.persistence.metamodel.Type;

import io.github.prolobjectlink.db.Schema;

/** @author Jose Zalacain @since 1.0 */
public abstract class JpaType<X> /* extends AbstractWrapper */ implements Type<X> {

	protected final Schema schema;
	protected final Class<X> javaType;

	public JpaType(Schema schema, Class<X> javaType) {
		this.schema = schema;
		this.javaType = javaType;
	}

	public Class<X> getJavaType() {
		return javaType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((javaType == null) ? 0 : javaType.hashCode());
		result = prime * result + ((schema == null) ? 0 : schema.hashCode());
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
		JpaType<?> other = (JpaType<?>) obj;
		if (javaType == null) {
			if (other.javaType != null)
				return false;
		} else if (!javaType.equals(other.javaType)) {
			return false;
		}
		if (schema == null) {
			if (other.schema != null)
				return false;
		} else if (!schema.equals(other.schema)) {
			return false;
		}
		return true;
	}

	@Override
	public abstract String toString();

}

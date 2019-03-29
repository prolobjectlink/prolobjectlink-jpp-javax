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
package org.prolobjectlink.db.jpa.criteria;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaSubstring<X> extends JpaFunctionExpression<X> implements Expression<X> {

	protected final Expression<?> length;

	public JpaSubstring(String alias, Class<? extends X> javaType, Expression<?> x, Expression<?> from,
			Metamodel metamodel) {
		this(alias, javaType, x, from, null, metamodel);
	}

	public JpaSubstring(String alias, Class<? extends X> javaType, Expression<?> x, Expression<?> from,
			Expression<?> len, Metamodel metamodel) {
		super(alias, javaType, x, "SUBSTRING", from, metamodel);
		this.length = len;
	}

	@Override
	public String toString() {
		return "JpaSubstring [length=" + length + ", operator=" + operator + ", right=" + right + ", metamodel="
				+ metamodel + ", distinct=" + distinct + ", expression=" + expression + ", roots=" + roots + ", alias="
				+ alias + ", value=" + value + ", javaType=" + javaType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((length == null) ? 0 : length.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JpaSubstring<?> other = (JpaSubstring<?>) obj;
		if (length == null) {
			if (other.length != null)
				return false;
		} else if (!length.equals(other.length)) {
			return false;
		}
		return true;
	}

}

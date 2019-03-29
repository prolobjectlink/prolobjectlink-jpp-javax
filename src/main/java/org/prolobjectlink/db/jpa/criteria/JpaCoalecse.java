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

import javax.persistence.criteria.CriteriaBuilder.Coalesce;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

import org.prolobjectlink.db.util.JavaReflect;

public class JpaCoalecse<X> extends JpaExpression<X> implements Coalesce<X> {

	protected final Expression<?> right;

	public JpaCoalecse(String alias, Class<? extends X> javaType, Expression<?> left, Expression<?> right,
			Metamodel metamodel) {
		super(alias, javaType, left, metamodel);
		this.right = right;
	}

	public JpaCoalecse(String alias, Class<? extends X> javaType, Metamodel metamodel) {
		this(alias, javaType, null, null, metamodel);
	}

	public Coalesce<X> value(X value) {
		expression = new JpaObject<X>(value, JavaReflect.classOf(value));
		return this;
	}

	public Coalesce<X> value(Expression<? extends X> value) {
		expression = value;
		return this;
	}

	@Override
	public String toString() {
		return "JpaCoalecse [right=" + right + ", metamodel=" + metamodel + ", distinct=" + distinct + ", expression="
				+ expression + ", roots=" + roots + ", alias=" + alias + ", value=" + value + ", javaType=" + javaType
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((right == null) ? 0 : right.hashCode());
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
		JpaCoalecse<?> other = (JpaCoalecse<?>) obj;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right)) {
			return false;
		}
		return true;
	}

}

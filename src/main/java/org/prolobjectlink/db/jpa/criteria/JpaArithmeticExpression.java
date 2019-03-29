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

/** @author Jose Zalacain @since 1.0 */
public abstract class JpaArithmeticExpression<X> extends JpaExpression<X> implements Expression<X> {

	protected final String operator;
	protected final Expression<?> right;

	public JpaArithmeticExpression(String alias, Class<? extends X> javaType, Expression<?> left, String operator,
			Expression<?> right, Metamodel metamodel) {
		super(alias, javaType, left, metamodel);
		this.operator = operator;
		this.right = right;
	}

	@Override
	public final String toString() {
		return "" + expression + operator + right + "";
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JpaArithmeticExpression<?> other = (JpaArithmeticExpression<?>) obj;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator)) {
			return false;
		}
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right)) {
			return false;
		}
		return true;
	}

}

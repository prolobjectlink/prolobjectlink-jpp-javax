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
package io.github.prolobjectlink.db.jpa.criteria.predicate;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

import io.github.prolobjectlink.db.jpa.criteria.JpaPredicate;

public class JpaInPredicate<X> extends JpaPredicate implements In<X> {

	private final Expression<X> leftExpression;

	public JpaInPredicate(String alias, Class<? extends Boolean> javaType, Expression<?> expression,
			Metamodel metamodel, BooleanOperator operator, List<Expression<?>> expressions,
			Expression<X> leftExpression) {
		super(alias, javaType, expression, metamodel, operator, expressions);
		this.leftExpression = leftExpression;
	}

	public Expression<X> getExpression() {
		return leftExpression;
	}

	public In<X> value(X value) {
		expressions.add(value);
		return this;
	}

	public In<X> value(Expression<? extends X> value) {
		expressions.add(value);
		return this;
	}

	@Override
	public String toString() {
		return "JpaInPredicate [leftExpression=" + leftExpression + ", expressions=" + expressions + ", operator="
				+ operator + ", metamodel=" + metamodel + ", distinct=" + distinct + ", expression=" + expression
				+ ", roots=" + roots + ", alias=" + alias + ", value=" + value + ", javaType=" + javaType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((leftExpression == null) ? 0 : leftExpression.hashCode());
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
		JpaInPredicate<?> other = (JpaInPredicate<?>) obj;
		if (leftExpression == null) {
			if (other.leftExpression != null)
				return false;
		} else if (!leftExpression.equals(other.leftExpression)) {
			return false;
		}
		return true;
	}

}

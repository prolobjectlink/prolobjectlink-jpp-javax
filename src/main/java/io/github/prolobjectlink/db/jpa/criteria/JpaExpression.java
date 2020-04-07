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
package io.github.prolobjectlink.db.jpa.criteria;

import java.util.Collection;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.Metamodel;

import io.github.prolobjectlink.db.jpa.criteria.predicate.JpaNotNullPredicate;
import io.github.prolobjectlink.db.jpa.criteria.predicate.JpaNullPredicate;

/** @author Jose Zalacain @since 1.0 */
public abstract class JpaExpression<X> extends JpaSelection<X> implements Expression<X> {

	protected final Metamodel metamodel;

	public JpaExpression(String alias, Class<? extends X> javaType, Expression<?> expression, Metamodel metamodel) {
		super(alias, javaType, expression);
		this.metamodel = metamodel;
	}

	public Predicate isNull() {
		return new JpaNullPredicate(null, Boolean.class, expression, metamodel, null, null);
	}

	public Predicate isNotNull() {
		return new JpaNotNullPredicate(null, Boolean.class, expression, metamodel, null, null);
	}

	public Predicate in(Object... values) {
		return new JpaIn(null, Boolean.class, null, metamodel, null, newList(values));
	}

	public Predicate in(Expression<?>... values) {
		return new JpaIn(null, Boolean.class, null, metamodel, null, newList(values));
	}

	public Predicate in(Collection<?> values) {
		return new JpaIn(null, Boolean.class, null, metamodel, null, newList(values));
	}

	public Predicate in(Expression<Collection<?>> values) {
		return new JpaIn(null, Boolean.class, values, metamodel, null, newList());
	}

	public <Y> Expression<Y> as(Class<Y> type) {
		return new JpaAs<Y>(null, type, null, metamodel);
	}

	@Override
	public abstract String toString();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((metamodel == null) ? 0 : metamodel.hashCode());
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
		JpaExpression<?> other = (JpaExpression<?>) obj;
		if (metamodel == null) {
			if (other.metamodel != null)
				return false;
		} else if (!metamodel.equals(other.metamodel)) {
			return false;
		}
		return true;
	}

}

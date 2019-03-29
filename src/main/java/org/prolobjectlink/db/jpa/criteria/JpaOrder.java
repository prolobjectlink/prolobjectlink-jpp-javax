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
import javax.persistence.criteria.Order;

public class JpaOrder implements Order {

	protected final boolean isAscending;
	protected final Expression<?> expression;

	public JpaOrder(Expression<?> expression) {
		this(expression, true);
	}

	public JpaOrder(Expression<?> expression, boolean isAscending) {
		this.expression = expression;
		this.isAscending = isAscending;
	}

	public Order reverse() {
		return new JpaOrder(expression, !isAscending);
	}

	public boolean isAscending() {
		return isAscending;
	}

	public Expression<?> getExpression() {
		return expression;
	}

	@Override
	public String toString() {
		String order = isAscending ? "ASC" : "DESC";
		return "ORDER BY " + expression + " " + order;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expression == null) ? 0 : expression.hashCode());
		result = prime * result + (isAscending ? 1231 : 1237);
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
		JpaOrder other = (JpaOrder) obj;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression)) {
			return false;
		}
		return isAscending == other.isAscending;
	}

}

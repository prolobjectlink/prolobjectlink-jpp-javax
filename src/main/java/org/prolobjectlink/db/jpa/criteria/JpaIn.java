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

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.Metamodel;

public final class JpaIn<X> extends JpaPredicate implements In<X> {

	public JpaIn(String alias, Class<? extends Boolean> javaType, Expression<?> expression, Metamodel metamodel,
			BooleanOperator operator, List<Expression<?>> expressions) {
		super(alias, javaType, expression, metamodel, operator, expressions);
	}

	public BooleanOperator getOperator() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isNegated() {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Expression<Boolean>> getExpressions() {
		// TODO Auto-generated method stub
		return null;
	}

	public Predicate not() {
		// TODO Auto-generated method stub
		return null;
	}

	public Expression<X> getExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	public In<X> value(X value) {
		// TODO Auto-generated method stub
		return null;
	}

	public In<X> value(Expression<? extends X> value) {
		// TODO Auto-generated method stub
		return null;
	}

}

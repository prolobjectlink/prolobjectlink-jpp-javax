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

import javax.persistence.criteria.CriteriaBuilder.SimpleCase;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaSimpleCase<C, R> extends JpaExpression<R> implements SimpleCase<C, R> {

	public JpaSimpleCase(String alias, Class<? extends R> javaType, Expression<?> expression, Metamodel metamodel) {
		super(alias, javaType, expression, metamodel);
	}

	public Expression<C> getExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	public SimpleCase<C, R> when(C condition, R result) {
		// TODO Auto-generated method stub
		return null;
	}

	public SimpleCase<C, R> when(C condition, Expression<? extends R> result) {
		// TODO Auto-generated method stub
		return null;
	}

	public Expression<R> otherwise(R result) {
		// TODO Auto-generated method stub
		return null;
	}

	public Expression<R> otherwise(Expression<? extends R> result) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "JpaSimpleCase [metamodel=" + metamodel + ", distinct=" + distinct + ", expression=" + expression
				+ ", roots=" + roots + ", alias=" + alias + ", value=" + value + ", javaType=" + javaType + "]";
	}

}

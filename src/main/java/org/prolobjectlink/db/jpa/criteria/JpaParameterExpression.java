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
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.metamodel.Metamodel;

public class JpaParameterExpression<X> extends JpaExpression<X> implements ParameterExpression<X> {

	protected final Integer position;

	public JpaParameterExpression(String alias, Class<? extends X> javaType, Expression<?> expression,
			Metamodel metamodel, Integer position) {
		super(alias, javaType, expression, metamodel);
		this.position = position;
	}

	public String getName() {
		return alias;
	}

	public Integer getPosition() {
		return position;
	}

	public Class<X> getParameterType() {
		return (Class<X>) javaType;
	}

	@Override
	public String toString() {
		return "JpaParameterExpression [position=" + position + ", metamodel=" + metamodel + ", distinct=" + distinct
				+ ", expression=" + expression + ", roots=" + roots + ", alias=" + alias + ", value=" + value
				+ ", javaType=" + javaType + "]";
	}

}

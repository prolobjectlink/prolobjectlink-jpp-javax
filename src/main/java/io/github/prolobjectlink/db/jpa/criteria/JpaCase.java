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

import javax.persistence.criteria.CriteriaBuilder.Case;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaCase<R> extends JpaExpression<R> implements Case<R> {

	public JpaCase(String alias, Class<? extends R> javaType, Metamodel metamodel) {
		super(alias, javaType, null, metamodel);
	}

	public Case<R> when(Expression<Boolean> condition, R result) {
		// TODO Auto-generated method stub
		return null;
	}

	public Case<R> when(Expression<Boolean> condition, Expression<? extends R> result) {
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
		return "JpaCase [metamodel=" + metamodel + ", distinct=" + distinct + ", expression=" + expression + ", roots="
				+ roots + ", alias=" + alias + ", value=" + value + ", javaType=" + javaType + "]";
	}

}

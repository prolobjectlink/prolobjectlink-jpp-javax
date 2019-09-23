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
package org.prolobjectlink.db.jpa.criteria.predicate;

import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

import org.prolobjectlink.db.jpa.criteria.JpaPredicate;

public class JpaIsNotMember extends JpaPredicate {

	public JpaIsNotMember(String alias, Class<? extends Boolean> javaType, Expression<?> expression,
			Metamodel metamodel, BooleanOperator operator, List<Expression<?>> expressions) {
		super(alias, javaType, expression, metamodel, operator, expressions);
	}

	@Override
	public String toString() {
		return "JpaIsNotMember [expressions=" + expressions + ", operator=" + operator + ", metamodel=" + metamodel
				+ ", distinct=" + distinct + ", expression=" + expression + ", roots=" + roots + ", alias=" + alias
				+ ", value=" + value + ", javaType=" + javaType + "]";
	}

}

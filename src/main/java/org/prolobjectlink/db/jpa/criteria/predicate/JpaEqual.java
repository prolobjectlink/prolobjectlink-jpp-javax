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
package org.prolobjectlink.db.jpa.criteria.predicate;

import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.metamodel.Metamodel;

public class JpaEqual extends JpaComparablePredicate {

	public JpaEqual(String alias, Class<? extends Boolean> javaType, Expression<? extends Comparable<?>> expression,
			Metamodel metamodel, List<Expression<?>> expressions) {
		super(alias, javaType, expression, metamodel, expressions);
	}

	@Override
	public String toString() {
		Object left = expressions.get(0);
		Object right = expressions.get(1);
		if (left instanceof From) {
			left = ((From<?, ?>) left).getAlias();
		}
		if (right instanceof From) {
			right = ((From<?, ?>) right).getAlias();
		}
		if (left instanceof Path) {
			left = ((Path<?>) left).getParentPath();
		}
		if (right instanceof Path) {
			right = ((Path<?>) right).getParentPath();
		}
		return "" + left + " = " + right + "";
	}

}

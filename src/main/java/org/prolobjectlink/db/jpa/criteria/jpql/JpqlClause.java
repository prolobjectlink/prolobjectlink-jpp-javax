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
package org.prolobjectlink.db.jpa.criteria.jpql;

import java.util.List;

import javax.persistence.criteria.Expression;

import org.prolobjectlink.Wrapper;
import org.prolobjectlink.db.jpa.criteria.JpaAbstractWrapper;

/** @author Jose Zalacain @since 1.0 */
public abstract class JpqlClause extends JpaAbstractWrapper implements Wrapper {

	protected List<Expression<?>> expressions = newList();

	public JpqlClause(Expression<?> expression) {
		expressions.add(expression);
	}

	public JpqlClause(Expression<?>... expressions) {
		for (Expression<?> expression : expressions) {
			this.expressions.add(expression);
		}
	}

	public JpqlClause(List<Expression<?>> expressions) {
		this.expressions = expressions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expressions == null) ? 0 : expressions.hashCode());
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
		JpqlClause other = (JpqlClause) obj;
		if (expressions == null) {
			if (other.expressions != null)
				return false;
		} else if (!expressions.equals(other.expressions)) {
			return false;
		}
		return true;
	}

}

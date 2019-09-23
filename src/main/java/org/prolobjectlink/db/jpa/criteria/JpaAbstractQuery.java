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
package org.prolobjectlink.db.jpa.criteria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;

/** @author Jose Zalacain @since 1.0 */
public abstract class JpaAbstractQuery<T> extends JpaAbstractCriteria<T> implements AbstractQuery<T> {

	protected boolean distinct;
	protected final Set<Root<?>> roots;
	protected Expression<Boolean> havingClause;
	protected List<Expression<?>> groupBy = newList();

	@Deprecated
	public JpaAbstractQuery(Expression<Boolean> restriction, Metamodel metamodel, Class<T> resultType) {
		this(restriction, metamodel, false, resultType, new HashSet<Root<?>>(), new ArrayList<Expression<?>>());
	}

	public JpaAbstractQuery(Expression<Boolean> restriction, Metamodel metamodel, boolean distinct, Class<T> resultType,
			Set<Root<?>> roots, List<Expression<?>> groupBy) {
		super(restriction, metamodel, resultType);
		this.distinct = distinct;
		this.groupBy = groupBy;
		this.roots = roots;
	}

	public final Set<Root<?>> getRoots() {
		return roots;
	}

	public final List<Expression<?>> getGroupList() {
		return groupBy;
	}

	public final Predicate getGroupRestriction() {
		return unwrap(havingClause, Predicate.class);
	}

	public final boolean isDistinct() {
		return distinct;
	}

	public final Class<T> getResultType() {
		return resultType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (distinct ? 1231 : 1237);
		result = prime * result + ((groupBy == null) ? 0 : groupBy.hashCode());
		result = prime * result + ((havingClause == null) ? 0 : havingClause.hashCode());
		result = prime * result + ((roots == null) ? 0 : roots.hashCode());
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
		JpaAbstractQuery<?> other = (JpaAbstractQuery<?>) obj;
		if (distinct != other.distinct)
			return false;
		if (groupBy == null) {
			if (other.groupBy != null)
				return false;
		} else if (!groupBy.equals(other.groupBy)) {
			return false;
		}
		if (havingClause == null) {
			if (other.havingClause != null)
				return false;
		} else if (!havingClause.equals(other.havingClause)) {
			return false;
		}
		if (roots == null) {
			if (other.roots != null)
				return false;
		} else if (!roots.equals(other.roots)) {
			return false;
		}
		return true;
	}

}

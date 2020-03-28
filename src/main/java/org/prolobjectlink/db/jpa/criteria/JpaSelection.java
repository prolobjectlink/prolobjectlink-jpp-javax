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

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.prolobjectlink.db.jpa.JpaTupleElement;

/**
 * 
 * @author Jose Zalacain
 *
 * @param <X>
 * @since 1.0
 */
public class JpaSelection<X> extends JpaTupleElement<X> implements Selection<X> {

	protected boolean distinct;
	protected Expression<?> expression;
	protected /* final */ Set<Root<?>> roots;

	/**
	 * @deprecated Use JpaSelection(String, Class, boolean, Set) instead to set
	 *             DISTINCT boolean flag.
	 * @param alias
	 * @param javaType
	 * @param expression
	 */
	@Deprecated
	public JpaSelection(String alias, Class<? extends X> javaType, Expression<?> expression) {
		super(alias, javaType);
		this.expression = expression;
	}

	/**
	 * @deprecated Use JpaSelection(String, Class, boolean, Set) instead to set
	 *             DISTINCT boolean flag.
	 * @param distinct
	 * @param alias
	 * @param javaType
	 * @param expression
	 */
	@Deprecated
	public JpaSelection(boolean distinct, String alias, Class<? extends X> javaType, Expression<?> expression) {
		super(alias, javaType);
		this.expression = expression;
		this.distinct = distinct;
	}

	public JpaSelection(boolean distinct, String alias, Class<? extends X> javaType, Set<Root<?>> roots) {
		super(alias, javaType);
		this.distinct = distinct;
		this.roots = roots;
	}

	public boolean isCompoundSelection() {
		return false;
	}

	public List<Selection<?>> getCompoundSelectionItems() {
		return Collections.emptyList();
	}

	public Selection<X> alias(String name) {
		this.alias = name;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		if (distinct) {
			b.append("SELECT DISTINCT " + alias + " ");
		} else {
			b.append("SELECT " + alias + " ");
		}
		return "" + b + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((expression == null) ? 0 : expression.hashCode());
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
		JpaSelection<?> other = (JpaSelection<?>) obj;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression)) {
			return false;
		}
		return true;
	}

}

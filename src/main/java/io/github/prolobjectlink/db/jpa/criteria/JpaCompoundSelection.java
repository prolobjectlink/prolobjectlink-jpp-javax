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

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Selection;

import io.github.prolobjectlink.prolog.ArrayIterator;

public class JpaCompoundSelection<X> extends JpaSelection<X> implements CompoundSelection<X> {

	protected Selection<?>[] selections;

	public JpaCompoundSelection(String alias, Class<? extends X> javaType, Expression<X> expression,
			Selection<?>[] subSelections) {
		super(alias, javaType, expression);
		this.selections = subSelections;
	}

	public JpaCompoundSelection(String alias, Class<? extends X> javaType, Expression<X> expression,
			List<Selection<?>> selectionList) {
		super(alias, javaType, expression);
		this.selections = selectionList.toArray(new JpaSelection[0]);
	}

	@Override
	public boolean isCompoundSelection() {
		return true;
	}

	@Override
	public List<Selection<?>> getCompoundSelectionItems() {
		return Arrays.asList(selections);
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		if (distinct) {
			b.append("SELECT DISTINCT ");
		} else {
			b.append("SELECT ");
		}
		if (selections != null && selections.length > 0) {
			Iterator<Selection<?>> i = new ArrayIterator<Selection<?>>(selections);
			while (i.hasNext()) {
				b.append(i.next().getAlias());
				if (i.hasNext()) {
					b.append(',');
				}
				b.append(' ');
			}
		}
		if (expression != null) {
			b.append(expression);
		}
		if (value != null) {
			b.append(value);
		}
		return "" + b + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(selections);
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
		JpaCompoundSelection<?> other = (JpaCompoundSelection<?>) obj;
		return Arrays.equals(selections, other.selections);
	}

}

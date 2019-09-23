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

import javax.persistence.criteria.Expression;

/** @author Jose Zalacain @since 1.0 */
public abstract class JpaNumber<N extends Number> extends JpaExpression<N> implements Expression<N> {

	protected final N number;

	public JpaNumber(N value, Class<? extends Number> javaType) {
		super("", (Class<? extends N>) javaType, null, null);
		this.number = value;
	}

	@Override
	public String toString() {
		return "" + number + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		JpaNumber<?> other = (JpaNumber<?>) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number)) {
			return false;
		}
		return true;
	}

}

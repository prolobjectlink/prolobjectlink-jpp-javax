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

import java.util.Arrays;
import java.util.Iterator;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

import org.prolobjectlink.prolog.ArrayIterator;

public class JpaFunction<X> extends JpaExpression<X> implements Expression<X> {

	private final Expression<?>[] arguments;

	public JpaFunction(String alias, Class<? extends X> javaType, Expression<?>[] arguments, Metamodel metamodel) {
		this(alias, javaType, null, arguments, metamodel);
	}

	public JpaFunction(String alias, Class<? extends X> javaType, Expression<?> expression, Expression<?>[] arguments,
			Metamodel metamodel) {
		super(alias, javaType, expression, metamodel);
		this.arguments = arguments;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder(alias);
		Iterator<?> i = new ArrayIterator<Object>(arguments);
		if (i.hasNext()) {
			b.append('(');
			while (i.hasNext()) {
				b.append(i.next());
				if (i.hasNext()) {
					b.append(',');
					b.append(' ');
				}
			}
			b.append(')');
		}
		return "" + b + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(arguments);
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
		JpaFunction<X> other = (JpaFunction<X>) obj;
		return Arrays.equals(arguments, other.arguments);
	}

}

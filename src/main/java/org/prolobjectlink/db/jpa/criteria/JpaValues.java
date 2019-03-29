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

import java.util.Collection;
import java.util.Map;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaValues<V> extends JpaExpression<Collection<V>> implements Expression<Collection<V>> {

	private final Map<?, V> map;

	public JpaValues(String alias, Class<? extends Collection<V>> javaType, Expression<?> expression,
			Metamodel metamodel, Map<?, V> map) {
		super(alias, javaType, expression, metamodel);
		this.map = map;
	}

	@Override
	public String toString() {
		return "JpaValues [map=" + map + ", metamodel=" + metamodel + ", distinct=" + distinct + ", expression="
				+ expression + ", roots=" + roots + ", alias=" + alias + ", value=" + value + ", javaType=" + javaType
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((map == null) ? 0 : map.hashCode());
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
		JpaValues<V> other = (JpaValues<V>) obj;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map)) {
			return false;
		}
		return true;
	}

}

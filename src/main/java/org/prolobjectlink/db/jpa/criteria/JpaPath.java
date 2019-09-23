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

import java.util.Collection;
import java.util.Map;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

public class JpaPath<X> extends JpaExpression<X> implements Path<X> {

	protected final Path<?> pathParent;
	protected final Bindable<X> model;

	public JpaPath(String alias, Class<? extends X> javaType, Expression<X> expression, Metamodel metamodel,
			Path<?> pathParent, Bindable<X> model) {
		super(alias, javaType, expression, metamodel);
		this.pathParent = pathParent;
		this.model = model;
	}

	public Bindable<X> getModel() {
		return model;
	}

	public Path<?> getParentPath() {
		return pathParent;
	}

	public <Y> Path<Y> get(SingularAttribute<? super X, Y> attribute) {
		// TODO Auto-generated method stub
		return null;
	}

	public <E, C extends Collection<E>> Expression<C> get(PluralAttribute<X, C, E> collection) {
		// TODO Auto-generated method stub
		return null;
	}

	public <K, V, M extends Map<K, V>> Expression<M> get(MapAttribute<X, K, V> map) {
		// TODO Auto-generated method stub
		return null;
	}

	public Expression<Class<? extends X>> type() {
		// TODO Auto-generated method stub
		return null;
	}

	public <Y> Path<Y> get(String attributeName) {
		return new JpaPath(attributeName, javaType, expression, metamodel, pathParent, model);
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		if (pathParent != null) {
			b.append(pathParent);
		}
		if (expression != null) {
			b.append(expression);
		}
		if (alias != null) {
			b.append('.');
			b.append(alias);
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
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((pathParent == null) ? 0 : pathParent.hashCode());
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
		JpaPath<?> other = (JpaPath<?>) obj;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (pathParent == null) {
			if (other.pathParent != null)
				return false;
		} else if (!pathParent.equals(other.pathParent))
			return false;
		return true;
	}

}

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

import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

public final class JpaCriteriaDelete<T> extends JpaAbstractCriteria<T> implements CriteriaDelete<T> {

	private Root<T> root;
	private final List<Predicate> predicates = new LinkedList<Predicate>();

	public JpaCriteriaDelete(Class<T> targetEntity, Predicate restriction, Metamodel metamodel) {
		super(restriction, metamodel, targetEntity);
	}

	public Root<T> from(Class<T> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	public Root<T> from(EntityType<T> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public Root<T> getRoot() {
		return root;
	}

	public JpaCriteriaDelete<T> where(Expression<Boolean> restriction) {
		this.restriction = restriction;
		return this;
	}

	public JpaCriteriaDelete<T> where(Predicate... restrictions) {
		for (Predicate predicate : restrictions) {
			predicates.add(predicate);
		}
		return this;
	}

	public <U> Subquery<U> subquery(Class<U> type) {
		// TODO Auto-generated method stub
		return null;
	}

}

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.Expression;

import org.prolobjectlink.AbstractWrapper;

/** @author Jose Zalacain @since 1.0 */
public abstract class JpaAbstractWrapper extends AbstractWrapper implements JpaTreeNode {

	private static final String MESSAGE2 = "Unsupported featured";

	public String getJpqlString() {
		return toString();
	}

	public String getQueryString() {
		throw new UnsupportedOperationException(MESSAGE2);
	}

	protected List<Expression<?>> newList(Object... values) {
		ArrayList<Expression<?>> list = new ArrayList<Expression<?>>(values.length);
		for (Object object : values) {
			list.add(new JpaObject<Object>(object, Object.class));
		}
		return list;
	}

	protected List<Expression<?>> newList(Collection<?> values) {
		ArrayList<Expression<?>> list = new ArrayList<Expression<?>>(values.size());
		for (Object object : values) {
			list.add(new JpaObject<Object>(object, Object.class));
		}
		return list;
	}

	protected List<Expression<?>> newList(Expression<?>... expressions) {
		ArrayList<Expression<?>> list = new ArrayList<Expression<?>>(expressions.length);
		for (Expression<?> exp : expressions) {
			list.add(exp);
		}
		return list;
	}

//	@Override
//	public abstract int hashCode();
//
//	@Override
//	public abstract boolean equals(Object obj);

}

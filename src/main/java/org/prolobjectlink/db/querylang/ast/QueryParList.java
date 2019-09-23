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
package org.prolobjectlink.db.querylang.ast;

import java.util.Iterator;
import java.util.List;

import org.prolobjectlink.db.jpa.criteria.JpaTreeNode;
import org.prolobjectlink.db.querylang.AbstractParList;
import org.prolobjectlink.db.querylang.ParList;

public class QueryParList extends AbstractParList implements ParList {

	public QueryParList(List<JpaTreeNode> parameters) {
		super(parameters);
	}

	public Iterator<JpaTreeNode> iterator() {
		return parameters.iterator();
	}

	public int arity() {
		return parameters.size();
	}

}

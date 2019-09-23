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
package org.prolobjectlink.db.querylang;

import java.util.Iterator;

import org.prolobjectlink.db.jpa.criteria.JpaAbstractWrapper;
import org.prolobjectlink.prolog.AbstractIterator;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractExpList extends JpaAbstractWrapper implements ExpList {

	Exp exp; // expression of this list node
	ExpList explist; // next list element (optional null)

	protected AbstractExpList(Exp e) {
		explist = null;
		exp = e;
	}

	protected AbstractExpList(ExpList p, Exp e) {
		explist = p;
		exp = e;
	}

	@Override
	public final String toString() {
		if (explist != null)
			return explist + "," + exp;
		else
			return "" + exp + "";
	}

	public final Iterator<ExpList> iterator() {
		return new ExpListIter();
	}

	private class ExpListIter extends AbstractIterator<ExpList> implements Iterator<ExpList> {

		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		public ExpList next() {
			// TODO Auto-generated method stub
			return null;
		}

	}

}

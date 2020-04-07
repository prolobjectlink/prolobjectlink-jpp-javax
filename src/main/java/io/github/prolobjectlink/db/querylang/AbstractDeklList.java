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

package io.github.prolobjectlink.db.querylang;

import java.util.Iterator;

import io.github.prolobjectlink.db.jpa.criteria.JpaAbstractWrapper;
import io.github.prolobjectlink.prolog.AbstractIterator;

/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractDeklList extends JpaAbstractWrapper implements DeklList {

	Dekl dekl; // declaration
	DeklList dekllist; // rest list (optional null)

	protected AbstractDeklList(Dekl e) {
		this(null, e);
	}

	protected AbstractDeklList(DeklList p, Dekl e) {
		dekllist = p;
		dekl = e;
	}

	@Override
	public final String toString() {
		if (dekllist != null)
			return dekllist + ",\n" + dekl;
		else
			return "" + dekl + "";
	}

	public Iterator<Dekl> iterator() {
		return new DeklListIter();
	}

	private class DeklListIter extends AbstractIterator<Dekl> implements Iterator<Dekl> {

		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		public Dekl next() {
			// TODO Auto-generated method stub
			return null;
		}

	}

}

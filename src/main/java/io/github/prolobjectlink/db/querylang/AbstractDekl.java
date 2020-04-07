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

import io.github.prolobjectlink.db.jpa.criteria.JpaAbstractWrapper;

/**
 *
 * @author Jose Zalacain
 */
/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractDekl extends JpaAbstractWrapper implements Dekl {

	Ident ident; // identifier
	ParList parlist; // liste of parameter
	Exp exp; // function body

	protected AbstractDekl(Ident i, ParList p, Exp e) {
		parlist = p;
		ident = i;
		exp = e;
	}

	@Override
	public String toString() {
		return ident + "(" + parlist + ") = \n  " + exp;
	}

	public final int arity() {
		return (parlist.arity());
	}

}

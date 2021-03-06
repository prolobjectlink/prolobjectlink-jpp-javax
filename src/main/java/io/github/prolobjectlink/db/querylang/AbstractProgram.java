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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.prolobjectlink.db.querylang;

import io.github.prolobjectlink.db.jpa.criteria.JpaAbstractWrapper;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractProgram extends JpaAbstractWrapper implements Program {

	ParList parlist; // input variables
	DeklList dekllist; // function declarations
	ExpList explist; // result expressions
	ExpList arguments; // input values

	SymbolTable inputs; // table of input variables
	SymbolTable functions; // table of functions

	public AbstractProgram(ParList p, DeklList d, ExpList e, ExpList a) {
		parlist = p;
		dekllist = d;
		explist = e;
		arguments = a;
	}

	@Override
	public final String toString() {

		return "Program:\n=============\ninput " + parlist + "\nfunctions\n" + dekllist + "\noutput " + explist
				+ "\narguments " + arguments + "\nend";

	}

}

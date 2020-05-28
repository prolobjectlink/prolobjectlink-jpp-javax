/*-
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2020 Prolobjectlink Project
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
package io.github.prolobjectlink.web.prolog;

import io.github.prolobjectlink.db.prolog.PrologProgrammer;
import io.github.prolobjectlink.prolog.PrologEngine;

public interface PrologWebEngine extends PrologEngine {

	/**
	 * Check that two object (x and y) unify. This operation convert x and y object
	 * to prolog terms and call terms unification. This operation is defined like
	 * object unification.
	 * 
	 * 
	 * @param x the term to unify.
	 * @param y the term to unify.
	 * @return true if the specified term unify whit the current term, false
	 *         otherwise.
	 * @since 1.0
	 */
	public boolean unify(Object x, Object y);

	public PrologProgrammer getProgrammer();

}

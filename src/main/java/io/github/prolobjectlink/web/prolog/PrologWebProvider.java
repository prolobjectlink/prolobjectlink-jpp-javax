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

import io.github.prolobjectlink.prolog.PrologProvider;

public interface PrologWebProvider extends PrologProvider {

	/**
	 * Create a new prolog engine instance ready to be operate. The created engine
	 * is clause empty and only have the defaults supported built-ins.
	 * 
	 * @return new prolog engine instance
	 * @since 1.0
	 */
	public PrologWebEngine newEngine();

	/**
	 * Create a new prolog engine instance ready to be operate. The created engine
	 * consult the given file loading the clauses present in this file and the
	 * defaults supported built-ins. Is equivalent to
	 * {@code newEngine().consult(file);}
	 * 
	 * @param file file path to be consulted
	 * @return new prolog engine instance
	 * @since 1.0
	 */
	public PrologWebEngine newEngine(String file);

}

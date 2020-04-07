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
package io.github.prolobjectlink.db.querylang.ast;

import io.github.prolobjectlink.db.querylang.AbstractProgram;
import io.github.prolobjectlink.db.querylang.DeklList;
import io.github.prolobjectlink.db.querylang.ExpList;
import io.github.prolobjectlink.db.querylang.ParList;
import io.github.prolobjectlink.db.querylang.Program;

public class QueryProgram extends AbstractProgram implements Program {

	public QueryProgram(ParList p, DeklList d, ExpList e, ExpList a) {
		super(p, d, e, a);
	}

}

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

package io.github.prolobjectlink.db.querylang.ast;

import io.github.prolobjectlink.db.querylang.AbstractExpInfix;
import io.github.prolobjectlink.db.querylang.Exp;
import io.github.prolobjectlink.db.querylang.ExpInfix;

public class QueryExpInfix extends AbstractExpInfix implements ExpInfix {

	public QueryExpInfix(Exp e1, char k, Exp e2) {
		super(e1, k, e2);
	}

}

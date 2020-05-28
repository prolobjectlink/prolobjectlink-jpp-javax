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
package io.github.prolobjectlink.web.template.base;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Set;

import io.github.prolobjectlink.web.template.Template;
import io.github.prolobjectlink.web.template.TemplateEntry;

public abstract class AbstractTemplate extends AbstractMap<String, Object> implements Template {

	@Override
	public Set<Entry<String, Object>> entrySet() {
		Set<Entry<String, Object>> set = new HashSet<Entry<String, Object>>();
		for (String variable : getVariables()) {
			Object value = getVariableValue(variable);
			Entry<String, Object> e = new TemplateEntry(variable, value);
			set.add(e);
		}
		return set;
	}

}

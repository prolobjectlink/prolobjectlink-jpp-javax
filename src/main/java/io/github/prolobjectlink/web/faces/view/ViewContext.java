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
package io.github.prolobjectlink.web.faces.view;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.github.prolobjectlink.web.template.TemplateEntry;

public class ViewContext extends AbstractMap<String, Object> implements Map<String, Object> {

	private final io.marioslab.basis.template.TemplateContext context;

	public ViewContext() {
		context = new io.marioslab.basis.template.TemplateContext();
	}

	public Object get(String arg0) {
		return context.get(arg0);
	}

	public Set<String> getVariables() {
		return context.getVariables();
	}

	public void pop() {
		context.pop();
	}

	public void push() {
		context.push();
	}

	public void set(String arg0, Object arg1) {
		context.set(arg0, arg1);
	}

	public void setOnCurrentScope(String name, Object value) {
		context.setOnCurrentScope(name, value);
	}

	@Override
	public Object put(String arg0, Object arg1) {
		return context.set(arg0, arg1);
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		Set<Entry<String, Object>> set = new HashSet<Entry<String, Object>>();
		for (String variable : getVariables()) {
			TemplateEntry entry = new TemplateEntry(variable, get(variable));
			set.add(entry);
		}
		return set;
	}

	@Override
	public String toString() {
		return "TemplateContext [context=" + context + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ViewContext other = (ViewContext) obj;
		if (context == null) {
			if (other.context != null)
				return false;
		} else if (!context.equals(other.context)) {
			return false;
		}
		return true;
	}

}

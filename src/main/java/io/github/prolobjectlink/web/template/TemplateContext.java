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
package io.github.prolobjectlink.web.template;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TemplateContext extends AbstractMap<Object, Object> implements Map<Object, Object> {

	private final String name;
	private final Map<Object, Object> map;

	public TemplateContext(String name) {
		this.map = new HashMap<Object, Object>();
		this.name = name;
	}

	@Override
	public Object put(Object arg0, Object arg1) {
		return map.put(arg0, arg1);
	}

	@Override
	public Set<Entry<Object, Object>> entrySet() {
		return map.entrySet();
	}

	public String getName() {
		return name;
	}

	public Map<Object, Object> getMap() {
		return map;
	}

	@Override
	public String toString() {
		return "TemplateContext [name=" + name + ", map=" + map + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		TemplateContext other = (TemplateContext) obj;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map)) {
			return false;
		}
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}

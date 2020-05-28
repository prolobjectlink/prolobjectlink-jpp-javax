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
package io.github.prolobjectlink.web.faces.common;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import io.github.prolobjectlink.web.faces.FacesSideBar;
import io.github.prolobjectlink.web.util.LinkedHashMap;

public abstract class AbstractFacesSideBar extends AbstractMap<String, String> implements FacesSideBar {

	private final Map<String, String> items;

	public AbstractFacesSideBar() {
		items = new LinkedHashMap<String, String>();
	}

	@Override
	public Set<Entry<String, String>> entrySet() {
		return items.entrySet();
	}

	@Override
	public String put(String arg0, String arg1) {
		return items.put(arg0, arg1);
	}

	@Override
	public void set(String arg0, String arg1) {
		items.put(arg0, arg1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((items == null) ? 0 : items.hashCode());
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
		AbstractFacesSideBar other = (AbstractFacesSideBar) obj;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items)) {
			return false;
		}
		return true;
	}

	@Override
	public Map<String, String> getItems() {
		return items;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(
				"<ul class=\"navbar-nav bg-gradient-primary sidebar sidebar-dark accordion\" id=\"accordionSidebar\">");
		Iterator<Entry<String, String>> i = items.entrySet().iterator();
		if (i.hasNext()) {
			Entry<?, ?> entry = i.next();
			result.append("<li class=\"nav-item\">");
			result.append("\t<a class=\"nav-link\" href=\"{{ path(\"" + entry.getValue() + "\") }}\">");
			result.append("\t\t<i class=\"fas fa-fw fa-table\"></i>");
			result.append("\t\t<span>" + entry.getKey() + "</span></a>");
			result.append("</li>");
			while (i.hasNext()) {
				entry = i.next();
				result.append('\n');
				result.append("<li class=\"nav-item\">");
				result.append("\t<a class=\"nav-link\" href=\"{{ path(\"" + entry.getValue() + "\") }}\">");
				result.append("\t\t<i class=\"fas fa-fw fa-table\"></i>");
				result.append("\t\t<span>" + entry.getKey() + "</span></a>");
				result.append("</li>");
			}
		}
		result.append("</ul>");
		return result.toString();
	}

}

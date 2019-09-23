/*-
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
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
package org.prolobjectlink.web.application;

import javax.servlet.Servlet;

public class ServletUrlMapping {

	private final Servlet servlet;
	private final String mappingUrl;

	public ServletUrlMapping(Servlet servlet, String mappingUrl) {
		this.servlet = servlet;
		this.mappingUrl = mappingUrl;
	}

	public Servlet getServlet() {
		return servlet;
	}

	public String getMappingUrl() {
		return mappingUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mappingUrl == null) ? 0 : mappingUrl.hashCode());
		result = prime * result + ((servlet == null) ? 0 : servlet.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServletUrlMapping other = (ServletUrlMapping) obj;
		if (mappingUrl == null) {
			if (other.mappingUrl != null)
				return false;
		} else if (!mappingUrl.equals(other.mappingUrl))
			return false;
		if (servlet == null) {
			if (other.servlet != null)
				return false;
		} else if (!servlet.equals(other.servlet))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServletUrlMapping [servlet=" + servlet + ", mappingUrl=" + mappingUrl + "]";
	}

}

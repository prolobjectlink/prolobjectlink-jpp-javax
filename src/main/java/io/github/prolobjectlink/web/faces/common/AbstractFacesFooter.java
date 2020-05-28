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

import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;

import io.github.prolobjectlink.web.faces.FacesFooter;

public class AbstractFacesFooter implements FacesFooter {

	private final String applicationName;

	public AbstractFacesFooter() {
		applicationName = "Prolobjectlink";
	}

	public AbstractFacesFooter(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applicationName == null) ? 0 : applicationName.hashCode());
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
		AbstractFacesFooter other = (AbstractFacesFooter) obj;
		if (applicationName == null) {
			if (other.applicationName != null)
				return false;
		} else if (!applicationName.equals(other.applicationName)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		int year = new Date(System.currentTimeMillis()).getYear();
		result.append("<footer class=\"sticky-footer bg-white\">");
		result.append("<div class=\"container my-auto\">");
		result.append("<div class=\"copyright text-center my-auto\">");
		result.append("<span>Copyright &copy; " + getApplicationName() + " " + year + "</span>");
		result.append("</div>");
		result.append("</div>");
		result.append("</footer>");
		return result.toString();
	}

}

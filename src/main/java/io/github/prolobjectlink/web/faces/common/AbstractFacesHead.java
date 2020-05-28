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

import java.util.ArrayList;
import java.util.List;

import io.github.prolobjectlink.web.faces.FacesHead;

public class AbstractFacesHead implements FacesHead {

	private final String title;
	private final List<String> meta;
	private final List<String> links;
	private final List<String> scripts;

	public AbstractFacesHead(String title) {
		this.scripts = new ArrayList<String>();
		this.links = new ArrayList<String>();
		this.meta = new ArrayList<String>();
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public List<String> getMeta() {
		return meta;
	}

	public List<String> getLinks() {
		return links;
	}

	public List<String> getScripts() {
		return scripts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((links == null) ? 0 : links.hashCode());
		result = prime * result + ((meta == null) ? 0 : meta.hashCode());
		result = prime * result + ((scripts == null) ? 0 : scripts.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		AbstractFacesHead other = (AbstractFacesHead) obj;
		if (links == null) {
			if (other.links != null)
				return false;
		} else if (!links.equals(other.links)) {
			return false;
		}
		if (meta == null) {
			if (other.meta != null)
				return false;
		} else if (!meta.equals(other.meta)) {
			return false;
		}
		if (scripts == null) {
			if (other.scripts != null)
				return false;
		} else if (!scripts.equals(other.scripts)) {
			return false;
		}
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "AbstractFacesHead [title=" + title + ", meta=" + meta + ", links=" + links + ", scripts=" + scripts
				+ "]";
	}

}

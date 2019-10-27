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
package org.prolobjectlink.web.entry;

import java.util.Date;

public class DatabaseEntry {

	private final String type;
	private final String name;
	private final Long size;
	private final Date modify;

	public DatabaseEntry(String type, String name, Long size, Long modify) {
		this.modify = new Date(modify);
		this.type = type;
		this.name = name;
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public Long getSize() {
		return size;
	}

	public Date getModify() {
		return modify;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((modify == null) ? 0 : modify.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		DatabaseEntry other = (DatabaseEntry) obj;
		if (modify == null) {
			if (other.modify != null)
				return false;
		} else if (!modify.equals(other.modify)) {
			return false;
		}
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size)) {
			return false;
		}
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}

}

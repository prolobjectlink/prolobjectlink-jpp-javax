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
package io.github.prolobjectlink.db.jpa.spi;

import java.util.StringTokenizer;

import javax.persistence.PersistenceException;

public final class JPAPersistenceSchemaVersion {

	private final String version;
	private final String encoding;

	public JPAPersistenceSchemaVersion(String version, String encoding) {
		this.version = version;
		this.encoding = encoding;
	}

	String getVersion() {
		return version;
	}

	String getEncoding() {
		return encoding;
	}

	String getFileName() {
		StringTokenizer tokenizer = new StringTokenizer(version, ".");
		if (tokenizer.countTokens() == 2) {
			int major = Integer.parseInt(tokenizer.nextToken());
			int minor = Integer.parseInt(tokenizer.nextToken());
			return String.format("persistence_%d_%d.xsd", major, minor);
		}
		throw new PersistenceException("No valid xml schema version");
	}

	@Override
	public String toString() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((encoding == null) ? 0 : encoding.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		JPAPersistenceSchemaVersion other = (JPAPersistenceSchemaVersion) obj;
		if (encoding == null) {
			if (other.encoding != null)
				return false;
		} else if (!encoding.equals(other.encoding))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

}

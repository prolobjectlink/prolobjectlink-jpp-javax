/*
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.prolobjectlink.db.jpa.spi;

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

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
package io.github.prolobjectlink.db.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.AttributeNode;
import javax.persistence.Subgraph;

public class JpaAttributeNode<T> implements AttributeNode<T> {

	protected final String attributeName;
	protected final Map<Class, Subgraph> subgraphs;
	protected final Map<Class, Subgraph> keySubgraphs;

	public JpaAttributeNode(String attribute) {
		this(attribute, new HashMap<Class, Subgraph>(), new HashMap<Class, Subgraph>());
	}

	public JpaAttributeNode(String attributeName, Map<Class, Subgraph> subgraphs, Map<Class, Subgraph> keySubgraphs) {
		this.attributeName = attributeName;
		this.keySubgraphs = keySubgraphs;
		this.subgraphs = subgraphs;
	}

	public final String getAttributeName() {
		return attributeName;
	}

	public final Map<Class, Subgraph> getSubgraphs() {
		return subgraphs;
	}

	public final Map<Class, Subgraph> getKeySubgraphs() {
		return keySubgraphs;
	}

	public void addSubgraph(JpaEntityGraph<?> entityGraph) {
		subgraphs.put(entityGraph.getClassType(), entityGraph);
	}

	public void addKeySubgraph(JpaEntityGraph<?> entityGraphImpl) {
		keySubgraphs.put(entityGraphImpl.getClassType(), entityGraphImpl);
	}

	@Override
	public String toString() {
		return "LogicAttributeNode [attributeName=" + attributeName + ", subgraphs=" + subgraphs + ", keySubgraphs="
				+ keySubgraphs + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributeName == null) ? 0 : attributeName.hashCode());
		result = prime * result + ((keySubgraphs == null) ? 0 : keySubgraphs.hashCode());
		result = prime * result + ((subgraphs == null) ? 0 : subgraphs.hashCode());
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
		JpaAttributeNode<?> other = (JpaAttributeNode<?>) obj;
		if (attributeName == null) {
			if (other.attributeName != null)
				return false;
		} else if (!attributeName.equals(other.attributeName))
			return false;
		if (keySubgraphs == null) {
			if (other.keySubgraphs != null)
				return false;
		} else if (!keySubgraphs.equals(other.keySubgraphs))
			return false;
		if (subgraphs == null) {
			if (other.subgraphs != null)
				return false;
		} else if (!subgraphs.equals(other.subgraphs))
			return false;
		return true;
	}

}

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
package io.github.prolobjectlink.db.jpa.metamodel;

import java.util.Map;

import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.Type;
import javax.persistence.metamodel.Type.PersistenceType;

public final class JpaMapAttribute<X, K, V> extends JpaPluralAttribute<X, Map<K, V>, V>
		implements MapAttribute<X, K, V> {

	private final Type<K> keyType;

	public JpaMapAttribute(ManagedType<X> ownerType, String name, Type<Map<K, V>> type, Type<V> elementType,
			PersistentAttributeType attributeType, Type<K> keyType) {
		super(ownerType, name, type, elementType, attributeType);
		this.keyType = keyType;
	}

	public Class<K> getKeyJavaType() {
		return keyType.getJavaType();
	}

	public Type<K> getKeyType() {
		return keyType;
	}

	public CollectionType getCollectionType() {
		return CollectionType.MAP;
	}

	@Override
	public boolean isAssociation() {
		return

		(getElementType().getPersistenceType() == PersistenceType.ENTITY)

				||

				(getKeyType().getPersistenceType() == PersistenceType.ENTITY);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((keyType == null) ? 0 : keyType.hashCode());
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
		JpaMapAttribute<?, ?, ?> other = (JpaMapAttribute<?, ?, ?>) obj;
		if (keyType == null) {
			if (other.keyType != null)
				return false;
		} else if (!keyType.equals(other.keyType)) {
			return false;
		}
		return true;
	}

}

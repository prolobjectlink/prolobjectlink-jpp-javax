/*
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
 * #L%
 */
package org.prolobjectlink.db.jpa.metamodel;

import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.Type;
import javax.persistence.metamodel.Type.PersistenceType;

/** @author Jose Zalacain @since 1.0 */
public abstract class JpaPluralAttribute<X, C, E> extends JpaAttribute<X, C> implements PluralAttribute<X, C, E> {

	protected final Type<E> elementType;
	protected final PersistentAttributeType attributeType;

	public JpaPluralAttribute(ManagedType<X> ownerType, String name, Type<C> type, Type<E> elementType,
			PersistentAttributeType attributeType) {
		super(ownerType, name, type);
		this.elementType = elementType;
		this.attributeType = attributeType;
	}

	public BindableType getBindableType() {
		return BindableType.PLURAL_ATTRIBUTE;
	}

	public Class<E> getBindableJavaType() {
		return elementType.getJavaType();
	}

	public PersistentAttributeType getPersistentAttributeType() {
		return attributeType;
	}

	public Type<E> getElementType() {
		return elementType;
	}

	public boolean isAssociation() {
		return getElementType().getPersistenceType() == PersistenceType.ENTITY;
	}

	public boolean isCollection() {
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((attributeType == null) ? 0 : attributeType.hashCode());
		result = prime * result + ((elementType == null) ? 0 : elementType.hashCode());
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
		JpaPluralAttribute<?, ?, ?> other = (JpaPluralAttribute<?, ?, ?>) obj;
		if (attributeType != other.attributeType)
			return false;
		if (elementType == null) {
			if (other.elementType != null)
				return false;
		} else if (!elementType.equals(other.elementType)) {
			return false;
		}
		return true;
	}

}

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
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import javax.persistence.metamodel.Type.PersistenceType;

public final class JpaSingularAttribute<X, Y> extends JpaAttribute<X, Y> implements SingularAttribute<X, Y> {

	private boolean id;
	private boolean version;

	protected JpaSingularAttribute(ManagedType<X> ownerType, String name, Type<Y> type) {
		super(ownerType, name, type);
	}

	public BindableType getBindableType() {
		return BindableType.SINGULAR_ATTRIBUTE;
	}

	public Class<Y> getBindableJavaType() {
		return getJavaType();
	}

	public boolean isId() {
		return id;
	}

	public boolean isVersion() {
		return version;
	}

	public boolean isOptional() {
		return !(getType() instanceof ManagedType);
	}

	public Type<Y> getType() {
		return type;
	}

	public boolean isAssociation() {
		return getType().getPersistenceType() == PersistenceType.ENTITY;
	}

	public boolean isCollection() {
		return false;
	}

	public PersistentAttributeType getPersistentAttributeType() {
		if (getType().getPersistenceType() == PersistenceType.BASIC) {
			return PersistentAttributeType.BASIC;
		}
		return PersistentAttributeType.ONE_TO_ONE;
	}

	public void setId(boolean id) {
		this.id = id;
	}

	public void setVersion(boolean version) {
		this.version = version;
	}

	public void markLikeId() {
		setId(true);
	}

	public void markLikeVersion() {
		setVersion(true);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (id ? 1231 : 1237);
		result = prime * result + (version ? 1231 : 1237);
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
		JpaSingularAttribute<?, ?> other = (JpaSingularAttribute<?, ?>) obj;
		if (id != other.id)
			return false;
		return version == other.version;
	}

}

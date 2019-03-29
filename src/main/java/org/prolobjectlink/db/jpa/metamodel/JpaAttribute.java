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

import java.lang.reflect.Member;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Type;

import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;

/** @author Jose Zalacain @since 1.0 */
public abstract class JpaAttribute<X, Y> implements Attribute<X, Y> {

	protected final String name;
	protected final Type<Y> type;
	protected final ManagedType<X> ownerType;

	protected JpaAttribute(ManagedType<X> ownerType, String name, Type<Y> type) {
		this.ownerType = ownerType;
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public ManagedType<X> getDeclaringType() {
		return ownerType;
	}

	public Class<Y> getJavaType() {
		return type.getJavaType();
	}

	public Member getJavaMember() {
		Member member = null;
		try {
			Class<X> clazz = ownerType.getJavaType();
			member = clazz.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			LoggerUtils.error(getClass(), LoggerConstants.NO_SUCH_FIELD, e);
		} catch (SecurityException e) {
			LoggerUtils.error(getClass(), LoggerConstants.SECURITY, e);
		}
		return member;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ownerType == null) ? 0 : ownerType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		JpaAttribute<?, ?> other = (JpaAttribute<?, ?>) obj;
		if (ownerType == null) {
			if (other.ownerType != null)
				return false;
		} else if (!ownerType.equals(other.ownerType)) {
			return false;
		}
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
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

	@Override
	public String toString() {
		return "JPAAttribute [name=" + name + ", type=" + type + ", declaredType=" + ownerType + "]";
	}

}

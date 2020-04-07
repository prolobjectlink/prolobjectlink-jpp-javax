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

import javax.persistence.Entity;
import javax.persistence.metamodel.EntityType;

import io.github.prolobjectlink.db.DatabaseClass;
import io.github.prolobjectlink.db.Schema;

public final class JpaEntityType<X> extends JpaIdentifiableType<X> implements EntityType<X> {

	public JpaEntityType(Schema schema, DatabaseClass databaseClass) {
		super(schema, databaseClass);
	}

	public PersistenceType getPersistenceType() {
		return PersistenceType.ENTITY;
	}

	public BindableType getBindableType() {
		return BindableType.ENTITY_TYPE;
	}

	public Class<X> getBindableJavaType() {
		return getJavaType();
	}

	public String getName() {
		Class<?> type = getJavaType();
		String name = type.getAnnotation(Entity.class).name();
		return name.isEmpty() ? type.getSimpleName() : name;
	}

}

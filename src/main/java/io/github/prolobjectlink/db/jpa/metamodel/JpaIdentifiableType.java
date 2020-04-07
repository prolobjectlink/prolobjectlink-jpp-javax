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

import java.lang.reflect.Field;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.MappedSuperclass;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.IdentifiableType;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;

import io.github.prolobjectlink.db.DatabaseClass;
import io.github.prolobjectlink.db.Schema;

/** @author Jose Zalacain @since 1.0 */
public abstract class JpaIdentifiableType<X> extends JpaManagedType<X> implements IdentifiableType<X> {

	public JpaIdentifiableType(Schema schema, DatabaseClass databaseClass) {
		super(schema, databaseClass);
	}

	public final <Y> SingularAttribute<? super X, Y> getId(Class<Y> type) {
		return getDeclaredId(type);
	}

	public final <Y> SingularAttribute<X, Y> getDeclaredId(Class<Y> type) {
		Class<?> owner = databaseClass.getJavaClass();
		Field[] fields = owner.getDeclaredFields();
		for (Field field : fields) {
			if (field.getType() == type) {
				String name = field.getName();
				if (field.isAnnotationPresent(Id.class)) {
					if (type.isAnnotationPresent(IdClass.class)) {
						DatabaseClass cls = schema.getClass(type);
						if (type.isAnnotationPresent(MappedSuperclass.class)) {
							Type<Y> modelType = new JpaMappedSuperclassType<Y>(schema, cls);
							return new JpaSingularAttribute<X, Y>(this, name, modelType);
						} else {
							Type<Y> modelType = new JpaEntityType<Y>(schema, cls);
							return new JpaSingularAttribute<X, Y>(this, name, modelType);
						}
					} else {
						Type<Y> modelType = new JpaBasicType<Y>(schema, type);
						return new JpaSingularAttribute<X, Y>(this, name, modelType);
					}
				} else if (field.isAnnotationPresent(EmbeddedId.class) && type.isAnnotationPresent(Embeddable.class)) {
					DatabaseClass cls = schema.getClass(type);
					Type<Y> modelType = new JpaEmbeddableType<Y>(schema, cls);
					return new JpaSingularAttribute<X, Y>(this, name, modelType);
				}
			} else {
				throw new PersistenceException("No type matching for given argument and " + field.getType());
			}
		}
		throw new PersistenceException("No @Id annotation was found in " + databaseClass);
	}

	public final <Y> SingularAttribute<? super X, Y> getVersion(Class<Y> type) {
		return getDeclaredVersion(type);
	}

	public final <Y> SingularAttribute<X, Y> getDeclaredVersion(Class<Y> type) {
		Type<Y> modelType = null;
		String name = databaseClass.getVersionField().getName();
		Class<?> fType = databaseClass.getVersionField().getType();
		if (!schema.containsClass(fType.getName())) {
			modelType = new JpaBasicType<Y>(schema, (Class<Y>) fType);
		} else {
			DatabaseClass cls = schema.getClass(fType);
			modelType = new JpaEntityType<Y>(schema, cls);
		}
		assert modelType != null;
		return new JpaSingularAttribute<X, Y>(this, name, modelType);
	}

	public final IdentifiableType<? super X> getSupertype() {
		DatabaseClass s = databaseClass.getSuperClass();
		// TODO Auto-generated method stub
		return null;
	}

	public final boolean hasSingleIdAttribute() {
		Class<?> fType = databaseClass.getIdField().getType();
		return (!schema.containsClass(fType.getName()));
	}

	public final boolean hasVersionAttribute() {
		return databaseClass.hasVersionField();
	}

	public final Set<SingularAttribute<? super X, ?>> getIdClassAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	public final Type<?> getIdType() {
		Type<?> modelType = null;
		Class<?> fType = databaseClass.getIdField().getType();
		if (!schema.containsClass(fType.getName())) {
			modelType = new JpaBasicType(schema, fType);
		} else {
			DatabaseClass cls = schema.getClass(fType);
			modelType = new JpaEntityType(schema, cls);
		}
		return modelType;
	}

}

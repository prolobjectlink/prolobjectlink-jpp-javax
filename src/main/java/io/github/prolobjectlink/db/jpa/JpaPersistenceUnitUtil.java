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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Id;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnitUtil;

import io.github.prolobjectlink.db.util.JavaReflect;

public final class JpaPersistenceUnitUtil implements PersistenceUnitUtil {

	public JpaPersistenceUnitUtil() {
	}

	public boolean isLoaded(Object entity, String attributeName) {
		Class<?> clazz = entity.getClass();
		Field field = JavaReflect.getDeclaredField(clazz, attributeName);
		return JavaReflect.readValue(field, entity) != null;
	}

	public boolean isLoaded(Object entity) {
		Class<?> clazz = entity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!isLoaded(entity, field.getName())) {
				return false;
			}
		}
		return true;
	}

	public Object getIdentifier(Object entity) {
		Class<?> clazz = entity.getClass();

		// Check AccessType.PROPERTY
		if (clazz.isAnnotationPresent(Access.class)) {
			AccessType accessType = clazz.getAnnotation(Access.class).value();
			if (accessType == AccessType.PROPERTY) {
				Method[] methods = clazz.getDeclaredMethods();
				for (Method method : methods) {
					if (method.isAnnotationPresent(Id.class)) {
						return JavaReflect.invoke(entity, method);
					}
				}
			}
		}

		// AccessType.FIELD is assumed by default
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Id.class)) {
				return JavaReflect.readValue(field, entity);
			}
		}

		throw new PersistenceException(" The class " + clazz + " no field was annotated with @Id annotation");
	}

}

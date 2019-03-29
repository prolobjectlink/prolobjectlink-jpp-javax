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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.MappedSuperclassType;
import javax.persistence.metamodel.Metamodel;

import org.prolobjectlink.db.DatabaseClass;
import org.prolobjectlink.db.Schema;

public final class JpaMetamodel implements Metamodel {

	private final Schema schema;

	public JpaMetamodel(Schema schema) {
		this.schema = schema;
	}

	public <X> EntityType<X> entity(Class<X> cls) {
		DatabaseClass c = schema.getClass(cls);
		assertEntityAnnotatedClass(cls);
		return new JpaEntityType<X>(schema, c);
	}

	public <X> ManagedType<X> managedType(Class<X> cls) {
		ManagedType<X> managedType = null;
		if (cls.isAnnotationPresent(MappedSuperclass.class)) {
			managedType = mappedSuperclass(cls);
		} else if (cls.isAnnotationPresent(Embeddable.class)) {
			managedType = embeddable(cls);
		} else if (cls.isAnnotationPresent(Entity.class)) {
			managedType = entity(cls);
		}
		return managedType;
	}

	public <X> MappedSuperclassType<X> mappedSuperclass(Class<X> cls) {
		DatabaseClass c = schema.getClass(cls);
		assertMappedSuperclassAnnotatedClass(cls);
		return new JpaMappedSuperclassType<X>(schema, c);
	}

	public <X> EmbeddableType<X> embeddable(Class<X> cls) {
		DatabaseClass c = schema.getClass(cls);
		assertEmbeddableAnnotatedClass(cls);
		return new JpaEmbeddableType<X>(schema, c);
	}

	public Set<ManagedType<?>> getManagedTypes() {
		Set<ManagedType<?>> types = new HashSet<ManagedType<?>>();
		types.addAll(getEmbeddables());
		types.addAll(getEntities());
		return types;
	}

	public Set<EntityType<?>> getEntities() {
		Set<EntityType<?>> entities = new HashSet<EntityType<?>>();
		for (DatabaseClass cls : schema.getClasses()) {
			if (cls.getJavaClass().isAnnotationPresent(Entity.class)) {
				EntityType<?> e = new JpaEntityType(schema, cls);
				entities.add(e);
			}
		}
		return entities;
	}

	public Set<EmbeddableType<?>> getEmbeddables() {
		Set<EmbeddableType<?>> embeddables = new HashSet<EmbeddableType<?>>();
		for (DatabaseClass cls : schema.getClasses()) {
			if (cls.getJavaClass().isAnnotationPresent(Embeddable.class)) {
				EmbeddableType<?> e = new JpaEmbeddableType(schema, cls);
				embeddables.add(e);
			}
		}
		return embeddables;
	}

	private void assertEmbeddableAnnotatedClass(Class<?> cls) {
		if (!cls.isAnnotationPresent(Embeddable.class)) {
			throw new PersistenceException("No @Embeddable annotated class " + cls);
		}
	}

	private void assertEntityAnnotatedClass(Class<?> cls) {
		if (!cls.isAnnotationPresent(Entity.class)) {
			throw new PersistenceException("No @Entity annotated class " + cls);
		}
	}

	private void assertMappedSuperclassAnnotatedClass(Class<?> cls) {
		if (!cls.isAnnotationPresent(MappedSuperclass.class)) {
			throw new PersistenceException("No @MappedSuperclass annotated class " + cls);
		}
	}

}

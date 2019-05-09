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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;

import org.prolobjectlink.db.DatabaseClass;
import org.prolobjectlink.db.DatabaseField;
import org.prolobjectlink.db.Schema;

/**
 * 
 * @author Jose Zalacain
 *
 * @param <X>
 * @since 1.0
 */
/** @author Jose Zalacain @since 1.0 */
public abstract class JpaManagedType<X> extends JpaType<X> implements ManagedType<X> {

	protected final DatabaseClass databaseClass;

	public JpaManagedType(Schema schema, DatabaseClass databaseClass) {
		super(schema, (Class<X>) databaseClass.getJavaClass());
		this.databaseClass = databaseClass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((databaseClass == null) ? 0 : databaseClass.hashCode());
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
		JpaManagedType<?> other = (JpaManagedType<?>) obj;
		if (databaseClass == null) {
			if (other.databaseClass != null)
				return false;
		} else if (!databaseClass.equals(other.databaseClass)) {
			return false;
		}
		return true;
	}

	public final Set<Attribute<? super X, ?>> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	public final Set<Attribute<X, ?>> getDeclaredAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	public final <Y> SingularAttribute<? super X, Y> getSingularAttribute(String name, Class<Y> type) {
		return getDeclaredSingularAttribute(name, type);
	}

	public final <Y> SingularAttribute<X, Y> getDeclaredSingularAttribute(String name, Class<Y> type) {
		return null;
	}

	public final Set<SingularAttribute<? super X, ?>> getSingularAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	public final Set<SingularAttribute<X, ?>> getDeclaredSingularAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	public final <E> CollectionAttribute<? super X, E> getCollection(String name, Class<E> elementType) {
		return getDeclaredCollection(name, elementType);
	}

	public final <E> CollectionAttribute<X, E> getDeclaredCollection(String name, Class<E> elementType) {
		// TODO Auto-generated method stub
		return null;
	}

	public final <E> SetAttribute<? super X, E> getSet(String name, Class<E> elementType) {
		return getDeclaredSet(name, elementType);
	}

	public final <E> SetAttribute<X, E> getDeclaredSet(String name, Class<E> elementType) {
		// TODO Auto-generated method stub
		return null;
	}

	public final <E> ListAttribute<? super X, E> getList(String name, Class<E> elementType) {
		return getDeclaredList(name, elementType);
	}

	public final <E> ListAttribute<X, E> getDeclaredList(String name, Class<E> elementType) {
		// TODO Auto-generated method stub
		return null;
	}

	public final <K, V> MapAttribute<? super X, K, V> getMap(String name, Class<K> keyType, Class<V> valueType) {
		return getDeclaredMap(name, keyType, valueType);
	}

	public final <K, V> MapAttribute<X, K, V> getDeclaredMap(String name, Class<K> keyType, Class<V> valueType) {
		// TODO Auto-generated method stub
		return null;
	}

	public final Set<PluralAttribute<? super X, ?, ?>> getPluralAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	public final Set<PluralAttribute<X, ?, ?>> getDeclaredPluralAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	public final Attribute<? super X, ?> getAttribute(String name) {
		return getDeclaredAttribute(name);
	}

	public final Attribute<X, ?> getDeclaredAttribute(String name) {
		DatabaseField att = databaseClass.getField(name);
		Class<X> cls = (Class<X>) att.getType();
		Attribute<X, ?> attribute = null;
		// check basic or managed type

		// check plural attributes
		if (Collection.class.isAssignableFrom(cls)) {
			Type<X> type = new JpaBasicType<X>(schema, cls);
			Type<X> elementType = new JpaBasicType<X>(schema, cls);
			Type<X> attributeType = new JpaBasicType<X>(schema, cls);
			Type<X> keyType = new JpaBasicType<X>(schema, cls);
			attribute = new JpaCollectionAttribute(this, name, type, null, null);
		} else if (List.class.isAssignableFrom(cls)) {
			Type<X> type = new JpaBasicType<X>(schema, cls);
			Type<X> elementType = new JpaBasicType<X>(schema, cls);
			Type<X> attributeType = new JpaBasicType<X>(schema, cls);
			Type<X> keyType = new JpaBasicType<X>(schema, cls);
			attribute = new JpaListAttribute(this, name, type, null, null);
		} else if (Set.class.isAssignableFrom(cls)) {
			Type<X> type = new JpaBasicType<X>(schema, cls);
			Type<X> elementType = new JpaBasicType<X>(schema, cls);
			Type<X> attributeType = new JpaBasicType<X>(schema, cls);
			Type<X> keyType = new JpaBasicType<X>(schema, cls);
			attribute = new JpaSetAttribute(this, name, type, null, null);
		} else if (Map.class.isAssignableFrom(cls)) {
			Type<X> type = new JpaBasicType<X>(schema, cls);
			Type<X> elementType = new JpaBasicType<X>(schema, cls);
			Type<X> attributeType = new JpaBasicType<X>(schema, cls);
			Type<X> keyType = new JpaBasicType<X>(schema, cls);
			attribute = new JpaMapAttribute(this, name, type, null, null, null);
		} else {
			Type<X> type = new JpaBasicType<X>(schema, cls);
			attribute = new JpaSingularAttribute(this, name, type);
		}

		return attribute;
	}

	public final SingularAttribute<? super X, ?> getSingularAttribute(String name) {
		return getDeclaredSingularAttribute(name);
	}

	public final SingularAttribute<X, ?> getDeclaredSingularAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public final CollectionAttribute<? super X, ?> getCollection(String name) {
		return getCollection(name, null);
	}

	public final CollectionAttribute<X, ?> getDeclaredCollection(String name) {
		return getDeclaredCollection(name, null);
	}

	public final SetAttribute<? super X, ?> getSet(String name) {
		return getDeclaredSet(name);
	}

	public final SetAttribute<X, ?> getDeclaredSet(String name) {
		return getDeclaredSet(name, null);
	}

	public final ListAttribute<? super X, ?> getList(String name) {
		return getDeclaredList(name);
	}

	public final ListAttribute<X, ?> getDeclaredList(String name) {
		return getDeclaredList(name, null);
	}

	public final MapAttribute<? super X, ?, ?> getMap(String name) {
		return getDeclaredMap(name);
	}

	public final MapAttribute<X, ?, ?> getDeclaredMap(String name) {
		return getDeclaredMap(name, null, null);
	}

	@Override
	public final String toString() {
		return databaseClass.getShortName();
	}

}

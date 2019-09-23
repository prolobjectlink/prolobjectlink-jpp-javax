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
package org.prolobjectlink.db.jpa.criteria;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CollectionJoin;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.SetJoin;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.prolobjectlink.db.jpa.criteria.predicate.JpaAndPredicate;

public class JpaJoin<Z, X> extends JpaPath<X> implements Join<Z, X> {

	protected ManagedType<X> managedType;
	protected Set<Join<X, ?>> joins;
	protected Set<Fetch<X, ?>> fetches;
	protected boolean isJoin = false;
	protected boolean isFetch = false;
	protected From<Z, X> correlatedParent;
	private final JoinType joinType;

	public JpaJoin(String alias, Class<? extends X> javaType, Expression<X> expression, Metamodel metamodel,
			Path<?> pathParent, Bindable<X> model, ManagedType<X> managedType, Set<Join<X, ?>> joins,
			Set<Fetch<X, ?>> fetches, boolean isJoin, boolean isFetch, From<Z, X> correlatedParent, JoinType joinType) {
		super(alias, javaType, expression, metamodel, pathParent, model);
		this.managedType = managedType;
		this.joins = joins;
		this.fetches = fetches;
		this.isJoin = isJoin;
		this.isFetch = isFetch;
		this.correlatedParent = correlatedParent;
		this.joinType = joinType;
	}

	public Join<Z, X> on(Expression<Boolean> restriction) {
		expression = restriction;
		return this;
	}

	public Join<Z, X> on(Predicate... restrictions) {
		for (Predicate predicate : restrictions) {
			expression = new JpaAndPredicate(null, Boolean.class, null, metamodel, newList(expression, predicate));
		}
		return this;
	}

	public Predicate getOn() {
		return (Predicate) expression;
	}

	public Attribute<? super Z, ?> getAttribute() {
		return (Attribute<? super Z, ?>) model;
	}

	public From<?, Z> getParent() {
		return null;
	}

	public JoinType getJoinType() {
		return joinType;
	}

	public Set<Join<X, ?>> getJoins() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isCorrelated() {
		// TODO Auto-generated method stub
		return false;
	}

	public From<Z, X> getCorrelationParent() {
		// TODO Auto-generated method stub
		return null;
	}

	public <Y> Join<X, Y> join(SingularAttribute<? super X, Y> attribute) {
		// TODO Auto-generated method stub
		return null;
	}

	public <Y> Join<X, Y> join(SingularAttribute<? super X, Y> attribute, JoinType jt) {
		// TODO Auto-generated method stub
		return null;
	}

	public <Y> CollectionJoin<X, Y> join(CollectionAttribute<? super X, Y> collection) {
		// TODO Auto-generated method stub
		return null;
	}

	public <Y> SetJoin<X, Y> join(SetAttribute<? super X, Y> set) {
		// TODO Auto-generated method stub
		return null;
	}

	public <Y> ListJoin<X, Y> join(ListAttribute<? super X, Y> list) {
		// TODO Auto-generated method stub
		return null;
	}

	public <K, V> MapJoin<X, K, V> join(MapAttribute<? super X, K, V> map) {
		// TODO Auto-generated method stub
		return null;
	}

	public <Y> CollectionJoin<X, Y> join(CollectionAttribute<? super X, Y> collection, JoinType jt) {
		// TODO Auto-generated method stub
		return null;
	}

	public <Y> SetJoin<X, Y> join(SetAttribute<? super X, Y> set, JoinType jt) {
		// TODO Auto-generated method stub
		return null;
	}

	public <Y> ListJoin<X, Y> join(ListAttribute<? super X, Y> list, JoinType jt) {
		// TODO Auto-generated method stub
		return null;
	}

	public <K, V> MapJoin<X, K, V> join(MapAttribute<? super X, K, V> map, JoinType jt) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, Y> Join<X, Y> join(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, Y> CollectionJoin<X, Y> joinCollection(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, Y> SetJoin<X, Y> joinSet(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, Y> ListJoin<X, Y> joinList(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, K, V> MapJoin<X, K, V> joinMap(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, Y> Join<X, Y> join(String attributeName, JoinType jt) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, Y> CollectionJoin<X, Y> joinCollection(String attributeName, JoinType jt) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, Y> SetJoin<X, Y> joinSet(String attributeName, JoinType jt) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, Y> ListJoin<X, Y> joinList(String attributeName, JoinType jt) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, K, V> MapJoin<X, K, V> joinMap(String attributeName, JoinType jt) {
		// TODO Auto-generated method stub
		return null;
	}

	public Bindable<X> getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public Path<?> getParentPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public <Y> Path<Y> get(SingularAttribute<? super X, Y> attribute) {
		// TODO Auto-generated method stub
		return null;
	}

	public <E, C extends Collection<E>> Expression<C> get(PluralAttribute<X, C, E> collection) {
		// TODO Auto-generated method stub
		return null;
	}

	public <K, V, M extends Map<K, V>> Expression<M> get(MapAttribute<X, K, V> map) {
		// TODO Auto-generated method stub
		return null;
	}

	public Expression<Class<? extends X>> type() {
		// TODO Auto-generated method stub
		return null;
	}

	public <Y> Path<Y> get(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Predicate isNull() {
		// TODO Auto-generated method stub
		return null;
	}

	public Predicate isNotNull() {
		// TODO Auto-generated method stub
		return null;
	}

	public Predicate in(Object... values) {
		// TODO Auto-generated method stub
		return null;
	}

	public Predicate in(Expression<?>... values) {
		// TODO Auto-generated method stub
		return null;
	}

	public Predicate in(Collection<?> values) {
		// TODO Auto-generated method stub
		return null;
	}

	public Predicate in(Expression<Collection<?>> values) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X> Expression<X> as(Class<X> type) {
		// TODO Auto-generated method stub
		return null;
	}

	public Selection<X> alias(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isCompoundSelection() {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Selection<?>> getCompoundSelectionItems() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<? extends X> getJavaType() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAlias() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Fetch<X, ?>> getFetches() {
		// TODO Auto-generated method stub
		return null;
	}

	public <Y> Fetch<X, Y> fetch(SingularAttribute<? super X, Y> attribute) {
		// TODO Auto-generated method stub
		return null;
	}

	public <Y> Fetch<X, Y> fetch(SingularAttribute<? super X, Y> attribute, JoinType jt) {
		// TODO Auto-generated method stub
		return null;
	}

	public <Y> Fetch<X, Y> fetch(PluralAttribute<? super X, ?, Y> attribute) {
		// TODO Auto-generated method stub
		return null;
	}

	public <Y> Fetch<X, Y> fetch(PluralAttribute<? super X, ?, Y> attribute, JoinType jt) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, Y> Fetch<X, Y> fetch(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, Y> Fetch<X, Y> fetch(String attributeName, JoinType jt) {
		// TODO Auto-generated method stub
		return null;
	}

}

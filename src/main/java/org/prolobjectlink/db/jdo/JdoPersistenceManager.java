/*
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.prolobjectlink.db.jdo;

import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import javax.jdo.Extent;
import javax.jdo.FetchGroup;
import javax.jdo.FetchPlan;
import javax.jdo.JDOException;
import javax.jdo.ObjectState;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.datastore.JDOConnection;
import javax.jdo.datastore.Sequence;
import javax.jdo.listener.InstanceLifecycleListener;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SynchronizationType;

import org.prolobjectlink.db.DatabaseEngine;
import org.prolobjectlink.db.common.AbstractEntityManager;
import org.prolobjectlink.db.jpa.JpaResultSetMapping;

public class JdoPersistenceManager extends AbstractEntityManager implements PersistenceManager {

	private final Transaction transaction;

	public JdoPersistenceManager(DatabaseEngine database, EntityManagerFactory managerFactory,
			SynchronizationType synchronizationType, Map properties, Map<String, Class<?>> entityMap,
			Map<String, javax.persistence.Query> namedQueries, Map<String, EntityGraph<?>> namedEntityGraphs,
			Map<String, JpaResultSetMapping> resultSetMappings) {
		super(database, managerFactory, synchronizationType, properties, entityMap, namedQueries, namedEntityGraphs,
				resultSetMappings);
		this.transaction = new JdoTransaction(database.getTransaction());

	}

	public boolean isClosed() {
		return closed;
	}

	public Transaction currentTransaction() {
		return transaction;
	}

	public void evict(Object pc) {
		// TODO Auto-generated method stub

	}

	public void evictAll(Object... pcs) {
		for (Object object : pcs) {
			evict(object);
		}
	}

	public void evictAll(Collection pcs) {
		for (Object object : pcs) {
			evict(object);
		}
	}

	public void evictAll(boolean subclasses, Class pcClass) {
		// TODO Auto-generated method stub
	}

	public void evictAll() {
		// TODO Auto-generated method stub

	}

	public void refreshAll(Object... pcs) {
		// TODO Auto-generated method stub

	}

	public void refreshAll(Collection pcs) {
		// TODO Auto-generated method stub

	}

	public void refreshAll() {
		// TODO Auto-generated method stub

	}

	public void refreshAll(JDOException jdoe) {
		// TODO Auto-generated method stub

	}

	public Query newQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	public Query newQuery(Object compiled) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query newQuery(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query newQuery(String language, Object query) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query newQuery(Class cls) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query newQuery(Extent cln) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query newQuery(Class cls, Collection cln) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query newQuery(Class cls, String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query newQuery(Class cls, Collection cln, String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query newQuery(Extent cln, String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public Query newNamedQuery(Class cls, String queryName) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> Extent<T> getExtent(Class<T> persistenceCapableClass, boolean subclasses) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> Extent<T> getExtent(Class<T> persistenceCapableClass) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getObjectById(Object oid, boolean validate) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T getObjectById(Class<T> cls, Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getObjectById(Object oid) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getObjectId(Object pc) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getTransactionalObjectId(Object pc) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object newObjectIdInstance(Class pcClass, Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection getObjectsById(Collection oids, boolean validate) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection getObjectsById(Collection oids) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] getObjectsById(Object[] oids, boolean validate) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] getObjectsById(boolean validate, Object... oids) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] getObjectsById(Object... oids) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T makePersistent(T pc) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T[] makePersistentAll(T... pcs) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> Collection<T> makePersistentAll(Collection<T> pcs) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deletePersistent(Object pc) {
		// TODO Auto-generated method stub

	}

	public void deletePersistentAll(Object... pcs) {
		// TODO Auto-generated method stub

	}

	public void deletePersistentAll(Collection pcs) {
		// TODO Auto-generated method stub

	}

	public void makeTransient(Object pc) {
		// TODO Auto-generated method stub

	}

	public void makeTransientAll(Object... pcs) {
		// TODO Auto-generated method stub

	}

	public void makeTransientAll(Collection pcs) {
		// TODO Auto-generated method stub

	}

	public void makeTransient(Object pc, boolean useFetchPlan) {
		// TODO Auto-generated method stub

	}

	public void makeTransientAll(Object[] pcs, boolean useFetchPlan) {
		// TODO Auto-generated method stub

	}

	public void makeTransientAll(boolean useFetchPlan, Object... pcs) {
		// TODO Auto-generated method stub

	}

	public void makeTransientAll(Collection pcs, boolean useFetchPlan) {
		// TODO Auto-generated method stub

	}

	public void makeTransactional(Object pc) {
		// TODO Auto-generated method stub

	}

	public void makeTransactionalAll(Object... pcs) {
		// TODO Auto-generated method stub

	}

	public void makeTransactionalAll(Collection pcs) {
		// TODO Auto-generated method stub

	}

	public void makeNontransactional(Object pc) {
		// TODO Auto-generated method stub

	}

	public void makeNontransactionalAll(Object... pcs) {
		// TODO Auto-generated method stub

	}

	public void makeNontransactionalAll(Collection pcs) {
		// TODO Auto-generated method stub

	}

	public void retrieve(Object pc) {
		// TODO Auto-generated method stub

	}

	public void retrieve(Object pc, boolean useFetchPlan) {
		// TODO Auto-generated method stub

	}

	public void retrieveAll(Collection pcs) {
		// TODO Auto-generated method stub

	}

	public void retrieveAll(Collection pcs, boolean useFetchPlan) {
		// TODO Auto-generated method stub

	}

	public void retrieveAll(Object... pcs) {
		// TODO Auto-generated method stub

	}

	public void retrieveAll(Object[] pcs, boolean useFetchPlan) {
		// TODO Auto-generated method stub

	}

	public void retrieveAll(boolean useFetchPlan, Object... pcs) {
		// TODO Auto-generated method stub

	}

	public void setUserObject(Object o) {
		// TODO Auto-generated method stub

	}

	public Object getUserObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public PersistenceManagerFactory getPersistenceManagerFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class getObjectIdClass(Class cls) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setMultithreaded(boolean flag) {
		// TODO Auto-generated method stub

	}

	public boolean getMultithreaded() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setIgnoreCache(boolean flag) {
		// TODO Auto-generated method stub

	}

	public boolean getIgnoreCache() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getDetachAllOnCommit() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setDetachAllOnCommit(boolean flag) {
		// TODO Auto-generated method stub

	}

	public boolean getCopyOnAttach() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setCopyOnAttach(boolean flag) {
		// TODO Auto-generated method stub

	}

	public <T> T detachCopy(T pc) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> Collection<T> detachCopyAll(Collection<T> pcs) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T[] detachCopyAll(T... pcs) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object putUserObject(Object key, Object val) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getUserObject(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object removeUserObject(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	public void checkConsistency() {
		// TODO Auto-generated method stub

	}

	public FetchPlan getFetchPlan() {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T newInstance(Class<T> pcClass) {
		// TODO Auto-generated method stub
		return null;
	}

	public Sequence getSequence(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public JDOConnection getDataStoreConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addInstanceLifecycleListener(InstanceLifecycleListener listener, Class... classes) {
		// TODO Auto-generated method stub

	}

	public void removeInstanceLifecycleListener(InstanceLifecycleListener listener) {
		// TODO Auto-generated method stub

	}

	public Date getServerDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set getManagedObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set getManagedObjects(EnumSet<ObjectState> states) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set getManagedObjects(Class... classes) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set getManagedObjects(EnumSet<ObjectState> states, Class... classes) {
		// TODO Auto-generated method stub
		return null;
	}

	public FetchGroup getFetchGroup(Class cls, String name) {
		// TODO Auto-generated method stub
		return null;
	}

}

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
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.jdo.FetchGroup;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.datastore.DataStoreCache;
import javax.jdo.listener.InstanceLifecycleListener;
import javax.jdo.metadata.ComponentMetadata;
import javax.jdo.metadata.JDOMetadata;

import org.prolobjectlink.db.DatabaseEngine;
import org.prolobjectlink.db.common.AbstractEntityManagerFactory;

public class JdoPersistenceManagerFactory extends AbstractEntityManagerFactory implements PersistenceManagerFactory {

	private static final long serialVersionUID = -8268233969545842471L;

	public JdoPersistenceManagerFactory(DatabaseEngine database, Map<Object, Object> properties) {
		super(database);
	}

	public void close() {
		// TODO Auto-generated method stub

	}

	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	public PersistenceManager getPersistenceManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public PersistenceManager getPersistenceManagerProxy() {
		// TODO Auto-generated method stub
		return null;
	}

	public PersistenceManager getPersistenceManager(String userid, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setConnectionUserName(String userName) {
		// TODO Auto-generated method stub

	}

	public String getConnectionUserName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setConnectionPassword(String password) {
		// TODO Auto-generated method stub

	}

	public void setConnectionURL(String url) {
		// TODO Auto-generated method stub

	}

	public String getConnectionURL() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setConnectionDriverName(String driverName) {
		// TODO Auto-generated method stub

	}

	public String getConnectionDriverName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setConnectionFactoryName(String connectionFactoryName) {
		// TODO Auto-generated method stub

	}

	public String getConnectionFactoryName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setConnectionFactory(Object connectionFactory) {
		// TODO Auto-generated method stub

	}

	public Object getConnectionFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setConnectionFactory2Name(String connectionFactoryName) {
		// TODO Auto-generated method stub

	}

	public String getConnectionFactory2Name() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setConnectionFactory2(Object connectionFactory) {
		// TODO Auto-generated method stub

	}

	public Object getConnectionFactory2() {
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

	public void setMapping(String mapping) {
		// TODO Auto-generated method stub

	}

	public String getMapping() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setOptimistic(boolean flag) {
		// TODO Auto-generated method stub

	}

	public boolean getOptimistic() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setRetainValues(boolean flag) {
		// TODO Auto-generated method stub

	}

	public boolean getRetainValues() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setRestoreValues(boolean restoreValues) {
		// TODO Auto-generated method stub

	}

	public boolean getRestoreValues() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setNontransactionalRead(boolean flag) {
		// TODO Auto-generated method stub

	}

	public boolean getNontransactionalRead() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setNontransactionalWrite(boolean flag) {
		// TODO Auto-generated method stub

	}

	public boolean getNontransactionalWrite() {
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

	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPersistenceUnitName(String name) {
		// TODO Auto-generated method stub

	}

	public String getPersistenceUnitName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setServerTimeZoneID(String timezoneid) {
		// TODO Auto-generated method stub

	}

	public String getServerTimeZoneID() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setTransactionType(String name) {
		// TODO Auto-generated method stub

	}

	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setReadOnly(boolean flag) {
		// TODO Auto-generated method stub

	}

	public String getTransactionIsolationLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setTransactionIsolationLevel(String level) {
		// TODO Auto-generated method stub

	}

	public Properties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<String> supportedOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	public DataStoreCache getDataStoreCache() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addInstanceLifecycleListener(InstanceLifecycleListener listener, Class[] classes) {
		// TODO Auto-generated method stub

	}

	public void removeInstanceLifecycleListener(InstanceLifecycleListener listener) {
		// TODO Auto-generated method stub

	}

	public void addFetchGroups(FetchGroup... groups) {
		// TODO Auto-generated method stub

	}

	public void removeFetchGroups(FetchGroup... groups) {
		// TODO Auto-generated method stub

	}

	public void removeAllFetchGroups() {
		// TODO Auto-generated method stub

	}

	public FetchGroup getFetchGroup(Class cls, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set getFetchGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	public ComponentMetadata getMetadata(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getQueryTimeoutMillis() {
		// TODO Auto-generated method stub
		return 0;
	}

	public JDOMetadata newMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	public void registerMetadata(JDOMetadata arg0) {
		// TODO Auto-generated method stub

	}

	public void setQueryTimeoutMillis(int arg0) {
		// TODO Auto-generated method stub

	}

}

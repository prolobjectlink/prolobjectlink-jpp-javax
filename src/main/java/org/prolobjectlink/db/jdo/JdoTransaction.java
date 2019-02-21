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

import javax.jdo.PersistenceManager;
import javax.transaction.Synchronization;

import org.prolobjectlink.db.Transaction;

public class JdoTransaction implements javax.jdo.Transaction {

	private final Transaction tx;

	public JdoTransaction(Transaction tx) {
		this.tx = tx;
	}

	public void begin() {
		tx.begin();
	}

	public void commit() {
		tx.commit();
	}

	public void rollback() {
		tx.rollback();
	}

	public boolean isActive() {
		return tx.isActive();
	}

	public void setRollbackOnly() {
		// TODO Auto-generated method stub

	}

	public boolean getRollbackOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setNontransactionalRead(boolean nontransactionalRead) {
		// TODO Auto-generated method stub

	}

	public boolean getNontransactionalRead() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setNontransactionalWrite(boolean nontransactionalWrite) {
		// TODO Auto-generated method stub

	}

	public boolean getNontransactionalWrite() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setRetainValues(boolean retainValues) {
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

	public void setOptimistic(boolean optimistic) {
		// TODO Auto-generated method stub

	}

	public boolean getOptimistic() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getIsolationLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setIsolationLevel(String level) {
		// TODO Auto-generated method stub

	}

	public void setSynchronization(Synchronization sync) {
		// TODO Auto-generated method stub

	}

	public Synchronization getSynchronization() {
		// TODO Auto-generated method stub
		return null;
	}

	public PersistenceManager getPersistenceManager() {
		// TODO Auto-generated method stub
		return null;
	}

}

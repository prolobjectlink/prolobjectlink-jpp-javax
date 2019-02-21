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
package org.prolobjectlink.db.jdo.spi;

import javax.jdo.PersistenceManager;
import javax.jdo.spi.PersistenceCapable;
import javax.jdo.spi.StateManager;

public class JdoPersistenceCapable implements PersistenceCapable {

	public PersistenceManager jdoGetPersistenceManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public void jdoReplaceStateManager(StateManager sm) throws SecurityException {
		// TODO Auto-generated method stub

	}

	public void jdoProvideField(int fieldNumber) {
		// TODO Auto-generated method stub

	}

	public void jdoProvideFields(int[] fieldNumbers) {
		// TODO Auto-generated method stub

	}

	public void jdoReplaceField(int fieldNumber) {
		// TODO Auto-generated method stub

	}

	public void jdoReplaceFields(int[] fieldNumbers) {
		// TODO Auto-generated method stub

	}

	public void jdoReplaceFlags() {
		// TODO Auto-generated method stub

	}

	public void jdoCopyFields(Object other, int[] fieldNumbers) {
		// TODO Auto-generated method stub

	}

	public void jdoMakeDirty(String fieldName) {
		// TODO Auto-generated method stub

	}

	public Object jdoGetObjectId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object jdoGetTransactionalObjectId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object jdoGetVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean jdoIsDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean jdoIsTransactional() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean jdoIsPersistent() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean jdoIsNew() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean jdoIsDeleted() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean jdoIsDetached() {
		// TODO Auto-generated method stub
		return false;
	}

	public PersistenceCapable jdoNewInstance(StateManager sm) {
		// TODO Auto-generated method stub
		return null;
	}

	public PersistenceCapable jdoNewInstance(StateManager sm, Object oid) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object jdoNewObjectIdInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object jdoNewObjectIdInstance(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	public void jdoCopyKeyFieldsToObjectId(Object oid) {
		// TODO Auto-generated method stub

	}

	public void jdoCopyKeyFieldsToObjectId(ObjectIdFieldSupplier fm, Object oid) {
		// TODO Auto-generated method stub

	}

	public void jdoCopyKeyFieldsFromObjectId(ObjectIdFieldConsumer fm, Object oid) {
		// TODO Auto-generated method stub

	}

}

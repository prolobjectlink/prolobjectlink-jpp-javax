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

import java.util.Iterator;

import javax.jdo.Extent;
import javax.jdo.FetchPlan;
import javax.jdo.PersistenceManager;

public class JdoExtend<E> implements Extent<E> {

	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasSubclasses() {
		// TODO Auto-generated method stub
		return false;
	}

	public Class<E> getCandidateClass() {
		// TODO Auto-generated method stub
		return null;
	}

	public PersistenceManager getPersistenceManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public void closeAll() {
		// TODO Auto-generated method stub

	}

	public void close(Iterator<E> it) {
		// TODO Auto-generated method stub

	}

	public FetchPlan getFetchPlan() {
		// TODO Auto-generated method stub
		return null;
	}

	public void close() throws Exception {
		// TODO Auto-generated method stub

	}

}

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

import javax.persistence.EntityTransaction;

import io.github.prolobjectlink.db.Transaction;

public final class JpaEntityTransaction implements EntityTransaction {

	private final Transaction tx;

	public JpaEntityTransaction(Transaction tx) {
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

	public void setRollbackOnly() {
		// TODO
	}

	public boolean getRollbackOnly() {
		// TODO
		return false;
	}

	public boolean isActive() {
		return tx.isActive();
	}

}

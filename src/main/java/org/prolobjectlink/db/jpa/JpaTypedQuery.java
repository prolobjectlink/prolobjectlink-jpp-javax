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
package org.prolobjectlink.db.jpa;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Parameter;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.prolobjectlink.db.DatabaseEngine;

public class JpaTypedQuery<X> extends JpaQuery implements TypedQuery<X> {

	public JpaTypedQuery(DatabaseEngine database, String qlString, Class<X> resultClass) {
		super(database, qlString, resultClass);
	}

	public List<X> getResultList() {
		// TODO Auto-generated method stub
		return null;
	}

	public X getSingleResult() {
		// TODO Auto-generated method stub
		return null;
	}

	public TypedQuery<X> setMaxResults(int maxResult) {
		// TODO Auto-generated method stub
		return null;
	}

	public TypedQuery<X> setFirstResult(int startPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	public TypedQuery<X> setHint(String hintName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> TypedQuery<X> setParameter(Parameter<T> param, T value) {
		// TODO Auto-generated method stub
		return null;
	}

	public TypedQuery<X> setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType) {
		// TODO Auto-generated method stub
		return null;
	}

	public TypedQuery<X> setParameter(Parameter<Date> param, Date value, TemporalType temporalType) {
		// TODO Auto-generated method stub
		return null;
	}

	public TypedQuery<X> setParameter(String name, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public TypedQuery<X> setParameter(String name, Calendar value, TemporalType temporalType) {
		// TODO Auto-generated method stub
		return null;
	}

	public TypedQuery<X> setParameter(String name, Date value, TemporalType temporalType) {
		// TODO Auto-generated method stub
		return null;
	}

	public TypedQuery<X> setParameter(int position, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public TypedQuery<X> setParameter(int position, Calendar value, TemporalType temporalType) {
		// TODO Auto-generated method stub
		return null;
	}

	public TypedQuery<X> setParameter(int position, Date value, TemporalType temporalType) {
		// TODO Auto-generated method stub
		return null;
	}

	public TypedQuery<X> setFlushMode(FlushModeType flushMode) {
		// TODO Auto-generated method stub
		return null;
	}

	public TypedQuery<X> setLockMode(LockModeType lockMode) {
		// TODO Auto-generated method stub
		return null;
	}

}

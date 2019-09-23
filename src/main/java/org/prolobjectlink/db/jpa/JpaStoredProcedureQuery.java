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
package org.prolobjectlink.db.jpa;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.FlushModeType;
import javax.persistence.Parameter;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceException;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TemporalType;

import org.prolobjectlink.db.DatabaseEngine;
import org.prolobjectlink.db.ProcedureQuery;

public final class JpaStoredProcedureQuery extends JpaQuery implements StoredProcedureQuery {

	private boolean hasMoreResult;
	private final String procedureName;
	private final String[] resultSetMappings;

	public JpaStoredProcedureQuery(DatabaseEngine database, String procedureName) {
		this(database, procedureName, new Class<?>[] { Object[].class }, null);
	}

	public JpaStoredProcedureQuery(DatabaseEngine database, String procedureName, Class<?>[] resultClasses) {
		this(database, procedureName, resultClasses, null);
	}

	public JpaStoredProcedureQuery(DatabaseEngine database, String procedureName, String[] resultSetMappings) {
		this(database, procedureName, new Class<?>[] { Object[].class }, resultSetMappings);
	}

	public JpaStoredProcedureQuery(DatabaseEngine database, String procedureName, Class<?>[] resultClasses,
			String[] resultSetMappings) {
		super(database, procedureName, resultClasses);
		this.procedureName = procedureName;
		this.resultSetMappings = resultSetMappings;
	}

	public StoredProcedureQuery registerStoredProcedureParameter(int position, Class type, ParameterMode mode) {
		Parameter<?> parameter = new JpaParameter(WILD_CARD + String.valueOf(position), position, type, mode);
		setParameter(parameter, null);
		return this;
	}

	public StoredProcedureQuery registerStoredProcedureParameter(String parameterName, Class type, ParameterMode mode) {
		Parameter<?> parameter = new JpaParameter(parameterName, parameterPosition++, type, mode);
		setParameter(parameter, null);
		return this;
	}

	public Object getOutputParameterValue(int position) {
		Parameter<?> parameter = getParameter(position);
		return getOutputParameterValue(parameter.getName());
	}

	public Object getOutputParameterValue(String parameterName) {
		Parameter<?> parameter = getParameter(parameterName);
		if (parameter instanceof JpaParameter) {
			JpaParameter<?> logicParameter = (JpaParameter<?>) parameter;
			ParameterMode mode = logicParameter.getParameterMode();
			if (mode == ParameterMode.INOUT || mode == ParameterMode.OUT) {
				return getParameterValue(parameterName);
			}
		}
		throw new PersistenceException("The parameter '" + parameterName + "' is not register in some out mode");
	}

	public boolean execute() {

		//
		int parametersNumber = parameters.size();
		String[] arguments = new String[parametersNumber];
		for (int i = 0; i < parametersNumber; i++) {
			arguments[i] = parameters.get(i).getName();
		}

		// creating, setting up and executing internal query
		ProcedureQuery dbProcedureQuery = database.createProcedureQuery(procedureName, arguments);
		dbProcedureQuery.setFirstSolution(firstResult).setMaxSolution(maxResult).execute();
		hasMoreResult = dbProcedureQuery.hasNext();

		// setting parameters values
		for (int i = 0; i < parametersNumber; i++) {
			String parameterName = parameters.get(i).getName();
			Parameter<?> parameter = getParameter(parameterName);
			if (parameter instanceof JpaParameter) {
				Object parameterValue = dbProcedureQuery.getArgumentValue(parameterName);
				JpaParameter<?> logicParameter = (JpaParameter<?>) parameter;
				logicParameter.setValue(parameterValue);
			}
		}

		// adding in tuple list all results
		for (Iterator<?> e = dbProcedureQuery; e.hasNext();) {
			Object result = e.next();
			if (result instanceof Object[]) {
				Object[] objects = (Object[]) result;
				JpaTuple tuple = new JpaTuple(objects.length);

				// loop to fill each result tuple
				for (int i = 0; i < objects.length; i++) {
					Parameter<?> parameter = parameters.get(i);
					if (parameter instanceof JpaParameter) {
						JpaParameter<?> logicParameter = (JpaParameter<?>) parameter;
						ParameterMode mode = logicParameter.getParameterMode();
						if (mode == ParameterMode.INOUT || mode == ParameterMode.OUT) {
							String parameterName = logicParameter.getName();
							Object parameterValue = dbProcedureQuery.getArgumentValue(parameterName);
							JpaTupleElement<?> element = new JpaTupleElement(parameterName, Object.class,
									parameterValue);
							tuple.add(element);
						}
					}
				}
				tuples.add(tuple);
			}
		}

		return hasMoreResult;
	}

	public boolean hasMoreResults() {
		return hasMoreResult;
	}

	public int getUpdateCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public <T> StoredProcedureQuery setParameter(Parameter<T> param, T value) {
		if (param instanceof JpaParameter) {
			JpaParameter<T> parameter = (JpaParameter<T>) param;
			parameter.setValue(value);
			parameters.add(param);
		}
		return this;
	}

	public StoredProcedureQuery setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType) {
		throw new UnsupportedOperationException("setParameter(Parameter<Calendar>, Calendar, TemporalType");
	}

	public StoredProcedureQuery setParameter(Parameter<Date> param, Date value, TemporalType temporalType) {
		throw new UnsupportedOperationException("setParameter(Parameter<Date>, Date, TemporalType");
	}

	public StoredProcedureQuery setParameter(String name, Object value) {
		Class<?> valueType = value != null ? value.getClass() : null;
		parameters.add(new JpaParameter(name, parameterPosition++, valueType, value));
		return this;
	}

	public StoredProcedureQuery setParameter(String name, Calendar value, TemporalType temporalType) {
		throw new UnsupportedOperationException("setParameter(String, Calendar, TemporalType");
	}

	public StoredProcedureQuery setParameter(String name, Date value, TemporalType temporalType) {
		throw new UnsupportedOperationException("setParameter(String, Date, TemporalType");
	}

	public StoredProcedureQuery setParameter(int position, Object value) {
		String name = WILD_CARD + String.valueOf(position);
		Class<?> valueType = value != null ? value.getClass() : null;
		parameters.add(new JpaParameter(name, position, valueType));
		return this;
	}

	public StoredProcedureQuery setParameter(int position, Calendar value, TemporalType temporalType) {
		throw new UnsupportedOperationException("setParameter(int, Calendar, TemporalType");
	}

	public StoredProcedureQuery setParameter(int position, Date value, TemporalType temporalType) {
		throw new UnsupportedOperationException("setParameter(int, Date, TemporalType)");
	}

	public StoredProcedureQuery setHint(String hintName, Object value) {
		hints.put(hintName, value);
		return this;
	}

	public StoredProcedureQuery setFlushMode(FlushModeType flushMode) {
		this.flushMode = flushMode;
		return this;
	}

}

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

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import io.github.prolobjectlink.db.DatabaseEngine;
import io.github.prolobjectlink.db.ObjectConverter;
import io.github.prolobjectlink.db.util.JavaReflect;
import io.github.prolobjectlink.prolog.PrologTerm;

public final class JpaNativeQuery extends JpaQuery implements Query {

	private JpaResultSetMapping resultSetMapping;

	// TODO OPTIMIZE SAME CODE BLOCKS

	public JpaNativeQuery(DatabaseEngine database, String qlString) {
		super(database, qlString);
		ObjectConverter<PrologTerm> converter = database.getConverter();
		PrologTerm[] prologTerms = converter.toTermsArray(qlString);
		List<Class<?>> classes = database.classesOf(prologTerms);
		List<Object> solutions = database.solutionsOf(prologTerms, classes);
		for (Iterator<?> i = solutions.iterator(); i.hasNext();) {
			Object result = i.next();
			if (result instanceof Object[]) {
				Object[] objects = (Object[]) result;
				JpaTuple tuple = new JpaTuple(objects.length);
				for (int j = 0; j < objects.length; j++) {
					Class<?> clazz = classes.get(j);
					Object object = objects[j];
					Field[] fields = clazz.getDeclaredFields();
					for (int k = 0; k < fields.length; k++) {
						Class<?> javaType = fields[k].getType();
						String alias = fields[k].getName();
						Object value = JavaReflect.readValue(fields[k], object);
						tuple.add(new JpaTupleElement<Object>(alias, javaType, value));
					}
				}
				tuples.add(tuple);
			}
		}

	}

	public JpaNativeQuery(DatabaseEngine database, String qlString, Class<?> resultClass) {
		super(database, qlString, resultClass);
		ObjectConverter<PrologTerm> converter = database.getConverter();
		PrologTerm[] prologTerms = converter.toTermsArray(qlString);
		List<Class<?>> classes = database.classesOf(prologTerms);
		List<Object> solutions = database.solutionsOf(prologTerms, classes);
		for (Iterator<?> i = solutions.iterator(); i.hasNext();) {
			Object result = i.next();
			Object[] objects = (Object[]) result;
			JpaTuple tuple = new JpaTuple(objects.length);
			for (int j = 0; j < objects.length; j++) {
				if (resultClass.isInstance(objects[j])) {
					Object object = objects[j];
					tuple.add(new JpaTupleElement<Object>("", resultClass, object));
				}
			}
			tuples.add(tuple);
		}

	}

	public JpaNativeQuery(DatabaseEngine database, String qlString, JpaResultSetMapping resultSetMapping) {
		super(database, qlString);
		this.resultSetMapping = resultSetMapping;
		ObjectConverter<PrologTerm> converter = database.getConverter();
		PrologTerm[] prologTerms = converter.toTermsArray(qlString);
		List<Class<?>> classes = database.classesOf(prologTerms);
		List<Object> solutions = database.solutionsOf(prologTerms, classes);
		for (Iterator<?> i = solutions.iterator(); i.hasNext();) {
			Object result = i.next();
			Object[] objects = (Object[]) result;
			JpaTuple tuple = new JpaTuple(objects.length);
			for (int j = 0; j < objects.length; j++) {
				System.out.println(Arrays.toString(objects));
			}
			tuples.add(tuple);
		}

	}

}

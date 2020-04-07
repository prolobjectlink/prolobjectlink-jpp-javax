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

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;

public final class JpaTuple extends AbstractCollection<TupleElement<?>> implements Tuple, Collection<TupleElement<?>> {

	private final List<TupleElement<?>> elements;

	public JpaTuple() {
		this(new ArrayList<TupleElement<?>>());
	}

	public JpaTuple(int length) {
		this(new ArrayList<TupleElement<?>>(length));
	}

	public JpaTuple(List<TupleElement<?>> elements) {
		this.elements = elements;
	}

	@Override
	public boolean add(TupleElement<?> e) {
		return elements.add(e);
	}

	public <X> X get(TupleElement<X> tupleElement) {
		if (tupleElement != null) {
			for (TupleElement<?> element : elements) {
				if (element.equals(tupleElement) && element instanceof JpaTupleElement) {
					JpaTupleElement<X> logicTupleElement = (JpaTupleElement<X>) element;
					return logicTupleElement.getValue();
				}
			}
		}
		return null;
	}

	public <X> X get(String alias, Class<X> type) {
		return get(new JpaTupleElement<X>(alias, type));
	}

	public Object get(String alias) {
		if (alias != null) {
			for (TupleElement<?> element : elements) {
				if (element.getAlias().equals(alias) && element instanceof JpaTupleElement) {
					JpaTupleElement<?> logicTupleElement = (JpaTupleElement<?>) element;
					return logicTupleElement.getValue();
				}
			}
		}
		return null;
	}

	public <X> X get(int i, Class<X> type) {
		if (i >= 0 && i < elements.size()) {
			TupleElement<?> tupleElement = elements.get(i);
			if (tupleElement.getJavaType() == type) {
				return (X) get(tupleElement);
			}
			throw new IllegalArgumentException();
		}
		throw new IllegalArgumentException();
	}

	public Object get(int i) {
		if (i >= 0 && i < elements.size()) {
			TupleElement<?> tupleElement = elements.get(i);
			return get(tupleElement);
		}
		throw new IllegalArgumentException();
	}

	public Object[] toArray() {
		int size = elements.size();
		Object[] values = new Object[size];
		for (int i = 0; i < values.length; i++) {
			TupleElement<?> tupleElement = elements.get(i);
			if (tupleElement instanceof JpaTupleElement) {
				JpaTupleElement<?> logicTupleElement = (JpaTupleElement<?>) tupleElement;
				values[i] = logicTupleElement.getValue();
			}
		}
		return values;
	}

	public List<TupleElement<?>> getElements() {
		return elements;
	}

	@Override
	public Iterator<TupleElement<?>> iterator() {
		return elements.iterator();
	}

	@Override
	public int size() {
		return elements.size();
	}

}

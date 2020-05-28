/*-
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2020 Prolobjectlink Project
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
package io.github.prolobjectlink.web.introspection;

import io.github.prolobjectlink.prolog.AbstractProvider;
import io.github.prolobjectlink.prolog.PrologAtom;
import io.github.prolobjectlink.prolog.PrologDouble;
import io.github.prolobjectlink.prolog.PrologEngine;
import io.github.prolobjectlink.prolog.PrologFloat;
import io.github.prolobjectlink.prolog.PrologInteger;
import io.github.prolobjectlink.prolog.PrologJavaConverter;
import io.github.prolobjectlink.prolog.PrologList;
import io.github.prolobjectlink.prolog.PrologLogger;
import io.github.prolobjectlink.prolog.PrologLong;
import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.PrologStructure;
import io.github.prolobjectlink.prolog.PrologTerm;
import io.github.prolobjectlink.prolog.PrologVariable;
import io.github.prolobjectlink.prolog.jtrolog.JTrolog;
import io.github.prolobjectlink.web.prolog.PrologWebEngine;
import io.github.prolobjectlink.web.prolog.PrologWebLogger;
import io.github.prolobjectlink.web.prolog.PrologWebProvider;

public class IntrospectionProvider extends AbstractProvider implements PrologWebProvider {

	private final PrologProvider provider = new JTrolog();

	public IntrospectionProvider() {
		super(new JTrolog().getConverter());
	}

	@Override
	public PrologTerm parseTerm(String term) {
		return provider.parseTerm(term);
	}

	@Override
	public PrologTerm[] parseTerms(String stringTerms) {
		return provider.parseTerms(stringTerms);
	}

	@Override
	public boolean isCompliant() {
		return provider.isCompliant();
	}

	@Override
	public PrologTerm prologNil() {
		return provider.prologNil();
	}

	@Override
	public PrologTerm prologCut() {
		return provider.prologCut();
	}

	@Override
	public PrologTerm prologFail() {
		return provider.prologFail();
	}

	@Override
	public PrologTerm prologTrue() {
		return provider.prologTrue();
	}

	@Override
	public PrologTerm prologFalse() {
		return prologFalse();
	}

	@Override
	public PrologTerm prologEmpty() {
		return provider.prologEmpty();
	}

	@Override
	public PrologTerm prologInclude(String file) {
		return provider.prologInclude(file);
	}

	@Override
	public PrologWebEngine newEngine() {
		PrologEngine e = provider.newEngine();
		return new IntrospectionEngine(e);
	}

	@Override
	public PrologWebEngine newEngine(String file) {
		PrologWebEngine engine = newEngine();
		engine.consult(file);
		return engine;
	}

	@Override
	public PrologAtom newAtom(String functor) {
		return provider.newAtom(functor);
	}

	@Override
	public PrologFloat newFloat(Number value) {
		return provider.newFloat(value);
	}

	@Override
	public PrologDouble newDouble(Number value) {
		return provider.newDouble(value);
	}

	@Override
	public PrologInteger newInteger(Number value) {
		return provider.newInteger(value);
	}

	@Override
	public PrologLong newLong(Number value) {
		return provider.newLong(value);
	}

	@Override
	public PrologVariable newVariable(int position) {
		return provider.newVariable(position);
	}

	@Override
	public PrologVariable newVariable(String name, int position) {
		return provider.newVariable(name, position);
	}

	@Override
	public PrologList newList() {
		return provider.newList();
	}

	@Override
	public PrologList newList(PrologTerm[] arguments) {
		return provider.newList(arguments);
	}

	@Override
	public PrologList newList(PrologTerm head, PrologTerm tail) {
		return provider.newList(head, tail);
	}

	@Override
	public PrologList newList(PrologTerm[] arguments, PrologTerm tail) {
		return provider.newList(arguments, tail);
	}

	@Override
	public PrologStructure newStructure(String functor, PrologTerm... arguments) {
		return provider.newStructure(functor, arguments);
	}

	@Override
	public PrologTerm newStructure(PrologTerm left, String operator, PrologTerm right) {
		return provider.newStructure(left, operator, right);
	}

	@Override
	public PrologTerm newReference(Object object) {
		return provider.newReference(object);
	}

	@Override
	public PrologJavaConverter getJavaConverter() {
		return provider.getJavaConverter();
	}

	@Override
	public PrologLogger getLogger() {
		return new PrologWebLogger();
	}

	@Override
	public String toString() {
		return provider.toString();
	}

	@Override
	public int hashCode() {
		return provider.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return provider.equals(obj);
	}

}

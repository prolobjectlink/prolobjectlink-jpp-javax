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

import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import io.github.prolobjectlink.db.prolog.PrologProgrammer;
import io.github.prolobjectlink.prolog.AbstractEngine;
import io.github.prolobjectlink.prolog.PrologClause;
import io.github.prolobjectlink.prolog.PrologEngine;
import io.github.prolobjectlink.prolog.PrologIndicator;
import io.github.prolobjectlink.prolog.PrologJavaConverter;
import io.github.prolobjectlink.prolog.PrologOperator;
import io.github.prolobjectlink.prolog.PrologQuery;
import io.github.prolobjectlink.prolog.PrologTerm;
import io.github.prolobjectlink.web.prolog.PrologWebEngine;

public class IntrospectionEngine extends AbstractEngine implements PrologWebEngine {

	private final PrologEngine engine;

	IntrospectionEngine(PrologEngine engine) {
		super(engine.getProvider());
		this.engine = engine;
	}

	@Override
	public List<String> verify() {
		return engine.verify();
	}

	@Override
	public void consult(String path) {
		engine.consult(path);
	}

	@Override
	public void consult(Reader reader) {
		engine.consult(reader);
	}

	@Override
	public void include(String path) {
		engine.include(path);
	}

	@Override
	public void include(Reader reader) {
		engine.include(reader);
	}

	@Override
	public void persist(String path) {
		engine.persist(path);
	}

	@Override
	public void abolish(String functor, int arity) {
		engine.abolish(functor, arity);
	}

	@Override
	public void asserta(String stringClause) {
		engine.asserta(stringClause);
	}

	@Override
	public void asserta(PrologTerm head, PrologTerm... body) {
		engine.asserta(head, body);
	}

	@Override
	public void assertz(String stringClause) {
		engine.assertz(stringClause);
	}

	@Override
	public void assertz(PrologTerm head, PrologTerm... body) {
		engine.assertz(head, body);
	}

	@Override
	public boolean clause(String stringClause) {
		return engine.clause(stringClause);
	}

	@Override
	public boolean clause(PrologTerm head, PrologTerm... body) {
		return engine.clause(head, body);
	}

	@Override
	public void retract(String stringClause) {
		engine.retract(stringClause);
	}

	@Override
	public void retract(PrologTerm head, PrologTerm... body) {
		engine.retract(head, body);
	}

	@Override
	public PrologQuery query(String query) {
		return engine.query(query);
	}

	@Override
	public PrologQuery query(PrologTerm[] terms) {
		return engine.query(terms);
	}

	@Override
	public PrologQuery query(PrologTerm term, PrologTerm... terms) {
		return engine.query(term, terms);
	}

	@Override
	public void operator(int priority, String specifier, String operator) {
		engine.operator(priority, specifier, operator);
	}

	@Override
	public boolean currentPredicate(String functor, int arity) {
		return engine.currentPredicate(functor, arity);
	}

	@Override
	public boolean currentOperator(int priority, String specifier, String operator) {
		return engine.currentOperator(priority, specifier, operator);
	}

	@Override
	public Set<PrologOperator> currentOperators() {
		return engine.currentOperators();
	}

	@Override
	public int getProgramSize() {
		return engine.getProgramSize();
	}

	@Override
	public Set<PrologIndicator> getPredicates() {
		return engine.getPredicates();
	}

	@Override
	public Set<PrologIndicator> getBuiltIns() {
		return engine.getBuiltIns();
	}

	@Override
	public String getLicense() {
		return engine.getLicense();
	}

	@Override
	public String getVersion() {
		return engine.getVersion();
	}

	@Override
	public String getName() {
		return engine.getName();
	}

	@Override
	public void dispose() {
		engine.dispose();
	}

	@Override
	public Iterator<PrologClause> iterator() {
		return engine.iterator();
	}

	@Override
	public boolean unify(Object x, Object y) {
		PrologJavaConverter c = provider.getJavaConverter();
		PrologTerm xt = c.toTerm(x);
		PrologTerm yt = c.toTerm(y);
		return engine.unify(xt, yt);
	}

	@Override
	public PrologProgrammer getProgrammer() {
		return new IntrospectionProgrammer(provider);
	}

	@Override
	public int hashCode() {
		return engine.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return engine.equals(obj);
	}

}

/*-
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
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
package org.prolobjectlink.web.application;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.prolobjectlink.prolog.PrologList;
import org.prolobjectlink.prolog.PrologTerm;
import org.prolobjectlink.prolog.PrologVariable;

import io.marioslab.basis.template.Template;
import io.marioslab.basis.template.TemplateContext;
import io.marioslab.basis.template.TemplateLoader;
import io.marioslab.basis.template.TemplateLoader.FileTemplateLoader;

public class TemplateEngine implements Map<String, Object> {

	private final TemplateLoader loader = new FileTemplateLoader();
	private final TemplateContext context = new TemplateContext();
	private OutputStream stream;


	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean containsKey(Object arg0) {
		return context.get(arg0.toString()) != null;
	}

	@Override
	public boolean containsValue(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(Object arg0) {
		return context.get(arg0.toString());
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object put(String arg0, Object arg1) {
		return context.set(arg0, arg1);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> arg0) {
		for (Entry<? extends String, ? extends Object> entry : arg0.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public Object remove(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<Object> values() {
		// TODO Auto-generated method stub
		return null;
	}

	public OutputStream getStream() {
		return stream;
	}

	public void setStream(OutputStream stream) {
		this.stream = stream;
	}

	public TemplateLoader getLoader() {
		return loader;
	}

	public TemplateContext getContext() {
		return context;
	}

	public void render(String view, PrologList variables) {
		Template template = loader.load(view);
		for (PrologTerm term : variables) {
			if (term instanceof PrologVariable) {
				PrologVariable v = (PrologVariable) term;
				context.set(v.getName(), v.getTerm());
			}
		}
		template.render(context, stream);
	}

	public void render(String view, PrologVariable v) {
		Template template = loader.load(view);
		context.set(v.getName(), v.getTerm());
		template.render(context, stream);
	}

	public void render(String view) {
		Template template = loader.load(view);
		template.render(context, stream);
	}

}

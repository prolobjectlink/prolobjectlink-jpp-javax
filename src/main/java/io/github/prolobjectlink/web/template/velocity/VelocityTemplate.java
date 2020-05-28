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
package io.github.prolobjectlink.web.template.velocity;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import io.github.prolobjectlink.web.template.Template;
import io.github.prolobjectlink.web.template.base.AbstractTemplate;

public class VelocityTemplate extends AbstractTemplate implements Template {

	private final org.apache.velocity.Template template;
	private final org.apache.velocity.VelocityContext context;

	public VelocityTemplate(org.apache.velocity.Template template) {
		this.context = new org.apache.velocity.VelocityContext();
		this.template = template;
	}

	@Override
	public void render(OutputStream outputStream) {
		PrintWriter writer = new PrintWriter(outputStream);
		template.merge(context, writer);
	}

	@Override
	public Object getVariableValue(String variable) {
		return context.get(variable);
	}

	@Override
	public Set<String> getVariables() {
		Set<String> set = new HashSet<String>();
		Object[] key = context.getKeys();
		for (Object object : key) {
			set.add(object.toString());
		}
		return set;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		result = prime * result + ((template == null) ? 0 : template.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		VelocityTemplate other = (VelocityTemplate) obj;
		if (context == null) {
			if (other.context != null)
				return false;
		} else if (!context.equals(other.context)) {
			return false;
		}
		if (template == null) {
			if (other.template != null)
				return false;
		} else if (!template.equals(other.template)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "VelocityTemplate [template=" + template + ", context=" + context + "]";
	}

}

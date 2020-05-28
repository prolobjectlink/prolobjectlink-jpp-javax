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
package io.github.prolobjectlink.web.template.basis;

import java.io.OutputStream;
import java.util.Set;

import io.github.prolobjectlink.web.template.Template;
import io.github.prolobjectlink.web.template.base.AbstractTemplate;

public class BasisTemplate extends AbstractTemplate implements Template {

	private final io.marioslab.basis.template.Template template;
	private final io.marioslab.basis.template.TemplateContext context;

	public BasisTemplate(io.marioslab.basis.template.Template template) {
		this.context = new io.marioslab.basis.template.TemplateContext();
		this.template = template;
	}

	@Override
	public void render(OutputStream outputStream) {
		template.render(context, outputStream);
	}

	@Override
	public Object getVariableValue(String variable) {
		return context.get(variable);
	}

	@Override
	public Set<String> getVariables() {
		return context.getVariables();
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
		BasisTemplate other = (BasisTemplate) obj;
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
		return "BasisTemplate [template=" + template + ", context=" + context + "]";
	}

}

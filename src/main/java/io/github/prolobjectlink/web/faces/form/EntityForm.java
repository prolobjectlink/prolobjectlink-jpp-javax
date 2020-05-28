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
package io.github.prolobjectlink.web.faces.form;

import io.github.prolobjectlink.web.faces.FacesForm;
import io.github.prolobjectlink.web.faces.common.AbstractFacesForm;

public class EntityForm extends AbstractFacesForm implements FacesForm {

	public EntityForm(String name, String action) {
		super(name, name, action, POST, "", "", "");
	}

	public EntityForm(String name, String action, String method) {
		super(name, name, action, method, "", "", "");
	}

	public EntityForm(String name, String action, String method, String enctype) {
		super(name, name, action, method, enctype, "", "");
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("<form id=\"" + getId() + "\" action=\"{{ path(\"" + getAction() + "\") }}\" method=\""
				+ getMethod() + "\">");
		result.append('\n');
		result.append("<table>");
		result.append('\n');
		result.append('\t');

		i = getInputs().entrySet().iterator();
		if (i.hasNext()) {
			Entry<?, ?> entry = i.next();
			result.append(entry.getValue());
			while (i.hasNext()) {
				entry = i.next();
				result.append('\n');
				result.append(entry.getValue());
			}
		}

		result.append('\n');
		result.append("</table>");
		result.append('\n');

		i = getButtons().entrySet().iterator();
		if (i.hasNext()) {
			Entry<?, ?> entry = i.next();
			result.append(entry.getValue());
			while (i.hasNext()) {
				entry = i.next();
				result.append('\n');
				result.append(entry.getValue());
			}
		}

		result.append('\n');
		result.append("</form>");
		return "" + result + "";
	}

}

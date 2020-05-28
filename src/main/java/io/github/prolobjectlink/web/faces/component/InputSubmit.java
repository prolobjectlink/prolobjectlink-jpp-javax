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
package io.github.prolobjectlink.web.faces.component;

import javax.faces.component.html.HtmlCommandButton;

import io.github.prolobjectlink.web.faces.FacesComponent;

public class InputSubmit extends HtmlCommandButton implements FacesComponent {

	public static final String SUBMIT = "submit";

	public InputSubmit(String name) {
		setId(name);
		setTitle(name);
		setValue(name);
	}

	@Override
	public String toString() {
		return "<" + INPUT + " type=\"" + SUBMIT + "\" id=\"" + getId() + "\" value=\"" + getValue() + "\" />";
	}

}
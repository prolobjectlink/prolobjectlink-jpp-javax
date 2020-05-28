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
package io.github.prolobjectlink.web.faces.common;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import io.github.prolobjectlink.web.faces.FacesComponent;
import io.github.prolobjectlink.web.faces.FacesForm;

public abstract class AbstractFacesForm extends AbstractMap<Object, FacesComponent> implements FacesForm {

	private final String id;
	private final String name;
	private final String action;
	private final String method;
	private final String enctype;
	private final String onreset;
	private final String onsubmit;

	private final Map<Object, FacesComponent> inputs;
	private final Map<Object, FacesComponent> buttons;

	protected Iterator<Entry<Object, FacesComponent>> i;

	protected AbstractFacesForm(String id, String name, String action, String method, String enctype, String onreset,
			String onsubmit) {
		this.id = id;
		this.name = name;
		this.action = action;
		this.method = method;
		this.enctype = enctype;
		this.onreset = onreset;
		this.onsubmit = onsubmit;
		inputs = new LinkedHashMap<Object, FacesComponent>();
		buttons = new LinkedHashMap<Object, FacesComponent>();
	}

	public final void putInput(Object arg0, FacesComponent arg1) {
		inputs.put(arg0, arg1);
	}

	public final void putButton(Object arg0, FacesComponent arg1) {
		buttons.put(arg0, arg1);
	}

	@Override
	public final Set<Entry<Object, FacesComponent>> entrySet() {
		Map<Object, FacesComponent> m = new LinkedHashMap<Object, FacesComponent>();
		m.putAll(inputs);
		m.putAll(buttons);
		return m.entrySet();
	}

	@Override
	public final Map<Object, FacesComponent> getInputs() {
		return inputs;
	}

	@Override
	public final Map<Object, FacesComponent> getButtons() {
		return buttons;
	}

	@Override
	public final String getId() {
		return id;
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public final String getAction() {
		return action;
	}

	@Override
	public final String getMethod() {
		return method;
	}

	@Override
	public final String getEnctype() {
		return enctype;
	}

	@Override
	public final String getOnreset() {
		return onreset;
	}

	@Override
	public final String getOnsubmit() {
		return onsubmit;
	}

	@Override
	public final String evaluate() {
		return "" + this + "";
	}

	@Override
	public abstract String toString();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((buttons == null) ? 0 : buttons.hashCode());
		result = prime * result + ((enctype == null) ? 0 : enctype.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inputs == null) ? 0 : inputs.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((onreset == null) ? 0 : onreset.hashCode());
		result = prime * result + ((onsubmit == null) ? 0 : onsubmit.hashCode());
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
		AbstractFacesForm other = (AbstractFacesForm) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (buttons == null) {
			if (other.buttons != null)
				return false;
		} else if (!buttons.equals(other.buttons))
			return false;
		if (enctype == null) {
			if (other.enctype != null)
				return false;
		} else if (!enctype.equals(other.enctype))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inputs == null) {
			if (other.inputs != null)
				return false;
		} else if (!inputs.equals(other.inputs))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (onreset == null) {
			if (other.onreset != null)
				return false;
		} else if (!onreset.equals(other.onreset))
			return false;
		if (onsubmit == null) {
			if (other.onsubmit != null)
				return false;
		} else if (!onsubmit.equals(other.onsubmit))
			return false;
		return true;
	}

}

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

import java.util.Iterator;
import java.util.List;

import io.github.prolobjectlink.web.faces.FacesComponent;
import io.github.prolobjectlink.web.faces.FacesPage;

public abstract class AbstractFacesPage implements FacesPage {

	private String language;
	private Object head;
	private Object body;

	//
	private List<FacesComponent> modals;
	private List<FacesComponent> dialogs;

	public AbstractFacesPage() {
		language = "en";
	}

	public AbstractFacesPage(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Object getHead() {
		return head;
	}

	public void setHead(Object head) {
		this.head = head;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public List<FacesComponent> getModals() {
		return modals;
	}

	public void setModals(List<FacesComponent> modals) {
		this.modals = modals;
	}

	public List<FacesComponent> getDialogs() {
		return dialogs;
	}

	public void setDialogs(List<FacesComponent> dialogs) {
		this.dialogs = dialogs;
	}

	public void addModals(FacesComponent modal) {
		modals.add(modal);
	}

	public void removeModals(FacesComponent modal) {
		modals.remove(modal);
	}

	public void addDialog(FacesComponent dialog) {
		modals.add(dialog);
	}

	public void removeDialog(FacesComponent dialog) {
		modals.remove(dialog);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((dialogs == null) ? 0 : dialogs.hashCode());
		result = prime * result + ((head == null) ? 0 : head.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((modals == null) ? 0 : modals.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractFacesPage other = (AbstractFacesPage) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (dialogs == null) {
			if (other.dialogs != null)
				return false;
		} else if (!dialogs.equals(other.dialogs))
			return false;
		if (head == null) {
			if (other.head != null)
				return false;
		} else if (!head.equals(other.head))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (modals == null) {
			if (other.modals != null)
				return false;
		} else if (!modals.equals(other.modals))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("<!DOCTYPE html>");
		result.append("<html lang=\"" + language + "\">");
		result.append(head);
		result.append('\n');
		result.append(head);
		result.append('\n');

		Iterator<FacesComponent> i = modals.iterator();
		if (i.hasNext()) {
			FacesComponent component = i.next();
			result.append(component);
			while (i.hasNext()) {
				component = i.next();
				result.append('\n');
				result.append(component);
			}
		}

		result.append('\n');

		i = dialogs.iterator();
		if (i.hasNext()) {
			FacesComponent component = i.next();
			result.append(component);
			while (i.hasNext()) {
				component = i.next();
				result.append('\n');
				result.append(component);
			}
		}

		return result.toString();
	}

}

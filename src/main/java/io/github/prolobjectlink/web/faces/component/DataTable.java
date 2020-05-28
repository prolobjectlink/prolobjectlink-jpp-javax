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

import java.util.List;

import javax.faces.component.html.HtmlDataTable;

import io.github.prolobjectlink.web.faces.FacesComponent;

public class DataTable extends HtmlDataTable implements FacesComponent {

	private final String[] headers;
	private final List<?> data;

	private DataTable(String name, String[] header, List<?> data) {
		this.headers = header;
		this.data = data;
		setTitle(name);
	}

	public String[] getHeaders() {
		return headers;
	}

	public List<?> getData() {
		return data;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("<h1 class=\"h3 mb-2 text-gray-800\">" + getTitle() + "</h1>");
		b.append("<div class=\"card shadow mb-4\">");
		b.append("<div class=\"card-body\">");
		b.append("<div class=\"table-responsive\">");
		b.append("<table class=\"table table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
		b.append("<thead>");
		b.append("<tr>");
		for (String string : headers) {
			b.append("<th>" + string + "</th>");
		}
		b.append("</tr>");
		b.append("</thead>");
		b.append("<tfoot>");
		b.append("<tr>");
		for (String string : headers) {
			b.append("<th>" + string + "</th>");
		}
		b.append("</tr>");
		b.append("/tfoot");
		b.append("<tbody>");

		// data go here

		b.append("</tbody>");
		b.append("</table>");
		b.append("</div>");
		b.append("</div>");
		b.append("</div>");
		return b.toString();
	}

}

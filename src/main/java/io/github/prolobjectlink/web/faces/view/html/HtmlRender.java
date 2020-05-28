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
package io.github.prolobjectlink.web.faces.view.html;

import java.rmi.UnexpectedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.prolobjectlink.web.faces.view.ViewResult;

public final class HtmlRender extends ViewResult {

	private static final long serialVersionUID = -8896777917855638357L;
	private final String html;

	public HtmlRender(String arg0) {
		this.html = arg0;
	}

	public HtmlRender(CharSequence html) {
		this.html = html.toString();
	}

	@Override
	public final void apply(HttpServletRequest request, HttpServletResponse response) throws UnexpectedException {
		try {
			response.setContentType("text/html");
			response.getOutputStream().write(html.getBytes("utf-8"));
		} catch (Exception e) {
			throw new UnexpectedException(e.getMessage());
		}
	}

}

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
package io.github.prolobjectlink.web.faces.view;

import java.io.File;
import java.io.IOException;

import io.github.prolobjectlink.prolog.PrologVariable;
import io.github.prolobjectlink.web.platform.AbstractWebPlatform;

public class ViewReader extends AbstractWebPlatform {

	protected static final String SLASH = File.separator;
	protected final String viewDirectory;
	protected final String application;
	protected final String view;

	private String resolveViewRuntimeObjectLocation(Object view) {
		if (view instanceof PrologVariable) {
			PrologVariable var = (PrologVariable) view;
			if (var.getTerm() != var && !var.getTerm().equals(var)) {
				System.out.println(var.getFunctor());
				return var.getFunctor();
			}
		}
		return view.toString();
	}

	public ViewReader(String application, Object view) throws IOException {
		String viewPath = resolveViewRuntimeObjectLocation(view);
		String webdir = getWebDirectory().getCanonicalPath();
		String applicationdir = webdir + File.separator + application;
		this.viewDirectory = applicationdir + File.separator + view;
		this.application = application;
		this.view = viewPath;
	}

	public void render(String application, Object view) {
		String v = resolveViewRuntimeObjectLocation(view);
		ViewRender.cache.put(application + SLASH + v, v);
	}

	public void render(Object view) {
		String v = resolveViewRuntimeObjectLocation(view);
		ViewRender.cache.put(application + SLASH + v, v);
	}

	public void render() {
		ViewRender.cache.put(application + SLASH + view, view);
	}

}

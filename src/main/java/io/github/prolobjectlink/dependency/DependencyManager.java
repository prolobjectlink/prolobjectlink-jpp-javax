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
package io.github.prolobjectlink.dependency;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.ivy.Ivy;
import org.apache.ivy.core.report.ArtifactDownloadReport;
import org.apache.ivy.core.report.ResolveReport;

import io.github.prolobjectlink.Platform;

public interface DependencyManager extends Platform {

	public Ivy configure() throws Exception;

	public ResolveReport resolve() throws Exception;

	public File install(ArtifactDownloadReport artifact) throws Exception;

	public List<File> retrieve(ResolveReport report) throws Exception;

	public Set<String> retrieveModules() throws Exception;

	public boolean problems() throws Exception;

	public void sync(List<File> installed) throws IOException;

	public void report() throws Exception;

}

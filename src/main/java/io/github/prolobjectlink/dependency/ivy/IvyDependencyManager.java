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
package io.github.prolobjectlink.dependency.ivy;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.ivy.Ivy;
import org.apache.ivy.core.report.ArtifactDownloadReport;
import org.apache.ivy.core.report.ResolveReport;

import io.github.prolobjectlink.dependency.BaseDependencyManager;
import io.github.prolobjectlink.dependency.DependencyManager;

public class IvyDependencyManager extends BaseDependencyManager implements DependencyManager {

	public IvyDependencyManager(File application, File framework, File userHome) {
		super(application, framework, userHome);
	}

	public static void main(String[] args) throws Exception {
		String applicationPath = System.getProperty("application.path");
		if (applicationPath == null) {
			System.out.println("~ ERROR: cannot resolve \"application.path\"");
			return;
		}
		String frameworkPath = System.getProperty("framework.path");
		if (frameworkPath == null) {
			System.out.println("~ ERROR: cannot resolve \"framework.path\"");
			return;
		}
		String userHomePath = System.getProperty("user.home");
		if (userHomePath == null) {
			System.out.println("~ ERROR: cannot resolve \"user.home\"");
			return;
		}
		String playVersion = System.getProperty("play.version");
		if (playVersion == null) {
			System.out.println("~ ERROR: cannot resolve \"play.version\"");
			return;
		}

		// Paths
		File application = new File(applicationPath);
		File framework = new File(frameworkPath);
		File userHome = new File(userHomePath);

		DependencyManager deps = new BaseDependencyManager(application, framework, userHome);

		ResolveReport report = deps.resolve();
		if (report != null) {
			deps.report();
			List<File> installed = deps.retrieve(report);
			deps.sync(installed);
		}

		if (deps.problems()) {
			System.out.println("~");
			System.out.println("~ Some dependencies are still missing.");
			System.out.println("~");
		} else {
			System.out.println("~");
			System.out.println("~ Done!");
			System.out.println("~");
		}
	}

	@Override
	public Ivy configure() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResolveReport resolve() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File install(ArtifactDownloadReport artifact) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<File> retrieve(ResolveReport report) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> retrieveModules() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean problems() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sync(List<File> installed) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void report() {
		// TODO Auto-generated method stub
		
	}

}

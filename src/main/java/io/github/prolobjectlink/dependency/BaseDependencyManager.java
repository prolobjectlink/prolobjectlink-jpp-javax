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
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.ivy.Ivy;
import org.apache.ivy.core.module.id.ModuleRevisionId;
import org.apache.ivy.core.report.ArtifactDownloadReport;
import org.apache.ivy.core.report.ResolveReport;
import org.apache.ivy.core.resolve.IvyNode;
import org.apache.ivy.core.resolve.ResolveEngine;
import org.apache.ivy.core.resolve.ResolveOptions;
import org.apache.ivy.core.settings.IvySettings;
import org.apache.ivy.util.DefaultMessageLogger;
import org.apache.ivy.util.Message;
import org.apache.ivy.util.filter.FilterHelper;

import io.github.prolobjectlink.web.platform.AbstractWebPlatform;

public class BaseDependencyManager extends AbstractWebPlatform implements DependencyManager {

	private File application;
	private File framework;
	private File userHome;

	final FileFilter dirsToTrim = new FileFilter() {

		@Override
		public boolean accept(File file) {
			return file.isDirectory() && isDirToTrim(file.getName());
		}

		private boolean isDirToTrim(String fileName) {
			return "documentation".equals(fileName) || "src".equals(fileName) || "tmp".equals(fileName)
					|| fileName.contains("sample") || fileName.contains("test");
		}
	};

	public BaseDependencyManager(File application, File framework, File userHome) {
		this.application = application;
		this.framework = framework;
		this.userHome = userHome;
	}

	@Override
	public void report() throws Exception {
		System.out.println("~");
		System.out.println("~ Some dynamic revisions have been resolved as following,");
		System.out.println("~");

		System.out.println("~");
		System.out.println("~ Some dependencies have been evicted,");
		System.out.println("~");
	}

	@Override
	public void sync(List<File> installed) throws IOException {

		List<File> notSync = new ArrayList<>();

		File[] paths = new File[] { new File(application, "lib"), new File(application, "modules") };
		for (File path : paths) {
			if (path.exists()) {
				for (File f : path.listFiles()) {
					if (!installed.contains(f)) {
						notSync.add(f);
					}
				}
			}
		}

		boolean autoSync = System.getProperty("nosync") == null;

		if (autoSync && !notSync.isEmpty()) {
			System.out.println("~");
			System.out.println("~ Synchronizing, deleting unknown dependencies");
			System.out.println("~");
			for (File f : notSync) {
				Files.delete(f.toPath());
				System.out.println("~ \tDeleted: " + f.getAbsolutePath());
			}
			System.out.println("~");
		} else if (!notSync.isEmpty()) {
			System.out.println("~");
			System.out.println("~ *****************************************************************************");
			System.out.println(
					"~ WARNING: Your lib/ and modules/ directories are not synced with current dependencies (don't use --nosync to automatically delete them)");
			System.out.println("~");
			for (File f : notSync) {
				System.out.println("~ \tUnknown: " + f.getAbsolutePath());
			}
			System.out.println("~ *****************************************************************************");
		}
	}

	@Override
	public boolean problems() throws Exception {
		System.out.println("~");
		System.out.println("~ *****************************************************************************");
		System.out.println(
				"~ WARNING: These dependencies are missing, your application may not work properly (use --verbose for details),");
		System.out.println("~");
		System.out.println("~ *****************************************************************************");
		return true;
	}

	// Retrieve the list of modules in the order they were defined in the
	// dependencies.yml.
	@Override
	public Set<String> retrieveModules() throws Exception {
		File ivyModule = new File(application, "conf/dependencies.yml");
		if (ivyModule == null || !ivyModule.exists()) {
			return new LinkedHashSet<>();
		}

//		return YamlParser.getOrderedModuleList(ivyModule);
//		Here create a prolog parser for ivy dependency manager

		return null;
	}

	@Override
	public List<File> retrieve(ResolveReport report) throws Exception {
		// Track missing artifacts
		List<ArtifactDownloadReport> missing = new ArrayList<>();

		// Track deps with errors
		List<IvyNode> problems = new ArrayList<>();

		List<ArtifactDownloadReport> artifacts = new ArrayList<>();
		for (IvyNode node : ((List<IvyNode>) report.getDependencies())) {
			if (node.isLoaded() && !node.isCompletelyEvicted()) {
				ArtifactDownloadReport[] adr = report.getArtifactsReports(node.getResolvedId());
				for (ArtifactDownloadReport artifact : adr) {
					if (artifact.getLocalFile() == null) {
						missing.add(artifact);
					} else {
						if (isPlayModule(artifact) || !isFrameworkLocal(artifact)) {
							artifacts.add(artifact);

							// Save the order of module
							if (isPlayModule(artifact)) {
								String mName = artifact.getLocalFile().getName();
								if (mName.endsWith(".jar") || mName.endsWith(".zip")) {
									mName = mName.substring(0, mName.length() - 4);
								}
							}
						}
					}
				}
			} else if (node.hasProblem() && !node.isCompletelyEvicted()) {
				problems.add(node);
			}
		}

		// Create directory if not exist
		File modulesDir = new File(application, "modules");
		if (!modulesDir.exists()) {
			modulesDir.mkdir();
		}

		if (!missing.isEmpty() || !problems.isEmpty()) {
			System.out.println("~");
			System.out.println("~ WARNING: Some dependencies could not be downloaded (use --verbose for details),");
			System.out.println("~");
			for (ArtifactDownloadReport d : missing) {
				StringBuilder msg = new StringBuilder(d.getArtifact().getModuleRevisionId().getOrganisation())
						.append(" -> ").append(d.getArtifact().getModuleRevisionId().getName()).append(' ')
						.append(d.getArtifact().getModuleRevisionId().getRevision()).append(": ")
						.append(d.getDownloadDetails());
				System.out.println("~\t" + msg.toString());
			}
			if (!problems.isEmpty()) {
				for (IvyNode node : problems) {
					ModuleRevisionId moduleRevisionId = node.getId();

					StringBuilder msg = new StringBuilder(moduleRevisionId.getOrganisation()).append("->")
							.append(moduleRevisionId.getName()).append(' ').append(moduleRevisionId.getRevision())
							.append(": ").append(node.getProblemMessage());
					System.out.println("~\t" + msg.toString());
				}
			}
		}

		List<File> installed = new ArrayList<>();

		// Install
		if (artifacts.isEmpty()) {
			System.out.println("~");
			System.out.println("~ No dependencies to install");
		} else {
			System.out.println("~");
			System.out.println("~ Installing resolved dependencies,");
			System.out.println("~");

			for (ArtifactDownloadReport artifact : artifacts) {
				installed.add(install(artifact));
			}
		}

		return installed;
	}

	@Override
	public File install(ArtifactDownloadReport artifact) throws Exception {
		boolean force = "true".equalsIgnoreCase(System.getProperty("play.forcedeps"));
		boolean trim = "true".equalsIgnoreCase(System.getProperty("play.trimdeps"));
		boolean shortModuleNames = "true".equalsIgnoreCase(System.getProperty("play.shortModuleNames"));

		try {
			File from = artifact.getLocalFile();

			if (!isPlayModule(artifact)) {
				if ("source".equals(artifact.getArtifact().getType())) {
					// A source artifact: leave it in the cache, and write its path in
					// tmp/lib-src/<jar-name>.src
					// so that it can be used later by commands generating IDE project fileS.
					new File(application, "tmp/lib-src").mkdirs();
					copy(new FileInputStream(from.getAbsolutePath()), new FileOutputStream(
							new File(application, "tmp/lib-src/" + from.getName().replace("-sources", "") + ".src")));
					return null;

				} else {
					// A regular library: copy it to the lib/ directory
					File to = new File(application, "lib" + File.separator + from.getName()).getCanonicalFile();
					new File(application, "lib").mkdir();
					Files.copy(from.toPath(), to.toPath());
					System.out.println("~ \tlib/" + to.getName());
					return to;
				}

			} else {
				// A module
				String mName = moduleName(artifact, shortModuleNames);
				File to = new File(application, "modules" + File.separator + mName).getCanonicalFile();
				new File(application, "modules").mkdir();
				Files.delete(to.toPath());
				if (from.isDirectory()) {
					if (force) {
//						TODO IO.copyDirectory(from, to);
					} else {
//						TODO IO.writeContent(from.getAbsolutePath(), to);
					}
					System.out.println("~ \tmodules/" + to.getName() + " -> " + from.getAbsolutePath());
				} else {
//					TODO Files.unzip(from, to);
					System.out.println("~ \tmodules/" + to.getName());
				}

				if (trim) {
					for (File dirToTrim : to.listFiles(dirsToTrim)) {
						Files.deleteIfExists(dirToTrim.toPath());
					}
				}

				return to;
			}
		} catch (Exception e) {
			System.out.println("~ \tError installing " + artifact.getLocalFile());
			throw e;
		}
	}

	String moduleName(ArtifactDownloadReport artifact, boolean shortModuleNames) {
		if (shortModuleNames) {
			return artifact.getName();
		}

		String mName = artifact.getLocalFile().getName();
		if (mName.endsWith(".jar") || mName.endsWith(".zip")) {
			mName = mName.substring(0, mName.length() - 4);
		}
		return mName;
	}

	private boolean isFrameworkLocal(ArtifactDownloadReport artifact) throws Exception {
		String artifactFileName = artifact.getLocalFile().getName();
		return new File(framework, "framework/lib/" + artifactFileName).exists()
				|| new File(framework, "framework/" + artifactFileName).exists();
	}

	private boolean isPlayModule(ArtifactDownloadReport artifact) throws Exception {
		boolean isPlayModule = artifact.getLocalFile().getName().endsWith(".zip");
		if (!isPlayModule) {
			// Check again from origin location
			if (!artifact.getArtifactOrigin().isLocal()
					&& artifact.getArtifactOrigin().getLocation().endsWith(".zip")) {
				isPlayModule = true;
			} else if (artifact.getArtifactOrigin().isLocal() && artifact.getLocalFile().isDirectory()) {
				isPlayModule = true;
			} else if (artifact.getArtifactOrigin().isLocal()) {
				String frameworkPath = new File(framework, "modules").getCanonicalPath();
				isPlayModule = artifact.getArtifactOrigin().getLocation().startsWith(frameworkPath);
			}
		}
		return isPlayModule;
	}

	@Override
	public ResolveReport resolve() throws Exception {

		// Module
//		ModuleDescriptorParserRegistry.getInstance().addParser(new YamlParser());
//		Here create a prolog parser for ivy dependency manager

		File ivyModule = new File(application, "conf/dependencies.yml");
		if (!ivyModule.exists()) {
			System.out.println("~ !! " + ivyModule.getAbsolutePath() + " does not exist");
			System.exit(-1);
			return null;
		}

		// Variables
		System.setProperty("play.path", framework.getAbsolutePath());

		// Ivy
		Ivy ivy = configure();

		// Clear the cache
		boolean clearcache = System.getProperty("clearcache") != null;
		if (clearcache) {
			System.out.println("~ Clearing cache : " + ivy.getResolutionCacheManager().getResolutionCacheRoot() + ",");
			System.out.println("~");
			try {
				FileUtils.deleteDirectory(ivy.getResolutionCacheManager().getResolutionCacheRoot());
				System.out.println("~       Clear");
			} catch (IOException e) {
				System.out.println("~       Could not clear");
				System.out.println("~ ");
				e.printStackTrace();
			}

			System.out.println("~");
		}

		System.out.println("~ Resolving dependencies using " + ivyModule.getAbsolutePath() + ",");
		System.out.println("~");

		// Resolve
		ResolveEngine resolveEngine = ivy.getResolveEngine();
		ResolveOptions resolveOptions = new ResolveOptions();
		resolveOptions.setConfs(new String[] { "default" });
		resolveOptions
				.setArtifactFilter(FilterHelper.getArtifactTypeFilter(new String[] { "jar", "bundle", "source" }));

		return resolveEngine.resolve(ivyModule.toURI().toURL(), resolveOptions);
	}

	@Override
	public Ivy configure() throws Exception {
		boolean verbose = System.getProperty("verbose") != null;
		boolean debug = System.getProperty("debug") != null;

		IvySettings ivySettings = new IvySettings();
//		new SettingsParser(this.logger).parse(ivySettings, new File(framework, "framework/dependencies.yml"));
//		new SettingsParser(this.logger).parse(ivySettings, new File(application, "conf/dependencies.yml"));
		ivySettings.setDefaultResolver("mavenCentral");
		ivySettings.setDefaultUseOrigin(true);
//		PlayConflictManager conflictManager = new PlayConflictManager();
//		ivySettings.addConflictManager("playConflicts", conflictManager);
//		ivySettings.addConflictManager("defaultConflicts", conflictManager.delegate);
//		ivySettings.setDefaultConflictManager(conflictManager);

		Ivy ivy = Ivy.newInstance(ivySettings);

		// Default ivy config see:
		// http://play.lighthouseapp.com/projects/57987-play-framework/tickets/807
		if (userHome != null) {
			File ivyDefaultSettings = new File(userHome, ".ivy2/ivysettings.xml");
			if (ivyDefaultSettings.exists()) {
				ivy.configure(ivyDefaultSettings);
			}
		}

		if (debug) {
			ivy.getLoggerEngine().pushLogger(new DefaultMessageLogger(Message.MSG_DEBUG));
		} else if (verbose) {
			ivy.getLoggerEngine().pushLogger(new DefaultMessageLogger(Message.MSG_INFO));
		} else {
//			ivy.getLoggerEngine().setDefaultLogger(this.logger);
		}

		ivy.pushContext();

		return ivy;
	}
}

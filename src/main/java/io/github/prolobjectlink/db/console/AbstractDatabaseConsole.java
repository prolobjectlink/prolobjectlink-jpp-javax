/*-
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
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
package io.github.prolobjectlink.db.console;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.github.prolobjectlink.db.DatabaseConsole;
import io.github.prolobjectlink.db.logging.DatabaseLoggerUtils;
import io.github.prolobjectlink.db.prolog.PrologDatabaseEngine;
import io.github.prolobjectlink.db.prolog.PrologDatabaseProvider;
import io.github.prolobjectlink.db.prolog.PrologProgrammer;
import io.github.prolobjectlink.db.prolog.PrologProject;
import io.github.prolobjectlink.prolog.ArrayIterator;
import io.github.prolobjectlink.prolog.PrologIndicator;
import io.github.prolobjectlink.prolog.PrologQuery;
import io.github.prolobjectlink.prolog.PrologTerm;

/**
 * @deprecated use io.github.prolobjectlink.web.console.AbstractWebConsole
 *             isntead
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
@Deprecated
public abstract class AbstractDatabaseConsole implements DatabaseConsole {

	private static final String PROLOBJECTLINK = "Prolobjectlink";
	private static final String COPYRIHT = " (C)";

	// default input stream
	private final InputStreamReader reader = new InputStreamReader(System.in);

	// buffered reader for read from standard input stream
	private final BufferedReader stdin = new BufferedReader(reader);

	// standard output stream
	// private final PrintWriter stdout = System.console().writer()
	private static final PrintWriter stdout = new PrintWriter(System.out, true);

	//
	private final PrologDatabaseEngine engine;

	public AbstractDatabaseConsole(PrologDatabaseProvider provider) {
		this.engine = provider.newEngine();
	}

	public final Map<String, String> getArguments(String[] args) {
		final Map<String, String> map = new HashMap<String, String>();
		if (args.length > 0) {
			Iterator<String> i = new ArrayIterator<String>(args);
			String name = i.next();
			if (i.hasNext()) {
				String value = i.next();
				map.put(name, value);
			} else {
				map.put(name, "");
			}
		}
		return map;
	}

	public final int getDefaultHttpPort() {
		return 8080;
	}

	public final void printUsage() {
		stdout.println("Usage: pllink option [file] to consult a file");
		stdout.println("options:");
		stdout.println("	-r	consult/run a prolog file");
		stdout.println("	-v	print the prolog engine version");
		stdout.println("	-n	print the prolog engine name");
		stdout.println("	-l	print the prolog engine license");
		stdout.println("	-i	print the prolog engine information");
		stdout.println("	-a	print the prolog engine about");
		stdout.println("	-e	print the prolog engine enviroment paths");
		stdout.println("	-x	start the prolog engine execution");
		stdout.println("	-w	print the current work directory ");
		stdout.println("	-f	consult a prolog file and save formatted code");
		stdout.println("	-t	test and report integration conditions");
		stdout.println("	-p	print in a file a snapshot of currents predicates");
		stdout.println("	-g	generate all java class path wrapper procedures");
		stdout.println("	-s	generate .project file for Prolog Development Tool");
		stdout.println("	-m	generate model jar file for all web applications");
		stdout.println("	-c	generate controllers for all web applications");
		stdout.println("	-j	generate model jar to prolog");
		stdout.println("	-b	generate web application");
		stdout.println("	-u	generate web HTML views");
		stdout.println("	-h	print usage");
	}

	public final void run(String[] args) {

		Map<String, String> m = getArguments(args);
		if (!m.isEmpty()) {
			if (m.containsKey("-v")) {
				stdout.println(engine.getVersion());
			} else if (m.containsKey("-n")) {
				stdout.println(engine.getName());
			} else if (m.containsKey("-l")) {
				stdout.println(engine.getLicense());
			} else if (m.containsKey("-i")) {
				stdout.print(PROLOBJECTLINK);
				stdout.print(COPYRIHT);
				stdout.print(" ");
				stdout.print(engine.getName());
				stdout.print(" v");
				stdout.println(engine.getVersion());
				stdout.println(engine.getLicense());
				stdout.println(System.getProperty("java.vm.name"));
				stdout.println(System.getProperty("java.vendor"));
				stdout.println(System.getProperty("java.version"));
				stdout.println();
			} else if (m.containsKey("-w")) {
				try {
					stdout.println("Working directory");
					ProtectionDomain p = getClass().getProtectionDomain();
					URI d = p.getCodeSource().getLocation().toURI();
					stdout.println(d);
				} catch (URISyntaxException e) {
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
				}
			} else if (m.containsKey("-e")) {
				stdout.println("Enviroment");
				stdout.println("Class path");
				stdout.println(System.getenv("java.class.path"));
				stdout.println("System path");
				stdout.println(System.getenv("Path"));
			} else if (m.containsKey("-a")) {
				stdout.print(PROLOBJECTLINK);
				stdout.print(COPYRIHT);
			} else if (m.containsKey("-r")) {
				String file = m.get("-r");
				stdout.print("Consult ");
				stdout.println(file);
				engine.consult(file);
			} else if (m.containsKey("-x")) {
				// do nothing silently execution
			} else if (m.containsKey("-f")) {
				String file = m.get("-r");
				stdout.print("Format ");
				stdout.println(file);
				engine.consult(file);
				engine.persist(file);
			} else if (m.containsKey("-t")) {
				List<String> status = engine.verify();
				for (String string : status) {
					stdout.println(string);
				}
			} else if (m.containsKey("-p")) {
				String file = m.get("-p");
				try {
					PrintWriter writter = new PrintWriter(file);
					Set<PrologIndicator> set = engine.currentPredicates();
					for (PrologIndicator prologIndicator : set) {
						writter.println(prologIndicator);
					}
					writter.close();
				} catch (FileNotFoundException e) {
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
					System.exit(1);
				}
			} else if (m.containsKey("-g")) {
				engine.getProgrammer().codingRuntime(stdout);
				System.exit(0);
			} else if (m.containsKey("-s")) {
				PrologProject.dotProject();
				System.exit(0);
			} else if (m.containsKey("-h")) {
				printUsage();
			} else if (m.containsKey("-j")) {
				DatabaseLoggerUtils.info(getClass(), "Coding web applications models");
				String file = m.get("-j");
				try {
					JarFile jarFile = new JarFile(file);
					PrologProgrammer p = engine.getProgrammer();
					p.codingModel(stdout, jarFile, false);
					jarFile.close();
				} catch (IOException e) {
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
					System.exit(1);
				}
				DatabaseLoggerUtils.info(getClass(), "Coding OK");
				System.exit(0);
			} else if (m.containsKey("-c")) {
				stdout.println("Coding applications controllers");
				String file = m.get("-c");
				try {
					JarFile jarFile = new JarFile(file);
					PrologProgrammer p = engine.getProgrammer();
					p.codingController(stdout, jarFile, false);
					jarFile.close();
				} catch (IOException e) {
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
					System.exit(1);
				}
				stdout.println("Coding OK");
				System.exit(0);
			} else if (m.containsKey("-u")) {
				stdout.println("Coding applications views");
				String file = m.get("-u");
				try {
					JarFile jarFile = new JarFile(file);
					PrologProgrammer p = engine.getProgrammer();
					p.codingView(stdout, jarFile, false);
					jarFile.close();
				} catch (IOException e) {
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
					System.exit(1);
				}
				stdout.println("Coding OK");
				System.exit(0);
			} else {
				printUsage();
				System.exit(1);
			}

			try {

				String input;
				stdout.print("?- ");
				stdout.flush();
				input = stdin.readLine();

				while (true) {

					if (!input.equals("")) {
						stdout.println();

						if (input.lastIndexOf('.') == input.length() - 1) {
							input = input.substring(0, input.length() - 1);
						}

						PrologQuery query = engine.query(input);
						if (query.hasSolution()) {
							Map<String, PrologTerm> s = query.oneVariablesSolution();
							for (Entry<String, PrologTerm> e : s.entrySet()) {
								stdout.println(e.getKey() + " = " + e.getValue());
							}
							stdout.println();
							stdout.println("Yes.");
						}

						else {
							stdout.println("No.");
						}

						stdout.println();
						stdout.println();

					} else {
						stdout.println("Emty query");
						stdout.println();
					}

					stdout.print("?- ");
					stdout.flush();
					input = stdin.readLine();

				}

			} catch (UnsatisfiedLinkError e) {
				stdout.println("Prolog engine link conditions:");
				List<String> status = engine.verify();
				for (String string : status) {
					stdout.println(string);
				}
			} catch (IOException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
				System.exit(1);
			} catch (NullPointerException e) {
				Runtime rt = Runtime.getRuntime();
				try {
					if (System.getProperty("os.name").toLowerCase().indexOf("windows") > -1)
						rt.exec("FOR /F \"tokens=1,2 delims= \" %%G IN ('jps -l') DO IF %%H==org.prolobjectlink.db.prolog.jpl.swi.SwiPrologDatabaseConsole taskkill /F /PID %%G");
					else
						rt.exec("kill $(jps -l | grep org.prolobjectlink.db.prolog.jpl.swi.SwiPrologDatabaseConsole | awk '{print $1}')");
				} catch (IOException e1) {
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
					System.exit(1);
				}
			}

		} else {
			printUsage();
			System.exit(1);
		}

	}

}

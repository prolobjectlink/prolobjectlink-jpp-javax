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
package org.prolobjectlink.web.application;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Id;

import org.prolobjectlink.db.prolog.AbstractPrologProgrammer;
import org.prolobjectlink.db.prolog.PrologProgrammer;

import io.github.prolobjectlink.prolog.PrologProvider;

public abstract class AbstractViewProgrammer extends AbstractPrologProgrammer implements PrologProgrammer {

	protected AbstractViewProgrammer(PrologProvider provider) {
		super(provider);
	}

	public final void codingView(PrintWriter stdout, JarFile jarFile, File folder, boolean warnings) {
		ClassLoader l = Thread.currentThread().getContextClassLoader();
		Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			JarEntry jarEntry = entries.nextElement();
			String jarEntryName = jarEntry.getName();
			if (isValidJarEntry(jarEntryName)) {
				String className = jarEntryName.substring(0, jarEntryName.length() - 6);
				if (!className.contains("Dao")) {
					String modelFileName = fromCamelCase(className) + ".pl";
					String name = modelFileName.substring(0, modelFileName.lastIndexOf(".pl"));
					name = modelFileName.substring(name.lastIndexOf('/') + 1, name.length());

					PrintWriter editWriter = null;
					PrintWriter listWriter = null;
					PrintWriter _newWriter = null;
					PrintWriter showWriter = null;

					try {
						File viewModule = createViewFile(folder, modelFileName);
						File edit = new File(viewModule + "/edit.html");
						File list = new File(viewModule + "/list.html");
						File _new = new File(viewModule + "/new.html");
						File show = new File(viewModule + "/show.html");

						edit.createNewFile();
						list.createNewFile();
						_new.createNewFile();
						show.createNewFile();

						Class<?> runtimeClass = l.loadClass(className.replace('/', '.'));
						if (isValidClass(runtimeClass)) {

							editWriter = new PrintWriter(edit);
							listWriter = new PrintWriter(list);
							_newWriter = new PrintWriter(_new);
							showWriter = new PrintWriter(show);

							String htmlTag = "<html>\n";
							String headTag = "\t<head>\n";
							String metaTag = "\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n";
							String jquery = "\t\t<script src=\"{{ asset(\"js/jquery-3.4.1.min.js\") }}\"></script>\n";
							String popper = "\t\t<script src=\"{{ asset(\"js/popper.min.js\") }}\"></script>\n";
							String bootstrapjs = "\t\t<script src=\"{{ asset(\"js/bootstrap.min.js\") }}\"></script>\n";
							String bootstrapcss = "\t\t<link href=\"{{ asset(\"css/bootstrap.min.css\") }}\" rel=\"stylesheet\">\n";
							String headEndTag = "\t</head>\n";
							String bodyTag = "\t<body>\n";
							String bodyEndTag = "\t</body>\n";
							String htmlEndTag = "</html>";

							// edit
							editWriter.write(htmlTag);
							editWriter.write(headTag);
							editWriter.write(metaTag);
							editWriter.write("\t\t<title>Edit</title>\n");
							editWriter.write(jquery);
							editWriter.write(popper);
							editWriter.write(bootstrapjs);
							editWriter.write(bootstrapcss);
							editWriter.write(headEndTag);
							editWriter.write(bodyTag);
							editWriter.write("\t\t<h1>Edit</h1>\n");
							editWriter.write("\t\t<form id=\"edit\" action=\"{{ path(\"" + name
									+ "_update\") }}\" method=\"post\">\n");
							editWriter.write("\t\t<table>\n");

							// list
							listWriter.write(htmlTag);
							listWriter.write(headTag);
							listWriter.write(metaTag);
							listWriter.write("\t\t<title>List</title>\n");
							listWriter.write(jquery);
							listWriter.write(popper);
							listWriter.write(bootstrapjs);
							listWriter.write(bootstrapcss);
							listWriter.write(headEndTag);
							listWriter.write(bodyTag);
							listWriter.write("\t\t<h1>List</h1>\n");
							listWriter.write("\t\t<a href=\"{{ path(\"" + name + "_new\") }}\">New</a>\n");
							listWriter.write("\t\t<table>\n");

							// new
							_newWriter.write(htmlTag);
							_newWriter.write(headTag);
							_newWriter.write(metaTag);
							_newWriter.write("\t\t<title>Create</title>\n");
							_newWriter.write(jquery);
							_newWriter.write(popper);
							_newWriter.write(bootstrapjs);
							_newWriter.write(bootstrapcss);
							_newWriter.write(headEndTag);
							_newWriter.write(bodyTag);
							_newWriter.write("\t\t<h1>Create</h1>\n");
							_newWriter.write("\t\t<form id=\"edit\" action=\"{{ path(\"" + name
									+ "_create\") }}\" method=\"post\">\n");
							_newWriter.write("\t\t<table>\n");

							// show
							showWriter.write(htmlTag);
							showWriter.write(headTag);
							showWriter.write(metaTag);
							showWriter.write("\t\t<title>Details</title>\n");
							showWriter.write(jquery);
							showWriter.write(popper);
							showWriter.write(bootstrapjs);
							showWriter.write(bootstrapcss);
							showWriter.write(headEndTag);
							showWriter.write(bodyTag);
							showWriter.write("\t\t<h1>Details</h1>\n");
							showWriter.write("\t\t<a href=\"{{ path(\"" + name + "_new\") }}\">New</a>\n");
							showWriter.write(
									"\t\t<a href=\"{{ path(\"" + name + "_edit/\"+ENTITY.getId()) }}\">Editar</a>\n");
							showWriter.write("\t\t<table>\n");

							StringBuilder tableHeader = new StringBuilder();
							StringBuilder tableData = new StringBuilder();
							tableHeader.append("\t\t<tr>\n");
							tableData.append("\t\t{{for value in LIST}}\n");
							tableData.append("\t\t<tr>\n");
							Field[] fields = runtimeClass.getDeclaredFields();
							for (Field field : fields) {

								String fieldName = field.getName();
								String attribute = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

								// edit
								editWriter.write("\t\t\t<tr><td>" + attribute + ":</td><td><input type=\"text\" id = \""
										+ attribute + "\" name=\"" + attribute + "\" value=\"{{ENTITY.get" + attribute
										+ "()}}\" /></td></tr>\n");

								if (!field.isAnnotationPresent(Id.class)) {

									// list
									tableHeader.append("\t\t\t<th>" + attribute + "</th>\n");
									tableData.append("\t\t\t<td>{{value.get" + attribute + "()}}</td>\n");

									// new
									_newWriter.write(
											"\t\t\t<tr><td>" + attribute + ":</td><td><input type=\"text\" id = \""
													+ attribute + "\" name=\"" + attribute + "\" /></td></tr>\n");

									// show
									showWriter.write("\t\t\t<tr><th>" + attribute + "</th><td>{{ENTITY.get" + attribute
											+ "()}}</td></tr>\n");
								}

							}
							tableHeader.append("\t\t</tr>\n");

							tableData.append("\t\t\t<td>\n");
							tableData.append("\t\t\t\t<ul>\n");
							tableData.append("\t\t\t\t\t<li>\n");
							tableData.append("\t\t\t\t\t\t<a href=\"{{ path(\"" + name
									+ "_find/\"+value.getId()) }}\">Details</a>\n");
							tableData.append("\t\t\t\t\t</li>\n");
							tableData.append("\t\t\t\t\t<li>\n");
							tableData.append("\t\t\t\t\t\t<a href=\"{{ path(\"" + name
									+ "_edit/\"+value.getId()) }}\">Edit</a>\n");
							tableData.append("\t\t\t\t\t</li>\n");
							tableData.append("\t\t\t\t\t<li>\n");
							tableData.append("\t\t\t\t\t\t<a href=\"{{ path(\"" + name
									+ "_delete/\"+value.getId()) }}\">Delete</a>\n");
							tableData.append("\t\t\t\t\t</li>\n");
							tableData.append("\t\t\t\t</ul>\n");
							tableData.append("\t\t\t</td>\n");

							tableData.append("\t\t</tr>\n");
							tableData.append("\t\t{{end}}\n");

							// edit
							editWriter.write("\t\t</table>\n");
							editWriter.write("\t\t<input type=\"submit\" id=\"Update\" value=\"Update\" />\n");
							editWriter.write("\t\t</form>\n");
							editWriter.write("\t\t<a href=\"{{ path(\"" + name + "_find_all\") }}\">List</a>\n");
							editWriter.write(bodyEndTag);
							editWriter.write(htmlEndTag);

							// list
							listWriter.write("" + tableHeader + "");
							listWriter.write("" + tableData + "");
							listWriter.write("\t\t</table>\n");
							listWriter.write(bodyEndTag);
							listWriter.write(htmlEndTag);

							// new
							_newWriter.write("\t\t</table>\n");
							_newWriter.write("\t\t<input type=\"submit\" id=\"Create\" value=\"Create\" />\n");
							_newWriter.write("\t\t</form>\n");
							_newWriter.write("\t\t<a href=\"{{ path(\"" + name + "_find_all\") }}\">List</a>\n");
							_newWriter.write(bodyEndTag);
							_newWriter.write(htmlEndTag);

							// show
							showWriter.write("\t\t</table>\n");
							showWriter.write("\t\t<a href=\"{{ path(\"" + name + "_find_all\") }}\">List</a>");
							showWriter.write(bodyEndTag);
							showWriter.write(htmlEndTag);

						}

					} catch (IOException e) {
						Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
					} catch (ClassNotFoundException e) {
						Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
					} finally {
						if (editWriter != null) {
							editWriter.close();
						}
						if (listWriter != null) {
							listWriter.close();
						}
						if (_newWriter != null) {
							_newWriter.close();
						}
						if (showWriter != null) {
							showWriter.close();
						}
					}
				}
			}
		}

	}

}

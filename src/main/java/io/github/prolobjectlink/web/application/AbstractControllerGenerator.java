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
package io.github.prolobjectlink.web.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.CheckClassAdapter;

import io.github.prolobjectlink.db.DatabaseClass;
import io.github.prolobjectlink.db.DynamicClassLoader;
import io.github.prolobjectlink.db.etc.Settings;
import io.github.prolobjectlink.db.util.JavaReflect;
import io.github.prolobjectlink.prolog.PrologClause;
import io.github.prolobjectlink.prolog.PrologEngine;
import io.github.prolobjectlink.prolog.PrologProvider;

public abstract class AbstractControllerGenerator extends AbstractWebApplication implements ControllerGenerator {

	private final List<ServletUrlMapping> mappings;

	protected AbstractControllerGenerator() {
		this(new Settings().load().getProvider());
	}

	protected AbstractControllerGenerator(PrologProvider provider) {
		mappings = new ArrayList<ServletUrlMapping>();
		String controller = "controller.pl";
		File appRoot = getWebDirectory();
		File[] apps = appRoot.listFiles();
		for (File file : apps) {
			String appName = file.getName();
			String appPath = file.getPath();
			String separator = File.separator;
			String path = appPath + separator + controller;
			PrologEngine engine = provider.newEngine(path);
			for (PrologClause clause : engine) {
				String relative = clause.getArgument(0).getFunctor();
				String controllerPath = relative.substring(1, relative.length() - 1);
				String location = appPath + separator + controllerPath;
				PrologEngine prologEngine = provider.newEngine(location);

				// Generating Servlet
				String apkName = Character.toUpperCase(appName.charAt(0)) + appName.substring(1);
				for (PrologClause prologClause : prologEngine) {

					if (!prologClause.getFunctor().equals(":-")) { // exclude directives

						String functor = prologClause.getFunctor();
						String procedure = Character.toUpperCase(functor.charAt(0)) + functor.substring(1);
						String name = "org.prolobjectlink.web.servlet." + apkName + procedure;
						ClassWriter cw = new ClassWriter(Opcodes.ASM4);
						String internalName = name.replace('.', '/');

						// extends from HttpServlet
						String superclass = Type.getInternalName(HttpServlet.class);

						// implements from Servlet
						String[] interfaces = new String[1];
						interfaces[0] = Type.getInternalName(Servlet.class);

						String javaVersion = System.getProperty("java.version");
						javaVersion = javaVersion.substring(0, javaVersion.lastIndexOf('.'));
						CheckClassAdapter ca = new CheckClassAdapter(cw);

						ca.visit(DatabaseClass.versionMap.get(javaVersion), Opcodes.ACC_PUBLIC, internalName, null,
								superclass, interfaces);

						// @WebServlet annotation
						ca.visitAnnotation(Type.getDescriptor(WebServlet.class), true).visitEnd();

						// empty constructor
						MethodVisitor con = ca.visitMethod(Opcodes.ACC_PUBLIC, "<init>",
								Type.getMethodDescriptor(Type.VOID_TYPE), null, null);

						con.visitCode();
						con.visitVarInsn(Opcodes.ALOAD, 0);
						con.visitMethodInsn(Opcodes.INVOKESPECIAL, superclass, "<init>",
								Type.getMethodDescriptor(Type.VOID_TYPE), false);
						con.visitInsn(Opcodes.RETURN);
						con.visitMaxs(1, 1);
						con.visitEnd();

						// doGet Method
						String ioException = Type.getInternalName(IOException.class);
						String servletException = Type.getInternalName(ServletException.class);
						String[] exceptions = { servletException, ioException };
						Type reqType = Type.getType(HttpServletRequest.class);
						Type respType = Type.getType(HttpServletResponse.class);
						String methodDesc = Type.getMethodDescriptor(Type.VOID_TYPE, reqType, respType);
						String runMethodDesc = Type.getMethodDescriptor(Type.VOID_TYPE, reqType, respType);

						MethodVisitor mw = ca.visitMethod(Opcodes.ACC_PROTECTED, "doGet", methodDesc, null, exceptions);
						mw.visitCode();
						mw.visitVarInsn(Opcodes.ALOAD, 1);
						mw.visitVarInsn(Opcodes.ALOAD, 2);
						mw.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(getControllerRuntimeClass()),
								"run", runMethodDesc, false);
						mw.visitInsn(Opcodes.RETURN);
						mw.visitMaxs(3, 3);
						mw.visitEnd();

						// doPost Method
						mw = ca.visitMethod(Opcodes.ACC_PROTECTED, "doPost", methodDesc, null, exceptions);
						mw.visitCode();
						mw.visitVarInsn(Opcodes.ALOAD, 1);
						mw.visitVarInsn(Opcodes.ALOAD, 2);
						mw.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(getControllerRuntimeClass()),
								"run", runMethodDesc, false);
						mw.visitInsn(Opcodes.RETURN);
						mw.visitMaxs(3, 3);
						mw.visitEnd();

						ca.visitEnd();

						byte[] byteCode = cw.toByteArray();
						DynamicClassLoader dcl = new DynamicClassLoader();
						Class<?> cls = dcl.defineClass(name, byteCode);
						Object object = JavaReflect.newInstance(cls);

						if (object instanceof Servlet) {
							Servlet servlet = (Servlet) object;
							StringBuilder builder = new StringBuilder();
							builder.append(appName);
							builder.append('/');
							builder.append(functor);
							builder.append('/');
							builder.append('*');
							String url = builder.toString();
							ServletUrlMapping map = new ServletUrlMapping(servlet, url);
							mappings.add(map);
						}

					}

				}

				prologEngine.dispose();

			}

			engine.dispose();

		}
	}

	public final List<ServletUrlMapping> getMappings() {
		return mappings;
	}

}

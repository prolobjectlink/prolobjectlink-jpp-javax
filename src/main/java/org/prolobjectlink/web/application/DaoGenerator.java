/*-
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
 * #L%
 */
package org.prolobjectlink.web.application;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.CheckClassAdapter;
import org.prolobjectlink.db.DatabaseClass;

public class DaoGenerator {

	private final String name;
	private final String unit;

	public DaoGenerator(String name, String unit) {
		this.name = name;
		this.unit = unit;
	}

	public String generate() {
		// TODO
		return null;
	}

	public byte[] compile() {

		String daoName = name + "Dao";
		ClassWriter cw = new ClassWriter(Opcodes.ASM4);
		String internalName = daoName.replace('.', '/');
		String superclass = Type.getInternalName(Object.class);

		// active record variables
		Type mapType = Type.getType(Map.class);
		Type idType = Type.getType(Long.class);
		Type punType = Type.getType(String.class);
		Type objType = Type.getType(Object.class);
		Type classType = Type.getType(Class.class);
		Type emType = Type.getType(EntityManager.class);
		Type txType = Type.getType(EntityTransaction.class);
		Type emfType = Type.getType(EntityManagerFactory.class);

		String javaVersion = System.getProperty("java.version");
		javaVersion = javaVersion.substring(0, javaVersion.lastIndexOf('.'));
		CheckClassAdapter ca = new CheckClassAdapter(cw);
		ca.visit(DatabaseClass.versionMap.get(javaVersion), Opcodes.ACC_PUBLIC, internalName, null, superclass,
				new String[0]);

		ca.visitField(Opcodes.ACC_PRIVATE, "em", Type.getDescriptor(EntityManager.class), null, null).visitEnd();

		MethodVisitor con = ca.visitMethod(Opcodes.ACC_PUBLIC, "<init>", Type.getMethodDescriptor(Type.VOID_TYPE), null,
				null);
		con.visitCode();
		con.visitVarInsn(Opcodes.ALOAD, 0);
		con.visitMethodInsn(Opcodes.INVOKESPECIAL, superclass, "<init>", Type.getMethodDescriptor(Type.VOID_TYPE),
				false);

		con.visitTypeInsn(Opcodes.NEW, Type.getInternalName(HibernatePersistenceProvider.class));
		con.visitInsn(Opcodes.DUP);
		con.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(HibernatePersistenceProvider.class), "<init>",
				Type.getMethodDescriptor(Type.VOID_TYPE), false);
		con.visitLdcInsn(unit);
		con.visitTypeInsn(Opcodes.NEW, Type.getInternalName(HashMap.class));
		con.visitInsn(Opcodes.DUP);
		con.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(HashMap.class), "<init>",
				Type.getMethodDescriptor(Type.VOID_TYPE), false);

		con.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(HibernatePersistenceProvider.class),
				"createEntityManagerFactory", Type.getMethodDescriptor(emfType, punType, mapType), false);

		con.visitVarInsn(Opcodes.ASTORE, 1);
		con.visitVarInsn(Opcodes.ALOAD, 0);
		con.visitVarInsn(Opcodes.ALOAD, 1);
		con.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManagerFactory.class),
				"createEntityManager", Type.getMethodDescriptor(emType), true);
		con.visitFieldInsn(Opcodes.PUTFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		con.visitInsn(Opcodes.RETURN);
		con.visitMaxs(4, 2);
		con.visitEnd();

		// active record create method
		MethodVisitor mv = ca.visitMethod(Opcodes.ACC_PUBLIC, "create",
				Type.getMethodDescriptor(Type.VOID_TYPE, objType), null, null);
		mv.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "begin",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "persist",
				Type.getMethodDescriptor(Type.VOID_TYPE, objType), true);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "commit",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(2, 2);
		mv.visitEnd();

		// active record delete method
		mv = ca.visitMethod(Opcodes.ACC_PUBLIC, "delete", Type.getMethodDescriptor(Type.VOID_TYPE, objType), null,
				null);
		mv.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "begin",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "remove",
				Type.getMethodDescriptor(Type.VOID_TYPE, objType), true);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "commit",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(2, 2);
		mv.visitEnd();

		// active record update method
		mv = ca.visitMethod(Opcodes.ACC_PUBLIC, "update", Type.getMethodDescriptor(Type.VOID_TYPE, objType), null,
				null);
		mv.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "begin",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "merge",
				Type.getMethodDescriptor(objType, objType), true);
		mv.visitInsn(Opcodes.POP);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "commit",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(2, 2);
		mv.visitEnd();

		// active record retrieve by id method
		mv = ca.visitMethod(Opcodes.ACC_PUBLIC, "retrieve", Type.getMethodDescriptor(objType, idType), null, null);
		mv.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "begin",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitLdcInsn(objType);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "find",
				Type.getMethodDescriptor(objType, classType, objType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 2);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "commit",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 2);
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(3, 3);
		mv.visitEnd();

		ca.visitEnd();

		return cw.toByteArray();
	}

}

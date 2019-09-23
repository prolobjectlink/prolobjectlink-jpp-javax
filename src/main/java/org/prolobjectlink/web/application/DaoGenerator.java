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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.CheckClassAdapter;
import org.prolobjectlink.db.DatabaseClass;

public class DaoGenerator {

	private final Class<?> cls;
	private final String unit;

	public DaoGenerator(Class<?> cls, String unit) {
		this.cls = cls;
		this.unit = unit;
	}

	public String generate() {
		// TODO
		return null;
	}

	public byte[] compile() {

		String daoName = cls.getName() + "Dao";
		ClassWriter cw = new ClassWriter(Opcodes.ASM4);
		String internalName = daoName.replace('.', '/');
		String superclass = Type.getInternalName(Object.class);

		// dao variables

		Type modelType = Type.getType(cls);
		Type mapType = Type.getType(Map.class);
		Type intType = Type.getType(int.class);
		Type listType = Type.getType(List.class);
		Type integerType = Type.getType(Integer.class);
		Type stringType = Type.getType(String.class);
		Type objType = Type.getType(Object.class);
		Type classType = Type.getType(Class.class);
		Type emType = Type.getType(EntityManager.class);
		Type txType = Type.getType(EntityTransaction.class);
		Type emfType = Type.getType(EntityManagerFactory.class);

		Type builderType = Type.getType(CriteriaBuilder.class);
		Type rootType = Type.getType(Root.class);
		Type queryType = Type.getType(TypedQuery.class);
		Type criteriaType = Type.getType(CriteriaQuery.class);
		Type selectionType = Type.getType(Selection.class);

		String id = "";
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Id.class)) {
				id = field.getName();
			}
		}

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
				"createEntityManagerFactory", Type.getMethodDescriptor(emfType, stringType, mapType), false);

		con.visitVarInsn(Opcodes.ASTORE, 1);
		con.visitVarInsn(Opcodes.ALOAD, 0);
		con.visitVarInsn(Opcodes.ALOAD, 1);
		con.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManagerFactory.class),
				"createEntityManager", Type.getMethodDescriptor(emType), true);
		con.visitFieldInsn(Opcodes.PUTFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		con.visitInsn(Opcodes.RETURN);
		con.visitMaxs(4, 2);
		con.visitEnd();

		// dao create method
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

		// dao delete method
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

		// dao update method
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

		// dao retrieve by id method
		mv = ca.visitMethod(Opcodes.ACC_PUBLIC, "retrieveOne", Type.getMethodDescriptor(objType, intType), null, null);
		mv.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "begin",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));

		//
		Character v = Character.toLowerCase(cls.getSimpleName().charAt(0));
		mv.visitLdcInsn("select " + v + " from " + cls.getSimpleName() + " " + v + " where " + v + "." + id + " = ?1");
		mv.visitLdcInsn(modelType);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "createQuery",
				Type.getMethodDescriptor(queryType, stringType, classType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 2);
		mv.visitVarInsn(Opcodes.ALOAD, 2);
		mv.visitInsn(Opcodes.ICONST_1);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(Integer.class), "valueOf",
				Type.getMethodDescriptor(integerType, intType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(TypedQuery.class), "setParameter",
				Type.getMethodDescriptor(queryType, intType, objType), true);
		mv.visitInsn(Opcodes.POP);
		mv.visitVarInsn(Opcodes.ALOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(TypedQuery.class), "getSingleResult",
				Type.getMethodDescriptor(objType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 3);
		//

		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "commit",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 3);
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(3, 4);
		mv.visitEnd();

		// dao retrieve all method
		mv = ca.visitMethod(Opcodes.ACC_PUBLIC, "retrieveAll", Type.getMethodDescriptor(listType), null, null);
		mv.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "begin",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));

		//
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getCriteriaBuilder",
				Type.getMethodDescriptor(builderType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 1);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitLdcInsn(modelType);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(CriteriaBuilder.class), "createQuery",
				Type.getMethodDescriptor(criteriaType, classType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 2);
		mv.visitVarInsn(Opcodes.ALOAD, 2);
		mv.visitLdcInsn(modelType);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(CriteriaQuery.class), "from",
				Type.getMethodDescriptor(rootType, classType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 3);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitVarInsn(Opcodes.ALOAD, 2);
		mv.visitVarInsn(Opcodes.ALOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(CriteriaQuery.class), "select",
				Type.getMethodDescriptor(criteriaType, selectionType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "createQuery",
				Type.getMethodDescriptor(queryType, criteriaType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(TypedQuery.class), "getResultList",
				Type.getMethodDescriptor(listType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 4);
		//

		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "commit",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 4);
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(3, 5);
		mv.visitEnd();

		// dao retrieve all method
		mv = ca.visitMethod(Opcodes.ACC_PUBLIC, "retrieveAll", Type.getMethodDescriptor(listType, intType, intType),
				null, null);
		mv.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "begin",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));

		//
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getCriteriaBuilder",
				Type.getMethodDescriptor(builderType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 3);
		mv.visitVarInsn(Opcodes.ALOAD, 3);
		mv.visitLdcInsn(modelType);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(CriteriaBuilder.class), "createQuery",
				Type.getMethodDescriptor(criteriaType, classType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 4);
		mv.visitVarInsn(Opcodes.ALOAD, 4);
		mv.visitLdcInsn(modelType);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(CriteriaQuery.class), "from",
				Type.getMethodDescriptor(rootType, classType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 5);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitVarInsn(Opcodes.ALOAD, 4);
		mv.visitVarInsn(Opcodes.ALOAD, 5);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(CriteriaQuery.class), "select",
				Type.getMethodDescriptor(criteriaType, selectionType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "createQuery",
				Type.getMethodDescriptor(queryType, criteriaType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 6);
		mv.visitVarInsn(Opcodes.ALOAD, 6);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(TypedQuery.class), "setMaxResults",
				Type.getMethodDescriptor(queryType, intType), true);
		mv.visitInsn(Opcodes.POP);
		mv.visitVarInsn(Opcodes.ALOAD, 6);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(TypedQuery.class), "setFirstResult",
				Type.getMethodDescriptor(queryType, intType), true);
		mv.visitInsn(Opcodes.POP);
		mv.visitVarInsn(Opcodes.ALOAD, 6);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(TypedQuery.class), "getResultList",
				Type.getMethodDescriptor(listType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 7);
		//

		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "commit",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 7);
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(3, 8);
		mv.visitEnd();

		// dao query one method
		mv = ca.visitMethod(Opcodes.ACC_PUBLIC, "queryOne", Type.getMethodDescriptor(objType, stringType), null, null);
		mv.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "begin",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));

		//
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitLdcInsn(modelType);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "createQuery",
				Type.getMethodDescriptor(queryType, stringType, classType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 2);
		mv.visitVarInsn(Opcodes.ALOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(TypedQuery.class), "getSingleResult",
				Type.getMethodDescriptor(objType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 3);
		//

		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "commit",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 3);
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(3, 4);
		mv.visitEnd();

		// dao query all method
		mv = ca.visitMethod(Opcodes.ACC_PUBLIC, "queryAll", Type.getMethodDescriptor(listType, stringType), null, null);
		mv.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "begin",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));

		//
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitLdcInsn(modelType);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "createQuery",
				Type.getMethodDescriptor(queryType, stringType, classType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 2);
		mv.visitVarInsn(Opcodes.ALOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(TypedQuery.class), "getResultList",
				Type.getMethodDescriptor(listType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 3);
		//

		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "commit",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 3);
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(3, 4);
		mv.visitEnd();

		// dao query all range method
		mv = ca.visitMethod(Opcodes.ACC_PUBLIC, "queryAll",
				Type.getMethodDescriptor(listType, stringType, intType, intType), null, null);
		mv.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "begin",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));

		//
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitLdcInsn(modelType);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "createQuery",
				Type.getMethodDescriptor(queryType, stringType, classType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 4);
		mv.visitVarInsn(Opcodes.ALOAD, 4);
		mv.visitVarInsn(Opcodes.ILOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(TypedQuery.class), "setMaxResults",
				Type.getMethodDescriptor(queryType, intType), true);
		mv.visitInsn(Opcodes.POP);
		mv.visitVarInsn(Opcodes.ALOAD, 4);
		mv.visitVarInsn(Opcodes.ILOAD, 3);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(TypedQuery.class), "setFirstResult",
				Type.getMethodDescriptor(queryType, intType), true);
		mv.visitInsn(Opcodes.POP);
		mv.visitVarInsn(Opcodes.ALOAD, 4);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(TypedQuery.class), "getResultList",
				Type.getMethodDescriptor(listType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 5);
		//

		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "em", Type.getDescriptor(EntityManager.class));
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "commit",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 5);
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(3, 6);
		mv.visitEnd();

		ca.visitEnd();

		return cw.toByteArray();
	}

}

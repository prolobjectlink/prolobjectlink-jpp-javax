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
package io.github.prolobjectlink.db.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.CheckClassAdapter;

import io.github.prolobjectlink.db.DatabaseClass;
import io.github.prolobjectlink.db.DatabaseField;
import io.github.prolobjectlink.db.Modifier;
import io.github.prolobjectlink.db.Schema;
import io.github.prolobjectlink.db.util.JavaStrings;

public class EntityClass extends DatabaseClass {

	private final Class<?> ppc;

	private static final long serialVersionUID = -4814556384681246762L;

	public EntityClass(Class<?> javaClass, String comment, Schema schema, Class<?> ppc) {
		super(javaClass, comment, schema);
		this.ppc = ppc;
	}

	public EntityClass(String name, String comment, Class<?> javaClass, Schema schema, Class<?> ppc) {
		super(name, comment, javaClass, schema);
		this.ppc = ppc;
	}

	public EntityClass(String name, String comment, Schema schema, Class<?> ppc) {
		super(name, comment, schema);
		this.ppc = ppc;
	}

	@Override
	protected DatabaseField newField(String name, String comment, int position, Class<?> type) {
		return new EntityField(name, comment, position, type, schema, this);
	}

	/**
	 * Add a field with your respective type. The created field is not marked like
	 * primary key.
	 * 
	 * @param name field name
	 * @param type type of the field
	 * @return the field created and added to this class
	 * @since 1.0
	 */
	@Override
	public DatabaseField addField(String name, String comment, int position, Class<?> type) {
		return addField(name, comment, position, type, false);
	}

	/**
	 * Add a field with your respective type and marked like class's primary key.
	 * 
	 * @param name       field name
	 * @param type       type of the field
	 * @param primaryKey true if this field is marked like primary key
	 * @return the field created and added to this class
	 * @since 1.0
	 */
	@Override
	public DatabaseField addField(String name, String comment, int position, Class<?> type, boolean primaryKey) {
		DatabaseField f = newField(name, comment, position, type);
		f.setPrimaryKey(primaryKey);
		fields.put(name, f);
		return f;
	}

	/**
	 * Add a field of type Collection/Map of given class type
	 * 
	 * @param name       field name
	 * @param type       type of the field
	 * @param linkedType Generic class for collection/Map
	 * @return the field created and added to this class
	 * @since 1.0
	 */
	@Override
	public DatabaseField addField(String name, String comment, int position, Class<?> type, Class<?> linkedType) {
		DatabaseField f = newField(name, comment, position, type);
		f.setLinkedType(linkedType);
		fields.put(name, f);
		return f;
	}

	/**
	 * Add a field
	 * 
	 * @param field to be added
	 * @return the given field
	 * @since 1.0
	 */
	@Override
	public DatabaseField addField(DatabaseField field) {
		fields.put(field.getName(), field);
		return field;
	}

	public String getPersistenceUnit() {
		return comment;
	}

	@Override
	public String generate() {
		String table = JavaStrings.toLowerCase(shortName);
		StringBuilder buffer = new StringBuilder();
		String pack = name.replace("." + shortName, "");
		buffer.append("package " + pack + ";");
		buffer.append('\n');
		buffer.append('\n');
		buffer.append("import java.util.*;");
		buffer.append('\n');
		buffer.append("import javax.persistence.*;");
		buffer.append('\n');
		buffer.append("import " + ppc.getPackage().getName() + ".*;");
		buffer.append('\n');
		buffer.append('\n');
		buffer.append("@Entity");
		buffer.append('\n');
		buffer.append("@Table(name = \"" + table + "\")");
		buffer.append('\n');
		buffer.append(Modifier.PUBLIC);
		buffer.append(' ');
		buffer.append("class");
		buffer.append(' ');
		buffer.append(shortName);
		buffer.append(' ');
		if (superClass != null) {
			buffer.append("extends");
			buffer.append(' ');
			buffer.append(superClass);
		}
		Iterator<DatabaseClass> i = superClasses.iterator();
		if (i.hasNext()) {
			buffer.append("implements");
			buffer.append(' ');
		}
		while (i.hasNext()) {
			DatabaseClass clazz = i.next();
			buffer.append(clazz);
			if (i.hasNext()) {
				buffer.append(',');
				buffer.append(' ');
			}
		}
		buffer.append(' ');
		buffer.append('{');
		buffer.append('\n');
		buffer.append('\n');

		// fields
		EntityField[] e = new EntityField[0];
		EntityField[] a = fields.values().toArray(e);
		Arrays.sort(a, new FieldComparator());
		for (EntityField field : a) {
			field.generateField(buffer);
		}
		buffer.append('\n');

		// empty constructor
		buffer.append('\t');
		buffer.append(Modifier.PUBLIC);
		buffer.append(' ');
		buffer.append(shortName);
		buffer.append("()");
		buffer.append('{');
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('}');
		buffer.append('\n');
		buffer.append('\n');

		// full constructor
		buffer.append('\t');
		buffer.append(Modifier.PUBLIC);
		buffer.append(' ');
		buffer.append(shortName);
		buffer.append('(');

		// TODO respect declaration order resolve this doing field a persistent linked
		// hash map

		Iterator<DatabaseField> j = fields.values().iterator();
		while (j.hasNext()) {
			DatabaseField field = j.next();
			field.generateParameter(buffer);
			if (j.hasNext()) {
				buffer.append(',');
				buffer.append(' ');
			}
		}
		buffer.append(')');
		buffer.append('{');
		buffer.append('\n');
		for (EntityField field : a) {
			field.generateAssigment(buffer);
		}
		buffer.append('\t');
		buffer.append('}');
		buffer.append('\n');
		buffer.append('\n');

		// methods
		for (DatabaseField field : fields.values()) {
			field.generateGetter(buffer);
			field.generateSetter(buffer);
		}

		buffer.append('\t');

		// toString method
		buffer.append("public String toString() {");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("return ");
		buffer.append("\"" + getShortName() + " [");
		for (DatabaseField field : fields.values()) {
			buffer.append(" " + field.getName() + "=\"+" + field.getName() + "+\"");
		}
		buffer.append(" ]\";");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append("}");

		buffer.append('\n');
		buffer.append('\n');
		buffer.append('\t');

		// create CRUD methods
		buffer.append("public void create() {");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("Map properties = new HashMap();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append(ppc.getSimpleName() + " pp = new " + ppc.getSimpleName() + "();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("EntityManagerFactory emf = pp.createEntityManagerFactory(\"" + getPersistenceUnit()
				+ "\", properties);");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("EntityManager em = emf.createEntityManager();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("try {");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("em.getTransaction().begin();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("em.persist(this);");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("em.getTransaction().commit();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("} catch (Exception e) {");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("em.getTransaction().rollback();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("e.printStackTrace();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("} finally {");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("em.close();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("emf.close();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("}");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append("}");

		buffer.append('\n');
		buffer.append('\n');
		buffer.append('\t');

		// update CRUD methods
		buffer.append("public void update() {");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("Map properties = new HashMap();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append(ppc.getSimpleName() + " pp = new " + ppc.getSimpleName() + "();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("EntityManagerFactory emf = pp.createEntityManagerFactory(\"" + getPersistenceUnit()
				+ "\", properties);");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("EntityManager em = emf.createEntityManager();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("try {");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("em.getTransaction().begin();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("em.merge(this);");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("em.getTransaction().commit();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("} catch (Exception e) {");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("em.getTransaction().rollback();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("e.printStackTrace();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("} finally {");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("em.close();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("emf.close();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("}");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append("}");

		buffer.append('\n');
		buffer.append('\n');
		buffer.append('\t');

		// delete CRUD methods
		buffer.append("public void delete() {");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("Map properties = new HashMap();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append(ppc.getSimpleName() + " pp = new " + ppc.getSimpleName() + "();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("EntityManagerFactory emf = pp.createEntityManagerFactory(\"" + getPersistenceUnit()
				+ "\", properties);");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("EntityManager em = emf.createEntityManager();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("try {");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("em.getTransaction().begin();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("em.remove(em.merge(this));");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("em.getTransaction().commit();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("} catch (Exception e) {");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("em.getTransaction().rollback();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("e.printStackTrace();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("} finally {");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("em.close();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("emf.close();");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("}");
		buffer.append('\n');
		buffer.append('\t');
		buffer.append("}");

		buffer.append('\n');
		buffer.append('\n');
		buffer.append('}');
		buffer.append('\n');
		return "" + buffer + "";
	}

	/**
	 * @deprecated Use generator method and compile with
	 *             io.github.prolobjectlink.web.compiler.ModelCompiler. This method
	 *             compile fine but remove entity not work fine because raise an
	 *             exception.
	 */
	@Override
	@Deprecated
	public byte[] compile() {
		ClassWriter cw = new ClassWriter(Opcodes.ASM4);
		String internalName = name.replace('.', '/');
		String superclass = superClass != null ? //
				superClass.getName().replace('.', '/') : //
				Type.getInternalName(Object.class);

		String[] interfaces = new String[superClasses.size()];
		for (int i = 0; i < superClasses.size(); i++) {
			interfaces[i] = superClasses.get(i).getName().replace('.', '/');
		}

		String javaVersion = System.getProperty("java.version");
		javaVersion = javaVersion.substring(0, javaVersion.lastIndexOf('.'));
		CheckClassAdapter ca = new CheckClassAdapter(cw);
		ca.visit(versionMap.get(javaVersion), Opcodes.ACC_PUBLIC, internalName, null, superclass, interfaces);

		// @Entity annotation
		ca.visitAnnotation(Type.getDescriptor(Entity.class), true).visitEnd();

		// Fields Declaration
		for (DatabaseField field : fields.values()) {
			field.createField(cw);
		}

		MethodVisitor con = ca.visitMethod(Opcodes.ACC_PUBLIC, "<init>", Type.getMethodDescriptor(Type.VOID_TYPE), null,
				null);

		con.visitCode();
		con.visitVarInsn(Opcodes.ALOAD, 0);
		con.visitMethodInsn(Opcodes.INVOKESPECIAL, superclass, "<init>", Type.getMethodDescriptor(Type.VOID_TYPE),
				false);
		con.visitInsn(Opcodes.RETURN);
		con.visitMaxs(1, 1);
		con.visitEnd();

		// Fields Getters and Setters
		for (DatabaseField field : fields.values()) {
			Class<?> type = field.getType();
			String typeDescriptor = Type.getDescriptor(type);
			field.createGetter(ca, internalName, typeDescriptor, type);
			field.createSetter(ca, internalName, typeDescriptor, type);
		}

		Type mapType = Type.getType(Map.class);
		Type objType = Type.getType(Object.class);
		Type stringType = Type.getType(String.class);
		Type emType = Type.getType(EntityManager.class);
		Type txType = Type.getType(EntityTransaction.class);
		Type emfType = Type.getType(EntityManagerFactory.class);
		Type stringBuilderType = Type.getType(StringBuilder.class);

		// active record create method
		MethodVisitor mv = ca.visitMethod(Opcodes.ACC_PUBLIC, "create", Type.getMethodDescriptor(Type.VOID_TYPE), null,
				null);
		mv.visitCode();
		mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(ppc));
		mv.visitInsn(Opcodes.DUP);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(ppc), "<init>",
				Type.getMethodDescriptor(Type.VOID_TYPE), false);
		mv.visitLdcInsn(getPersistenceUnit());
		mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(HashMap.class));
		mv.visitInsn(Opcodes.DUP);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(HashMap.class), "<init>",
				Type.getMethodDescriptor(Type.VOID_TYPE), false);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(ppc), "createEntityManagerFactory",
				Type.getMethodDescriptor(emfType, stringType, mapType), false);
		mv.visitVarInsn(Opcodes.ASTORE, 1); // emf
		mv.visitVarInsn(Opcodes.ALOAD, 1); // emf
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManagerFactory.class),
				"createEntityManager", Type.getMethodDescriptor(emType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 2); // em
		mv.visitVarInsn(Opcodes.ALOAD, 2);// em
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "begin",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 2);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "persist",
				Type.getMethodDescriptor(Type.VOID_TYPE, objType), true);
		mv.visitVarInsn(Opcodes.ALOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "commit",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "close",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManagerFactory.class), "close",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(4, 3);
		mv.visitEnd();

		// active record update method
		mv = ca.visitMethod(Opcodes.ACC_PUBLIC, "update", Type.getMethodDescriptor(Type.VOID_TYPE), null, null);
		mv.visitCode();
		mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(ppc));
		mv.visitInsn(Opcodes.DUP);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(ppc), "<init>",
				Type.getMethodDescriptor(Type.VOID_TYPE), false);
		mv.visitLdcInsn(getPersistenceUnit());
		mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(HashMap.class));
		mv.visitInsn(Opcodes.DUP);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(HashMap.class), "<init>",
				Type.getMethodDescriptor(Type.VOID_TYPE), false);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(ppc), "createEntityManagerFactory",
				Type.getMethodDescriptor(emfType, stringType, mapType), false);
		mv.visitVarInsn(Opcodes.ASTORE, 1); // emf
		mv.visitVarInsn(Opcodes.ALOAD, 1); // emf
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManagerFactory.class),
				"createEntityManager", Type.getMethodDescriptor(emType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 2); // em
		mv.visitVarInsn(Opcodes.ALOAD, 2);// em
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "begin",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 2);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "merge",
				Type.getMethodDescriptor(objType, objType), true);
		mv.visitInsn(Opcodes.POP);
		mv.visitVarInsn(Opcodes.ALOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "commit",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "close",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManagerFactory.class), "close",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(4, 3);
		mv.visitEnd();

		// active record delete method
		mv = ca.visitMethod(Opcodes.ACC_PUBLIC, "delete", Type.getMethodDescriptor(Type.VOID_TYPE), null, null);
		mv.visitCode();
		mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(ppc));
		mv.visitInsn(Opcodes.DUP);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(ppc), "<init>",
				Type.getMethodDescriptor(Type.VOID_TYPE), false);
		mv.visitLdcInsn(getPersistenceUnit());
		mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(HashMap.class));
		mv.visitInsn(Opcodes.DUP);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(HashMap.class), "<init>",
				Type.getMethodDescriptor(Type.VOID_TYPE), false);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(ppc), "createEntityManagerFactory",
				Type.getMethodDescriptor(emfType, stringType, mapType), false);
		mv.visitVarInsn(Opcodes.ASTORE, 1); // emf
		mv.visitVarInsn(Opcodes.ALOAD, 1); // emf
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManagerFactory.class),
				"createEntityManager", Type.getMethodDescriptor(emType), true);
		mv.visitVarInsn(Opcodes.ASTORE, 2); // em
		mv.visitVarInsn(Opcodes.ALOAD, 2);// em
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "begin",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 2);
		mv.visitVarInsn(Opcodes.ALOAD, 0);

		////////////////////////////////////////////////////////////////////////////////

		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "remove",
				Type.getMethodDescriptor(Type.VOID_TYPE, objType), true);
//		mv.visitVarInsn(Opcodes.ALOAD, 0);
//		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "flush",
//				Type.getMethodDescriptor(Type.VOID_TYPE), true);

		////////////////////////////////////////////////////////////////////////////////

		mv.visitVarInsn(Opcodes.ALOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "getTransaction",
				Type.getMethodDescriptor(txType), true);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityTransaction.class), "commit",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManager.class), "close",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(EntityManagerFactory.class), "close",
				Type.getMethodDescriptor(Type.VOID_TYPE), true);
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(4, 3);
		mv.visitEnd();

		// active record to string method
		mv = ca.visitMethod(Opcodes.ACC_PUBLIC, "toString", Type.getMethodDescriptor(stringType), null, null);
		mv.visitCode();
		mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(StringBuilder.class));
		mv.visitInsn(Opcodes.DUP);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(StringBuilder.class), "<init>",
				Type.getMethodDescriptor(Type.VOID_TYPE), false);
		mv.visitLdcInsn(getShortName() + "[");
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(StringBuilder.class), "append",
				Type.getMethodDescriptor(stringBuilderType, objType), false);
		for (DatabaseField field : fields.values()) {
			mv.visitLdcInsn(" " + field.getName() + "=");
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(StringBuilder.class), "append",
					Type.getMethodDescriptor(stringBuilderType, objType), false);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitFieldInsn(Opcodes.GETFIELD, internalName, field.getName(), Type.getDescriptor(field.getType()));
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(StringBuilder.class), "append",
					Type.getMethodDescriptor(stringBuilderType, objType), false);
		}
		mv.visitLdcInsn(" ]");
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(StringBuilder.class), "append",
				Type.getMethodDescriptor(stringBuilderType, objType), false);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(StringBuilder.class), "toString",
				Type.getMethodDescriptor(stringType), false);
		mv.visitInsn(Opcodes.ARETURN);
		mv.visitMaxs(2, 1);
		mv.visitEnd();

		ca.visitEnd();

		return cw.toByteArray();
	}

}

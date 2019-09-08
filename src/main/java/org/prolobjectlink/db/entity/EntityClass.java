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
package org.prolobjectlink.db.entity;

import java.util.Arrays;
import java.util.Iterator;

import javax.persistence.Entity;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.CheckClassAdapter;
import org.prolobjectlink.db.DatabaseClass;
import org.prolobjectlink.db.DatabaseField;
import org.prolobjectlink.db.Modifier;
import org.prolobjectlink.db.Schema;

public class EntityClass extends DatabaseClass {

	private static final long serialVersionUID = -4814556384681246762L;

	public EntityClass(Class<?> javaClass, String comment, Schema schema) {
		super(javaClass, comment, schema);
	}

	public EntityClass(String name, String comment, Class<?> javaClass, Schema schema) {
		super(name, comment, javaClass, schema);
	}

	public EntityClass(String name, String comment, Schema schema) {
		super(name, comment, schema);
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

	@Override
	public String generate() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("import javax.persistence.Entity");
		buffer.append('\n');
		buffer.append("@Entity");
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
		buffer.append('}');
		buffer.append('\n');
		return "" + buffer + "";
	}

	@Override
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

		ca.visitEnd();

		return cw.toByteArray();
	}

}

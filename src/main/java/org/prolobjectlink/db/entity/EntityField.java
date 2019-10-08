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
package org.prolobjectlink.db.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureVisitor;
import org.objectweb.asm.signature.SignatureWriter;
import org.prolobjectlink.db.DatabaseClass;
import org.prolobjectlink.db.DatabaseField;
import org.prolobjectlink.db.Modifier;
import org.prolobjectlink.db.Schema;

public class EntityField extends DatabaseField {

	private static final long serialVersionUID = 5995411521453113340L;

	public EntityField(String name, String comment, int position, Class<?> type, Schema schema, DatabaseClass owner) {
		super(name, comment, position, type, schema, owner);
	}

	@Override
	public void generateField(StringBuilder buffer) {
		int index = typeName.lastIndexOf('.');
		String n = typeName.substring(index + 1);
		buffer.append('\t');
		buffer.append(Modifier.PRIVATE);
		buffer.append(' ');
		buffer.append(n);
		if (hasLinkedTypeName()) {
			buffer.append('<');
			buffer.append(getLinkedTypeShortName());
			buffer.append('>');
		}
		buffer.append(' ');
		buffer.append(getName());
		buffer.append(';');
		buffer.append('\n');
	}

	/**
	 * Create a field in byte code instruction
	 * 
	 * @param cv class writer to field declaration
	 * @since 1.0
	 */
	@Override
	public void createField(ClassVisitor cv) {
		FieldVisitor fv = null;
		if (isList(getType()) || isSet(getType()) || isCollection(getType())) {

			SignatureVisitor sv = new SignatureWriter();
			SignatureVisitor psv = sv.visitParameterType();
			psv.visitClassType(Type.getInternalName(List.class));
			SignatureVisitor ppsv = psv.visitTypeArgument('=');
			ppsv.visitClassType(Type.getInternalName(String.class));
			ppsv.visitEnd();
			// psv.visitEnd();
			sv.visitEnd();

			// remove not valid parenthesis at start
			String sig = sv.toString().substring(1);

			fv = cv.visitField(Opcodes.ACC_PRIVATE, getName(), Type.getDescriptor(getType()), sig, null);
		} else {
			fv = cv.visitField(Opcodes.ACC_PRIVATE, getName(), Type.getDescriptor(getType()), null, null);
		}

		if (isPrimaryKey()) {
			fv.visitAnnotation(Type.getDescriptor(Id.class), true).visitEnd();
			fv.visitAnnotation(Type.getDescriptor(GeneratedValue.class), true).visitEnd();
		}
		if (isOneToOne()) {
			fv.visitAnnotation(Type.getDescriptor(OneToOne.class), true).visitEnd();
		}
		if (isOneToMany()) {
			fv.visitAnnotation(Type.getDescriptor(OneToMany.class), true).visitEnd();
		}
		if (isManyToOne()) {
			fv.visitAnnotation(Type.getDescriptor(ManyToOne.class), true).visitEnd();
		}
		if (isManyToMany()) {
			fv.visitAnnotation(Type.getDescriptor(ManyToMany.class), true).visitEnd();
		}
		fv.visitEnd();
	}

	public final boolean isList(Class<?> clazz) {
		return clazz.isAssignableFrom(List.class);
	}

	public final boolean isMap(Class<?> clazz) {
		return clazz.isAssignableFrom(Map.class);
	}

	public final boolean isSet(Class<?> clazz) {
		return clazz.isAssignableFrom(Set.class);
	}

	public final boolean isCollection(Class<?> clazz) {
		return clazz.isAssignableFrom(Collection.class);
	}

	@Override
	public void generateSetter(StringBuilder buffer) {
		String fieldName = getName();
		char n = Character.toUpperCase(fieldName.charAt(0));
		String fname = n + fieldName.substring(1);
		buffer.append('\t');
		buffer.append(Modifier.PUBLIC);
		buffer.append(' ');
		buffer.append("void");
		buffer.append(' ');
		buffer.append("set");
		buffer.append(fname);
		buffer.append('(');
		buffer.append(getType().getSimpleName());
		if (hasLinkedTypeName()) {
			buffer.append('<');
			buffer.append(getLinkedTypeShortName());
			buffer.append('>');
		}
		buffer.append(' ');
		buffer.append(fieldName);
		buffer.append(')');
		buffer.append('{');
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('\t');
		buffer.append("this");
		buffer.append(".");
		buffer.append(fieldName);
		buffer.append(' ');
		buffer.append('=');
		buffer.append(' ');
		buffer.append(fieldName);
		buffer.append(';');
		buffer.append('\n');
		buffer.append('\t');
		buffer.append('}');
		buffer.append('\n');
		buffer.append('\n');
	}

	@Override
	public void createSetter(ClassVisitor cv, String className, String type, Class<?> c) {

		// for jpl 3.x.x compatibility
		// not support wrapper class in
		// method calling

		String supportedType = type;
		if (supportedType.equals(Type.getDescriptor(Integer.class))) {
			supportedType = Type.getDescriptor(int.class);
			String methodName = "set" + getName().substring(0, 1).toUpperCase() + getName().substring(1);
			MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC, methodName, "(" + supportedType + ")V", null, null);
			mv.visitCode();
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(Integer.class));
			mv.visitInsn(Opcodes.DUP);
			mv.visitVarInsn(Opcodes.ILOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(Integer.class), "<init>",
					Type.getMethodDescriptor(Type.VOID_TYPE, Type.getType(int.class)), false);
			mv.visitFieldInsn(Opcodes.PUTFIELD, className, getName(), type);
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(4, 2);
			mv.visitEnd();
		} else if (supportedType.equals(Type.getDescriptor(Float.class))) {
			supportedType = Type.getDescriptor(float.class);
			String methodName = "set" + getName().substring(0, 1).toUpperCase() + getName().substring(1);
			MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC, methodName, "(" + supportedType + ")V", null, null);
			mv.visitCode();
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(Float.class));
			mv.visitInsn(Opcodes.DUP);
			mv.visitVarInsn(Opcodes.FLOAD, 1);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(Float.class), "<init>",
					Type.getMethodDescriptor(Type.VOID_TYPE, Type.getType(float.class)), false);
			mv.visitFieldInsn(Opcodes.PUTFIELD, className, getName(), type);
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(4, 2);
			mv.visitEnd();
		} else {
			String methodName = "set" + getName().substring(0, 1).toUpperCase() + getName().substring(1);
			MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC, methodName, "(" + type + ")V", null, null);
			mv.visitCode();
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(Type.getType(c).getOpcode(Opcodes.ILOAD), Type.getType(c).getSize());
			mv.visitFieldInsn(Opcodes.PUTFIELD, className, getName(), type);
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
		}

	}

}

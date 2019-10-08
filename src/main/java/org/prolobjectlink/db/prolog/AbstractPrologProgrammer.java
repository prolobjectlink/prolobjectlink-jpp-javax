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
package org.prolobjectlink.db.prolog;

import java.io.PrintWriter;
import java.lang.reflect.Field;

import javax.persistence.Id;

import org.prolobjectlink.prolog.ArrayIterator;
import org.prolobjectlink.prolog.PrologProvider;

public abstract class AbstractPrologProgrammer extends AbstractProgrammer implements PrologProgrammer {

	protected AbstractPrologProgrammer(PrologProvider provider) {
		super(provider);
	}

	public final void codingActiveRecord(PrintWriter programmer, String fileName) {
		String modelName = fileName.replace('/', '_');
		programmer.print(modelName + "_query_one(ARG0, OUT) :- \n");
		programmer.print("\t" + modelName + "_dao(DAO),\n");
		programmer.print("\t" + modelName + "_dao_query_one(DAO, ARG0, OUT),\n");
		programmer.print("\t" + modelName + "_dao_close(DAO).\n\n");

		programmer.print(modelName + "_query_all(ARG0, OUT) :- \n");
		programmer.print("\t" + modelName + "_dao(DAO),\n");
		programmer.print("\t" + modelName + "_dao_query_all(DAO, ARG0, OUT),\n");
		programmer.print("\t" + modelName + "_dao_close(DAO).\n\n");

		programmer.print(modelName + "_query_all(ARG0, ARG1, ARG2, OUT) :- \n");
		programmer.print("\t" + modelName + "_dao(DAO),\n");
		programmer.print("\t" + modelName + "_dao_query_all(DAO, ARG0, ARG1, ARG2, OUT),\n");
		programmer.print("\t" + modelName + "_dao_close(DAO).\n\n");

		programmer.print(modelName + "_retrieve_one(ARG0, OUT) :- \n");
		programmer.print("\t" + modelName + "_dao(DAO),\n");
		programmer.print("\t" + modelName + "_dao_retrieve_one(DAO, ARG0, OUT),\n");
		programmer.print("\t" + modelName + "_dao_close(DAO).\n\n");

		programmer.print(modelName + "_retrieve_all(OUT) :- \n");
		programmer.print("\t" + modelName + "_dao(DAO),\n");
		programmer.print("\t" + modelName + "_dao_retrieve_all(DAO, OUT),\n");
		programmer.print("\t" + modelName + "_dao_close(DAO).\n\n");

		programmer.print(modelName + "_retrieve_all(ARG0, ARG1, OUT) :- \n");
		programmer.print("\t" + modelName + "_dao(DAO),\n");
		programmer.print("\t" + modelName + "_dao_retrieve_all(DAO, ARG0, ARG1, OUT),\n");
		programmer.print("\t" + modelName + "_dao_close(DAO).\n\n");
	}

	public final void codingProcedures(PrintWriter programmer, String fileName, String clsName, Class<?> cls) {

		String modelName = fileName.replace('/', '_');

		String id = null;
		StringBuilder create = new StringBuilder();
		StringBuilder update = new StringBuilder();
		StringBuilder code = new StringBuilder();
		Field[] fields = cls.getDeclaredFields();
		ArrayIterator<Field> i = new ArrayIterator<>(fields);
		if (i.hasNext()) {
			while (i.hasNext()) {
				Field field = i.next();
				String fieldname = fromCamelCase(field.getName());
				String FIELDNAME = field.getName().toUpperCase();
				if (field.isAnnotationPresent(Id.class)) {
					id = FIELDNAME;
				} else {
					create.append(FIELDNAME);
					Class<?> type = field.getType();
					if (type == Integer.class || type == int.class) {
						code.append("\tinteger_parse_int(" + FIELDNAME + ", " + FIELDNAME + "_INT_VALUE),\n");
						code.append(
								"\t" + modelName + "_set_" + fieldname + "(ENTITY, " + FIELDNAME + "_INT_VALUE),\n");
					} else if (type == Float.class || type == float.class) {
						code.append("\tfloat_parse_float(" + FIELDNAME + ", " + FIELDNAME + "_FLOAT_VALUE),\n");
						code.append(
								"\t" + modelName + "_set_" + fieldname + "(ENTITY, " + FIELDNAME + "_FLOAT_VALUE),\n");
					} else {
						code.append("\t" + modelName + "_set_" + fieldname + "(ENTITY, " + FIELDNAME + "),\n");
					}
					if (i.hasNext()) {
						create.append(',');
						create.append(' ');
					}
				}
				update.append(FIELDNAME);
				if (i.hasNext()) {
					update.append(',');
					update.append(' ');
				}
			}

		}

		programmer.print(modelName + "_new(_) :- \n");
		programmer.print("\trender('view/" + modelName + "/new.html').\n\n");

		programmer.print(modelName + "_edit(" + id + ", ENTITY) :- \n");
		programmer.print("\tinteger_parse_int(" + id + ", A),\n");
		programmer.print("\t" + modelName + "_retrieve_one(A, ENTITY),\n");
		programmer.print("\trender('view/" + modelName + "/edit.html').\n\n");

		programmer.print(modelName + "_find_all(LIST) :- \n");
		programmer.print("\t" + modelName + "_retrieve_all(LIST),\n");
		programmer.print("\trender('view/" + modelName + "/list.html').\n\n");

		programmer.print(modelName + "_query_all(LIST) :- \n");
		programmer.print("\t" + modelName + "_query_all('select a from " + clsName + " a', LIST),\n");
		programmer.print("\trender('view/" + modelName + "/list.html').\n\n");

		programmer.print(modelName + "_find_all_range(MAX, FIRST, LIST) :- \n");
		programmer.print("\tinteger_parse_int(MAX, A),\n");
		programmer.print("\tinteger_parse_int(FIRST, B),\n");
		programmer.print("\t" + modelName + "_retrieve_all(A, B, LIST),\n");
		programmer.print("\trender('view/" + modelName + "/list.html').\n\n");

		programmer.print(modelName + "_query_all_range(MAX, FIRST, LIST) :- \n");
		programmer.print("\tinteger_parse_int(MAX, A),\n");
		programmer.print("\tinteger_parse_int(FIRST, B),\n");
		programmer.print("\t" + modelName + "_query_all('select a from " + clsName + " a', A, B, LIST),\n");
		programmer.print("\trender('view/" + modelName + "/list.html').\n\n");

		programmer.print(modelName + "_find(" + id + ", ENTITY) :- \n");
		programmer.print("\tinteger_parse_int(" + id + ", A),\n");
		programmer.print("\t" + modelName + "_retrieve_one(A, ENTITY),\n");
		programmer.print("\trender('view/" + modelName + "/show.html').\n\n");

		programmer.print(modelName + "_query(" + id + ", ENTITY) :- \n");
		programmer.print("\tatom_concat('select a from " + clsName + " a where a.id =', " + id + ", QUERY),\n");
		programmer.print("\t" + modelName + "_query_one(QUERY, ENTITY),\n");
		programmer.print("\trender(\'view/" + modelName + "/show.html\').\n\n");

		programmer.print(modelName + "_update(" + update + ", ENTITY) :- \n");
		programmer.print("\tinteger_parse_int(" + id + ", A),\n");
		programmer.print("\t" + modelName + "_retrieve_one(A, ENTITY),\n");
		programmer.print(code);
		programmer.print("\t" + modelName + "_update(ENTITY),\n");
		programmer.print("\trender('view/" + modelName + "/show.html').\n\n");

		programmer.print(modelName + "_create(" + create + ", ENTITY) :- \n");
		programmer.print("\t" + modelName + "(ENTITY),\n");
		programmer.print(code);
		programmer.print("\t" + modelName + "_create(ENTITY),\n");
		programmer.print("\trender('view/" + modelName + "/show.html').\n\n");

		programmer.print(modelName + "_delete(" + id + ", ENTITY) :- \n");
		programmer.print("\tinteger_parse_int(" + id + ", A),\n");
		programmer.print("\t" + modelName + "_retrieve_one(A, ENTITY),\n");
		programmer.print("\t" + modelName + "_delete(ENTITY),\n");
		programmer.print("\t" + modelName + "_retrieve_all(ENTITY),\n");
		programmer.print("\trender('view/" + modelName + "/list.html').\n\n");

	}

}

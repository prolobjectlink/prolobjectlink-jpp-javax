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

import java.io.File;

public interface WebApplication {

	/**
	 * Automatically validates or exports schema DDL to the database when the
	 * SessionFactory is created. With create-drop, the database schema will be
	 * dropped when the SessionFactory is closed explicitly. e.g. validate | update
	 * | create | create-drop
	 */
	public static final String HBM2DDL = "hibernate.hbm2ddl.auto";
	public static final String DEFAULT_HBM2DDL = "update";

	public static final String SQLSHOW = "hibernate.show_sql";
	public static final String DEFAULT_SQLSHOW = "true";

	public static final String SQLFORMAT = "hibernate.format_sql";
	public static final String DEFAULT_SQLFORMAT = "true";

	public static final String DATABASE_ACTION = "javax.persistence.schema-generation.database.action";
	public static final String SCRIPT_ACTION = "javax.persistence.schema-generation.scripts.action";

	public static final String DRIVER = "javax.persistence.jdbc.driver";
	public static final String URL = "javax.persistence.jdbc.url";
	public static final String USER = "javax.persistence.jdbc.user";
	public static final String PWD = "javax.persistence.jdbc.password";

	public static final String DEFAULT_PROVIDER = "org.hibernate.jpa.HibernatePersistenceProvider";
	public static final String DEFAULT_DRIVER = "org.postgresql.Driver";
	public static final String DEFAULT_ACTION = "drop-and-create";
	public static final String DEFAULT_USER = "sa";
	public static final String DEFAULT_PWD = "";

	public static final String ROOT = "web";

	public String getCurrentPath();

	public File getWebDirectory();

}

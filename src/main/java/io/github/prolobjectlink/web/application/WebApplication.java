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

public interface WebApplication {

	// jpa basic properties
	public static final String DRIVER = "javax.persistence.jdbc.driver";
	public static final String URL = "javax.persistence.jdbc.url";
	public static final String USER = "javax.persistence.jdbc.user";
	public static final String PWD = "javax.persistence.jdbc.password";

	public static final String DATABASE_ACTION = "javax.persistence.schema-generation.database.action";
	public static final String SCRIPT_ACTION = "javax.persistence.schema-generation.scripts.action";

	// HIBERNATE
	// Automatically validates or exports schema DDL to the database when the
	// SessionFactory is created. With create-drop, the database schema will be
	// dropped when the SessionFactory is closed explicitly. e.g. validate | update
	// | create | create-drop

	public static final String HBM2DDL = "hibernate.hbm2ddl.auto";
	public static final String DEFAULT_HBM2DDL = "update";
	public static final String SQLSHOW = "hibernate.show_sql";
	public static final String DEFAULT_SQLSHOW = "false";
	public static final String SQLFORMAT = "hibernate.format_sql";
	public static final String DEFAULT_SQLFORMAT = "false";
	public static final String DEFAULT_ACTION = "drop-and-create";
	public static final String DIALECT = "hibernate.dialect";

	// ECLIPSELINK
	public static final String LOGGING_LEVEL = "eclipselink.logging.level";
	public static final String DEFAULT_LOGGING_LEVEL = "off";
	public static final String DDL_GENERATION = "eclipselink.ddl-generation";
	public static final String DEFAULT_DDL_GENERATION = "create-tables";
	public static final String TARGET_DATABASE = "eclipselink.target-database";

	// Default Actions
	public static final String DEFAULT_PROVIDER = "org.hibernate.jpa.HibernatePersistenceProvider";
	public static final String DEFAULT_DRIVER = "org.postgresql.Driver";
	public static final String DEFAULT_USER = "sa";
	public static final String DEFAULT_PWD = "";

	// OPENJPA
	public static final String LOG = "openjpa.Log";
	public static final String DEFAULT_LOG = "DefaultLevel=ERROR";
	public static final String DATACACHE = "openjpa.DataCache";
	public static final String DEFAULT_DATACACHE = "true";
	public static final String REMOTE_COMMIT_PROVIDER = "openjpa.RemoteCommitProvider";
	public static final String DEFAULT_REMOTE_COMMIT_PROVIDER = "sjvm";
	public static final String INITIALIZE_EAGERLY = "openjpa.InitializeEagerly";
	public static final String DEFAULT_INITIALIZE_EAGERLY = "true";
	public static final String DYNAMIC_ENHANCEMENT_AGENT = "openjpa.DynamicEnhancementAgent";
	public static final String DEFAULT_DYNAMIC_ENHANCEMENT_AGENT = "true";
	public static final String RUNTIME_UNENHANCED_CLASSES = "openjpa.RuntimeUnenhancedClasses";
	public static final String DEFAULT_RUNTIME_UNENHANCED_CLASSES = "supported";
	public static final String SYNCHRONIZE_MAPPINGS = "openjpa.jdbc.SynchronizeMappings";
	public static final String DEFAULT_SYNCHRONIZE_MAPPINGS = "buildSchema(ForeignKeys=true)";
	public static final String QUERY_SQL_CACHE = "openjpa.jdbc.QuerySQLCache";
	public static final String DEFAULT_QUERY_SQL_CACHE = "true(EnableStatistics=true)";

	public static final String ROOT = "web";

	public String getCurrentPath();

	public File getWebDirectory();

}

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
package io.github.prolobjectlink.web.servlet;

import static io.github.prolobjectlink.db.XmlParser.XML;
import static io.github.prolobjectlink.logging.LoggerConstants.IO;
import static io.github.prolobjectlink.prolog.PrologLogger.FILE_NOT_FOUND;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.Servlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import io.github.prolobjectlink.ArrayQueue;
import io.github.prolobjectlink.db.jpa.spi.JPAPersistenceSchemaVersion;
import io.github.prolobjectlink.db.jpa.spi.JPAPersistenceUnitInfo;
import io.github.prolobjectlink.db.jpa.spi.JPAPersistenceVersion;
import io.github.prolobjectlink.db.jpa.spi.JPAPersistenceXmlParser;
import io.github.prolobjectlink.db.util.JavaReflect;
import io.github.prolobjectlink.logging.LoggerConstants;
import io.github.prolobjectlink.logging.LoggerUtils;
import io.github.prolobjectlink.prolog.PrologList;
import io.github.prolobjectlink.prolog.PrologStructure;
import io.github.prolobjectlink.prolog.PrologTerm;
import io.github.prolobjectlink.web.application.AbstractControllerGenerator;
import io.github.prolobjectlink.web.application.WebApplication;
import io.github.prolobjectlink.web.entry.ApplicationEntry;
import io.github.prolobjectlink.web.entry.ClassesEntry;
import io.github.prolobjectlink.web.entry.DatabaseEntry;
import io.github.prolobjectlink.web.entry.FieldEntry;
import io.github.prolobjectlink.web.entry.LibraryEntry;
import io.github.prolobjectlink.web.etc.UserManagement;

public abstract class AbstractServlet extends HttpServlet implements Servlet {

	protected static final String CONTENT_TYPE = "application/x-download";
	private static final String SALT = "LVbjyjFASEHoGvuGVaEdbXPwdltkwkvU";
	private static final long serialVersionUID = 4877024796708886136L;
	protected static final long MAX_IO_BUFFER_SIZE = Long.MAX_VALUE;
	protected static final int IO_BUFFER_SIZE = 4 * 1024;

	public final String getUserName() {
		return System.getProperty("user.name");
	}

	public final String getJavaVersion() {
		return System.getProperty("java.version");
	}

	public final String getJavaVendor() {
		return System.getProperty("java.vendor");
	}

	public final String getJavaName() {
		return System.getProperty("java.vm.name");
	}

	public final String getJavaHome() {
		return System.getProperty("java.home");
	}

	public final boolean runOnOsX() {
		return getOsName().equals("Mac OS X") || getOsName().equals("Darwin");
	}

	public final boolean runOnWindows() {
		return getOsName().startsWith("Windows");
	}

	public final boolean runOnLinux() {
		return getOsName().equals("Linux");
	}

	public final String getOsName() {
		String os = System.getProperty("os.name");
		if (os == null)
			return "unknow";
		return os;
	}

	public final String getClassPath() {
		return System.getenv("java.class.path");
	}

	public final String getPath() {
		return System.getenv("Path");
	}

	public final String getPathSeparator() {
		return System.getProperty("path.separator");
	}

	public final String getPathExt() {
		return System.getenv("PATHEXT");
	}

	public final Runtime getRuntime() {
		return Runtime.getRuntime();
	}

	public final String getUserHome() {
		return System.getProperty("user.home");
	}

	public final String getUserDir() {
		return System.getProperty("user.dir");
	}

	public final String getWorkDir() {
		return System.getProperty("user.dir");
	}

	public final String getTempDir() {
		return System.getProperty("java.io.tmpdir");
	}

	public final String getArch() {
		return System.getProperty("os.arch");
	}

	public final File getBinDirectory() {
		File appRoot = null;
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		File prt = pdk.getParentFile();
		try {
			if (!prt.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
				// production mode
				appRoot = new File(prt.getCanonicalPath() + File.separator + "bin");
			} else {
				// development mode
				appRoot = new File("bin");
			}
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return appRoot;
	}

	public final File getDBDirectory() {
		File appRoot = null;
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		File prt = pdk.getParentFile();
		try {
			if (!prt.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
				// production mode
				appRoot = new File(prt.getCanonicalPath() + File.separator + "db");
			} else {
				// development mode
				appRoot = new File("db");
			}
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return appRoot;
	}

	public final File getETCDirectory() {
		File appRoot = null;
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		File prt = pdk.getParentFile();
		try {
			if (!prt.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
				// production mode
				appRoot = new File(prt.getCanonicalPath() + File.separator + "etc");
			} else {
				// development mode
				appRoot = new File("etc");
			}
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return appRoot;
	}

	public final File getLibDirectory() {
		File appRoot = null;
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		File prt = pdk.getParentFile();
		try {
			if (!prt.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
				// production mode
				appRoot = new File(prt.getCanonicalPath() + File.separator + "lib");
			} else {
				// development mode
				appRoot = new File("lib");
			}
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return appRoot;
	}

	public final File getMiscDirectory() {
		File appRoot = null;
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		File prt = pdk.getParentFile();
		try {
			if (!prt.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
				// production mode
				appRoot = new File(prt.getCanonicalPath() + File.separator + "misc");
			} else {
				// development mode
				appRoot = new File("misc");
			}
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return appRoot;
	}

	public final File getPrtDirectory() {
		File appRoot = null;
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		File prt = pdk.getParentFile();
		try {
			if (!prt.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
				// production mode
				appRoot = new File(prt.getCanonicalPath() + File.separator + "prt");
			} else {
				// development mode
				appRoot = new File("prt");
			}
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return appRoot;
	}

	public final File getWebDirectory() {
		File appRoot = null;
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		File prt = pdk.getParentFile();
		try {
			if (!prt.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
				// production mode
				appRoot = new File(prt.getCanonicalPath() + File.separator + WebApplication.ROOT);
			} else {
				// development mode
				appRoot = new File(WebApplication.ROOT);
			}
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return appRoot;
	}

	public final File getDistDirectory() {
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		return pdk.getParentFile();
	}

	public final String getCurrentPath() {
		Class<?> c = AbstractControllerGenerator.class;
		ProtectionDomain d = c.getProtectionDomain();
		CodeSource s = d.getCodeSource();
		return s.getLocation().getPath();
	}

	protected final boolean isValidJarEntry(String name) {
		if (!name.endsWith(".class") || name.contains("$")) {
			return false;
		}
		return true;
	}

	public final String getLicense() {
		FileReader reader = null;
		BufferedReader buffer = null;
		StringBuilder b = new StringBuilder();
		try {
			File in = new File(getDistDirectory().getCanonicalPath() + File.separator + "LICENSE.md");
			reader = new FileReader(in);
			buffer = new BufferedReader(reader);
			String line = buffer.readLine();
			while (line != null) {
				b.append(line);
				line = buffer.readLine();
			}
		} catch (FileNotFoundException e) {
			LoggerUtils.error(getClass(), FILE_NOT_FOUND, e);
		} catch (IOException e) {
			LoggerUtils.error(getClass(), IO, e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO, e);
				}
			}
			if (buffer != null) {
				try {
					buffer.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO, e);
				}
			}
		}

		return b.toString();
	}

	public final synchronized long copy(InputStream in, OutputStream out) {
		long copied = 0;
		try {
			long length = MAX_IO_BUFFER_SIZE;
			int len = (int) Math.min(length, IO_BUFFER_SIZE);
			byte[] buffer = new byte[len];
			while (length > 0) {
				len = in.read(buffer, 0, len);
				if (len < 0) {
					break;
				}
				if (out != null) {
					out.write(buffer, 0, len);
				}
				copied += len;
				length -= len;
				len = (int) Math.min(length, IO_BUFFER_SIZE);
			}
			return copied;
		} catch (Exception e) {
			LoggerUtils.error(getClass(), "Some error occurss on copy", e);
		}
		return copied;
	}

	public final List<ApplicationEntry> listApplications() {
		List<ApplicationEntry> applications = new ArrayList<ApplicationEntry>();
		File webapps = getWebDirectory();
		if (webapps != null) {
			File[] apps = webapps.listFiles();
			for (File file : apps) {
				// check application
				if (file.isDirectory()) {
					long size = file.length();
					String name = file.getName();
					long modified = file.lastModified();
					ApplicationEntry e = new ApplicationEntry(name, size, modified);
					applications.add(e);
				}
			}
		}
		return applications;
	}

	public final List<DatabaseEntry> listDatabases() {
		List<DatabaseEntry> databases = new ArrayList<DatabaseEntry>();
		File db = getDBDirectory();
		if (db != null) {
			if (db != null) {
				File[] dbs = db.listFiles();
				for (File file : dbs) {
					if (file.isDirectory()) {
						if (file.getName().equals("hsqldb")) {
							File[] scripts = file.listFiles(new FileFilter() {

								@Override
								public boolean accept(File arg0) {
									return arg0.getName().endsWith(".script");
								}

							});
							for (File x : scripts) {
								String type = "HSQLDB";
								long size = x.length();
								long modified = x.lastModified();
								String name = x.getName().substring(0, x.getName().lastIndexOf(".script"));
								DatabaseEntry e = new DatabaseEntry(type, name, size, modified);
								databases.add(e);
							}
						} else if (file.getName().equals("pdb")) {

						} else if (file.getName().equals("h2db")) {
							File[] scripts = file.listFiles(new FileFilter() {

								@Override
								public boolean accept(File arg0) {
									return arg0.getName().endsWith(".h2.db");
								}

							});
							for (File x : scripts) {
								String type = "H2";
								long size = x.length();
								long modified = x.lastModified();
								String name = x.getName().substring(0, x.getName().lastIndexOf(".h2.db"));
								DatabaseEntry e = new DatabaseEntry(type, name, size, modified);
								databases.add(e);
							}
						} else if (file.getName().equals("odb")) {
							File[] scripts = file.listFiles(new FileFilter() {

								@Override
								public boolean accept(File arg0) {
									return arg0.getName().endsWith(".odb");
								}

							});
							for (File x : scripts) {
								String type = "ODB";
								long size = x.length();
								long modified = x.lastModified();
								String name = x.getName().substring(0, x.getName().lastIndexOf(".odb"));
								DatabaseEntry e = new DatabaseEntry(type, name, size, modified);
								databases.add(e);
							}
						} else if (file.getName().equals("derby")) {
							File[] scripts = file.listFiles();
							for (File x : scripts) {
								String type = "Derby";
								long size = x.length();
								long modified = x.lastModified();
								String name = x.getName();
								DatabaseEntry e = new DatabaseEntry(type, name, size, modified);
								databases.add(e);
							}
						}
					}
				}
			}
		}
		return databases;

	}

	public final List<LibraryEntry> listLibraries() {
		List<LibraryEntry> list = new ArrayList<LibraryEntry>();
		File libfolder = getLibDirectory();
		if (libfolder != null) {
			File[] libraries = libfolder.listFiles();
			for (File file : libraries) {
				String name = file.getName();
				long size = file.getTotalSpace();
				LibraryEntry e = new LibraryEntry(name, size);
				list.add(e);
			}
		}
		return list;
	}

	public final long librariesSize() {
		long size = 0;
		File libfolder = getLibDirectory();
		if (libfolder != null) {
			File[] libraries = libfolder.listFiles();
			for (File file : libraries) {
				size += file.getUsableSpace();
			}
		}
		return size;
	}

	public final List<ClassesEntry> listModels() throws IOException {
		List<ClassesEntry> classes = new ArrayList<ClassesEntry>();
		File webapps = getWebDirectory();
		if (webapps != null) {
			File[] apps = webapps.listFiles();
			for (File file : apps) {
				// check application
				if (file.isDirectory()) {
					String appname = file.getCanonicalPath();
					File model = new File(appname + "/model.pl");
					ScriptEngineManager manager = new ScriptEngineManager();
					ScriptEngine engine = manager.getEngineByName("Prolog");
					try {
						engine.eval(new FileReader(model));
						engine.eval("?-findall(Name/Type/Fields,entity(Name,Type,Fields),List)");
						Object object = engine.get("List");
						Object[] array = (Object[]) object;
						for (Object terna : array) {
							PrologTerm term = (PrologTerm) terna;
							String name = term.getArgument(0).getArgument(0).getFunctor();
							ClassesEntry e = new ClassesEntry(file.getName(), "" + name + "");
							PrologList list = (PrologList) term.getArgument(1);
							for (PrologTerm field : list) {
								PrologStructure s = (PrologStructure) field;
								String fieldName = s.getArgument(0).getFunctor();
								String typeName = s.getArgument(1).getFunctor();
								FieldEntry f = new FieldEntry(fieldName, typeName);
								e.addField(f);
							}
							classes.add(e);
						}
					} catch (ScriptException e) {
						LoggerUtils.error(getClass(), LoggerConstants.SYNTAX_ERROR, e);
					}
				}
			}
		}
		return classes;
	}

	public final PersistenceUnitInfo listProperties(String database) {
		URL persistenceXml = Thread.currentThread().getContextClassLoader().getResource(XML);
		Map<String, PersistenceUnitInfo> persistenceUnits = new JPAPersistenceXmlParser()
				.parsePersistenceXml(persistenceXml);
		for (Entry<String, PersistenceUnitInfo> entry : persistenceUnits.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(database)) {
				PersistenceUnitInfo info = entry.getValue();
				String pcls = info.getPersistenceProviderClassName();
				Class<?> pc = JavaReflect.classForName(pcls);
				Object object = JavaReflect.newInstance(pc);
				assert object instanceof PersistenceProvider;
				return info;
			}
		}

		PersistenceUnitTransactionType transactionType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
		JPAPersistenceSchemaVersion schemaVersion = new JPAPersistenceSchemaVersion("2.0", "UTF-8");
		JPAPersistenceVersion persistenceVersion = new JPAPersistenceVersion(

				"2.0",

				"http://java.sun.com/xml/ns/persistence",

				"http://www.w3.org/2001/XMLSchema-instance",

				"http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd"

		);

		return new JPAPersistenceUnitInfo(persistenceXml, schemaVersion,

				persistenceVersion, "empty", transactionType);

	}

	/**
	 * Create backs up application file.
	 * 
	 * @param application the path to copy into zip backup file
	 */
	public final void createApplicationBackup(String application) {

		File filePtr = null;
		InputStream in = null;
		OutputStream out = null;
		ZipOutputStream zipOut = null;
		String temp = getTempDir();

		try {

			//
			File zipFile = new File(temp + File.separator + application);
			if (!zipFile.exists()) {
				File parent = zipFile.getParentFile();
				if (parent != null) {
					parent.mkdirs();
				}
			}

			//
			out = new FileOutputStream(zipFile);
			zipOut = new ZipOutputStream(out);
			zipOut.setComment("PAS Application Backup File");

			//
			Queue<File> queue = new ArrayQueue<File>();
			String appdir = getWebDirectory().getCanonicalPath();

			queue.offer(new File(appdir + File.separator + application));
			while (!queue.isEmpty()) {
				filePtr = queue.poll();
				if (filePtr.isDirectory()) {

					File[] files = filePtr.listFiles();
					if (files != null) {
						for (File file : files) {
							queue.offer(file);
						}
					}

				} else {

					String x = getWebDirectory().getPath() + File.separator;
					String path = filePtr.getPath().replace(x, "");
					in = new FileInputStream(filePtr);
					ZipEntry entry = new ZipEntry(path);
					zipOut.putNextEntry(entry);
					copy(in, zipOut);
					zipOut.closeEntry();

				}
			}

		} catch (IOException e) {
			LoggerUtils.error(getClass(), IO, e);
		} finally {
			if (zipOut != null) {
				try {
					zipOut.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO, e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO, e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO, e);
				}
			}
		}

	}

	/**
	 * Create backs up database file.
	 * 
	 * @param database the path to copy into zip backup file
	 */
	public final void createDatabaseBackup(String type, String database) {

		File filePtr = null;
		InputStream in = null;
		OutputStream out = null;
		ZipOutputStream zipOut = null;
		String temp = getTempDir();

		try {

			//
			File zipFile = new File(temp + File.separator + database);
			if (!zipFile.exists()) {
				File parent = zipFile.getParentFile();
				if (parent != null) {
					parent.mkdirs();
				}
			}

			//
			out = new FileOutputStream(zipFile);
			zipOut = new ZipOutputStream(out);
			zipOut.setComment("PAS Database Backup File");

			//
			Queue<File> queue = new ArrayQueue<File>();
			String dbdir = getDBDirectory().getCanonicalPath();

			if (type.equals("hsqldb")) {
				queue.offer(new File(dbdir + File.separator + "hsqldb"));
			} else {
				queue.offer(new File(dbdir + File.separator + "hsqldb"));
			}

			while (!queue.isEmpty()) {
				filePtr = queue.poll();
				if (filePtr.isDirectory()) {

					File[] files = filePtr.listFiles();
					if (files != null) {
						for (File file : files) {
							queue.offer(file);
						}
					}

				} else if (filePtr.getName().contains(database)) {

					String x = getWebDirectory().getPath() + File.separator;
					String path = filePtr.getPath().replace(x, "");
					in = new FileInputStream(filePtr);
					ZipEntry entry = new ZipEntry(path);
					zipOut.putNextEntry(entry);
					copy(in, zipOut);
					zipOut.closeEntry();

				}
			}

		} catch (IOException e) {
			LoggerUtils.error(getClass(), IO, e);
		} finally {
			if (zipOut != null) {
				try {
					zipOut.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO, e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO, e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO, e);
				}
			}
		}

	}

	public final void copyBackupFile(HttpServletResponse resp, String pathInfo) throws IOException {
		String temp = getTempDir();
		File zipFile = new File(temp + File.separator + pathInfo);

		ServletOutputStream outputStream = resp.getOutputStream();
		resp.setContentType(CONTENT_TYPE);
		resp.setContentLength((int) zipFile.length());
		resp.setHeader("Content-Disposition", "attachment; filename=" + pathInfo + ".zip");

		byte[] buffer = new byte[8 * 1024]; // 8k buffer
		FileInputStream input = new FileInputStream(zipFile);
		DataInputStream inputStream = new DataInputStream(input);
		int length = 0;

		try {
			while (((length = inputStream.read(buffer)) != -1)) {
				outputStream.write(buffer, 0, length);
			}
		} catch (Exception e) {
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			resp.setContentType("text/plain");
			resp.getOutputStream().print(stringWriter.toString());
		} finally {
			inputStream.close();
			outputStream.flush();
			outputStream.close();
		}
	}

	public void addUser(String user, String pwd) {
		UserManagement management = new UserManagement();
		management = management.load();
		// code
		management.put(user, pwd);
		management.save();
	}

	public String findUser(String user) {
		UserManagement management = new UserManagement();
		management = management.load();
		return (String) management.get(user);
	}

	public void removeUser(String user) {
		UserManagement management = new UserManagement();
		management = management.load();
		management.remove(user);
		management.save();
	}

	public Set<Entry<Object, Object>> listUsers() {
		UserManagement management = new UserManagement();
		management = management.load();
		return management.entrySet();
	}

	public String retrieveUserSalt() {
//		return RandomStringUtils.random(64);
		return SALT;
	}

}

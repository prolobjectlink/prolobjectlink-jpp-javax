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

import static io.github.prolobjectlink.logging.LoggerConstants.IO;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.Servlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import io.github.prolobjectlink.ArrayQueue;
import io.github.prolobjectlink.logging.LoggerConstants;
import io.github.prolobjectlink.logging.LoggerUtils;
import io.github.prolobjectlink.web.application.AbstractControllerGenerator;
import io.github.prolobjectlink.web.application.WebApplication;
import io.github.prolobjectlink.web.entry.ApplicationEntry;
import io.github.prolobjectlink.web.entry.DatabaseEntry;

public class AbstractServlet extends HttpServlet implements Servlet {

	protected static final String CONTENT_TYPE = "application/x-download";
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
					}
				}
			}
		}

		return databases;

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
		String temp = System.getProperty("java.io.tmpdir");

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

					String path = filePtr.getPath();
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
		String temp = System.getProperty("java.io.tmpdir");

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

					String path = filePtr.getPath();
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
		String temp = System.getProperty("java.io.tmpdir");
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

}

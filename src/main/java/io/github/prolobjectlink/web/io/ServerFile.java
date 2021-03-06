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
package io.github.prolobjectlink.web.io;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.security.CodeSource;
import java.security.ProtectionDomain;

import io.github.prolobjectlink.logging.LoggerConstants;
import io.github.prolobjectlink.logging.LoggerUtils;
import io.github.prolobjectlink.web.application.AbstractControllerGenerator;

public class ServerFile {

	private File javaFile;

	public ServerFile(String file) {
		File root = getRootDirectory();
		String location = root.getAbsolutePath();
		try {
			location = root.getCanonicalPath();
			javaFile = new File(location + File.separator + file);
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
	}

	/**
	 * Returns the name of the file or directory denoted by this abstract pathname.
	 * This is just the last name in the pathname's name sequence. If the pathname's
	 * name sequence is empty, then the empty string is returned.
	 *
	 * @return The name of the file or directory denoted by this abstract pathname,
	 *         or the empty string if this pathname's name sequence is empty
	 */
	public String getName() {
		return javaFile.getName();
	}

	/**
	 * Returns the pathname string of this abstract pathname's parent, or
	 * <code>null</code> if this pathname does not name a parent directory.
	 *
	 * <p>
	 * The <em>parent</em> of an abstract pathname consists of the pathname's
	 * prefix, if any, and each name in the pathname's name sequence except for the
	 * last. If the name sequence is empty then the pathname does not name a parent
	 * directory.
	 *
	 * @return The pathname string of the parent directory named by this abstract
	 *         pathname, or <code>null</code> if this pathname does not name a
	 *         parent
	 */
	public String getParent() {
		return javaFile.getParent();
	}

	/**
	 * Returns the abstract pathname of this abstract pathname's parent, or
	 * <code>null</code> if this pathname does not name a parent directory.
	 *
	 * <p>
	 * The <em>parent</em> of an abstract pathname consists of the pathname's
	 * prefix, if any, and each name in the pathname's name sequence except for the
	 * last. If the name sequence is empty then the pathname does not name a parent
	 * directory.
	 *
	 * @return The abstract pathname of the parent directory named by this abstract
	 *         pathname, or <code>null</code> if this pathname does not name a
	 *         parent
	 *
	 * @since 1.2
	 */
	public File getParentFile() {
		return javaFile.getParentFile();
	}

	/**
	 * Converts this abstract pathname into a pathname string. The resulting string
	 * uses the separator default name-separator character to separate the names in
	 * the name sequence.
	 *
	 * @return The string form of this abstract pathname
	 */
	public String getPath() {
		return javaFile.getPath();
	}

	/* -- Path operations -- */

	/**
	 * Tests whether this abstract pathname is absolute. The definition of absolute
	 * pathname is system dependent. On UNIX systems, a pathname is absolute if its
	 * prefix is <code>"/"</code>. On Microsoft Windows systems, a pathname is
	 * absolute if its prefix is a drive specifier followed by <code>"\\"</code>, or
	 * if its prefix is <code>"\\\\"</code>.
	 *
	 * @return <code>true</code> if this abstract pathname is absolute,
	 *         <code>false</code> otherwise
	 */
	public boolean isAbsolute() {
		return javaFile.isAbsolute();
	}

	/**
	 * Returns the absolute pathname string of this abstract pathname.
	 *
	 * <p>
	 * If this abstract pathname is already absolute, then the pathname string is
	 * simply returned as if by the <code>{@link #getPath}</code> method. If this
	 * abstract pathname is the empty abstract pathname then the pathname string of
	 * the current user directory, which is named by the system property
	 * <code>user.dir</code>, is returned. Otherwise this pathname is resolved in a
	 * system-dependent way. On UNIX systems, a relative pathname is made absolute
	 * by resolving it against the current user directory. On Microsoft Windows
	 * systems, a relative pathname is made absolute by resolving it against the
	 * current directory of the drive named by the pathname, if any; if not, it is
	 * resolved against the current user directory.
	 *
	 * @return The absolute pathname string denoting the same file or directory as
	 *         this abstract pathname
	 *
	 * @throws SecurityException If a required system property value cannot be
	 *                           accessed.
	 *
	 * @see java.io.File#isAbsolute()
	 */
	public String getAbsolutePath() {
		return javaFile.getAbsolutePath();
	}

	/**
	 * Returns the absolute form of this abstract pathname. Equivalent to
	 * <code>new&nbsp;File(this.{@link #getAbsolutePath})</code>.
	 *
	 * @return The absolute abstract pathname denoting the same file or directory as
	 *         this abstract pathname
	 *
	 * @throws SecurityException If a required system property value cannot be
	 *                           accessed.
	 *
	 * @since 1.2
	 */
	public File getAbsoluteFile() {
		return javaFile.getAbsoluteFile();
	}

	/**
	 * Returns the canonical pathname string of this abstract pathname.
	 *
	 * <p>
	 * A canonical pathname is both absolute and unique. The precise definition of
	 * canonical form is system-dependent. This method first converts this pathname
	 * to absolute form if necessary, as if by invoking the {@link #getAbsolutePath}
	 * method, and then maps it to its unique form in a system-dependent way. This
	 * typically involves removing redundant names such as <tt>"."</tt> and
	 * <tt>".."</tt> from the pathname, resolving symbolic links (on UNIX
	 * platforms), and converting drive letters to a standard case (on Microsoft
	 * Windows platforms).
	 *
	 * <p>
	 * Every pathname that denotes an existing file or directory has a unique
	 * canonical form. Every pathname that denotes a nonexistent file or directory
	 * also has a unique canonical form. The canonical form of the pathname of a
	 * nonexistent file or directory may be different from the canonical form of the
	 * same pathname after the file or directory is created. Similarly, the
	 * canonical form of the pathname of an existing file or directory may be
	 * different from the canonical form of the same pathname after the file or
	 * directory is deleted.
	 *
	 * @return The canonical pathname string denoting the same file or directory as
	 *         this abstract pathname
	 *
	 * @throws IOException       If an I/O error occurs, which is possible because
	 *                           the construction of the canonical pathname may
	 *                           require filesystem queries
	 *
	 * @throws SecurityException If a required system property value cannot be
	 *                           accessed, or if a security manager exists and its
	 *                           <code>{@link
	 *          java.lang.SecurityManager#checkRead}</code> method denies read
	 *                           access to the file
	 *
	 * @since JDK1.1
	 * @see Path#toRealPath
	 */
	public String getCanonicalPath() throws IOException {
		return javaFile.getCanonicalPath();
	}

	/**
	 * Returns the canonical form of this abstract pathname. Equivalent to
	 * <code>new&nbsp;File(this.{@link #getCanonicalPath})</code>.
	 *
	 * @return The canonical pathname string denoting the same file or directory as
	 *         this abstract pathname
	 *
	 * @throws IOException       If an I/O error occurs, which is possible because
	 *                           the construction of the canonical pathname may
	 *                           require filesystem queries
	 *
	 * @throws SecurityException If a required system property value cannot be
	 *                           accessed, or if a security manager exists and its
	 *                           <code>{@link
	 *          java.lang.SecurityManager#checkRead}</code> method denies read
	 *                           access to the file
	 *
	 * @since 1.2
	 * @see Path#toRealPath
	 */
	public File getCanonicalFile() throws IOException {
		return javaFile.getCanonicalFile();
	}

	/**
	 * Converts this abstract pathname into a <code>file:</code> URL. The exact form
	 * of the URL is system-dependent. If it can be determined that the file denoted
	 * by this abstract pathname is a directory, then the resulting URL will end
	 * with a slash.
	 *
	 * @return A URL object representing the equivalent file URL
	 *
	 * @throws MalformedURLException If the path cannot be parsed as a URL
	 *
	 * @see #toURI()
	 * @see java.net.URI
	 * @see java.net.URI#toURL()
	 * @see java.net.URL
	 * @since 1.2
	 *
	 * @deprecated This method does not automatically escape characters that are
	 *             illegal in URLs. It is recommended that new code convert an
	 *             abstract pathname into a URL by first converting it into a URI,
	 *             via the {@link #toURI() toURI} method, and then converting the
	 *             URI into a URL via the {@link java.net.URI#toURL() URI.toURL}
	 *             method.
	 */
	@Deprecated
	public URL toURL() throws MalformedURLException {
		return javaFile.toURL();
	}

	/**
	 * Constructs a <tt>file:</tt> URI that represents this abstract pathname.
	 *
	 * <p>
	 * The exact form of the URI is system-dependent. If it can be determined that
	 * the file denoted by this abstract pathname is a directory, then the resulting
	 * URI will end with a slash.
	 *
	 * <p>
	 * For a given abstract pathname <i>f</i>, it is guaranteed that
	 *
	 * <blockquote><tt>
	 * new File(java.net.URI) File(</tt><i>&nbsp;f</i><tt>.toURI()).equals(</tt><i>&nbsp;f</i><tt>.{@link #getAbsoluteFile() getAbsoluteFile}())
	 * </tt></blockquote>
	 *
	 * so long as the original abstract pathname, the URI, and the new abstract
	 * pathname are all created in (possibly different invocations of) the same Java
	 * virtual machine. Due to the system-dependent nature of abstract pathnames,
	 * however, this relationship typically does not hold when a <tt>file:</tt> URI
	 * that is created in a virtual machine on one operating system is converted
	 * into an abstract pathname in a virtual machine on a different operating
	 * system.
	 *
	 * <p>
	 * Note that when this abstract pathname represents a UNC pathname then all
	 * components of the UNC (including the server name component) are encoded in
	 * the {@code URI} path. The authority component is undefined, meaning that it
	 * is represented as {@code null}. The {@link Path} class defines the
	 * {@link Path#toUri toUri} method to encode the server name in the authority
	 * component of the resulting {@code URI}. The {@link #toPath toPath} method may
	 * be used to obtain a {@code Path} representing this abstract pathname.
	 *
	 * @return An absolute, hierarchical URI with a scheme equal to <tt>"file"</tt>,
	 *         a path representing this abstract pathname, and undefined authority,
	 *         query, and fragment components
	 * @throws SecurityException If a required system property value cannot be
	 *                           accessed.
	 *
	 * @see java.io.File
	 * @see java.net.URI
	 * @see java.net.URI#toURL()
	 * @since 1.4
	 */
	public URI toURI() {
		return javaFile.toURI();
	}

	/* -- Attribute accessors -- */

	/**
	 * Tests whether the application can read the file denoted by this abstract
	 * pathname. On some platforms it may be possible to start the Java virtual
	 * machine with special privileges that allow it to read files that are marked
	 * as unreadable. Consequently this method may return {@code true} even though
	 * the file does not have read permissions.
	 *
	 * @return <code>true</code> if and only if the file specified by this abstract
	 *         pathname exists <em>and</em> can be read by the application;
	 *         <code>false</code> otherwise
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkRead(java.lang.String)}</code> method
	 *                           denies read access to the file
	 */
	public boolean canRead() {
		return javaFile.canRead();
	}

	/**
	 * Tests whether the application can modify the file denoted by this abstract
	 * pathname. On some platforms it may be possible to start the Java virtual
	 * machine with special privileges that allow it to modify files that are marked
	 * read-only. Consequently this method may return {@code true} even though the
	 * file is marked read-only.
	 *
	 * @return <code>true</code> if and only if the file system actually contains a
	 *         file denoted by this abstract pathname <em>and</em> the application
	 *         is allowed to write to the file; <code>false</code> otherwise.
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
	 *                           method denies write access to the file
	 */
	public boolean canWrite() {
		return javaFile.canWrite();
	}

	/**
	 * Tests whether the file or directory denoted by this abstract pathname exists.
	 *
	 * @return <code>true</code> if and only if the file or directory denoted by
	 *         this abstract pathname exists; <code>false</code> otherwise
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkRead(java.lang.String)}</code> method
	 *                           denies read access to the file or directory
	 */
	public boolean exists() {
		return javaFile.exists();
	}

	/**
	 * Tests whether the file denoted by this abstract pathname is a directory.
	 *
	 * <p>
	 * Where it is required to distinguish an I/O exception from the case that the
	 * file is not a directory, or where several attributes of the same file are
	 * required at the same time, then the
	 * {@link java.nio.file.Files#readAttributes(Path,Class,LinkOption[])
	 * Files.readAttributes} method may be used.
	 *
	 * @return <code>true</code> if and only if the file denoted by this abstract
	 *         pathname exists <em>and</em> is a directory; <code>false</code>
	 *         otherwise
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkRead(java.lang.String)}</code> method
	 *                           denies read access to the file
	 */
	public boolean isDirectory() {
		return javaFile.isDirectory();
	}

	/**
	 * Tests whether the file denoted by this abstract pathname is a normal file. A
	 * file is <em>normal</em> if it is not a directory and, in addition, satisfies
	 * other system-dependent criteria. Any non-directory file created by a Java
	 * application is guaranteed to be a normal file.
	 *
	 * <p>
	 * Where it is required to distinguish an I/O exception from the case that the
	 * file is not a normal file, or where several attributes of the same file are
	 * required at the same time, then the
	 * {@link java.nio.file.Files#readAttributes(Path,Class,LinkOption[])
	 * Files.readAttributes} method may be used.
	 *
	 * @return <code>true</code> if and only if the file denoted by this abstract
	 *         pathname exists <em>and</em> is a normal file; <code>false</code>
	 *         otherwise
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkRead(java.lang.String)}</code> method
	 *                           denies read access to the file
	 */
	public boolean isFile() {
		return javaFile.isFile();
	}

	/**
	 * Tests whether the file named by this abstract pathname is a hidden file. The
	 * exact definition of <em>hidden</em> is system-dependent. On UNIX systems, a
	 * file is considered to be hidden if its name begins with a period character
	 * (<code>'.'</code>). On Microsoft Windows systems, a file is considered to be
	 * hidden if it has been marked as such in the filesystem.
	 *
	 * @return <code>true</code> if and only if the file denoted by this abstract
	 *         pathname is hidden according to the conventions of the underlying
	 *         platform
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkRead(java.lang.String)}</code> method
	 *                           denies read access to the file
	 *
	 * @since 1.2
	 */
	public boolean isHidden() {
		return javaFile.isHidden();
	}

	/**
	 * Returns the time that the file denoted by this abstract pathname was last
	 * modified.
	 *
	 * <p>
	 * Where it is required to distinguish an I/O exception from the case where
	 * {@code 0L} is returned, or where several attributes of the same file are
	 * required at the same time, or where the time of last access or the creation
	 * time are required, then the
	 * {@link java.nio.file.Files#readAttributes(Path,Class,LinkOption[])
	 * Files.readAttributes} method may be used.
	 *
	 * @return A <code>long</code> value representing the time the file was last
	 *         modified, measured in milliseconds since the epoch (00:00:00 GMT,
	 *         January 1, 1970), or <code>0L</code> if the file does not exist or if
	 *         an I/O error occurs
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkRead(java.lang.String)}</code> method
	 *                           denies read access to the file
	 */
	public long lastModified() {
		return javaFile.lastModified();
	}

	/**
	 * Returns the length of the file denoted by this abstract pathname. The return
	 * value is unspecified if this pathname denotes a directory.
	 *
	 * <p>
	 * Where it is required to distinguish an I/O exception from the case that
	 * {@code 0L} is returned, or where several attributes of the same file are
	 * required at the same time, then the
	 * {@link java.nio.file.Files#readAttributes(Path,Class,LinkOption[])
	 * Files.readAttributes} method may be used.
	 *
	 * @return The length, in bytes, of the file denoted by this abstract pathname,
	 *         or <code>0L</code> if the file does not exist. Some operating systems
	 *         may return <code>0L</code> for pathnames denoting system-dependent
	 *         entities such as devices or pipes.
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkRead(java.lang.String)}</code> method
	 *                           denies read access to the file
	 */
	public long length() {
		return javaFile.length();
	}

	/* -- File operations -- */

	/**
	 * Atomically creates a new, empty file named by this abstract pathname if and
	 * only if a file with this name does not yet exist. The check for the existence
	 * of the file and the creation of the file if it does not exist are a single
	 * operation that is atomic with respect to all other filesystem activities that
	 * might affect the file.
	 * <P>
	 * Note: this method should <i>not</i> be used for file-locking, as the
	 * resulting protocol cannot be made to work reliably. The
	 * {@link java.nio.channels.FileLock FileLock} facility should be used instead.
	 *
	 * @return <code>true</code> if the named file does not exist and was
	 *         successfully created; <code>false</code> if the named file already
	 *         exists
	 *
	 * @throws IOException       If an I/O error occurred
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
	 *                           method denies write access to the file
	 *
	 * @since 1.2
	 */
	public boolean createNewFile() throws IOException {
		return javaFile.createNewFile();
	}

	/**
	 * Deletes the file or directory denoted by this abstract pathname. If this
	 * pathname denotes a directory, then the directory must be empty in order to be
	 * deleted.
	 *
	 * <p>
	 * Note that the {@link java.nio.file.Files} class defines the
	 * {@link java.nio.file.Files#delete(Path) delete} method to throw an
	 * {@link IOException} when a file cannot be deleted. This is useful for error
	 * reporting and to diagnose why a file cannot be deleted.
	 *
	 * @return <code>true</code> if and only if the file or directory is
	 *         successfully deleted; <code>false</code> otherwise
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkDelete}</code> method denies delete
	 *                           access to the file
	 */
	public boolean delete() {
		return javaFile.delete();
	}

	/**
	 * Requests that the file or directory denoted by this abstract pathname be
	 * deleted when the virtual machine terminates. Files (or directories) are
	 * deleted in the reverse order that they are registered. Invoking this method
	 * to delete a file or directory that is already registered for deletion has no
	 * effect. Deletion will be attempted only for normal termination of the virtual
	 * machine, as defined by the Java Language Specification.
	 *
	 * <p>
	 * Once deletion has been requested, it is not possible to cancel the request.
	 * This method should therefore be used with care.
	 *
	 * <P>
	 * Note: this method should <i>not</i> be used for file-locking, as the
	 * resulting protocol cannot be made to work reliably. The
	 * {@link java.nio.channels.FileLock FileLock} facility should be used instead.
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkDelete}</code> method denies delete
	 *                           access to the file
	 *
	 * @see #delete
	 *
	 * @since 1.2
	 */
	public void deleteOnExit() {
		javaFile.deleteOnExit();
	}

	/**
	 * Returns an array of strings naming the files and directories in the directory
	 * denoted by this abstract pathname.
	 *
	 * <p>
	 * If this abstract pathname does not denote a directory, then this method
	 * returns {@code null}. Otherwise an array of strings is returned, one for each
	 * file or directory in the directory. Names denoting the directory itself and
	 * the directory's parent directory are not included in the result. Each string
	 * is a file name rather than a complete path.
	 *
	 * <p>
	 * There is no guarantee that the name strings in the resulting array will
	 * appear in any specific order; they are not, in particular, guaranteed to
	 * appear in alphabetical order.
	 *
	 * <p>
	 * Note that the {@link java.nio.file.Files} class defines the
	 * {@link java.nio.file.Files#newDirectoryStream(Path) newDirectoryStream}
	 * method to open a directory and iterate over the names of the files in the
	 * directory. This may use less resources when working with very large
	 * directories, and may be more responsive when working with remote directories.
	 *
	 * @return An array of strings naming the files and directories in the directory
	 *         denoted by this abstract pathname. The array will be empty if the
	 *         directory is empty. Returns {@code null} if this abstract pathname
	 *         does not denote a directory, or if an I/O error occurs.
	 *
	 * @throws SecurityException If a security manager exists and its
	 *                           {@link SecurityManager#checkRead(String)} method
	 *                           denies read access to the directory
	 */
	public String[] list() {
		return javaFile.list();
	}

	/**
	 * Returns an array of strings naming the files and directories in the directory
	 * denoted by this abstract pathname that satisfy the specified filter. The
	 * behavior of this method is the same as that of the {@link #list()} method,
	 * except that the strings in the returned array must satisfy the filter. If the
	 * given {@code filter} is {@code null} then all names are accepted. Otherwise,
	 * a name satisfies the filter if and only if the value {@code true} results
	 * when the {@link FilenameFilter#accept
	 * FilenameFilter.accept(File,&nbsp;String)} method of the filter is invoked on
	 * this abstract pathname and the name of a file or directory in the directory
	 * that it denotes.
	 *
	 * @param filter A filename filter
	 *
	 * @return An array of strings naming the files and directories in the directory
	 *         denoted by this abstract pathname that were accepted by the given
	 *         {@code filter}. The array will be empty if the directory is empty or
	 *         if no names were accepted by the filter. Returns {@code null} if this
	 *         abstract pathname does not denote a directory, or if an I/O error
	 *         occurs.
	 *
	 * @throws SecurityException If a security manager exists and its
	 *                           {@link SecurityManager#checkRead(String)} method
	 *                           denies read access to the directory
	 *
	 * @see java.nio.file.Files#newDirectoryStream(Path,String)
	 */
	public String[] list(FilenameFilter filter) {
		return javaFile.list(filter);
	}

	/**
	 * Returns an array of abstract pathnames denoting the files in the directory
	 * denoted by this abstract pathname.
	 *
	 * <p>
	 * If this abstract pathname does not denote a directory, then this method
	 * returns {@code null}. Otherwise an array of {@code File} objects is returned,
	 * one for each file or directory in the directory. Pathnames denoting the
	 * directory itself and the directory's parent directory are not included in the
	 * result. Each resulting abstract pathname is constructed from this abstract
	 * pathname using the File(File, String) File(File,&nbsp;String) constructor.
	 * Therefore if this pathname is absolute then each resulting pathname is
	 * absolute; if this pathname is relative then each resulting pathname will be
	 * relative to the same directory.
	 *
	 * <p>
	 * There is no guarantee that the name strings in the resulting array will
	 * appear in any specific order; they are not, in particular, guaranteed to
	 * appear in alphabetical order.
	 *
	 * <p>
	 * Note that the java.nio.file.Files class defines the
	 * {@link java.nio.file.Files#newDirectoryStream(Path) newDirectoryStream}
	 * method to open a directory and iterate over the names of the files in the
	 * directory. This may use less resources when working with very large
	 * directories.
	 *
	 * @return An array of abstract pathnames denoting the files and directories in
	 *         the directory denoted by this abstract pathname. The array will be
	 *         empty if the directory is empty. Returns {@code null} if this
	 *         abstract pathname does not denote a directory, or if an I/O error
	 *         occurs.
	 *
	 * @throws SecurityException If a security manager exists and its
	 *                           {@link SecurityManager#checkRead(String)} method
	 *                           denies read access to the directory
	 *
	 * @since 1.2
	 */
	public File[] listFiles() {
		return javaFile.listFiles();
	}

	/**
	 * Returns an array of abstract pathnames denoting the files and directories in
	 * the directory denoted by this abstract pathname that satisfy the specified
	 * filter. The behavior of this method is the same as that of the
	 * {@link #listFiles()} method, except that the pathnames in the returned array
	 * must satisfy the filter. If the given {@code filter} is {@code null} then all
	 * pathnames are accepted. Otherwise, a pathname satisfies the filter if and
	 * only if the value {@code true} results when the {@link FilenameFilter#accept
	 * FilenameFilter.accept(File,&nbsp;String)} method of the filter is invoked on
	 * this abstract pathname and the name of a file or directory in the directory
	 * that it denotes.
	 *
	 * @param filter A filename filter
	 *
	 * @return An array of abstract pathnames denoting the files and directories in
	 *         the directory denoted by this abstract pathname. The array will be
	 *         empty if the directory is empty. Returns {@code null} if this
	 *         abstract pathname does not denote a directory, or if an I/O error
	 *         occurs.
	 *
	 * @throws SecurityException If a security manager exists and its
	 *                           {@link SecurityManager#checkRead(String)} method
	 *                           denies read access to the directory
	 *
	 * @since 1.2
	 * @see java.nio.file.Files#newDirectoryStream(Path,String)
	 */
	public File[] listFiles(FilenameFilter filter) {
		return javaFile.listFiles(filter);
	}

	/**
	 * Returns an array of abstract pathnames denoting the files and directories in
	 * the directory denoted by this abstract pathname that satisfy the specified
	 * filter. The behavior of this method is the same as that of the
	 * {@link #listFiles()} method, except that the pathnames in the returned array
	 * must satisfy the filter. If the given {@code filter} is {@code null} then all
	 * pathnames are accepted. Otherwise, a pathname satisfies the filter if and
	 * only if the value {@code true} results when the {@link FileFilter#accept
	 * FileFilter.accept(File)} method of the filter is invoked on the pathname.
	 *
	 * @param filter A file filter
	 *
	 * @return An array of abstract pathnames denoting the files and directories in
	 *         the directory denoted by this abstract pathname. The array will be
	 *         empty if the directory is empty. Returns {@code null} if this
	 *         abstract pathname does not denote a directory, or if an I/O error
	 *         occurs.
	 *
	 * @throws SecurityException If a security manager exists and its
	 *                           {@link SecurityManager#checkRead(String)} method
	 *                           denies read access to the directory
	 *
	 * @since 1.2
	 * @see java.nio.file.Files#newDirectoryStream(Path,java.nio.file.DirectoryStream.Filter)
	 */
	public File[] listFiles(FileFilter filter) {
		return javaFile.listFiles(filter);
	}

	/**
	 * Creates the directory named by this abstract pathname.
	 *
	 * @return <code>true</code> if and only if the directory was created;
	 *         <code>false</code> otherwise
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
	 *                           method does not permit the named directory to be
	 *                           created
	 */
	public boolean mkdir() {
		return javaFile.mkdir();
	}

	/**
	 * Creates the directory named by this abstract pathname, including any
	 * necessary but nonexistent parent directories. Note that if this operation
	 * fails it may have succeeded in creating some of the necessary parent
	 * directories.
	 *
	 * @return <code>true</code> if and only if the directory was created, along
	 *         with all necessary parent directories; <code>false</code> otherwise
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkRead(java.lang.String)}</code> method
	 *                           does not permit verification of the existence of
	 *                           the named directory and all necessary parent
	 *                           directories; or if the <code>{@link
	 *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
	 *                           method does not permit the named directory and all
	 *                           necessary parent directories to be created
	 */
	public boolean mkdirs() {
		if (exists()) {
			return false;
		}
		if (mkdir()) {
			return true;
		}
		File canonFile = null;
		try {
			canonFile = getCanonicalFile();
		} catch (IOException e) {
			return false;
		}

		File parent = canonFile.getParentFile();
		return (parent != null && (parent.mkdirs() || parent.exists()) && canonFile.mkdir());
	}

	/**
	 * Renames the file denoted by this abstract pathname.
	 *
	 * <p>
	 * Many aspects of the behavior of this method are inherently
	 * platform-dependent: The rename operation might not be able to move a file
	 * from one filesystem to another, it might not be atomic, and it might not
	 * succeed if a file with the destination abstract pathname already exists. The
	 * return value should always be checked to make sure that the rename operation
	 * was successful.
	 *
	 * <p>
	 * Note that the {@link java.nio.file.Files} class defines the
	 * {@link java.nio.file.Files#move move} method to move or rename a file in a
	 * platform independent manner.
	 *
	 * @param dest The new abstract pathname for the named file
	 *
	 * @return <code>true</code> if and only if the renaming succeeded;
	 *         <code>false</code> otherwise
	 *
	 * @throws SecurityException    If a security manager exists and its
	 *                              <code>{@link
	 *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
	 *                              method denies write access to either the old or
	 *                              new pathnames
	 *
	 * @throws NullPointerException If parameter <code>dest</code> is
	 *                              <code>null</code>
	 */
	public boolean renameTo(File dest) {
		return javaFile.renameTo(dest);
	}

	/**
	 * Sets the last-modified time of the file or directory named by this abstract
	 * pathname.
	 *
	 * <p>
	 * All platforms support file-modification times to the nearest second, but some
	 * provide more precision. The argument will be truncated to fit the supported
	 * precision. If the operation succeeds and no intervening operations on the
	 * file take place, then the next invocation of the
	 * <code>{@link #lastModified}</code> method will return the (possibly
	 * truncated) <code>time</code> argument that was passed to this method.
	 *
	 * @param time The new last-modified time, measured in milliseconds since the
	 *             epoch (00:00:00 GMT, January 1, 1970)
	 *
	 * @return <code>true</code> if and only if the operation succeeded;
	 *         <code>false</code> otherwise
	 *
	 * @throws IllegalArgumentException If the argument is negative
	 *
	 * @throws SecurityException        If a security manager exists and its
	 *                                  <code>{@link
	 *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
	 *                                  method denies write access to the named file
	 *
	 * @since 1.2
	 */
	public boolean setLastModified(long time) {
		return javaFile.setLastModified(time);
	}

	/**
	 * Marks the file or directory named by this abstract pathname so that only read
	 * operations are allowed. After invoking this method the file or directory will
	 * not change until it is either deleted or marked to allow write access. On
	 * some platforms it may be possible to start the Java virtual machine with
	 * special privileges that allow it to modify files that are marked read-only.
	 * Whether or not a read-only file or directory may be deleted depends upon the
	 * underlying system.
	 *
	 * @return <code>true</code> if and only if the operation succeeded;
	 *         <code>false</code> otherwise
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
	 *                           method denies write access to the named file
	 *
	 * @since 1.2
	 */
	public boolean setReadOnly() {
		return javaFile.setReadOnly();
	}

	/**
	 * Sets the owner's or everybody's write permission for this abstract pathname.
	 * On some platforms it may be possible to start the Java virtual machine with
	 * special privileges that allow it to modify files that disallow write
	 * operations.
	 *
	 * <p>
	 * The {@link java.nio.file.Files} class defines methods that operate on file
	 * attributes including file permissions. This may be used when finer
	 * manipulation of file permissions is required.
	 *
	 * @param writable  If <code>true</code>, sets the access permission to allow
	 *                  write operations; if <code>false</code> to disallow write
	 *                  operations
	 *
	 * @param ownerOnly If <code>true</code>, the write permission applies only to
	 *                  the owner's write permission; otherwise, it applies to
	 *                  everybody. If the underlying file system can not distinguish
	 *                  the owner's write permission from that of others, then the
	 *                  permission will apply to everybody, regardless of this
	 *                  value.
	 *
	 * @return <code>true</code> if and only if the operation succeeded. The
	 *         operation will fail if the user does not have permission to change
	 *         the access permissions of this abstract pathname.
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
	 *                           method denies write access to the named file
	 *
	 * @since 1.6
	 */
	public boolean setWritable(boolean writable, boolean ownerOnly) {
		return javaFile.setWritable(writable, ownerOnly);
	}

	/**
	 * A convenience method to set the owner's write permission for this abstract
	 * pathname. On some platforms it may be possible to start the Java virtual
	 * machine with special privileges that allow it to modify files that disallow
	 * write operations.
	 *
	 * <p>
	 * An invocation of this method of the form <tt>file.setWritable(arg)</tt>
	 * behaves in exactly the same way as the invocation
	 *
	 * <pre>
	 * file.setWritable(arg, true)
	 * </pre>
	 *
	 * @param writable If <code>true</code>, sets the access permission to allow
	 *                 write operations; if <code>false</code> to disallow write
	 *                 operations
	 *
	 * @return <code>true</code> if and only if the operation succeeded. The
	 *         operation will fail if the user does not have permission to change
	 *         the access permissions of this abstract pathname.
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
	 *                           method denies write access to the file
	 *
	 * @since 1.6
	 */
	public boolean setWritable(boolean writable) {
		return setWritable(writable, true);
	}

	/**
	 * Sets the owner's or everybody's read permission for this abstract pathname.
	 * On some platforms it may be possible to start the Java virtual machine with
	 * special privileges that allow it to read files that are marked as unreadable.
	 *
	 * <p>
	 * The {@link java.nio.file.Files} class defines methods that operate on file
	 * attributes including file permissions. This may be used when finer
	 * manipulation of file permissions is required.
	 *
	 * @param readable  If <code>true</code>, sets the access permission to allow
	 *                  read operations; if <code>false</code> to disallow read
	 *                  operations
	 *
	 * @param ownerOnly If <code>true</code>, the read permission applies only to
	 *                  the owner's read permission; otherwise, it applies to
	 *                  everybody. If the underlying file system can not distinguish
	 *                  the owner's read permission from that of others, then the
	 *                  permission will apply to everybody, regardless of this
	 *                  value.
	 *
	 * @return <code>true</code> if and only if the operation succeeded. The
	 *         operation will fail if the user does not have permission to change
	 *         the access permissions of this abstract pathname. If
	 *         <code>readable</code> is <code>false</code> and the underlying file
	 *         system does not implement a read permission, then the operation will
	 *         fail.
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
	 *                           method denies write access to the file
	 *
	 * @since 1.6
	 */
	public boolean setReadable(boolean readable, boolean ownerOnly) {
		return javaFile.setReadable(readable, ownerOnly);
	}

	/**
	 * A convenience method to set the owner's read permission for this abstract
	 * pathname. On some platforms it may be possible to start the Java virtual
	 * machine with special privileges that allow it to read files that that are
	 * marked as unreadable.
	 *
	 * <p>
	 * An invocation of this method of the form <tt>file.setReadable(arg)</tt>
	 * behaves in exactly the same way as the invocation
	 *
	 * <pre>
	 * file.setReadable(arg, true)
	 * </pre>
	 *
	 * @param readable If <code>true</code>, sets the access permission to allow
	 *                 read operations; if <code>false</code> to disallow read
	 *                 operations
	 *
	 * @return <code>true</code> if and only if the operation succeeded. The
	 *         operation will fail if the user does not have permission to change
	 *         the access permissions of this abstract pathname. If
	 *         <code>readable</code> is <code>false</code> and the underlying file
	 *         system does not implement a read permission, then the operation will
	 *         fail.
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
	 *                           method denies write access to the file
	 *
	 * @since 1.6
	 */
	public boolean setReadable(boolean readable) {
		return setReadable(readable, true);
	}

	/**
	 * Sets the owner's or everybody's execute permission for this abstract
	 * pathname. On some platforms it may be possible to start the Java virtual
	 * machine with special privileges that allow it to execute files that are not
	 * marked executable.
	 *
	 * <p>
	 * The {@link java.nio.file.Files} class defines methods that operate on file
	 * attributes including file permissions. This may be used when finer
	 * manipulation of file permissions is required.
	 *
	 * @param executable If <code>true</code>, sets the access permission to allow
	 *                   execute operations; if <code>false</code> to disallow
	 *                   execute operations
	 *
	 * @param ownerOnly  If <code>true</code>, the execute permission applies only
	 *                   to the owner's execute permission; otherwise, it applies to
	 *                   everybody. If the underlying file system can not
	 *                   distinguish the owner's execute permission from that of
	 *                   others, then the permission will apply to everybody,
	 *                   regardless of this value.
	 *
	 * @return <code>true</code> if and only if the operation succeeded. The
	 *         operation will fail if the user does not have permission to change
	 *         the access permissions of this abstract pathname. If
	 *         <code>executable</code> is <code>false</code> and the underlying file
	 *         system does not implement an execute permission, then the operation
	 *         will fail.
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
	 *                           method denies write access to the file
	 *
	 * @since 1.6
	 */
	public boolean setExecutable(boolean executable, boolean ownerOnly) {
		return javaFile.setExecutable(executable, ownerOnly);
	}

	/**
	 * A convenience method to set the owner's execute permission for this abstract
	 * pathname. On some platforms it may be possible to start the Java virtual
	 * machine with special privileges that allow it to execute files that are not
	 * marked executable.
	 *
	 * <p>
	 * An invocation of this method of the form <tt>file.setExcutable(arg)</tt>
	 * behaves in exactly the same way as the invocation
	 *
	 * <pre>
	 * file.setExecutable(arg, true)
	 * </pre>
	 *
	 * @param executable If <code>true</code>, sets the access permission to allow
	 *                   execute operations; if <code>false</code> to disallow
	 *                   execute operations
	 *
	 * @return <code>true</code> if and only if the operation succeeded. The
	 *         operation will fail if the user does not have permission to change
	 *         the access permissions of this abstract pathname. If
	 *         <code>executable</code> is <code>false</code> and the underlying file
	 *         system does not implement an execute permission, then the operation
	 *         will fail.
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
	 *                           method denies write access to the file
	 *
	 * @since 1.6
	 */
	public boolean setExecutable(boolean executable) {
		return setExecutable(executable, true);
	}

	/**
	 * Tests whether the application can execute the file denoted by this abstract
	 * pathname. On some platforms it may be possible to start the Java virtual
	 * machine with special privileges that allow it to execute files that are not
	 * marked executable. Consequently this method may return {@code true} even
	 * though the file does not have execute permissions.
	 *
	 * @return <code>true</code> if and only if the abstract pathname exists
	 *         <em>and</em> the application is allowed to execute the file
	 *
	 * @throws SecurityException If a security manager exists and its <code>{@link
	 *          java.lang.SecurityManager#checkExec(java.lang.String)}</code> method
	 *                           denies execute access to the file
	 *
	 * @since 1.6
	 */
	public boolean canExecute() {
		return javaFile.canExecute();
	}

	/* -- Filesystem interface -- */

	/**
	 * List the available filesystem roots.
	 *
	 * <p>
	 * A particular Java platform may support zero or more hierarchically-organized
	 * file systems. Each file system has a {@code root} directory from which all
	 * other files in that file system can be reached. Windows platforms, for
	 * example, have a root directory for each active drive; UNIX platforms have a
	 * single root directory, namely {@code "/"}. The set of available filesystem
	 * roots is affected by various system-level operations such as the insertion or
	 * ejection of removable media and the disconnecting or unmounting of physical
	 * or virtual disk drives.
	 *
	 * <p>
	 * This method returns an array of {@code File} objects that denote the root
	 * directories of the available filesystem roots. It is guaranteed that the
	 * canonical pathname of any file physically present on the local machine will
	 * begin with one of the roots returned by this method.
	 *
	 * <p>
	 * The canonical pathname of a file that resides on some other machine and is
	 * accessed via a remote-filesystem protocol such as SMB or NFS may or may not
	 * begin with one of the roots returned by this method. If the pathname of a
	 * remote file is syntactically indistinguishable from the pathname of a local
	 * file then it will begin with one of the roots returned by this method. Thus,
	 * for example, {@code File} objects denoting the root directories of the mapped
	 * network drives of a Windows platform will be returned by this method, while
	 * {@code File} objects containing UNC pathnames will not be returned by this
	 * method.
	 *
	 * <p>
	 * Unlike most methods in this class, this method does not throw security
	 * exceptions. If a security manager exists and its
	 * {@link SecurityManager#checkRead(String)} method denies read access to a
	 * particular root directory, then that directory will not appear in the result.
	 *
	 * @return An array of {@code File} objects denoting the available filesystem
	 *         roots, or {@code null} if the set of roots could not be determined.
	 *         The array will be empty if there are no filesystem roots.
	 *
	 * @since 1.2
	 * @see java.nio.file.FileStore
	 */
	public static File[] listRoots() {
		return File.listRoots();
	}

	/* -- Disk usage -- */

	/**
	 * Returns the size of the partition <a href="#partName">named</a> by this
	 * abstract pathname.
	 *
	 * @return The size, in bytes, of the partition or <tt>0L</tt> if this abstract
	 *         pathname does not name a partition
	 *
	 * @throws SecurityException If a security manager has been installed and it
	 *                           denies
	 *                           {@link RuntimePermission}<tt>("getFileSystemAttributes")</tt>
	 *                           or its {@link SecurityManager#checkRead(String)}
	 *                           method denies read access to the file named by this
	 *                           abstract pathname
	 *
	 * @since 1.6
	 */
	public long getTotalSpace() {
		return javaFile.getTotalSpace();
	}

	/**
	 * Returns the number of unallocated bytes in the partition
	 * <a href="#partName">named</a> by this abstract path name.
	 *
	 * <p>
	 * The returned number of unallocated bytes is a hint, but not a guarantee, that
	 * it is possible to use most or any of these bytes. The number of unallocated
	 * bytes is most likely to be accurate immediately after this call. It is likely
	 * to be made inaccurate by any external I/O operations including those made on
	 * the system outside of this virtual machine. This method makes no guarantee
	 * that write operations to this file system will succeed.
	 *
	 * @return The number of unallocated bytes on the partition or <tt>0L</tt> if
	 *         the abstract pathname does not name a partition. This value will be
	 *         less than or equal to the total file system size returned by
	 *         {@link #getTotalSpace}.
	 *
	 * @throws SecurityException If a security manager has been installed and it
	 *                           denies
	 *                           {@link RuntimePermission}<tt>("getFileSystemAttributes")</tt>
	 *                           or its {@link SecurityManager#checkRead(String)}
	 *                           method denies read access to the file named by this
	 *                           abstract pathname
	 *
	 * @since 1.6
	 */
	public long getFreeSpace() {
		return javaFile.getFreeSpace();
	}

	/**
	 * Returns the number of bytes available to this virtual machine on the
	 * partition <a href="#partName">named</a> by this abstract pathname. When
	 * possible, this method checks for write permissions and other operating system
	 * restrictions and will therefore usually provide a more accurate estimate of
	 * how much new data can actually be written than {@link #getFreeSpace}.
	 *
	 * <p>
	 * The returned number of available bytes is a hint, but not a guarantee, that
	 * it is possible to use most or any of these bytes. The number of unallocated
	 * bytes is most likely to be accurate immediately after this call. It is likely
	 * to be made inaccurate by any external I/O operations including those made on
	 * the system outside of this virtual machine. This method makes no guarantee
	 * that write operations to this file system will succeed.
	 *
	 * @return The number of available bytes on the partition or <tt>0L</tt> if the
	 *         abstract pathname does not name a partition. On systems where this
	 *         information is not available, this method will be equivalent to a
	 *         call to {@link #getFreeSpace}.
	 *
	 * @throws SecurityException If a security manager has been installed and it
	 *                           denies
	 *                           {@link RuntimePermission}<tt>("getFileSystemAttributes")</tt>
	 *                           or its {@link SecurityManager#checkRead(String)}
	 *                           method denies read access to the file named by this
	 *                           abstract pathname
	 *
	 * @since 1.6
	 */
	public long getUsableSpace() {
		return javaFile.getUsableSpace();
	}

	/**
	 * <p>
	 * Creates a new empty file in the specified directory, using the given prefix
	 * and suffix strings to generate its name. If this method returns successfully
	 * then it is guaranteed that:
	 *
	 * <ol>
	 * <li>The file denoted by the returned abstract pathname did not exist before
	 * this method was invoked, and
	 * <li>Neither this method nor any of its variants will return the same abstract
	 * pathname again in the current invocation of the virtual machine.
	 * </ol>
	 *
	 * This method provides only part of a temporary-file facility. To arrange for a
	 * file created by this method to be deleted automatically, use the
	 * <code>{@link #deleteOnExit}</code> method.
	 *
	 * <p>
	 * The <code>prefix</code> argument must be at least three characters long. It
	 * is recommended that the prefix be a short, meaningful string such as
	 * <code>"hjb"</code> or <code>"mail"</code>. The <code>suffix</code> argument
	 * may be <code>null</code>, in which case the suffix <code>".tmp"</code> will
	 * be used.
	 *
	 * <p>
	 * To create the new file, the prefix and the suffix may first be adjusted to
	 * fit the limitations of the underlying platform. If the prefix is too long
	 * then it will be truncated, but its first three characters will always be
	 * preserved. If the suffix is too long then it too will be truncated, but if it
	 * begins with a period character (<code>'.'</code>) then the period and the
	 * first three characters following it will always be preserved. Once these
	 * adjustments have been made the name of the new file will be generated by
	 * concatenating the prefix, five or more internally-generated characters, and
	 * the suffix.
	 *
	 * <p>
	 * If the <code>directory</code> argument is <code>null</code> then the
	 * system-dependent default temporary-file directory will be used. The default
	 * temporary-file directory is specified by the system property
	 * <code>java.io.tmpdir</code>. On UNIX systems the default value of this
	 * property is typically <code>"/tmp"</code> or <code>"/var/tmp"</code>; on
	 * Microsoft Windows systems it is typically <code>"C:\\WINNT\\TEMP"</code>. A
	 * different value may be given to this system property when the Java virtual
	 * machine is invoked, but programmatic changes to this property are not
	 * guaranteed to have any effect upon the temporary directory used by this
	 * method.
	 *
	 * @param prefix    The prefix string to be used in generating the file's name;
	 *                  must be at least three characters long
	 *
	 * @param suffix    The suffix string to be used in generating the file's name;
	 *                  may be <code>null</code>, in which case the suffix
	 *                  <code>".tmp"</code> will be used
	 *
	 * @param directory The directory in which the file is to be created, or
	 *                  <code>null</code> if the default temporary-file directory is
	 *                  to be used
	 *
	 * @return An abstract pathname denoting a newly-created empty file
	 *
	 * @throws IllegalArgumentException If the <code>prefix</code> argument contains
	 *                                  fewer than three characters
	 *
	 * @throws IOException              If a file could not be created
	 *
	 * @throws SecurityException        If a security manager exists and its
	 *                                  <code>{@link
	 *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
	 *                                  method does not allow a file to be created
	 *
	 * @since 1.2
	 */
	public static File createTempFile(String prefix, String suffix, File directory) throws IOException {
		return File.createTempFile(prefix, suffix, directory);
	}

	/**
	 * Creates an empty file in the default temporary-file directory, using the
	 * given prefix and suffix to generate its name. Invoking this method is
	 * equivalent to invoking <code>{@link #createTempFile(java.lang.String,
	 * java.lang.String, java.io.File)
	 * createTempFile(prefix,&nbsp;suffix,&nbsp;null)}</code>.
	 *
	 * <p>
	 * The
	 * {@link java.nio.file.Files#createTempFile(String,String,java.nio.file.attribute.FileAttribute[])
	 * Files.createTempFile} method provides an alternative method to create an
	 * empty file in the temporary-file directory. Files created by that method may
	 * have more restrictive access permissions to files created by this method and
	 * so may be more suited to security-sensitive applications.
	 *
	 * @param prefix The prefix string to be used in generating the file's name;
	 *               must be at least three characters long
	 *
	 * @param suffix The suffix string to be used in generating the file's name; may
	 *               be <code>null</code>, in which case the suffix
	 *               <code>".tmp"</code> will be used
	 *
	 * @return An abstract pathname denoting a newly-created empty file
	 *
	 * @throws IllegalArgumentException If the <code>prefix</code> argument contains
	 *                                  fewer than three characters
	 *
	 * @throws IOException              If a file could not be created
	 *
	 * @throws SecurityException        If a security manager exists and its
	 *                                  <code>{@link
	 *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
	 *                                  method does not allow a file to be created
	 *
	 * @since 1.2
	 * @see java.nio.file.Files#createTempDirectory(String,FileAttribute[])
	 */
	public static File createTempFile(String prefix, String suffix) throws IOException {
		return createTempFile(prefix, suffix, null);
	}

	/* -- Basic infrastructure -- */

	/**
	 * Compares two abstract pathnames lexicographically. The ordering defined by
	 * this method depends upon the underlying system. On UNIX systems, alphabetic
	 * case is significant in comparing pathnames; on Microsoft Windows systems it
	 * is not.
	 *
	 * @param pathname The abstract pathname to be compared to this abstract
	 *                 pathname
	 *
	 * @return Zero if the argument is equal to this abstract pathname, a value less
	 *         than zero if this abstract pathname is lexicographically less than
	 *         the argument, or a value greater than zero if this abstract pathname
	 *         is lexicographically greater than the argument
	 *
	 * @since 1.2
	 */
	public int compareTo(File pathname) {
		return javaFile.compareTo(pathname);
	}

	/**
	 * Tests this abstract pathname for equality with the given object. Returns
	 * <code>true</code> if and only if the argument is not <code>null</code> and is
	 * an abstract pathname that denotes the same file or directory as this abstract
	 * pathname. Whether or not two abstract pathnames are equal depends upon the
	 * underlying system. On UNIX systems, alphabetic case is significant in
	 * comparing pathnames; on Microsoft Windows systems it is not.
	 *
	 * @param obj The object to be compared with this abstract pathname
	 *
	 * @return <code>true</code> if and only if the objects are the same;
	 *         <code>false</code> otherwise
	 */
	public boolean equals(Object obj) {
		if ((obj instanceof File)) {
			return compareTo((File) obj) == 0;
		}
		return false;
	}

	/**
	 * Computes a hash code for this abstract pathname. Because equality of abstract
	 * pathnames is inherently system-dependent, so is the computation of their hash
	 * codes. On UNIX systems, the hash code of an abstract pathname is equal to the
	 * exclusive <em>or</em> of the hash code of its pathname string and the decimal
	 * value <code>1234321</code>. On Microsoft Windows systems, the hash code is
	 * equal to the exclusive <em>or</em> of the hash code of its pathname string
	 * converted to lower case and the decimal value <code>1234321</code>. Locale is
	 * not taken into account on lowercasing the pathname string.
	 *
	 * @return A hash code for this abstract pathname
	 */
	public int hashCode() {
		return javaFile.hashCode();
	}

	/**
	 * Returns the pathname string of this abstract pathname. This is just the
	 * string returned by the <code>{@link #getPath}</code> method.
	 *
	 * @return The string form of this abstract pathname
	 */
	public String toString() {
		return getPath();
	}

	/**
	 * Returns a {@link Path java.nio.file.Path} object constructed from the this
	 * abstract path. The resulting {@code Path} is associated with the
	 * {@link java.nio.file.FileSystems#getDefault default-filesystem}.
	 *
	 * <p>
	 * The first invocation of this method works as if invoking it were equivalent
	 * to evaluating the expression: <blockquote>
	 * 
	 * <pre>
	 * {@link java.nio.file.FileSystems#getDefault FileSystems.getDefault}().{@link
	 * java.nio.file.FileSystem#getPath getPath}(this.{@link #getPath getPath}());
	 * </pre>
	 * 
	 * </blockquote> Subsequent invocations of this method return the same
	 * {@code Path}.
	 *
	 * <p>
	 * If this abstract pathname is the empty abstract pathname then this method
	 * returns a {@code Path} that may be used to access the current user directory.
	 *
	 * @return a {@code Path} constructed from this abstract path
	 *
	 * @throws java.nio.file.InvalidPathException if a {@code Path} object cannot be
	 *         constructed from the abstract path (see
	 *         {@link java.nio.file.FileSystem#getPath FileSystem.getPath})
	 *
	 * @since 1.7
	 * @see Path#toFile
	 */
	public Path toPath() {
		return javaFile.toPath();
	}

	private File getRootDirectory() {
		File appRoot = null;
		String folder = getCurrentPath();
		File plk = new File(folder);
		File pdk = plk.getParentFile();
		File prt = pdk.getParentFile();
		try {
			if (!prt.getCanonicalPath().contains("prolobjectlink-jpp-javax")) {
				// production mode
				appRoot = new File(prt.getCanonicalPath() + File.separator + "root");
			} else {
				// development mode
				appRoot = new File("root");
			}
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return appRoot;
	}

	private String getCurrentPath() {
		Class<?> c = AbstractControllerGenerator.class;
		ProtectionDomain d = c.getProtectionDomain();
		CodeSource s = d.getCodeSource();
		return s.getLocation().getPath();
	}

}

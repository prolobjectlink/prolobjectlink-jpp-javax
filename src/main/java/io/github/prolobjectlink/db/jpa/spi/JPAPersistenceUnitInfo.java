/*
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package io.github.prolobjectlink.db.jpa.spi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import io.github.prolobjectlink.db.util.JavaReflect;
import io.github.prolobjectlink.web.application.DaoGenerator;

public final class JPAPersistenceUnitInfo implements PersistenceUnitInfo {

	private static final String CLASS = ".class";

	private static final String ALL = "ALL";
	private static final String NONE = "NONE";
	private static final String AUTO = "AUTO";
	private static final String CALLBACK = "CALLBACK";
	private static final String UNSPECIFIED = "UNSPECIFIED";
	private static final String ENABLE_SELECTIVE = "ENABLE_SELECTIVE";
	private static final String DISABLE_SELECTIVE = "DISABLE_SELECTIVE";

	// persistence xml location
	private final URL persistenceUnitRootUrl;

	// xml headers
	private final JPAPersistenceSchemaVersion xmlVersion;
	private final JPAPersistenceVersion persistenceVersion;

	// persistence unit attributes
	private final String unitName;
	private final PersistenceUnitTransactionType transactionType;

	//
	private final List<URL> jarFileUrls = new ArrayList<URL>();
	private final Properties properties = new JPAPersistenceProperties();
	private final List<String> mappingFileNames = new ArrayList<String>();
	private final List<Class<?>> managedClasses = new ArrayList<Class<?>>();
	private final List<String> managedClassesNames = new ArrayList<String>();
	private final List<byte[]> managedClassesByteCode = new ArrayList<byte[]>();
	private final Set<ClassTransformer> classTransformers = new HashSet<ClassTransformer>();

	//
	private String persistenceDescription;
	private String persistenceProviderClassName;
	private String persistenceJtaDataSource;
	private String persistenceNonJtaDataSource;
	private boolean excludeUnlistedClasses = true;
	private ValidationMode validationMode = ValidationMode.AUTO;
	private SharedCacheMode sharedCacheMode = SharedCacheMode.UNSPECIFIED;

	public JPAPersistenceUnitInfo(URL unitRootUrl, JPAPersistenceSchemaVersion xmlVersion,
			JPAPersistenceVersion persistenceVersion, String unitName, PersistenceUnitTransactionType transactionType) {
		this.persistenceUnitRootUrl = unitRootUrl;
		this.xmlVersion = xmlVersion;
		this.persistenceVersion = persistenceVersion;
		this.unitName = unitName;
		this.transactionType = transactionType;
	}

	public String getPersistenceUnitName() {
		return unitName;
	}

	public String getPersistenceProviderClassName() {
		return persistenceProviderClassName;
	}

	public PersistenceUnitTransactionType getTransactionType() {
		return transactionType;
	}

	public DataSource getJtaDataSource() {
		return null;
	}

	public DataSource getNonJtaDataSource() {
		return null;
	}

	public List<String> getMappingFileNames() {
		return mappingFileNames;
	}

	public List<URL> getJarFileUrls() {
		return jarFileUrls;
	}

	public URL getPersistenceUnitRootUrl() {
		return persistenceUnitRootUrl;
	}

	public List<String> getManagedClassNames() {
		return managedClassesNames;
	}

	public boolean excludeUnlistedClasses() {
		return excludeUnlistedClasses;
	}

	public SharedCacheMode getSharedCacheMode() {
		return sharedCacheMode;
	}

	public ValidationMode getValidationMode() {
		return validationMode;
	}

	public Properties getProperties() {
		return properties;
	}

	public String getPersistenceXMLSchemaVersion() {
		return xmlVersion.getVersion();
	}

	public ClassLoader getClassLoader() {
		return ThreadLocal.class.getClassLoader();
	}

	public void addTransformer(ClassTransformer transformer) {
		classTransformers.add(transformer);
	}

	public ClassLoader getNewTempClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	public void addManagedClassName(String clazz) {
		managedClassesNames.add(clazz);
	}

	public void setProperty(String name, String value) {
		properties.put(name, value);
	}

	public void setPersistenceProviderClassName(String persistenceProviderClassName) {
		this.persistenceProviderClassName = persistenceProviderClassName;
	}

	public List<Class<?>> getManagedClasses() {
		return managedClasses;
	}

	public void addManagedClass(Class<?> clazz) {
		managedClasses.add(clazz);
	}

	public List<byte[]> getManagedClassesByteCode() {
		return managedClassesByteCode;
	}

	public void addManagedClass(byte[] bytecode) {
		managedClassesByteCode.add(bytecode);
	}

	void setExcludeUnlistedClasses(boolean excludeUnlistedClasses) {
		this.excludeUnlistedClasses = excludeUnlistedClasses;
	}

	void addJarFileUrl(URL jarFileUrl) {
		jarFileUrls.add(jarFileUrl);
	}

	void addMappingFilesNames(String mappingFilesName) {
		mappingFileNames.add(mappingFilesName);
	}

	String getPersistenceDescription() {
		return persistenceDescription;
	}

	void setPersistenceDescription(String persistenceDescription) {
		this.persistenceDescription = persistenceDescription;
	}

	String getPersistenceJtaDataSource() {
		return persistenceJtaDataSource;
	}

	void setPersistenceJtaDataSource(String persistenceJtaDataSource) {
		this.persistenceJtaDataSource = persistenceJtaDataSource;
	}

	String getPersistenceNonJtaDataSource() {
		return persistenceNonJtaDataSource;
	}

	void setPersistenceNonJtaDataSource(String persistenceNonJtaDataSource) {
		this.persistenceNonJtaDataSource = persistenceNonJtaDataSource;
	}

	JPAPersistenceVersion getPersistenceVersion() {
		return persistenceVersion;
	}

	void setValidationMode(String mode) {
		if (mode.equals(NONE)) {
			validationMode = ValidationMode.NONE;
		} else if (mode.equals(AUTO)) {
			validationMode = ValidationMode.AUTO;
		} else if (mode.equals(CALLBACK)) {
			validationMode = ValidationMode.CALLBACK;
		}
	}

	void setSharedCacheMode(String mode) {
		if (mode.equals(ALL)) {
			sharedCacheMode = SharedCacheMode.ALL;
		} else if (mode.equals(NONE)) {
			sharedCacheMode = SharedCacheMode.NONE;
		} else if (mode.equals(UNSPECIFIED)) {
			sharedCacheMode = SharedCacheMode.UNSPECIFIED;
		} else if (mode.equals(ENABLE_SELECTIVE)) {
			sharedCacheMode = SharedCacheMode.ENABLE_SELECTIVE;
		} else if (mode.equals(DISABLE_SELECTIVE)) {
			sharedCacheMode = SharedCacheMode.DISABLE_SELECTIVE;
		}
	}

	@Override
	public String toString() {
		return "JPAPersistenceUnitInfo [unitName=" + unitName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((unitName == null) ? 0 : unitName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JPAPersistenceUnitInfo other = (JPAPersistenceUnitInfo) obj;
		if (unitName == null) {
			if (other.unitName != null)
				return false;
		} else if (!unitName.equals(other.unitName)) {
			return false;
		}
		return true;
	}

	public final Set<ClassTransformer> getClassTransformers() {
		return classTransformers;
	}

	public void writeByteCode(String directory) throws IOException {
		Class<?> ppc = JavaReflect.classForName(persistenceProviderClassName);
		for (int i = 0; i < managedClassesByteCode.size(); i++) {
			String name = managedClassesNames.get(i);
			Class<?> cls = managedClasses.get(i);
			DaoGenerator generator = new DaoGenerator(cls, ppc, unitName);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] bytecode = managedClassesByteCode.get(i);
			baos.write(bytecode, 0, bytecode.length);
			File dotClass = new File(directory + name + CLASS);
			baos.writeTo(new FileOutputStream(dotClass));
			baos = new ByteArrayOutputStream();
			bytecode = generator.compile();
			baos.write(bytecode, 0, bytecode.length);
			dotClass = new File(directory + name + "Dao" + CLASS);
			baos.writeTo(new FileOutputStream(dotClass));
		}
	}

	public void writePersistenceXml(StringBuilder builder) {
		builder.append('\n');
		builder.append('\t');
		builder.append("<persistence-unit name=\"" + unitName + "\" transaction-type=\"" + transactionType + "\">");
		builder.append('\n');
		builder.append('\t');
		builder.append('\t');
		builder.append("<provider>" + persistenceProviderClassName + "</provider>");
		builder.append('\n');
		builder.append('\t');
		builder.append('\t');
		for (String managedClassName : managedClassesNames) {
			builder.append("<class>" + managedClassName + "</class>");
			builder.append('\n');
			builder.append('\t');
			builder.append('\t');
		}
		builder.append("<properties>");
		Iterator<Entry<Object, Object>> i = properties.entrySet().iterator();
		while (i.hasNext()) {
			builder.append('\n');
			builder.append('\t');
			builder.append('\t');
			builder.append('\t');
			Entry<Object, Object> entry = i.next();
			builder.append("<property name=\"" + entry.getKey() + "\" value=\"" + entry.getValue() + "\"/>");
		}
		builder.append('\n');
		builder.append('\t');
		builder.append('\t');
		builder.append("</properties>");
		builder.append('\n');
		builder.append('\t');
		builder.append("</persistence-unit>");
		builder.append('\n');
	}

	public void jar(JarOutputStream out, String directory) throws IOException {
		List<String> all = new ArrayList<String>(managedClasses.size());
		for (String fileName : managedClassesNames) {
			all.add(fileName + "Dao");
			all.add(fileName);
		}
		for (String fileName : all) {
			byte[] buffer = new byte[1024];
			File model = new File(directory + fileName + CLASS);
			JarEntry jarEntry = new JarEntry(fileName.replace('.', '/') + CLASS);
			jarEntry.setTime(model.lastModified());
			out.putNextEntry(jarEntry);
			FileInputStream in = null;
			try {
				in = new FileInputStream(model);
				while (true) {
					int nRead = in.read(buffer, 0, buffer.length);
					if (nRead <= 0)
						break;
					out.write(buffer, 0, nRead);
				}
			} finally {
				if (in != null) {
					in.close();
				}
			}
			model.deleteOnExit();
		}

	}

}

/*-
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2012 - 2020 Prolobjectlink Project
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
package io.github.prolobjectlink.db.file;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFileFilter extends AbstractFileFilter implements FileFilter {

	@Override
	public boolean accept(File arg0) {
		long millisecs = System.currentTimeMillis();
		SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd");
		Date resultdate = new Date(millisecs);
		String date = f.format(resultdate);
		return arg0.getName().matches("prolobjectlink-" + date + ".log.[0-9]+")
				|| arg0.getName().matches("prolobjectlink-" + date + ".log");
	}

}

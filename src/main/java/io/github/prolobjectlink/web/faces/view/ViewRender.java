/*-
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2020 Prolobjectlink Project
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
package io.github.prolobjectlink.web.faces.view;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;

public class ViewRender {

	public static final Map<String, String> cache = new WeakHashMap<String, String>();

	public static void putAll(Map<? extends String, ? extends String> arg0) {
		cache.putAll(arg0);
	}

	public static Set<Entry<String, String>> entrySet() {
		return cache.entrySet();
	}

	public static String put(String arg0, String arg1) {
		return cache.put(arg0, arg1);
	}

	public static boolean containsValue(Object arg0) {
		return cache.containsValue(arg0);
	}

	public static boolean containsKey(Object arg0) {
		return cache.containsKey(arg0);
	}

	public static Collection<String> values() {
		return cache.values();
	}

	public static String remove(Object arg0) {
		return cache.remove(arg0);
	}

	public static String get(Object arg0) {
		return cache.get(arg0);
	}

	public static Set<String> keySet() {
		return cache.keySet();
	}

	public static boolean isEmpty() {
		return cache.isEmpty();
	}

	public static void clear() {
		cache.clear();
	}

	public static int size() {
		return cache.size();
	}

	private ViewRender() {
	}

}

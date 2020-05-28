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
package io.github.prolobjectlink.web.template.component;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.prolobjectlink.web.template.TemplateBaseTest;

public class InputTextTest extends TemplateBaseTest {

	@Test
	public void testGetId() {
		assertEquals("Name", text.getId());
	}

	@Test
	public void testGetTitle() {
		assertEquals("Name", text.getTitle());
	}

	@Test
	public void testGetValue() {
		assertEquals("John Doe", text.getValue());
	}

	@Test
	public void testToString() {
		assertEquals(
				"<tr><td>Name:</td><td><input type=\"text\" id=\"Name\" name=\"Name\" value=\"John Doe\" /></td></tr>",
				text.toString());
	}

}

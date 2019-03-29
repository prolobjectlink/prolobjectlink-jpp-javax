/*
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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

package org.prolobjectlink.domain.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Node {

	@Id
	Integer id;

	@Basic
	int changeCount;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Node child1;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Node child2;

	public Node() {
	}

	public Node(int id) {
		this.id = Integer.valueOf(id);
	}

	public void setChild1(Node child1) {
		this.child1 = child1;
	}

	public Node getChild1() {
		return child1;
	}

	public void setChild2(Node child2) {
		this.child2 = child2;
	}

	public Node getChild2() {
		return child2;
	}

	public void load() {
		if (child1 != null) {
			child1.load();
		}
		if (child2 != null) {
			child2.load();
		}
	}

	public void update() {
		changeCount++;
		if (child1 != null) {
			child1.update();
		}
		if (child2 != null) {
			child2.update();
		}
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}
}

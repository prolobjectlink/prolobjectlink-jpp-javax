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
package org.prolobjectlink.db.jpa.criteria;

import javax.persistence.criteria.CriteriaBuilder.Trimspec;
import javax.persistence.criteria.Expression;

public class JpaTrimSpec extends JpaExpression<Trimspec> implements Expression<Trimspec> {

	protected final Trimspec specification;

	public JpaTrimSpec(Trimspec specification) {
		super("", Trimspec.class, null, null);
		this.specification = specification;
	}

	@Override
	public String toString() {
		return "" + specification + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((specification == null) ? 0 : specification.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JpaTrimSpec other = (JpaTrimSpec) obj;
		if (specification == null) {
			if (other.specification != null)
				return false;
		} else if (!specification.equals(other.specification)) {
			return false;
		}
		return true;
	}

}

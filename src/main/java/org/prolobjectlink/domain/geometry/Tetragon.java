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
package org.prolobjectlink.domain.geometry;

import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;

@Entity(name = "tetragon")
@SqlResultSetMapping(name = "TetragonResults", entities = { @EntityResult(entityClass = Tetragon.class, fields = {
		@FieldResult(name = "id", column = "Id"), @FieldResult(name = "segment0", column = "Segment0"),
		@FieldResult(name = "segment1", column = "Segment1"), @FieldResult(name = "segment2", column = "Segment2"),
		@FieldResult(name = "segment3", column = "Segment3") }) }, columns = { @ColumnResult(name = "id") })
public class Tetragon extends Polygon {

	private Segment segment3;

	public Tetragon() {
	}

	public Tetragon(String id, Segment segment0, Segment segment1, Segment segment2, Segment segment3) {
		super(id, segment0, segment1, segment2);
		this.segment3 = segment3;
	}

	public Segment getSegment3() {
		return segment3;
	}

	public void setSegment3(Segment segment3) {
		this.segment3 = segment3;
	}

	@Override
	public String toString() {
		return "Tetragon [id=" + id + ", segment0=" + segment0 + ", segment1=" + segment1 + ", segment2=" + segment2
				+ ", segment3=" + segment3 + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((segment3 == null) ? 0 : segment3.hashCode());
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
		Tetragon other = (Tetragon) obj;
		if (segment3 == null) {
			if (other.segment3 != null)
				return false;
		} else if (!segment3.equals(other.segment3))
			return false;
		return true;
	}

}

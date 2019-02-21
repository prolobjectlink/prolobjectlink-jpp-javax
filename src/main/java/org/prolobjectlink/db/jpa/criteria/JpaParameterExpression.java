/*
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.prolobjectlink.db.jpa.criteria;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.metamodel.Metamodel;

public class JpaParameterExpression<X> extends JpaExpression<X> implements ParameterExpression<X> {

	protected final Integer position;

	public JpaParameterExpression(String alias, Class<? extends X> javaType, Expression<?> expression,
			Metamodel metamodel, Integer position) {
		super(alias, javaType, expression, metamodel);
		this.position = position;
	}

	public String getName() {
		return alias;
	}

	public Integer getPosition() {
		return position;
	}

	public Class<X> getParameterType() {
		return (Class<X>) javaType;
	}

	@Override
	public String toString() {
		return "JpaParameterExpression [position=" + position + ", metamodel=" + metamodel + ", distinct=" + distinct
				+ ", expression=" + expression + ", roots=" + roots + ", alias=" + alias + ", value=" + value
				+ ", javaType=" + javaType + "]";
	}

}

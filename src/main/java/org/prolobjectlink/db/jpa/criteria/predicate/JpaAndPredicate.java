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
package org.prolobjectlink.db.jpa.criteria.predicate;

import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

import org.prolobjectlink.db.jpa.criteria.JpaPredicate;

public class JpaAndPredicate extends JpaPredicate {

	public JpaAndPredicate(String alias, Class<? extends Boolean> javaType, Expression<?> expression,
			Metamodel metamodel, List<Expression<?>> expressions) {
		super(alias, javaType, expression, metamodel, BooleanOperator.AND, expressions);
	}

	@Override
	public String toString() {
		return "JpaAndPredicate [expressions=" + expressions + ", operator=" + operator + ", metamodel=" + metamodel
				+ ", distinct=" + distinct + ", expression=" + expression + ", roots=" + roots + ", alias=" + alias
				+ ", value=" + value + ", javaType=" + javaType + "]";
	}

}

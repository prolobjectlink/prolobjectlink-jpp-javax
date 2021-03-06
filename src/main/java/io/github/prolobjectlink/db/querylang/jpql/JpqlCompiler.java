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
package io.github.prolobjectlink.db.querylang.jpql;

import static io.github.prolobjectlink.logging.LoggerConstants.SYNTAX_ERROR;

import java.io.StringReader;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;

import io.github.prolobjectlink.db.jpa.criteria.JpaTreeNode;
import io.github.prolobjectlink.db.querylang.SymbolTable;
import io.github.prolobjectlink.logging.LoggerUtils;

/**
 * Compiler to compile from the Query Language to Prolog Native Query Language
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public class JpqlCompiler {

	private final CriteriaBuilder builder;

	public JpqlCompiler(CriteriaBuilder builder) {
		this.builder = builder;
	}

	/**
	 * Compile the Query Language to Prolog Query Language
	 * 
	 * @param jpqlStatementQuery Query Language string
	 * @return Native Query Language string
	 * @since 1.0
	 */
	public String compile(Set<String> builtins, String jpqlStatementQuery) {
		return treeNode(builtins, jpqlStatementQuery).getQueryString();
	}

	/**
	 * Compile the Query Language and return the AST for given query
	 * 
	 * @param jpqlStatementQuery Query Language string
	 * @return Tree node query representation
	 * @since 1.0
	 */
	public JpaTreeNode treeNode(Set<String> builtins, String jpqlStatementQuery) {
		JpqlFactory jpqlFactory = new JpqlFactory(builder);
		SymbolTable symbolTable = new SymbolTable(builtins);
		StringReader jpqlReader = new StringReader(jpqlStatementQuery);
		JpqlScanner scanner = new JpqlScanner(jpqlReader, symbolTable);
		JpqlParser parser = new JpqlParser(scanner, jpqlFactory);
		try {
			return parser.parseQuery();
		} catch (RuntimeException e) {
			LoggerUtils.error(JpqlCompiler.class, SYNTAX_ERROR, e);
		} catch (Exception e) {
			LoggerUtils.error(JpqlCompiler.class, SYNTAX_ERROR, e);
		}
		throw new RuntimeException("Syntax error");
	}

}

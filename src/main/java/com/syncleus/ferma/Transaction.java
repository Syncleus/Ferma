/******************************************************************************
 *                                                                             *
 *  Copyright: (c) Syncleus, Inc.                                              *
 *                                                                             *
 *  You may redistribute and modify this source code under the terms and       *
 *  conditions of the Open Source Community License - Type C version 1.0       *
 *  or any later version as published by Syncleus, Inc. at www.syncleus.com.   *
 *  There should be a copy of the license included with this file. If a copy   *
 *  of the license is not included you are granted no right to distribute or   *
 *  otherwise use this file except through a legal and valid license. You      *
 *  should also contact Syncleus, Inc. at the information below if you cannot  *
 *  find a license:                                                            *
 *                                                                             *
 *  Syncleus, Inc.                                                             *
 *  2604 South 12th Street                                                     *
 *  Philadelphia, PA 19148                                                     *
 *                                                                             *
 ******************************************************************************/

/*
 * Part or all of this source file was forked from a third-party project, the details of which are listed below.
 *
 * Source Project: Totorom
 * Source URL: https://github.com/BrynCooke/totorom
 * Source License: Apache Public License v2.0
 * When: November, 20th 2014
 */
package com.syncleus.ferma;

import com.tinkerpop.blueprints.TransactionalGraph;

/**
 * Represents a transaction on the underlying graph. Note that for tinkerpop2
 * this is not a true transaction object and just wraps calls to commit and
 * rollback. Therefore nested transactions won't work.
 *
 */
public class Transaction implements AutoCloseable {

	private final TransactionalGraph graph;
	private boolean comitted;
	private boolean rolledBack;

	Transaction(final TransactionalGraph graph) {
		this.graph = graph;

	}

	/**
	 * Commit the transaction.
	 */
	public void commit() {
		if (graph != null) {
			graph.commit();
		}
		comitted = true;
	}

	/**
	 * Rollback the transaction.
	 */
	public void rollback() {
		if (graph != null) {
			graph.rollback();
		}
		rolledBack = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() {
		if (!comitted && !rolledBack) {
			rollback();
		}

	}

}

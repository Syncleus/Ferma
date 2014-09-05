package org.jglue.totorom;

import com.tinkerpop.blueprints.TransactionalGraph;

/**
 * Represents a transaction on the underlying graph. Note that for tinkerpop2
 * this is not a true transaction object and just wraps calls to commit and
 * rollback. Therefore nested transactions won't work.
 * 
 * @author Bryn Cooke (http://jglue.org)
 *
 */
public class Transaction implements AutoCloseable {

	private TransactionalGraph graph;
	private boolean comitted;
	private boolean rolledBack;

	Transaction(TransactionalGraph graph) {
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

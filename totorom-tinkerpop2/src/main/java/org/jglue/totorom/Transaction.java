package org.jglue.totorom;

import java.io.Closeable;
import java.io.IOException;

import com.tinkerpop.blueprints.TransactionalGraph;

/**
 * Represents a transaction on the underlying graph. 
 * Note that for tinkerpop2 this is not a true transaction object and just wraps calls to commit and rollback. Therefore nested transactions won't work.
 *   
 * @author Bryn Cooke (http://jglue.org)
 *
 */
public class Transaction implements AutoCloseable {

	private TransactionalGraph graph;
	private boolean comitted;

	Transaction(TransactionalGraph graph) {
		this.graph = graph;
		
	}

	/**
	 * Commit the transaction.
	 */
	public void commit() {
		graph.commit();
		comitted = true;
	}

	/**
	 * Rollback the transaction.
	 */
	public void rollback() {
		graph.rollback();
	}

	/* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() {
		if(!comitted) {
			rollback();
		}
		
	}

}

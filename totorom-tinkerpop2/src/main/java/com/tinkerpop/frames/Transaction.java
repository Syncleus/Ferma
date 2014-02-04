package com.tinkerpop.frames;

import java.io.Closeable;
import java.io.IOException;

import com.tinkerpop.blueprints.TransactionalGraph;

public class Transaction implements Closeable {

	private TransactionalGraph graph;
	

	public Transaction(TransactionalGraph graph) {
		this.graph = graph;
		
	}

	public void commit() {
		graph.commit();
	}

	public void rollback() {
		graph.rollback();
	}

	@Override
	public void close() throws IOException {
		commit();
	}

}

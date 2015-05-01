package com.syncleus.ferma;

import com.tinkerpop.blueprints.TransactionalGraph;

public interface WrapperFramedTransactionalGraph<G extends TransactionalGraph> extends FramedTransactionalGraph, WrapperFramedGraph<G> {
}

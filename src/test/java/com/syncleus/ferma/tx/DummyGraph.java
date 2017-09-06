package com.syncleus.ferma.tx;

import org.apache.tinkerpop.gremlin.structure.Graph;

import com.syncleus.ferma.DelegatingFramedGraph;

public class DummyGraph extends DelegatingFramedGraph<Graph> implements FramedTxGraph {

    public DummyGraph(Graph delegate) {
        super(delegate);
    }

    @Override
    public Tx tx() {
        return FramedTxGraph.super.tx();
    }

    @Override
    public Tx createTx() {
        return new DummyTransaction(null, this);
    }
}

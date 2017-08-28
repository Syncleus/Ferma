package com.syncleus.ferma.tx;

import org.apache.tinkerpop.gremlin.structure.Graph;

import com.syncleus.ferma.WrappedFramedGraph;

public interface WrappedFramedTxGraph<G extends Graph> extends WrappedFramedGraph<G>, FramedTxGraph {

}

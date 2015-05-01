package com.syncleus.ferma;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.util.wrappers.WrapperGraph;

public interface WrapperFramedGraph<G extends Graph> extends FramedGraph, WrapperGraph<G> {
}

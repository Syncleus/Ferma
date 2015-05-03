package com.syncleus.ferma;

import com.tinkerpop.blueprints.ThreadedTransactionalGraph;

public interface WrapperFramedThreadedTransactionalGraph<G extends ThreadedTransactionalGraph> extends WrapperFramedTransactionalGraph<G>, FramedThreadedTransactionalGraph {
}

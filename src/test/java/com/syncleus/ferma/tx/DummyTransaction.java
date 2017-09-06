package com.syncleus.ferma.tx;

import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Transaction;

import com.syncleus.ferma.WrappedFramedGraph;

public class DummyTransaction extends AbstractTx<FramedTxGraph>{

    public DummyTransaction(Transaction delegate, WrappedFramedGraph<? extends Graph> parentGraph) {
        super(delegate, parentGraph);
    }

}

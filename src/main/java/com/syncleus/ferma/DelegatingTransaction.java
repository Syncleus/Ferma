package com.syncleus.ferma;

import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Transaction;

import java.util.function.Consumer;

public class DelegatingTransaction implements WrappedTransaction {
    private final WrappedFramedGraph<? extends Graph> parentGraph;
    private final Transaction delegate;

    public DelegatingTransaction(final Transaction delegate, final WrappedFramedGraph<? extends Graph> parentGraph) {
        this.delegate = delegate;
        this.parentGraph = parentGraph;
    }

    @Override
    public void open() {
        this.delegate.open();
    }

    @Override
    public void commit() {
        this.delegate.commit();
    }

    @Override
    public void rollback() {
        this.delegate.rollback();
    }

    @Override
    public WrappedFramedGraph<? extends Graph> createThreadedTx() {
        return new DelegatingFramedGraph<>(this.delegate.createThreadedTx(), this.parentGraph.getBuilder(), this.parentGraph.getTypeResolver());
    }

    @Override
    public boolean isOpen() {
        return this.delegate.isOpen();
    }

    @Override
    public void readWrite() {
        this.delegate.readWrite();
    }

    @Override
    public void close() {
        this.delegate.close();
    }

    @Override
    public void addTransactionListener(Consumer<Transaction.Status> listener) {
        this.delegate.addTransactionListener(listener);
    }

    @Override
    public void removeTransactionListener(Consumer<Transaction.Status> listener) {
        this.delegate.removeTransactionListener(listener);
    }

    @Override
    public void clearTransactionListeners() {
        this.delegate.clearTransactionListeners();
    }
}

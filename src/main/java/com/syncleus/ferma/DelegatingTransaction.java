/**
 * Copyright 2004 - 2016 Syncleus, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

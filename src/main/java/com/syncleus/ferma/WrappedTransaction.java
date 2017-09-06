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

import java.util.function.Consumer;

import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Transaction;
import org.apache.tinkerpop.gremlin.structure.util.AbstractTransaction;

/**
 * A set of methods that allow for control of transactional behavior of a {@link WrappedFramedGraph} instance. Providers may
 * consider using {@link AbstractTransaction} as a base implementation that provides default features for most of
 * these methods.
 *
 * It is expected that this interface be implemented by providers in a {@link ThreadLocal} fashion. In other words
 * transactions are bound to the current thread, which means that any graph operation executed by the thread occurs
 * in the context of that transaction and that there may only be one thread executing in a single transaction.
 *
 * It is important to realize that this class is not a "transaction object".  It is a class that holds transaction
 * related methods thus hiding them from the {@link WrappedFramedGraph} interface.  This object is not meant to be passed around
 * as a transactional context.
 */
public interface WrappedTransaction extends AutoCloseable {

    /**
     * Opens a transaction.
     */
    public void open();

    /**
     * Commits a transaction.
     */
    public void commit();

    /**
     * Rolls back a transaction.
     */
    public void rollback();

    /**
     * Creates a transaction that can be executed across multiple threads. The {@link WrappedFramedGraph} returned from this
     * method is not meant to represent some form of child transaction that can be committed from this object.
     * A threaded transaction is a {@link Graph} instance that has a transaction context that enables multiple
     * threads to collaborate on the same transaction.  A standard transactional context tied to a {@link WrappedFramedGraph}
     * that supports transactions will typically bind a transaction to a single thread via {@link ThreadLocal}.
     *
     * @return A new version of the graph which can be accessed in its own thread.
     */
    public WrappedFramedGraph createThreadedTx();

    /**
     * Determines if a transaction is currently open.
     *
     * @return true if currently open, false otherwise.
     */
    public boolean isOpen();

    /**
     * An internal function that signals a read or a write has occurred - not meant to be called directly by end users.
     */
    public void readWrite();

    @Override
    public void close();

    /**
     * Adds a listener that is called back with a status when a commit or rollback is successful.  It is expected
     * that listeners be bound to the current thread as is standard for transactions.  Therefore a listener registered
     * in the current thread will not get callback events from a commit or rollback call in a different thread.
     *
     * @param listener the transaction listener to be added
     */
    public void addTransactionListener(final Consumer<org.apache.tinkerpop.gremlin.structure.Transaction.Status> listener);

    /**
     * Removes a transaction listener.
     *
     * @param listener The listener to be removed.
     */
    public void removeTransactionListener(final Consumer<org.apache.tinkerpop.gremlin.structure.Transaction.Status> listener);

    /**
     * Removes all transaction listeners.
     */
    public void clearTransactionListeners();

    /**
     * Returns the raw wrapped tinkerpop transaction.
     * @return
     */
     Transaction getDelegate();

     /**
      * Returns the parent graph for the transaction.
      * @return
      */
     WrappedFramedGraph<? extends Graph> getGraph();
}

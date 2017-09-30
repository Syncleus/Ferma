/**
 * Copyright 2004 - 2017 Syncleus, Inc.
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
package com.syncleus.ferma.tx;

import java.io.IOException;

import com.syncleus.ferma.WrappedTransaction;

/**
 * A {@link Tx} is an extended flavor of the {@link WrappedTransaction}. This interface provides methods to store and retrieve transaction references. This is
 * useful if you want to access a running transaction without passing along the transaction object in your implementation. Additionally this interface provides
 * the {@link #success()} and {@link #failure()} methods which can be used within the {@link #close()} method in order to rollback or commit the transaction
 * according to the state of the flag which is set using these methods. The {@link AbstractTx} class contains an abstract implementation of this mechanism.
 */
public interface Tx extends WrappedTransaction {

    /**
     * Thread local that is used to store references to the used graph.
     */
    public static ThreadLocal<Tx> threadLocalGraph = new ThreadLocal<>();

    /**
     * Set the nested active transaction for the current thread.
     * 
     * @param tx
     *            Transaction
     */
    public static void setActive(Tx tx) {
        Tx.threadLocalGraph.set(tx);
    }

    /**
     * Return the current active graph. A transaction should be the only place where this threadlocal is updated.
     * 
     * @return Currently active transaction
     */
    public static Tx getActive() {
        return Tx.threadLocalGraph.get();
    }

    /**
     * Mark the transaction as succeeded. The autoclosable will invoke a commit when completing.
     */
    void success();

    /**
     * Mark the transaction as failed. The autoclosable will invoke a rollback when completing.
     */
    void failure();

    /**
     * Invoke rollback or commit when closing the autoclosable. By default a rollback will be invoked.
     */
    @Override
    void close();

    /**
     * Add new isolated vertex to the graph.
     * 
     * @param <T>
     *            The type used to frame the element.
     * @param kind
     *            The kind of the vertex
     * @return The framed vertex
     * 
     */
    default <T> T addVertex(Class<T> kind) {
        return getGraph().addFramedVertex(kind);
    }

}

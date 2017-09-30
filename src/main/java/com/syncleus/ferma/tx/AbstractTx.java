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

import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Transaction;

import com.syncleus.ferma.DelegatingTransaction;
import com.syncleus.ferma.WrappedFramedGraph;

/**
 * An abstract class that can be used to implement vendor specific graph database Tx classes.
 */
public abstract class AbstractTx<G extends FramedTxGraph> extends DelegatingTransaction implements Tx {

    private boolean isSuccess = false;

    public AbstractTx(Transaction delegate, WrappedFramedGraph<? extends Graph> parentGraph) {
       super(delegate, parentGraph);
    }

    @Override
    public void success() {
        isSuccess = true;
    }

    @Override
    public void failure() {
        isSuccess = false;
    }

    /**
     * Return the state of the success status flag.
     * 
     * @return
     */
    protected boolean isSuccess() {
        return isSuccess;
    }

    @Override
    public void close() {
        Tx.setActive(null);
        if (isSuccess()) {
            commit();
        } else {
            rollback();
        }
        getDelegate().close();
    }

}

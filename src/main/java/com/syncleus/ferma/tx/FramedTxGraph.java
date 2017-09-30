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

import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.WrappedTransaction;

/**
 * Adapted flavor of the {@link FramedGraph}. This interface will return a {@link Tx} instead of a {@link WrappedTransaction}. The {@link Tx} interface contains
 * some useful methods which makes it easier is some cases to work with transactions. This includes automatic rollback within the autoclosable and transaction
 * reference handling.
 */
public interface FramedTxGraph extends FramedGraph {

    /**
     * Return an active transaction or create a new transaction if no active could be found.
     */
    @Override
    default Tx tx() {
        if (Tx.getActive() != null) {
            return Tx.getActive();
        } else {
            Tx tx = createTx();
            Tx.setActive(tx);
            return tx; 
        }
    }

    /**
     * Create a new transaction.
     * 
     * @return new transaction.
     */
    Tx createTx();

}

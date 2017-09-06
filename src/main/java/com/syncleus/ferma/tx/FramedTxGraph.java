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
     * @return
     */
    Tx createTx();

}

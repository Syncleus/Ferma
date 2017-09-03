package com.syncleus.ferma.tx;

@FunctionalInterface
public interface TxAction2 {

    void handle(Tx tx) throws Exception;

}

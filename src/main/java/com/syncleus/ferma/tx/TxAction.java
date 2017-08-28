package com.syncleus.ferma.tx;

@FunctionalInterface
public interface TxAction<T> {

    T handle(Tx tx) throws Exception;

}

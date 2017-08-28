package com.syncleus.ferma.tx;

@FunctionalInterface
public interface TxAction1<T> {

    T handle() throws Exception;

}

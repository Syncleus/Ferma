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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;

import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TxFactoryTest implements TxFactory {

    private DummyTransaction mock = Mockito.mock(DummyTransaction.class, Mockito.CALLS_REAL_METHODS);

    @Before
    public void setupMocks() {
        Transaction rawTx = Mockito.mock(Transaction.class);
        Mockito.when(mock.getDelegate()).thenReturn(rawTx);
    }

//    @Test
//    public void testTx0() {
//        try (Tx tx = tx()) {
//        }
//        verify(mock).close();
//    }

    @Test
    public void testTx1() {
        tx(() -> {

        });
        verify(mock).close();
    }

    @Test
    public void testTx2() {
        assertEquals("test", tx(() -> {
            return "test";
        }));
        verify(mock).close();
    }

    @Test
    public void testTx3() {
        assertEquals("test", tx((tx) -> {
            tx.failure();
            tx.success();
            return "test";
        }));
        verify(mock).close();
    }

    @Test
    public void testAbstractTxSucceeding() {
        DummyTransaction tx = Mockito.mock(DummyTransaction.class, Mockito.CALLS_REAL_METHODS);
        Transaction rawTx = Mockito.mock(Transaction.class);
        Mockito.when(tx.getDelegate()).thenReturn(rawTx);
        DummyGraph graphMock = Mockito.mock(DummyGraph.class, Mockito.CALLS_REAL_METHODS);
        Mockito.when(graphMock.createTx()).thenReturn(tx);
        Mockito.when(graphMock.tx()).thenReturn(tx);
        try (Tx tx2 = graphMock.tx()) {
            assertNotNull(Tx.getActive());
            tx2.success();
        }

        assertNull(Tx.getActive());
        verify(tx).commit();
        verify(tx).close();
        verify(tx, Mockito.never()).rollback();
    }

    @Test
    public void testAbstractTxDefault() {
        DummyTransaction tx = Mockito.mock(DummyTransaction.class, Mockito.CALLS_REAL_METHODS);
        Transaction rawTx = Mockito.mock(Transaction.class);
        Mockito.when(tx.getDelegate()).thenReturn(rawTx);
        DummyGraph graphMock = Mockito.mock(DummyGraph.class, Mockito.CALLS_REAL_METHODS);
        Mockito.when(graphMock.tx()).thenReturn(tx);
        try (Tx tx2 = tx) {
            assertNotNull(Tx.getActive());
            // Don't call tx2.success() or tx2.failure()
        }
        assertNull(Tx.getActive());
        verify(tx).close();
        verify(graphMock.tx()).rollback();
        verify(graphMock.tx()).close();
        verify(graphMock.tx(), Mockito.never()).commit();
    }

    @Test
    public void testAbstractTxFailing() {
        DummyTransaction tx = Mockito.mock(DummyTransaction.class, Mockito.CALLS_REAL_METHODS);
        Transaction rawTx = Mockito.mock(Transaction.class);
        Mockito.when(tx.getDelegate()).thenReturn(rawTx);
        DummyGraph graphMock = Mockito.mock(DummyGraph.class, Mockito.CALLS_REAL_METHODS);
        Mockito.when(graphMock.tx()).thenReturn(tx);
        try (Tx tx2 = tx) {
            assertNotNull(Tx.getActive());
            tx2.failure();
        }
        assertNull(Tx.getActive());
        verify(tx).close();
        verify(graphMock.tx()).rollback();
        verify(graphMock.tx()).close();
        verify(graphMock.tx(), Mockito.never()).commit();
    }

    @Test
    public void testActiveTxRetrieval() {
        Tx tx = tx();
        mock = Mockito.mock(DummyTransaction.class);
        Assert.assertSame(tx, tx());
        Assert.assertSame(tx, tx());
        Assert.assertSame(tx, tx());
    }
    
    @Override
    public Tx createTx() {
        return mock;
    }
    
    @Override
    public <T> T tx(TxAction<T> txHandler) {
        try (Tx tx = tx()) {
            return txHandler.handle(mock);
        }
    }

}

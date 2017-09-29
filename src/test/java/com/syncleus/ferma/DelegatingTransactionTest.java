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
import org.apache.tinkerpop.gremlin.structure.Transaction;
import org.apache.tinkerpop.gremlin.structure.util.wrapped.WrappedGraph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author rqpa
 */
public class DelegatingTransactionTest {
    
    private Transaction gremlinTx;
    private WrappedFramedGraph<?> framedGraph;
    private DelegatingTransaction delegatingTx;
    
    @Before
    public void setUp() {
        gremlinTx = Mockito.mock(Transaction.class);
        framedGraph = Mockito.mock(WrappedFramedGraph.class, Mockito.RETURNS_MOCKS);
        delegatingTx = new DelegatingTransaction(gremlinTx, framedGraph);
    }
    
    @Test
    public void testAddTxListener() {
        
        Consumer<Transaction.Status> txListener = Mockito.mock(Consumer.class, "Foo");
        delegatingTx.addTransactionListener(txListener);
        
        // Only delegating so the same listener should be passed
        Mockito.verify(gremlinTx, Mockito.times(1)).addTransactionListener(txListener);
        Mockito.verifyZeroInteractions(framedGraph);
        Mockito.verifyZeroInteractions(txListener);
        Mockito.verifyNoMoreInteractions(gremlinTx);
    }
    
    @Test
    public void testClearTxListeners() {
        delegatingTx.clearTransactionListeners();
        
        Mockito.verify(gremlinTx, Mockito.times(1)).clearTransactionListeners();
        Mockito.verifyZeroInteractions(framedGraph);
        Mockito.verifyNoMoreInteractions(gremlinTx);
    }
    
    @Test
    public void testAutoClosable() {
        try (DelegatingTransaction tx = new DelegatingTransaction(gremlinTx, framedGraph)){
            
        }
        
        Mockito.verify(gremlinTx, Mockito.times(1)).close();
        Mockito.verifyZeroInteractions(framedGraph);
        Mockito.verifyNoMoreInteractions(gremlinTx);
    }
    
    @Test
    public void testCommit() {
        delegatingTx.commit();
        
        Mockito.verify(gremlinTx, Mockito.times(1)).commit();
        Mockito.verifyZeroInteractions(framedGraph);
        Mockito.verifyNoMoreInteractions(gremlinTx);
    }
    
    @Test
    public void testCreateThreadedTx() {
        delegatingTx.createThreadedTx();
        
        Mockito.verify(gremlinTx, Mockito.times(1)).createThreadedTx();
        // A new wrapped graph may need to be constructed so calls to simple
        // getters are OK
        Mockito.verify(framedGraph, Mockito.atLeast(0)).getBaseGraph();
        Mockito.verify(framedGraph, Mockito.atLeast(0)).getBuilder();
        Mockito.verify(framedGraph, Mockito.atLeast(0)).getRawTraversal();
        Mockito.verify(framedGraph, Mockito.atLeast(0)).getTypeResolver();
        // No other interactions. We're only delegating
        Mockito.verifyNoMoreInteractions(gremlinTx, framedGraph);
    }
    
    @Test
    public void testGetDelegate() {
        Transaction actualDelegate = delegatingTx.getDelegate();
        Mockito.verifyZeroInteractions(gremlinTx, framedGraph);
        Assert.assertEquals(gremlinTx, actualDelegate);
    }
    
    @Test
    public void testGetGraph() {
        WrappedGraph actualDelegate = delegatingTx.getGraph();
        Mockito.verifyZeroInteractions(gremlinTx, framedGraph);
        Assert.assertEquals(framedGraph, actualDelegate);
    }
    
    @Test
    public void testIsOpen() {
        assertDelegatingIsOpenUsage(true);
        assertDelegatingIsOpenUsage(false);
    }
    
    private void assertDelegatingIsOpenUsage(boolean expectedValue) {
        Transaction tx = Mockito.mock(Transaction.class);
        WrappedFramedGraph<?> graph = Mockito.mock(WrappedFramedGraph.class);
        Mockito.when(tx.isOpen()).thenReturn(expectedValue);
        DelegatingTransaction delTx = new DelegatingTransaction(tx, graph);
        Assert.assertEquals(expectedValue, delTx.isOpen());
        
        Mockito.verify(tx, Mockito.times(1)).isOpen();
        Mockito.verifyNoMoreInteractions(tx, graph);
    }
    
    @Test
    public void testOpenTx() {
        delegatingTx.open();
        
        Mockito.verify(gremlinTx, Mockito.times(1)).open();
        Mockito.verifyZeroInteractions(framedGraph);
        Mockito.verifyNoMoreInteractions(gremlinTx);
    }
    
    @Test
    public void testReadWrite() {
        delegatingTx.readWrite();
        
        Mockito.verify(gremlinTx, Mockito.times(1)).readWrite();
        Mockito.verifyZeroInteractions(framedGraph);
        Mockito.verifyNoMoreInteractions(gremlinTx);
    }
    
    @Test
    public void testRollback() {
        delegatingTx.rollback();
        
        Mockito.verify(gremlinTx, Mockito.times(1)).rollback();
        Mockito.verifyZeroInteractions(framedGraph);
        Mockito.verifyNoMoreInteractions(gremlinTx);
    }
    
    @Test
    public void testRemoveTxListener() {
        Consumer<Transaction.Status> txListener = Mockito.mock(Consumer.class, "Foo");
        delegatingTx.removeTransactionListener(txListener);
        
        Mockito.verify(gremlinTx, Mockito.times(1)).removeTransactionListener(txListener);
        Mockito.verifyZeroInteractions(framedGraph, txListener);
        Mockito.verifyNoMoreInteractions(gremlinTx);
    }
}

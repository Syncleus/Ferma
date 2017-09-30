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
package com.syncleus.ferma.tx;

import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author rqpa
 */
public class TxFramedGraphTest {
    
    private DummyGraph graph;

    @Before
    public void setUp() {
        graph = new DummyGraph(TinkerGraph.open());
    }
    
    @Test
    public void testSingleTxCreation() {
        Tx expected = graph.tx();
        Assert.assertSame(expected, graph.tx());
        Assert.assertSame(expected, graph.tx());
        Assert.assertSame(expected, graph.tx());
        Assert.assertSame(expected, graph.tx());
    }
    
}

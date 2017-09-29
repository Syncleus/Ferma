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
package com.syncleus.ferma.annotations;

import com.syncleus.ferma.DelegatingFramedGraph;
import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.TVertex;
import com.syncleus.ferma.VertexFrame;
import com.syncleus.ferma.graphtypes.javaclass.invalid.NoArgSetOutVertexEdge;
import com.syncleus.ferma.graphtypes.javaclass.invalid.OneArgGetOutVertexEdge;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class OutVertexMethodHandlerTest {

    private static final Set<Class<?>> TEST_TYPES = new HashSet<>(Arrays.asList(new Class<?>[]{God.class, FatherEdge.class, GodExtended.class, FatherEdgeExtended.class}));

    @Test
    public void testGetSonEdges() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final Iterator<? extends FatherEdge> childEdgeIterator = father.getSonEdges(FatherEdge.class);
        Assert.assertTrue(childEdgeIterator.hasNext());
        final FatherEdge childEdge = childEdgeIterator.next();

        final God son = childEdge.getSon();
        Assert.assertTrue(son != null);
        final VertexFrame sonVertex = son;
        Assert.assertEquals(sonVertex.getProperty("name"), "hercules");
    }
    
    @Test (expected = IllegalStateException.class)
    public void testSingleArgGetMethod() {
        DelegatingFramedGraph fg = new DelegatingFramedGraph(TinkerGraph.open(), false, true);
        TVertex t1 = fg.addFramedVertex();
        TVertex t2 = fg.addFramedVertex();
        fg.addFramedEdge(t1, t2, "somename", OneArgGetOutVertexEdge.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testNoArgSetMethod() {
        DelegatingFramedGraph fg = new DelegatingFramedGraph(TinkerGraph.open(), false, true);
        TVertex t1 = fg.addFramedVertex();
        TVertex t2 = fg.addFramedVertex();
        fg.addFramedEdge(t1, t2, "somename", NoArgSetOutVertexEdge.class);
    }
}

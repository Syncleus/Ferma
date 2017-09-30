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
package com.syncleus.ferma.typeresolvers;

import com.syncleus.ferma.AbstractEdgeFrame;
import com.syncleus.ferma.AbstractVertexFrame;
import com.syncleus.ferma.DelegatingFramedGraph;
import com.syncleus.ferma.EdgeFrame;
import com.syncleus.ferma.TEdge;
import com.syncleus.ferma.TVertex;
import com.syncleus.ferma.VertexFrame;
import com.syncleus.ferma.annotations.God;
import com.syncleus.ferma.graphtypes.network.ComputerVertex;
import com.syncleus.ferma.graphtypes.network.NetworkGraphLoader;
import java.util.List;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author rqpa
 */
public class UntypedTypeResolverTest {
    
    private UntypedTypeResolver resolver;
    
    @Before
    public void setUp() {
        resolver = new UntypedTypeResolver();
    }
    
    @Test
    public void testResolve() {
        Element mockElement = Mockito.mock(Element.class);
        // THis one should do no resolution. Mock should not be used and null returned.
        Assert.assertNull(resolver.resolve(mockElement));
        Mockito.verifyZeroInteractions(mockElement);
    }
    
    @Test
    public void testResolveWithDefault() {
        Element mockElement = Mockito.mock(Element.class);
        Assert.assertEquals(resolver.resolve(mockElement, VertexFrame.class), TVertex.class);
        Mockito.verifyZeroInteractions(mockElement);
        Assert.assertEquals(resolver.resolve(mockElement, AbstractVertexFrame.class), TVertex.class);
        Mockito.verifyZeroInteractions(mockElement);
        Assert.assertEquals(resolver.resolve(mockElement, EdgeFrame.class), TEdge.class);
        Mockito.verifyZeroInteractions(mockElement);
        Assert.assertEquals(resolver.resolve(mockElement, AbstractEdgeFrame.class), TEdge.class);
        Mockito.verifyZeroInteractions(mockElement);
        Assert.assertEquals(resolver.resolve(mockElement, God.class), God.class);
        Mockito.verifyZeroInteractions(mockElement);
    }
    
    @Test
    public void testInit() {
        Element mockElement = Mockito.mock(Element.class);
        resolver.init(mockElement, God.class);
        Mockito.verifyZeroInteractions(mockElement);
    }
    
    @Test
    public void testDenit() {
        Element mockElement = Mockito.mock(Element.class);
        resolver.deinit(mockElement);
        Mockito.verifyZeroInteractions(mockElement);
    }
    
    @Test
    public void testHasNotType() {
        // Resolver knows nothing about types so it should not filter any elements
        // and return the same traverser
        GraphTraversal<Element, Element> expected = Mockito.mock(GraphTraversal.class);
        GraphTraversal<Element, Element> actual = resolver.hasNotType(expected, God.class);
        Assert.assertSame(expected, actual);
        Mockito.verifyZeroInteractions(expected);
    }
    
    @Test
    public void testHasType() {
        // Does not know about types so should filter out all elements
        DelegatingFramedGraph<?> fg = NetworkGraphLoader.INSTANCE.load();
        List<Vertex> actualVertices = resolver
                .hasType(fg.getBaseGraph().traversal().V(), ComputerVertex.class)
                .toList();
        Assert.assertTrue(actualVertices.isEmpty());
    }
}

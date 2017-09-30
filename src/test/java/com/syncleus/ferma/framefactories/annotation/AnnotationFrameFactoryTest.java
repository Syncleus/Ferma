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
package com.syncleus.ferma.framefactories.annotation;

import com.syncleus.ferma.DelegatingFramedGraph;
import com.syncleus.ferma.ReflectionCache;
import com.syncleus.ferma.TEdge;
import com.syncleus.ferma.TVertex;
import com.syncleus.ferma.annotations.Adjacency;
import com.syncleus.ferma.annotations.Friend;
import com.syncleus.ferma.annotations.God;
import com.syncleus.ferma.graphtypes.javaclass.invalid.InvalidFrame;
import com.syncleus.ferma.graphtypes.javaclass.invalid.OneArgConstructorVertex;
import com.syncleus.ferma.typeresolvers.PolymorphicTypeResolver;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collections;
import net.bytebuddy.dynamic.DynamicType;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author rqpa
 */
public class AnnotationFrameFactoryTest {
    
    private AnnotationFrameFactory frameFactory;
    private DelegatingFramedGraph fg;
    
    @Before
    public void setUp() {
        frameFactory = new AnnotationFrameFactory(new ReflectionCache());
        fg = new DelegatingFramedGraph(TinkerGraph.open());
    }
    
    @After
    public void tearDown() throws IOException {
        fg.close();
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testOneArgNonAbstractFrame() {
        frameFactory.create(fg.addFramedVertex().getElement(), OneArgConstructorVertex.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testInvalidAbstractVertexFrame() {
        frameFactory.create(fg.addFramedVertex().getElement(), InvalidFrame.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testInvalidAbstractVertexEdge() {
        TVertex v1 = fg.addFramedVertex();
        TVertex v2 = fg.addFramedVertex();
        TEdge e1 = fg.addFramedEdge(v1, v2, "some_label");
        frameFactory.create(e1.getElement(), InvalidFrame.class);
    }
    
    @Test
    public void testCustomHandlers() {
        MethodHandler custom = Mockito.mock(AbstractMethodHandler.class, Mockito.CALLS_REAL_METHODS);
        Class<? extends Annotation> annotation = Adjacency.class;
        custom = Mockito.when(custom.getAnnotationType()).then(inv -> annotation).getMock();
        custom = Mockito
                .when(custom.processMethod(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenAnswer(inv -> inv.getArgumentAt(0, DynamicType.Builder.class))
                .getMock();
        AnnotationFrameFactory frameFactory = new AnnotationFrameFactory(new ReflectionCache(), Collections.singleton(custom));
        DelegatingFramedGraph framedGraph = new DelegatingFramedGraph(fg.getBaseGraph(), frameFactory, new PolymorphicTypeResolver());
        framedGraph.addFramedVertex(God.class);
        Mockito.verify(custom, Mockito.atLeast(0)).getAnnotationType();
        Mockito.verify(custom, Mockito.atLeastOnce()).processMethod(Mockito.any(), Mockito.any(), Mockito.any());
    }
    
    @Test (expected = IllegalStateException.class)
    public void testBadElementInterfaceFrame() {
        Element badElement = Mockito.mock(Element.class);
        frameFactory.create(badElement, God.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testBadElementAbstractClassFrame() {
        Element badElement = Mockito.mock(Element.class);
        frameFactory.create(badElement, Friend.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testBadElementBadFrame() {
        Element badElement = Mockito.mock(Element.class);
        frameFactory.create(badElement, InvalidFrame.class);
    }
    
}

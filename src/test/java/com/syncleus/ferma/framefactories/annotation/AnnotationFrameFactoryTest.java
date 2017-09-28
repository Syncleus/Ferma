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
import com.syncleus.ferma.graphtypes.javaclass.invalid.InvalidFrame;
import com.syncleus.ferma.graphtypes.javaclass.invalid.OneArgConstructorVertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Before;
import org.junit.Test;

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
}

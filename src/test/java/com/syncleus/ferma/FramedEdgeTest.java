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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.google.common.base.Function;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javax.annotation.Nullable;
import java.util.Iterator;

public class FramedEdgeTest {

    private Person p1;
    private Person p2;
    private Knows e1;
    private FramedGraph fg;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        final Graph g = TinkerGraph.open();
        fg = new DelegatingFramedGraph(g);
        p1 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        p2 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        p1.setName("Bryn");
        p2.setName("Julia");
        e1 = p1.addKnows(p2);
        e1.setYears(15);
        
    }

    @Test
    public void testLabel() {
        Assert.assertEquals("knows", e1.getLabel());
    }

    @Test
    public void testInV() {
        final Person person = e1.traverseSingleton(new Function<GraphTraversal<? extends Edge, ? extends Edge>, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversal<? extends Edge, ? extends Edge> input) {
                return input.inV();
            }
        }, Person.class, false);
        Assert.assertEquals(p2, person);
    }

    @Test
    public void testOutV() {
        final Person person = e1.traverseSingleton(new Function<GraphTraversal<? extends Edge, ? extends Edge>, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversal<? extends Edge, ? extends Edge> input) {
                return input.outV();
            }
        }, Person.class, false);
        Assert.assertEquals(p1, person);
    }

    @Test
    public void testBothV() {
        final Person person = e1.traverseSingleton(new Function<GraphTraversal<? extends Edge, ? extends Edge>, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversal<? extends Edge, ? extends Edge> input) {
                return input.bothV();
            }
        }, Person.class, false);
        Assert.assertEquals(p1, person);
    }

    @Test
    public void testInVExplicit() {
        final Person person = e1.traverseSingletonExplicit(new Function<GraphTraversal<? extends Edge, ? extends Edge>, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversal<? extends Edge, ? extends Edge> input) {
                return input.inV();
            }
        }, Person.class, false);
        Assert.assertEquals(p2, person);
    }

    @Test
    public void testOutVExplicit() {
        final Person person = e1.traverseSingletonExplicit(new Function<GraphTraversal<? extends Edge, ? extends Edge>, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversal<? extends Edge, ? extends Edge> input) {
                return input.outV();
            }
        }, Person.class, false);
        Assert.assertEquals(p1, person);
    }

    @Test
    public void testBothVExplicit() {
        final Person person = e1.traverseSingletonExplicit(new Function<GraphTraversal<? extends Edge, ? extends Edge>, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversal<? extends Edge, ? extends Edge> input) {
                return input.bothV();
            }
        }, Person.class, false);
        Assert.assertEquals(p1, person);
    }
}

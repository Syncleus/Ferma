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

import java.util.function.Function;
import com.syncleus.ferma.typeresolvers.PolymorphicTypeResolver;
import com.syncleus.ferma.framefactories.annotation.AnnotationFrameFactory;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.*;

public class PolymorphicTypeResolverTest {

    private static final Set<Class<?>> TEST_TYPES = new HashSet<>(Arrays.asList(new Class<?>[]{Person.class, Programmer.class}));
    private static final String CUSTOM_TYPE_KEY = "some_custom_type_key";

    @Test
    public void testChangeType() {
        final Graph godGraph = TinkerGraph.open();
        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        //add a single node to the graph, a programmer.
        framedGraph.addFramedVertex(Programmer.class);

        //make sure the newly added node is actually a programmer
        final Person programmer = framedGraph.traverse(new Function<GraphTraversalSource, GraphTraversal<?, ?>>() {
            @Nullable
            @Override
            public GraphTraversal<?, ?> apply(@Nullable final GraphTraversalSource input) {
                return input.V();
            }
        }).next(Person.class);
        Assert.assertTrue(programmer instanceof Programmer);

        //change the type resolution to person
        programmer.setTypeResolution(Person.class);

        //make sure the newly added node is not actually a programmer
        final Person person = framedGraph.traverse(new Function<GraphTraversalSource, GraphTraversal<?, ?>>() {
            @Nullable
            @Override
            public GraphTraversal<?, ?> apply(@Nullable final GraphTraversalSource input) {
                return input.V();
            }
        }).next(Person.class);
        Assert.assertFalse(person instanceof Programmer);
    }
    
    @Test
    public void testCustomTypeKey() {
        final Graph g = TinkerGraph.open();
        final ReflectionCache cache = new ReflectionCache(TEST_TYPES);
        final FramedGraph fg = new DelegatingFramedGraph(g, new AnnotationFrameFactory(cache), new PolymorphicTypeResolver(cache, CUSTOM_TYPE_KEY));

        final Person p1 = fg.addFramedVertex(Programmer.DEFAULT_INITIALIZER);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        p2.setName("Julia");

        final Person bryn = fg.traverse(new Function<GraphTraversalSource, GraphTraversal<?, ?>>() {
            @Nullable
            @Override
            public GraphTraversal<?, ?> apply(@Nullable final GraphTraversalSource input) {
                return input.V().has("name", "Bryn");
            }
        }).next(Person.class);
        final Person julia = fg.traverse(new Function<GraphTraversalSource, GraphTraversal<?, ?>>() {
            @Nullable
            @Override
            public GraphTraversal<?, ?> apply(@Nullable final GraphTraversalSource input) {
                return input.V().has("name", "Julia");
            }
        }).next(Person.class);

        Assert.assertEquals(Programmer.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());

        Assert.assertNotNull(bryn.getElement().property(CUSTOM_TYPE_KEY).value());
        Assert.assertFalse(bryn.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).isPresent());
        Assert.assertNotNull(julia.getElement().property(CUSTOM_TYPE_KEY).value());
        Assert.assertFalse(julia.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).isPresent());
    }

    @Test
    public void testCustomTypeKeyByClass() {
        final Graph g = TinkerGraph.open();
        final ReflectionCache cache = new ReflectionCache(TEST_TYPES);
        final FramedGraph fg = new DelegatingFramedGraph(g, new AnnotationFrameFactory(cache), new PolymorphicTypeResolver(cache, CUSTOM_TYPE_KEY));

        final Person p1 = fg.addFramedVertex(Programmer.class);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertex(Person.class);
        p2.setName("Julia");

        final Person bryn = fg.traverse(new Function<GraphTraversalSource, GraphTraversal<?, ?>>() {
            @Nullable
            @Override
            public GraphTraversal<?, ?> apply(@Nullable final GraphTraversalSource input) {
                return input.V().has("name", "Bryn");
            }
        }).next(Person.class);
        final Person julia = fg.traverse(new Function<GraphTraversalSource, GraphTraversal<?, ?>>() {
            @Nullable
            @Override
            public GraphTraversal<?, ?> apply(@Nullable final GraphTraversalSource input) {
                return input.V().has("name", "Julia");
            }
        }).next(Person.class);

        Assert.assertEquals(Programmer.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());

        Assert.assertNotNull(bryn.getElement().property(CUSTOM_TYPE_KEY).value());
        Assert.assertFalse(bryn.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).isPresent());
        Assert.assertNotNull(julia.getElement().property(CUSTOM_TYPE_KEY).value());
        Assert.assertFalse(julia.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).isPresent());
    }

    @Test
    public void testCustomTypeKeyExplicit() {
        final Graph g = TinkerGraph.open();
        final ReflectionCache cache = new ReflectionCache(TEST_TYPES);
        final FramedGraph fg = new DelegatingFramedGraph(g, new AnnotationFrameFactory(cache), new PolymorphicTypeResolver(cache, CUSTOM_TYPE_KEY));

        final Person p1 = fg.addFramedVertexExplicit(Programmer.DEFAULT_INITIALIZER);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertexExplicit(Person.DEFAULT_INITIALIZER);
        p2.setName("Julia");

        final Person bryn = fg.traverse(new Function<GraphTraversalSource, GraphTraversal<?, ?>>() {
            @Nullable
            @Override
            public GraphTraversal<?, ?> apply(@Nullable final GraphTraversalSource input) {
                return input.V().has("name", "Bryn");
            }
        }).next(Person.class);
        final Person julia = fg.traverse(new Function<GraphTraversalSource, GraphTraversal<?, ?>>() {
            @Nullable
            @Override
            public GraphTraversal<?, ?> apply(@Nullable final GraphTraversalSource input) {
                return input.V().has("name", "Julia");
            }
        }).next(Person.class);

        Assert.assertEquals(Person.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());

        Assert.assertFalse(bryn.getElement().property(CUSTOM_TYPE_KEY).isPresent());
        Assert.assertFalse(bryn.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).isPresent());
        Assert.assertFalse(julia.getElement().property(CUSTOM_TYPE_KEY).isPresent());
        Assert.assertFalse(julia.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).isPresent());
    }

    @Test
    public void testCustomTypeKeyExplicitByClass() {
        final Graph g = TinkerGraph.open();
        final ReflectionCache cache = new ReflectionCache(TEST_TYPES);
        final FramedGraph fg = new DelegatingFramedGraph(g, new AnnotationFrameFactory(cache), new PolymorphicTypeResolver(cache, CUSTOM_TYPE_KEY));

        final Person p1 = fg.addFramedVertexExplicit(Programmer.class);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertexExplicit(Person.class);
        p2.setName("Julia");

        final Person bryn = fg.traverse(new Function<GraphTraversalSource, GraphTraversal<?, ?>>() {
            @Nullable
            @Override
            public GraphTraversal<?, ?> apply(@Nullable final GraphTraversalSource input) {
                return input.V().has("name", "Bryn");
            }
        }).next(Person.class);
        final Person julia = fg.traverse(new Function<GraphTraversalSource, GraphTraversal<?, ?>>() {
            @Nullable
            @Override
            public GraphTraversal<?, ?> apply(@Nullable final GraphTraversalSource input) {
                return input.V().has("name", "Julia");
            }
        }).next(Person.class);

        Assert.assertEquals(Person.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());

        Assert.assertFalse(bryn.getElement().property(CUSTOM_TYPE_KEY).isPresent());
        Assert.assertFalse(bryn.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).isPresent());
        Assert.assertFalse(julia.getElement().property(CUSTOM_TYPE_KEY).isPresent());
        Assert.assertFalse(julia.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).isPresent());
    }
}

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
package com.syncleus.ferma.annotations;

import com.syncleus.ferma.DelegatingFramedGraph;
import com.syncleus.ferma.EdgeFrame;
import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.VertexFrame;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class IncidenceMethodHandlerTest {

    private static final Set<Class<?>> TEST_TYPES = new HashSet<>(Arrays.asList(new Class<?>[]{God.class, FatherEdge.class, GodExtended.class, FatherEdgeExtended.class}));

    @Test
    public void testGetSonEdgesDefault() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final Iterator<? extends EdgeFrame> childEdgeIterator = father.getSonEdges();
        Assert.assertTrue(childEdgeIterator.hasNext());
        final EdgeFrame childEdge = childEdgeIterator.next();
        Assert.assertEquals(childEdge.getElement().outVertex().property("name").value(), "hercules");
    }

    @Test
    public void testGetSonEdgesListDefault() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final List<? extends EdgeFrame> edgesList = father.getSonEdgesList();
        Assert.assertFalse(edgesList.isEmpty());
        final EdgeFrame childEdge = edgesList.get(0);
        Assert.assertEquals(childEdge.getElement().outVertex().property("name").value(), "hercules");
    }

    @Test
    public void testGetSonEdgesSetDefault() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final Set<? extends EdgeFrame> sonEdgesSet = father.getSonEdgesSet();
        Assert.assertFalse(sonEdgesSet.isEmpty());
        final EdgeFrame childEdge = sonEdgesSet.iterator().next();
        Assert.assertEquals(childEdge.getElement().outVertex().property("name").value(), "hercules");
    }

    @Test
    public void testGetSonEdgesByType() {
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
        Assert.assertTrue(childEdge != null);
        final EdgeFrame edge = childEdge;
        Assert.assertEquals(edge.getElement().outVertex().property("name").value(), "hercules");
    }

    @Test
    public void testGetSonEdgesListByType() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final List<? extends FatherEdge> childEdges = father.getSonEdgesList(FatherEdge.class);
        Assert.assertFalse(childEdges.isEmpty());
        final FatherEdge childEdge = childEdges.get(0);
        Assert.assertTrue(childEdge != null);
        final EdgeFrame edge = childEdge;
        Assert.assertEquals(edge.getElement().outVertex().property("name").value(), "hercules");
    }

    @Test
    public void testGetSonEdgesSetByType() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final Set<? extends FatherEdge> childEdges = father.getSonEdgesSet(FatherEdge.class);
        Assert.assertFalse(childEdges.isEmpty());
        final FatherEdge childEdge = childEdges.iterator().next();
        Assert.assertTrue(childEdge != null);
        final EdgeFrame edge = childEdge;
        Assert.assertEquals(edge.getElement().outVertex().property("name").value(), "hercules");
    }

    @Test
    public void testObtainSonEdgesByType() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final Iterator<? extends FatherEdge> childEdgeIterator = father.obtainSonEdges(FatherEdge.class);
        Assert.assertTrue(childEdgeIterator.hasNext());
        final FatherEdge childEdge = childEdgeIterator.next();
        Assert.assertTrue(childEdge != null);
        final EdgeFrame edge = childEdge;
        Assert.assertEquals(edge.getElement().outVertex().property("name").value(), "hercules");
    }

    @Test
    public void testGetSonEdgesExtended() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final Iterator<? extends FatherEdge> childEdgeIterator = father.getSonEdges(FatherEdgeExtended.class);
        Assert.assertTrue(childEdgeIterator.hasNext());
        final FatherEdge childEdge = childEdgeIterator.next();
        Assert.assertTrue(childEdge instanceof FatherEdgeExtended);
        Assert.assertTrue(childEdge instanceof EdgeFrame);
        final EdgeFrame edge = childEdge;
        Assert.assertEquals(edge.getElement().outVertex().property("name").value(), "hercules");
    }

    @Test
    public void testGetSonEdgeDefault() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final EdgeFrame childEdge = father.getSonEdge();
        Assert.assertEquals(childEdge.getElement().outVertex().property("name").value(), "hercules");
    }

    @Test
    public void testGetSonEdgeByType() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final FatherEdge childEdge = father.getSonEdge(FatherEdge.class);
        Assert.assertTrue(childEdge != null);
        final EdgeFrame edge = childEdge;
        Assert.assertEquals(childEdge.getElement().outVertex().property("name").value(), "hercules");
    }

    @Test
    public void testRemoveSonEdge() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final FatherEdge child = father.getSonEdge(FatherEdge.class);
        Assert.assertNotNull(child);
        Assert.assertTrue(child instanceof EdgeFrame);
        final EdgeFrame childEdge = child;
        Assert.assertEquals(childEdge.getRawTraversal().outV().next().property("name").value(), "hercules");

        father.removeSonEdge(child);

        Assert.assertFalse(father.getSonEdges(FatherEdge.class).hasNext());
    }

    @Test
    public void testDeleteSonEdge() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final FatherEdge child = father.getSonEdge(FatherEdge.class);
        Assert.assertNotNull(child);
        Assert.assertTrue(child instanceof EdgeFrame);
        final EdgeFrame childEdge = child;
        Assert.assertEquals(childEdge.getRawTraversal().outV().next().property("name").value(), "hercules");

        father.deleteSonEdge(child);

        Assert.assertFalse(father.getSonEdges(FatherEdge.class).hasNext());
    }

    @Test
    public void testIncludeSonEdge() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final FatherEdge fatherEdge = father.getSonEdge(FatherEdge.class);
        Assert.assertNotNull(fatherEdge);
        Assert.assertEquals(fatherEdge.getRawTraversal().outV().next().property("name").value(), "hercules");
        final God child = fatherEdge.getSon();

        father.deleteSonEdge(fatherEdge);

        Assert.assertFalse(father.getSonEdges(FatherEdge.class).hasNext());

        father.includeSonEdge(child, FatherEdge.DEFAULT_INITIALIZER);
        Assert.assertTrue(father.getSonEdges(FatherEdge.class).hasNext());
    }
}

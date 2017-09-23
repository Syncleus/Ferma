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

import java.util.function.Function;
import com.syncleus.ferma.*;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.*;

public class AdjacencyMethodHandlerTest {

    private static final Set<Class<?>> TEST_TYPES = new HashSet<>(Arrays.asList(new Class<?>[]{God.class, FatherEdge.class, GodExtended.class, GodAlternative.class}));

    @Test
    public void testGetSonsDefault() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final Iterator<? extends God> childIterator = father.getSons();
        Assert.assertTrue(childIterator.hasNext());
        final God child = childIterator.next();
        Assert.assertTrue(child != null);
        final VertexFrame childVertex = child;
        Assert.assertEquals(childVertex.getElement().property("name").value(), "hercules");
        Assert.assertTrue(child instanceof GodExtended);
    }

    @Test
    public void testGetSonsByType() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final Iterator<? extends God> childIterator = father.getSons(God.class);
        Assert.assertTrue(childIterator.hasNext());
        final God child = childIterator.next();
        Assert.assertTrue(child != null);
        final VertexFrame childVertex = child;
        Assert.assertEquals(childVertex.getElement().property("name").value(), "hercules");
        Assert.assertTrue(child instanceof GodExtended);
    }

    @Test
    public void testObtainSonsByType() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final Iterator<? extends God> childIterator = father.obtainSons();
        Assert.assertTrue(childIterator.hasNext());
        final God child = childIterator.next();
        Assert.assertTrue(child != null);
        final VertexFrame childVertex = child;
        Assert.assertEquals(childVertex.getElement().property("name").value(), "hercules");
        Assert.assertTrue(child instanceof GodExtended);
    }

    @Test
    public void testGetSonsExtended() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getElement().property("name").value(), "jupiter");

        final Iterator<? extends God> childIterator = father.getSons(GodExtended.class);
        Assert.assertTrue(childIterator.hasNext());
        final God child = childIterator.next();
        Assert.assertTrue(child != null);
        final VertexFrame childVertex = child;
        Assert.assertEquals(childVertex.getElement().property("name").value(), "hercules");
        Assert.assertTrue(child instanceof GodExtended);
    }

    @Test
    public void testGetSonsForceAlternative() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends GodAlternative> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(GodAlternative.class);

        final GodAlternative father = gods.iterator().next();
        Assert.assertTrue(father != null);
        Assert.assertTrue(father instanceof VertexFrame);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getElement().property("name").value(), "jupiter");

        final Iterator<? extends God> childIterator = father.getSons(God.class);
        Assert.assertTrue(childIterator.hasNext());
        final God child = childIterator.next();
        Assert.assertTrue(child != null);
        final VertexFrame childVertex = child;
        Assert.assertEquals(childVertex.getElement().property("name").value(), "hercules");
        Assert.assertTrue(child instanceof GodExtended);
    }

    @Test
    public void testGetSonsNoLabel() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends GodAlternative> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(GodAlternative.class);

        final GodAlternative father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getElement().property("name").value(), "jupiter");

        final Iterator<? extends God> childIterator = father.getNoLabel(God.class);
        Assert.assertTrue(!childIterator.hasNext());
    }

    @Test
    public void testGetSonDefault() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final God child = father.getSon();
        Assert.assertNotNull(child);
        Assert.assertTrue(child instanceof VertexFrame);
        final VertexFrame childVertex = child;
        Assert.assertEquals(childVertex.getElement().property("name").value(), "hercules");
        Assert.assertTrue(child instanceof GodExtended);
    }

    @Test
    public void testGetSonByType() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final God child = father.getSon(God.class);
        Assert.assertNotNull(child);
        Assert.assertTrue(child instanceof VertexFrame);
        final VertexFrame childVertex = child;
        Assert.assertEquals(childVertex.getElement().property("name").value(), "hercules");
        Assert.assertTrue(child instanceof GodExtended);
    }

    @Test
    public void testAddSonDefault() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final VertexFrame childVertex = father.addSon();
        Assert.assertFalse(childVertex.getElement().property("name").isPresent());
    }

    @Test
    public void testAddSonByTypeUntypedEdge() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final God child = father.addSon(God.DEFAULT_INITIALIZER);
        Assert.assertTrue(child != null);
        final VertexFrame childVertex = child;
        Assert.assertFalse(childVertex.getElement().property("name").isPresent());
    }

    @Test
    public void testAddSonByObjectUntypedEdge() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final God child = framedGraph.addFramedVertex(God.DEFAULT_INITIALIZER);
        father.addSon(child);

        Assert.assertTrue(child != null);
        final VertexFrame childVertex = child;
        Assert.assertFalse(childVertex.getElement().property("name").isPresent());
    }

    @Test
    public void testAddSonByTypeTypedEdge() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final God child = father.addSon(God.DEFAULT_INITIALIZER, FatherEdge.DEFAULT_INITIALIZER);
        Assert.assertTrue(child != null);
        final VertexFrame childVertex = child;
        Assert.assertFalse(childVertex.getElement().property("name").isPresent());
    }

    @Test
    public void testincludeSon() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final God child = father.includeSon(God.DEFAULT_INITIALIZER);
        Assert.assertTrue(child != null);
        final VertexFrame childVertex = child;
        Assert.assertFalse(childVertex.getElement().property("name").isPresent());
    }

    @Test
    public void testAddSonByObjectTypedEdge() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final God child = framedGraph.addFramedVertex(God.DEFAULT_INITIALIZER);
        father.addSon(child, FatherEdge.DEFAULT_INITIALIZER);

        Assert.assertTrue(child != null);
        final VertexFrame childVertex = child;
        Assert.assertFalse(childVertex.getElement().property("name").isPresent());
    }

    @Test
    public void testSetSonsEmpty() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final God child = father.getSon(God.class);
        Assert.assertNotNull(child);
        Assert.assertTrue(child instanceof VertexFrame);
        final VertexFrame childVertex = child;
        Assert.assertEquals(childVertex.getElement().property("name").value(), "hercules");
        Assert.assertTrue(child instanceof GodExtended);

        father.setSons(Collections.<God>emptySet().iterator());
        Assert.assertFalse(father.getSons(God.class).hasNext());
    }

    @Test
    public void testSetSons() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        God child = father.getSon(God.class);
        Assert.assertNotNull(child);
        Assert.assertTrue(child instanceof VertexFrame);
        final VertexFrame childVertex = child;
        Assert.assertEquals(childVertex.getElement().property("name").value(), "hercules");
        Assert.assertTrue(child instanceof GodExtended);

        father.setSons(Arrays.asList(framedGraph.addFramedVertex(God.DEFAULT_INITIALIZER)).iterator());

        child = father.getSon(God.class);

        Assert.assertNotNull(child);
        Assert.assertNull(child.getName());
    }

    @Test
    public void testApplySons() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        God child = father.getSon(God.class);
        Assert.assertNotNull(child);
        Assert.assertTrue(child instanceof VertexFrame);
        final VertexFrame childVertex = child;
        Assert.assertEquals(childVertex.getElement().property("name").value(), "hercules");
        Assert.assertTrue(child instanceof GodExtended);

        father.applySons(Arrays.asList(framedGraph.addFramedVertex(God.DEFAULT_INITIALIZER)).iterator());

        child = father.getSon(God.class);

        Assert.assertNotNull(child);
        Assert.assertNull(child.getName());
    }

    @Test
    public void testRemoveSon() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final God child = father.getSon(God.class);
        Assert.assertNotNull(child);
        Assert.assertTrue(child instanceof VertexFrame);
        final VertexFrame childVertex = child;
        Assert.assertEquals(childVertex.getElement().property("name").value(), "hercules");
        Assert.assertTrue(child instanceof GodExtended);

        father.removeSon(child);
        Assert.assertFalse(father.getSons(God.class).hasNext());
    }

    @Test
    public void testDeleteSon() {
        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final God child = father.getSon(God.class);
        Assert.assertNotNull(child);
        Assert.assertTrue(child instanceof VertexFrame);
        final VertexFrame childVertex = child;
        Assert.assertEquals(childVertex.getElement().property("name").value(), "hercules");
        Assert.assertTrue(child instanceof GodExtended);

        father.deleteSon(child);
        Assert.assertFalse(father.getSons(God.class).hasNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetSonsNoArgumentGetClass() {

        final Set<Class<?>> exceptionTypes = new HashSet<>(Arrays.asList(new Class<?>[]{BadGetSonsArgumentClass.class}));

        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, exceptionTypes);

        final List<? extends BadGetSonsArgumentClass> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(BadGetSonsArgumentClass.class);

        final BadGetSonsArgumentClass father = gods.iterator().next();
    }

    @Test(expected = IllegalStateException.class)
    public void testGetSonsNoArgumentGetInterface() {

        final Set<Class<?>> exceptionTypes = new HashSet<>(Arrays.asList(new Class<?>[]{BadGetSonsArgumentInterface.class}));

        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, exceptionTypes);

        final List<? extends BadGetSonsArgumentInterface> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(BadGetSonsArgumentInterface.class);

        final BadGetSonsArgumentInterface father = gods.iterator().next();
    }

    @Test(expected = IllegalStateException.class)
    public void testAddSonNoArgument() {

        final Set<Class<?>> exceptionTypes = new HashSet<>(Arrays.asList(new Class<?>[]{BadAddSonNoArguments.class}));

        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, exceptionTypes);

        final List<? extends BadGetSonsArgumentInterface> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(BadGetSonsArgumentInterface.class);

        final BadGetSonsArgumentInterface father = gods.iterator().next();
    }

    @Test(expected = IllegalStateException.class)
    public void testAddSonBadArgument() {

        final Set<Class<?>> exceptionTypes = new HashSet<>(Arrays.asList(new Class<?>[]{BadAddSonArgument.class}));

        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, exceptionTypes);

        final List<? extends BadGetSonsArgumentInterface> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(BadGetSonsArgumentInterface.class);

        final BadGetSonsArgumentInterface father = gods.iterator().next();
    }

    @Test(expected = IllegalStateException.class)
    public void testAddSonExtraArgument() {

        final Set<Class<?>> exceptionTypes = new HashSet<>(Arrays.asList(new Class<?>[]{BadAddSonExtraArgument.class}));

        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, exceptionTypes);

        final List<? extends BadGetSonsArgumentInterface> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(BadGetSonsArgumentInterface.class);

        final BadGetSonsArgumentInterface father = gods.iterator().next();
    }

    @Test(expected = IllegalStateException.class)
    public void testBadSonMethodName() {

        final Set<Class<?>> exceptionTypes = new HashSet<>(Arrays.asList(new Class<?>[]{BadSonMethodName.class}));

        final TinkerGraph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, exceptionTypes);

        final List<? extends BadGetSonsArgumentInterface> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(BadGetSonsArgumentInterface.class);

        final BadGetSonsArgumentInterface father = gods.iterator().next();
    }

    public interface BadSonMethodName extends VertexFrame {

        @Adjacency(label = "Father", direction = Direction.IN)
        <N extends God> Iterable<? extends N> badSons(Class<? extends N> type);
    }

    public interface BadAddSonExtraArgument extends VertexFrame {

        @Adjacency(label = "father", direction = Direction.IN)
        <N extends God> N addSon(String badArg, String worseArg);
    }

    public interface BadAddSonArgument extends VertexFrame {

        @Adjacency(label = "father", direction = Direction.IN)
        <N extends God> N addSon(String badArg);
    }

    public interface BadAddSonNoArguments extends VertexFrame {

        @Adjacency(label = "father", direction = Direction.IN)
        <N extends God> N addSon();
    }

    public static abstract class BadGetSonsArgumentClass extends AbstractVertexFrame {

        @Adjacency(label = "Father", direction = Direction.IN)
        public abstract <N extends God> Iterable<? extends N> getSons(String badStuff);
    }

    public interface BadGetSonsArgumentInterface extends VertexFrame {

        @Adjacency(label = "Father", direction = Direction.IN)
        <N extends God> Iterable<? extends N> getSons(String badStuff);
    }
}

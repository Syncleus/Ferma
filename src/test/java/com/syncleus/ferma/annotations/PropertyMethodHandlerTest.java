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
import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.VertexFrame;
import com.syncleus.ferma.graphtypes.javaclass.JavaAccessModifier;
import com.syncleus.ferma.graphtypes.javaclass.JavaGraphLoader;
import com.syncleus.ferma.graphtypes.javaclass.JavaTypeVertex;
import com.syncleus.ferma.graphtypes.javaclass.invalid.NoArgPropertySetter;
import com.syncleus.ferma.graphtypes.javaclass.invalid.OneArgPropertyGetter;
import com.syncleus.ferma.graphtypes.javaclass.invalid.OneArgPropertyRemover;
import com.syncleus.ferma.graphtypes.javaclass.invalid.TwoArgPropertySetter;
import com.syncleus.ferma.graphtypes.javaclass.invalid.UnresolvablePropertyAccessor;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class PropertyMethodHandlerTest {

    private static final Set<Class<?>> TEST_TYPES = new HashSet<>(Arrays.asList(new Class<?>[]{God.class, FatherEdge.class, GodExtended.class, GodAlternative.class}));

    @Test
    public void testGetName() {
        final Graph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");
        Assert.assertEquals("jupiter", father.getName());
    }

    @Test
    public void testObtainName() {
        final Graph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");
        Assert.assertEquals("jupiter", father.obtainName());
    }

    @Test
    public void testSetName() {
        final Graph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");
        father.setName("joopiter");

        gods = framedGraph.traverse(
            input -> input.V().has("name", "joopiter")).toList(God.class);

        father = gods.iterator().next();
        Assert.assertTrue(father != null);
        fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "joopiter");
    }

    @Test
    public void testApplyName() {
        final Graph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");
        father.applyName("joopiter");

        gods = framedGraph.traverse(
            input -> input.V().has("name", "joopiter")).toList(God.class);

        father = gods.iterator().next();
        Assert.assertTrue(father != null);
        fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "joopiter");
    }

    @Test
    public void testRemoveName() {
        final Graph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");
        father.removeName();

        Assert.assertNull(fatherVertex.getProperty("name"));
    }

    @Test
    public void testDeleteName() {
        final Graph godGraph = TinkerGraph.open();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        List<? extends God> gods = framedGraph.traverse(
            input -> input.V().has("name", "jupiter")).toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        final VertexFrame fatherVertex = father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");
        father.deleteName();

        Assert.assertNull(fatherVertex.getProperty("name"));
    }
    
    @Test
    public void testGetSetEnumProperty() {
        FramedGraph javaTypesGraph = JavaGraphLoader.INSTANCE.load();
        List<? extends JavaTypeVertex> collectionList = javaTypesGraph.traverse(
                input -> input.V().has("fqn", Collection.class.getName())).toList(JavaTypeVertex.class);
        JavaTypeVertex collectionVertex = collectionList.get(0);
        Assert.assertEquals(JavaAccessModifier.PUBLIC, collectionVertex.getAccessModifier());
        collectionVertex.setAccessModifier(JavaAccessModifier.PROTECTED);
        Assert.assertEquals(JavaAccessModifier.PROTECTED, collectionVertex.getAccessModifier());
        collectionVertex.setAccessModifier(null);
        Assert.assertNull(collectionVertex.getAccessModifier());
    }
    
    @Test (expected = IllegalStateException.class)
    public void testNoArgSetter() {
        FramedGraph javaTypesGraph = JavaGraphLoader.INSTANCE.load();
        javaTypesGraph.addFramedVertex(NoArgPropertySetter.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testTwoArgSetter() {
        FramedGraph javaTypesGraph = JavaGraphLoader.INSTANCE.load();
        javaTypesGraph.addFramedVertex(TwoArgPropertySetter.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testOneArgGetter() {
        FramedGraph javaTypesGraph = JavaGraphLoader.INSTANCE.load();
        javaTypesGraph.addFramedVertex(OneArgPropertyGetter.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testOneArgRemove() {
        FramedGraph javaTypesGraph = JavaGraphLoader.INSTANCE.load();
        javaTypesGraph.addFramedVertex(OneArgPropertyRemover.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testUnresolvableAccessor() {
        FramedGraph javaTypesGraph = JavaGraphLoader.INSTANCE.load();
        javaTypesGraph.addFramedVertex(UnresolvablePropertyAccessor.class);
    }
}

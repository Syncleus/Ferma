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

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import com.syncleus.ferma.annotations.God;
import com.syncleus.ferma.graphtypes.javaclass.JavaAccessModifier;
import java.io.IOException;
import org.apache.tinkerpop.gremlin.structure.T;
import org.junit.After;

public class AbstractElementFrameTest {

    private FramedGraph fg;
    private Person p1;
    private Knows e1;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        final Graph g = TinkerGraph.open();
        fg = new DelegatingFramedGraph(g);
        p1 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        final Person p2 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        p1.setName("Bryn");
        p2.setName("Julia");
        e1 = p1.addKnows(p2);
        e1.setYears(15);
    }
    
    @After
    public void deinit() throws IOException {
        fg.close();
    }

    @Test
    public void testGetId() {
        Assert.assertEquals((Long) 0L, (Long) p1.getId());
    }

    @Test
    public void testGetPropertyKeys() {
        Assert.assertEquals(Sets.newHashSet("name"), p1.getPropertyKeys());
    }

    @Test
    public void testGetStringProperty() {
        Assert.assertEquals("Bryn", p1.getProperty("name"));
    }

    @Test
    public void testSetStringProperty() {
        p1.setProperty("name", "Bryn Cooke");
        Assert.assertEquals("Bryn Cooke", p1.getProperty("name"));
    }
    
    @Test
    public void testGetSetTypedIntegerProperty() {
        String propName = "ageInMonths";
        Class<?> type = Integer.class;
        Integer value = 12 * 34;
        Assert.assertNull(p1.getProperty(propName, type));
        p1.setProperty(propName, value);
        Assert.assertEquals(p1.getProperty(propName, type), value);
        p1.setProperty(propName, null);
        Assert.assertNull(p1.getProperty(propName, type));
    }
    
    @Test
    public void testGetSetTypedEnumProperty() {
        // No semantic meaning but we test pure functionality so it's ok
        String propName = "foo";
        Class<?> type = JavaAccessModifier.class;
        JavaAccessModifier value = JavaAccessModifier.PRIVATE;
        Assert.assertNull(p1.getProperty(propName, type));
        p1.setProperty(propName, value);
        Assert.assertEquals(p1.getProperty(propName, type), value);
        p1.setProperty(propName, null);
        Assert.assertNull(p1.getProperty(propName, type));
    }

    @Test
    public void testSetPropertyNull() {
        p1.setProperty("name", null);
        Assert.assertNull(p1.getProperty("name"));
    }

    @Test
    public void testV() {
        final Long count = fg.getRawTraversal().V().count().next();
        Assert.assertEquals((Long) 2L, count);
    }

    @Test
    public void testE() {
        final Long count = fg.getRawTraversal().E().count().next();
        Assert.assertEquals((Long) 1L, count);
    }

    @Test
    public void testVById() {
        final Person person = fg.traverse(
            input -> input.V(p1.<Long>getId())).next(Person.class);
        Assert.assertEquals(p1, person);
    }

    @Test
    public void testEById() {
        final Knows knows = fg.traverse((GraphTraversalSource s) -> {return s.E(e1.<Long>getId());}).next(Knows.class);
//        final Knows knows = fg.traverse(new Function<GraphTraversalSource, GraphTraversal<?, ?>>() {
//            @Nullable
//            @Override
//            public GraphTraversal<?, ?> apply(@Nullable final GraphTraversalSource input) {
//                return input.E(e1.<Long>getId());
//            }
//        }).next(Knows.class);
        Assert.assertEquals(e1, knows);
    }

    @Test
    public void testVByIdExplicit() {
        final Person person = fg.traverse(
            input -> input.V(p1.<Long>getId())).nextExplicit(Person.class);
        Assert.assertEquals(p1, person);
    }

    @Test
    public void testEByIdExplicit() {
        final Knows knows = fg.traverse(
            input -> input.E(e1.<Long>getId())).nextExplicit(Knows.class);
        Assert.assertEquals(e1, knows);
    }

    @Test
    public void testRemove() {
        p1.remove();
        final Long count = fg.getRawTraversal().V().count().next();
        Assert.assertEquals((Long) 1L, count);
    }

    @Test
    public void testReframe() {
        final TVertex v1 = p1.reframe(TVertex.class);
        Assert.assertEquals((Long) p1.getId(), (Long) v1.getId());
    }

    @Test
    public void testReframeExplicitVertex() {
        final TVertex v1 = p1.reframeExplicit(TVertex.class);
        Assert.assertEquals((Long) p1.getId(), (Long) v1.getId());
    }

    @Test
    public void testReframeExplicitEdge() {
        final TEdge edgeReframed = e1.reframeExplicit(TEdge.class);
        Assert.assertEquals((Long) e1.getId(), (Long) edgeReframed.getId());
    }

    @Test
    public void testVNull() {
        final Long count = fg.getRawTraversal().V("noId").count().next();
        Assert.assertEquals((Long) 0L, count);
    }

    @Test
    public void testVtoJson() {
        JsonObject actual = p1.toJson();
        Assert.assertEquals(p1.getId(Long.class).longValue(), actual.get("id").getAsLong());
        Assert.assertEquals("vertex", actual.get("elementClass").getAsString());
        Assert.assertEquals(p1.getName(), actual.get("name").getAsString());
    }

    @Test
    public void testVtoJson2() {
        String stringPropName = "custom-string-property";
        String stringPropValue = "custom-string-value";
        String charPropName = "custom-char-property";
        Character charPropValue = 'D';
        String intPropName = "custom-int-property";
        int intPropValue = 1234;
        Person expected = fg.addFramedVertex(Person.DEFAULT_INITIALIZER,
                T.id, "some-id",
                stringPropName, stringPropValue,
                charPropName, charPropValue,
                intPropName, intPropValue);
        JsonObject actual = expected.toJson();
        Assert.assertEquals(expected.getId(String.class), actual.get("id").getAsString());
        Assert.assertEquals("vertex", actual.get("elementClass").getAsString());
        Assert.assertEquals(expected.getProperty(intPropName), (Integer) actual.get(intPropName).getAsInt());
        Assert.assertEquals(expected.getProperty(stringPropName), actual.get(stringPropName).getAsString());
        Assert.assertEquals(expected.getProperty(charPropName), (Character) actual.get(charPropName).getAsCharacter());
    }

    @Test
    public void testEtoJson() {
        JsonObject actual = e1.toJson();
        Assert.assertEquals(e1.getId(Long.class).longValue(), actual.get("id").getAsLong());
        Assert.assertEquals("edge", actual.get("elementClass").getAsString());
        Assert.assertEquals(e1.getYears(), actual.get("years").getAsInt());
    }

    @Test
    public void testEtoJson2() {
        String stringPropName = "custom-string-property";
        String stringPropValue = "custom-string-value";
        String charPropName = "custom-char-property";
        Character charPropValue = 'D';
        Person p3 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        Knows expected = fg.addFramedEdge(p1, p3, "knows", Knows.DEFAULT_INITIALIZER, 
                "years", 15,
                T.id, "some-id",
                stringPropName, stringPropValue,
                charPropName, charPropValue);
        JsonObject actual = expected.toJson();
        Assert.assertEquals(expected.getId(String.class), actual.get("id").getAsString());
        Assert.assertEquals("edge", actual.get("elementClass").getAsString());
        Assert.assertEquals(expected.getYears(), actual.get("years").getAsInt());
        Assert.assertEquals(expected.getProperty(stringPropName), actual.get(stringPropName).getAsString());
        Assert.assertEquals(expected.getProperty(charPropName), (Character) actual.get(charPropName).getAsCharacter());
    }
    
    @Test
    public void testEquals() {
        Assert.assertFalse(p1.equals(null));
        Programmer g1 = fg.addFramedVertex(Programmer.class);
        Assert.assertFalse(p1.equals(g1));
        Person p4 = fg.frameElement(p1.getElement(), Person.class);
        Assert.assertTrue(p1.equals(p4));
        Person p3 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        Assert.assertFalse(p1.equals(p3));
        p1.setElement(null);
        Assert.assertFalse(p1.equals(p3));
    }
}

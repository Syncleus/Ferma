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

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import com.google.common.collect.Lists;
import com.syncleus.ferma.framefactories.FrameFactory;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import com.syncleus.ferma.typeresolvers.PolymorphicTypeResolver;


public class FramedGraphTest {

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSanity() {
        final Graph g = TinkerGraph.open();
        final FramedGraph fg = new DelegatingFramedGraph(g);

        final Person p1 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        p2.setName("Julia");
        final Knows knows = p1.addKnows(p2);
        knows.setYears(15);

        final Person bryn = fg.traverse(
            input -> input.V().has("name", "Bryn")).next(Person.class);


        Assert.assertEquals("Bryn", bryn.getName());
        Assert.assertEquals(15, bryn.getKnowsList().get(0).getYears());

        final Long knowsCount = fg.getRawTraversal().V().has("name", "Julia").bothE().properties("years").aggregate("test").cap("test").count().next();
        Assert.assertEquals((Long) 1L, knowsCount);
    }

    @Test
    public void testSanityByClass() {
        final Graph g = TinkerGraph.open();
        final FramedGraph fg = new DelegatingFramedGraph(g);
        final Person p1 = fg.addFramedVertex(Person.class);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertex(Person.class);
        p2.setName("Julia");
        final Knows knows = p1.addKnows(p2);
        knows.setYears(15);

        final Person bryn = fg.traverse(
            input -> input.V().has("name", "Bryn")).next(Person.class);


        Assert.assertEquals("Bryn", bryn.getName());
        Assert.assertEquals(15, bryn.getKnowsList().get(0).getYears());

        final Long knowsCount = fg.getRawTraversal().V().has("name", "Julia").bothE().properties("years").aggregate("test").cap("test").count().next();
        Assert.assertEquals((Long) 1L, knowsCount);
    }

    @Test
    public void testSanityExplicit() {
        final Graph g = TinkerGraph.open();
        final FramedGraph fg = new DelegatingFramedGraph(g);
        final Person p1 = fg.addFramedVertexExplicit(Person.DEFAULT_INITIALIZER);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertexExplicit(Person.DEFAULT_INITIALIZER);
        p2.setName("Julia");
        final Knows knows = p1.addKnowsExplicit(p2);
        knows.setYears(15);

        final Person bryn = fg.traverse(
            input -> input.V().has("name", "Bryn")).nextExplicit(Person.class);


        Assert.assertEquals("Bryn", bryn.getName());
        Assert.assertEquals(15, bryn.getKnowsListExplicit().get(0).getYears());
        final Long knowsCount = fg.getRawTraversal().V().has("name", "Julia").bothE().properties("years").aggregate("test").cap("test").count().next();
        Assert.assertEquals((Long) 1L, knowsCount);
    }

    @Test
    public void testSanityExplicitByClass() {
        final Graph g = TinkerGraph.open();
        final FramedGraph fg = new DelegatingFramedGraph(g);
        final Person p1 = fg.addFramedVertexExplicit(Person.class);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertexExplicit(Person.class);
        p2.setName("Julia");
        final Knows knows = p1.addKnowsExplicit(p2);
        knows.setYears(15);

        final Person bryn = fg.traverse(
            input -> input.V().has("name", "Bryn")).nextExplicit(Person.class);


        Assert.assertEquals("Bryn", bryn.getName());
        Assert.assertEquals(15, bryn.getKnowsListExplicit().get(0).getYears());

        final Long knowsCount = fg.getRawTraversal().V().has("name", "Julia").bothE().properties("years").aggregate("test").cap("test").count().next();
        Assert.assertEquals((Long) 1L, knowsCount);
    }

    @Test
    public void testHasClassFilter() {
        final Graph g = TinkerGraph.open();
        final FramedGraph fg = new DelegatingFramedGraph(g, true, false);

        final Person rootPerson = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        for (int i = 0; i < 10; i++) {
            final Person person = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
            rootPerson.addKnows(person);
        }

        for (int i = 0; i < 5; i++) {
            final Program program = fg.addFramedVertex(Program.DEFAULT_INITIALIZER);
            program.getElement().addEdge("UNTYPED_EDGE", rootPerson.getElement());
        }

        final Long hasNotKnowsCount = fg.getTypeResolver().hasNotType(fg.getRawTraversal().E(), Knows.class).count().next();
        final Long hasKnowsCount = fg.getTypeResolver().hasType(fg.getRawTraversal().E(), Knows.class).count().next();
        final Long hasNotPersonCount = fg.getTypeResolver().hasNotType(fg.getRawTraversal().V(), Person.class).count().next();
        final Long noPersonCount = fg.getTypeResolver().hasType(fg.getTypeResolver().hasNotType(fg.getRawTraversal().V(), Person.class), Person.class).count().next();
        Assert.assertEquals((Long) 5L, hasNotKnowsCount);
        Assert.assertEquals((Long) 10L, hasKnowsCount);
        Assert.assertEquals((Long) 5L, hasNotPersonCount);
        Assert.assertEquals((Long) 0L, noPersonCount);

        final Iterator<? extends Person> persons = fg.traverse(
            input -> fg.getTypeResolver().hasType(input.V(), Person.class)).frameExplicit(Person.class);

        final List<Person> personList = Lists.newArrayList(persons);
        Assert.assertEquals(11, personList.size());
        // Verify that all found persons have indeed been filtered
        persons.forEachRemaining(new Consumer<Person>() {
            @Override
            public void accept(final Person person) {
                Assert.assertEquals(Person.class.getName(), person.getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
            }
        });
    }

    @Test
    public void testJavaTyping() {
        final Graph g = TinkerGraph.open();
        final FramedGraph fg = new DelegatingFramedGraph(g, true, false);

        final Person p1 = fg.addFramedVertex(Programmer.DEFAULT_INITIALIZER);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        p2.setName("Julia");

        final Person bryn = fg.traverse(
            input -> input.V().has("name", "Bryn")).next(Person.class);
        final Person julia = fg.traverse(
            input -> input.V().has("name", "Julia")).next(Person.class);

        Assert.assertEquals(Programmer.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());

        Assert.assertNotNull(bryn.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).value());
        Assert.assertNotNull(julia.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).value());
    }

    @Test
    public void testJavaTypingByClass() {
        final Graph g = TinkerGraph.open();
        final FramedGraph fg = new DelegatingFramedGraph(g, true, false);

        final Person p1 = fg.addFramedVertex(Programmer.class);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertex(Person.class);
        p2.setName("Julia");

        final Person bryn = fg.traverse(
            input -> input.V().has("name", "Bryn")).next(Person.class);
        final Person julia = fg.traverse(
            input -> input.V().has("name", "Julia")).next(Person.class);

        Assert.assertEquals(Programmer.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());

        Assert.assertNotNull(bryn.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).value());
        Assert.assertNotNull(julia.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).value());
    }

    @Test
    public void testJavaTypingAddExplicit() {
        final Graph g = TinkerGraph.open();
        final FramedGraph fg = new DelegatingFramedGraph(g, true, false);

        final Person p1 = fg.addFramedVertexExplicit(Programmer.DEFAULT_INITIALIZER);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertexExplicit(Person.DEFAULT_INITIALIZER);
        p2.setName("Julia");

        final Person bryn = fg.traverse(
            input -> input.V().has("name", "Bryn")).next(Person.class);
        final Person julia = fg.traverse(
            input -> input.V().has("name", "Julia")).next(Person.class);

        Assert.assertEquals(Person.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());

        Assert.assertFalse(bryn.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).isPresent());
        Assert.assertFalse(julia.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).isPresent());
    }

    @Test
    public void testJavaTypingAddExplicitByClass() {
        final Graph g = TinkerGraph.open();
        final FramedGraph fg = new DelegatingFramedGraph(g, true, false);

        final Person p1 = fg.addFramedVertexExplicit(Programmer.class);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertexExplicit(Person.class);
        p2.setName("Julia");

        final Person bryn = fg.traverse(
            input -> input.V().has("name", "Bryn")).next(Person.class);
        final Person julia = fg.traverse(
            input -> input.V().has("name", "Julia")).next(Person.class);

        Assert.assertEquals(Person.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());

        Assert.assertFalse(bryn.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).isPresent());
        Assert.assertFalse(julia.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).isPresent());
    }

    @Test
    public void testJavaTypingNextExplicit() {
        final Graph g = TinkerGraph.open();
        final FramedGraph fg = new DelegatingFramedGraph(g, true, false);

        final Person p1 = fg.addFramedVertex(Programmer.DEFAULT_INITIALIZER);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        p2.setName("Julia");

        final Person bryn = fg.traverse(
            input -> input.V().has("name", "Bryn")).nextExplicit(Person.class);
        final Person julia = fg.traverse(
            input -> input.V().has("name", "Julia")).nextExplicit(Person.class);

        Assert.assertEquals(Person.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());

        Assert.assertNotNull(bryn.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).value());
        Assert.assertNotNull(julia.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).value());
    }

    @Test
    public void testJavaTypingNextExplicitByClass() {
        final Graph g = TinkerGraph.open();
        final FramedGraph fg = new DelegatingFramedGraph(g, true, false);

        final Person p1 = fg.addFramedVertex(Programmer.class);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertex(Person.class);
        p2.setName("Julia");

        final Person bryn = fg.traverse(
            input -> input.V().has("name", "Bryn")).nextExplicit(Person.class);
        final Person julia = fg.traverse(
            input -> input.V().has("name", "Julia")).nextExplicit(Person.class);

        Assert.assertEquals(Person.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());

        Assert.assertNotNull(bryn.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).value());
        Assert.assertNotNull(julia.getElement().property(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY).value());
    }

    @Test
    public void testCustomFrameBuilder() {
        final Person o = new Person();
        final Graph g = TinkerGraph.open();
        final FramedGraph fg = new DelegatingFramedGraph(g, new FrameFactory() {

            @SuppressWarnings("unchecked")
            @Override
            public <T> T create(final Element e, final Class<T> kind) {
                return (T) o;
            }
        }, new PolymorphicTypeResolver());
        final Person person = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        Assert.assertEquals(o, person);
    }

    @Test
    public void testCustomFrameBuilderByClass() {
        final Person o = new Person();
        final Graph g = TinkerGraph.open();
        final FramedGraph fg = new DelegatingFramedGraph(g, new FrameFactory() {

            @SuppressWarnings("unchecked")
            @Override
            public <T> T create(final Element e, final Class<T> kind) {
                return (T) o;
            }
        }, new PolymorphicTypeResolver());
        final Person person = fg.addFramedVertex(Person.class);
        Assert.assertEquals(o, person);
    }

    @Test
    public void testCustomFrameBuilderExplicit() {
        final Person o = new Person();
        final Graph g = TinkerGraph.open();
        final FramedGraph fg = new DelegatingFramedGraph(g, new FrameFactory() {

            @SuppressWarnings("unchecked")
            @Override
            public <T> T create(final Element e, final Class<T> kind) {
                return (T) o;
            }
        }, new PolymorphicTypeResolver());
        final Person person = fg.addFramedVertexExplicit(Person.DEFAULT_INITIALIZER);
        Assert.assertEquals(o, person);
    }

    @Test
    public void testCustomFrameBuilderExplicitByClass() {
        final Person o = new Person();
        final Graph g = TinkerGraph.open();
        final FramedGraph fg = new DelegatingFramedGraph(g, new FrameFactory() {

            @SuppressWarnings("unchecked")
            @Override
            public <T> T create(final Element e, final Class<T> kind) {
                return (T) o;
            }
        }, new PolymorphicTypeResolver());
        final Person person = fg.addFramedVertexExplicit(Person.class);
        Assert.assertEquals(o, person);
    }

    @Test
    public void testUntypedFrames() {
        final Graph g = TinkerGraph.open();
        final FramedGraph fg = new DelegatingFramedGraph(g);
        final TVertex p1 = fg.addFramedVertex();
        p1.setProperty("name", "Bryn");

        final TVertex p2 = fg.addFramedVertex();
        p2.setProperty("name", "Julia");
        final TEdge knows = p1.addFramedEdge("knows", p2);
        knows.setProperty("years", 15);

        final VertexFrame bryn = fg.traverse(
            input -> input.V().has("name", "Bryn")).next(VertexFrame.class);


        Assert.assertEquals("Bryn", bryn.getProperty("name"));
        Assert.assertEquals(15, bryn.getRawTraversal().outE("knows").toList().get(0).property("years").value());

        final Long knowsCount = fg.getRawTraversal().V().has("name", "Julia").bothE().properties("years").aggregate("test").cap("test").count().next();
        Assert.assertEquals((Long) 1L, knowsCount);
    }

    @Test
    public void testUntypedFramesExplicit() {
        final Graph g = TinkerGraph.open();
        final FramedGraph fg = new DelegatingFramedGraph(g);
        final TVertex p1 = fg.addFramedVertexExplicit();
        p1.setProperty("name", "Bryn");

        final TVertex p2 = fg.addFramedVertexExplicit();
        p2.setProperty("name", "Julia");
        final TEdge knows = p1.addFramedEdgeExplicit("knows", p2);
        knows.setProperty("years", 15);

        final VertexFrame bryn = fg.traverse(
            input -> input.V().has("name", "Bryn")).next(VertexFrame.class);


        Assert.assertEquals("Bryn", bryn.getProperty("name"));
        Assert.assertEquals(15, bryn.getRawTraversal().outE("knows").toList().get(0).property("years").value());

        final Long knowsCount = fg.getRawTraversal().V().has("name", "Julia").bothE().properties("years").aggregate("test").cap("test").count().next();
        Assert.assertEquals((Long) 1L, knowsCount);
    }

    @Test
    public void testGetFramedVertexExplicit() {
        final Graph g = TinkerGraph.open();
        final FramedGraph fg = new DelegatingFramedGraph(g);
        final TVertex p1 = fg.addFramedVertexExplicit();
        p1.setProperty("name", "Bryn");

        Person p = fg.getFramedVertexExplicit(Person.class, p1.getId());
        Assert.assertNotNull(p);
        Assert.assertEquals("Bryn", p.getName());
    }
}

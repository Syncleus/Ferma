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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.syncleus.ferma.framefactories.FrameFactory;
import com.syncleus.ferma.typeresolvers.PolymorphicTypeResolver;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.ThreadedTransactionalGraph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;

public class FramedGraphTest {

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSanity() {
        final Graph g = new TinkerGraph();
        final FramedGraph fg = new DelegatingFramedGraph(g);

        final Person p1 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        p2.setName("Julia");
        final Knows knows = p1.addKnows(p2);
        knows.setYears(15);

        final Person bryn = fg.v().has("name", "Bryn").next(Person.class);


        Assert.assertEquals("Bryn", bryn.getName());
        Assert.assertEquals(15, bryn.getKnowsList().get(0).getYears());

        final Collection<? extends Integer> knowsCollection = fg.v().has("name", "Julia").bothE().property("years", Integer.class).aggregate().cap();
        Assert.assertEquals(1, knowsCollection.size());
    }
    
    @Test
    public void testSanityByClass() {
        final Graph g = new TinkerGraph();
        final FramedGraph fg = new DelegatingFramedGraph(g);
        final Person p1 = fg.addFramedVertex(Person.class);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertex(Person.class);
        p2.setName("Julia");
        final Knows knows = p1.addKnows(p2);
        knows.setYears(15);

        final Person bryn = fg.v().has("name", "Bryn").next(Person.class);


        Assert.assertEquals("Bryn", bryn.getName());
        Assert.assertEquals(15, bryn.getKnowsList().get(0).getYears());

        final Collection<? extends Integer> knowsCollection = fg.v().has("name", "Julia").bothE().property("years", Integer.class).aggregate().cap();
        Assert.assertEquals(1, knowsCollection.size());
    }

    @Test
    public void testSanityExplicit() {
        final Graph g = new TinkerGraph();
        final FramedGraph fg = new DelegatingFramedGraph(g);
        final Person p1 = fg.addFramedVertexExplicit(Person.DEFAULT_INITIALIZER);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertexExplicit(Person.DEFAULT_INITIALIZER);
        p2.setName("Julia");
        final Knows knows = p1.addKnowsExplicit(p2);
        knows.setYears(15);

        final Person bryn = fg.v().has("name", "Bryn").nextExplicit(Person.class);


        Assert.assertEquals("Bryn", bryn.getName());
        Assert.assertEquals(15, bryn.getKnowsListExplicit().get(0).getYears());

        final Collection<? extends Integer> knowsCollection = fg.v().has("name", "Julia").bothE().property("years", Integer.class).aggregate().cap();
        Assert.assertEquals(1, knowsCollection.size());
    }
    
    @Test
    public void testSanityExplicitByClass() {
        final Graph g = new TinkerGraph();
        final FramedGraph fg = new DelegatingFramedGraph(g);
        final Person p1 = fg.addFramedVertexExplicit(Person.class);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertexExplicit(Person.class);
        p2.setName("Julia");
        final Knows knows = p1.addKnowsExplicit(p2);
        knows.setYears(15);

        final Person bryn = fg.v().has("name", "Bryn").nextExplicit(Person.class);


        Assert.assertEquals("Bryn", bryn.getName());
        Assert.assertEquals(15, bryn.getKnowsListExplicit().get(0).getYears());

        final Collection<? extends Integer> knowsCollection = fg.v().has("name", "Julia").bothE().property("years", Integer.class).aggregate().cap();
        Assert.assertEquals(1, knowsCollection.size());
    }

    @Test
    public void testHasClassFilter() {
        final Graph g = new TinkerGraph();
        final FramedGraph fg = new DelegatingFramedGraph(g, true, false);

        Person rootPerson = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        for (int i = 0; i < 10; i++) {
            Person person = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
            rootPerson.addKnows(person);
        }

        for (int i = 0; i < 5; i++) {
            Program program = fg.addFramedVertex(Program.DEFAULT_INITIALIZER);
            program.getElement().addEdge("UNTYPED_EDGE", rootPerson.getElement());
        }

        assertEquals(5, fg.e().hasNot(Knows.class).count());
        assertEquals(10, fg.e().has(Knows.class).count());
        assertEquals(5, fg.v().hasNot(Person.class).count());
        List<? extends Person> persons = fg.v().hasNot(Person.class).has(Person.class).toListExplicit(Person.class);
        assertEquals(0, persons.size());

        persons = fg.v().has(Person.class).toListExplicit(Person.class);
        assertEquals(11, persons.size());
        // Verify that all found persons have indeed been filtered
        for(Person person: persons ) {
            assertEquals(Person.class.getName(), person.getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
        }
    }

    @Test
    public void testJavaTyping() {
        final Graph g = new TinkerGraph();
        final FramedGraph fg = new DelegatingFramedGraph(g, true, false);

        final Person p1 = fg.addFramedVertex(Programmer.DEFAULT_INITIALIZER);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        p2.setName("Julia");

        final Person bryn = fg.v().has("name", "Bryn").next(Person.class);
        final Person julia = fg.v().has("name", "Julia").next(Person.class);

        Assert.assertEquals(Programmer.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());
        
        Assert.assertNotNull(bryn.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
        Assert.assertNotNull(julia.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
    }
    
    @Test
    public void testJavaTypingByClass() {
        final Graph g = new TinkerGraph();
        final FramedGraph fg = new DelegatingFramedGraph(g, true, false);

        final Person p1 = fg.addFramedVertex(Programmer.class);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertex(Person.class);
        p2.setName("Julia");

        final Person bryn = fg.v().has("name", "Bryn").next(Person.class);
        final Person julia = fg.v().has("name", "Julia").next(Person.class);

        Assert.assertEquals(Programmer.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());
        
        Assert.assertNotNull(bryn.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
        Assert.assertNotNull(julia.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
    }

    @Test
    public void testJavaTypingAddExplicit() {
        final Graph g = new TinkerGraph();
        final FramedGraph fg = new DelegatingFramedGraph(g, true, false);

        final Person p1 = fg.addFramedVertexExplicit(Programmer.DEFAULT_INITIALIZER);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertexExplicit(Person.DEFAULT_INITIALIZER);
        p2.setName("Julia");

        final Person bryn = fg.v().has("name", "Bryn").next(Person.class);
        final Person julia = fg.v().has("name", "Julia").next(Person.class);

        Assert.assertEquals(Person.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());
        
        Assert.assertNull(bryn.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
        Assert.assertNull(julia.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
    }
    
    @Test
    public void testJavaTypingAddExplicitByClass() {
        final Graph g = new TinkerGraph();
        final FramedGraph fg = new DelegatingFramedGraph(g, true, false);

        final Person p1 = fg.addFramedVertexExplicit(Programmer.class);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertexExplicit(Person.class);
        p2.setName("Julia");

        final Person bryn = fg.v().has("name", "Bryn").next(Person.class);
        final Person julia = fg.v().has("name", "Julia").next(Person.class);

        Assert.assertEquals(Person.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());
        
        Assert.assertNull(bryn.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
        Assert.assertNull(julia.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
    }

    @Test
    public void testJavaTypingNextExplicit() {
        final Graph g = new TinkerGraph();
        final FramedGraph fg = new DelegatingFramedGraph(g, true, false);

        final Person p1 = fg.addFramedVertex(Programmer.DEFAULT_INITIALIZER);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        p2.setName("Julia");

        final Person bryn = fg.v().has("name", "Bryn").nextExplicit(Person.class);
        final Person julia = fg.v().has("name", "Julia").nextExplicit(Person.class);

        Assert.assertEquals(Person.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());
        
        Assert.assertNotNull(bryn.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
        Assert.assertNotNull(julia.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
    }
    
    @Test
    public void testJavaTypingNextExplicitByClass() {
        final Graph g = new TinkerGraph();
        final FramedGraph fg = new DelegatingFramedGraph(g, true, false);

        final Person p1 = fg.addFramedVertex(Programmer.class);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertex(Person.class);
        p2.setName("Julia");

        final Person bryn = fg.v().has("name", "Bryn").nextExplicit(Person.class);
        final Person julia = fg.v().has("name", "Julia").nextExplicit(Person.class);

        Assert.assertEquals(Person.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());
        
        Assert.assertNotNull(bryn.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
        Assert.assertNotNull(julia.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
    }

    @Test
    public void testCustomFrameBuilder() {
        final Person o = new Person();
        final Graph g = new TinkerGraph();
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
        final Graph g = new TinkerGraph();
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
        final Graph g = new TinkerGraph();
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
        final Graph g = new TinkerGraph();
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

    @Mock(answer = Answers.RETURNS_MOCKS)
    private ThreadedTransactionalGraph transactionalGraph;

    @Test
    public void testTransactionCommitted() {
        final FramedTransactionalGraph fg = new DelegatingFramedTransactionalGraph(transactionalGraph);
        fg.commit();
        Mockito.verify(transactionalGraph).commit();
    }

    @Test
    public void testTransactionRolledBack() {
        final FramedTransactionalGraph fg = new DelegatingFramedTransactionalGraph(transactionalGraph);
        fg.rollback();
        Mockito.verify(transactionalGraph).rollback();
    }

    @Test
    public void testTransactionStart() {
        final FramedThreadedTransactionalGraph fg = new DelegatingFramedThreadedTransactionalGraph(transactionalGraph);
        fg.newTransaction();
        Mockito.verify(transactionalGraph).newTransaction();
    }

    @Test
    public void testUntypedFrames() {
        final Graph g = new TinkerGraph();
        final FramedGraph fg = new DelegatingFramedGraph(g);
        final TVertex p1 = fg.addFramedVertex();
        p1.setProperty("name", "Bryn");

        final TVertex p2 = fg.addFramedVertex();
        p2.setProperty("name", "Julia");
        final TEdge knows = p1.addFramedEdge("knows", p2);
        knows.setProperty("years", 15);

        final VertexFrame bryn = fg.v().has("name", "Bryn").next();


        Assert.assertEquals("Bryn", bryn.getProperty("name"));
        Assert.assertEquals(15, bryn.outE("knows").toList().get(0).getProperty("years"));

        final Collection<? extends Integer> knowsCollection = fg.v().has("name", "Julia").bothE().property("years", Integer.class).aggregate().cap();
        Assert.assertEquals(1, knowsCollection.size());
    }

    @Test
    public void testUntypedFramesExplicit() {
        final Graph g = new TinkerGraph();
        final FramedGraph fg = new DelegatingFramedGraph(g);
        final TVertex p1 = fg.addFramedVertexExplicit();
        p1.setProperty("name", "Bryn");

        final TVertex p2 = fg.addFramedVertexExplicit();
        p2.setProperty("name", "Julia");
        final TEdge knows = p1.addFramedEdgeExplicit("knows", p2);
        knows.setProperty("years", 15);

        final VertexFrame bryn = fg.v().has("name", "Bryn").next();


        Assert.assertEquals("Bryn", bryn.getProperty("name"));
        Assert.assertEquals(15, bryn.outE("knows").toList().get(0).getProperty("years"));

        final Collection<? extends Integer> knowsCollection = fg.v().has("name", "Julia").bothE().property("years", Integer.class).aggregate().cap();
        Assert.assertEquals(1, knowsCollection.size());
    }

    @Test
    public void testGetFramedVertexExplicit() {
        final Graph g = new TinkerGraph();
        final FramedGraph fg = new DelegatingFramedGraph(g);
        final TVertex p1 = fg.addFramedVertexExplicit();
        p1.setProperty("name", "Bryn");

        Person p = fg.getFramedVertexExplicit(Person.class, p1.getId());
        assertNotNull(p);
        assertEquals("Bryn", p.getName());
    }
}

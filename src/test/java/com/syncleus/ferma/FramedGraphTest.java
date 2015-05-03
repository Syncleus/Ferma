/******************************************************************************
 *                                                                             *
 *  Copyright: (c) Syncleus, Inc.                                              *
 *                                                                             *
 *  You may redistribute and modify this source code under the terms and       *
 *  conditions of the Open Source Community License - Type C version 1.0       *
 *  or any later version as published by Syncleus, Inc. at www.syncleus.com.   *
 *  There should be a copy of the license included with this file. If a copy   *
 *  of the license is not included you are granted no right to distribute or   *
 *  otherwise use this file except through a legal and valid license. You      *
 *  should also contact Syncleus, Inc. at the information below if you cannot  *
 *  find a license:                                                            *
 *                                                                             *
 *  Syncleus, Inc.                                                             *
 *  2604 South 12th Street                                                     *
 *  Philadelphia, PA 19148                                                     *
 *                                                                             *
 ******************************************************************************/
package com.syncleus.ferma;

import com.syncleus.ferma.framefactories.FrameFactory;
import com.syncleus.ferma.typeresolvers.PolymorphicTypeResolver;
import java.util.Collection;

import com.tinkerpop.blueprints.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
}

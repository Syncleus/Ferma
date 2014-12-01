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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
/**
 * @author Bryn Cooke (http://jglue.org)
 */

public class FramedGraphTest {
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
    @Test
    public void testSanity() {
        Graph g = new TinkerGraph();
        FramedGraph fg = new DelegatingFramedGraph(g);
        Person p1 = fg.addFramedVertex(Person.class);
        p1.setName("Bryn");

        Person p2 = fg.addFramedVertex(Person.class);
        p2.setName("Julia");
        Knows knows = p1.addKnows(p2);
        knows.setYears(15);

        Person bryn = fg.v().has("name", "Bryn").next(Person.class);
        
        
        Assert.assertEquals("Bryn", bryn.getName());
        Assert.assertEquals(15, bryn.getKnowsList().get(0).getYears());
   
        Collection<? extends Integer> knowsCollection = fg.v().has("name", "Julia").bothE().property("years", Integer.class).aggregate().cap();
        Assert.assertEquals(1, knowsCollection.size());
    }

    @Test
    public void testSanityExplicit() {
        Graph g = new TinkerGraph();
        FramedGraph fg = new DelegatingFramedGraph(g);
        Person p1 = fg.addFramedVertexExplicit(Person.class);
        p1.setName("Bryn");

        Person p2 = fg.addFramedVertexExplicit(Person.class);
        p2.setName("Julia");
        Knows knows = p1.addKnowsExplicit(p2);
        knows.setYears(15);

        Person bryn = fg.v().has("name", "Bryn").nextExplicit(Person.class);


        Assert.assertEquals("Bryn", bryn.getName());
        Assert.assertEquals(15, bryn.getKnowsListExplicit().get(0).getYears());

        Collection<? extends Integer> knowsCollection = fg.v().has("name", "Julia").bothE().property("years", Integer.class).aggregate().cap();
        Assert.assertEquals(1, knowsCollection.size());
    }
    
    @Test
    public void testJavaTyping() {
        Graph g = new TinkerGraph();
        FramedGraph fg = new DelegatingFramedGraph(g, true, false);

        Person p1 = fg.addFramedVertex(Programmer.class);
        p1.setName("Bryn");

        Person p2 = fg.addFramedVertex(Person.class);
        p2.setName("Julia");

        Person bryn = fg.v().has("name", "Bryn").next(Person.class);
        Person julia = fg.v().has("name", "Julia").next(Person.class);

        Assert.assertEquals(Programmer.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());
    }

    @Test
    public void testJavaTypingAddExplicit() {
        Graph g = new TinkerGraph();
        FramedGraph fg = new DelegatingFramedGraph(g, true, false);

        Person p1 = fg.addFramedVertexExplicit(Programmer.class);
        p1.setName("Bryn");

        Person p2 = fg.addFramedVertexExplicit(Person.class);
        p2.setName("Julia");

        Person bryn = fg.v().has("name", "Bryn").next(Person.class);
        Person julia = fg.v().has("name", "Julia").next(Person.class);

        Assert.assertEquals(Person.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());
    }

    @Test
    public void testJavaTypingNextExplicit() {
        Graph g = new TinkerGraph();
        FramedGraph fg = new DelegatingFramedGraph(g, true, false);

        Person p1 = fg.addFramedVertex(Programmer.class);
        p1.setName("Bryn");

        Person p2 = fg.addFramedVertex(Person.class);
        p2.setName("Julia");

        Person bryn = fg.v().has("name", "Bryn").nextExplicit(Person.class);
        Person julia = fg.v().has("name", "Julia").nextExplicit(Person.class);

        Assert.assertEquals(Person.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());
    }

    @Test
    public void testCustomFrameBuilder() {
    	final Person o = new Person();
        Graph g = new TinkerGraph();
        FramedGraph fg = new DelegatingFramedGraph(g, new FrameFactory() {
			
			@SuppressWarnings("unchecked")
			@Override
			public <T> T create(Element e, Class<T> kind) {
				return (T)o;
			}
		}, new SimpleTypeResolver());
        Person person = fg.addFramedVertex(Person.class);
        Assert.assertEquals(o, person);
    }

    @Test
    public void testCustomFrameBuilderExplicit() {
        final Person o = new Person();
        Graph g = new TinkerGraph();
        FramedGraph fg = new DelegatingFramedGraph(g, new FrameFactory() {

            @SuppressWarnings("unchecked")
            @Override
            public <T> T create(Element e, Class<T> kind) {
                return (T)o;
            }
        }, new SimpleTypeResolver());
        Person person = fg.addFramedVertexExplicit(Person.class);
        Assert.assertEquals(o, person);
    }
    
    @Mock
    private TransactionalGraph transactionalGraph;
    
    public void testTransactionUnsupported() {
        Graph g = new TinkerGraph();
        FramedGraph fg = new DelegatingFramedGraph(g);
        try(Transaction t = fg.tx()) {
        	
        }

    }
    
    @Test
    public void testTransactionCommitted() {
        
        FramedGraph fg = new DelegatingFramedGraph(transactionalGraph);
        try(Transaction t = fg.tx()) {
        	t.commit();
        }

        Mockito.verify(transactionalGraph).commit();
    }
    
    @Test
    public void testTransactionRolledBack() {
        
        FramedGraph fg = new DelegatingFramedGraph(transactionalGraph);
        try(Transaction t = fg.tx()) {
        	t.rollback();
        }

        Mockito.verify(transactionalGraph).rollback();
    }
    
    @Test
    public void testTransactionNotComitted() {
        
        FramedGraph fg = new DelegatingFramedGraph(transactionalGraph);
        try(Transaction t = fg.tx()) {
        	
        }

        Mockito.verify(transactionalGraph).rollback();
    }
    
    @Test
    public void testTransactionException() {
        
        FramedGraph fg = new DelegatingFramedGraph(transactionalGraph);
        
        try(Transaction t = fg.tx()) {
        	throw new Exception();
        }
        catch(Exception e) {
        }

        Mockito.verify(transactionalGraph).rollback();
    }
    

    @Test
    public void testUntypedFrames() {
        Graph g = new TinkerGraph();
        FramedGraph fg = new DelegatingFramedGraph(g);
        TVertex p1 = fg.addFramedVertex();
        p1.setProperty("name", "Bryn");

        TVertex p2 = fg.addFramedVertex();
        p2.setProperty("name", "Julia");
        TEdge knows = p1.addFramedEdge("knows", p2);
        knows.setProperty("years", 15);

        VertexFrame bryn = fg.v().has("name", "Bryn").next();
        
        
        Assert.assertEquals("Bryn", bryn.getProperty("name"));
        Assert.assertEquals(15, bryn.outE("knows").toList().get(0).getProperty("years"));
   
        Collection<? extends Integer> knowsCollection = fg.v().has("name", "Julia").bothE().property("years", Integer.class).aggregate().cap();
        Assert.assertEquals(1, knowsCollection.size());
    }

    @Test
    public void testUntypedFramesExplicit() {
        Graph g = new TinkerGraph();
        FramedGraph fg = new DelegatingFramedGraph(g);
        TVertex p1 = fg.addFramedVertexExplicit();
        p1.setProperty("name", "Bryn");

        TVertex p2 = fg.addFramedVertexExplicit();
        p2.setProperty("name", "Julia");
        TEdge knows = p1.addFramedEdgeExplicit("knows", p2);
        knows.setProperty("years", 15);

        VertexFrame bryn = fg.v().has("name", "Bryn").next();


        Assert.assertEquals("Bryn", bryn.getProperty("name"));
        Assert.assertEquals(15, bryn.outE("knows").toList().get(0).getProperty("years"));

        Collection<? extends Integer> knowsCollection = fg.v().has("name", "Julia").bothE().property("years", Integer.class).aggregate().cap();
        Assert.assertEquals(1, knowsCollection.size());
    }

    @Test
    public void testKeyValueTraversal() {
        Graph g = TinkerGraphFactory.createTinkerGraph();
        FramedGraph fg = new DelegatingFramedGraph(g);
        final Set<Object> expectedSet = new HashSet<>();

        TVertex p1 = fg.addFramedVertexExplicit();
        p1.setProperty("name", "Bryn");
        p1.setProperty("findme", "yes");
        expectedSet.add(p1.getId());

        TVertex p2 = fg.addFramedVertexExplicit();
        p2.setProperty("name", "Julia");
        p2.setProperty("findme", "yes");
        expectedSet.add(p2.getId());

        TEdge knows = p1.addFramedEdgeExplicit("knows", p2);
        knows.setProperty("years", 15);

        Assert.assertEquals(expectedSet, fg.v("findme", "yes").id().toSet());
    }
}

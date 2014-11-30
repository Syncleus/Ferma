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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Sets;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
/**
 * @author Bryn Cooke (http://jglue.org)
 */

public class AbstractElementFrameTest {

	
	private FramedGraph fg;
	private Person p1;
	private Person p2;
	private Knows e1;
    
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		Graph g = new TinkerGraph();
        fg = new FramedGraph(g);
        p1 = fg.addFramedVertex(Person.class);
        p2 = fg.addFramedVertex(Person.class);
        p1.setName("Bryn");
        p2.setName("Julia");
        e1 = p1.addKnows(p2);
        e1.setYears(15);

	}
	
    @Test
    public void testGetId() {
    	Assert.assertEquals("0", p1.getId());
        
    }
    
    @Test
    public void testGetPropertyKeys() {
    	Assert.assertEquals(Sets.newHashSet("name"), p1.getPropertyKeys());
    }
    
    @Test
    public void testGetProperty() {
    	Assert.assertEquals("Bryn", p1.getProperty("name"));
    }
    
    @Test
    public void testSetProperty() {
    	p1.setProperty("name", "Bryn Cooke");
    	Assert.assertEquals("Bryn Cooke", p1.getProperty("name"));
    }
    
    @Test
    public void testSetPropertyNull() {
    	p1.setProperty("name", null);
    	Assert.assertNull(p1.getProperty("name"));
    }
    
    
    @Test
    public void testV() {
    	Assert.assertEquals(2, p1.v().count());
    }
    
    @Test
    public void testE() {
    	Assert.assertEquals(1, p1.e().count());
    }
    
    @Test
    public void testv() {
    	Assert.assertEquals(p1, p1.v(p1.getId()).next(Person.class));
    }
    
    @Test
    public void teste() {
    	Assert.assertEquals(e1, p1.e(e1.getId()).next(Knows.class));
    }

    @Test
    public void testvExplicit() {
        Assert.assertEquals(p1, p1.v(p1.getId()).nextExplicit(Person.class));
    }

    @Test
    public void testeExplicit() {
        Assert.assertEquals(e1, p1.e(e1.getId()).nextExplicit(Knows.class));
    }
    
    @Test
    public void testRemove() {
    	p1.remove();
    	Assert.assertEquals(1, p1.v().count());
    }
    
    @Test
    public void testReframe() {
    	TVertex v1 = p1.reframe(TVertex.class);
    	Assert.assertEquals(p1.getId(), v1.getId());
    }

    @Test
    public void testReframeExplicit() {
        TVertex v1 = p1.reframeExplicit(TVertex.class);
        Assert.assertEquals(p1.getId(), v1.getId());
    }

    @Test
    public void testvNull() {
        Assert.assertNull(p1.v("noId").next());
    }
    
}

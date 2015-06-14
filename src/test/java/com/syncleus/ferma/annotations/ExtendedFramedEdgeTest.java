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
package com.syncleus.ferma.annotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.syncleus.ferma.DelegatingFramedGraph;
import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.Knows;
import com.syncleus.ferma.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;

public class ExtendedFramedEdgeTest {

	private static final Set<Class<?>> TEST_TYPES = new HashSet<Class<?>>(
    		Arrays.asList(new Class<?>[]{
    				Person.class,
    				Friend.class,
    				Knows.class
    				}
    		));
	private Friend p1;
    private Friend p2;
    private Knows e1;
    private Knows e2;

    @Before
    public void init() {
    	
        MockitoAnnotations.initMocks(this);
        final Graph g =TinkerGraphFactory.createTinkerGraph();
        final FramedGraph fg = new DelegatingFramedGraph(g, TEST_TYPES);
        p1 = fg.addFramedVertex(Friend.class);
        p2 = fg.addFramedVertex(Friend.class);
        p1.setName("Bryn");
        p2.setName("Julia");
        e1 = p1.addKnows(p2);
        e1.setYears(15);
        e2 = p1.addKnownBy(p2);
        e2.setYears(15);
        
    }

    @Test
    public void testLabel() {
        Assert.assertEquals(Knows.class.getCanonicalName(), e1.getLabel());
    }

    @Test
    public void testInV() {
        Assert.assertEquals(p2, e1.inV().next(Friend.class));
    }

    @Test
    public void testOutV() {
        Assert.assertEquals(p1, e1.outV().next(Friend.class));
    }

    @Test
    public void testBothV() {
        Assert.assertEquals(p1, e1.bothV().next(Friend.class));
    }

    @Test
    public void testInVExplicit() {
        Assert.assertEquals(p2, e1.inV().nextExplicit(Friend.class));
    }

    @Test
    public void testOutVExplicit() {
        Assert.assertEquals(p1, e1.outV().nextExplicit(Friend.class));
    }

    @Test
    public void testBothVExplicit() {
        Assert.assertEquals(p1, e1.bothV().nextExplicit(Friend.class));
    }
    
}

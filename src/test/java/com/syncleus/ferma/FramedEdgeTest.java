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
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;

public class FramedEdgeTest {

    private Person p1;
    private Person p2;
    private Knows e1;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        final Graph g = new TinkerGraph();
        final FramedGraph fg = new DelegatingFramedGraph(g);
        p1 = fg.addFramedVertex(Person.class);
        p2 = fg.addFramedVertex(Person.class);
        p1.setName("Bryn");
        p2.setName("Julia");
        e1 = p1.addKnows(p2);
        e1.setYears(15);

    }

    @Test
    public void testLabel() {
        Assert.assertEquals("knows", e1.getLabel());
    }

    @Test
    public void testInV() {
        Assert.assertEquals(p2, e1.inV().next(Person.class));
    }

    @Test
    public void testOutV() {
        Assert.assertEquals(p1, e1.outV().next(Person.class));
    }

    @Test
    public void testBothV() {
        Assert.assertEquals(p1, e1.bothV().next(Person.class));
    }

    @Test
    public void testInVExplicit() {
        Assert.assertEquals(p2, e1.inV().nextExplicit(Person.class));
    }

    @Test
    public void testOutVExplicit() {
        Assert.assertEquals(p1, e1.outV().nextExplicit(Person.class));
    }

    @Test
    public void testBothVExplicit() {
        Assert.assertEquals(p1, e1.bothV().nextExplicit(Person.class));
    }
}

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

import com.syncleus.ferma.DelegatingFramedGraph;
import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.VertexFrame;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class InVertexMethodHandlerTest {
    private static final Set<Class<?>> TEST_TYPES = new HashSet<>(Arrays.asList(new Class<?>[]{God.class, FatherEdge.class, GodExtended.class, FatherEdgeExtended.class}));

    @Test
    public void testGetSonEdges() {
        final TinkerGraph godGraph = new TinkerGraph();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        final List<? extends God> gods = framedGraph.v().has("name", "jupiter").toList(God.class);

        God father = gods.iterator().next();
        Assert.assertTrue(father != null);
        VertexFrame fatherVertex = (VertexFrame) father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final Iterable<? extends FatherEdge> childrenEdges = father.getSonEdges(FatherEdge.class);
        final Iterator<? extends FatherEdge> childEdgeIterator = childrenEdges.iterator();
        Assert.assertTrue(childEdgeIterator.hasNext());
        final FatherEdge childEdge = childEdgeIterator.next();

        father = childEdge.getFather();
        Assert.assertTrue(father != null);
        fatherVertex = (VertexFrame) father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");
    }
}

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

import com.syncleus.ferma.annotations.*;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class PerformanceTest {
    private static final Set<Class<?>> TEST_TYPES = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{God.class, FatherEdge.class, GodExtended.class, GodAlternative.class}));

    @Test
    public void testFramesSimpleGetGodBenchmark() {
        final TinkerGraph godGraph = new TinkerGraph();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new FramedGraph(godGraph, TEST_TYPES);

        Date start = new Date();
        for(int i = 0; i < 10000000; i++) {
            final Iterable<God> gods = framedGraph.getFramedVertices("name", "saturn", God.class);
            Iterator<God> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());
        }
        final long framesElapsed = new Date().getTime() - start.getTime();

        start = new Date();
        for(int i = 0; i < 10000000; i++) {
            Iterable<Vertex> gods = godGraph.getVertices("name", "saturn");
            Iterator<Vertex> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());
        }
        final long noFramesElapsed = new Date().getTime() - start.getTime();
        System.out.println("simple percentage: " + ((double)noFramesElapsed) / ((double)framesElapsed) * 100.0 + "%");
    }

    @Test
    public void testFramesPartialTraverseGetGodBenchmark() {
        final TinkerGraph godGraph = new TinkerGraph();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new FramedGraph(godGraph, TEST_TYPES);

        Date start = new Date();
        for(int i = 0; i < 5000000; i++) {
            final Iterable<God> gods = framedGraph.getFramedVertices("name", "saturn", God.class);
            Iterator<God> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            final God father = godsIterator.next();
        }
        final long framesElapsed = new Date().getTime() - start.getTime();

        start = new Date();
        for(int i = 0; i < 5000000; i++) {
            Iterable<Vertex> gods = godGraph.getVertices("name", "saturn");
            Iterator<Vertex> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            final Vertex father = godsIterator.next();
        }
        final long noFramesElapsed = new Date().getTime() - start.getTime();
        System.out.println("partial percentage: " + ((double)noFramesElapsed) / ((double)framesElapsed) * 100.0 + "%");
    }

    @Test
    public void testFramesFullTraverseGetGodBenchmark() {
        final TinkerGraph godGraph = new TinkerGraph();
        GodGraphLoader.load(godGraph);

        final FramedGraph framedGraph = new FramedGraph(godGraph, TEST_TYPES);

        Date start = new Date();
        for(int i = 0; i < 500000; i++) {
            final Iterable<God> gods = framedGraph.getFramedVertices("name", "saturn", God.class);
            Iterator<God> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            final God father = godsIterator.next();
            Iterable<? extends God> children = father.getSons();
            for(final God child : children)
                Assert.assertTrue(child.getParents().iterator().next().getName().equals("saturn"));
        }
        final long framesElapsed = new Date().getTime() - start.getTime();

        start = new Date();
        for(int i = 0; i < 500000; i++) {
            Iterable<Vertex> gods = godGraph.getVertices("name", "saturn");
            Iterator<Vertex> godsIterator = gods.iterator();
            Assert.assertTrue(godsIterator.hasNext());

            final Vertex father = godsIterator.next();
            Iterable<? extends Vertex> children = father.getVertices(Direction.IN, "father");
            for(final Vertex child : children)
                Assert.assertTrue(child.getVertices(Direction.OUT, "father").iterator().next().getProperty("name").equals("saturn"));
        }
        final long noFramesElapsed = new Date().getTime() - start.getTime();
        System.out.println("full traversal percentage: " + ((double)noFramesElapsed) / ((double)framesElapsed) * 100.0 + "%");
    }
}

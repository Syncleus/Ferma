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

import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class SimpleTypeResolverTest {

    private static final Set<Class<?>> TEST_TYPES = new HashSet<>(Arrays.asList(new Class<?>[]{Person.class, Programmer.class}));

    @Test
    public void testChangeType() {
        final TinkerGraph godGraph = new TinkerGraph();
        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        //add a single node to the graph, a programmer.
        framedGraph.addFramedVertex(Programmer.DEFAULT_INITIALIZER);

        //make sure the newly added node is actually a programmer
        final Person programmer = framedGraph.v().next(Person.class);
        Assert.assertTrue(programmer instanceof Programmer);

        //change the type resolution to person
        programmer.setTypeResolution(Person.class);

        //make sure the newly added node is actually a programmer
        final Person person = framedGraph.v().next(Person.class);
        Assert.assertFalse(person instanceof Programmer);
    }
}

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

import com.syncleus.ferma.typeresolvers.PolymorphicTypeResolver;
import com.syncleus.ferma.annotations.AnnotationFrameFactory;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import com.tinkerpop.blueprints.Graph;

public class SimpleTypeResolverTest {

    private static final Set<Class<?>> TEST_TYPES = new HashSet<>(Arrays.asList(new Class<?>[]{Person.class, Programmer.class}));
    private static final String CUSTOM_TYPE_KEY = "some_custom_type_key";

    @Test
    public void testChangeType() {
        final TinkerGraph godGraph = new TinkerGraph();
        final FramedGraph framedGraph = new DelegatingFramedGraph(godGraph, TEST_TYPES);

        //add a single node to the graph, a programmer.
        framedGraph.addFramedVertex(Programmer.class);

        //make sure the newly added node is actually a programmer
        final Person programmer = framedGraph.v().next(Person.class);
        Assert.assertTrue(programmer instanceof Programmer);

        //change the type resolution to person
        programmer.setTypeResolution(Person.class);

        //make sure the newly added node is actually a programmer
        final Person person = framedGraph.v().next(Person.class);
        Assert.assertFalse(person instanceof Programmer);
    }
    
    @Test
    public void testCustomTypeKey() {
        final Graph g = new TinkerGraph();
        final ReflectionCache cache = new ReflectionCache(TEST_TYPES);
        final FramedGraph fg = new DelegatingFramedGraph(g, new AnnotationFrameFactory(cache), new PolymorphicTypeResolver(cache, CUSTOM_TYPE_KEY));

        final Person p1 = fg.addFramedVertex(Programmer.DEFAULT_INITIALIZER);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        p2.setName("Julia");

        final Person bryn = fg.v().has("name", "Bryn").next(Person.class);
        final Person julia = fg.v().has("name", "Julia").next(Person.class);

        Assert.assertEquals(Programmer.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());
        
        Assert.assertNotNull(bryn.getElement().getProperty(CUSTOM_TYPE_KEY));
        Assert.assertNull(bryn.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
        Assert.assertNotNull(julia.getElement().getProperty(CUSTOM_TYPE_KEY));
        Assert.assertNull(julia.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
    }
    
    @Test
    public void testCustomTypeKeyByClass() {
        final Graph g = new TinkerGraph();
        final ReflectionCache cache = new ReflectionCache(TEST_TYPES);
        final FramedGraph fg = new DelegatingFramedGraph(g, new AnnotationFrameFactory(cache), new PolymorphicTypeResolver(cache, CUSTOM_TYPE_KEY));

        final Person p1 = fg.addFramedVertex(Programmer.class);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertex(Person.class);
        p2.setName("Julia");

        final Person bryn = fg.v().has("name", "Bryn").next(Person.class);
        final Person julia = fg.v().has("name", "Julia").next(Person.class);

        Assert.assertEquals(Programmer.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());
        
        Assert.assertNotNull(bryn.getElement().getProperty(CUSTOM_TYPE_KEY));
        Assert.assertNull(bryn.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
        Assert.assertNotNull(julia.getElement().getProperty(CUSTOM_TYPE_KEY));
        Assert.assertNull(julia.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
    }
    
    @Test
    public void testCustomTypeKeyExplicit() {
        final Graph g = new TinkerGraph();
        final ReflectionCache cache = new ReflectionCache(TEST_TYPES);
        final FramedGraph fg = new DelegatingFramedGraph(g, new AnnotationFrameFactory(cache), new PolymorphicTypeResolver(cache, CUSTOM_TYPE_KEY));

        final Person p1 = fg.addFramedVertexExplicit(Programmer.DEFAULT_INITIALIZER);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertexExplicit(Person.DEFAULT_INITIALIZER);
        p2.setName("Julia");

        final Person bryn = fg.v().has("name", "Bryn").next(Person.class);
        final Person julia = fg.v().has("name", "Julia").next(Person.class);

        Assert.assertEquals(Person.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());
        
        Assert.assertNull(bryn.getElement().getProperty(CUSTOM_TYPE_KEY));
        Assert.assertNull(bryn.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
        Assert.assertNull(julia.getElement().getProperty(CUSTOM_TYPE_KEY));
        Assert.assertNull(julia.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
    }

    @Test
    public void testCustomTypeKeyExplicitByClass() {
        final Graph g = new TinkerGraph();
        final ReflectionCache cache = new ReflectionCache(TEST_TYPES);
        final FramedGraph fg = new DelegatingFramedGraph(g, new AnnotationFrameFactory(cache), new PolymorphicTypeResolver(cache, CUSTOM_TYPE_KEY));

        final Person p1 = fg.addFramedVertexExplicit(Programmer.class);
        p1.setName("Bryn");

        final Person p2 = fg.addFramedVertexExplicit(Person.class);
        p2.setName("Julia");

        final Person bryn = fg.v().has("name", "Bryn").next(Person.class);
        final Person julia = fg.v().has("name", "Julia").next(Person.class);

        Assert.assertEquals(Person.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());
        
        Assert.assertNull(bryn.getElement().getProperty(CUSTOM_TYPE_KEY));
        Assert.assertNull(bryn.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
        Assert.assertNull(julia.getElement().getProperty(CUSTOM_TYPE_KEY));
        Assert.assertNull(julia.getElement().getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY));
    }
}

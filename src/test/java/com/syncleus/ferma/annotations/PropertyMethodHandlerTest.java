package com.syncleus.ferma.annotations;

import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.FramedVertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class PropertyMethodHandlerTest {
    private static final Set<Class<?>> TEST_TYPES = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{God.class, FatherEdge.class, GodExtended.class, GodAlternative.class}));

    @Test
    public void testGetName() {
        final TinkerGraph godGraph = new TinkerGraph();
        GodGraphLoader.load(godGraph);

        final AnnotationTypeResolver resolver = new AnnotationTypeResolver();
        final AnnotationFrameFactory factory = new AnnotationFrameFactory(TEST_TYPES);
        final FramedGraph framedGraph = new FramedGraph(godGraph, factory, resolver);

        final List<God> gods = framedGraph.V().has("name", "jupiter").toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father instanceof FramedVertex);
        final FramedVertex fatherVertex = (FramedVertex) father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");
        Assert.assertEquals("jupiter", father.getName());
    }

    @Test
    public void testSetName() {
        final TinkerGraph godGraph = new TinkerGraph();
        GodGraphLoader.load(godGraph);

        final AnnotationTypeResolver resolver = new AnnotationTypeResolver();
        final AnnotationFrameFactory factory = new AnnotationFrameFactory(TEST_TYPES);
        final FramedGraph framedGraph = new FramedGraph(godGraph, factory, resolver);

        List<God> gods = framedGraph.V().has("name", "jupiter").toList(God.class);

        God father = gods.iterator().next();
        Assert.assertTrue(father instanceof FramedVertex);
        FramedVertex fatherVertex = (FramedVertex) father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");
        father.setName("joopiter");

        gods = framedGraph.V().has("name", "joopiter").toList(God.class);

        father = gods.iterator().next();
        Assert.assertTrue(father instanceof FramedVertex);
        fatherVertex = (FramedVertex) father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "joopiter");
    }
}

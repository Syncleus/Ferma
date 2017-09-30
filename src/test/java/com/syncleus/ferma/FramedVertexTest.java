/**
 * Copyright 2004 - 2017 Syncleus, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.syncleus.ferma;

import java.util.HashMap;
import java.util.Map;

import java.util.function.Function;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import com.google.common.collect.Lists;

import javax.annotation.Nullable;

public class FramedVertexTest {
    private static final Function<GraphTraversal<Vertex, Vertex>, GraphTraversal<?, ?>> OUT_TRAVERSAL = input -> input.out();

    private FramedGraph fg;
    private Person p1;
    private Person p2;
    private Knows e1;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        final Graph g = TinkerGraph.open();
        fg = new DelegatingFramedGraph(g);
        p1 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        p2 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        p1.setName("Bryn");
        p2.setName("Julia");
        e1 = p1.addKnows(p2);
        e1.setYears(15);

    }

    @Test
    public void testOut() {
        Assert.assertEquals(p2, p1.traverse(OUT_TRAVERSAL).next(Person.class));
    }

    @Test
    public void testOutExplicit() {
        Assert.assertEquals(p2, p1.traverse(OUT_TRAVERSAL).nextExplicit(Person.class));
    }


    @Test
    public void testLinkOutSingleLabel() {
        final Person p3 = fg.addFramedVertex(Person.class);
        p3.setName("Tjad");

        final String label = "knows";

        Assert.assertEquals((Long) 0L, p3.getRawTraversal().outE(label).count().next());
        Assert.assertEquals((Long) 3L, fg.getRawTraversal().V().count().next());

        p3.linkOut(p1, label);
        //Make sure a new edge was created
        Assert.assertEquals("knows edge was not created", (Long) 1L, p3.getRawTraversal().outE(label).count().next());

        //Make sure the edge was created to the correct vertex
        Assert.assertEquals("Incorrect vertex associated", (Long) 1L, p3.getRawTraversal().out(label).has("name", "Bryn").count().next());
    }

    @Test
    public void testLinkOutMultiLabel() {

        final String[] newLabels = new String[]{"knows", "friends_with"};

        final Person p3 = fg.addFramedVertex(Person.class);
        p3.setName("Tjad");

        final Map<String, Number> expectedCounts = new HashMap<>();

        for (final String label : newLabels) {
            expectedCounts.put("expected_e_" + label, p3.getRawTraversal().outE(label).count().next() + 1);
            expectedCounts.put("expected_v_" + label, p3.getRawTraversal().out(label).has("name", "Bryn").count().next() + 1);
        }

        p3.linkOut(p1, newLabels);


        for (final String label : newLabels) {
            //Make sure a new edge was created
            Assert.assertEquals("knows edge was not created", expectedCounts.get("expected_e_" + label), p3.getRawTraversal().outE(label).count().next());

            //Make sure the edge was created to the correct vertex
            Assert.assertEquals("Incorrect vertex associated", expectedCounts.get("expected_v_" + label), p3.getRawTraversal().out(label).has("name", "Bryn").count().next());
        }
    }

    @Test
    public void testLinkInSingleLabel() {
        final Person p3 = fg.addFramedVertex(Person.class);
        p3.setName("Tjad");

        final String label = "knows";
        final Long expectedEdgeCount = p3.getRawTraversal().inE(label).count().next() + 1;
        final Long expectedVerticesCount = p3.getRawTraversal().in(label).has("name", "Bryn").count().next() + 1;

        p3.linkIn(p1, label);
        //Make sure a new edge was created
        Assert.assertEquals("knows edge was not created", expectedEdgeCount, p3.getRawTraversal().inE(label).count().next());

        //Make sure the edge was created to the correct vertex
        Assert.assertEquals("Incorrect vertex associated", expectedVerticesCount, p3.getRawTraversal().in(label).has("name", "Bryn").count().next());
    }

    @Test
    public void testLinkInMultiLabel() {

        final String[] newLabels = new String[]{"knows", "friends_with"};

        final Person p3 = fg.addFramedVertex(Person.class);
        p3.setName("Tjad");

        final Map<String, Number> expectedCounts = new HashMap<>();

        for (final String label : newLabels) {
            expectedCounts.put("expected_e_" + label, p3.getRawTraversal().inE(label).count().next() + 1);
            expectedCounts.put("expected_v_" + label, p3.getRawTraversal().in(label).has("name", "Bryn").count().next() + 1);
        }

        p3.linkIn(p1, newLabels);


        for (final String label : newLabels) {
            //Make sure a new edge was created
            Assert.assertEquals("knows edge was not created", expectedCounts.get("expected_e_" + label), p3.getRawTraversal().inE(label).count().next());

            //Make sure the edge was created to the correct vertex
            Assert.assertEquals("Incorrect vertex associated", expectedCounts.get("expected_v_" + label), p3.getRawTraversal().in(label).has("name", "Bryn").count().next());
        }

    }

    @Test
    public void testLinkBothSingleLabel() {
        final Person p3 = fg.addFramedVertex(Person.class);
        p3.setName("Tjad");

        final String label = "knows";
        final Long expectedEdgeCount = p3.getRawTraversal().bothE(label).count().next() + 2;
        final Long tailCount = p3.getRawTraversal().in(label).has("name", "Bryn").count().next() + 1;
        final Long headCount = p3.getRawTraversal().out(label).has("name", "Bryn").count().next() + 1;

        p3.linkBoth(p1, label);
        //Make sure a new edge was created
        Assert.assertEquals("knows edge was not created", expectedEdgeCount, p3.getRawTraversal().bothE(label).count().next());

        //Make sure the edge was created to the correct vertex
        Assert.assertEquals("Incorrect in vertex associated", tailCount, p3.getRawTraversal().in(label).has("name", "Bryn").count().next());
        Assert.assertEquals("Incorrect out vertex associated", headCount, p3.getRawTraversal().out(label).has("name", "Bryn").count().next());
    }

    @Test
    public void testLinkBothMultiLabel() {

        final String[] newLabels = new String[]{"knows", "friends_with"};

        final Person p3 = fg.addFramedVertex(Person.class);
        p3.setName("Tjad");

        final Map<String, Number> expectedCounts = new HashMap<>();

        for (final String label : newLabels) {
            expectedCounts.put("expected_e_" + label, p3.getRawTraversal().bothE(label).count().next() + 2);
            expectedCounts.put("expected_tail_" + label, p3.getRawTraversal().in(label).has("name", "Bryn").count().next() + 1);
            expectedCounts.put("expected_head_" + label, p3.getRawTraversal().out(label).has("name", "Bryn").count().next() + 1);
        }

        p3.linkBoth(p1, newLabels);


        for (final String label : newLabels) {
            //Make sure a new edge was created
            Assert.assertEquals("knows edge was not created", expectedCounts.get("expected_e_" + label), p3.getRawTraversal().bothE(label).count().next());

            //Make sure the edge was created to the correct vertex
            Assert.assertEquals("Incorrect vertex associated", expectedCounts.get("expected_tail_" + label), p3.getRawTraversal().in(label).has("name", "Bryn").count().next());
            Assert.assertEquals("Incorrect vertex associated", expectedCounts.get("expected_head_" + label), p3.getRawTraversal().out(label).has("name", "Bryn").count().next());
        }

    }

    @Test
    public void testUnlinkInWithNull() {
        final String label = "knows";
        final Person p3 = fg.addFramedVertex(Person.class);
        final Person p4 = fg.addFramedVertex(Person.class);
        final Person p5 = fg.addFramedVertex(Person.class);

        p4.addFramedEdge(label, p3, Knows.class);
        p5.addFramedEdge(label, p3, Knows.class);

        Assert.assertTrue("Multiple edges(in) of type " + label + " must exist for vertice", p3.getRawTraversal().in(label).count().next() > 1);

        p3.unlinkIn(null, label);

        //Make all edges were removed
        Assert.assertEquals("All " + label + " edges(in) should have been removed", (Long) 0L, p3.getRawTraversal().in(label).count().next());
    }

    @Test
    public void testUnlinkOutWithNull() {
        final String label = "knows";
        final Person p3 = fg.addFramedVertex(Person.class);
        final Person p4 = fg.addFramedVertex(Person.class);
        final Person p5 = fg.addFramedVertex(Person.class);

        p3.addFramedEdge(label, p4, Knows.class);
        p3.addFramedEdge(label, p5, Knows.class);

        Assert.assertTrue("Multiple edges(out) of type " + label + " must exist for vertice", p3.getRawTraversal().out(label).count().next() > 1);

        p3.unlinkOut(null, label);

        //Make all edges were removed
        Assert.assertEquals("All " + label + " edges(out) should have been removed", (Long) 0L, p3.getRawTraversal().out(label).count().next());

    }

    @Test
    public void testUnlinkBothWithNull() {
        final String label = "knows";
        final Person p3 = fg.addFramedVertex(Person.class);
        final Person p4 = fg.addFramedVertex(Person.class);
        final Person p5 = fg.addFramedVertex(Person.class);

        p3.addFramedEdge(label, p4, Knows.class);
        p3.addFramedEdge(label, p5, Knows.class);

        Assert.assertTrue("Multiple edges(out) of type " + label + " must exist for vertice", p3.getRawTraversal().out(label).count().next() > 1);

        p4.addFramedEdge(label, p3, Knows.class);
        p5.addFramedEdge(label, p3, Knows.class);

        Assert.assertTrue("Multiple edges(in) of type " + label + " must exist for vertice", p3.getRawTraversal().in(label).count().next() > 1);

        p3.unlinkBoth(null, label);

        //Make all edges were removed
        Assert.assertEquals("All " + label + " edges should have been removed", (Long) 0L, p3.getRawTraversal().both(label).count().next());

    }

    @Test
    public void testUnlinkIn() {
        final String label = "knows";
        final Person p3 = fg.addFramedVertex(Person.class);
        final Person p4 = fg.addFramedVertex(Person.class);
        p4.setProperty("name", "noob");
        final Person p5 = fg.addFramedVertex(Person.class);

        p4.addFramedEdge(label, p3, Knows.class);
        p5.addFramedEdge(label, p3, Knows.class);
        Long allInEdgesCount = p3.getRawTraversal().in(label).count().next();
        Long targetVertex_InEdgeCount = p3.getRawTraversal().in(label).has("name", "noob").count().next();
        Long allEdgeCount = fg.getRawTraversal().E().count().next();

        Assert.assertEquals((Long) 3L, allEdgeCount);
        Assert.assertTrue("target vertex requires an in edge for " + label, targetVertex_InEdgeCount.equals(1L));
        Assert.assertTrue("Multiple edges(in) of type " + label + " must exist for vertice", allInEdgesCount.equals(2L));

        p3.unlinkIn(p4, label);

        allInEdgesCount = p3.getRawTraversal().in(label).count().next();
        targetVertex_InEdgeCount = p3.getRawTraversal().in(label).has("name", "noob").count().next();
        allEdgeCount = fg.getRawTraversal().E().count().next();

        Assert.assertEquals((Long) 2L, allEdgeCount);
        Assert.assertEquals(targetVertex_InEdgeCount, (Long) 0L);
        Assert.assertTrue(allInEdgesCount.equals(1L));

        //Make all edges were removed
        Assert.assertEquals("All " + label + " edges(in) should have been removed from given vertex", (Long) 0L, targetVertex_InEdgeCount);
        Assert.assertTrue("All " + label + " edges(in) should have remained for other vertices", p3.getRawTraversal().inE(label).count().next() > 0);
    }

    @Test
    public void testUnlinkOut() {
        final String label = "knows";
        final Person p3 = fg.addFramedVertex(Person.class);
        final Person p4 = fg.addFramedVertex(Person.class);
        p4.setProperty("name", "noob");
        final Person p5 = fg.addFramedVertex(Person.class);

        p3.addFramedEdge(label, p4, Knows.class);
        p3.addFramedEdge(label, p5, Knows.class);
        Long allOutEdgesCount = p3.getRawTraversal().out(label).count().next();
        Long targetVertex_OutEdgeCount = p3.getRawTraversal().out(label).has("name", "noob").count().next();
        Long allEdgeCount = fg.getRawTraversal().E().count().next();

        Assert.assertEquals((Long) 3L, allEdgeCount);
        Assert.assertTrue("target vertex requires an in edge for " + label, targetVertex_OutEdgeCount > 0);
        Assert.assertTrue("Multiple edges(out) of type " + label + " must exist for vertice", allOutEdgesCount - targetVertex_OutEdgeCount > 0);

        p3.unlinkOut(p4, label);

        allOutEdgesCount = p3.getRawTraversal().out(label).count().next();
        targetVertex_OutEdgeCount = p3.getRawTraversal().out(label).has("name", "noob").count().next();
        allEdgeCount = fg.getRawTraversal().E().count().next();

        Assert.assertEquals((Long) 2L, allEdgeCount);
        Assert.assertEquals(targetVertex_OutEdgeCount, (Long) 0L);
        Assert.assertTrue(allOutEdgesCount.equals(1L));

        //Make all edges were removed
        Assert.assertEquals("All " + label + " edges(in) should have been removed from given vertex", (Long) 0L, targetVertex_OutEdgeCount);
        Assert.assertTrue("All " + label + " edges(in) should have remained for other vertices", p3.getRawTraversal().outE(label).count().next() > 0);
    }

    @Test
    public void testUnlinkBoth() {
        final String label = "knows";
        final Person p3 = fg.addFramedVertex(Person.class);
        final Person p4 = fg.addFramedVertex(Person.class);
        p4.setProperty("name", "noob");
        final Person p5 = fg.addFramedVertex(Person.class);

        p4.addFramedEdge(label, p3, Knows.class);
        p5.addFramedEdge(label, p3, Knows.class);
        p3.addFramedEdge(label, p4, Knows.class);
        p3.addFramedEdge(label, p5, Knows.class);

        Long allEdgeCount = fg.getRawTraversal().E().count().next();
        Long allBothEdgesCount = p3.getRawTraversal().both(label).count().next();
        Long targetVertex_EdgeCount = p3.getRawTraversal().both(label).has("name", "noob").count().next();

        Assert.assertEquals(allEdgeCount, (Long) 5L);
        Assert.assertTrue("target vertex requires an in/out edge for " + label, targetVertex_EdgeCount > 0);
        Assert.assertTrue("Multiple edges of type " + label + " must exist for vertice", allBothEdgesCount - targetVertex_EdgeCount > 0);

        p3.unlinkBoth(p4, label);

        allEdgeCount = fg.getRawTraversal().E().count().next();
        allBothEdgesCount = p3.getRawTraversal().both(label).count().next();
        targetVertex_EdgeCount = p3.getRawTraversal().both(label).has("name", "noob").count().next();

        Assert.assertEquals((Long) 3L, allEdgeCount);
        Assert.assertEquals(targetVertex_EdgeCount, (Long) 0L);
        Assert.assertEquals(allBothEdgesCount, (Long) 2L);

        //Make all edges were removed
        Assert.assertEquals("All " + label + " edges(in/out) should have been removed from given vertex", (Long) 0L, targetVertex_EdgeCount);
        Assert.assertTrue("All " + label + " edges(in/out) should have remained for other vertices", p3.getRawTraversal().bothE(label).count().next() > 0);
    }

    @Test
    public void testSetLinkIn() {
        final String label = "knows";
        final Person p3 = fg.addFramedVertex(Person.class);
        final Person p4 = fg.addFramedVertex(Person.class);
        p4.setProperty("name", "noob");
        final Person p5 = fg.addFramedVertex(Person.class);
        p5.setProperty("name", "leet");

        p4.addFramedEdge(label, p3, Knows.class);

        Assert.assertTrue("An edge(in) of type " + label + " must exist between vertices", p3.getRawTraversal().in(label).has("name", "noob").count().next() > 0);

        p3.setLinkIn(p5, label);

        //Make sure old edge was deleted
        Assert.assertEquals("old " + label + " edge was not removed", (Long) 0L, p3.getRawTraversal().in(label).has("name", "noob").count().next());


        //Make sure a new edge was created (and to correct vertex)
        Assert.assertEquals(label + " edge was not created", (Long) 1L, p3.getRawTraversal().in(label).has("name", "leet").count().next());
    }

    @Test
    public void testSetLinkInNull() {
        final String label = "knows";
        final Person p3 = fg.addFramedVertex(Person.class);
        final Person p4 = fg.addFramedVertex(Person.class);
        p4.setProperty("name", "noob");
        p3.setLinkIn(p4, label);
        Assert.assertTrue("An out edge of type " + label + " must exist between vertices", p3.getRawTraversal().in(label).has("name", "noob").count().next() > 0);

        // tests if setLinkOut accepts null values, and only unlinks the edges
        p3.setLinkIn((VertexFrame) null, label);
        Assert.assertEquals(label + " edge was not unlinked", (Long) 0L, p3.getRawTraversal().in(label).count().next());
    }

    @Test
    public void testSetLinkOut() {
        final String label = "knows";
        final Person p3 = fg.addFramedVertex(Person.class);
        final Person p4 = fg.addFramedVertex(Person.class);
        p4.setProperty("name", "noob");
        final Person p5 = fg.addFramedVertex(Person.class);
        p5.setProperty("name", "leet");

        p3.addFramedEdge(label, p4, Knows.class);

        Assert.assertTrue("An out edge of type " + label + " must exist between vertices", p3.getRawTraversal().out(label).has("name", "noob").count().next() > 0);

        p3.setLinkOut(p5, label);

        //Make sure old edge was deleted
        Assert.assertEquals("old " + label + " edge was not removed", (Long) 0L, p3.getRawTraversal().out(label).has("name", "noob").count().next());


        //Make sure a new edge was created (and to correct vertex)
        Assert.assertEquals(label + " edge was not created", (Long) 1L, p3.getRawTraversal().out(label).has("name", "leet").count().next());
    }

    @Test
    public void testSetLinkOutNull() {
        final String label = "knows";
        final Person p3 = fg.addFramedVertex(Person.class);
        final Person p4 = fg.addFramedVertex(Person.class);
        p4.setProperty("name", "noob");
        p3.setLinkOut(p4, label);
        Assert.assertTrue("An out edge of type " + label + " must exist between vertices", p3.getRawTraversal().out(label).has("name", "noob").count().next() > 0);

        // tests if setLinkOut accepts null values, and only unlinks the edges
        p3.setLinkOut((VertexFrame) null, label);
        Assert.assertEquals(label + " edge was not unlinked", (Long) 0L, p3.getRawTraversal().out(label).count().next());
    }

    @Test
    public void testSetLinkBoth() {
        final String label = "knows";
        final Person p3 = fg.addFramedVertex(Person.class);
        final Person p4 = fg.addFramedVertex(Person.class);
        p4.setProperty("name", "noob");
        final Person p5 = fg.addFramedVertex(Person.class);
        p5.setProperty("name", "leet");

        p4.addFramedEdge(label, p3, Knows.class);
        p3.addFramedEdge(label, p4, Knows.class);

        Assert.assertTrue("An in edge of type " + label + " must exist between vertices", p3.getRawTraversal().in(label).has("name", "noob").count().next() > 0);
        Assert.assertTrue("An out edge of type " + label + " must exist between vertices", p3.getRawTraversal().out(label).has("name", "noob").count().next() > 0);

        p3.setLinkBoth(p5, label);

        //Make sure old edge was deleted
        Assert.assertEquals("old " + label + " edge in was not removed", (Long) 0L, p3.getRawTraversal().in(label).has("name", "noob").count().next());
        Assert.assertEquals("old " + label + " edge out was not removed", (Long) 0L, p3.getRawTraversal().out(label).has("name", "noob").count().next());


        //Make sure a new edges were created (and to correct vertex)
        Assert.assertEquals(label + " in edge was not created", (Long) 1L, p3.getRawTraversal().in(label).has("name", "leet").count().next());
        Assert.assertEquals(label + " out edge was not created", (Long) 1L, p3.getRawTraversal().out(label).has("name", "leet").count().next());

    }

    @Test
    public void testSetLinkBothNull() {
        final String label = "knows";
        final Person p3 = fg.addFramedVertex(Person.class);
        final Person p4 = fg.addFramedVertex(Person.class);
        p4.setProperty("name", "noob");
        p3.setLinkBoth(p4, label);
        Assert.assertTrue("An out edge of type " + label + " must exist between vertices", p3.getRawTraversal().both(label).has("name", "noob").count().next() > 0);

        // tests if setLinkOut accepts null values, and only unlinks the edges
        p3.setLinkBoth((VertexFrame) null, label);
        Assert.assertEquals(label + " edge was not unlinked", (Long) 0L, p3.getRawTraversal().both(label).count().next());
    }

    @Test
    public void testSetNewLinkIn() {
        final String label = "knows";
        final Person p3 = fg.addFramedVertex(Person.class);
        final Person p4 = fg.addFramedVertex(Person.class);
        p4.setProperty("name", "noob");

        p4.addFramedEdge(label, p3, Knows.class);

        Assert.assertTrue("An out edge of type " + label + " must exist between vertices", p3.getRawTraversal().in(label).has("name", "noob").count().next() > 0);

        final Person p5 = p3.setLinkIn(Person.class, label);
        p5.setProperty("name", "leet");

        //Make sure old edge was deleted
        Assert.assertEquals("old " + label + " edge was not removed", (Long) 0L, p3.getRawTraversal().in(label).has("name", "noob").count().next());


        //Make sure a new edge was created (and to correct vertex)
        Assert.assertEquals(label + " edge was not created", (Long) 1L, p3.getRawTraversal().in(label).has("name", "leet").count().next());

    }

    @Test
    public void testSetNewLinkOut() {
        final String label = "knows";
        final Person p3 = fg.addFramedVertex(Person.class);
        final Person p4 = fg.addFramedVertex(Person.class);
        p4.setProperty("name", "noob");

        p3.addFramedEdge(label, p4, Knows.class);

        Assert.assertTrue("An out edge of type " + label + " must exist between vertices", p3.getRawTraversal().out(label).has("name", "noob").count().next() > 0);

        final Person p5 = p3.setLinkOut(Person.class, label);
        p5.setProperty("name", "leet");

        //Make sure old edge was deleted
        Assert.assertEquals("old " + label + " edge was not removed", (Long) 0L, p3.getRawTraversal().out(label).has("name", "noob").count().next());


        //Make sure a new edge was created (and to correct vertex)
        Assert.assertEquals(label + " edge was not created", (Long) 1L, p3.getRawTraversal().out(label).has("name", "leet").count().next());

    }

    @Test
    public void testSetNewLinkBoth() {
        final String label = "knows";
        final Person p3 = fg.addFramedVertex(Person.class);
        final Person p4 = fg.addFramedVertex(Person.class);
        p4.setProperty("name", "noob");

        p3.addFramedEdge(label, p4, Knows.class);
        p4.addFramedEdge(label, p3, Knows.class);

        Assert.assertTrue("An in edge of type " + label + " must exist between vertices", p3.getRawTraversal().in(label).has("name", "noob").count().next() > 0);
        Assert.assertTrue("An out edge of type " + label + " must exist between vertices", p3.getRawTraversal().out(label).has("name", "noob").count().next() > 0);

        final Person p5 = p3.setLinkBoth(Person.class, label);
        p5.setProperty("name", "leet");

        //Make sure old edge was deleted
        Assert.assertEquals("old " + label + " edge was not removed", (Long) 0L, p3.getRawTraversal().both(label).has("name", "noob").count().next());


        //Make sure a new edge was created (and to correct vertex)
        Assert.assertEquals(label + " edge(out) was not created", (Long) 1L, p3.getRawTraversal().out(label).has("name", "leet").count().next());
        Assert.assertEquals(label + " edge(in) was not created", (Long) 1L, p3.getRawTraversal().in(label).has("name", "leet").count().next());
    }

}

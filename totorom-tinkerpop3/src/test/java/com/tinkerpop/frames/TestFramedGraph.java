package com.tinkerpop.frames;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.tinkergraph.TinkerFactory;
import org.junit.Test;
/**
 * @author Bryn Cooke (http://jglue.org)
 */

public class TestFramedGraph {
    @Test
    public void test() {
        Graph g = TinkerFactory.createClassic();
        FramedGraph fg = new FramedGraph(g);


        Person p1 = fg.addVertex(Person.class);
        p1.setName("Bryn");

        Person p2 = fg.addVertex(Person.class);
        p2.setName("Julia");
        Knows knows = p1.addKnows(p2);
        knows.setYears(15);

        Iterable<Vertex> vertices = g.query().has("name", "Bryn").vertices();
        Vertex next = vertices.iterator().next();
        System.out.println(next.getProperty("name").get());

        // fg.tx().commit();



    }
}

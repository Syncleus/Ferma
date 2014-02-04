package com.tinkerpop.frames;

import java.util.Iterator;

import org.junit.Test;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
/**
 * @author Bryn Cooke (http://jglue.org)
 */

public class TestFramedGraph {
    @Test
    public void test() {
        Graph g = new TinkerGraph();
        FramedGraph fg = new FramedGraph(g);

        
        

        Person p1 = fg.addVertex(Person.class);
        p1.setName("Bryn");

        Person p2 = fg.addVertex(Person.class);
        p2.setName("Julia");
        Knows knows = p1.addKnows(p2);
        knows.setYears(15);

        Iterator<Person> frame = fg.V().has("name", "Bryn").frame(Person.class);
        Person p = frame.next();
        
        
        System.out.println(p.getName());
        System.out.println(p.getKnowsCollection().get(0).getYears());



    }
    
    @Test
    public void test2() {
        Graph g = new TinkerGraph();
        FramedGraph fg = new FramedGraph(g, TypeResolver.Java, FrameBuilder.Default);

        Person p1 = fg.addVertex(Programmer.class);
        p1.setName("Bryn");

        Person p2 = fg.addVertex(Person.class);
        p2.setName("Julia");
        Knows knows = p1.addKnows(p2);
        knows.setYears(15);

        Person bryn = fg.V().has("name", "Bryn").next(Person.class);
        Person julia = fg.V().has("name", "Julia").next(Person.class);
        
        
        System.out.println(bryn.getClass().getName());
        System.out.println(julia.getClass().getName());
        

    }
}

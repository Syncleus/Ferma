package org.jglue.totorom;

import org.junit.Assert;
import org.junit.Test;

import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
/**
 * @author Bryn Cooke (http://jglue.org)
 */

public class TestFramedGraph {
    @Test
    public void testSanity() {
        Graph g = new TinkerGraph();
        FramedGraph fg = new FramedGraph(g);
        Person p1 = fg.addVertex(Person.class);
        p1.setName("Bryn");

        Person p2 = fg.addVertex(Person.class);
        p2.setName("Julia");
        Knows knows = p1.addKnows(p2);
        knows.setYears(15);

        Person bryn = fg.V().has("name", "Bryn").next(Person.class);
        
        
        Assert.assertEquals("Bryn", bryn.getName());
        Assert.assertEquals(15, bryn.getKnowsList().get(0).getYears());
   

    }
    
    @Test
    public void testJavaTyping() {
        Graph g = new TinkerGraph();
        FramedGraph fg = new FramedGraph(g, FrameFactory.Default, TypeResolver.Java);

        Person p1 = fg.addVertex(Programmer.class);
        p1.setName("Bryn");

        Person p2 = fg.addVertex(Person.class);
        p2.setName("Julia");

        Person bryn = fg.V().has("name", "Bryn").next(Person.class);
        Person julia = fg.V().has("name", "Julia").next(Person.class);
        
        Assert.assertEquals(Programmer.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());
    }
    
    
    
    
    @Test
    public void testCustomFrameBuilder() {
    	final Person o = new Person();
        Graph g = new TinkerGraph();
        FramedGraph fg = new FramedGraph(g, new FrameFactory() {
			
			@SuppressWarnings("unchecked")
			@Override
			public <T extends FramedElement<?>> T create(Element e, Class<T> kind) {
				return (T)o;
			}
		}, TypeResolver.Java);
        Person person = fg.addVertex(Person.class);
        Assert.assertEquals(o, person);
    }
}

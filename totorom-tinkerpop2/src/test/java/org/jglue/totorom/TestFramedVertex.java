package org.jglue.totorom;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
/**
 * @author Bryn Cooke (http://jglue.org)
 */

public class TestFramedVertex {

	
	private FramedGraph fg;
	private Person p1;
	private Person p2;
	private Knows e1;
    
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		Graph g = new TinkerGraph();
        fg = new FramedGraph(g);
        p1 = fg.addVertex(Person.class);
        p2 = fg.addVertex(Person.class);
        p1.setName("Bryn");
        p2.setName("Julia");
        e1 = p1.addKnows(p2);
        e1.setYears(15);

	}
	
    @Test
    public void testOut() {
        
        Assert.assertEquals(p2, p1.out().next(Person.class));
        Assert.assertEquals(p2, p1.out(1).next(Person.class));
    }

    
    @Test
    public void testIn() {
        Assert.assertEquals(p1, p2.in().next(Person.class));
        Assert.assertEquals(p1, p2.in(1).next(Person.class));
        
    }
    
    @Test
    public void testOutE() {
        Assert.assertEquals(e1, p1.outE().next(Knows.class));
        Assert.assertEquals(e1, p1.outE(1).next(Knows.class));
    }
    
    @Test
    public void testInE() {
        Assert.assertEquals(e1, p2.inE().next(Knows.class));
        Assert.assertEquals(e1, p2.inE(1).next(Knows.class));
    }
    
    @Test
    public void testBoth() {
        Assert.assertEquals(p2, p1.both().next(Person.class));
        Assert.assertEquals(p2, p1.both(1).next(Person.class));
        Assert.assertEquals(p1, p2.both().next(Person.class));
        Assert.assertEquals(p1, p2.both(1).next(Person.class));
    }
    
    @Test
    public void testBothE() {
        Assert.assertEquals(e1, p2.bothE().next(Knows.class));
        Assert.assertEquals(e1, p2.bothE(1).next(Knows.class));
    }
}

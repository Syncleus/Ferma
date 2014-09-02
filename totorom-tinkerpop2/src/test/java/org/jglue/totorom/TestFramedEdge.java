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

public class TestFramedEdge {

	
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
    public void testLabel() {
        Assert.assertEquals("knows", e1.getLabel());
    }
    
    @Test
    public void testInV() {
    	Assert.assertEquals(p2, e1.inV().next(Person.class));
    }
    
    @Test
    public void testOutV() {
    	Assert.assertEquals(p1, e1.outV().next(Person.class));
    }
    
    @Test
    public void testBothV() {
    	Assert.assertEquals(p1, e1.bothV().next(Person.class));
    }
}

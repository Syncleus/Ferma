package org.jglue.totorom;

import java.util.Collection;

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

public class TestFramedGraph {
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
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
   
        Collection<Integer> knowsCollection = fg.V().has("name", "Julia").bothE().property("years", Integer.class).aggregate().cap();
        Assert.assertEquals(1, knowsCollection.size());
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
			public <T extends FramedElement> T create(Element e, Class<T> kind) {
				return (T)o;
			}
		}, TypeResolver.Java);
        Person person = fg.addVertex(Person.class);
        Assert.assertEquals(o, person);
    }
    
    @Mock
    private TransactionalGraph transactionalGraph;
    
    public void testTransactionUnsupported() {
        Graph g = new TinkerGraph();
        FramedGraph fg = new FramedGraph(g);
        try(Transaction t = fg.tx()) {
        	
        }

    }
    
    @Test
    public void testTransactionCommitted() {
        
        FramedGraph fg = new FramedGraph(transactionalGraph);
        try(Transaction t = fg.tx()) {
        	t.commit();
        }

        Mockito.verify(transactionalGraph).commit();
    }
    
    @Test
    public void testTransactionRolledBack() {
        
        FramedGraph fg = new FramedGraph(transactionalGraph);
        try(Transaction t = fg.tx()) {
        	t.rollback();
        }

        Mockito.verify(transactionalGraph).rollback();
    }
    
    @Test
    public void testTransactionNotComitted() {
        
        FramedGraph fg = new FramedGraph(transactionalGraph);
        try(Transaction t = fg.tx()) {
        	
        }

        Mockito.verify(transactionalGraph).rollback();
    }
    
    @Test
    public void testTransactionException() {
        
        FramedGraph fg = new FramedGraph(transactionalGraph);
        
        try(Transaction t = fg.tx()) {
        	throw new Exception();
        }
        catch(Exception e) {
        }

        Mockito.verify(transactionalGraph).rollback();
    }
    
    
}

/**
 * Copyright 2014-Infinity Bryn Cooke
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 * This project is derived from code in the Tinkerpop project under the following licenses:
 *
 * Tinkerpop3
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Tinkerpop2
 * Copyright (c) 2009-Infinity, TinkerPop [http://tinkerpop.com]
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the TinkerPop nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL TINKERPOP BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.jglue.totorom;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Sets;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
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
    

    @Test
    public void testUntypedFrames() {
        Graph g = new TinkerGraph();
        FramedGraph fg = new FramedGraph(g);
        TVertex p1 = fg.addVertex();
        p1.setProperty("name", "Bryn");

        TVertex p2 = fg.addVertex();
        p2.setProperty("name", "Julia");
        TEdge knows = p1.addEdge("knows", p2);
        knows.setProperty("years", 15);

        TVertex bryn = fg.V().has("name", "Bryn").next();
        
        
        Assert.assertEquals("Bryn", bryn.getProperty("name"));
        Assert.assertEquals(15, bryn.outE("knows").toList().get(0).getProperty("years"));
   
        Collection<Integer> knowsCollection = fg.V().has("name", "Julia").bothE().property("years", Integer.class).aggregate().cap();
        Assert.assertEquals(1, knowsCollection.size());
    }
    
    
    @Test
    public void testKeyValueTraversal() {
        Graph g = TinkerGraphFactory.createTinkerGraph();
        FramedGraph fg = new FramedGraph(g);
        Assert.assertEquals(Sets.newHashSet("3", "5"),  fg.V().has("lang", "java").id().toSet());

    }
}

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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Sets;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
/**
 * @author Bryn Cooke (http://jglue.org)
 */

public class TestFramedElement {

	
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
    public void testGetId() {
    	Assert.assertEquals("0", p1.getId());
        
    }
    
    @Test
    public void testGetPropertyKeys() {
    	Assert.assertEquals(Sets.newHashSet("name"), p1.getPropertyKeys());
    }
    
    @Test
    public void testGetProperty() {
    	Assert.assertEquals("Bryn", p1.getProperty("name"));
    }
    
    @Test
    public void testSetProperty() {
    	p1.setProperty("name", "Bryn Cooke");
    	Assert.assertEquals("Bryn Cooke", p1.getProperty("name"));
    }
    
    @Test
    public void testSetPropertyNull() {
    	p1.setProperty("name", null);
    	Assert.assertNull(p1.getProperty("name"));
    }
    
    
    @Test
    public void testV() {
    	Assert.assertEquals(2, p1.V().count());
    }
    
    @Test
    public void testE() {
    	Assert.assertEquals(1, p1.E().count());
    }
    
    @Test
    public void testv() {
    	Assert.assertEquals(p1, p1.v(p1.getId()).next(Person.class));
    }
    
    @Test
    public void testvNull() {
    	Assert.assertNull(p1.v("noId").next());
    }
    
    @Test
    public void teste() {
    	Assert.assertEquals(e1, p1.e(e1.getId()).next(Knows.class));
    }
    
    @Test
    public void testRemove() {
    	p1.remove();
    	Assert.assertEquals(1, p1.V().count());
    }
    
    @Test
    public void testReframe() {
    	TVertex v1 = p1.reframe(TVertex.class);
    	Assert.assertEquals(p1.getId(), v1.getId());
    }
    
}

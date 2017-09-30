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
package com.syncleus.ferma.framefactories;

import com.syncleus.ferma.Person;
import com.syncleus.ferma.graphtypes.javaclass.invalid.OneArgConstructorVertex;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author rqpa
 */
public class DefaultFrameFactoryTest {
    
    private Graph graph;
    private Vertex emptyVertex;
    private DefaultFrameFactory factory;
    
    @Before
    public void setUp() {
        graph = TinkerGraph.open();
        emptyVertex = graph.addVertex();
        factory = new DefaultFrameFactory();
    }
    
    @After
    public void tearDown() throws Exception {
        graph.close();
    }
    
    @Test
    public void testCreate() {
        Person frame = factory.create(emptyVertex, Person.class);
        Assert.assertEquals(Person.class, frame.getClass());
    } 
    
    @Test (expected = IllegalStateException.class)
    public void testCreateBadFrame() {
        factory.create(emptyVertex, OneArgConstructorVertex.class);
    } 
}

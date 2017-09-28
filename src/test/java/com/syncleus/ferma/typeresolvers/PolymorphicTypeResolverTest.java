/**
 * Copyright 2004 - 2016 Syncleus, Inc.
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
package com.syncleus.ferma.typeresolvers;

import java.util.List;
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
public class PolymorphicTypeResolverTest {
    
    private static final String TYPE_RESOLUTION_KEY = "typeREsKey";
    private PolymorphicTypeResolver resolver;
    
    private Graph graph;
    private Vertex emptyVertex;
    
    @Before
    public void setUp() {
        graph = TinkerGraph.open();
        resolver = new PolymorphicTypeResolver(TYPE_RESOLUTION_KEY);
        emptyVertex = graph.addVertex();
    }
    
    @After
    public void tearDown() throws Exception {
        graph.close();
    }
    
    @Test
    public void testResolveType() {
        Class<?> testClass = List.class;
        Assert.assertNull(resolver.resolve(emptyVertex));
        emptyVertex.property(TYPE_RESOLUTION_KEY, testClass.getName());
        Assert.assertEquals(resolver.resolve(emptyVertex), testClass);
    }
    
    @Test
    public void testDeinit() {
        Class<?> testClass = List.class;
        emptyVertex.property(TYPE_RESOLUTION_KEY, testClass.getName());
        Assert.assertEquals(resolver.resolve(emptyVertex), testClass);
        resolver.deinit(emptyVertex);
        Assert.assertNull(resolver.resolve(emptyVertex));
    }
}

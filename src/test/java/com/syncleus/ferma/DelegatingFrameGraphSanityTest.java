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
package com.syncleus.ferma;

import com.syncleus.ferma.typeresolvers.TypeResolver;
import com.syncleus.ferma.typeresolvers.UntypedTypeResolver;
import java.util.HashSet;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if DelegatingFramedGraph constructors create usable instance
 *
 * @author rqpa
 */
public class DelegatingFrameGraphSanityTest {

    private Graph g;
    private TypeResolver resolver;

    @Before
    public void setUp() {
        g = TinkerGraph.open();
        resolver = new UntypedTypeResolver();
    }

    @After
    public void tearDown() throws Exception {
        g.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDelegate() {
        new DelegatingFramedGraph(null, resolver);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullResolver() {
        new DelegatingFramedGraph(g, (TypeResolver) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullBuilder() {
        new DelegatingFramedGraph(g, null, resolver);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullReflectionsCache() {
        new DelegatingFramedGraph(g, null, true, true);
    }

    @Test
    public void testNoTypeResolutionNoAnnotations() {
        assertSanity(new DelegatingFramedGraph(g, new ReflectionCache(), false, false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullTypes() {
        new DelegatingFramedGraph(g, true, null);
    }

    @Test
    public void testEmptyTypesSet() {
        assertSanity(new DelegatingFramedGraph(g, false, new HashSet<>()));
    }

    private void assertSanity(DelegatingFramedGraph framed) {
        Assert.assertNotNull(framed.getBaseGraph());
        Assert.assertNotNull(framed.getBuilder());
        Assert.assertNotNull(framed.getTypeResolver());
    }

}

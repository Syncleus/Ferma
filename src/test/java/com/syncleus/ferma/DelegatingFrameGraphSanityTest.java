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
package com.syncleus.ferma;

import com.syncleus.ferma.typeresolvers.TypeResolver;
import com.syncleus.ferma.typeresolvers.UntypedTypeResolver;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

    @Test
    public void testNullDelegate() {
        try {
            assertSanity(new DelegatingFramedGraph(null, resolver));
        } catch (IllegalArgumentException e) {
            // Illegal args is ok
        }
    }

    @Test
    public void testNullResolver() {
        try {
            assertSanity(new DelegatingFramedGraph(g, (TypeResolver) null));
        } catch (IllegalArgumentException e) {
            // Illegal args is ok
        }
    }

    @Test
    public void testNullBuilder() {
        try {
            assertSanity(new DelegatingFramedGraph(g, null, resolver));
        } catch (IllegalArgumentException e) {
            // Illegal args is ok
        }
    }

    @Test
    public void testNullReflectionsCache() {
        try {
            assertSanity(new DelegatingFramedGraph(g, null, true, true));
        } catch (IllegalArgumentException e) {
            // Illegal args is ok
        }
    }

    @Test
    public void testNoTypeResolutionNoAnnotations() {
        try {
            assertSanity(new DelegatingFramedGraph(g, new ReflectionCache(), false, false));
        } catch (IllegalArgumentException e) {
            // Illegal args is ok
        }
    }

    @Test
    public void testNullTypes() {
        try {
            assertSanity(new DelegatingFramedGraph(g, true, null));
        } catch (IllegalArgumentException e) {
            // Illegal args is ok
        }
    }

    @Test
    public void testEmptyTypesSet() {
        try {
            assertSanity(new DelegatingFramedGraph(g, false, new HashSet<>()));
        } catch (IllegalArgumentException e) {
            // Illegal args is ok
        }
    }

    private void assertSanity(DelegatingFramedGraph framed) {
        Assert.assertNotNull(framed.getBaseGraph());
        Assert.assertNotNull(framed.getBuilder());
        Assert.assertNotNull(framed.getTypeResolver());
    }

}

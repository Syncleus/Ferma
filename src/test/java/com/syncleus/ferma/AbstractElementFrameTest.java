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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Sets;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;

public class AbstractElementFrameTest {

    private Person p1;
    private Knows e1;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        final Graph g = new TinkerGraph();
        final FramedGraph fg = new DelegatingFramedGraph(g);
        p1 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
        final Person p2 = fg.addFramedVertex(Person.DEFAULT_INITIALIZER);
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
        Assert.assertEquals(2, p1.v().count());
    }

    @Test
    public void testE() {
        Assert.assertEquals(1, p1.e().count());
    }

    @Test
    public void testv() {
        Assert.assertEquals(p1, p1.v(p1.getId()).next(Person.class));
    }

    @Test
    public void teste() {
        Assert.assertEquals(e1, p1.e(e1.getId()).next(Knows.class));
    }

    @Test
    public void testvExplicit() {
        Assert.assertEquals(p1, p1.v(p1.getId()).nextExplicit(Person.class));
    }

    @Test
    public void testeExplicit() {
        Assert.assertEquals(e1, p1.e(e1.getId()).nextExplicit(Knows.class));
    }

    @Test
    public void testRemove() {
        p1.remove();
        Assert.assertEquals(1, p1.v().count());
    }

    @Test
    public void testReframe() {
        final TVertex v1 = p1.reframe(TVertex.class);
        Assert.assertEquals(p1.getId(), v1.getId());
    }

    @Test
    public void testReframeExplicit() {
        final TVertex v1 = p1.reframeExplicit(TVertex.class);
        Assert.assertEquals(p1.getId(), v1.getId());
    }

    @Test
    public void testvNull() {
        Assert.assertNull(p1.v("noId").next());
    }

}

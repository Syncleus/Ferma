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

import java.util.Comparator;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author rqpa
 */
public class ComparatorsTest {

    private Person p1;
    private Person p2;
    private Person p3;
    private Person p4;
    
    @Before
    public void setUp() {
        FramedGraph g = new DelegatingFramedGraph(TinkerGraph.open());
        p1 = g.addFramedVertex(Person.DEFAULT_INITIALIZER, T.id, "1");
        p1.setName("Alice");
        p2 = g.addFramedVertex(Person.DEFAULT_INITIALIZER, T.id, "2");
        p2.setName("Bob");
        p3 = g.addFramedVertex(Person.DEFAULT_INITIALIZER, T.id, "3");
        p3.setName("Frank");
        p4 = g.addFramedVertex(Person.DEFAULT_INITIALIZER, T.id, "4");
        p4.setName("Zelda");
    }
    
    @Test
    public void testIdComparator() {
        Comparator<ElementFrame> comparator = Comparators.id();
        Assert.assertTrue(comparator.compare(p1, p1) == 0);
        Assert.assertTrue(comparator.compare(p2, p1) > 0);
        Assert.assertTrue(comparator.compare(p1, p2) < 0);
    }
    
    @Test
    public void testIdAsLongComparator() {
        Comparator<ElementFrame> comparator = Comparators.idAsLong();
        Assert.assertTrue(comparator.compare(p1, p1) == 0);
        Assert.assertTrue(comparator.compare(p2, p1) > 0);
        Assert.assertTrue(comparator.compare(p1, p2) < 0);
    }
    
    @Test
    public void testPropertyComparator() {
        Comparator<ElementFrame> comparator = Comparators.property("name");
        Assert.assertTrue(comparator.compare(p1, p1) == 0);
        Assert.assertTrue(comparator.compare(p2, p1) > 0);
        Assert.assertTrue(comparator.compare(p1, p2) < 0);
    }
}

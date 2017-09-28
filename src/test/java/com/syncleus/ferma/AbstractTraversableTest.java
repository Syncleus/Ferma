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

import com.syncleus.ferma.annotations.NetworkGraphTestHelper;
import com.syncleus.ferma.graphtypes.network.ComputerVertex;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.util.function.TriFunction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author rqpa
 */
public abstract class AbstractTraversableTest extends NetworkGraphTestHelper {
    
    private Set<String> dev2AdjacentVerticesNames;
    private ComputerVertex dev6;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        dev2AdjacentVerticesNames = new HashSet<>();
        dev2AdjacentVerticesNames.add(dev1.getName());
        dev2AdjacentVerticesNames.add(dev5.getName());
        dev2AdjacentVerticesNames = Collections.unmodifiableSet(dev2AdjacentVerticesNames);
        
        dev6 = graph.addFramedVertex(ComputerVertex.class);
        dev6.setName("DEV6");
        graph.addFramedEdge(dev6, dev5, "connects");
    }

    @Test
    public void testNextOrDefault() {
        doTestNextOrDefault((t, def) -> t.nextOrDefault(ComputerVertex.class, def));
    }

    @Test
    public void testNextOrDefaultExplicit() {
        doTestNextOrDefault((t, def) -> t.nextOrDefaultExplicit(ComputerVertex.class, def));
    }

    private void doTestNextOrDefault(BiFunction<Traversable<?, ?>, ComputerVertex, ComputerVertex> nextOrDefault) {
        Traversable<?, ?> t = createAdjacentVerticesTraversable(dev2);
        Set<String> dev2Adjacent = new HashSet<>(dev2AdjacentVerticesNames);
        ComputerVertex lastDevice;
        while (true) {
            lastDevice = nextOrDefault.apply(t, dev6);
            Assert.assertNotNull(lastDevice);
            Assert.assertNotNull(lastDevice.getName());
            boolean existed = dev2Adjacent.remove(lastDevice.getName());
            if (!existed) {
                break;
            }
        }
        
        // now all connections dev2 adjacent vertices must have been traversed
        // and dev2Adjacent should be empty
        Assert.assertTrue(dev2Adjacent.isEmpty());
        // Also the last device must be the default one (dev6)
        Assert.assertEquals(dev6.getName(), lastDevice.getName());
    }
    
    @Test
    public void testNextOrAdd() {
        Traversable<?, ?> t = createAdjacentVerticesTraversable(dev6);
        VertexFrame actualDev5 = t.nextOrAdd();
        Assert.assertEquals(dev5.getName(), actualDev5.getProperty("name"));
        VertexFrame actualNew = t.nextOrAdd();
        Assert.assertNull(actualNew.getProperty("name"));
    }
    
    @Test
    public void testNextOrAddExplicitWithInitializer() {
        doTestNextOrAddTyped(t -> t.nextOrAddExplicit(ComputerVertex.DEFAULT_INITIALIZER));
    }
    
    @Test
    public void testNextOrAddExplicitWithKind() {
        doTestNextOrAddTyped(t -> t.nextOrAddExplicit(ComputerVertex.class));
    }
    
    @Test
    public void testNextOrAddWithInitializer() {
        doTestNextOrAddTyped(t -> t.nextOrAdd(ComputerVertex.DEFAULT_INITIALIZER));
    }
    
    private void doTestNextOrAddTyped(Function<Traversable<?, ?>, ComputerVertex> nextOrAdd) {
        Traversable<?, ?> t = createAdjacentVerticesTraversable(dev6);
        ComputerVertex actualDev5 = nextOrAdd.apply(t);
        Assert.assertEquals(dev5.getName(), actualDev5.getName());
        ComputerVertex actualNew = nextOrAdd.apply(t);
        Assert.assertNull(actualNew.getName());
    }
    
    @Test
    public void testNextOrAddWithKind() {
        Traversable<?, ?> t = createAdjacentVerticesTraversable(dev6);
        ComputerVertex actualDev5 = t.nextOrAdd(ComputerVertex.class);
        Assert.assertEquals(dev5.getName(), actualDev5.getName());
        ComputerVertex actualNew = t.nextOrAdd(ComputerVertex.class);
        Assert.assertNull(actualNew.getName());
    }
    
    @Test
    public void testNextAmount() {
        doTestNextAmount(Traversable::next);
    }
    
    @Test
    public void testNextAmountExplicit() {
        doTestNextAmount(Traversable::nextExplicit);
    }
    
    private void  doTestNextAmount(TriFunction<Traversable<?, ?>, Integer, Class<ComputerVertex>, List<? extends ComputerVertex>> listExtractor) {
        List<? extends ComputerVertex> l = listExtractor.apply(createAdjacentVerticesTraversable(dev2), 1, ComputerVertex.class);
        Assert.assertEquals(1, l.size());
        Assert.assertTrue(dev2AdjacentVerticesNames.contains(l.get(0).getName()));
        
        Set<String> dev2Adj = new HashSet<>(dev2AdjacentVerticesNames);
        l = listExtractor.apply(createAdjacentVerticesTraversable(dev2), 2, ComputerVertex.class);
        Assert.assertEquals(2, l.size());
        for (ComputerVertex v : l) {
            Assert.assertTrue(dev2Adj.remove(v.getName()));
        }
        
        dev2Adj = new HashSet<>(dev2AdjacentVerticesNames);
        l = listExtractor.apply(createAdjacentVerticesTraversable(dev2), 4, ComputerVertex.class);
        Assert.assertEquals(2, l.size());
        for (ComputerVertex v : l) {
            Assert.assertTrue(dev2Adj.remove(v.getName()));
        }
    }
    
    private Traversable<?, ?> createAdjacentVerticesTraversable(ComputerVertex dev) {
        return createNewTraversable(graph, dev.traverse(input -> input.both().dedup()).getRawTraversal());
    }
    
    protected abstract <PE, E> Traversable<PE, E> createNewTraversable(FramedGraph graph, GraphTraversal<PE, E> graphTraversal);
}

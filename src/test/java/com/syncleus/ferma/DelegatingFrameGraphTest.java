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

import com.syncleus.ferma.annotations.NetworkGraphTestHelper;
import com.syncleus.ferma.graphtypes.network.ComputerVertex;
import com.syncleus.ferma.graphtypes.network.NetworkConnectionEdge;
import com.syncleus.ferma.graphtypes.network.NetworkDeviceVertex;
import com.syncleus.ferma.typeresolvers.PolymorphicTypeResolver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author rqpa
 */
public class DelegatingFrameGraphTest extends NetworkGraphTestHelper {
    
    private Element dev6;
    private final String dev6Name = "DEV6";
    private Element dev7;
    private final String dev7Name = "DEV7";
    private final int allDevicesCount = 7;
    private final int allConnectionsCount = 12;

    @Before
    @Override
    public void setUp() {
        super.setUp(); 
        
        dev6 = graph.getBaseGraph().addVertex("name", dev6Name);
        dev7 = graph.getBaseGraph().addVertex("name", dev7Name);
    }
    
    
    
    @Test
    public void testFrameNullElement() {
        Assert.assertNull(graph.frameElement(null, NetworkDeviceVertex.class));
    }
    
    @Test
    public void testFrameNullElementExplicit() {
        Assert.assertNull(graph.frameElementExplicit(null, NetworkDeviceVertex.class));
    }
    
    @Test
    public void testFrameNewElement() {
        ComputerVertex dev6Framed = graph.frameNewElement(dev6, ComputerVertex.class);
        Assert.assertNotNull(dev6Framed);
        Assert.assertEquals(dev6Name, dev6Framed.getName());
    }
    
    @Test
    public void testFrameNewElementsIterator() {
        Iterator<? extends ComputerVertex> framed = graph.frame(Arrays.asList(dev6, dev7).iterator(), ComputerVertex.class);
        assertDev6Dev7Iterator(framed);
    }
    
    @Test
    public void testFrameNewElementsExplicitIterator() {
        Iterator<? extends ComputerVertex> framed = graph.frameExplicit(Arrays.asList(dev6, dev7).iterator(), ComputerVertex.class);
        assertDev6Dev7Iterator(framed);
    }
    
    private void assertDev6Dev7Iterator(Iterator<? extends ComputerVertex> actual) {
        Assert.assertNotNull(actual);
        Assert.assertTrue(actual.hasNext());
        Assert.assertEquals(dev6Name, actual.next().getName());
        Assert.assertTrue(actual.hasNext());
        Assert.assertEquals(dev7Name, actual.next().getName());
        Assert.assertFalse(actual.hasNext());
    }
    
    @Test
    public void testFrameNewElementExplicit() {
        ComputerVertex actual = graph.frameNewElementExplicit(dev6, ComputerVertex.class);
        Assert.assertNotNull(actual);
        Assert.assertEquals(dev6Name, actual.getName());
    }
    
    @Test
    public void testAddFramedEdgeExplicitWithKind() {
        NetworkConnectionEdge actual = graph.addFramedEdgeExplicit(
                dev1,
                dev5, 
                "connects",
                NetworkConnectionEdge.class);
        Assert.assertNotNull(actual);
        assertOneWayExclusiveConnection(dev1, dev5);
    }
    
    @Test
    public void testAddFramedEdgeExplicitWithInitializer() {
        NetworkConnectionEdge actual = graph.addFramedEdgeExplicit(
                dev1,
                dev5, 
                "connects",
                NetworkConnectionEdge.DEFAULT_INITIALIZER);
        Assert.assertNotNull(actual);
        assertOneWayExclusiveConnection(dev1, dev5);
    }
    
    @Test
    public void testAddFramedEdgeExplicitUntyped() {
        TEdge actual = graph.addFramedEdgeExplicit(
                dev1,
                dev5, 
                "connects");
        Assert.assertNotNull(actual);
        assertOneWayExclusiveConnection(dev1, dev5);
    }
    
    @Test
    public void testGetFramedVertexById() {
        ComputerVertex actual = graph.getFramedVertex(ComputerVertex.class, dev1.getId());
        assertIsDev1(actual);
    }
    
    private void assertIsDev1(Iterator<? extends ComputerVertex> actual) {
        Assert.assertNotNull(actual);
        Assert.assertTrue(actual.hasNext());
        assertIsDev1(actual.next());
        Assert.assertFalse(actual.hasNext());
    }
    
    private void assertIsDev1(ComputerVertex actual) {
        Assert.assertNotNull(actual);
        Assert.assertEquals(dev1.getName(), actual.getName());
    }
    
    @Test
    public void testGetAllFramedVertices() {
        Iterator<? extends ComputerVertex> actual = graph.getFramedVertices(ComputerVertex.class);
        assertAllDevices(actual);
    }
    
    @Test
    public void testGetAllFramedVerticesExplicit() {
        Iterator<? extends ComputerVertex> actual = graph.getFramedVerticesExplicit(ComputerVertex.class);
        assertAllDevices(actual);
    }
    
    private void assertAllDevices(Iterator<? extends ComputerVertex> actualIt) {
        List<? extends ComputerVertex> actualAsList = iteratorToList(actualIt);
        Assert.assertEquals(allDevicesCount, actualAsList.size());
        for (ComputerVertex actual : actualAsList) {
            Assert.assertNotNull(actual);
            Assert.assertTrue(actual.getName().startsWith("DEV"));
        }
    }
    
    private void assertAllConnections(Iterator<? extends NetworkConnectionEdge> actualIt) {
        List<? extends NetworkConnectionEdge> actualAsList = iteratorToList(actualIt);
        Assert.assertEquals(allConnectionsCount, actualAsList.size());
        for (NetworkConnectionEdge actual : actualAsList) {
            Assert.assertNotNull(actual);
            Assert.assertNotNull(actual.getFrom());
            Assert.assertNotNull(actual.getTo());
            Assert.assertTrue(actual.getFrom().getName().startsWith("DEV"));
            Assert.assertTrue(actual.getTo().getName().startsWith("DEV"));
        }
    }
    
    private <T> List<T> iteratorToList(Iterator<T> it) {
        List<T> list = new ArrayList<>();
        it.forEachRemaining(list::add);
        return list;
    }
    
    @Test
    public void testGetAllFramedByProperty() {
        Iterator<? extends ComputerVertex> actual = graph.getFramedVertices("name", dev1.getName(), ComputerVertex.class);
        assertIsDev1(actual);
    }
    
    @Test
    public void testGetAllFramedByPropertyExplicit() {
        Iterator<? extends ComputerVertex> actual = graph.getFramedVerticesExplicit("name", dev1.getName(), ComputerVertex.class);
        assertIsDev1(actual);
    }
    
    @Test
    public void testGetAllEdgesFramed() {
        Iterator<? extends NetworkConnectionEdge> actual = graph.getFramedEdges(NetworkConnectionEdge.class);
        assertAllConnections(actual);
    }
    
    @Test
    public void testGetAllEdgesFramedExplicit() {
        Iterator<? extends NetworkConnectionEdge> actual = graph.getFramedEdgesExplicit(NetworkConnectionEdge.class);
        assertAllConnections(actual);
    }
    
    @Test
    public void testGetAllEdgesFramedByProperty() {
        Iterator<? extends NetworkConnectionEdge> actual = graph.getFramedEdges(
                PolymorphicTypeResolver.TYPE_RESOLUTION_KEY, 
                NetworkConnectionEdge.class.getName(), 
                NetworkConnectionEdge.class);
        assertAllConnections(actual);
    }
    
    @Test
    public void testGetAllEdgesFramedByPropertyExplicit() {
        Iterator<? extends NetworkConnectionEdge> actual = graph.getFramedEdgesExplicit(
                PolymorphicTypeResolver.TYPE_RESOLUTION_KEY, 
                NetworkConnectionEdge.class.getName(), 
                NetworkConnectionEdge.class);
        assertAllConnections(actual);
    }
}

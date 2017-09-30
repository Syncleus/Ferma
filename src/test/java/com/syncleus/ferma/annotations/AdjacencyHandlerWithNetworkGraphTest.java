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
package com.syncleus.ferma.annotations;

import com.syncleus.ferma.VertexFrame;
import com.syncleus.ferma.graphtypes.network.NetworkConnectionEdge;
import org.junit.Test;
import com.syncleus.ferma.graphtypes.network.ComputerVertex;
import com.syncleus.ferma.graphtypes.network.RouterVertex;
import java.util.Arrays;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Graph;

/**
 *
 * @author rqpa
 */
public class AdjacencyHandlerWithNetworkGraphTest extends NetworkGraphTestHelper {
    
    private static final String newDevName = "DEV6";
    
    @Test
    public void testAddVertexBothDefault() {
        VertexFrame dev6 = dev1.addAndConnectBothDefault();
        ComputerVertex dev6reframed = dev6.reframe(ComputerVertex.class);
        dev6reframed.setName(newDevName);
        assertTwoWayConnection(dev1, dev6reframed);
    }
    
    @Test
    public void testAddVertexOutTyped() {
        VertexFrame dev6 = dev1.addAndConnectOutDefault();
        ComputerVertex dev6reframed = dev6.reframe(ComputerVertex.class);
        dev6reframed.setName(newDevName);
        assertOneWayExclusiveConnection(dev1, dev6reframed);
    }
    
    @Test
    public void testAddVertexBothVertexTypedEdgeDefault() {
        ComputerVertex dev6 = dev1.addAndConnectBothVertexTypedEdgeDefault(ComputerVertex.DEFAULT_INITIALIZER);
        dev6.setName(newDevName);
        assertTwoWayConnection(dev1, dev6);
    }
    
    @Test
    public void testAddVertexOutVertexTypedEdgeDefault() {
        ComputerVertex dev6 = dev1.addAndConnectOutVertexTypedEdgeDefault(ComputerVertex.DEFAULT_INITIALIZER);
        dev6.setName(newDevName);
        assertOneWayExclusiveConnection(dev1, dev6);
    }
    
    @Test
    public void testAddVertexBothVertexTypedEdgeTyped() {
        ComputerVertex dev6 = dev1.addAndConnectBothVertexTypedEdgeTyped(ComputerVertex.DEFAULT_INITIALIZER,
                NetworkConnectionEdge.DEFAULT_INITIALIZER);
        dev6.setName(newDevName);
        assertTwoWayConnection(dev1, dev6);
    }
    
    @Test
    public void testAddVertexOutVertexTypedEdgeTyped() {
        ComputerVertex dev6 = dev1.addAndConnectOutVertexTypedEdgeTyped(ComputerVertex.DEFAULT_INITIALIZER,
                NetworkConnectionEdge.DEFAULT_INITIALIZER);
        dev6.setName(newDevName);
        assertOneWayExclusiveConnection(dev1, dev6);
    }
    
    @Test
    public void testAddVertexBothUntypedEdge() {
        RouterVertex dev6 = dev1.addAndConnectBoth(makeRouterVertex(graph.getBaseGraph(), newDevName));
        dev6.setName(newDevName);
        assertTwoWayConnection(dev1, dev6);
    }
    
    @Test
    public void testAddVertexOutUntypedEdge() {
        RouterVertex dev6 = dev1.addAndConnectOut(makeRouterVertex(graph.getBaseGraph(), newDevName));
        dev6.setName(newDevName);
        assertOneWayExclusiveConnection(dev1, dev6);
    }
    
    @Test
    public void testAddVertexBothTypedEdge() {
        RouterVertex dev6 = dev1.addAndConnectBothTypedEdge(
                makeRouterVertex(graph.getBaseGraph(), newDevName),
                NetworkConnectionEdge.DEFAULT_INITIALIZER);
        dev6.setName(newDevName);
        assertTwoWayConnection(dev1, dev6);
    }
    
    @Test
    public void testAddVertexOutTypedEdge() {
        RouterVertex dev6 = dev1.addAndConnectOutTypedEdge(
                makeRouterVertex(graph.getBaseGraph(), newDevName),
                NetworkConnectionEdge.DEFAULT_INITIALIZER);
        dev6.setName(newDevName);
        assertOneWayExclusiveConnection(dev1, dev6);
    }
    
    private RouterVertex makeRouterVertex(Graph graph, String name) {
        Element e = graph.addVertex();
        RouterVertex rv = new RouterVertex();
        rv.setElement(e);
        rv.setName(name);
        return rv;
    }
    
    @Test
    public void testSetTwoWayEdgesMultipleVerticesIterator() {
        // Add the new vertex, connect it to dev1 and verify the connection
        testAddVertexBothVertexTypedEdgeTyped();
        ComputerVertex newDev = findDeviceByName(graph, newDevName);
        // Now make it linked only to devices 2 and 4
        newDev.setTwoWayConnectionsWith(Arrays.asList(dev2, dev4).iterator());
        assertTwoWayConnection(newDev, dev2);
        assertTwoWayConnection(newDev, dev4);
        assertNoConnection(newDev, dev1);
    }
    
    @Test
    public void testSetTwoWayEdgesMultipleVerticesIterable() {
        // Add the new vertex, connect it to dev1 and verify the connection
        testAddVertexBothVertexTypedEdgeTyped();
        ComputerVertex newDev = findDeviceByName(graph, newDevName);
        // Now make it linked only to devices 2 and 4
        newDev.setTwoWayConnectionsWith(Arrays.asList(dev2, dev4));
        assertTwoWayConnection(newDev, dev2);
        assertTwoWayConnection(newDev, dev4);
        assertNoConnection(newDev, dev1);
    }
    
    @Test
    public void testSetOutEdgesMultipleVerticesIterable() {
        // Add the new vertex, connect it to dev1 and verify the connection
        testAddVertexBothVertexTypedEdgeTyped();
        ComputerVertex newDev = findDeviceByName(graph, newDevName);
        // Now make it linked only to devices 2 and 4
        newDev.setOutConnectionsWith(Arrays.asList(dev2, dev4));
        assertOneWayExclusiveConnection(dev1, newDev);
        assertOneWayExclusiveConnection(newDev, dev2);
        assertOneWayExclusiveConnection(newDev, dev4);
    }
    
    @Test
    public void testSetBothEdgesSingleVertex() {
        // Add the new vertex, connect it to dev1 and verify the connection
        testAddVertexBothVertexTypedEdgeTyped();
        ComputerVertex newDev = findDeviceByName(graph, newDevName);
        // Now make it linked only to devices 2
        newDev.setTwoWayConnectionWith(dev2);
        assertNoConnection(dev1, newDev);
        assertTwoWayConnection(newDev, dev2);
    }
    
    @Test
    public void testSetOutEdgeSingleVertex() {
        // Add the new vertex, connect it to dev1 and verify the connection
        testAddVertexBothVertexTypedEdgeTyped();
        ComputerVertex newDev = findDeviceByName(graph, newDevName);
        // Now make it linked only to devices 2
        newDev.setOutConnectionWith(dev2);
        assertOneWayExclusiveConnection(dev1, newDev);
        assertOneWayExclusiveConnection(newDev, dev2);
    }
    
    @Test
    public void testRemoveBothEdgesSingleVertex() {
        dev1.disconnectWithDevice(dev2);
        assertNoConnection(dev1, dev2);
        assertTwoWayConnection(dev1, dev3);
        assertTwoWayConnection(dev1, dev4);
        assertTwoWayConnection(dev2, dev5);
    }
    
    @Test
    public void testRemoveOutEdgeSingleVertex() {
        dev1.removeOutConnection(dev2);
        assertOneWayExclusiveConnection(dev2, dev1);
        assertTwoWayConnection(dev1, dev3);
        assertTwoWayConnection(dev1, dev4);
        assertTwoWayConnection(dev2, dev5);
    }
    
    @Test
    public void testRemoveAllEdges() {
        dev1.disconnectFromNetwork();
        assertNoConnection(dev1, dev2);
        assertNoConnection(dev1, dev3);
        assertNoConnection(dev1, dev4);
        assertNoConnection(dev1, dev5);
    }
    
    @Test
    public void testRemoveAllOutEdges() {
        dev1.removeOutConnections();
        assertOneWayExclusiveConnection(dev2, dev1);
        assertOneWayExclusiveConnection(dev3, dev1);
        assertOneWayExclusiveConnection(dev4, dev1);
        assertNoConnection(dev1, dev5);
    }
    
}

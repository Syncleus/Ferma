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
package com.syncleus.ferma.annotations;

import com.syncleus.ferma.DelegatingFramedGraph;
import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.graphtypes.network.NetworkConnectionEdge;
import com.syncleus.ferma.graphtypes.network.NetworkGraphLoader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Before;
import com.syncleus.ferma.graphtypes.network.ComputerVertex;

/**
 *
 * @author rqpa
 */
public class NetworkGraphTestHelper {

    protected DelegatingFramedGraph graph;
    protected ComputerVertex dev1;
    protected ComputerVertex dev2;
    protected ComputerVertex dev3;
    protected ComputerVertex dev4;
    protected ComputerVertex dev5;
    
    protected Set<String> dev1OutConnections;
    protected Set<String> dev1InConnections;
    
    @Before
    public void setUp() {
        graph = NetworkGraphLoader.INSTANCE.load();
        
        dev1 = findDeviceByName(graph, "DEV1");
        dev2 = findDeviceByName(graph, "DEV2");
        dev3 = findDeviceByName(graph, "DEV3");
        dev4 = findDeviceByName(graph, "DEV4");
        dev5 = findDeviceByName(graph, "DEV5");
        
        dev1OutConnections = new HashSet<>();
        dev1OutConnections.add(dev2.getName());
        dev1OutConnections.add(dev3.getName());
        dev1OutConnections.add(dev4.getName());
        dev1OutConnections = Collections.unmodifiableSet(dev1OutConnections);
        dev1InConnections = dev1OutConnections;
    }
    
    protected ComputerVertex findDeviceByName(FramedGraph graph, String deviceName) {
        return graph
                .traverse(input -> input.V().has("name", deviceName))
                .nextOrDefault(ComputerVertex.class, null);
    }
    
    protected Set<String> hasOutConnectionsTo(FramedGraph graph, String deviceName) {
        return graph
                .traverse(input -> input.E().hasLabel("connects"))
                .toSetExplicit(NetworkConnectionEdge.class)
                .stream()
                .filter(conn -> conn.getFrom().getName().equals(deviceName))
                .map(NetworkConnectionEdge::getTo)
                .map(ComputerVertex::getName)
                .collect(Collectors.toSet());
    }
    
    protected Set<String> hasInConnectionsFrom(FramedGraph graph, String deviceName) {
        return graph
                .traverse(input -> input.E().hasLabel("connects"))
                .toSetExplicit(NetworkConnectionEdge.class)
                .stream()
                .filter(conn -> conn.getTo().getName().equals(deviceName))
                .map(NetworkConnectionEdge::getFrom)
                .map(ComputerVertex::getName)
                .collect(Collectors.toSet());
    }
    
}

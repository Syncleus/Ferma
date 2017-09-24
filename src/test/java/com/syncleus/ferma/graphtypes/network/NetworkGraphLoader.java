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
package com.syncleus.ferma.graphtypes.network;

import com.syncleus.ferma.DelegatingFramedGraph;
import com.syncleus.ferma.FramedGraph;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

/**
 *
 * @author rqpa
 */
public class NetworkGraphLoader {
    
    public static final NetworkGraphLoader INSTANCE = new NetworkGraphLoader();
    
    private static final String CONNECTS_LABEL = "connects";
    private static final Set<Class<?>> NETWORK_GRAPH_TYPES = new HashSet<>(Arrays.asList(
            ComputerVertex.class,
            NetworkConnectionEdge.class
    ));
    
    private NetworkGraphLoader() {
        
    }
    
    /**
     * Constructs network graph with the following devices and connections.
     * All connections are bidirectional.
     * There are 5 devices named DEV1 to DEV5
     * DEV1 is connected to DEV2, DEV3, DEV4
     * DEV5 is connected to DEV2, DEV3, DEV4
     * @return 
     */
    public DelegatingFramedGraph<?> load() {
        final DelegatingFramedGraph<TinkerGraph> framedGraph = new DelegatingFramedGraph(TinkerGraph.open(), true, NETWORK_GRAPH_TYPES);
        
        ComputerVertex dev1 = makeDevice(framedGraph, "DEV1");
        ComputerVertex dev2 = makeDevice(framedGraph, "DEV2");
        ComputerVertex dev3 = makeDevice(framedGraph, "DEV3");
        ComputerVertex dev4 = makeDevice(framedGraph, "DEV4");
        ComputerVertex dev5 = makeDevice(framedGraph, "DEV5");
        
        makeTwoWayConnection(framedGraph, dev1, dev2);
        makeTwoWayConnection(framedGraph, dev1, dev3);
        makeTwoWayConnection(framedGraph, dev1, dev4);
        
        makeTwoWayConnection(framedGraph, dev5, dev2);
        makeTwoWayConnection(framedGraph, dev5, dev3);
        makeTwoWayConnection(framedGraph, dev5, dev4);
        
        return framedGraph;
    }
    
    private ComputerVertex makeDevice(FramedGraph graph, String name) {
        ComputerVertex dev = graph.addFramedVertexExplicit(ComputerVertex.class);
        dev.setName(name);
        return dev;
    }
    
    private void makeTwoWayConnection(FramedGraph graph, ComputerVertex dev1, ComputerVertex dev2) {
        graph.addFramedEdge(dev1, dev2, CONNECTS_LABEL, NetworkConnectionEdge.class);
        graph.addFramedEdge(dev2, dev1, CONNECTS_LABEL, NetworkConnectionEdge.class);
    }
}

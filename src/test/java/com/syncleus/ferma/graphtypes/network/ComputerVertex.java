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

import com.syncleus.ferma.ClassInitializer;
import com.syncleus.ferma.DefaultClassInitializer;
import com.syncleus.ferma.VertexFrame;
import com.syncleus.ferma.annotations.Adjacency;
import com.syncleus.ferma.annotations.Property;
import java.util.Iterator;
import org.apache.tinkerpop.gremlin.structure.Direction;

/**
 *
 * @author rqpa
 */
public interface ComputerVertex extends NetworkDeviceVertex {
    
    public static final ClassInitializer<ComputerVertex> DEFAULT_INITIALIZER = new DefaultClassInitializer<>(ComputerVertex.class);
    
    @Property(value = "name")
    @Override
    String getName();
    
    @Property(value = "name")
    @Override
    void setName(String name);
    
    @Adjacency(label = "connects", direction = Direction.BOTH)
    VertexFrame addAndConnectBothDefault();
    
    @Adjacency(label = "connects", direction = Direction.OUT)
    VertexFrame addAndConnectOutDefault();
    
    @Adjacency(label = "connects", direction = Direction.BOTH)
    <V extends ComputerVertex> V addAndConnectBothVertexTypedEdgeDefault(ClassInitializer<V> vertexInitializer);
    
    @Adjacency(label = "connects", direction = Direction.OUT)
    <V extends ComputerVertex> V  addAndConnectOutVertexTypedEdgeDefault(ClassInitializer<V> vertexInitializer);
    
    @Adjacency(label = "connects", direction = Direction.BOTH)
    <V extends ComputerVertex, E extends NetworkConnectionEdge> V addAndConnectBothVertexTypedEdgeTyped(
            ClassInitializer<V> vertexInitializer,
            ClassInitializer<E> edgeInitializer);
    
    @Adjacency(label = "connects", direction = Direction.OUT)
    <V extends ComputerVertex, E extends NetworkConnectionEdge> V addAndConnectOutVertexTypedEdgeTyped(
            ClassInitializer<V> vertexInitializer,
            ClassInitializer<E> edgeInitializer);
    
    @Adjacency(label = "connects", direction = Direction.BOTH)
    <V extends NetworkDeviceVertex> V addAndConnectBoth(V vertex);
    
    @Adjacency(label = "connects", direction = Direction.OUT)
    <V extends NetworkDeviceVertex> V addAndConnectOut(V vertex);
    
    @Adjacency(label = "connects", direction = Direction.BOTH)
    <V extends NetworkDeviceVertex, E extends NetworkConnectionEdge> V addAndConnectBothTypedEdge(
            V vertex,
            ClassInitializer<E> edgeInitializer);
    
    @Adjacency(label = "connects", direction = Direction.OUT)
    <V extends NetworkDeviceVertex, E extends NetworkConnectionEdge> V addAndConnectOutTypedEdge(
            V vertex,
            ClassInitializer<E> edgeInitializer);
    
    @Adjacency(label = "connects", direction = Direction.BOTH)
    <T extends NetworkDeviceVertex> void setTwoWayConnectionsWithIterator(Iterator<T> connectees);
    
    @Adjacency(label = "connects", direction = Direction.BOTH)
    <T extends NetworkDeviceVertex> void setTwoWayConnectionsWithIterable(Iterable<T> connectees);
    
    @Adjacency(label = "connects", direction = Direction.OUT)
    <T extends NetworkDeviceVertex> void setOutConnectionsWith(Iterable<T> connectees);
    
    @Adjacency(label = "connects", direction = Direction.BOTH)
    <T extends NetworkDeviceVertex> void setTwoWayConnectionWith(T connectee);
    
    @Adjacency(label = "connects", direction = Direction.OUT)
    <T extends NetworkDeviceVertex> void setOutConnectionWith(T connectee);
    
    @Adjacency(label = "connects", direction = Direction.BOTH, operation = Adjacency.Operation.REMOVE)
    <T extends NetworkDeviceVertex> void disconnectWithDevice(T toDisconnectWith);
    
    @Adjacency(label = "connects", direction = Direction.OUT, operation = Adjacency.Operation.REMOVE)
    <T extends NetworkDeviceVertex> void removeOutConnection(T toDisconnectWith);
    
    @Adjacency(label = "connects", direction = Direction.BOTH, operation = Adjacency.Operation.REMOVE)
    <T extends NetworkDeviceVertex> void disconnectFromNetwork();
    
    @Adjacency(label = "connects", direction = Direction.OUT, operation = Adjacency.Operation.REMOVE)
    <T extends NetworkDeviceVertex> void removeOutConnections();
    
}

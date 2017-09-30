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
package com.syncleus.ferma.graphtypes.javaclass;

import com.syncleus.ferma.AbstractEdgeFrame;
import com.syncleus.ferma.ClassInitializer;
import com.syncleus.ferma.EdgeFrame;
import com.syncleus.ferma.TEdge;
import com.syncleus.ferma.VertexFrame;
import com.syncleus.ferma.annotations.Adjacency;
import com.syncleus.ferma.annotations.Incidence;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.tinkerpop.gremlin.structure.Direction;

/**
 *
 * @author rqpa
 */
public interface JavaClassVertex extends JavaTypeVertex {

    public static final ClassInitializer<JavaClassVertex> DEFAULT_INITIALIZER = new JavaTypeVertexInitializer<>(JavaClassVertex.class);
    
    @Incidence(label = "extends", direction = Direction.OUT)
    public <T extends ExtendsEdge> T getSuperClassEdge(Class<T> ofType);
    
    @Incidence(label = "extends", direction = Direction.OUT)
    public AbstractEdgeFrame getSuperClassTEdge();
    
    // Makes sense to use it with singleton iterator only
    @Adjacency(label = "extends", direction = Direction.OUT)
    public void setSuperClasses(Iterator<JavaClassVertex> superClasses);
    
    @Incidence(label = "implements", direction = Direction.OUT, operation = Incidence.Operation.ADD)
    public TEdge implementNewInterface();
    
    @Incidence(label = "implements", direction = Direction.OUT, operation = Incidence.Operation.ADD)
    public TEdge implementNewType(ClassInitializer<? extends JavaInterfaceVertex> typeInitializer);
    
    @Incidence(label = "implements", direction = Direction.OUT, operation = Incidence.Operation.ADD)
    TEdge implementInterface(JavaInterfaceVertex iface);
    
    @Incidence(label = "implements", direction = Direction.OUT, operation = Incidence.Operation.ADD)
    <T extends ImplementsEdge> T addImplementsEdge(JavaInterfaceVertex toImplement, ClassInitializer<T> edgeInitializer);
    
    @Incidence(label = "implements", direction = Direction.OUT, operation = Incidence.Operation.GET)
    Iterator<EdgeFrame> getImplementedInterfaceEdgeFrames();
    
    @Incidence(label = "implements", direction = Direction.OUT, operation = Incidence.Operation.GET)
    List<EdgeFrame> getImplementedInterfaceEdgeFramesList();
    
    @Incidence(label = "implements", direction = Direction.OUT, operation = Incidence.Operation.GET)
    Set<EdgeFrame> getImplementedInterfaceEdgeFramesSet();
    
    @Incidence(label = "implements", direction = Direction.OUT, operation = Incidence.Operation.GET)
    <T extends ImplementsEdge> Iterator<T> getImplementedInterfaceEdgeFrames(Class<T> edgeType);
    
    @Incidence(label = "implements", direction = Direction.OUT, operation = Incidence.Operation.GET)
    <T extends ImplementsEdge> List<T> getImplementedInterfaceEdgeFramesList(Class<T> edgeType);
    
    @Incidence(label = "implements", direction = Direction.OUT, operation = Incidence.Operation.GET)
    <T extends ImplementsEdge> Set<T> getImplementedInterfaceEdgeFramesSet(Class<T> edgeType);
    
    
    
    @Incidence(label = "extends", direction = Direction.BOTH, operation = Incidence.Operation.GET)
    Iterator<EdgeFrame> getBothDirectionsExtendEdgeFrames();
    
    @Incidence(label = "extends", direction = Direction.BOTH, operation = Incidence.Operation.GET)
    List<EdgeFrame> getBothDirectionsExtendEdgeFramesList();
    
    @Incidence(label = "extends", direction = Direction.BOTH, operation = Incidence.Operation.GET)
    Set<EdgeFrame> getBothDirectionsExtendEdgeFramesSet();
    
    @Incidence(label = "extends", direction = Direction.BOTH, operation = Incidence.Operation.GET)
    <T extends ExtendsEdge> Iterator<T> getBothDirectionsExtendEdgeFrames(Class<T> edgeType);
    
    @Incidence(label = "extends", direction = Direction.BOTH, operation = Incidence.Operation.GET)
    <T extends ExtendsEdge> List<T> getBothDirectionsExtendEdgeFramesList(Class<T> edgeType);
    
    @Incidence(label = "extends", direction = Direction.BOTH, operation = Incidence.Operation.GET)
    <T extends ExtendsEdge> Set<T> getBothDirectionsExtendEdgeFramesSet(Class<T> edgeType);
    
    
    
    @Adjacency(label = "implements", direction = Direction.OUT, operation = Adjacency.Operation.GET) 
    Iterator<VertexFrame> getImplementedInterfacesVertexFrames();
    @Adjacency(label = "implements", direction = Direction.BOTH, operation = Adjacency.Operation.GET) 
    Iterator<VertexFrame> getImplementedInterfacesVertexFramesBoth();
    @Adjacency(label = "implements", direction = Direction.BOTH, operation = Adjacency.Operation.GET) 
    <V extends JavaInterfaceVertex> Iterator<V> getImplementedInterfacesBoth(Class<V> types);

    @Adjacency(label = "implements", direction = Direction.OUT, operation = Adjacency.Operation.GET) 
    <V extends JavaInterfaceVertex> List<V> getImplementedInterfacesList(Class<V> types);
    @Adjacency(label = "implements", direction = Direction.BOTH, operation = Adjacency.Operation.GET) 
    List<VertexFrame> getImplementedInterfacesVertexFramesBothList();
    @Adjacency(label = "implements", direction = Direction.BOTH, operation = Adjacency.Operation.GET) 
    <V extends JavaInterfaceVertex> List<V> getImplementedInterfacesBothList(Class<V> types);
    
    @Adjacency(label = "implements", direction = Direction.OUT, operation = Adjacency.Operation.GET) 
    Set<VertexFrame> getImplementedInterfacesVertexFramesSet();
    @Adjacency(label = "implements", direction = Direction.OUT, operation = Adjacency.Operation.GET) 
    <V extends JavaInterfaceVertex> Set<V> getImplementedInterfacesSet(Class<V> types);
    @Adjacency(label = "implements", direction = Direction.BOTH, operation = Adjacency.Operation.GET) 
    Set<VertexFrame> getImplementedInterfacesVertexFramesBothSet();
    @Adjacency(label = "implements", direction = Direction.BOTH, operation = Adjacency.Operation.GET) 
    <V extends JavaInterfaceVertex> Set<V> getImplementedInterfacesBothSet(Class<V> types);
    
    
    @Adjacency(label = "implements", direction = Direction.OUT, operation = Adjacency.Operation.GET) 
    VertexFrame getAnyImplementedInterface();
    @Adjacency(label = "implements", direction = Direction.OUT, operation = Adjacency.Operation.GET) 
    <V extends JavaInterfaceVertex> V getAnyImplementedInterface(Class<V> type);
    @Adjacency(label = "implements", direction = Direction.BOTH, operation = Adjacency.Operation.GET) 
    VertexFrame getAnyImplementedInterfaceBoth();
    @Adjacency(label = "implements", direction = Direction.BOTH, operation = Adjacency.Operation.GET) 
    <V extends JavaInterfaceVertex> V getAnyImplementedInterfaceBoth(Class<V> type);
}

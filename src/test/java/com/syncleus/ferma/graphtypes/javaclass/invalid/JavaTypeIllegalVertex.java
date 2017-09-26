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
package com.syncleus.ferma.graphtypes.javaclass.invalid;

import com.syncleus.ferma.ClassInitializer;
import com.syncleus.ferma.EdgeFrame;
import com.syncleus.ferma.annotations.Incidence;
import com.syncleus.ferma.graphtypes.javaclass.ExtendsEdge;
import com.syncleus.ferma.graphtypes.javaclass.ImplementsEdge;
import com.syncleus.ferma.graphtypes.javaclass.JavaClassVertex;
import com.syncleus.ferma.graphtypes.javaclass.JavaInterfaceVertex;
import com.syncleus.ferma.graphtypes.javaclass.JavaTypeVertex;
import com.syncleus.ferma.graphtypes.javaclass.JavaTypeVertexInitializer;
import org.apache.tinkerpop.gremlin.structure.Direction;

/**
 * Vertex that contains illegal operations determined in runtime
 * 
 * @author rqpa
 */
public interface JavaTypeIllegalVertex extends JavaTypeVertex {
    public static final ClassInitializer<JavaTypeIllegalVertex> DEFAULT_INITIALIZER = new JavaTypeVertexInitializer<>(JavaTypeIllegalVertex.class);
    
    @Incidence(label = "implements", direction = Direction.BOTH)
    public void addBidirectionalEdgeDefault();
    
    @Incidence(label = "implements", direction = Direction.BOTH)
    public void addImplementsTwoDirectionsEdge(ClassInitializer<? extends JavaInterfaceVertex> vertexInitializer);
    
    @Incidence(label = "implements", direction = Direction.BOTH)
    public void addImplementsInterfaceTwoDirectionsEdge(
            ClassInitializer<? extends JavaInterfaceVertex> ifaceInitializer,
            ClassInitializer<? extends ImplementsEdge> edgeInitializer);
    
    @Incidence(label = "extends", direction = Direction.BOTH)
    public void addExtendsBothDirections(JavaClassVertex other);
    
    @Incidence(label = "extends", direction = Direction.BOTH)
    public void addExtendsBothDirections(JavaClassVertex other, ClassInitializer<? extends ExtendsEdge> edgeInitializer);
    
}

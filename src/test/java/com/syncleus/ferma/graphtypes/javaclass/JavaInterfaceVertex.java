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
package com.syncleus.ferma.graphtypes.javaclass;

import com.syncleus.ferma.ClassInitializer;
import com.syncleus.ferma.EdgeFrame;
import com.syncleus.ferma.VertexFrame;
import com.syncleus.ferma.annotations.Incidence;
import java.util.Iterator;
import org.apache.tinkerpop.gremlin.structure.Direction;

/**
 *
 * @author rqpa
 */
public interface JavaInterfaceVertex extends JavaTypeVertex {
    
    public static final ClassInitializer<JavaInterfaceVertex> DEFAULT_INITIALIZER = new JavaTypeVertexInitializer<>(JavaInterfaceVertex.class);
    
    @Incidence(label = "implements", direction = Direction.BOTH, operation = Incidence.Operation.GET)
    EdgeFrame getAnyImplementsEdge();
    
    @Incidence(label = "implements", direction = Direction.BOTH, operation = Incidence.Operation.GET)
    <T extends ImplementsEdge> T getAnyImplementsEdge(Class<T> edgeType);
    
    
    @Incidence(label = "implements", direction = Direction.IN, operation = Incidence.Operation.GET)
    Iterator<VertexFrame> getImplementors();
    
    @Incidence(label = "implements", direction = Direction.IN, operation = Incidence.Operation.GET)
    <V extends JavaClassVertex> Iterator<V> getImplementors(Class<V> type);
}

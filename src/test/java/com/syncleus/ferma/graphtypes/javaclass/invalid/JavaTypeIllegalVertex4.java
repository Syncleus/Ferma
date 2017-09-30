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
package com.syncleus.ferma.graphtypes.javaclass.invalid;

import com.syncleus.ferma.ClassInitializer;
import com.syncleus.ferma.annotations.Incidence;
import com.syncleus.ferma.graphtypes.javaclass.ImplementsEdge;
import com.syncleus.ferma.graphtypes.javaclass.JavaInterfaceVertex;
import com.syncleus.ferma.graphtypes.javaclass.JavaTypeVertex;
import com.syncleus.ferma.graphtypes.javaclass.JavaTypeVertexInitializer;
import java.util.Collection;
import org.apache.tinkerpop.gremlin.structure.Direction;

/**
 * Vertex that contains a single illegally annotated method
 * 
 * @author rqpa
 */
public interface JavaTypeIllegalVertex4 extends JavaTypeVertex {
    public static final ClassInitializer<JavaTypeIllegalVertex4> DEFAULT_INITIALIZER = new JavaTypeVertexInitializer<>(JavaTypeIllegalVertex4.class);
    
    @Incidence(label = "implements", direction = Direction.OUT)
    public Collection<ImplementsEdge> getImplementsEdges(JavaInterfaceVertex implementedIface1, JavaInterfaceVertex implementedIface2);
}

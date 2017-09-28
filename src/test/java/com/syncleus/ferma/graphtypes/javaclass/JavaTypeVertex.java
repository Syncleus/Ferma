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
import com.syncleus.ferma.annotations.Incidence;
import com.syncleus.ferma.annotations.Property;
import org.apache.tinkerpop.gremlin.structure.Direction;

/**
 *
 * @author rqpa
 */
public interface JavaTypeVertex {
    
    @Property(value = "fqn", operation = Property.Operation.GET)
    String getFullyQualifiedName();
    
    @Property(value = "fqn")
    void setFullyQualifiedName(String fqn);
    
    @Property(value = "accessModifier")
    JavaAccessModifier getAccessModifier();
    
    @Property(value = "accessModifier")
    void setAccessModifier(JavaAccessModifier accessModifier);
    
    @Incidence(label = "related", direction = Direction.OUT, operation = Incidence.Operation.ADD)
    <EdgeType extends JavaTypeRelationsEdge, VertexType extends JavaTypeVertex> EdgeType createTypeWithRelation(
            ClassInitializer<VertexType> vertexInitializer,
            ClassInitializer<EdgeType> edgeInitializer
    );
    
}

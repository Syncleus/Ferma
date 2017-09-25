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

import com.syncleus.ferma.AbstractEdgeFrame;
import com.syncleus.ferma.ClassInitializer;
import com.syncleus.ferma.GenericClassInitializer;
import com.syncleus.ferma.TEdge;
import com.syncleus.ferma.annotations.Adjacency;
import com.syncleus.ferma.annotations.Incidence;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.tinkerpop.gremlin.structure.Direction;

/**
 *
 * @author rqpa
 */
public interface JavaClassVertex extends JavaTypeVertex {

    public static final ClassInitializer<JavaClassVertex> DEFAULT_INITIALIZER = new GenericClassInitializer<>(
            JavaClassVertex.class, new HashMap<>());
    
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
}

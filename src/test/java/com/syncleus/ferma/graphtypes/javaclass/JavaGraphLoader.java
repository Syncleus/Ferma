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

import com.syncleus.ferma.DelegatingFramedGraph;
import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.typeresolvers.PolymorphicTypeResolver;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

/**
 *
 * @author rqpa
 */
public class JavaGraphLoader {
     
    private static final Set<Class<?>> JAVA_TYPE_TYPES = new HashSet<>(Arrays.asList(
            ExtendsEdge.class, 
            ImplementsEdge.class, 
            JavaClassVertex.class,
            JavaInterfaceVertex.class));
    public static final JavaGraphLoader INSTANCE = new JavaGraphLoader();
    protected final static String TYPE_RESOLUTION_KEY = PolymorphicTypeResolver.TYPE_RESOLUTION_KEY;
    
    protected JavaGraphLoader() {
        // Singleton
    }
    
    public FramedGraph load() {
        Graph graph = TinkerGraph.open();
        final FramedGraph framedGraph = new DelegatingFramedGraph(graph, true, JAVA_TYPE_TYPES);

        // Interfaces
        Vertex collection = makeInterface(graph, Collection.class);
        Vertex list = makeInterface(graph, List.class);
        
        // Classes
        Vertex obj = makeClass(graph, null, Object.class);
        Vertex abstrCollection = makeClass(graph, obj, AbstractCollection.class, collection);
        Vertex abstrList = makeClass(graph, abstrCollection, AbstractList.class, list);
        Vertex abstrSeqList = makeClass(graph, abstrList, AbstractSequentialList.class, list);
        makeClass(graph, abstrList, ArrayList.class, list);
        makeClass(graph, abstrSeqList, LinkedList.class, list, collection);
        
        return framedGraph;
    }
    
    private Vertex makeInterface(Graph graph, Class<?> javaInterface) {
        return makeType(graph, JavaInterfaceVertex.class, javaInterface, JavaAccessModifier.PUBLIC);
    }
    
    private Vertex makeClass(Graph graph, Vertex superClass, Class<?> javaClass, Vertex... implementedInterfaces) {
        Vertex classVertex = makeType(graph, JavaClassVertex.class, javaClass, JavaAccessModifier.PUBLIC);
        
        for (Vertex implInterface : implementedInterfaces) {
            classVertex.addEdge("implements", implInterface, TYPE_RESOLUTION_KEY, ImplementsEdge.class.getName());
        }
        
        if (superClass != null) {
            classVertex.addEdge("extends", superClass, TYPE_RESOLUTION_KEY, ExtendsEdge.class.getName());
        }
        return classVertex;
    }
    
    private <T extends JavaTypeVertex> Vertex makeType(Graph graph, Class<T> vertexType, Class<?> javaType, JavaAccessModifier accessModifier) {
        Vertex type = graph.addVertex(
                TYPE_RESOLUTION_KEY, vertexType.getName(),
                "fqn", javaType.getName(),
                "accessModifier", accessModifier.name());
        return type;
    }
}

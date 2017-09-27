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

import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.graphtypes.javaclass.JavaClassVertex;
import com.syncleus.ferma.graphtypes.javaclass.JavaGraphLoader;
import com.syncleus.ferma.graphtypes.javaclass.JavaInterfaceVertex;
import com.syncleus.ferma.graphtypes.javaclass.invalid.JavaTypeIllegalVertex;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.Before;

/**
 *
 * @author rqpa
 */
public class JavaClassGraphTestHelper {
        
    protected FramedGraph javaClassesGraph;
    protected JavaClassVertex abstractList;
    protected JavaClassVertex object;
    protected JavaClassVertex arrayList;
    protected JavaClassVertex linkedList;
    protected JavaInterfaceVertex list;
    protected JavaInterfaceVertex collection;
    protected JavaTypeIllegalVertex illegal;
    
    @Before
    public void setUp() {
        javaClassesGraph = JavaGraphLoader.INSTANCE.load();
        object = findClassVertex(javaClassesGraph, Object.class);
        abstractList = findClassVertex(javaClassesGraph, AbstractList.class);
        arrayList = findClassVertex(javaClassesGraph, ArrayList.class);
        linkedList = findClassVertex(javaClassesGraph, LinkedList.class);
        list = findInterfaceVertex(javaClassesGraph, List.class);
        collection = findInterfaceVertex(javaClassesGraph, Collection.class);
        illegal = javaClassesGraph.addFramedVertex(JavaTypeIllegalVertex.class);
    }
    
    @After
    public void tearDown() throws IOException {
        javaClassesGraph.close();
    }
    
    protected JavaInterfaceVertex findInterfaceVertex(FramedGraph graph, Class<?> javaClass) {
        return findVertex(graph, "fqn", javaClass.getName(), JavaInterfaceVertex.class);
    }
    
    protected JavaClassVertex findClassVertex(FramedGraph graph, Class<?> javaClass) {
        return findVertex(graph, "fqn", javaClass.getName(), JavaClassVertex.class);
    }
    
    protected <VertexType, PropertyType> VertexType findVertex(FramedGraph g, String propName, PropertyType propValue, Class<VertexType> itemType) {
        List<? extends VertexType> items = g.traverse(
                input -> input.V().has(propName, propValue)).toList(itemType);

        return items.isEmpty() ? null : items.get(0);
    }
}

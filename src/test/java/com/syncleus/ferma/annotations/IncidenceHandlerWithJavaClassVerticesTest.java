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

import com.syncleus.ferma.AbstractEdgeFrame;
import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.TEdge;
import com.syncleus.ferma.graphtypes.javaclass.ExtendsEdge;
import com.syncleus.ferma.graphtypes.javaclass.JavaClassVertex;
import com.syncleus.ferma.graphtypes.javaclass.JavaGraphFactory;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author rqpa
 */
public class IncidenceHandlerWithJavaClassVerticesTest {
    
    @Test
    public void testGetOutEdgeByType() {
        FramedGraph graph = JavaGraphFactory.INSTANCE.load();
        JavaClassVertex list = findVertex(graph, "fqn", ArrayList.class.getName(), JavaClassVertex.class);
        ExtendsEdge actualListParentEdge = list.getSuperClassEdge(ExtendsEdge.class);
        Assert.assertNotNull(actualListParentEdge);
        Assert.assertEquals(ArrayList.class.getName(), actualListParentEdge.getSubClass().getFullyQualifiedName());
        Assert.assertEquals(AbstractList.class.getName(), actualListParentEdge.getSuperClass().getFullyQualifiedName());
    }
    
    @Test
    public void testGetOutEdgeDefault() {
        FramedGraph graph = JavaGraphFactory.INSTANCE.load();
        JavaClassVertex list = findVertex(graph, "fqn", ArrayList.class.getName(), JavaClassVertex.class);
        AbstractEdgeFrame actualListParentTEdge = list.getSuperClassTEdge();
        ExtendsEdge actualListParentEdge = actualListParentTEdge.reframe(ExtendsEdge.class);
        Assert.assertNotNull(actualListParentEdge);
        Assert.assertEquals(ArrayList.class.getName(), actualListParentEdge.getSubClass().getFullyQualifiedName());
        Assert.assertEquals(AbstractList.class.getName(), actualListParentEdge.getSuperClass().getFullyQualifiedName());
    }
    
    @Test
    public void testSetOutEdge() {
        FramedGraph graph = JavaGraphFactory.INSTANCE.load();
        JavaClassVertex obj = findClassVertex(graph, Object.class);
        JavaClassVertex list = findClassVertex(graph, ArrayList.class);
        list.setSuperClasses(Collections.singleton(obj).iterator());
        
        AbstractEdgeFrame listParentTEdge = list.getSuperClassTEdge();
        ExtendsEdge listParentEdge = listParentTEdge.reframe(ExtendsEdge.class);
        Assert.assertNotNull(listParentEdge);
        Assert.assertEquals(ArrayList.class.getName(), listParentEdge.getSubClass().getFullyQualifiedName());
        Assert.assertEquals(Object.class.getName(), listParentEdge.getSuperClass().getFullyQualifiedName());
    }
    
    private JavaClassVertex findClassVertex(FramedGraph graph, Class<?> javaClass) {
        return findVertex(graph, "fqn", javaClass.getName(), JavaClassVertex.class);
    }
    
    private <VertexType, PropertyType> VertexType findVertex(FramedGraph g, String propName, PropertyType propValue, Class<VertexType> itemType) {
        List<? extends VertexType> items = g.traverse(
                input -> input.V().has(propName, propValue)).toList(itemType);

        return items.isEmpty() ? null : items.get(0);
    }
}

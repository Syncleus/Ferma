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
import com.syncleus.ferma.graphtypes.javaclass.ImplementsEdge;
import com.syncleus.ferma.graphtypes.javaclass.JavaClassVertex;
import com.syncleus.ferma.graphtypes.javaclass.JavaGraphFactory;
import com.syncleus.ferma.graphtypes.javaclass.JavaInterfaceVertex;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
    
    @Test
    public void testAddEdgeDefault() {
        FramedGraph graph = JavaGraphFactory.INSTANCE.load();
        JavaClassVertex list = findClassVertex(graph, ArrayList.class);
        TEdge implTedge = list.implementNewInterface();
        ImplementsEdge implEdge = implTedge.reframe(ImplementsEdge.class);
        implEdge.getInterface().setFullyQualifiedName(Iterable.class.getName());
        JavaInterfaceVertex iterable = findInterfaceVertex(graph, Iterable.class);
        Assert.assertNotNull(iterable);
        Assert.assertEquals(Iterable.class.getName(), iterable.getFullyQualifiedName());
    }
    
    @Test
    public void testAddUntypedEdgeTypedVertex() {
        FramedGraph graph = JavaGraphFactory.INSTANCE.load();
        JavaClassVertex list = findClassVertex(graph, ArrayList.class);
        TEdge implTedge = list.implementNewType(JavaInterfaceVertex.DEFAULT_INITIALIZER);
        ImplementsEdge implEdge = implTedge.reframe(ImplementsEdge.class);
        implEdge.getInterface().setFullyQualifiedName(Iterable.class.getName());
        JavaInterfaceVertex iterable = findInterfaceVertex(graph, Iterable.class);
        Assert.assertNotNull(iterable);
        Assert.assertEquals(Iterable.class.getName(), iterable.getFullyQualifiedName());
    }
    
    @Test
    public void testAddTypedEdgeTypedVertex() {
        FramedGraph graph = JavaGraphFactory.INSTANCE.load();
        JavaClassVertex list = findClassVertex(graph, ArrayList.class);
        ImplementsEdge implEdge = list.createTypeWithRelation(JavaInterfaceVertex.DEFAULT_INITIALIZER, ImplementsEdge.DEFAULT_INITIALIZER);
        implEdge.getInterface().setFullyQualifiedName(Iterable.class.getName());
        JavaInterfaceVertex iterable = findInterfaceVertex(graph, Iterable.class);
        Assert.assertNotNull(iterable);
        Assert.assertEquals(Iterable.class.getName(), iterable.getFullyQualifiedName());
    }
    
    @Test
    public void testAddEdgeByObjectUntypedEdge() {
        FramedGraph graph = JavaGraphFactory.INSTANCE.load();
        JavaClassVertex list = findClassVertex(graph, ArrayList.class);
        JavaInterfaceVertex iterable = graph.addFramedVertex(JavaInterfaceVertex.class);
        iterable.setFullyQualifiedName(Iterable.class.getName());
        list.implementInterface(iterable);
        
        List<JavaClassVertex> iterableImplementors = findImplementors(graph, Iterable.class);
        Assert.assertEquals(1, iterableImplementors.size());
        JavaClassVertex actualIterableImplementor = iterableImplementors.get(0);
        Assert.assertEquals(ArrayList.class.getName(), actualIterableImplementor.getFullyQualifiedName());
    }
    
    private List<JavaClassVertex> findImplementors(FramedGraph graph, Class<?> ifaceClass) {
        List<? extends ImplementsEdge> implEdges = graph.traverse(
                input -> input.E().hasLabel("implements")).toList(ImplementsEdge.class);
        
        return implEdges.stream()
                .filter(edge -> edge.getInterface().getFullyQualifiedName().equals(ifaceClass.getName()))
                .map(ImplementsEdge::getImplementor)
                .collect(Collectors.toList());
    }
    
    private JavaInterfaceVertex findInterfaceVertex(FramedGraph graph, Class<?> javaClass) {
        return findVertex(graph, "fqn", javaClass.getName(), JavaInterfaceVertex.class);
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

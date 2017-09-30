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
package com.syncleus.ferma.annotations;

import com.syncleus.ferma.AbstractEdgeFrame;
import com.syncleus.ferma.EdgeFrame;
import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.TEdge;
import com.syncleus.ferma.graphtypes.javaclass.ExtendsEdge;
import com.syncleus.ferma.graphtypes.javaclass.ImplementsEdge;
import com.syncleus.ferma.graphtypes.javaclass.JavaClassVertex;
import com.syncleus.ferma.graphtypes.javaclass.JavaInterfaceVertex;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author rqpa
 */
public class IncidenceHandlerWithJavaClassVerticesTest extends JavaClassGraphTestHelper {
    
    @Test
    public void testGetOutEdgeByType() {
        ExtendsEdge actualListParentEdge = arrayList.getSuperClassEdge(ExtendsEdge.class);
        Assert.assertNotNull(actualListParentEdge);
        Assert.assertEquals(ArrayList.class.getName(), actualListParentEdge.getSubClass().getFullyQualifiedName());
        Assert.assertEquals(AbstractList.class.getName(), actualListParentEdge.getSuperClass().getFullyQualifiedName());
    }
    
    @Test
    public void testGetOutEdgeDefault() {
        AbstractEdgeFrame actualListParentTEdge = arrayList.getSuperClassTEdge();
        ExtendsEdge actualListParentEdge = actualListParentTEdge.reframe(ExtendsEdge.class);
        Assert.assertNotNull(actualListParentEdge);
        Assert.assertEquals(ArrayList.class.getName(), actualListParentEdge.getSubClass().getFullyQualifiedName());
        Assert.assertEquals(AbstractList.class.getName(), actualListParentEdge.getSuperClass().getFullyQualifiedName());
    }
    
    @Test
    public void testSetOutEdge() {
        arrayList.setSuperClasses(Collections.singleton(object).iterator());
        
        AbstractEdgeFrame listParentTEdge = arrayList.getSuperClassTEdge();
        ExtendsEdge listParentEdge = listParentTEdge.reframe(ExtendsEdge.class);
        Assert.assertNotNull(listParentEdge);
        Assert.assertEquals(ArrayList.class.getName(), listParentEdge.getSubClass().getFullyQualifiedName());
        Assert.assertEquals(Object.class.getName(), listParentEdge.getSuperClass().getFullyQualifiedName());
    }
    
    @Test
    public void testAddEdgeDefault() {
        TEdge implTedge = arrayList.implementNewInterface();
        ImplementsEdge implEdge = implTedge.reframe(ImplementsEdge.class);
        implEdge.getInterface().setFullyQualifiedName(Iterable.class.getName());
        JavaInterfaceVertex iterable = findInterfaceVertex(javaClassesGraph, Iterable.class);
        Assert.assertNotNull(iterable);
        Assert.assertEquals(Iterable.class.getName(), iterable.getFullyQualifiedName());
    }
    
    @Test
    public void testAddUntypedEdgeTypedVertex() {
        TEdge implTedge = arrayList.implementNewType(JavaInterfaceVertex.DEFAULT_INITIALIZER);
        ImplementsEdge implEdge = implTedge.reframe(ImplementsEdge.class);
        implEdge.getInterface().setFullyQualifiedName(Iterable.class.getName());
        JavaInterfaceVertex iterable = findInterfaceVertex(javaClassesGraph, Iterable.class);
        Assert.assertNotNull(iterable);
        Assert.assertEquals(Iterable.class.getName(), iterable.getFullyQualifiedName());
    }
    
    @Test
    public void testAddTypedEdgeTypedVertex() {
        ImplementsEdge implEdge = arrayList.createTypeWithRelation(JavaInterfaceVertex.DEFAULT_INITIALIZER, ImplementsEdge.DEFAULT_INITIALIZER);
        implEdge.getInterface().setFullyQualifiedName(Iterable.class.getName());
        JavaInterfaceVertex iterable = findInterfaceVertex(javaClassesGraph, Iterable.class);
        Assert.assertNotNull(iterable);
        Assert.assertEquals(Iterable.class.getName(), iterable.getFullyQualifiedName());
    }
    
    @Test
    public void testAddEdgeByObjectUntypedEdge() {
        JavaInterfaceVertex iterable = javaClassesGraph.addFramedVertex(JavaInterfaceVertex.class);
        iterable.setFullyQualifiedName(Iterable.class.getName());
        arrayList.implementInterface(iterable);
        
        List<JavaClassVertex> iterableImplementors = findImplementors(javaClassesGraph, Iterable.class);
        Assert.assertEquals(1, iterableImplementors.size());
        JavaClassVertex actualIterableImplementor = iterableImplementors.get(0);
        Assert.assertEquals(ArrayList.class.getName(), actualIterableImplementor.getFullyQualifiedName());
    }
    
    @Test
    public void testAddEdgeByObjectTypedEdge() {
        ImplementsEdge implEdge = arrayList.addImplementsEdge(collection, ImplementsEdge.DEFAULT_INITIALIZER);
        Assert.assertEquals(arrayList.getFullyQualifiedName(), implEdge.getImplementor().getFullyQualifiedName());
        Assert.assertEquals(collection.getFullyQualifiedName(), implEdge.getInterface().getFullyQualifiedName());
        
        List<JavaClassVertex> collectionImplementors = findImplementors(javaClassesGraph, Collection.class);
        boolean arrListIsCollImplementor = collectionImplementors.stream()
                .anyMatch(clz -> clz.getFullyQualifiedName().equals(arrayList.getFullyQualifiedName()));
        Assert.assertTrue(arrListIsCollImplementor);
    }
    
    @Test
    public void testGetEdgesIteratorDefault() {
        Iterator<EdgeFrame> actualLinkedListIfaces = linkedList.getImplementedInterfaceEdgeFrames();
        Iterator<ImplementsEdge> actialEdgesReframed = new ReframingEdgeIterator<>(actualLinkedListIfaces, ImplementsEdge.class);
        assertImplementsEdges(linkedList, getLinkedListExpectedImplIfaces(), actialEdgesReframed);
    }
    
    @Test
    public void testGetEdgesListDefault() {
        Iterator<EdgeFrame> actualLinkedListIfaces = linkedList.getImplementedInterfaceEdgeFramesList().iterator();
        Iterator<ImplementsEdge> actialEdgesReframed = new ReframingEdgeIterator<>(actualLinkedListIfaces, ImplementsEdge.class);
        assertImplementsEdges(linkedList, getLinkedListExpectedImplIfaces(), actialEdgesReframed);
    }
    
    @Test
    public void testGetEdgesSetDefault() {
        Iterator<EdgeFrame> actualLinkedListIfaces = linkedList.getImplementedInterfaceEdgeFramesSet().iterator();
        Iterator<ImplementsEdge> actialEdgesReframed = new ReframingEdgeIterator<>(actualLinkedListIfaces, ImplementsEdge.class);
        assertImplementsEdges(linkedList, getLinkedListExpectedImplIfaces(), actialEdgesReframed);
    }
    
    
    @Test
    public void testGetEdgesIteratorDefaultTyped() {
        Iterator<ImplementsEdge> actualLinkedListIfaces = linkedList.getImplementedInterfaceEdgeFrames(ImplementsEdge.class);
        assertImplementsEdges(linkedList, getLinkedListExpectedImplIfaces(), actualLinkedListIfaces);
    }
    
    @Test
    public void testGetEdgesListDefaultTyped() {
        Iterator<ImplementsEdge> actualLinkedListIfaces = linkedList.getImplementedInterfaceEdgeFramesList(ImplementsEdge.class).iterator();
        assertImplementsEdges(linkedList, getLinkedListExpectedImplIfaces(), actualLinkedListIfaces);
    }
    
    @Test
    public void testGetEdgesSetDefaultTyped() {
        Iterator<ImplementsEdge> actualLinkedListIfaces = linkedList.getImplementedInterfaceEdgeFramesSet(ImplementsEdge.class).iterator();
        assertImplementsEdges(linkedList, getLinkedListExpectedImplIfaces(), actualLinkedListIfaces);
    }
    
    private Set<String> getLinkedListExpectedImplIfaces() {
        Set<String> expLinkedListIfaces = new HashSet<>();
        expLinkedListIfaces.add(Collection.class.getName());
        expLinkedListIfaces.add(List.class.getName());
        return expLinkedListIfaces;
    }
    
    private void assertImplementsEdges(JavaClassVertex implementor, Set<String> expectedImplementedIfaces, Iterator<ImplementsEdge> actualImplIfaces) {
        int actualImplIfacesCount = 0;
        while (actualImplIfaces.hasNext()) {
            ImplementsEdge implEdge = actualImplIfaces.next();
            String actualInterfaceFqn = implEdge.getInterface().getFullyQualifiedName();
            Assert.assertTrue(expectedImplementedIfaces.contains(actualInterfaceFqn));
            Assert.assertEquals(implementor.getFullyQualifiedName(), implEdge.getImplementor().getFullyQualifiedName());
            actualImplIfacesCount += 1;
        }
        Assert.assertEquals(expectedImplementedIfaces.size(), actualImplIfacesCount);
    }
    
    @Test
    public void testGetBothDirectionsEdgesDefaultUntyped() {
        Iterator<EdgeFrame> actualEdges = abstractList.getBothDirectionsExtendEdgeFrames();
        Iterator<ExtendsEdge> framedExtendsEdges = new ReframingEdgeIterator<>(actualEdges, ExtendsEdge.class);
        assertExtendsEdges(getAbstrListBothDirectionsExtEdges(), framedExtendsEdges);
    }
    
    @Test
    public void testGetBothDirectionsEdgesDefaultUntypedList() {
        Iterator<EdgeFrame> actualEdges = abstractList.getBothDirectionsExtendEdgeFramesList().iterator();
        Iterator<ExtendsEdge> framedExtendsEdges = new ReframingEdgeIterator<>(actualEdges, ExtendsEdge.class);
        assertExtendsEdges(getAbstrListBothDirectionsExtEdges(), framedExtendsEdges);
    }
    
    @Test
    public void testGetBothDirectionsEdgesDefaultUntypedSet() {
        Iterator<EdgeFrame> actualEdges = abstractList.getBothDirectionsExtendEdgeFramesSet().iterator();
        Iterator<ExtendsEdge> framedExtendsEdges = new ReframingEdgeIterator<>(actualEdges, ExtendsEdge.class);
        assertExtendsEdges(getAbstrListBothDirectionsExtEdges(), framedExtendsEdges);
    }
    
    @Test
    public void testGetBothDirectionsEdges() {
        Iterator<ExtendsEdge> framedExtendsEdges = abstractList.getBothDirectionsExtendEdgeFrames(ExtendsEdge.class);
        assertExtendsEdges(getAbstrListBothDirectionsExtEdges(), framedExtendsEdges);
    }
    
    @Test
    public void testGetBothDirectionsEdgesList() {
        Iterator<ExtendsEdge> framedExtendsEdges = abstractList.getBothDirectionsExtendEdgeFramesList(ExtendsEdge.class).iterator();
        assertExtendsEdges(getAbstrListBothDirectionsExtEdges(), framedExtendsEdges);
    }
    
    @Test
    public void testGetBothDirectionsEdgesSet() {
        Iterator<ExtendsEdge> framedExtendsEdges = abstractList.getBothDirectionsExtendEdgeFramesSet(ExtendsEdge.class).iterator();
        assertExtendsEdges(getAbstrListBothDirectionsExtEdges(), framedExtendsEdges);
    }
    
    private Map<String, String> getAbstrListBothDirectionsExtEdges() {
        Map<String, String> edges = new HashMap<>();
        edges.put(AbstractList.class.getName(), AbstractCollection.class.getName());
        edges.put(ArrayList.class.getName(), AbstractList.class.getName());
        edges.put(AbstractSequentialList.class.getName(), AbstractList.class.getName());
        return edges;
    }
    
    private void assertExtendsEdges(
            Map<String, String> expSuperClassesBySubClass, 
            Iterator<ExtendsEdge> actualExtendsEdges) {
        int actualEdgesCount = 0;
        while (actualExtendsEdges.hasNext()) {
            ExtendsEdge extEdge = actualExtendsEdges.next();
            String superclassFqn = extEdge.getSuperClass().getFullyQualifiedName();
            String subclassFqn = extEdge.getSubClass().getFullyQualifiedName();
            Assert.assertEquals(expSuperClassesBySubClass.get(subclassFqn), superclassFqn);
            actualEdgesCount += 1;
        }
        Assert.assertEquals(expSuperClassesBySubClass.size(), actualEdgesCount);
    }
    
    @Test
    public void testGetEdgeDefault() {
        Set<String> listImplementorsFqns = findImplementors(javaClassesGraph, List.class)
                .stream()
                .map(JavaClassVertex::getFullyQualifiedName)
                .collect(Collectors.toSet());
        ImplementsEdge implEdge = list.getAnyImplementsEdge().reframe(ImplementsEdge.class);
        Assert.assertTrue(listImplementorsFqns.contains(implEdge.getImplementor().getFullyQualifiedName()));
        Assert.assertEquals(List.class.getName(), implEdge.getInterface().getFullyQualifiedName());
    }
    
    @Test
    public void testGetEdgeTyped() {
        Set<String> listImplementorsFqns = findImplementors(javaClassesGraph, List.class)
                .stream()
                .map(JavaClassVertex::getFullyQualifiedName)
                .collect(Collectors.toSet());
        ImplementsEdge implEdge = list.getAnyImplementsEdge(ImplementsEdge.class);
        Assert.assertTrue(listImplementorsFqns.contains(implEdge.getImplementor().getFullyQualifiedName()));
        Assert.assertEquals(List.class.getName(), implEdge.getInterface().getFullyQualifiedName());
    }
    
    private List<JavaClassVertex> findImplementors(FramedGraph graph, Class<?> ifaceClass) {
        List<? extends ImplementsEdge> implEdges = graph.traverse(
                input -> input.E().hasLabel("implements")).toList(ImplementsEdge.class);
        
        return implEdges.stream()
                .filter(edge -> edge.getInterface().getFullyQualifiedName().equals(ifaceClass.getName()))
                .map(ImplementsEdge::getImplementor)
                .collect(Collectors.toList());
    }
}

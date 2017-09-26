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

import com.syncleus.ferma.VertexFrame;
import com.syncleus.ferma.graphtypes.javaclass.JavaInterfaceVertex;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author rqpa
 */
public class AdjacencyHandlerWithJavaClassVerticesTest extends JavaClassGraphTestHelper {
 
    private Set<String> linkedListImplementedIfacesFqn;
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        
        linkedListImplementedIfacesFqn = new HashSet<>();
        linkedListImplementedIfacesFqn.add(List.class.getName());
        linkedListImplementedIfacesFqn.add(Collection.class.getName());
        linkedListImplementedIfacesFqn = Collections.unmodifiableSet(linkedListImplementedIfacesFqn);
    }
    
    @Test
    public void testGetOutVerticesDefault() {
        assertImplementedInterfacesDefault(linkedListImplementedIfacesFqn, linkedList.getImplementedInterfacesVertexFrames());
    }
    
    @Test
    public void testGetBothVerticesDefault() {
        assertImplementedInterfacesDefault(linkedListImplementedIfacesFqn, linkedList.getImplementedInterfacesVertexFramesBoth());
    }
    
    @Test
    public void testGetBothVerticesTyped() {
        assertImplementedInterfaces(linkedListImplementedIfacesFqn, linkedList.getImplementedInterfacesBoth(JavaInterfaceVertex.class));
    }
    
    @Test
    public void testGetBothVerticesListDefault() {
        assertImplementedInterfacesDefaultIterable(
                linkedListImplementedIfacesFqn,  
                List.class,
                linkedList.getImplementedInterfacesVertexFramesBothList());
    }
    
    @Test
    public void testGetOutVerticesListTyped() {
        assertImplementedInterfacesIterable(
                linkedListImplementedIfacesFqn, 
                List.class,
                linkedList.getImplementedInterfacesList(JavaInterfaceVertex.class));
    }
    
    @Test
    public void testGetBothVerticesListTyped() {
        assertImplementedInterfacesIterable(
                linkedListImplementedIfacesFqn, 
                List.class,
                linkedList.getImplementedInterfacesBothList(JavaInterfaceVertex.class));
    }
    
    @Test
    public void testGetOutVerticesSetDefault() {
        assertImplementedInterfacesDefaultIterable(
                linkedListImplementedIfacesFqn, 
                Set.class,
                linkedList.getImplementedInterfacesVertexFramesSet());
    }
    
    @Test
    public void testGetBothVerticesSetDefault() {
        assertImplementedInterfacesDefaultIterable(
                linkedListImplementedIfacesFqn, 
                Set.class,
                linkedList.getImplementedInterfacesVertexFramesBothSet());
    }
    
    @Test
    public void testGetOutVerticesSetTyped() {
        assertImplementedInterfacesIterable(
                linkedListImplementedIfacesFqn, 
                Set.class,
                linkedList.getImplementedInterfacesSet(JavaInterfaceVertex.class));
    }
    
    @Test
    public void testGetBothVerticesSetTyped() {
        assertImplementedInterfacesIterable(
                linkedListImplementedIfacesFqn,
                Set.class,
                linkedList.getImplementedInterfacesBothSet(JavaInterfaceVertex.class));
    }
    
    @Test
    public void testGetVertexDefault() {
        JavaInterfaceVertex implIface = linkedList.getAnyImplementedInterface().reframe(JavaInterfaceVertex.class);
        Assert.assertNotNull(implIface);
        Assert.assertTrue(linkedListImplementedIfacesFqn.contains(implIface.getFullyQualifiedName()));
    }
    
    @Test
    public void testGetVertexDefaultBoth() {
        JavaInterfaceVertex implIface = linkedList.getAnyImplementedInterfaceBoth().reframe(JavaInterfaceVertex.class);
        Assert.assertNotNull(implIface);
        Assert.assertTrue(linkedListImplementedIfacesFqn.contains(implIface.getFullyQualifiedName()));
    }
    
    @Test
    public void testGetVertexTyped() {
        JavaInterfaceVertex implIface = linkedList.getAnyImplementedInterface(JavaInterfaceVertex.class);
        Assert.assertNotNull(implIface);
        Assert.assertTrue(linkedListImplementedIfacesFqn.contains(implIface.getFullyQualifiedName()));
    }
    
    @Test
    public void testGetVertexTypedBoth() {
        JavaInterfaceVertex implIface = linkedList.getAnyImplementedInterfaceBoth(JavaInterfaceVertex.class);
        Assert.assertNotNull(implIface);
        Assert.assertTrue(linkedListImplementedIfacesFqn.contains(implIface.getFullyQualifiedName()));
    }
    
    private void assertImplementedInterfacesDefaultIterable(
            Set<String> expectedIfaceFqns,
            Class<? extends Iterable> expectedIterableType,
            Iterable<VertexFrame> actualIfaces) {
        Assert.assertTrue(expectedIterableType.isAssignableFrom(actualIfaces.getClass()));
        assertImplementedInterfacesDefault(expectedIfaceFqns, actualIfaces.iterator());
    }
    
    private void assertImplementedInterfacesIterable(
            Set<String> expectedIfaceFqns, 
            Class<? extends Iterable> expectedIterableType,
            Iterable<JavaInterfaceVertex> actualIfaces) {
        Assert.assertTrue(expectedIterableType.isAssignableFrom(actualIfaces.getClass()));
        assertImplementedInterfaces(expectedIfaceFqns, actualIfaces.iterator());
    }
    
    private void assertImplementedInterfacesDefault(Set<String> expectedIfaceFqns, Iterator<VertexFrame> actualIfaces) {
        Iterator<JavaInterfaceVertex> framedIfacesIt = new ReframingVertexIterator<>(actualIfaces, JavaInterfaceVertex.class);
        assertImplementedInterfaces(expectedIfaceFqns, framedIfacesIt);
    }
    
    private void assertImplementedInterfaces(Set<String> expectedIfaceFqns, Iterator<JavaInterfaceVertex> actualIfaces) {
        int actualIfacesCount = 0;
        while (actualIfaces.hasNext()) {
            JavaInterfaceVertex actualIface = actualIfaces.next();
            Assert.assertTrue(expectedIfaceFqns.contains(actualIface.getFullyQualifiedName()));
            actualIfacesCount += 1;
        }
        Assert.assertEquals(expectedIfaceFqns.size(), actualIfacesCount);
    }

}

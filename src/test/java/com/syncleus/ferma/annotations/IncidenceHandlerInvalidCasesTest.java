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

import com.syncleus.ferma.graphtypes.javaclass.ExtendsEdge;
import com.syncleus.ferma.graphtypes.javaclass.ImplementsEdge;
import com.syncleus.ferma.graphtypes.javaclass.JavaInterfaceVertex;
import org.junit.Test;
import com.syncleus.ferma.graphtypes.javaclass.invalid.JavaTypeIllegalVertex1;
import com.syncleus.ferma.graphtypes.javaclass.invalid.JavaTypeIllegalVertex2;
import com.syncleus.ferma.graphtypes.javaclass.invalid.JavaTypeIllegalVertex3;
import com.syncleus.ferma.graphtypes.javaclass.invalid.JavaTypeIllegalVertex4;
import com.syncleus.ferma.graphtypes.javaclass.invalid.JavaTypeIllegalVertex5;
import com.syncleus.ferma.graphtypes.javaclass.invalid.JavaTypeIllegalVertex6;
import com.syncleus.ferma.graphtypes.javaclass.invalid.JavaTypeIllegalVertex7;

/**
 *
 * @author rqpa
 */
public class IncidenceHandlerInvalidCasesTest extends JavaClassGraphTestHelper {
    
    @Test (expected = IllegalStateException.class)
    public void testIllegalTwoArgAddMethod() {
        javaClassesGraph.addFramedVertex(JavaTypeIllegalVertex1.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testIllegalThreeArgAddMethod() {
        javaClassesGraph.addFramedVertex(JavaTypeIllegalVertex2.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testIllegalGetEdgeMethod() {
        javaClassesGraph.addFramedVertex(JavaTypeIllegalVertex3.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testIllegalGetMultipleEdgesMethod() {
        javaClassesGraph.addFramedVertex(JavaTypeIllegalVertex4.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testIllegalRemoveAllEdgesMethod() {
        javaClassesGraph.addFramedVertex(JavaTypeIllegalVertex5.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testIllegalRemoveMultipleEdgesMethod() {
        javaClassesGraph.addFramedVertex(JavaTypeIllegalVertex6.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testIllegalUnresolvableOperationMethod() {
        javaClassesGraph.addFramedVertex(JavaTypeIllegalVertex7.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testAddBidirectionalEdgeDefault() {
        illegal.addBidirectionalEdgeDefault();
    }
    
    @Test (expected = IllegalStateException.class)
    public void testAddBidirectionalUntypedEdge() {
        illegal.addImplementsTwoDirectionsEdge(JavaInterfaceVertex.DEFAULT_INITIALIZER);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testAddBidirectionalTypedEdgeAndVertex() {
        illegal.addImplementsInterfaceTwoDirectionsEdge(JavaInterfaceVertex.DEFAULT_INITIALIZER, ImplementsEdge.DEFAULT_INITIALIZER);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testAddBidirectionalExtendsEdge() {
        illegal.addExtendsBothDirections(linkedList);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testAddBidirectionalExtendsEdgeWithEdgeInitializer() {
        illegal.addExtendsBothDirections(linkedList, ExtendsEdge.DEFAULT_INITIALIZER);
    }
}

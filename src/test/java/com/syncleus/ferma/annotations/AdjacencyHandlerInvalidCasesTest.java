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

import com.syncleus.ferma.graphtypes.javaclass.invalid.JavaTypeIllegalVertex10;
import com.syncleus.ferma.graphtypes.javaclass.invalid.JavaTypeIllegalVertex11;
import com.syncleus.ferma.graphtypes.javaclass.invalid.JavaTypeIllegalVertex12;
import com.syncleus.ferma.graphtypes.javaclass.invalid.JavaTypeIllegalVertex13;
import com.syncleus.ferma.graphtypes.javaclass.invalid.JavaTypeIllegalVertex14;
import com.syncleus.ferma.graphtypes.javaclass.invalid.JavaTypeIllegalVertex15;
import com.syncleus.ferma.graphtypes.javaclass.invalid.JavaTypeIllegalVertex8;
import com.syncleus.ferma.graphtypes.javaclass.invalid.JavaTypeIllegalVertex9;
import org.junit.Test;

/**
 *
 * @author rqpa
 */
public class AdjacencyHandlerInvalidCasesTest extends JavaClassGraphTestHelper {
    
    @Test (expected = IllegalStateException.class)
    public void testIllegalTwoArgAddOperation() {
        javaClassesGraph.addFramedVertex(JavaTypeIllegalVertex8.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testIllegalTreeArgAddOperation() {
        javaClassesGraph.addFramedVertex(JavaTypeIllegalVertex9.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testIllegalThreeArgSetOperation() {
        javaClassesGraph.addFramedVertex(JavaTypeIllegalVertex10.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testIllegalZeroArgSetOperation() {
        javaClassesGraph.addFramedVertex(JavaTypeIllegalVertex11.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testIllegalTypeOneArgSetOperation() {
        javaClassesGraph.addFramedVertex(JavaTypeIllegalVertex12.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testIllegalTwoArgSetOperation() {
        javaClassesGraph.addFramedVertex(JavaTypeIllegalVertex13.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testUnresolvableTypeOperation() {
        javaClassesGraph.addFramedVertex(JavaTypeIllegalVertex14.class);
    }
    
    @Test (expected = IllegalStateException.class)
    public void testGetTwoArgsOperation() {
        javaClassesGraph.addFramedVertex(JavaTypeIllegalVertex15.class);
    }
}

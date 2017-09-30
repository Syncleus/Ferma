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
package com.syncleus.ferma;

import com.syncleus.ferma.annotations.NetworkGraphTestHelper;
import com.syncleus.ferma.graphtypes.network.ComputerVertex;
import com.syncleus.ferma.graphtypes.network.NetworkConnectionEdge;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author rqpa
 */
public class AbstractVertexFrameTest extends NetworkGraphTestHelper {
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
       
    }
    
    @Test
    public void testAddFramedEdgeExplicit() {
        ComputerVertex dev6 = graph.addFramedVertex(ComputerVertex.DEFAULT_INITIALIZER, "name", "DEV6");
        dev6.addFramedEdgeExplicit("connects", dev5, NetworkConnectionEdge.class);
        assertOneWayExclusiveConnection(dev6, dev5);
    }
    
    @Test
    public void testSetLinkOutExplicit() {
        ComputerVertex dev6 = dev5.setLinkOutExplicit(ComputerVertex.class, "connects");
        dev6.setName("DEV6");
        assertOneWayExclusiveConnection(dev2, dev5);
        assertOneWayExclusiveConnection(dev3, dev5);
        assertOneWayExclusiveConnection(dev4, dev5);
        assertOneWayExclusiveConnection(dev5, dev6);
    }
    
    @Test
    public void testSetLinkInExplicit() {
        ComputerVertex dev6 = dev5.setLinkInExplicit(ComputerVertex.class, "connects");
        dev6.setName("DEV6");
        assertOneWayExclusiveConnection(dev5, dev2);
        assertOneWayExclusiveConnection(dev5, dev3);
        assertOneWayExclusiveConnection(dev5, dev4);
        assertOneWayExclusiveConnection(dev6, dev5);
    }
    
    @Test
    public void testSetLinkBothExplicit() {
        ComputerVertex dev6 = dev5.setLinkBothExplicit(ComputerVertex.class, "connects");
        dev6.setName("DEV6");
        assertNoConnection(dev5, dev2);
        assertNoConnection(dev5, dev3);
        assertNoConnection(dev5, dev4);
        assertTwoWayConnection(dev6, dev5);
    }
    
}

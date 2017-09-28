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
package com.syncleus.ferma.graphtypes.filesystem;

import com.syncleus.ferma.ClassInitializer;
import com.syncleus.ferma.DefaultClassInitializer;
import com.syncleus.ferma.TEdge;
import com.syncleus.ferma.annotations.Adjacency;
import com.syncleus.ferma.annotations.GraphElement;
import com.syncleus.ferma.annotations.Incidence;
import java.util.List;
import org.apache.tinkerpop.gremlin.structure.Direction;

/**
 *
 * @author rqpa
 */
@GraphElement
public abstract class DirectoryVertex extends FileVertex {
    
    public static final ClassInitializer<DirectoryVertex> DEFAULT_INITIALIZER = new DefaultClassInitializer(DirectoryVertex.class);

    @Incidence(label = "parent", direction = Direction.IN, operation = Incidence.Operation.ADD)
    public abstract TEdge createSubDirParentEdge();
    
    @Incidence(label = "parent", direction = Direction.OUT, operation = Incidence.Operation.ADD)
    public abstract TEdge createSubDirChildEdge();
    
    @Adjacency(label = "parent", direction = Direction.IN)
    public abstract List<? extends FileVertex> getItems();
    
    @Incidence(label = "parent", direction = Direction.IN, operation = Incidence.Operation.ADD)
    public abstract <T extends FileVertex> TEdge addItemEdge(ClassInitializer<? extends T> initializer);
    
    @Incidence(label = "parent", direction = Direction.IN, operation = Incidence.Operation.ADD)
    public abstract <EdgeType, ItemType extends FileVertex> EdgeType addItemEdge(
            ClassInitializer<? extends ItemType> initializer,
            ClassInitializer<? extends EdgeType> edgeInitializer);
    
    @Incidence(label = "parent", direction = Direction.IN)
    public abstract TEdge addChild(FileVertex child);
}

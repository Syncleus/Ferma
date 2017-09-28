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

import com.syncleus.ferma.AbstractVertexFrame;
import com.syncleus.ferma.ClassInitializer;
import com.syncleus.ferma.DefaultClassInitializer;
import com.syncleus.ferma.annotations.Adjacency;
import com.syncleus.ferma.annotations.GraphElement;
import com.syncleus.ferma.annotations.Incidence;
import com.syncleus.ferma.annotations.Property;
import java.util.Iterator;
import java.util.List;
import org.apache.tinkerpop.gremlin.structure.Direction;

/**
 *
 * @author rqpa
 */
@GraphElement
public abstract class FileVertex extends AbstractVertexFrame {
    
    public static final ClassInitializer<FileVertex> DEFAULT_INITIALIZER = new DefaultClassInitializer(FileVertex.class);

    @Property(value = "name")
    public abstract String getName();
    
    @Property(value = "name")
    public abstract void setName(String name);
    
    @Adjacency(label = "parent", direction = Direction.OUT)
    public abstract List<? extends FileVertex> getParents();
    
    // "In the future" other filesystem types that can own filesystem objects may exist (e.g. symbolic link)
    @Incidence(label = "parent", direction = Direction.OUT)
    public abstract <EdgeType> Iterator<EdgeType> getOutEdges(Class<EdgeType> parentTypes);
    
}

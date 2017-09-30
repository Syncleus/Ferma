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

import com.syncleus.ferma.*;
import org.apache.tinkerpop.gremlin.structure.Direction;

import java.util.Iterator;

public interface GodAlternative extends VertexFrame {
    static final ClassInitializer<GodAlternative> DEFAULT_INITIALIZER = new DefaultClassInitializer(GodAlternative.class);

    @Adjacency(label = "father", direction = Direction.IN)
    <N extends God> Iterator<? extends N> getSons(Class<? extends N> type);

    @Adjacency(label = "father", direction = Direction.IN)
    <N extends God> N getSon(Class<? extends N> type);

    @Adjacency(label = "father", direction = Direction.IN)
    <N extends God> N addSon(ClassInitializer<? extends N> type);

    @Adjacency(label = "")
    <N extends God> Iterator<? extends N> getNoLabel(Class<? extends N> type);
}

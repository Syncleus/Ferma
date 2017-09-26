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
package com.syncleus.ferma;

import java.util.function.Function;
import com.google.gson.JsonObject;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Element;

import java.util.Iterator;

public interface EdgeFrame extends ElementFrame {
    @Override
    Edge getElement();

    /**
     * @return The label associated with this edge
     */
    String getLabel();

    JsonObject toJson();

    /**
     * Reframe this element as a different type of frame.
     *
     * @param <T> The type to frame as.
     * @param kind The new kind of frame.
     * @return The new frame
     */
    <T> T reframe(Class<T> kind);

    /**
     * Reframe this element as a different type of frame.
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param <T> The type to frame as.
     * @param kind The new kind of frame.
     * @return The new frame
     */
    <T> T reframeExplicit(Class<T> kind);

    <T extends Traversable<?, ?>> T traverse(Function<GraphTraversal<Edge, Edge>, GraphTraversal<?, ?>> traverser);

    GraphTraversal<? extends Edge, ? extends Edge> getRawTraversal();
}

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

import com.google.common.base.Function;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public interface Traversable<PE, E> {
    <T extends Traversable<?, ?>> T traverse(final Function<GraphTraversal<PE, E>, GraphTraversal<?,?>> traverser);

    GraphTraversal<PE, E> getRawTraversal();

    /**
     * Get the next object emitted from the pipeline. If no such object exists,
     * then a NoSuchElementException is thrown.
     *
     * @param <N> The type used to frame the element
     * @param kind
     *            The type of frame for the element.
     * @return the next emitted object
     */
    <N> N next(Class<N> kind);

    /**
     * Get the next object emitted from the pipeline. If no such object exists,
     * then a NoSuchElementException is thrown.
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param <N> The type used to frame the element
     * @param kind
     *            The type of frame for the element.
     * @return the next emitted object
     */
    <N> N nextExplicit(Class<N> kind);

    /**
     * Get the next object emitted from the pipeline. If no such object exists,
     * then a the default value is returned.
     *
     * @param <N> The type used to frame the element
     * @param kind
     *            The type of frame for the element.
     * @param defaultValue
     *            The object to return if no next object exists.
     * @return the next emitted object
     */
    <N> N nextOrDefault(Class<N> kind, N defaultValue);

    /**
     * Get the next object emitted from the pipeline. If no such object exists,
     * then a the default value is returned.
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param <N> The type used to frame the element
     * @param kind
     *            The type of frame for the element.
     * @param defaultValue
     *            The object to return if no next object exists.
     * @return the next emitted object
     */
    <N> N nextOrDefaultExplicit(Class<N> kind, N defaultValue);

    /**
     * Get the next object emitted from the pipeline. If no such object exists a
     * new vertex is created.
     *
     * @return the next emitted object
     */
    VertexFrame nextOrAdd();

    /**
     * Get the next object emitted from the pipeline. If no such object exists a
     * new vertex is created.
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param <N> The type used to frame the element
     * @param initializer
     *            the initializer for the frame which defines its type and may initialize properties
     * @return the next emitted object
     */
    <N> N nextOrAddExplicit(ClassInitializer<N> initializer);

    /**
     * Get the next object emitted from the pipeline. If no such object exists a
     * new vertex is created.
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param <N> The type used to frame the element
     * @param kind
     *            The kind of frame.
     * @return the next emitted object
     */
    <N> N nextOrAddExplicit(Class<N> kind);

    /**
     * Get the next object emitted from the pipeline. If no such object exists a
     * new vertex is created.
     *
     * @param <N> The type used to frame the element
     * @param initializer
     *            the initializer for the frame which defines its type and may initialize properties
     * @return the next emitted object
     */
    <N> N nextOrAdd(ClassInitializer<N> initializer);

    /**
     * Get the next object emitted from the pipeline. If no such object exists a
     * new vertex is created.
     *
     * @param <N> The type used to frame the element
     * @param kind
     *            The kind of frame.
     * @return the next emitted object
     */
    <N> N nextOrAdd(Class<N> kind);

    /**
     * Return the next X objects in the traversal as a list.
     *
     * @param <N> The type used to frame the element
     * @param amount
     *            the number of objects to return
     * @param kind
     *            the type of frame to for each element.
     * @return a list of X objects (if X objects occur)
     */
    <N> List<? extends N> next(int amount, Class<N> kind);

    /**
     * Return the next X objects in the traversal as a list.
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param <N> The type used to frame the element
     * @param amount
     *            the number of objects to return
     * @param kind
     *            the type of frame to for each element.
     * @return a list of X objects (if X objects occur)
     */
    <N> List<? extends N> nextExplicit(int amount, Class<N> kind);

    /**
     * Return an iterator of framed elements.
     *
     * @param <N> The type used to frame the element
     * @param kind
     *            The kind of framed elements to return.
     * @return An iterator of framed elements.
     */
    <N> Iterator<N> frame(Class<N> kind);

    /**
     * Return an iterator of framed elements.
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param <N> The type used to frame the element
     * @param kind
     *            The kind of framed elements to return.
     * @return An iterator of framed elements.
     */
    <N> Iterator<? extends N> frameExplicit(Class<N> kind);

    /**
     * Return a list of all the objects in the pipeline.
     *
     * @param <N> The type used to frame the element
     * @param kind
     *            The kind of framed elements to return.
     * @return a list of all the objects
     */
    <N> List<? extends N> toList(Class<N> kind);

    /**
     * Return a list of all the objects in the pipeline.
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param <N> The type used to frame the element
     * @param kind
     *            The kind of framed elements to return.
     * @return a list of all the objects
     */
    <N> List<? extends N> toListExplicit(Class<N> kind);

    /**
     * Return a set of all the objects in the pipeline.
     *
     * @param <N> The type used to frame the element
     * @param kind
     *            The kind of framed elements to return.
     * @return a set of all the objects
     */
    <N> Set<? extends N> toSet(Class<N> kind);

    /**
     * Return a set of all the objects in the pipeline.
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param <N> The type used to frame the element
     * @param kind
     *            The kind of framed elements to return.
     * @return a set of all the objects
     */
    <N> Set<? extends N> toSetExplicit(Class<N> kind);
}

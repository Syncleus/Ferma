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

import com.syncleus.ferma.framefactories.FrameFactory;
import com.syncleus.ferma.typeresolvers.TypeResolver;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Function;

/**
 * The primary class for framing your blueprints graphs.
 */
public interface FramedGraph extends AutoCloseable {

    TypeResolver getTypeResolver();

    FrameFactory getBuilder();

    /**
     * Close the delegate graph.
     *
     * @throws IOException Whenever an IO problem prevents the stream from closing.
     */
    void close() throws IOException;

    /**
     * Add a vertex to the graph
     *
     * @param <T> The type used to frame the element.
     * @param initializer
     *            the initializer for the frame which defines its type and may initialize properties
     * @param keyValues
     *            the recommended object identifier
     * @return The framed vertex.
     */
    <T> T addFramedVertex(ClassInitializer<T> initializer, Object... keyValues);

    /**
     * Add a vertex to the graph
     *
     * @param <T> The type used to frame the element.
     * @param kind
     *            The kind of the frame.
     * @param keyValues
     *            the recommended object identifier
     * @return The framed vertex.
     */
    <T> T addFramedVertex(Class<T> kind, Object... keyValues);
    
    /**
     * Add a vertex to the graph
     *
     * @param <T> The type used to frame the element.
     * @param kind
     *            The kind of the frame.
     * @return The framed vertex.
     */
    <T> T addFramedVertex(Class<T> kind);

    /**
     * Add a vertex to the graph
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param <T> The type used to frame the element.
     * @param initializer
     *            the initializer for the frame which defines its type and may initialize properties
     * @return The framed vertex.
     */
    <T> T addFramedVertexExplicit(ClassInitializer<T> initializer);
    
    /**
     * Add a vertex to the graph
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param <T> The type used to frame the element.
     * @param kind
     *            The kind of the frame.
     * @return The framed vertex.
     */
    <T> T addFramedVertexExplicit(Class<T> kind);

    /**
     * Add a vertex to the graph using a frame type of {@link TVertex}.
     *
     * @return The framed vertex.
     */
    TVertex addFramedVertex();

    /**
     * Add a vertex to the graph using a frame type of {@link TVertex}.
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @return The framed vertex.
     */
    TVertex addFramedVertexExplicit();

    /**
     * Add a edge to the graph
     *
     * @param <T> The type used to frame the element.
     * @param source the source vertex
     * @param destination the destination vertex
     * @param label the label.
     * @param initializer
     *            the initializer for the frame which defines its type and may initialize properties
     * @param keyValues the recommended object identifier
     * @return The framed edge.
     */
    <T> T addFramedEdge(final VertexFrame source, final VertexFrame destination, final String label, ClassInitializer<T> initializer, final Object... keyValues);

    /**
     * Add a edge to the graph
     *
     * @param <T> The type used to frame the element.
     * @param source The source vertex
     * @param destination the destination vertex
     * @param label the label.
     * @param kind
     *            The kind of the frame.
     * @return The framed edge.
     */
    <T> T addFramedEdge(final VertexFrame source, final VertexFrame destination, final String label, Class<T> kind);

    /**
     * Add a edge to the graph
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param <T> The type used to frame the element.
     * @param source The source vertex
     * @param destination the destination vertex
     * @param label the label.
     * @param initializer
     *            the initializer for the frame which defines its type and may initialize properties
     * @return The framed edge.
     */
    <T> T addFramedEdgeExplicit(final VertexFrame source, final VertexFrame destination, final String label, ClassInitializer<T> initializer);
    
    /**
     * Add a edge to the graph
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param <T> The type used to frame the element.
     * @param source The source vertex
     * @param destination the destination vertex
     * @param label the label.
     * @param kind
     *            The kind of the frame.
     * @return The framed edge.
     */
    <T> T addFramedEdgeExplicit(final VertexFrame source, final VertexFrame destination, final String label, Class<T> kind);

    /**
     * Add a edge to the graph using a frame type of {@link TEdge}.
     *
     * @param source The source vertex
     * @param destination the destination vertex
     * @param label the label.
     * @return The framed edge.
     */
    TEdge addFramedEdge(final VertexFrame source, final VertexFrame destination, final String label);

    /**
     * Add a edge to the graph using a frame type of {@link TEdge}.
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param source The source vertex
     * @param destination the destination vertex
     * @param label the label.
     * @return The framed edge.
     */
    TEdge addFramedEdgeExplicit(final VertexFrame source, final VertexFrame destination, final String label);

    <T extends Traversable<?, ?>> T traverse(Function<GraphTraversalSource, GraphTraversal<?, ?>> traverser);

    GraphTraversalSource getRawTraversal();

    <T> T getFramedVertex(Class<T> kind, Object id);

    <T> T getFramedVertexExplicit(Class<T> kind, Object id);

    <T> Iterator<? extends T> getFramedVertices(final Class<T> kind);

    <T> Iterator<? extends T> getFramedVertices(final String key, final Object value, final Class<T> kind);

    <T> Iterator<? extends T> getFramedVerticesExplicit(final Class<T> kind);

    <T> Iterator<? extends T> getFramedVerticesExplicit(final String key, final Object value, final Class<T> kind);

    <T> Iterator<? extends T> getFramedEdges(final Class<T> kind);

    <T> Iterator<? extends T> getFramedEdges(final String key, final Object value, final Class<T> kind);

    <T> Iterator<? extends T> getFramedEdgesExplicit(final Class<T> kind);

    <T> Iterator<? extends T> getFramedEdgesExplicit(final String key, final Object value, final Class<T> kind);

    <T> Iterator<? extends T> frame(Iterator<? extends Element> pipeline, final Class<T> kind);

    <T> T frameNewElement(Element e, ClassInitializer<T> initializer);
    
    <T> T frameNewElement(Element e, Class<T> kind);

    <T> T frameElement(Element e, Class<T> kind);

    <T> T frameNewElementExplicit(Element e, ClassInitializer<T> initializer);
    
    <T> T frameNewElementExplicit(Element e, Class<T> kind);

    <T> T frameElementExplicit(Element e, Class<T> kind);

    <T> Iterator<? extends T> frameExplicit(Iterator<? extends Element> pipeline, final Class<T> kind);

    WrappedTransaction tx();
}

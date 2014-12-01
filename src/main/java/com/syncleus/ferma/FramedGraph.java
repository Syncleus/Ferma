/******************************************************************************
 *                                                                             *
 *  Copyright: (c) Syncleus, Inc.                                              *
 *                                                                             *
 *  You may redistribute and modify this source code under the terms and       *
 *  conditions of the Open Source Community License - Type C version 1.0       *
 *  or any later version as published by Syncleus, Inc. at www.syncleus.com.   *
 *  There should be a copy of the license included with this file. If a copy   *
 *  of the license is not included you are granted no right to distribute or   *
 *  otherwise use this file except through a legal and valid license. You      *
 *  should also contact Syncleus, Inc. at the information below if you cannot  *
 *  find a license:                                                            *
 *                                                                             *
 *  Syncleus, Inc.                                                             *
 *  2604 South 12th Street                                                     *
 *  Philadelphia, PA 19148                                                     *
 *                                                                             *
 ******************************************************************************/
package com.syncleus.ferma;


import com.tinkerpop.blueprints.*;

import java.util.Collection;
import java.util.Iterator;

/**
 * The primary class for framing your blueprints graphs.
 */

public interface FramedGraph extends Graph {

	public Transaction tx();

	/**
	 * Close the delegate graph.
	 */
	public void close();

	/**
	 * Add a vertex to the graph
	 *
	 * @param kind
	 *            The kind of the frame.
	 * @return The framed vertex.
	 */
	public <T> T addFramedVertex(Class<T> kind);

	/**
	 * Add a vertex to the graph
	 *
	 * This will bypass the default type resolution and use the untyped resolver
	 * instead. This method is useful for speeding up a look up when type resolution
	 * isn't required.
	 *
	 * @param kind
	 *            The kind of the frame.
	 * @return The framed vertex.
	 */
	public <T> T addFramedVertexExplicit(Class<T> kind);

	/**
	 * Add a vertex to the graph using a frame type of {@link TVertex}.
	 *
	 * @return The framed vertex.
	 */
	public TVertex addFramedVertex();

	/**
	 * Add a vertex to the graph using a frame type of {@link TVertex}.
	 *
	 * This will bypass the default type resolution and use the untyped resolver
	 * instead. This method is useful for speeding up a look up when type resolution
	 * isn't required.
	 *
	 * @return The framed vertex.
	 */
	public TVertex addFramedVertexExplicit();

	/**
	 * Add a edge to the graph
	 *
	 * @param kind
	 *            The kind of the frame.
	 * @return The framed edge.
	 */
	public <T> T addFramedEdge(final VertexFrame source, final VertexFrame destination, final String label, Class<T> kind);

	/**
	 * Add a edge to the graph
	 *
	 * This will bypass the default type resolution and use the untyped resolver
	 * instead. This method is useful for speeding up a look up when type resolution
	 * isn't required.
	 *
	 * @param kind
	 *            The kind of the frame.
	 * @return The framed edge.
	 */
	public <T> T addFramedEdgeExplicit(final VertexFrame source, final VertexFrame destination, final String label, Class<T> kind);

	/**
	 * Add a edge to the graph using a frame type of {@link TEdge}.
	 *
	 * @return The framed edge.
	 */
	public TEdge addFramedEdge(final VertexFrame source, final VertexFrame destination, final String label);

	/**
	 * Add a edge to the graph using a frame type of {@link TEdge}.
	 *
	 * This will bypass the default type resolution and use the untyped resolver
	 * instead. This method is useful for speeding up a look up when type resolution
	 * isn't required.
	 *
	 * @return The framed edge.
	 */
	public TEdge addFramedEdgeExplicit(final VertexFrame source, final VertexFrame destination, final String label);

	/**
	 * Query over all vertices in the graph.
	 *
	 * @return The query.
	 */
	public VertexTraversal<?, ?, ?> v();

	/**
	 * Query vertices with a matching key and value
	 *
	 * @return The query.
	 */
	public VertexTraversal<?, ?, ?> v(String key, Object value);

	/**
	 * Query over all edges in the graph.
	 *
	 * @return The query.
	 */
	public EdgeTraversal<?, ?, ?> e();

	public <F> Iterable<? extends F> getFramedVertices(final Class<F> kind);

	public <F> Iterable<? extends F> getFramedVertices(final String key, final Object value, final Class<F> kind);

	public <F> Iterable<? extends F> getFramedVerticesExplicit(final Class<F> kind);

	public <F> Iterable<? extends F> getFramedVerticesExplicit(final String key, final Object value, final Class<F> kind);

	public <F> Iterable<? extends F> getFramedEdges(final Class<F> kind);

	public <F> Iterable<? extends F> getFramedEdges(final String key, final Object value, final Class<F> kind);

	public <F> Iterable<? extends F> getFramedEdgesExplicit(final Class<F> kind);

	public <F> Iterable<? extends F> getFramedEdgesExplicit(final String key, final Object value, final Class<F> kind);

	/**
	 * Query over a list of vertices in the graph.
	 *
	 * @param ids
	 *            The ids of the vertices.
	 * @return The query.
	 */
	public VertexTraversal<?, ?, ?> v(final Collection<?> ids);

	/**
	 * Query over a list of vertices in the graph.
	 *
	 * @param ids
	 *            The ids of the vertices.
	 * @return The query.
	 */
	public VertexTraversal<?, ?, ?> v(final Object... ids);

	/**
	 * Query over a list of edges in the graph.
	 *
	 * @param ids
	 *            The ids of the edges.
	 * @return The query.
	 */
	public EdgeTraversal<?, ?, ?> e(final Object... ids);


	/**
	 * Query over a list of edges in the graph.
	 *
	 * @param ids
	 *            The ids of the edges.
	 * @return The query.
	 */
	public EdgeTraversal<?, ?, ?> e(final Collection<?> ids);

	public Vertex addVertexExplicit(Object id);

	public Edge addEdgeExplicit(Object id, Vertex outVertex, Vertex inVertex, String label);

	<T extends ElementFrame> Iterator<? extends T> frame(Iterator<? extends Element> pipeline, final Class<T> kind);

	<T> T frameNewElement(Element e, Class<T> kind);

	<T> T frameElement(Element e, Class<T> kind);

	<T> T frameNewElementExplicit(Element e, Class<T> kind);

	<T> T frameElementExplicit(Element e, Class<T> kind);

	<T extends ElementFrame> Iterator<? extends T> frameExplicit(Iterator<? extends Element> pipeline, final Class<T> kind);
}

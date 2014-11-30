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

import com.google.gson.JsonObject;
import com.tinkerpop.blueprints.Vertex;

public interface VertexFrame extends ElementFrame {
    /*
     * (non-Javadoc)
     *
     * @see FramedElement#element()
     */
    public Vertex element();

    /**
     * Add an edge using the supplied frame type.
     *
     * @param label
     *            The label for the edge
     * @param inVertex
     *            The vertex to link to.
     * @param kind
     *            The kind of frame.
     * @return The new edge.
     */
    public <T> T addFramedEdge(String label, VertexFrame inVertex, Class<T> kind);

    /**
     * Add an edge using the supplied frame type.
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param label
     *            The label for the edge
     * @param inVertex
     *            The vertex to link to.
     * @param kind
     *            The kind of frame.
     * @return The new edge.
     */
    public <T> T addFramedEdgeExplicit(String label, VertexFrame inVertex, Class<T> kind);

    /**
     * Add an edge using a frame type of {@link TEdge}.
     *
     * @param label
     *            The label for the edge
     * @param inVertex
     *            The vertex to link to.
     * @return The added edge.
     */
    public TEdge addFramedEdge(String label, VertexFrame inVertex);

    /**
     * Add an edge using a frame type of {@link TEdge}.
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param label
     *            The label for the edge
     * @param inVertex
     *            The vertex to link to.
     * @return The added edge.
     */
    public TEdge addFramedEdgeExplicit(String label, VertexFrame inVertex);

    public VertexTraversal<?, ?, ?> out(final int branchFactor, final String... labels);

    public VertexTraversal<?, ?, ?> out(final String... labels);

    public VertexTraversal<?, ?, ?> in(final int branchFactor, final String... labels);

    public VertexTraversal<?, ?, ?> in(final String... labels);

    public VertexTraversal<?, ?, ?> both(final int branchFactor, final String... labels);

    public VertexTraversal<?, ?, ?> both(final String... labels);

    public EdgeTraversal<?, ?, ?> outE(final int branchFactor, final String... labels);

    public EdgeTraversal<?, ?, ?> outE(final String... labels);

    public EdgeTraversal<?, ?, ?> inE(final int branchFactor, final String... labels);

    public EdgeTraversal<?, ?, ?> inE(final String... labels);

    public EdgeTraversal<?, ?, ?> bothE(final int branchFactor, final String... labels);

    public EdgeTraversal<?, ?, ?> bothE(final String... labels);

    /**
     * Create edges from the framed vertex to the supplied vertex with the
     * supplied labels
     *
     * @param vertex
     *            The vertex to link to.
     * @param labels
     *            The labels for the edges.
     */
    public void linkOut(VertexFrame vertex, String... labels);

    /**
     * Create edges from the supplied vertex to the framed vertex with the
     * supplied labels
     *
     * @param vertex
     *            The vertex to link from.
     * @param labels
     *            The labels for the edges.
     */
    public void linkIn(VertexFrame vertex, String... labels);

    /**
     * Create edges from the supplied vertex to the framed vertex and vice versa
     * with the supplied labels
     *
     * @param vertex
     *            The vertex to link to and from.
     * @param labels
     *            The labels for the edges.
     */
    public void linkBoth(VertexFrame vertex, String... labels);

    /**
     * Remove all edges to the supplied vertex with the supplied labels.
     *
     * @param vertex
     *            The vertex to removed the edges to.
     * @param labels
     *            The labels of the edges.
     */
    public void unlinkOut(VertexFrame vertex, String... labels);

    /**
     * Remove all edges to the supplied vertex with the supplied labels.
     *
     * @param vertex
     *            The vertex to removed the edges from.
     * @param labels
     *            The labels of the edges.
     */
    public void unlinkIn(VertexFrame vertex, String... labels);

    /**
     * Remove all edges to/from the supplied vertex with the supplied labels.
     *
     * @param vertex
     *            The vertex to removed the edges to/from.
     * @param labels
     *            The labels of the edges.
     */
    public void unlinkBoth(VertexFrame vertex, String... labels);

    /**
     * Remove all out edges with the labels and then add a single edge to the
     * supplied vertex.
     *
     * @param vertex
     *            the vertex to link to.
     * @param labels
     *            The labels of the edges.
     */
    public void setLinkOut(VertexFrame vertex, String... labels);

    /**
     * Remove all in edges with the labels and then add a single edge from the
     * supplied vertex.
     *
     * @param vertex
     *            the vertex to link from.
     * @param labels
     *            The labels of the edges.
     */
    public void setLinkIn(VertexFrame vertex, String... labels);

    /**
     * Remove all in edges with the labels and then add a edges from the
     * supplied vertex and to the supplied vertex.
     *
     * @param vertex
     *            the vertex to link from.
     * @param labels
     *            The labels of the edges.
     */
    public void setLinkBoth(VertexFrame vertex, String... labels);

    /**
     * Remove all out edges with the labels and then add a single edge to a new
     * vertex.
     *
     * @param kind
     *            the vertex type to link to.
     * @param labels
     *            The labels of the edges.
     */
    public <K> K setLinkOut(Class<K> kind, String... labels);

    /**
     * Remove all out edges with the labels and then add a single edge to a new
     * vertex.
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param kind
     *            the vertex type to link to.
     * @param labels
     *            The labels of the edges.
     */
    public <K> K setLinkOutExplicit(Class<K> kind, String... labels);

    /**
     * Remove all out edges with the labels and then add a single edge from a
     * new vertex.
     *
     * @param kind
     *            the vertex type to link to.
     * @param labels
     *            The labels of the edges.
     */
    public <K> K setLinkIn(Class<K> kind, String... labels);

    /**
     * Remove all out edges with the labels and then add a single edge from a
     * new vertex.
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param kind
     *            the vertex type to link to.
     * @param labels
     *            The labels of the edges.
     */
    public <K> K setLinkInExplicit(Class<K> kind, String... labels);

    /**
     * Remove all out edges with the labels and then add edges to/from a new
     * vertex.
     *
     * @param kind
     *            the vertex type to link to.
     * @param labels
     *            The labels of the edges.
     */
    public <K> K setLinkBoth(Class<K> kind, String... labels);

    /**
     * Remove all out edges with the labels and then add edges to/from a new
     * vertex.
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param kind
     *            the vertex type to link to.
     * @param labels
     *            The labels of the edges.
     */
    public <K> K setLinkBothExplicit(Class<K> kind, String... labels);

    /**
     * Shortcut to get frameTraversal of current element
     *
     * @return
     */
    public VertexTraversal<?, ?, ?> traversal();

    /**
     * Output the vertex as json.
     *
     * @return
     */
    public JsonObject toJson();

    /**
     * Reframe this element as a different type of frame.
     *
     * @param kind The new kind of frame.
     * @return The new frame
     */
    public <T> T reframe(Class<T> kind);

    /**
     * Reframe this element as a different type of frame.
     *
     * This will bypass the default type resolution and use the untyped resolver
     * instead. This method is useful for speeding up a look up when type resolution
     * isn't required.
     *
     * @param kind The new kind of frame.
     * @return The new frame
     */
    public <T> T reframeExplicit(Class<T> kind);
}

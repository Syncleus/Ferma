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

/*
 * Part or all of this source file was forked from a third-party project, the details of which are listed below.
 *
 * Source Project: Totorom
 * Source URL: https://github.com/BrynCooke/totorom
 * Source License: Apache Public License v2.0
 * When: November, 20th 2014
 */
package com.syncleus.ferma;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * The base class that all vertex frames must extend.
 */
public abstract class FramedVertex extends FramedElement {

	/*
	 * (non-Javadoc)
	 * 
	 * @see FramedElement#element()
	 */
	public Vertex element() {
		return (Vertex) super.element();
	};

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
	public <T extends FramedEdge> T addFramedEdge(String label, FramedVertex inVertex, Class<T> kind) {

		Edge edge = element().addEdge(label, inVertex.element());
		T framedEdge = graph().frameNewElement(edge, kind);
		framedEdge.init();
		return framedEdge;
	}

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
	public <T extends FramedEdge> T addFramedEdgeExplicit(String label, FramedVertex inVertex, Class<T> kind) {

		Edge edge = element().addEdge(label, inVertex.element());
		T framedEdge = graph().frameNewElementExplicit(edge, kind);
		framedEdge.init();
		return framedEdge;
	}

	/**
	 * Add an edge using a frame type of {@link TEdge}.
	 * 
	 * @param label
	 *            The label for the edge
	 * @param inVertex
	 *            The vertex to link to.
	 * @return The added edge.
	 */
	public TEdge addFramedEdge(String label, FramedVertex inVertex) {
		return addFramedEdge(label, inVertex, TEdge.class);
	}

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
	public TEdge addFramedEdgeExplicit(String label, FramedVertex inVertex) {
		return addFramedEdgeExplicit(label, inVertex, TEdge.class);
	}

	public VertexTraversal<?, ?, ?> out(final int branchFactor, final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().out(branchFactor, labels);
	}

	public VertexTraversal<?, ?, ?> out(final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().out(labels);
	}

	public VertexTraversal<?, ?, ?> in(final int branchFactor, final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().in(branchFactor, labels);
	}

	public VertexTraversal<?, ?, ?> in(final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().in(labels);
	}

	public VertexTraversal<?, ?, ?> both(final int branchFactor, final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().both(branchFactor, labels);
	}

	public VertexTraversal<?, ?, ?> both(final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().both(labels);
	}

	public EdgeTraversal<?, ?, ?> outE(final int branchFactor, final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().outE(branchFactor, labels);
	}

	public EdgeTraversal<?, ?, ?> outE(final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().outE(labels);
	}

	public EdgeTraversal<?, ?, ?> inE(final int branchFactor, final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().inE(branchFactor, labels);
	}

	public EdgeTraversal<?, ?, ?> inE(final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().inE(labels);
	}

	public EdgeTraversal<?, ?, ?> bothE(final int branchFactor, final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().bothE(branchFactor, labels);
	}

	public EdgeTraversal<?, ?, ?> bothE(final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().bothE(labels);
	}

	/**
	 * Create edges from the framed vertex to the supplied vertex with the
	 * supplied labels
	 * 
	 * @param vertex
	 *            The vertex to link to.
	 * @param labels
	 *            The labels for the edges.
	 */
	public void linkOut(FramedVertex vertex, String... labels) {
		for (String label : labels) {
			traversal().linkOut(label, vertex).iterate();
		}
	}

	/**
	 * Create edges from the supplied vertex to the framed vertex with the
	 * supplied labels
	 * 
	 * @param vertex
	 *            The vertex to link from.
	 * @param labels
	 *            The labels for the edges.
	 */
	public void linkIn(FramedVertex vertex, String... labels) {
		for (String label : labels) {
			traversal().linkIn(label, vertex).iterate();
		}
	}

	/**
	 * Create edges from the supplied vertex to the framed vertex and vice versa
	 * with the supplied labels
	 * 
	 * @param vertex
	 *            The vertex to link to and from.
	 * @param labels
	 *            The labels for the edges.
	 */
	public void linkBoth(FramedVertex vertex, String... labels) {
		for (String label : labels) {
			traversal().linkBoth(label, vertex).iterate();
		}
	}

	/**
	 * Remove all edges to the supplied vertex with the supplied labels.
	 * 
	 * @param vertex
	 *            The vertex to removed the edges to.
	 * @param labels
	 *            The labels of the edges.
	 */
	public void unlinkOut(FramedVertex vertex, String... labels) {
		if (vertex != null) {
			outE(labels).mark().inV().retain(vertex).back().removeAll();
		} else {
			outE(labels).removeAll();
		}
	}

	/**
	 * Remove all edges to the supplied vertex with the supplied labels.
	 * 
	 * @param vertex
	 *            The vertex to removed the edges from.
	 * @param labels
	 *            The labels of the edges.
	 */
	public void unlinkIn(FramedVertex vertex, String... labels) {
		if (vertex != null) {
			inE(labels).mark().outV().retain(vertex).back().removeAll();
		} else {
			inE(labels).removeAll();
		}
	}

	/**
	 * Remove all edges to/from the supplied vertex with the supplied labels.
	 * 
	 * @param vertex
	 *            The vertex to removed the edges to/from.
	 * @param labels
	 *            The labels of the edges.
	 */
	public void unlinkBoth(FramedVertex vertex, String... labels) {
		if (vertex != null) {
			bothE(labels).mark().bothV().retain(vertex).back().removeAll();
		} else {
			bothE(labels).removeAll();
		}
	}

	/**
	 * Remove all out edges with the labels and then add a single edge to the
	 * supplied vertex.
	 * 
	 * @param vertex
	 *            the vertex to link to.
	 * @param labels
	 *            The labels of the edges.
	 */
	public void setLinkOut(FramedVertex vertex, String... labels) {
		unlinkOut(null, labels);
		if (vertex != null) {
			linkOut(vertex, labels);
		}
	}

	/**
	 * Remove all in edges with the labels and then add a single edge from the
	 * supplied vertex.
	 * 
	 * @param vertex
	 *            the vertex to link from.
	 * @param labels
	 *            The labels of the edges.
	 */
	public void setLinkIn(FramedVertex vertex, String... labels) {
		unlinkIn(null, labels);
		if (vertex != null) {
			linkIn(vertex, labels);
		}
	}

	/**
	 * Remove all in edges with the labels and then add a edges from the
	 * supplied vertex and to the supplied vertex.
	 * 
	 * @param vertex
	 *            the vertex to link from.
	 * @param labels
	 *            The labels of the edges.
	 */
	public void setLinkBoth(FramedVertex vertex, String... labels) {
		unlinkBoth(null, labels);
		if (vertex != null) {
			linkBoth(vertex, labels);
		}
	}

	/**
	 * Remove all out edges with the labels and then add a single edge to a new
	 * vertex.
	 * 
	 * @param kind
	 *            the vertex type to link to.
	 * @param labels
	 *            The labels of the edges.
	 */
	public <K extends FramedVertex> FramedVertex setLinkOut(Class<K> kind, String... labels) {
		K vertex = graph().addFramedVertex(kind);
		setLinkOut(vertex, labels);
		return vertex;
	}

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
	public <K extends FramedVertex> FramedVertex setLinkOutExplicit(Class<K> kind, String... labels) {
		K vertex = graph().addFramedVertexExplicit(kind);
		setLinkOut(vertex, labels);
		return vertex;
	}

	/**
	 * Remove all out edges with the labels and then add a single edge from a
	 * new vertex.
	 * 
	 * @param kind
	 *            the vertex type to link to.
	 * @param labels
	 *            The labels of the edges.
	 */
	public <K extends FramedVertex> FramedVertex setLinkIn(Class<K> kind, String... labels) {
		K vertex = graph().addFramedVertex(kind);
		setLinkIn(vertex, labels);
		return vertex;
	}

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
	public <K extends FramedVertex> FramedVertex setLinkInExplicit(Class<K> kind, String... labels) {
		K vertex = graph().addFramedVertexExplicit(kind);
		setLinkIn(vertex, labels);
		return vertex;
	}

	/**
	 * Remove all out edges with the labels and then add edges to/from a new
	 * vertex.
	 * 
	 * @param kind
	 *            the vertex type to link to.
	 * @param labels
	 *            The labels of the edges.
	 */
	public <K extends FramedVertex> FramedVertex setLinkBoth(Class<K> kind, String... labels) {
		K vertex = graph().addFramedVertex(kind);
		setLinkBoth(vertex, labels);
		return vertex;
	}

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
	public <K extends FramedVertex> FramedVertex setLinkBothExplicit(Class<K> kind, String... labels) {
		K vertex = graph().addFramedVertexExplicit(kind);
		setLinkBoth(vertex, labels);
		return vertex;
	}

	/**
	 * Shortcut to get frameTraversal of current element
	 * 
	 * @return
	 */
	public VertexTraversal<?, ?, ?> traversal() {
		return new TraversalImpl(graph(), this).castToVertices();
	}

	/**
	 * Output the vertex as json.
	 * 
	 * @return
	 */
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		if (getId() instanceof Number) {
			json.addProperty("id", getId(Number.class));
		}
		if (getId() instanceof String) {
			json.addProperty("id", getId(String.class));
		}
		json.addProperty("elementClass", "vertex");
		for (String key : getPropertyKeys()) {

			Object value = getProperty(key);
			if (value instanceof Number) {
				json.addProperty(key, (Number) value);
			} else if (value instanceof String) {
				json.addProperty(key, (String) value);
			}
		}
		return json;
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(toJson());
	}

	/**
	 * Reframe this element as a different type of frame.
	 *
	 * @param kind The new kind of frame.
	 * @return The new frame
	 */
	public <T extends FramedVertex> T reframe(Class<T> kind) {
		return graph().frameElement(element(), kind);
	}

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
	public <T extends FramedVertex> T reframeExplicit(Class<T> kind) {
		return graph().frameElementExplicit(element(), kind);
	}
}

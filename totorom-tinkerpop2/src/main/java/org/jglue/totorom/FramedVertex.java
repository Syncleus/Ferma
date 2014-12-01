/**
 * Copyright 2014-Infinity Bryn Cooke
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 * This project is derived from code in the Tinkerpop project under the following licenses:
 *
 * Tinkerpop3
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Tinkerpop2
 * Copyright (c) 2009-Infinity, TinkerPop [http://tinkerpop.com]
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the TinkerPop nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL TINKERPOP BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.jglue.totorom;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * The base class that all vertex frames must extend.
 * 
 * @author Bryn Cooke (http://jglue.org)
 */
/**
 * @author bryn
 *
 */
public abstract class FramedVertex extends FramedElement {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.FramedElement#element()
	 */
	protected Vertex element() {
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
	protected <T extends FramedEdge> T addEdge(String label, FramedVertex inVertex, Class<T> kind) {

		Edge edge = element().addEdge(label, inVertex.element());
		T framedEdge = graph().frameNewElement(edge, kind);
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
	protected TEdge addEdge(String label, FramedVertex inVertex) {
		return addEdge(label, inVertex, TEdge.class);
	}

	protected VertexTraversal<?, ?, ?> out(final int branchFactor, final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().out(branchFactor, labels);
	}

	protected VertexTraversal<?, ?, ?> out(final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().out(labels);
	}

	protected VertexTraversal<?, ?, ?> in(final int branchFactor, final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().in(branchFactor, labels);
	}

	protected VertexTraversal<?, ?, ?> in(final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().in(labels);
	}

	protected VertexTraversal<?, ?, ?> both(final int branchFactor, final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().both(branchFactor, labels);
	}

	protected VertexTraversal<?, ?, ?> both(final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().both(labels);
	}

	protected EdgeTraversal<?, ?, ?> outE(final int branchFactor, final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().outE(branchFactor, labels);
	}

	protected EdgeTraversal<?, ?, ?> outE(final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().outE(labels);
	}

	protected EdgeTraversal<?, ?, ?> inE(final int branchFactor, final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().inE(branchFactor, labels);
	}

	protected EdgeTraversal<?, ?, ?> inE(final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().inE(labels);
	}

	protected EdgeTraversal<?, ?, ?> bothE(final int branchFactor, final String... labels) {
		return new TraversalImpl(graph(), this).castToVertices().bothE(branchFactor, labels);
	}

	protected EdgeTraversal<?, ?, ?> bothE(final String... labels) {
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
	protected void linkOut(FramedVertex vertex, String... labels) {
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
	protected void linkIn(FramedVertex vertex, String... labels) {
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
	protected void linkBoth(FramedVertex vertex, String... labels) {
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
	protected void unlinkOut(FramedVertex vertex, String... labels) {
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
	protected void unlinkIn(FramedVertex vertex, String... labels) {
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
	protected void unlinkBoth(FramedVertex vertex, String... labels) {
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
	protected void setLinkOut(FramedVertex vertex, String... labels) {
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
	protected void setLinkIn(FramedVertex vertex, String... labels) {
		unlinkIn(null, labels);
		if (vertex != null) {
			linkIn(vertex, labels);
		}
	}

	/**
	 * Remove all edges with the labels and then add a edges from the
	 * supplied vertex and to the supplied vertex.
	 * 
	 * @param vertex
	 *            the vertex to link from.
	 * @param labels
	 *            The labels of the edges.
	 */
	protected void setLinkBoth(FramedVertex vertex, String... labels) {
		unlinkBoth(null, labels);
		if (vertex != null) {
			linkBoth(vertex, labels);
		}
	}

	/**
	 * Remove all out edges with the labels and then add a single edge to a new
	 * vertex.
	 * 
	 * @param vertex
	 *            the vertex to link to.
	 * @param labels
	 *            The labels of the edges.
	 */
	protected <K extends FramedVertex> FramedVertex setLinkOut(Class<K> kind, String... labels) {
		K vertex = graph().addVertex(kind);
		setLinkOut(vertex, labels);
		return vertex;
	}

	/**
	 * Remove all in edges with the labels and then add a single edge from a
	 * new vertex.
	 * 
	 * @param vertex
	 *            the vertex to link to.
	 * @param labels
	 *            The labels of the edges.
	 */
	protected <K extends FramedVertex> FramedVertex setLinkIn(Class<K> kind, String... labels) {
		K vertex = graph().addVertex(kind);
		setLinkIn(vertex, labels);
		return vertex;
	}

	/**
	 * Remove all edges with the labels and then add edges to/from a new
	 * vertex.
	 * 
	 * @param vertex
	 *            the vertex to link to.
	 * @param labels
	 *            The labels of the edges.
	 */
	protected <K extends FramedVertex> FramedVertex setLinkBoth(Class<K> kind, String... labels) {
		K vertex = graph().addVertex(kind);
		setLinkBoth(vertex, labels);
		return vertex;
	}

	/**
	 * Shortcut to get frameTraversal of current element
	 * 
	 * @return
	 */
	protected VertexTraversal<?, ?, ?> traversal() {
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
	 * @param kind
	 *            The new kind of frame.
	 * @return The new frame
	 */
	protected <T extends FramedVertex> T reframe(Class<T> kind) {
		return graph().frameElement(element(), kind);
	}
}

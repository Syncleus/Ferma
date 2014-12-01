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
public abstract class AbstractVertexFrame extends AbstractElementFrame implements VertexFrame {

	@Override
	public Vertex element() {
		return (Vertex) super.element();
	};

	@Override
	public <T> T addFramedEdge(final String label, final VertexFrame inVertex, final Class<T> kind) {

		final Edge edge = element().addEdge(label, inVertex.element());
		final T framedEdge = graph().frameNewElement(edge, kind);
		return framedEdge;
	}

	@Override
	public <T> T addFramedEdgeExplicit(final String label, final VertexFrame inVertex, final Class<T> kind) {

		final Edge edge = element().addEdge(label, inVertex.element());
		final T framedEdge = graph().frameNewElementExplicit(edge, kind);
		return framedEdge;
	}

	@Override
	public TEdge addFramedEdge(final String label, final VertexFrame inVertex) {
		return addFramedEdge(label, inVertex, TEdge.class);
	}

	@Override
	public TEdge addFramedEdgeExplicit(final String label, final VertexFrame inVertex) {
		return addFramedEdgeExplicit(label, inVertex, TEdge.class);
	}

	@Override
	public VertexTraversal<?, ?, ?> out(final int branchFactor, final String... labels) {
		return new SimpleTraversal(graph(), this).castToVertices().out(branchFactor, labels);
	}

	@Override
	public VertexTraversal<?, ?, ?> out(final String... labels) {
		return new SimpleTraversal(graph(), this).castToVertices().out(labels);
	}

	@Override
	public VertexTraversal<?, ?, ?> in(final int branchFactor, final String... labels) {
		return new SimpleTraversal(graph(), this).castToVertices().in(branchFactor, labels);
	}

	@Override
	public VertexTraversal<?, ?, ?> in(final String... labels) {
		return new SimpleTraversal(graph(), this).castToVertices().in(labels);
	}

	@Override
	public VertexTraversal<?, ?, ?> both(final int branchFactor, final String... labels) {
		return new SimpleTraversal(graph(), this).castToVertices().both(branchFactor, labels);
	}

	@Override
	public VertexTraversal<?, ?, ?> both(final String... labels) {
		return new SimpleTraversal(graph(), this).castToVertices().both(labels);
	}

	@Override
	public EdgeTraversal<?, ?, ?> outE(final int branchFactor, final String... labels) {
		return new SimpleTraversal(graph(), this).castToVertices().outE(branchFactor, labels);
	}

	@Override
	public EdgeTraversal<?, ?, ?> outE(final String... labels) {
		return new SimpleTraversal(graph(), this).castToVertices().outE(labels);
	}

	@Override
	public EdgeTraversal<?, ?, ?> inE(final int branchFactor, final String... labels) {
		return new SimpleTraversal(graph(), this).castToVertices().inE(branchFactor, labels);
	}

	@Override
	public EdgeTraversal<?, ?, ?> inE(final String... labels) {
		return new SimpleTraversal(graph(), this).castToVertices().inE(labels);
	}

	@Override
	public EdgeTraversal<?, ?, ?> bothE(final int branchFactor, final String... labels) {
		return new SimpleTraversal(graph(), this).castToVertices().bothE(branchFactor, labels);
	}

	@Override
	public EdgeTraversal<?, ?, ?> bothE(final String... labels) {
		return new SimpleTraversal(graph(), this).castToVertices().bothE(labels);
	}

	@Override
	public void linkOut(final VertexFrame vertex, final String... labels) {
		for (final String label : labels) {
			traversal().linkOut(label, vertex).iterate();
		}
	}

	@Override
	public void linkIn(final VertexFrame vertex, final String... labels) {
		for (final String label : labels) {
			traversal().linkIn(label, vertex).iterate();
		}
	}

	@Override
	public void linkBoth(final VertexFrame vertex, final String... labels) {
		for (final String label : labels) {
			traversal().linkBoth(label, vertex).iterate();
		}
	}

	@Override
	public void unlinkOut(final VertexFrame vertex, final String... labels) {
		if (vertex != null) {
			outE(labels).mark().inV().retain(vertex).back().removeAll();
		} else {
			outE(labels).removeAll();
		}
	}

	@Override
	public void unlinkIn(final VertexFrame vertex, final String... labels) {
		if (vertex != null) {
			inE(labels).mark().outV().retain(vertex).back().removeAll();
		} else {
			inE(labels).removeAll();
		}
	}

	@Override
	public void unlinkBoth(final VertexFrame vertex, final String... labels) {
		if (vertex != null) {
			bothE(labels).mark().bothV().retain(vertex).back().removeAll();
		} else {
			bothE(labels).removeAll();
		}
	}

	@Override
	public void setLinkOut(final VertexFrame vertex, final String... labels) {
		unlinkOut(null, labels);
		if (vertex != null) {
			linkOut(vertex, labels);
		}
	}

	@Override
	public void setLinkIn(final VertexFrame vertex, final String... labels) {
		unlinkIn(null, labels);
		if (vertex != null) {
			linkIn(vertex, labels);
		}
	}

	@Override
	public void setLinkBoth(final VertexFrame vertex, final String... labels) {
		unlinkBoth(null, labels);
		if (vertex != null) {
			linkBoth(vertex, labels);
		}
	}

	@Override
	public <K> K setLinkOut(final Class<K> kind, final String... labels) {
		final K vertex = graph().addFramedVertex(kind);
		setLinkOut((VertexFrame)vertex, labels);
		return vertex;
	}

	@Override
	public <K> K setLinkOutExplicit(final Class<K> kind, final String... labels) {
		final K vertex = graph().addFramedVertexExplicit(kind);
		setLinkOut((VertexFrame)vertex, labels);
		return vertex;
	}

	@Override
	public <K> K setLinkIn(final Class<K> kind, final String... labels) {
		final K vertex = graph().addFramedVertex(kind);
		setLinkIn((VertexFrame)vertex, labels);
		return vertex;
	}

	@Override
	public <K> K setLinkInExplicit(final Class<K> kind, final String... labels) {
		final K vertex = graph().addFramedVertexExplicit(kind);
		setLinkIn((VertexFrame)vertex, labels);
		return vertex;
	}

	@Override
	public <K> K setLinkBoth(final Class<K> kind, final String... labels) {
		final K vertex = graph().addFramedVertex(kind);
		setLinkBoth((VertexFrame)vertex, labels);
		return vertex;
	}

	@Override
	public <K> K setLinkBothExplicit(final Class<K> kind, final String... labels) {
		final K vertex = graph().addFramedVertexExplicit(kind);
		setLinkBoth((VertexFrame)vertex, labels);
		return vertex;
	}

	@Override
	public VertexTraversal<?, ?, ?> traversal() {
		return new SimpleTraversal(graph(), this).castToVertices();
	}

	@Override
	public JsonObject toJson() {
		final JsonObject json = new JsonObject();
		if (getId() instanceof Number) {
			json.addProperty("id", getId(Number.class));
		}
		if (getId() instanceof String) {
			json.addProperty("id", getId(String.class));
		}
		json.addProperty("elementClass", "vertex");
		for (final String key : getPropertyKeys()) {

			final Object value = getProperty(key);
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
		final Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(toJson());
	}

	@Override
	public <T> T reframe(final Class<T> kind) {
		return graph().frameElement(element(), kind);
	}

	@Override
	public <T> T reframeExplicit(final Class<T> kind) {
		return graph().frameElementExplicit(element(), kind);
	}
}

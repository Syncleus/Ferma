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

/**
 * The base class that all edge frames must extend.
 */
public abstract class FramedEdge extends FramedElement {

	public Edge element() {
		return (Edge) super.element();
	};

	/**
	 * @return The label associated with this edge
	 */
	public String getLabel() {
		return element().getLabel();
	}

	/**
	 * @return The in vertex for this edge.
	 */
	public VertexTraversal<?, ?, ?> inV() {
		return new TraversalImpl(graph(), this).castToEdges().inV();
	}

	/**
	 * @return The out vertex of this edge.
	 */
	public VertexTraversal<?, ?, ?> outV() {
		return new TraversalImpl(graph(), this).castToEdges().outV();
	}

	/**
	 * @return The vertices for this edge.
	 */
	public VertexTraversal<?, ?, ?> bothV() {
		return new TraversalImpl(graph(), this).castToEdges().bothV();
	}

	/**
	 * Shortcut to get frameTraversal of current element
	 * 
	 * @return
	 */
	public EdgeTraversal<?, ?, ?> traversal() {
		return new TraversalImpl(graph(), this).castToEdges();
	}

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		if (getId() instanceof Number) {
			json.addProperty("id", getId(Number.class));
		}
		if (getId() instanceof String) {
			json.addProperty("id", getId(String.class));
		}
		json.addProperty("elementClass", "edge");
		json.addProperty("label", getLabel());
		for (String key : getPropertyKeys()) {

			Object value = getProperty(key);
			if (value instanceof Number) {
				json.addProperty(key, (Number) value);
			} else if (value instanceof String) {
				json.addProperty(key, (String) value);
			}
		}
		json.add("outV", outV().next().toJson());
		json.add("inV", inV().next().toJson());
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
	public <T extends FramedEdge> T reframe(Class<T> kind) {
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
	public <T extends FramedEdge> T reframeExplicit(Class<T> kind) {
		return graph().frameElementExplicit(element(), kind);
	}
}

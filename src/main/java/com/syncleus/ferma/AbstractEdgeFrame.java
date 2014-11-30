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
public abstract class AbstractEdgeFrame extends AbstractElementFrame implements EdgeFrame {

	@Override
	public Edge element() {
		return (Edge) super.element();
	};

	@Override
	public String getLabel() {
		return element().getLabel();
	}

	@Override
	public VertexTraversal<?, ?, ?> inV() {
		return new SimpleTraversal(graph(), this).castToEdges().inV();
	}

	@Override
	public VertexTraversal<?, ?, ?> outV() {
		return new SimpleTraversal(graph(), this).castToEdges().outV();
	}

	@Override
	public VertexTraversal<?, ?, ?> bothV() {
		return new SimpleTraversal(graph(), this).castToEdges().bothV();
	}

	@Override
	public EdgeTraversal<?, ?, ?> traversal() {
		return new SimpleTraversal(graph(), this).castToEdges();
	}

	@Override
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
	
	@Override
	public <T extends AbstractEdgeFrame> T reframe(Class<T> kind) {
		return graph().frameElement(element(), kind);
	}

	@Override
	public <T extends AbstractEdgeFrame> T reframeExplicit(Class<T> kind) {
		return graph().frameElementExplicit(element(), kind);
	}
}

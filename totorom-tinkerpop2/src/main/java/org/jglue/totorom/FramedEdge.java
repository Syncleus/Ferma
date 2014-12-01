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

/**
 * The base class that all edge frames must extend.
 * 
 * @author Bryn Cooke (http://jglue.org)
 */
public abstract class FramedEdge extends FramedElement {

	protected Edge element() {
		return (Edge) super.element();
	};

	/**
	 * @return The label associated with this edge
	 */
	protected String getLabel() {
		return element().getLabel();
	}

	/**
	 * @return The in vertex for this edge.
	 */
	protected VertexTraversal<?, ?, ?> inV() {
		return new TraversalImpl(graph(), this).castToEdges().inV();
	}

	/**
	 * @return The out vertex of this edge.
	 */
	protected VertexTraversal<?, ?, ?> outV() {
		return new TraversalImpl(graph(), this).castToEdges().outV();
	}

	/**
	 * @return The vertices for this edge.
	 */
	protected VertexTraversal<?, ?, ?> bothV() {
		return new TraversalImpl(graph(), this).castToEdges().bothV();
	}

	/**
	 * Shortcut to get frameTraversal of current element
	 * 
	 * @return
	 */
	protected EdgeTraversal<?, ?, ?> traversal() {
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
	 * @param kind
	 *            The new kind of frame.
	 * @return The new frame
	 */
	protected <T extends FramedEdge> T reframe(Class<T> kind) {
		return graph().frameElement(element(), kind);
	}
}

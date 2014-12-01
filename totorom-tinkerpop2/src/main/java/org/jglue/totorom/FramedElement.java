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

import java.util.Set;

import com.tinkerpop.blueprints.Element;

/**
 * The base of all framed elements.
 * 
 * @author Bryn Cooke (http://jglue.org)
 */

public abstract class FramedElement {

	private Element element;
	private FramedGraph graph;

	protected void init(FramedGraph graph, Element element) {
		this.graph = graph;
		this.element = element;
	}

	protected void init() {

	}

	/**
	 * @return The id of this element.
	 */
	protected <N> N getId() {
		return (N) element.getId();
	}

	/**
	 * @return The property keys of this element.
	 */
	protected Set<String> getPropertyKeys() {
		return element.getPropertyKeys();
	}

	/**
	 * Remove this element from the graph.
	 */
	protected void remove() {
		element.remove();
	}

	/**
	 * @return The underlying element.
	 */
	protected Element element() {
		return element;
	}

	/**
	 * @return The underlying graph.
	 */
	protected FramedGraph graph() {
		return graph;
	}

	/**
	 * Return a property value.
	 * 
	 * @param name
	 *            The name of the property.
	 * @return the value of the property or null if none was present.
	 */
	protected <T> T getProperty(String name) {
		return element.getProperty(name);
	}

	/**
	 * Return a property value.
	 * 
	 * @param name
	 *            The name of the property.
	 * @param type
	 *            The type of the property.
	 * 
	 * @return the value of the property or null if none was present.
	 */
	protected <T> T getProperty(String name, Class<T> type) {
		if (type.isEnum()) {
			return (T) Enum.valueOf((Class<Enum>) type, (String) element.getProperty(name));
		}

		return element.getProperty(name);
	}

	/**
	 * Set a property value.
	 * 
	 * @param name
	 *            The name of the property.
	 * @param value
	 *            The value of the property.
	 */
	protected void setProperty(String name, Object value) {
		if (value == null) {
			element.removeProperty(name);
		} else {
			if (value instanceof Enum) {
				element.setProperty(name, value.toString());
			} else {
				element.setProperty(name, value);
			}
		}
	}

	/**
	 * Query over all vertices in the graph.
	 * 
	 * @return The query.
	 */
	protected VertexTraversal<?, ?, ?> V() {
		return graph.V();
	}

	/**
	 * Query over all edges in the graph.
	 * 
	 * @return The query.
	 */
	protected EdgeTraversal<?, ?, ?> E() {
		return graph.E();
	}

	/**
	 * Query over a list of vertices in the graph.
	 * 
	 * @param ids
	 *            The ids of the vertices.
	 * @return The query.
	 */
	protected VertexTraversal<?, ?, ?> v(final Object... ids) {
		return graph.v(ids);
	}

	/**
	 * Query over a list of edges in the graph.
	 * 
	 * @param ids
	 *            The ids of the edges.
	 * @return The query.
	 */
	protected EdgeTraversal<?, ?, ?> e(final Object... ids) {
		return graph.e(ids);
	}

	@Override
	public int hashCode() {
		return element.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FramedElement other = (FramedElement) obj;
		if (element == null) {
			if (other.element != null)
				return false;
		} else if (!element.equals(other.element))
			return false;
		return true;
	}

	protected <N> N getId(Class<N> clazz) {

		return (N) getId();
	}

	@Override
	public String toString() {

		return element().toString();
	}
	
	
}

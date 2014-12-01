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

import java.io.Closeable;
import java.util.Collection;
import java.util.Iterator;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;

/**
 * The primary class for framing your blueprints graphs.
 * 
 * @author Bryn Cooke (http://jglue.org)
 */

public class FramedGraph {
	Graph delegate;

	private TypeResolver resolver;
	private FrameFactory builder;

	/**
	 * Construct a framed graph.
	 * 
	 * @param delegate
	 *            The graph to wrap.
	 * @param builder
	 *            The builder that will construct frames.
	 * @param resolver
	 *            The type resolver that will decide the final frame type.
	 */
	public FramedGraph(Graph delegate, FrameFactory builder, TypeResolver resolver) {
		this.delegate = delegate;
		this.resolver = resolver;
		this.builder = builder;
	}

	/**
	 * Construct an untyped framed graph using no special frame construction.
	 * 
	 * @param delegate
	 *            The graph to wrap.
	 */
	public FramedGraph(Graph delegate) {
		this(delegate, FrameFactory.Default, TypeResolver.Untyped);
	}

	/**
	 * @return A transaction object that is {@link Closeable}.
	 */
	public Transaction tx() {
		if (delegate instanceof TransactionalGraph) {
			return new Transaction((TransactionalGraph) delegate);
		} else {
			return new Transaction((TransactionalGraph) null);
		}
	}

	/**
	 * Close the delegate graph.
	 */
	public void close() {
		delegate.shutdown();
	}

	<T extends FramedElement> T frameElement(Element e, Class<T> kind) {
		if(e == null){
			return null;
		}

		Class<T> frameType = (kind == TVertex.class || kind == TEdge.class) ? kind : resolver.resolve(e, kind);

		T framedElement = builder.create(e, frameType);
		framedElement.init(this, e);
		return framedElement;
	}

	<T extends FramedElement> T frameNewElement(Element e, Class<T> kind) {
		T t = frameElement(e, kind);
		resolver.init(e, kind);
		return t;
	}

	<T extends FramedElement> Iterator<T> frame(Iterator<? extends Element> pipeline, final Class<T> kind) {
		return Iterators.transform(pipeline, new Function<Element, T>() {

			@Override
			public T apply(Element element) {
				return frameElement(element, kind);
			}

		});
	}

	/**
	 * Add a vertex to the graph
	 * 
	 * @param kind
	 *            The kind of the frame.
	 * @return The framed vertex.
	 */
	public <T extends FramedVertex> T addVertex(Class<T> kind) {
		T framedVertex = frameNewElement(delegate.addVertex(null), kind);
		framedVertex.init();
		return framedVertex;
	}
	
	/**
	 * Add a vertex to the graph using a frame type of {@link TVertex}.
	 * 
	 * @return The framed vertex.
	 */
	public TVertex addVertex() {
		
		return addVertex(TVertex.class);
	}

	
	
	/**
	 * Query over all vertices in the graph.
	 * 
	 * @return The query.
	 */
	public VertexTraversal<?, ?, ?> V() {
		return new GlobalVertexTraversal(this, delegate);
	}

	/**
	 * Query over all edges in the graph.
	 * 
	 * @return The query.
	 */
	public EdgeTraversal<?, ?, ?> E() {
		return new TraversalImpl(this, delegate).E();
	}

	/**
	 * Query over a list of vertices in the graph.
	 * 
	 * @param ids
	 *            The ids of the vertices.
	 * @return The query.
	 */
	public VertexTraversal<?, ?, ?> v(final Collection<?> ids) {
		return new TraversalImpl(this, Iterators.transform(ids.iterator(), new Function<Object, Vertex>() {

			@Override
			public Vertex apply(Object id) {
				return delegate.getVertex(id);
			}

		})).castToVertices();
	}
	
	/**
	 * Query over a list of vertices in the graph.
	 * 
	 * @param ids
	 *            The ids of the vertices.
	 * @return The query.
	 */
	public VertexTraversal<?, ?, ?> v(final Object... ids) {
		return new TraversalImpl(this, Iterators.transform(Iterators.forArray(ids), new Function<Object, Vertex>() {

			@Override
			public Vertex apply(Object id) {
				return delegate.getVertex(id);
			}

		})).castToVertices();
	}

	/**
	 * Query over a list of edges in the graph.
	 * 
	 * @param ids
	 *            The ids of the edges.
	 * @return The query.
	 */
	public EdgeTraversal<?, ?, ?> e(final Object... ids) {
		return new TraversalImpl(this, Iterators.transform(Iterators.forArray(ids), new Function<Object, Edge>() {

			@Override
			public Edge apply(Object id) {
				return delegate.getEdge(id);
			}

		})).castToEdges();
	}
	
	
	/**
	 * Query over a list of edges in the graph.
	 * 
	 * @param ids
	 *            The ids of the edges.
	 * @return The query.
	 */
	public EdgeTraversal<?, ?, ?> e(final Collection<?> ids) {
		return new TraversalImpl(this, Iterators.transform(ids.iterator(), new Function<Object, Edge>() {

			@Override
			public Edge apply(Object id) {
				return delegate.getEdge(id);
			}

		})).castToEdges();
	}

}

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

import java.io.Closeable;
import java.util.Collection;
import java.util.Iterator;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.syncleus.ferma.annotations.AnnotationFrameFactory;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.Vertex;

/**
 * The primary class for framing your blueprints graphs.
 */

public class FramedGraph {
	private Graph delegate;

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
		this(delegate, FrameFactory.Default, TypeResolver.UNTYPED);
	}

	/**
	 * Construct an untyped framed graph using no special frame construction.
	 *
	 * @param delegate
	 *            The graph to wrap.
	 * @param resolver
	 *            The type resolver that will decide the final frame type.
	 */
	public FramedGraph(Graph delegate, final TypeResolver resolver) {
		this(delegate, FrameFactory.Default, resolver);
	}

	/**
	 * Construct an untyped framed graph using no special frame construction.
	 *
	 * @param delegate
	 *            The graph to wrap.
	 * @param annotatedTypes
	 *            The types to be consider for type resolution.
	 */
	public FramedGraph(Graph delegate, final Collection<? extends Class<?>> annotatedTypes) {
		this(delegate, new AnnotationFrameFactory(annotatedTypes), TypeResolver.ANNOTATED);
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

	<T> T frameElement(Element e, Class<T> kind) {

		Class<T> frameType = (kind == TVertex.class || kind == TEdge.class) ? kind : resolver.resolve(e, kind);

		T framedElement = builder.create(e, frameType);
		((FramedElement)framedElement).init(this, e);
		return framedElement;
	}

	<T> T frameNewElement(Element e, Class<T> kind) {
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
	public <T> T addVertex(Class<T> kind) {
		T framedVertex = frameNewElement(delegate.addVertex(null), kind);
		((FramedVertex)framedVertex).init();
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
	 * Add a edge to the graph
	 *
	 * @param kind
	 *            The kind of the frame.
	 * @return The framed edge.
	 */
	public <T> T addEdge(final FramedVertex source, final FramedVertex destination, final String label, Class<T> kind) {
		T framedEdge = frameNewElement(this.delegate.addEdge(null, source.element(), destination.element(), label), kind);
		((FramedEdge)framedEdge).init();
		return framedEdge;
	}

	/**
	 * Add a edge to the graph using a frame type of {@link TEdge}.
	 *
	 * @return The framed edge.
	 */
	public TEdge addEdge(final FramedVertex source, final FramedVertex destination, final String label) {

		return addEdge(source, destination, label, TEdge.class);
	}

	/**
	 * Query over all vertices in the graph.
	 * 
	 * @return The query.
	 */
	public VertexTraversal<?, ?, ?> V() {
		return new TraversalImpl(this, delegate).V();
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

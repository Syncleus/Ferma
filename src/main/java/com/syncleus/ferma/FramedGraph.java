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
import java.util.*;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.syncleus.ferma.annotations.AnnotationFrameFactory;
import com.tinkerpop.blueprints.*;

/**
 * The primary class for framing your blueprints graphs.
 */

public class FramedGraph implements Graph {
	private Graph delegate;

	private final TypeResolver defaultResolver;
	private final TypeResolver untypedResolver;
	private final FrameFactory builder;
	private final ReflectionCache reflections;

	/**
	 * Construct a framed graph.
	 * 
	 * @param delegate
	 *            The graph to wrap.
	 * @param builder
	 *            The builder that will construct frames.
	 * @param defaultResolver
	 *            The type defaultResolver that will decide the final frame type.
	 */
	public FramedGraph(final Graph delegate, final FrameFactory builder, final TypeResolver defaultResolver) {
		this.reflections = null;
		this.delegate = delegate;
		this.defaultResolver = defaultResolver;
		this.untypedResolver = new UntypedTypeResolver();
		this.builder = builder;
	}

	/**
	 * Construct an untyped framed graph without annotation support
	 * 
	 * @param delegate
	 *            The graph to wrap.
	 */
	public FramedGraph(final Graph delegate) {
		this.reflections = new ReflectionCache();
		this.delegate = delegate;
		this.defaultResolver = new UntypedTypeResolver();
		this.untypedResolver = this.defaultResolver;
		this.builder = new DefaultFrameFactory();
	}

	/**
	 * Construct an untyped framed graph without annotation support
	 *
	 * @param reflections
	 * 			  A RefelctionCache used to determine reflection and hierarchy of classes.
	 * @param delegate
	 *            The graph to wrap.
	 */
	public FramedGraph(final Graph delegate, final ReflectionCache reflections) {
		this.reflections = reflections;
		this.delegate = delegate;
		this.defaultResolver = new UntypedTypeResolver();
		this.untypedResolver = this.defaultResolver;
		this.builder = new DefaultFrameFactory();
	}

	/**
	 * Construct a framed graph without annotation support.
	 *
	 * @param delegate
	 *            The graph to wrap.
	 * @param defaultResolver
	 *            The type defaultResolver that will decide the final frame type.
	 */
	public FramedGraph(Graph delegate, final TypeResolver defaultResolver) {
		this(delegate, new DefaultFrameFactory(), defaultResolver);
	}

	/**
	 * Construct a framed graph with the specified typeResolution and annotation support
	 *
	 * @param delegate
	 *            The graph to wrap.
	 * @param typeResolution
	 * 			  True if type resolution is to be automatically handled by default, false causes explicit typing by
	 * @param annotationsSupported
	 * 			  True if annotated classes will be supported, false otherwise.
	 */
	public FramedGraph(Graph delegate, boolean typeResolution, final boolean annotationsSupported) {
		this.reflections = new ReflectionCache();
		this.delegate = delegate;
		if( typeResolution) {
			this.defaultResolver = new SimpleTypeResolver(this.reflections);
			this.untypedResolver = new UntypedTypeResolver();
		}
		else {
			this.defaultResolver = new UntypedTypeResolver();
			this.untypedResolver = this.defaultResolver;
		}
		if( annotationsSupported )
			this.builder = new AnnotationFrameFactory(this.reflections);
		else
			this.builder = new DefaultFrameFactory();
	}

	/**
	 * Construct a framed graph with the specified typeResolution and annotation support
	 *
	 * @param delegate
	 *            The graph to wrap.
	 * @param reflections
	 * 			  A RefelctionCache used to determine reflection and hierarchy of classes.
	 * @param typeResolution
	 * 			  True if type resolution is to be automatically handled by default, false causes explicit typing by
	 * @param annotationsSupported
	 * 			  True if annotated classes will be supported, false otherwise.
	 */
	public FramedGraph(Graph delegate, final ReflectionCache reflections, boolean typeResolution, final boolean annotationsSupported) {
		this.reflections = reflections;
		this.delegate = delegate;
		if( typeResolution) {
			this.defaultResolver = new SimpleTypeResolver(this.reflections);
			this.untypedResolver = new UntypedTypeResolver();
		}
		else {
			this.defaultResolver = new UntypedTypeResolver();
			this.untypedResolver = this.defaultResolver;
		}
		if( annotationsSupported )
			this.builder = new AnnotationFrameFactory(this.reflections);
		else
			this.builder = new DefaultFrameFactory();
	}

	/**
	 * Construct a Typed framed graph with the specified type resolution and with annotation support
	 *
	 * @param delegate
	 *            The graph to wrap.
	 * @param types
	 *            The types to be consider for type resolution.
	 */
	public FramedGraph(Graph delegate, final Collection<? extends Class<?>> types) {
		this.reflections = new ReflectionCache(types);
		this.delegate = delegate;
		this.defaultResolver = new SimpleTypeResolver(this.reflections);
		this.untypedResolver = new UntypedTypeResolver();
		this.builder = new AnnotationFrameFactory(this.reflections);
	}

	/**
	 * Construct an framed graph with the specified type resolution and with annotation support
	 *
	 * @param delegate
	 *            The graph to wrap.
	 * @param typeResolution
	 * 			  True if type resolution is to be automatically handled by default, false causes explicit typing by
	 * 			  default.
	 * @param types
	 *            The types to be consider for type resolution.
	 */
	public FramedGraph(Graph delegate, boolean typeResolution, final Collection<? extends Class<?>> types) {
		this.reflections = new ReflectionCache(types);
		this.delegate = delegate;
		if( typeResolution) {
			this.defaultResolver = new SimpleTypeResolver(this.reflections);
			this.untypedResolver = new UntypedTypeResolver();
		}
		else {
			this.defaultResolver = new UntypedTypeResolver();
			this.untypedResolver = this.defaultResolver;
		}
		this.builder = new AnnotationFrameFactory(this.reflections);
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
		if( e == null )
			return null;

		Class<T> frameType = (kind == TVertex.class || kind == TEdge.class) ? kind : defaultResolver.resolve(e, kind);

		T framedElement = builder.create(e, frameType);
		((FramedElement)framedElement).init(this, e);
		return framedElement;
	}

	<T> T frameNewElement(Element e, Class<T> kind) {
		T t = frameElement(e, kind);
		defaultResolver.init(e, kind);
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

	<T> T frameElementExplicit(Element e, Class<T> kind) {
		if(e == null)
			return null;

		Class<T> frameType = this.untypedResolver.resolve(e, kind);

		T framedElement = builder.create(e, frameType);
		((FramedElement)framedElement).init(this, e);
		return framedElement;
	}

	<T> T frameNewElementExplicit(Element e, Class<T> kind) {
		T t = frameElement(e, kind);
		this.untypedResolver.init(e, kind);
		return t;
	}

	<T extends FramedElement> Iterator<T> frameExplicit(Iterator<? extends Element> pipeline, final Class<T> kind) {
		return Iterators.transform(pipeline, new Function<Element, T>() {

			@Override
			public T apply(Element element) {
				return frameElementExplicit(element, kind);
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
	public <T> T addFramedVertex(Class<T> kind) {
		T framedVertex = frameNewElement(delegate.addVertex(null), kind);
		((FramedVertex)framedVertex).init();
		return framedVertex;
	}

	/**
	 * Add a vertex to the graph
	 *
	 * This will bypass the default type resolution and use the untyped resolver
	 * instead. This method is useful for speeding up a look up when type resolution
	 * isn't required.
	 *
	 * @param kind
	 *            The kind of the frame.
	 * @return The framed vertex.
	 */
	public <T> T addFramedVertexExplicit(Class<T> kind) {
		T framedVertex = frameNewElementExplicit(delegate.addVertex(null), kind);
		((FramedVertex)framedVertex).init();
		return framedVertex;
	}
	
	/**
	 * Add a vertex to the graph using a frame type of {@link TVertex}.
	 * 
	 * @return The framed vertex.
	 */
	public TVertex addFramedVertex() {
		
		return addFramedVertex(TVertex.class);
	}

	/**
	 * Add a vertex to the graph using a frame type of {@link TVertex}.
	 *
	 * This will bypass the default type resolution and use the untyped resolver
	 * instead. This method is useful for speeding up a look up when type resolution
	 * isn't required.
	 *
	 * @return The framed vertex.
	 */
	public TVertex addFramedVertexExplicit() {

		return addFramedVertexExplicit(TVertex.class);
	}

	/**
	 * Add a edge to the graph
	 *
	 * @param kind
	 *            The kind of the frame.
	 * @return The framed edge.
	 */
	public <T> T addFramedEdge(final FramedVertex source, final FramedVertex destination, final String label, Class<T> kind) {
		T framedEdge = frameNewElement(this.delegate.addEdge(null, source.element(), destination.element(), label), kind);
		((FramedEdge)framedEdge).init();
		return framedEdge;
	}

	/**
	 * Add a edge to the graph
	 *
	 * This will bypass the default type resolution and use the untyped resolver
	 * instead. This method is useful for speeding up a look up when type resolution
	 * isn't required.
	 *
	 * @param kind
	 *            The kind of the frame.
	 * @return The framed edge.
	 */
	public <T> T addFramedEdgeExplicit(final FramedVertex source, final FramedVertex destination, final String label, Class<T> kind) {
		T framedEdge = frameNewElementExplicit(this.delegate.addEdge(null, source.element(), destination.element(), label), kind);
		((FramedEdge)framedEdge).init();
		return framedEdge;
	}

	/**
	 * Add a edge to the graph using a frame type of {@link TEdge}.
	 *
	 * @return The framed edge.
	 */
	public TEdge addFramedEdge(final FramedVertex source, final FramedVertex destination, final String label) {

		return addFramedEdge(source, destination, label, TEdge.class);
	}

	/**
	 * Add a edge to the graph using a frame type of {@link TEdge}.
	 *
	 * This will bypass the default type resolution and use the untyped resolver
	 * instead. This method is useful for speeding up a look up when type resolution
	 * isn't required.
	 *
	 * @return The framed edge.
	 */
	public TEdge addFramedEdgeExplicit(final FramedVertex source, final FramedVertex destination, final String label) {

		return addFramedEdgeExplicit(source, destination, label, TEdge.class);
	}

	/**
	 * Query over all vertices in the graph.
	 * 
	 * @return The query.
	 */
	public VertexTraversal<?, ?, ?> v() {
		return new TraversalImpl(this, delegate).v();
	}

	/**
	 * Query vertices with a matching key and value
	 *
	 * @return The query.
	 */
	public VertexTraversal<?, ?, ?> v(String key, Object value) {
		return new TraversalImpl(this, delegate.getVertices(key, value).iterator()).castToVertices();
	}

	/**
	 * Query over all edges in the graph.
	 * 
	 * @return The query.
	 */
	public EdgeTraversal<?, ?, ?> e() {
		return new TraversalImpl(this, delegate).e();
	}

	public <F> Iterable<F> getFramedVertices(final String key, final Object value, final Class<F> kind) {
		return new FramingVertexIterable<>(this, this.getVertices(key, value), kind);
	}

	public <F> Iterable<F> getFramedVerticesExplicit(final String key, final Object value, final Class<F> kind) {
		return new FramingVertexIterable<>(this, this.getVertices(key, value), kind, true);
	}

	public <F> Iterable<F> getFramedEdges(final String key, final Object value, final Class<F> kind) {
		return new FramingEdgeIterable<>(this, this.getEdges(key, value), kind);
	}

	public <F> Iterable<F> getFramedEdgesExplicit(final String key, final Object value, final Class<F> kind) {
		return new FramingEdgeIterable<>(this, this.getEdges(key, value), kind, true);
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

	@Override
	public Features getFeatures() {
		return this.delegate.getFeatures();
	}

	@Override
	public Vertex addVertex(Object id) {
		FramedVertex framedVertex = frameNewElement(delegate.addVertex(null), TVertex.class);
		framedVertex.init();
		return framedVertex.element();
	}

	public Vertex addVertexExplicit(Object id) {
		return delegate.addVertex(null);
	}

	@Override
	public Vertex getVertex(Object id) {
		return this.delegate.getVertex(id);
	}

	@Override
	public void removeVertex(Vertex vertex) {
		this.delegate.removeVertex(vertex);
	}

	@Override
	public Iterable<Vertex> getVertices() {
		return this.delegate.getVertices();
	}

	@Override
	public Iterable<Vertex> getVertices(String key, Object value) {
		return this.delegate.getVertices(key, value);
	}

	@Override
	public Edge addEdge(Object id, Vertex outVertex, Vertex inVertex, String label) {
		FramedEdge framedEdge = frameNewElement(this.delegate.addEdge(id, outVertex, inVertex, label), TEdge.class);
		framedEdge.init();
		return framedEdge.element();
	}

	public Edge addEdgeExplicit(Object id, Vertex outVertex, Vertex inVertex, String label) {
		return this.delegate.addEdge(id, outVertex, inVertex, label);
	}

	@Override
	public Edge getEdge(Object id) {
		return this.delegate.getEdge(id);
	}

	@Override
	public void removeEdge(Edge edge) {
		this.delegate.removeEdge(edge);
	}

	@Override
	public Iterable<Edge> getEdges() {
		return this.delegate.getEdges();
	}

	@Override
	public Iterable<Edge> getEdges(String key, Object value) {
		return this.delegate.getEdges(key, value);
	}

	@Override
	public GraphQuery query() {
		return this.delegate.query();
	}

	@Override
	public void shutdown() {
		this.delegate.shutdown();
	}

	protected Graph getDelegate() {
		return delegate;
	}
}

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
import java.net.URL;
import java.util.*;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.syncleus.ferma.annotations.AnnotationFrameFactory;
import com.syncleus.ferma.annotations.AnnotationTypeResolver;
import com.tinkerpop.blueprints.*;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

/**
 * The primary class for framing your blueprints graphs.
 */

public class FramedGraph implements Graph {
	private Graph delegate;

	private TypeResolver resolver;
	private FrameFactory builder;
	private final ReflectionCache reflections;

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
		this.reflections = null;
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
		this(delegate, FrameFactory.DEFAULT, TypeResolver.UNTYPED);
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
		this(delegate, FrameFactory.DEFAULT, resolver);
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
		this.reflections = new ReflectionCache(annotatedTypes);
		this.delegate = delegate;
		this.resolver = new AnnotationTypeResolver(this.reflections);
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

	public <F> Iterable<F> getVertices(final String key, final Object value, final Class<F> kind) {
		return new FramedVertexIterable<>(this, this.delegate.getVertices(key, value), kind);
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
		return this.delegate.addVertex(id);
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

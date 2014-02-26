package org.jglue.totorom;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.Tokens;
import com.tinkerpop.gremlin.java.GremlinPipeline;

/**
 * @author Bryn Cooke (http://jglue.org)
 * 
 * @param <S>
 * @param <E>
 */
public class FramedTraversal<S, E> extends GremlinPipeline<S, E> {
	private FramedGraph graph;

	protected FramedTraversal(FramedGraph graph, Graph delegate) {
		super(delegate, true);
		this.graph = graph;
	}

	protected FramedTraversal(FramedGraph graph, Iterator<S> starts) {
		super(starts, true);
		this.graph = graph;
	}

	protected FramedTraversal(FramedGraph graph, S starts) {
		super(starts, true);
		this.graph = graph;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#V()
	 */
	@Override
	public FramedTraversal<S, Vertex> V() {
		return (FramedTraversal<S, Vertex>) super.V();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#E()
	 */
	@Override
	public FramedTraversal<S, Edge> E() {
		return (FramedTraversal<S, Edge>) super.E();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#_()
	 */
	@Override
	public FramedTraversal<S, E> _() {
		return (FramedTraversal<S, E>) super._();
	}

	/**
	 * Traversal over a of vertices in the graph.
	 * 
	 * @param ids
	 *            The ids of the vertices.
	 * @return The traversal.
	 */
	public FramedTraversal<Vertex, Vertex> v(final Object... ids) {
		return graph.v(ids);
	}

	/**
	 * Traversal over a list of edges in the graph.
	 * 
	 * @param ids
	 *            The ids of the edges.
	 * @return The traversal.
	 */
	public FramedTraversal<Edge, Edge> e(final Object... ids) {
		return graph.e(ids);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#has(java.lang.String)
	 */
	@Override
	public FramedTraversal<S, ? extends Element> has(final String key) {
		return (FramedTraversal<S, ? extends Element>) super.has(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#has(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public FramedTraversal<S, ? extends Element> has(final String key,
			final Object value) {
		return (FramedTraversal<S, ? extends Element>) super.has(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#has(java.lang.String,
	 * com.tinkerpop.gremlin.Tokens.T, java.lang.Object)
	 */
	@Override
	public FramedTraversal<S, ? extends Element> has(final String key,
			final Tokens.T compareToken, final Object value) {
		return (FramedTraversal<S, ? extends Element>) super.has(key,
				compareToken, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#has(java.lang.String,
	 * com.tinkerpop.blueprints.Predicate, java.lang.Object)
	 */
	@Override
	public FramedTraversal<S, ? extends Element> has(final String key,
			final Predicate predicate, final Object value) {
		return (FramedTraversal<S, ? extends Element>) super.has(key, predicate,
				value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#hasNot(java.lang.String)
	 */
	@Override
	public FramedTraversal<S, ? extends Element> hasNot(final String key) {
		return (FramedTraversal<S, ? extends Element>) super.hasNot(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#hasNot(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public FramedTraversal<S, ? extends Element> hasNot(final String key,
			final Object value) {
		return (FramedTraversal<S, ? extends Element>) super.hasNot(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tinkerpop.gremlin.java.GremlinPipeline#interval(java.lang.String,
	 * java.lang.Comparable, java.lang.Comparable)
	 */
	@SuppressWarnings("rawtypes")
	public FramedTraversal<S, ? extends Element> interval(final String key,
			final Comparable startValue, final Comparable endValue) {
		return (FramedTraversal<S, ? extends Element>) super.interval(key,
				startValue, endValue);
	}

	/**
	 * Add an identity step.
	 * 
	 * @return The traversal.
	 */
	public FramedTraversal<S, E> identity() {
		return (FramedTraversal<S, E>) super._();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#out(int,
	 * java.lang.String[])
	 */
	@Override
	public FramedTraversal<S, Vertex> out(final int branchFactor,
			final String... labels) {
		return (FramedTraversal<S, Vertex>) super.out(branchFactor, labels);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#out(java.lang.String[])
	 */
	@Override
	public FramedTraversal<S, Vertex> out(final String... labels) {
		return (FramedTraversal<S, Vertex>) super.out(labels);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#in(int,
	 * java.lang.String[])
	 */
	@Override
	public FramedTraversal<S, Vertex> in(final int branchFactor,
			final String... labels) {
		return (FramedTraversal<S, Vertex>) super.in(branchFactor, labels);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#in(java.lang.String[])
	 */
	@Override
	public FramedTraversal<S, Vertex> in(final String... labels) {
		return (FramedTraversal<S, Vertex>) super.in(labels);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#both(int,
	 * java.lang.String[])
	 */
	@Override
	public FramedTraversal<S, Vertex> both(final int branchFactor,
			final String... labels) {
		return (FramedTraversal<S, Vertex>) super.both(branchFactor, labels);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#both(java.lang.String[])
	 */
	@Override
	public FramedTraversal<S, Vertex> both(final String... labels) {
		return (FramedTraversal<S, Vertex>) super.both(labels);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#outE(int,
	 * java.lang.String[])
	 */
	@Override
	public FramedTraversal<S, Edge> outE(final int branchFactor,
			final String... labels) {
		return (FramedTraversal<S, Edge>) super.outE(branchFactor, labels);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#outE(java.lang.String[])
	 */
	@Override
	public FramedTraversal<S, Edge> outE(final String... labels) {
		return (FramedTraversal<S, Edge>) super.outE(labels);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#inE(int,
	 * java.lang.String[])
	 */
	@Override
	public FramedTraversal<S, Edge> inE(final int branchFactor,
			final String... labels) {
		return (FramedTraversal<S, Edge>) super.inE(branchFactor, labels);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#inE(java.lang.String[])
	 */
	@Override
	public FramedTraversal<S, Edge> inE(final String... labels) {
		return (FramedTraversal<S, Edge>) super.inE(labels);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#bothE(int,
	 * java.lang.String[])
	 */
	@Override
	public FramedTraversal<S, Edge> bothE(final int branchFactor,
			final String... labels) {
		return (FramedTraversal<S, Edge>) super.bothE(branchFactor, labels);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#bothE(java.lang.String[])
	 */
	@Override
	public FramedTraversal<S, Edge> bothE(final String... labels) {
		return (FramedTraversal<S, Edge>) super.bothE(labels);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#inV()
	 */
	@Override
	public FramedTraversal<S, Vertex> inV() {
		return (FramedTraversal<S, Vertex>) super.inV();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#outV()
	 */
	@Override
	public FramedTraversal<S, Vertex> outV() {
		return (FramedTraversal<S, Vertex>) super.outV();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#bothV()
	 */
	@Override
	public FramedTraversal<S, Vertex> bothV() {
		return (FramedTraversal<S, Vertex>) super.bothV();
	}

	/**
	 * Get the next object emitted from the pipeline. If no such object exists,
	 * then a NoSuchElementException is thrown.
	 * 
	 * @param kind
	 *            The type of frame for the element.
	 * @return the next emitted object
	 */
	public <T extends FramedElement<?>> T next(Class<T> kind) {

		return graph.frameElement((Element) super.next(), kind);
	}

	/**
	 * Return the next X objects in the traversal as a list.
	 * 
	 * @param number
	 *            the number of objects to return
	 * @param kind
	 *            the type of frame to for each element.
	 * @return a list of X objects (if X objects occur)
	 */
	public <T extends FramedElement<?>> List<T> next(int amount,
			final Class<T> kind) {
		return Lists.transform(super.next(amount), new Function<E, T>() {

			@Override
			public T apply(E e) {
				return graph.frameElement((Element) e, kind);
			}
		});

	}

	/**
	 * Return an iterator of framed elements.
	 * 
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return An iterator of framed elements.
	 */
	@SuppressWarnings("unchecked")
	public <T extends FramedElement<?>> Iterator<T> frame(Class<T> kind) {
		return graph.frame((Iterator<Element>)this, kind);
	}

	/**
	 * Return a list of all the objects in the pipeline.
	 * 
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return a list of all the objects
	 */
	public <T extends FramedElement<?>> List<T> toList(final Class<T> kind) {
		return Lists.transform(super.toList(), new Function<E, T>() {

			@Override
			public T apply(E e) {
				return graph.frameElement((Element) e, kind);
			}
		});

	}
}

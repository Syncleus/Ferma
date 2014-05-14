package org.jglue.totorom;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.gremlin.Tokens;
import com.tinkerpop.gremlin.java.GremlinPipeline;

/**
 * @author Bryn Cooke (http://jglue.org)
 * 
 * @param <S, E>
 * @param <E>
 */
public class FramedTraversalImpl<S, E> implements FramedTraversal<S, E>,
		FramedEdgeTraversal<S, E>, FramedVertexTraversal<S, E> {
	private FramedGraph graph;
	private GremlinPipeline pipeline;

	protected FramedTraversalImpl(FramedGraph graph, Graph delegate) {
		pipeline = new GremlinPipeline<>(delegate);
		this.graph = graph;
	}

	protected FramedTraversalImpl(FramedGraph graph, Iterator<S> starts) {
		pipeline = new GremlinPipeline<>(starts);
		this.graph = graph;
	}

	protected FramedTraversalImpl(FramedGraph graph, S starts) {
		pipeline = new GremlinPipeline<>(starts);
		this.graph = graph;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#V()
	 */
	@Override
	public FramedTraversalImpl<S, E> V() {
		pipeline.V();
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#E()
	 */
	@Override
	public FramedTraversalImpl<S, E> E() {
		pipeline.E();
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#v(java.lang.Object)
	 */
	@Override
	public FramedTraversalImpl<S, E> v(final Object... ids) {
		return (FramedTraversalImpl<S, E>) graph.v(ids);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#e(java.lang.Object)
	 */
	@Override
	public FramedTraversalImpl<S, E> e(final Object... ids) {
		return (FramedTraversalImpl<S, E>) graph.e(ids);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#has(java.lang.String)
	 */
	@Override
	public FramedTraversalImpl<S, E> has(final String key) {
		pipeline.has(key);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#has(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public FramedTraversalImpl<S, E> has(final String key, final Object value) {
		pipeline.has(key, value);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#has(java.lang.String,
	 * com.tinkerpop.gremlin.Tokens.T, java.lang.Object)
	 */
	@Override
	public FramedTraversalImpl<S, E> has(final String key,
			final Tokens.T compareToken, final Object value) {
		pipeline.has(key, compareToken, value);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#has(java.lang.String,
	 * com.tinkerpop.blueprints.Predicate, java.lang.Object)
	 */
	@Override
	public FramedTraversalImpl<S, E> has(final String key,
			final Predicate predicate, final Object value) {
		pipeline.has(key, predicate, value);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#hasNot(java.lang.String)
	 */
	@Override
	public FramedTraversalImpl<S, E> hasNot(final String key) {
		pipeline.hasNot(key);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#hasNot(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public FramedTraversalImpl<S, E> hasNot(final String key, final Object value) {
		pipeline.hasNot(key, value);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#interval(java.lang.String,
	 * java.lang.Comparable, java.lang.Comparable)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public FramedTraversalImpl<S, E> interval(final String key,
			final Comparable startValue, final Comparable endValue) {
		pipeline.interval(key, startValue, endValue);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#identity()
	 */
	@Override
	public FramedTraversalImpl<S, E> identity() {
		pipeline._();
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#out(int, java.lang.String)
	 */
	@Override
	public FramedTraversalImpl<S, E> out(final int branchFactor,
			final String... labels) {
		pipeline.out(branchFactor, labels);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#out(java.lang.String)
	 */
	@Override
	public FramedTraversalImpl<S, E> out(final String... labels) {
		pipeline.out(labels);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#in(int, java.lang.String)
	 */
	@Override
	public FramedTraversalImpl<S, E> in(final int branchFactor,
			final String... labels) {
		pipeline.in(branchFactor, labels);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#in(java.lang.String)
	 */
	@Override
	public FramedTraversalImpl<S, E> in(final String... labels) {
		pipeline.in(labels);
		return (FramedTraversalImpl<S, E>) this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#both(int, java.lang.String)
	 */
	@Override
	public FramedTraversalImpl<S, E> both(final int branchFactor,
			final String... labels) {
		pipeline.both(branchFactor, labels);
		return (FramedTraversalImpl<S, E>) this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#both(java.lang.String)
	 */
	@Override
	public FramedTraversalImpl<S, E> both(final String... labels) {
		pipeline.both(labels);
		return (FramedTraversalImpl<S, E>) this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#outE(int, java.lang.String)
	 */
	@Override
	public FramedTraversalImpl<S, E> outE(final int branchFactor,
			final String... labels) {
		pipeline.outE(branchFactor, labels);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#outE(java.lang.String)
	 */
	@Override
	public FramedTraversalImpl<S, E> outE(final String... labels) {
		pipeline.outE(labels);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#inE(int, java.lang.String)
	 */
	@Override
	public FramedTraversalImpl<S, E> inE(final int branchFactor,
			final String... labels) {
		pipeline.inE(branchFactor, labels);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#inE(java.lang.String[])
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#inE(java.lang.String)
	 */
	@Override
	public FramedTraversalImpl<S, E> inE(final String... labels) {
		pipeline.inE(labels);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#bothE(int, java.lang.String)
	 */
	@Override
	public FramedTraversalImpl<S, E> bothE(final int branchFactor,
			final String... labels) {
		pipeline.bothE(branchFactor, labels);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#bothE(java.lang.String[])
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#bothE(java.lang.String)
	 */
	@Override
	public FramedTraversalImpl<S, E> bothE(final String... labels) {
		pipeline.bothE(labels);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#inV()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#inV()
	 */
	@Override
	public FramedTraversalImpl<S, E> inV() {
		pipeline.inV();
		return (FramedTraversalImpl<S, E>) this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#outV()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#outV()
	 */
	@Override
	public FramedTraversalImpl<S, E> outV() {
		pipeline.outV();
		return (FramedTraversalImpl<S, E>) this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tinkerpop.gremlin.java.GremlinPipeline#bothV()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#bothV()
	 */
	@Override
	public FramedTraversalImpl<S, E> bothV() {
		pipeline.bothV();
		return this;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#next(int, java.lang.Class)
	 */
	@Override
	public List next(int amount, final Class kind) {
		return Lists.transform(pipeline.next(amount), new Function() {

			public Object apply(Object e) {
				return graph.frameElement((Element) e, kind);
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#frame(java.lang.Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Iterator frame(Class kind) {
		return graph.frame((Iterator<Element>) pipeline, kind);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jglue.totorom.Framedtraversal#toList(java.lang.Class)
	 */
	@Override
	public List toList(final Class kind) {
		return Lists.transform(pipeline.toList(), new Function() {

			public Object apply(Object e) {
				return graph.frameElement((Element) e, kind);
			}
		});

	}

	@Override
	public <T extends FramedVertex> T nextVertex(Class<T> kind) {
		return  graph.frameElement((Element) pipeline.next(), kind);
	}

	@Override
	public <T extends FramedEdge> T nextEdge(Class<T> kind) {
		return  graph.frameElement((Element) pipeline.next(), kind);
	}
	
	public long count() {
		return pipeline.count();
	}
}

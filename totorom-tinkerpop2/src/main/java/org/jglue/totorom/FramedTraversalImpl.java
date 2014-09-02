package org.jglue.totorom;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.Tokens.T;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.transform.TransformPipe.Order;

public class FramedTraversalImpl extends FramedTraversalBase implements FramedTraversal {

	private FramedGraph graph;
	private GremlinPipeline pipeline;

	private FramedEdgeTraversal edgeTraversal = new FramedEdgeTraversalImpl();
	private FramedVertexTraversal vertexTraversal = new FramedVertexTraversalImpl();

	private FramedTraversalImpl(FramedGraph graph, GremlinPipeline pipeline) {
		this.graph = graph;
		this.pipeline = pipeline;

	}

	protected FramedTraversalImpl(FramedGraph graph, Graph delegate) {
		this(graph, new GremlinPipeline<>(delegate));
	}

	protected FramedTraversalImpl(FramedGraph graph, Iterator starts) {
		this(graph, new GremlinPipeline<>(starts));
	}

	protected FramedTraversalImpl(FramedGraph graph, Element starts) {
		this(graph, new GremlinPipeline<>(starts));
	}

	@Override
	public FramedVertexTraversal V() {
		pipeline.V();
		return vertexTraversal;
	}

	@Override
	public FramedEdgeTraversal E() {
		pipeline.E();
		return edgeTraversal;
	}

	@Override
	public FramedVertexTraversal v(Object... ids) {
		return (FramedVertexTraversal) graph.v(ids);
	}

	@Override
	public FramedEdgeTraversal e(Object... ids) {
		return (FramedEdgeTraversal) graph.e(ids);
	}

	@Override
	public FramedTraversal identity() {
		pipeline._();
		return this;
	}

	@Override
	public long count() {
		return pipeline.count();
	}

	/**
	 * @return Cast the traversal to a {@link FramedVertexTraversal}
	 */
	public FramedVertexTraversal asVertices() {
		return vertexTraversal;
	}

	/**
	 * @return Cast the traversal to a {@link FramedEdgeTraversal}
	 */
	public FramedEdgeTraversal asEdges() {
		return edgeTraversal;
	}

	@Override
	protected GremlinPipeline pipeline() {

		return pipeline;
	}

	@Override
	protected FramedGraph graph() {

		return graph;
	}

	protected class FramedEdgeTraversalImpl<SE> extends FramedTraversalBase<Edge, SE> implements FramedEdgeTraversal<SE> {
		@Override
		public FramedEdgeTraversal has(String key) {
			return (FramedEdgeTraversal) super.has(key);
		}

		@Override
		public FramedEdgeTraversal has(String key, Object value) {
			return (FramedEdgeTraversal) super.has(key, value);
		}

		@Override
		public FramedEdgeTraversal has(String key, Predicate predicate, Object value) {
			return (FramedEdgeTraversal) super.has(key, predicate, value);
		}

		@Override
		public FramedEdgeTraversal has(String key, com.tinkerpop.gremlin.Tokens.T compareToken, Object value) {
			return (FramedEdgeTraversal) super.has(key, compareToken, value);
		}

		@Override
		public FramedEdgeTraversal hasNot(String key) {
			return (FramedEdgeTraversal) super.hasNot(key);
		}

		@Override
		public FramedEdgeTraversal hasNot(String key, Object value) {
			return (FramedEdgeTraversal) super.hasNot(key, value);
		}

		@Override
		public FramedEdgeTraversal as(String name) {
			return (FramedEdgeTraversal) super.as(name);
		}

		@Override
		public FramedEdgeTraversal identity() {

			return (FramedEdgeTraversal) super.identity();
		}

		@Override
		public FramedEdgeTraversal interval(String key, Comparable startValue, Comparable endValue) {
			return (FramedEdgeTraversal) super.interval(key, startValue, endValue);
		}

		@Override
		public FramedVertexTraversal inV() {
			pipeline.inV();
			return vertexTraversal;
		}

		@Override
		public FramedVertexTraversal outV() {
			pipeline.outV();
			return vertexTraversal;
		}

		@Override
		public FramedVertexTraversal bothV() {
			pipeline.bothV();
			return asVertices();
		}

		@Override
		public FramedEdge next(Class kind) {
			return graph.frameElement((Element) pipeline.next(), kind);
		}

		@Override
		public List next(int amount, final Class kind) {
			return Lists.transform(pipeline.next(amount), new Function() {

				public Object apply(Object e) {
					return graph.frameElement((Element) e, kind);
				}
			});
		}

		@Override
		public Iterator frame(final Class kind) {
			return Iterators.transform(pipeline, new Function() {

				public Object apply(Object e) {
					return graph.frameElement((Element) e, kind);
				}
			});
		}

		@Override
		public List<T> toList(final Class kind) {
			return Lists.transform(pipeline.toList(), new Function() {

				public Object apply(Object e) {
					return graph.frameElement((Element) e, kind);
				}
			});
		}

		@Override
		public FramedVertexTraversal asVertices() {
			return vertexTraversal;
		}

		@Override
		public FramedEdgeTraversal asEdges() {
			return edgeTraversal;
		}

		@Override
		protected FramedGraph graph() {
			return graph;
		}

		@Override
		protected GremlinPipeline pipeline() {
			return pipeline;
		}

		@Override
		public FramedTraversal label() {
			pipeline.label();
			return this;
		}
		
		@Override
		public FramedEdgeTraversal filter(PipeFunction filterFunction) {
			return (FramedEdgeTraversal) super.filter(filterFunction);

		}

		@Override
		public FramedEdgeTraversal aggregate() {
			return (FramedEdgeTraversal) super.aggregate();

		}

		@Override
		public FramedEdgeTraversal aggregate(Collection aggregate) {
			return (FramedEdgeTraversal) super.aggregate(aggregate);

		}

		@Override
		public FramedEdgeTraversal aggregate(Collection aggregate, PipeFunction aggregateFunction) {
			return (FramedEdgeTraversal) super.aggregate(aggregate, aggregateFunction);

		}

		@Override
		public FramedEdgeTraversal aggregate(PipeFunction aggregateFunction) {
			return (FramedEdgeTraversal) super.aggregate(aggregateFunction);

		}

		@Override
		public FramedEdgeTraversal and(Pipe... pipes) {

			return (FramedEdgeTraversal) super.and(pipes);

		}
		@Override
		public FramedEdgeTraversal sideEffect(PipeFunction sideEffectFunction) {
			return (FramedEdgeTraversal)super.sideEffect(sideEffectFunction);
		}
		
		@Override
		public FramedEdgeTraversal random(Double bias) {

			return (FramedEdgeTraversal) super.random(bias);
		}

		@Override
		public FramedEdgeTraversal dedup(PipeFunction dedupFunction) {
			return (FramedEdgeTraversal) super.dedup(dedupFunction);
		}

		@Override
		public FramedEdgeTraversal except(String... namedSteps) {

			return (FramedEdgeTraversal) super.except(namedSteps);
		}

		@Override
		public FramedEdgeTraversal except(Collection collection) {
			return (FramedEdgeTraversal) super.except(collection);
		}

		@Override
		public FramedEdgeTraversal range(int low, int high) {
			return (FramedEdgeTraversal) super.range(low, high);
		}

		@Override
		public FramedEdgeTraversal or(Pipe... pipes) {
			return (FramedEdgeTraversal) super.or(pipes);
		}

		@Override
		public FramedEdgeTraversal order() {
			return (FramedEdgeTraversal) super.order();
		}

		@Override
		public FramedEdgeTraversal order(Order order) {
			return (FramedEdgeTraversal) super.order(order);
		}

		@Override
		public FramedEdgeTraversal order(PipeFunction compareFunction) {
			return (FramedEdgeTraversal) super.order(compareFunction);
		}

		@Override
		public FramedEdgeTraversal order(T order) {
			return (FramedEdgeTraversal) super.order(order);
		}

		@Override
		public FramedEdgeTraversal dedup() {

			return (FramedEdgeTraversal) super.dedup();
		}

		@Override
		public FramedEdgeTraversal retain(Collection collection) {
			return (FramedEdgeTraversal) super.retain(collection);

		}

		@Override
		public FramedEdgeTraversal retain(String... namedSteps) {
			return (FramedEdgeTraversal) super.retain(namedSteps);
		}

		@Override
		public FramedEdgeTraversal simplePath() {
			return (FramedEdgeTraversal) super.simplePath();
		}

		@Override
		public FramedEdgeTraversal memoize(String namedStep) {
			return (FramedEdgeTraversal) super.memoize(namedStep);

		}

		@Override
		public FramedEdgeTraversal memoize(String namedStep, Map map) {
			return (FramedEdgeTraversal) super.memoize(namedStep, map);

		}

	}

	protected class FramedVertexTraversalImpl extends FramedTraversalBase implements FramedVertexTraversal {

		@Override
		public FramedVertexTraversal filter(PipeFunction filterFunction) {
			return (FramedVertexTraversal) super.filter(filterFunction);

		}

		@Override
		public FramedVertexTraversal aggregate() {
			return (FramedVertexTraversal) super.aggregate();

		}

		@Override
		public FramedVertexTraversal aggregate(Collection aggregate) {
			return (FramedVertexTraversal) super.aggregate(aggregate);

		}

		@Override
		public FramedVertexTraversal aggregate(Collection aggregate, PipeFunction aggregateFunction) {
			return (FramedVertexTraversal) super.aggregate(aggregate, aggregateFunction);

		}

		@Override
		public FramedVertexTraversal aggregate(PipeFunction aggregateFunction) {
			return (FramedVertexTraversal) super.aggregate(aggregateFunction);

		}

		@Override
		public FramedVertexTraversal and(Pipe... pipes) {

			return (FramedVertexTraversal) super.and(pipes);

		}
		@Override
		public FramedVertexTraversal sideEffect(PipeFunction sideEffectFunction) {
			return (FramedVertexTraversal)super.sideEffect(sideEffectFunction);
		}
		
		@Override
		public FramedVertexTraversal random(Double bias) {

			return (FramedVertexTraversal) super.random(bias);
		}

		@Override
		public FramedVertexTraversal dedup(PipeFunction dedupFunction) {
			return (FramedVertexTraversal) super.dedup(dedupFunction);
		}

		@Override
		public FramedVertexTraversal except(String... namedSteps) {

			return (FramedVertexTraversal) super.except(namedSteps);
		}

		@Override
		public FramedVertexTraversal except(Collection collection) {
			return (FramedVertexTraversal) super.except(collection);
		}

		@Override
		public FramedVertexTraversal range(int low, int high) {
			return (FramedVertexTraversal) super.range(low, high);
		}

		@Override
		public FramedVertexTraversal or(Pipe... pipes) {
			return (FramedVertexTraversal) super.or(pipes);
		}

		@Override
		public FramedVertexTraversal order() {
			return (FramedVertexTraversal) super.order();
		}

		@Override
		public FramedVertexTraversal order(Order order) {
			return (FramedVertexTraversal) super.order(order);
		}

		@Override
		public FramedVertexTraversal order(PipeFunction compareFunction) {
			return (FramedVertexTraversal) super.order(compareFunction);
		}

		@Override
		public FramedVertexTraversal order(T order) {
			return (FramedVertexTraversal) super.order(order);
		}

		@Override
		public FramedVertexTraversal dedup() {

			return (FramedVertexTraversal) super.dedup();
		}

		@Override
		public FramedVertexTraversal retain(Collection collection) {
			return (FramedVertexTraversal) super.retain(collection);

		}

		@Override
		public FramedVertexTraversal retain(String... namedSteps) {
			return (FramedVertexTraversal) super.retain(namedSteps);
		}

		@Override
		public FramedVertexTraversal simplePath() {
			return (FramedVertexTraversal) super.simplePath();
		}

		@Override
		public FramedVertexTraversal memoize(String namedStep) {
			return (FramedVertexTraversal) super.memoize(namedStep);

		}

		@Override
		public FramedVertexTraversal memoize(String namedStep, Map map) {
			return (FramedVertexTraversal) super.memoize(namedStep, map);

		}

		@Override
		public FramedVertexTraversal out(int branchFactor, String... labels) {
			pipeline().out(branchFactor, labels);
			return this;
		}

		@Override
		public FramedVertexTraversal out(String... labels) {
			pipeline().out(labels);
			return this;
		}

		@Override
		public FramedVertexTraversal in(int branchFactor, String... labels) {
			pipeline().in(branchFactor, labels);
			return this;
		}

		@Override
		public FramedVertexTraversal in(String... labels) {
			pipeline().in(labels);
			return this;
		}

		@Override
		public FramedVertexTraversal both(int branchFactor, String... labels) {
			pipeline().both(branchFactor, labels);
			return this;
		}

		@Override
		public FramedVertexTraversal both(String... labels) {
			pipeline().both(labels);
			return this;
		}

		@Override
		public FramedEdgeTraversal outE(int branchFactor, String... labels) {
			pipeline().outE(branchFactor, labels);
			return edgeTraversal;
		}

		@Override
		public FramedEdgeTraversal outE(String... labels) {
			pipeline().outE(labels);
			return edgeTraversal;
		}

		@Override
		public FramedEdgeTraversal inE(int branchFactor, String... labels) {
			pipeline().inE(branchFactor, labels);
			return edgeTraversal;
		}

		@Override
		public FramedEdgeTraversal inE(String... labels) {
			pipeline().inE(labels);
			return edgeTraversal;
		}

		@Override
		public FramedEdgeTraversal bothE(int branchFactor, String... labels) {
			pipeline().bothE(branchFactor, labels);
			return edgeTraversal;
		}

		@Override
		public FramedEdgeTraversal bothE(String... labels) {
			pipeline().bothE(labels);
			return edgeTraversal;
		}

		@Override
		public FramedVertexTraversal interval(String key, Comparable startValue, Comparable endValue) {
			return (FramedVertexTraversal) super.interval(key, startValue, endValue);
		}

		@Override
		public FramedVertex next(Class kind) {
			return graph.frameElement((Element) pipeline.next(), kind);
		}

		@Override
		public List next(int amount, final Class kind) {
			return Lists.transform(pipeline.next(amount), new Function() {

				public Object apply(Object e) {
					return graph.frameElement((Element) e, kind);
				}
			});
		}

		@Override
		public Iterator frame(final Class kind) {
			return Iterators.transform(pipeline, new Function() {

				public Object apply(Object e) {
					return graph.frameElement((Element) e, kind);
				}
			});
		}

		@Override
		public List toList(final Class kind) {
			return Lists.transform(pipeline.toList(), new Function() {

				public Object apply(Object e) {
					return graph.frameElement((Element) e, kind);
				}
			});
		}

		@Override
		public FramedVertexTraversal asVertices() {
			return vertexTraversal;
		}

		@Override
		public FramedEdgeTraversal asEdges() {
			return edgeTraversal;
		}

		@Override
		protected FramedGraph graph() {
			return graph;
		}

		@Override
		protected GremlinPipeline pipeline() {
			return pipeline;
		}

		@Override
		public FramedVertexTraversal has(String key) {
			return (FramedVertexTraversal) super.has(key);
		}

		@Override
		public FramedVertexTraversal has(String key, Object value) {
			return (FramedVertexTraversal) super.has(key, value);
		}

		@Override
		public FramedVertexTraversal has(String key, Predicate predicate, Object value) {
			return (FramedVertexTraversal) super.has(key, predicate, value);
		}

		@Override
		public FramedVertexTraversal has(String key, com.tinkerpop.gremlin.Tokens.T compareToken, Object value) {
			return (FramedVertexTraversal) super.has(key, compareToken, value);
		}

		@Override
		public FramedVertexTraversal hasNot(String key) {
			return (FramedVertexTraversal) super.hasNot(key);
		}

		@Override
		public FramedVertexTraversal hasNot(String key, Object value) {
			return (FramedVertexTraversal) super.hasNot(key, value);
		}

		@Override
		public FramedVertexTraversal as(String name) {
			return (FramedVertexTraversal) super.as(name);
		}

		@Override
		public FramedVertexTraversal identity() {
			return (FramedVertexTraversal) super.identity();
		}

		@Override
		public FramedVertexTraversal linkOut(String label, String namedStep) {
			pipeline().linkOut(label, namedStep);
			return this;
		}

		@Override
		public FramedVertexTraversal linkIn(String label, String namedStep) {
			pipeline().linkIn(label, namedStep);
			return this;
		}

		@Override
		public FramedVertexTraversal linkBoth(String label, String namedStep) {
			pipeline().linkBoth(label, namedStep);
			return this;
		}

		@Override
		public FramedVertexTraversal linkOut(String label, Vertex other) {
			pipeline().linkOut(label, other);
			return this;
		}

		@Override
		public FramedVertexTraversal linkIn(String label, Vertex other) {
			pipeline().linkIn(label, other);
			return this;
		}

		@Override
		public FramedVertexTraversal linkBoth(String label, Vertex other) {
			pipeline().linkBoth(label, other);
			return this;
		}

		@Override
		public FramedVertexTraversal linkOut(String label, FramedVertex other) {
			pipeline().linkOut(label, other.element());
			return this;
		}

		@Override
		public FramedVertexTraversal linkIn(String label, FramedVertex other) {
			pipeline().linkIn(label, other.element());
			return this;
		}

		@Override
		public FramedVertexTraversal linkBoth(String label, FramedVertex other) {
			pipeline().linkBoth(label, other.element());
			return this;
		}


	}

}

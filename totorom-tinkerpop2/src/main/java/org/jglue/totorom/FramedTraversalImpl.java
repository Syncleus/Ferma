package org.jglue.totorom;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
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
import com.tinkerpop.pipes.transform.TransformPipe.Order;
import com.tinkerpop.pipes.util.structures.Table;
import com.tinkerpop.pipes.util.structures.Tree;

@SuppressWarnings("rawtypes")
class FramedTraversalImpl extends FramedTraversalBase implements FramedTraversal {

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

	protected FramedTraversalImpl(FramedGraph graph, FramedElement starts) {
		this(graph, new GremlinPipeline<>(starts.element()));
	}

	private HashSet unwrap(Collection collection) {
		HashSet unwrapped = new HashSet(Collections2.transform(collection, new Function<Object, Object>() {

			@Override
			public Object apply(Object o) {
				if(o instanceof FramedVertex) {
					return ((FramedVertex) o).element();
				}
				if(o instanceof FramedEdge) {
					return ((FramedEdge) o).element();
				}
				return o;
			}
		}));
		return unwrapped;
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
	protected FramedTraversal asTraversal() {
		return this;
	}

	@Override
	protected GremlinPipeline pipeline() {

		return pipeline;
	}

	@Override
	protected FramedGraph graph() {

		return graph;
	}

	@SuppressWarnings("unchecked")
	protected class FramedEdgeTraversalImpl<SE> extends FramedTraversalBase<GenericFramedEdge, SE> implements
			FramedEdgeTraversal<SE> {
		
		
		
		@Override
		public GenericFramedEdge next() {

			return graph().frameElement((Edge) pipeline().next(), GenericFramedEdge.class);
		}

		@Override
		public FramedEdgeTraversal table() {
			return (FramedEdgeTraversal) super.table();
		}

		@Override
		public FramedEdgeTraversal table(TraversalFunction... columnFunctions) {

			return (FramedEdgeTraversal) super.table(columnFunctions);
		}

		@Override
		public FramedEdgeTraversal table(Table table) {

			return (FramedEdgeTraversal) super.table(table);
		}

		@Override
		public FramedEdgeTraversal table(Table table, Collection stepNames, TraversalFunction... columnFunctions) {

			return (FramedEdgeTraversal) super.table(table, stepNames, columnFunctions);
		}

		@Override
		public FramedEdgeTraversal table(Table table, TraversalFunction... columnFunctions) {
			return (FramedEdgeTraversal) super.table(table, columnFunctions);
		}

		@Override
		public FramedEdgeTraversal tree(TraversalFunction... branchFunctions) {
			return (FramedEdgeTraversal) super.tree(branchFunctions);
		}

		@Override
		public FramedEdgeTraversal tree(Tree tree, TraversalFunction... branchFunctions) {
			return (FramedEdgeTraversal) super.tree(tree, branchFunctions);
		}

		@Override
		public FramedEdgeTraversal store() {
			return (FramedEdgeTraversal) super.store(new FramedTraversalFunction(graph));
		}

		@Override
		public FramedEdgeTraversal store(Collection storage) {
			return (FramedEdgeTraversal) super.store(storage, new FramedTraversalFunction(graph));
		}

		@Override
		public FramedEdgeTraversal store(Collection storage, TraversalFunction storageFunction) {
			return (FramedEdgeTraversal) super.store(storage, new FramedTraversalFunction(storageFunction, graph));
		}

		@Override
		public FramedEdgeTraversal store(TraversalFunction storageFunction) {
			return (FramedEdgeTraversal) super.store(new FramedTraversalFunction(storageFunction, graph));
		}

		@Override
		public FramedEdgeTraversal groupCount() {
			return (FramedEdgeTraversal) super.groupCount();
		}

		@Override
		public FramedEdgeTraversal groupCount(Map map) {
			return (FramedEdgeTraversal) super.groupCount(map);
		}

		@Override
		public FramedEdgeTraversal groupCount(Map map, TraversalFunction keyFunction) {
			return (FramedEdgeTraversal) super.groupCount(map, new FramedTraversalFunction(keyFunction, graph));
		}

		@Override
		public FramedEdgeTraversal groupCount(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
			return (FramedEdgeTraversal) super.groupCount(map, new FramedTraversalFunction(keyFunction, graph), new FramedTraversalFunction(valueFunction, graph));
		}

		@Override
		public FramedEdgeTraversal groupCount(TraversalFunction keyFunction) {
			return (FramedEdgeTraversal) super.groupCount(new FramedTraversalFunction(keyFunction, graph));
		}

		@Override
		public FramedEdgeTraversal groupCount(TraversalFunction keyFunction, TraversalFunction valueFunction) {
			return (FramedEdgeTraversal) super.groupCount(new FramedTraversalFunction(keyFunction, graph), new FramedTraversalFunction(valueFunction, graph));
		}

		@Override
		public FramedEdgeTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction, TraversalFunction reduceFunction) {
			return (FramedEdgeTraversal) super.groupBy(new FramedTraversalFunction(keyFunction, graph), new FramedTraversalFunction(valueFunction, graph), new FramedTraversalFunction(reduceFunction, graph));
		}

		@Override
		public FramedEdgeTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction) {

			return (FramedEdgeTraversal) super.groupBy(new FramedTraversalFunction(keyFunction, graph), new FramedTraversalFunction(valueFunction, graph));
		}

		@Override
		public FramedEdgeTraversal groupBy(Map reduceMap, TraversalFunction keyFunction, TraversalFunction valueFunction,
				TraversalFunction reduceFunction) {
			return (FramedEdgeTraversal) super.groupBy(reduceMap, new FramedTraversalFunction(keyFunction, graph), new FramedTraversalFunction(valueFunction, graph), new FramedTraversalFunction(reduceFunction, graph));
		}

		@Override
		public FramedEdgeTraversal groupBy(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
			return (FramedEdgeTraversal) super.groupBy(map, new FramedTraversalFunction(keyFunction, graph), new FramedTraversalFunction(valueFunction, graph));
		}

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
		public Iterable frame(final Class kind) {
			return Iterables.transform(pipeline, new Function() {

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
		public FramedEdgeTraversal filter(TraversalFunction filterFunction) {
			return (FramedEdgeTraversal) super.filter(new FramedTraversalFunction(filterFunction, graph));

		}

		@Override
		public FramedEdgeTraversal aggregate() {
			return (FramedEdgeTraversal) super.aggregate(new FramedTraversalFunction(graph));

		}

		@Override
		public FramedEdgeTraversal aggregate(Collection aggregate) {
			return (FramedEdgeTraversal) super.aggregate(aggregate, new FramedTraversalFunction(graph));

		}

		@Override
		public FramedEdgeTraversal aggregate(Collection aggregate, TraversalFunction aggregateFunction) {
			return (FramedEdgeTraversal) super.aggregate(aggregate, new FramedTraversalFunction(aggregateFunction, graph));

		}

		@Override
		public FramedEdgeTraversal aggregate(TraversalFunction aggregateFunction) {
			return (FramedEdgeTraversal) super.aggregate(new FramedTraversalFunction(aggregateFunction, graph));

		}

		@Override
		public FramedEdgeTraversal and(Pipe... pipes) {

			return (FramedEdgeTraversal) super.and(pipes);

		}

		@Override
		public FramedEdgeTraversal sideEffect(SideEffectFunction sideEffectFunction) {
			return (FramedEdgeTraversal) super.sideEffect(new FramedSideEffectFunction(sideEffectFunction, graph));
		}

		@Override
		public FramedEdgeTraversal random(Double bias) {

			return (FramedEdgeTraversal) super.random(bias);
		}

		@Override
		public FramedEdgeTraversal dedup(TraversalFunction dedupFunction) {
			return (FramedEdgeTraversal) super.dedup(new FramedTraversalFunction(dedupFunction, graph));
		}

		@Override
		public FramedEdgeTraversal except(String... namedSteps) {

			return (FramedEdgeTraversal) super.except(namedSteps);
		}

		@Override
		public FramedEdgeTraversal except(Collection collection) {
			return (FramedEdgeTraversal) super.except(unwrap(collection));
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
		public FramedEdgeTraversal order(Comparator compareFunction) {
			return (FramedEdgeTraversal) super.order(new FramedComparator(compareFunction, graph));
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
			return (FramedEdgeTraversal) super.retain(unwrap(collection));

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

		@Override
		public Collection fill(Collection collection) {
			return super.fill(new FramedCollection<>(collection, graph, GenericFramedEdge.class));
		}
		
		@Override
		public Collection fill(Collection collection, Class clazz) {

			return super.fill(new FramedCollection(collection, graph, clazz));
		}

		@Override
		protected FramedTraversal asTraversal() {
			return FramedTraversalImpl.this;
		}
		public java.util.Iterator<GenericFramedEdge> iterator() {
			return Iterators.transform(pipeline, new Function() {

				public Object apply(Object e) {
					return graph.frameElement((Element) e, GenericFramedEdge.class);
				}
			});	
		};
		
	}

	@SuppressWarnings("unchecked")
	protected class FramedVertexTraversalImpl extends FramedTraversalBase implements FramedVertexTraversal {
		@Override
		public GenericFramedVertex next() {
			return graph().frameElement((Vertex) pipeline().next(), GenericFramedVertex.class);
		}

		@Override
		public FramedVertexTraversal table() {
			return (FramedVertexTraversal) super.table();
		}

		@Override
		public FramedVertexTraversal table(TraversalFunction... columnFunctions) {

			return (FramedVertexTraversal) super.table(columnFunctions);
		}

		@Override
		public FramedVertexTraversal table(Table table) {

			return (FramedVertexTraversal) super.table(table);
		}

		@Override
		public FramedVertexTraversal table(Table table, Collection stepNames, TraversalFunction... columnFunctions) {

			return (FramedVertexTraversal) super.table(table, stepNames, columnFunctions);
		}

		@Override
		public FramedVertexTraversal table(Table table, TraversalFunction... columnFunctions) {
			return (FramedVertexTraversal) super.table(table, columnFunctions);
		}

		@Override
		public FramedVertexTraversal tree(TraversalFunction... branchFunctions) {
			return (FramedVertexTraversal) super.tree(branchFunctions);
		}

		@Override
		public FramedVertexTraversal tree(Tree tree, TraversalFunction... branchFunctions) {
			return (FramedVertexTraversal) super.tree(tree, branchFunctions);
		}

		@Override
		public FramedVertexTraversal store() {
			return (FramedVertexTraversal) super.store(new FramedTraversalFunction(graph));
		}

		@Override
		public FramedVertexTraversal store(Collection storage) {
			return (FramedVertexTraversal) super.store(storage, new FramedTraversalFunction(graph));
		}

		@Override
		public FramedVertexTraversal store(Collection storage, TraversalFunction storageFunction) {
			return (FramedVertexTraversal) super.store(storage, new FramedTraversalFunction(storageFunction, graph));
		}

		@Override
		public FramedVertexTraversal store(TraversalFunction storageFunction) {
			return (FramedVertexTraversal) super.store(new FramedTraversalFunction(storageFunction, graph));
		}

		@Override
		public FramedVertexTraversal groupCount() {
			return (FramedVertexTraversal) super.groupCount();
		}

		@Override
		public FramedVertexTraversal groupCount(Map map) {
			return (FramedVertexTraversal) super.groupCount(map);
		}

		@Override
		public FramedVertexTraversal groupCount(Map map, TraversalFunction keyFunction) {
			return (FramedVertexTraversal) super.groupCount(map, new FramedTraversalFunction(keyFunction, graph));
		}

		@Override
		public FramedVertexTraversal groupCount(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
			return (FramedVertexTraversal) super.groupCount(map, new FramedTraversalFunction(keyFunction, graph), new FramedTraversalFunction(valueFunction, graph));
		}

		@Override
		public FramedVertexTraversal groupCount(TraversalFunction keyFunction) {
			return (FramedVertexTraversal) super.groupCount(new FramedTraversalFunction(keyFunction, graph));
		}

		@Override
		public FramedVertexTraversal groupCount(TraversalFunction keyFunction, TraversalFunction valueFunction) {
			return (FramedVertexTraversal) super.groupCount(new FramedTraversalFunction(keyFunction, graph), new FramedTraversalFunction(valueFunction, graph));
		}

		@Override
		public FramedVertexTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction, TraversalFunction reduceFunction) {
			return (FramedVertexTraversal) super.groupBy(new FramedTraversalFunction(keyFunction, graph), new FramedTraversalFunction(valueFunction, graph), new FramedTraversalFunction(reduceFunction, graph));
		}

		@Override
		public FramedVertexTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction) {

			return (FramedVertexTraversal) super.groupBy(new FramedTraversalFunction(keyFunction, graph), new FramedTraversalFunction(valueFunction, graph));
		}

		@Override
		public FramedVertexTraversal groupBy(Map reduceMap, TraversalFunction keyFunction, TraversalFunction valueFunction,
				TraversalFunction reduceFunction) {
			return (FramedVertexTraversal) super.groupBy(reduceMap, new FramedTraversalFunction(keyFunction, graph), new FramedTraversalFunction(valueFunction, graph), new FramedTraversalFunction(reduceFunction, graph));
		}

		@Override
		public FramedVertexTraversal groupBy(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
			return (FramedVertexTraversal) super.groupBy(map, new FramedTraversalFunction(keyFunction, graph), new FramedTraversalFunction(valueFunction, graph));
		}

		@Override
		public FramedVertexTraversal filter(TraversalFunction filterFunction) {
			return (FramedVertexTraversal) super.filter(new FramedTraversalFunction(filterFunction, graph));

		}

		@Override
		public FramedVertexTraversal aggregate() {
			return (FramedVertexTraversal) super.aggregate(new FramedTraversalFunction(graph));

		}

		@Override
		public FramedVertexTraversal aggregate(Collection aggregate) {
			return (FramedVertexTraversal) super.aggregate(aggregate, new FramedTraversalFunction(graph));

		}

		@Override
		public FramedVertexTraversal aggregate(Collection aggregate, TraversalFunction aggregateFunction) {
			return (FramedVertexTraversal) super.aggregate(aggregate, new FramedTraversalFunction(aggregateFunction, graph));

		}

		@Override
		public FramedVertexTraversal aggregate(TraversalFunction aggregateFunction) {
			return (FramedVertexTraversal) super.aggregate(new FramedTraversalFunction(aggregateFunction, graph));

		}

		@Override
		public FramedVertexTraversal and(Pipe... pipes) {

			return (FramedVertexTraversal) super.and(pipes);

		}

		@Override
		public FramedVertexTraversal sideEffect(SideEffectFunction sideEffectFunction) {
			return (FramedVertexTraversal) super.sideEffect(new FramedSideEffectFunction<>(sideEffectFunction, graph));
		}

		@Override
		public FramedVertexTraversal random(Double bias) {

			return (FramedVertexTraversal) super.random(bias);
		}

		@Override
		public FramedVertexTraversal dedup(TraversalFunction dedupFunction) {
			return (FramedVertexTraversal) super.dedup(dedupFunction);
		}

		@Override
		public FramedVertexTraversal except(String... namedSteps) {

			return (FramedVertexTraversal) super.except(namedSteps);
		}

		@Override
		public FramedVertexTraversal except(Collection collection) {
			return (FramedVertexTraversal) super.except(unwrap(collection));
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
		public FramedVertexTraversal order(Comparator compareFunction) {
			return (FramedVertexTraversal) super.order(new FramedComparator(compareFunction, graph));
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
			return (FramedVertexTraversal) super.retain(unwrap(collection));

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
		public Iterable frame(final Class kind) {
			return Iterables.transform(pipeline, new Function() {

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

		@Override
		public Collection fill(Collection collection) {
			return super.fill(new FramedCollection<>(collection, graph, GenericFramedVertex.class));
		}
		
		@Override
		public Collection fill(Collection collection, Class clazz) {

			return super.fill(new FramedCollection<>(collection, graph, clazz));
		}

		@Override
		protected FramedTraversal asTraversal() {
			return FramedTraversalImpl.this;
		}
		
		@Override
		public Iterator iterator() {
			return Iterators.transform(pipeline, new Function() {

				public Object apply(Object e) {
					return graph.frameElement((Element) e, GenericFramedVertex.class);
				}
			});
		}
		
	}



}

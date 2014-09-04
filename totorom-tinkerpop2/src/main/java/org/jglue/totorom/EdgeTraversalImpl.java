package org.jglue.totorom;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.gremlin.Tokens.T;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.transform.TransformPipe.Order;
import com.tinkerpop.pipes.util.structures.Table;
import com.tinkerpop.pipes.util.structures.Tree;

abstract class EdgeTraversalImpl extends TraversalBase implements EdgeTraversal {

	@Override
	public TEdge next() {

		return graph().frameElement((Edge) pipeline().next(), TEdge.class);
	}

	@Override
	public EdgeTraversal table() {
		return (EdgeTraversal) super.table();
	}

	@Override
	public EdgeTraversal table(TraversalFunction... columnFunctions) {

		return (EdgeTraversal) super.table(columnFunctions);
	}

	@Override
	public EdgeTraversal table(Table table) {

		return (EdgeTraversal) super.table(table);
	}

	@Override
	public EdgeTraversal table(Table table, Collection stepNames, TraversalFunction... columnFunctions) {

		return (EdgeTraversal) super.table(table, stepNames, columnFunctions);
	}

	@Override
	public EdgeTraversal table(Table table, TraversalFunction... columnFunctions) {
		return (EdgeTraversal) super.table(table, columnFunctions);
	}

	@Override
	public EdgeTraversal tree(TraversalFunction... branchFunctions) {
		return (EdgeTraversal) super.tree(branchFunctions);
	}

	@Override
	public EdgeTraversal tree(Tree tree, TraversalFunction... branchFunctions) {
		return (EdgeTraversal) super.tree(tree, branchFunctions);
	}

	@Override
	public EdgeTraversal store() {
		return (EdgeTraversal) super.store(new FramingTraversalFunction(graph(), TEdge.class));
	}

	@Override
	public EdgeTraversal store(Collection storage) {
		return (EdgeTraversal) super.store(storage, new FramingTraversalFunction(graph(), TEdge.class));
	}

	@Override
	public EdgeTraversal store(Collection storage, TraversalFunction storageFunction) {
		return (EdgeTraversal) super.store(storage, new FramingTraversalFunction(storageFunction, graph(),
				TEdge.class));
	}

	@Override
	public EdgeTraversal store(TraversalFunction storageFunction) {
		return (EdgeTraversal) super.store(new FramingTraversalFunction(storageFunction, graph(), TEdge.class));
	}

	@Override
	public EdgeTraversal groupCount() {
		return (EdgeTraversal) super.groupCount(new FramingMap(new HashMap(), graph()));
	}

	@Override
	public EdgeTraversal groupCount(Map map) {
		return (EdgeTraversal) super.groupCount(new FramingMap(map, graph()));
	}

	@Override
	public EdgeTraversal groupCount(Map map, TraversalFunction keyFunction) {
		return (EdgeTraversal) super.groupCount(map, new FramingTraversalFunction(keyFunction, graph(),
				TEdge.class));
	}

	@Override
	public EdgeTraversal groupCount(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return (EdgeTraversal) super.groupCount(map, new FramingTraversalFunction(keyFunction, graph(),
				TEdge.class), new FramingTraversalFunction(valueFunction, graph(), TEdge.class));
	}

	@Override
	public EdgeTraversal groupCount(TraversalFunction keyFunction) {
		return (EdgeTraversal) super
				.groupCount(new FramingTraversalFunction(keyFunction, graph(), TEdge.class));
	}

	@Override
	public EdgeTraversal groupCount(TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return (EdgeTraversal) super.groupCount(
				new FramingTraversalFunction(keyFunction, graph(), TEdge.class), new FramingTraversalFunction(
						valueFunction, graph(), TEdge.class));
	}

	@Override
	public EdgeTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction,
			TraversalFunction reduceFunction) {
		return (EdgeTraversal) super.groupBy(new FramingTraversalFunction(keyFunction, graph(), TEdge.class),
				new FramingTraversalFunction(valueFunction, graph(), TEdge.class), new FramingTraversalFunction(
						reduceFunction, graph(), TEdge.class));
	}

	@Override
	public EdgeTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction) {

		return (EdgeTraversal) super.groupBy(new FramingTraversalFunction(keyFunction, graph(), TEdge.class),
				new FramingTraversalFunction(valueFunction, graph(), TEdge.class));
	}

	@Override
	public EdgeTraversal groupBy(Map reduceMap, TraversalFunction keyFunction, TraversalFunction valueFunction,
			TraversalFunction reduceFunction) {
		return (EdgeTraversal) super.groupBy(reduceMap, new FramingTraversalFunction(keyFunction, graph(),
				TEdge.class), new FramingTraversalFunction(valueFunction, graph(), TEdge.class),
				new FramingTraversalFunction(reduceFunction, graph(), TEdge.class));
	}

	@Override
	public EdgeTraversal groupBy(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return (EdgeTraversal) super.groupBy(map, new FramingTraversalFunction(keyFunction, graph(),
				TEdge.class), new FramingTraversalFunction(valueFunction, graph(), TEdge.class));
	}

	@Override
	public EdgeTraversal has(String key) {
		return (EdgeTraversal) super.has(key);
	}

	@Override
	public EdgeTraversal has(String key, Object value) {
		return (EdgeTraversal) super.has(key, value);
	}

	@Override
	public EdgeTraversal has(String key, Predicate predicate, Object value) {
		return (EdgeTraversal) super.has(key, predicate, value);
	}

	@Override
	public EdgeTraversal has(String key, com.tinkerpop.gremlin.Tokens.T compareToken, Object value) {
		return (EdgeTraversal) super.has(key, compareToken, value);
	}

	@Override
	public EdgeTraversal hasNot(String key) {
		return (EdgeTraversal) super.hasNot(key);
	}

	@Override
	public EdgeTraversal hasNot(String key, Object value) {
		return (EdgeTraversal) super.hasNot(key, value);
	}

	@Override
	public EdgeTraversal as(String name) {
		return (EdgeTraversal) super.as(name);
	}

	@Override
	public EdgeTraversal identity() {

		return (EdgeTraversal) super.identity();
	}

	@Override
	public EdgeTraversal interval(String key, Comparable startValue, Comparable endValue) {
		return (EdgeTraversal) super.interval(key, startValue, endValue);
	}

	@Override
	public VertexTraversal inV() {
		pipeline().inV();
		return castToVertices();
	}

	@Override
	public VertexTraversal outV() {
		pipeline().outV();
		return castToVertices();
	}

	@Override
	public VertexTraversal bothV() {
		pipeline().bothV();
		return castToVertices();
	}

	@Override
	public FramedEdge next(Class kind) {
		return (FramedEdge) graph().frameElement((Element) pipeline().next(), kind);
	}

	@Override
	public List next(int amount, final Class kind) {
		return Lists.transform(pipeline().next(amount), new Function() {

			public Object apply(Object e) {
				return graph().frameElement((Element) e, kind);
			}
		});
	}

	@Override
	public Iterable frame(final Class kind) {
		return Iterables.transform(pipeline(), new Function() {

			public Object apply(Object e) {
				return graph().frameElement((Element) e, kind);
			}
		});
	}

	@Override
	public List<T> toList(final Class kind) {
		return Lists.transform(pipeline().toList(), new Function() {

			public Object apply(Object e) {
				return graph().frameElement((Element) e, kind);
			}
		});
	}

	@Override
	public Traversal label() {
		pipeline().label();
		return this;
	}

	@Override
	public EdgeTraversal filter(TraversalFunction filterFunction) {
		return (EdgeTraversal) super.filter(new FramingTraversalFunction(filterFunction, graph(), TEdge.class));

	}

	@Override
	public EdgeTraversal aggregate() {
		return (EdgeTraversal) super.aggregate(new FramingTraversalFunction(graph(), TEdge.class));

	}

	@Override
	public EdgeTraversal aggregate(Collection aggregate) {
		return (EdgeTraversal) super.aggregate(aggregate, new FramingTraversalFunction(graph(), TEdge.class));

	}

	@Override
	public EdgeTraversal aggregate(Collection aggregate, TraversalFunction aggregateFunction) {
		return (EdgeTraversal) super.aggregate(aggregate, new FramingTraversalFunction(aggregateFunction, graph(),
				TEdge.class));

	}

	@Override
	public EdgeTraversal aggregate(TraversalFunction aggregateFunction) {
		return (EdgeTraversal) super.aggregate(new FramingTraversalFunction(aggregateFunction, graph(),
				TEdge.class));

	}

	

	@Override
	public EdgeTraversal and(TraversalFunction... pipes) {
		Collection<Pipe> extractedPipes = Collections2.transform(Arrays.asList(pipes), new Function<TraversalFunction, Pipe>() {

			@Override
			public Pipe apply(TraversalFunction input) {
				return ((TraversalBase) input.compute(new TEdge())).pipeline();
			}
		});
		pipeline().and(extractedPipes.toArray(new Pipe[extractedPipes.size()]));
		return this;
	}
	
	@Override
	public EdgeTraversal or(TraversalFunction... pipes) {
		Collection<Pipe> extractedPipes = Collections2.transform(Arrays.asList(pipes), new Function<TraversalFunction, Pipe>() {

			@Override
			public Pipe apply(TraversalFunction input) {
				return ((TraversalBase) input.compute(new TEdge())).pipeline();
			}
		});
		pipeline().or(extractedPipes.toArray(new Pipe[extractedPipes.size()]));
		return this;
	}

	@Override
	public EdgeTraversal sideEffect(SideEffectFunction sideEffectFunction) {
		return (EdgeTraversal) super.sideEffect(new FramingSideEffectFunction(sideEffectFunction, graph(),
				TEdge.class));
	}

	@Override
	public EdgeTraversal random(Double bias) {

		return (EdgeTraversal) super.random(bias);
	}

	@Override
	public EdgeTraversal dedup(TraversalFunction dedupFunction) {
		return (EdgeTraversal) super.dedup(new FramingTraversalFunction(dedupFunction, graph(), TEdge.class));
	}

	@Override
	public EdgeTraversal except(String... namedSteps) {

		return (EdgeTraversal) super.except(namedSteps);
	}

	@Override
	public EdgeTraversal except(Collection collection) {
		return (EdgeTraversal) super.except(unwrap(collection));
	}

	@Override
	public EdgeTraversal range(int low, int high) {
		return (EdgeTraversal) super.range(low, high);
	}

	@Override
	public EdgeTraversal order() {
		return (EdgeTraversal) super.order();
	}

	@Override
	public EdgeTraversal order(Order order) {
		return (EdgeTraversal) super.order(order);
	}

	@Override
	public EdgeTraversal order(Comparator compareFunction) {
		return (EdgeTraversal) super.order(new FramingComparator(compareFunction, graph(), TEdge.class));
	}

	@Override
	public EdgeTraversal order(T order) {
		return (EdgeTraversal) super.order(order);
	}

	@Override
	public EdgeTraversal dedup() {

		return (EdgeTraversal) super.dedup();
	}

	@Override
	public EdgeTraversal retain(Collection collection) {
		return (EdgeTraversal) super.retain(unwrap(collection));

	}

	@Override
	public EdgeTraversal retain(String... namedSteps) {
		return (EdgeTraversal) super.retain(namedSteps);
	}

	@Override
	public EdgeTraversal simplePath() {
		return (EdgeTraversal) super.simplePath();
	}

	@Override
	public EdgeTraversal memoize(String namedStep) {
		return (EdgeTraversal) super.memoize(namedStep);

	}

	@Override
	public EdgeTraversal memoize(String namedStep, Map map) {
		return (EdgeTraversal) super.memoize(namedStep, map);

	}

	@Override
	public Collection fill(Collection collection) {
		return super.fill(new FramingCollection<>(collection, graph(), TEdge.class));
	}

	@Override
	public Collection fill(Collection collection, Class clazz) {

		return super.fill(new FramingCollection(collection, graph(), clazz));
	}

	public java.util.Iterator<TEdge> iterator() {
		return Iterators.transform(pipeline(), new Function() {

			public Object apply(Object e) {
				return graph().frameElement((Element) e, TEdge.class);
			}
		});
	};

	@Override
	public EdgeTraversal gatherScatter() {

		return (EdgeTraversal) super.gatherScatter();
	}
}

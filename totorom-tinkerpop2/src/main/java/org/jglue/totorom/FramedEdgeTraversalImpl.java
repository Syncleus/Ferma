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

public abstract class FramedEdgeTraversalImpl extends FramedTraversalBase implements FramedEdgeTraversal {

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
		return (FramedEdgeTraversal) super.store(new FramingTraversalFunction(graph(), GenericFramedEdge.class));
	}

	@Override
	public FramedEdgeTraversal store(Collection storage) {
		return (FramedEdgeTraversal) super.store(storage, new FramingTraversalFunction(graph(), GenericFramedEdge.class));
	}

	@Override
	public FramedEdgeTraversal store(Collection storage, TraversalFunction storageFunction) {
		return (FramedEdgeTraversal) super.store(storage, new FramingTraversalFunction(storageFunction, graph(),
				GenericFramedEdge.class));
	}

	@Override
	public FramedEdgeTraversal store(TraversalFunction storageFunction) {
		return (FramedEdgeTraversal) super.store(new FramingTraversalFunction(storageFunction, graph(), GenericFramedEdge.class));
	}

	@Override
	public FramedEdgeTraversal groupCount() {
		return (FramedEdgeTraversal) super.groupCount(new FramingMap(new HashMap(), graph()));
	}

	@Override
	public FramedEdgeTraversal groupCount(Map map) {
		return (FramedEdgeTraversal) super.groupCount(new FramingMap(map, graph()));
	}

	@Override
	public FramedEdgeTraversal groupCount(Map map, TraversalFunction keyFunction) {
		return (FramedEdgeTraversal) super.groupCount(map, new FramingTraversalFunction(keyFunction, graph(),
				GenericFramedEdge.class));
	}

	@Override
	public FramedEdgeTraversal groupCount(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return (FramedEdgeTraversal) super.groupCount(map, new FramingTraversalFunction(keyFunction, graph(),
				GenericFramedEdge.class), new FramingTraversalFunction(valueFunction, graph(), GenericFramedEdge.class));
	}

	@Override
	public FramedEdgeTraversal groupCount(TraversalFunction keyFunction) {
		return (FramedEdgeTraversal) super
				.groupCount(new FramingTraversalFunction(keyFunction, graph(), GenericFramedEdge.class));
	}

	@Override
	public FramedEdgeTraversal groupCount(TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return (FramedEdgeTraversal) super.groupCount(
				new FramingTraversalFunction(keyFunction, graph(), GenericFramedEdge.class), new FramingTraversalFunction(
						valueFunction, graph(), GenericFramedEdge.class));
	}

	@Override
	public FramedEdgeTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction,
			TraversalFunction reduceFunction) {
		return (FramedEdgeTraversal) super.groupBy(new FramingTraversalFunction(keyFunction, graph(), GenericFramedEdge.class),
				new FramingTraversalFunction(valueFunction, graph(), GenericFramedEdge.class), new FramingTraversalFunction(
						reduceFunction, graph(), GenericFramedEdge.class));
	}

	@Override
	public FramedEdgeTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction) {

		return (FramedEdgeTraversal) super.groupBy(new FramingTraversalFunction(keyFunction, graph(), GenericFramedEdge.class),
				new FramingTraversalFunction(valueFunction, graph(), GenericFramedEdge.class));
	}

	@Override
	public FramedEdgeTraversal groupBy(Map reduceMap, TraversalFunction keyFunction, TraversalFunction valueFunction,
			TraversalFunction reduceFunction) {
		return (FramedEdgeTraversal) super.groupBy(reduceMap, new FramingTraversalFunction(keyFunction, graph(),
				GenericFramedEdge.class), new FramingTraversalFunction(valueFunction, graph(), GenericFramedEdge.class),
				new FramingTraversalFunction(reduceFunction, graph(), GenericFramedEdge.class));
	}

	@Override
	public FramedEdgeTraversal groupBy(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return (FramedEdgeTraversal) super.groupBy(map, new FramingTraversalFunction(keyFunction, graph(),
				GenericFramedEdge.class), new FramingTraversalFunction(valueFunction, graph(), GenericFramedEdge.class));
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
		pipeline().inV();
		return castToVertices();
	}

	@Override
	public FramedVertexTraversal outV() {
		pipeline().outV();
		return castToVertices();
	}

	@Override
	public FramedVertexTraversal bothV() {
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
	public FramedTraversal label() {
		pipeline().label();
		return this;
	}

	@Override
	public FramedEdgeTraversal filter(TraversalFunction filterFunction) {
		return (FramedEdgeTraversal) super.filter(new FramingTraversalFunction(filterFunction, graph(), GenericFramedEdge.class));

	}

	@Override
	public FramedEdgeTraversal aggregate() {
		return (FramedEdgeTraversal) super.aggregate(new FramingTraversalFunction(graph(), GenericFramedEdge.class));

	}

	@Override
	public FramedEdgeTraversal aggregate(Collection aggregate) {
		return (FramedEdgeTraversal) super.aggregate(aggregate, new FramingTraversalFunction(graph(), GenericFramedEdge.class));

	}

	@Override
	public FramedEdgeTraversal aggregate(Collection aggregate, TraversalFunction aggregateFunction) {
		return (FramedEdgeTraversal) super.aggregate(aggregate, new FramingTraversalFunction(aggregateFunction, graph(),
				GenericFramedEdge.class));

	}

	@Override
	public FramedEdgeTraversal aggregate(TraversalFunction aggregateFunction) {
		return (FramedEdgeTraversal) super.aggregate(new FramingTraversalFunction(aggregateFunction, graph(),
				GenericFramedEdge.class));

	}

	

	@Override
	public FramedEdgeTraversal and(TraversalFunction... pipes) {
		Collection<Pipe> extractedPipes = Collections2.transform(Arrays.asList(pipes), new Function<TraversalFunction, Pipe>() {

			@Override
			public Pipe apply(TraversalFunction input) {
				return ((FramedTraversalImpl) input.compute(new GenericFramedEdge())).pipeline().get(0);
			}
		});
		pipeline().and(extractedPipes.toArray(new Pipe[extractedPipes.size()]));
		return this;
	}
	
	@Override
	public FramedEdgeTraversal or(TraversalFunction... pipes) {
		Collection<Pipe> extractedPipes = Collections2.transform(Arrays.asList(pipes), new Function<TraversalFunction, Pipe>() {

			@Override
			public Pipe apply(TraversalFunction input) {
				return ((FramedTraversalImpl) input.compute(new GenericFramedEdge())).pipeline().get(0);
			}
		});
		pipeline().or(extractedPipes.toArray(new Pipe[extractedPipes.size()]));
		return this;
	}

	@Override
	public FramedEdgeTraversal sideEffect(SideEffectFunction sideEffectFunction) {
		return (FramedEdgeTraversal) super.sideEffect(new FramingSideEffectFunction(sideEffectFunction, graph(),
				GenericFramedEdge.class));
	}

	@Override
	public FramedEdgeTraversal random(Double bias) {

		return (FramedEdgeTraversal) super.random(bias);
	}

	@Override
	public FramedEdgeTraversal dedup(TraversalFunction dedupFunction) {
		return (FramedEdgeTraversal) super.dedup(new FramingTraversalFunction(dedupFunction, graph(), GenericFramedEdge.class));
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
	public FramedEdgeTraversal order() {
		return (FramedEdgeTraversal) super.order();
	}

	@Override
	public FramedEdgeTraversal order(Order order) {
		return (FramedEdgeTraversal) super.order(order);
	}

	@Override
	public FramedEdgeTraversal order(Comparator compareFunction) {
		return (FramedEdgeTraversal) super.order(new FramingComparator(compareFunction, graph(), GenericFramedEdge.class));
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
		return super.fill(new FramingCollection<>(collection, graph(), GenericFramedEdge.class));
	}

	@Override
	public Collection fill(Collection collection, Class clazz) {

		return super.fill(new FramingCollection(collection, graph(), clazz));
	}

	public java.util.Iterator<GenericFramedEdge> iterator() {
		return Iterators.transform(pipeline(), new Function() {

			public Object apply(Object e) {
				return graph().frameElement((Element) e, GenericFramedEdge.class);
			}
		});
	};

	@Override
	public FramedEdgeTraversal gatherScatter() {

		return (FramedEdgeTraversal) super.gatherScatter();
	}
}

package org.jglue.totorom;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
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
	public List toList() {
		return toList(TEdge.class);
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
	public EdgeTraversal tree(Tree tree) {
		return (EdgeTraversal) super.tree(tree);
	}

	@Override
	public EdgeTraversal store() {
		return (EdgeTraversal) super.store();
	}

	@Override
	public EdgeTraversal store(Collection storage) {
		return (EdgeTraversal) super.store(storage);
	}

	@Override
	public EdgeTraversal store(Collection storage, TraversalFunction storageFunction) {
		return (EdgeTraversal) super.store(storage, storageFunction);
	}

	@Override
	public EdgeTraversal store(TraversalFunction storageFunction) {
		return (EdgeTraversal) super.store(storageFunction);
	}

	@Override
	public EdgeTraversal groupCount() {
		return (EdgeTraversal) super.groupCount();
	}

	@Override
	public EdgeTraversal groupCount(Map map) {
		return (EdgeTraversal) super.groupCount(map);
	}

	@Override
	public EdgeTraversal groupCount(Map map, TraversalFunction keyFunction) {
		return (EdgeTraversal) super.groupCount(map, keyFunction);
	}

	@Override
	public EdgeTraversal groupCount(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return (EdgeTraversal) super.groupCount(map, keyFunction, valueFunction);
	}

	@Override
	public EdgeTraversal groupCount(TraversalFunction keyFunction) {
		return (EdgeTraversal) super.groupCount(keyFunction);
	}

	@Override
	public EdgeTraversal groupCount(TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return (EdgeTraversal) super.groupCount(keyFunction, valueFunction);
	}

	@Override
	public EdgeTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction, TraversalFunction reduceFunction) {
		return (EdgeTraversal) super.groupBy(keyFunction, valueFunction, reduceFunction);
	}

	@Override
	public EdgeTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction) {

		return (EdgeTraversal) super.groupBy(keyFunction, valueFunction);
	}

	@Override
	public EdgeTraversal groupBy(Map reduceMap, TraversalFunction keyFunction, TraversalFunction valueFunction,
			TraversalFunction reduceFunction) {
		return (EdgeTraversal) super.groupBy(reduceMap, keyFunction, valueFunction, reduceFunction);
	}

	@Override
	public EdgeTraversal groupBy(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return (EdgeTraversal) super.groupBy(map, keyFunction, valueFunction);
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
		return castToTraversal();
	}

	@Override
	public EdgeTraversal filter(TraversalFunction filterFunction) {
		return (EdgeTraversal) super.filter(filterFunction);

	}

	@Override
	public EdgeTraversal aggregate() {
		return (EdgeTraversal) super.aggregate();

	}

	@Override
	public EdgeTraversal aggregate(Collection aggregate) {
		return (EdgeTraversal) super.aggregate(aggregate);

	}

	@Override
	public EdgeTraversal aggregate(Collection aggregate, TraversalFunction aggregateFunction) {
		return (EdgeTraversal) super.aggregate(aggregate, aggregateFunction);

	}

	@Override
	public EdgeTraversal aggregate(TraversalFunction aggregateFunction) {
		return (EdgeTraversal) super.aggregate(aggregateFunction);

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
		return (EdgeTraversal) super.sideEffect(sideEffectFunction);
	}

	@Override
	public EdgeTraversal random(double bias) {

		return (EdgeTraversal) super.random(bias);
	}

	@Override
	public EdgeTraversal dedup(TraversalFunction dedupFunction) {
		return (EdgeTraversal) super.dedup(dedupFunction);
	}

	@Override
	public EdgeTraversal except(String... namedSteps) {

		return (EdgeTraversal) super.except(namedSteps);
	}

	@Override
	public EdgeTraversal except(Iterable collection) {
		return (EdgeTraversal) super.except(Lists.newArrayList(collection));
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
		return (EdgeTraversal) super.order(compareFunction);
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
		return super.fill(collection);
	}

	@Override
	public Collection fill(Collection collection, Class clazz) {

		return pipeline().fill(new FramingCollection(collection, graph(), clazz));
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

	@Override
	public EdgeTraversal divert(SideEffectFunction sideEffect) {
		return (EdgeTraversal) super.divert(sideEffect);
	}

	@Override
	public EdgeTraversal retain(FramedEdge... edges) {

		return (EdgeTraversal) super.retain(Arrays.asList(edges));
	}

	@Override
	public EdgeTraversal shuffle() {
		return (EdgeTraversal) super.shuffle();
	}

	@Override
	public EdgeTraversal except(FramedEdge... edges) {
		return (EdgeTraversal) super.retain(Arrays.asList(edges));
	}

	@Override
	public EdgeTraversal retain(Iterable collection) {

		return (EdgeTraversal) super.retain(Lists.newArrayList(collection));
	}

	@Override
	public EdgeTraversal mark() {

		return (EdgeTraversal) super.mark();
	}

	@Override
	public void removeAll() {
		pipeline().remove();
	}

	@Override
	public SplitTraversal copySplit(TraversalFunction... traversals) {
		Collection<Pipe> extractedPipes = Collections2.transform(Arrays.asList(traversals),
				new Function<TraversalFunction, Pipe>() {

					@Override
					public Pipe apply(TraversalFunction input) {
						return ((TraversalBase) input.compute(new TEdge())).pipeline();
					}
				});
		pipeline().copySplit(extractedPipes.toArray(new Pipe[extractedPipes.size()]));
		return castToSplit();
	}

	@Override
	public EdgeTraversal tree() {

		return (EdgeTraversal) super.tree();
	}
	
	@Override
	public EdgeTraversal loop(TraversalFunction input) {
		return (EdgeTraversal) super.loop(input);
	}

	@Override
	public EdgeTraversal loop(TraversalFunction input, int depth) {
		return (EdgeTraversal) super.loop(input, depth);
	}
}

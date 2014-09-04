package org.jglue.totorom;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.Tokens.T;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.transform.TransformPipe.Order;
import com.tinkerpop.pipes.util.structures.Table;
import com.tinkerpop.pipes.util.structures.Tree;

public abstract class VertexTraversalImpl extends TraversalBase implements VertexTraversal {
	@Override
	public TVertex next() {
		return graph().frameElement((Vertex) pipeline().next(), TVertex.class);
	}

	@Override
	public VertexTraversal table() {
		return (VertexTraversal) super.table();
	}

	@Override
	public VertexTraversal table(TraversalFunction... columnFunctions) {

		return (VertexTraversal) super.table(columnFunctions);
	}

	@Override
	public VertexTraversal table(Table table) {

		return (VertexTraversal) super.table(table);
	}

	@Override
	public VertexTraversal table(Table table, Collection stepNames, TraversalFunction... columnFunctions) {

		return (VertexTraversal) super.table(table, stepNames, columnFunctions);
	}

	@Override
	public VertexTraversal table(Table table, TraversalFunction... columnFunctions) {
		return (VertexTraversal) super.table(table, columnFunctions);
	}

	@Override
	public VertexTraversal tree(TraversalFunction... branchFunctions) {
		return (VertexTraversal) super.tree(branchFunctions);
	}

	@Override
	public VertexTraversal tree(Tree tree, TraversalFunction... branchFunctions) {
		return (VertexTraversal) super.tree(tree, branchFunctions);
	}

	@Override
	public VertexTraversal store() {
		return (VertexTraversal) super.store(new FramingTraversalFunction(graph(), TVertex.class));
	}

	@Override
	public VertexTraversal store(Collection storage) {
		return (VertexTraversal) super.store(storage, new FramingTraversalFunction(graph(), TVertex.class));
	}

	@Override
	public VertexTraversal store(Collection storage, TraversalFunction storageFunction) {
		return (VertexTraversal) super.store(storage, new FramingTraversalFunction(storageFunction, graph(),
				TVertex.class));
	}

	@Override
	public VertexTraversal store(TraversalFunction storageFunction) {
		return (VertexTraversal) super.store(new FramingTraversalFunction(storageFunction, graph(),
				TVertex.class));
	}

	@Override
	public VertexTraversal groupCount() {
		return (VertexTraversal) super.groupCount(new FramingMap(new HashMap(), graph()));
	}

	@Override
	public VertexTraversal groupCount(Map map) {
		return (VertexTraversal) super.groupCount(new FramingMap(map, graph()));
	}

	@Override
	public VertexTraversal groupCount(Map map, TraversalFunction keyFunction) {
		return (VertexTraversal) super.groupCount(map, new FramingTraversalFunction(keyFunction, graph(),
				TVertex.class));
	}

	@Override
	public VertexTraversal groupCount(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return (VertexTraversal) super.groupCount(map, new FramingTraversalFunction(keyFunction, graph(),
				TVertex.class), new FramingTraversalFunction(valueFunction, graph(), TVertex.class));
	}

	@Override
	public VertexTraversal groupCount(TraversalFunction keyFunction) {
		return (VertexTraversal) super.groupCount(new FramingTraversalFunction(keyFunction, graph(),
				TVertex.class));
	}

	@Override
	public VertexTraversal groupCount(TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return (VertexTraversal) super.groupCount(new FramingTraversalFunction(keyFunction, graph(),
				TVertex.class), new FramingTraversalFunction(valueFunction, graph(), TVertex.class));
	}

	@Override
	public VertexTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction,
			TraversalFunction reduceFunction) {
		return (VertexTraversal) super.groupBy(
				new FramingTraversalFunction(keyFunction, graph(), TVertex.class), new FramingTraversalFunction(
						valueFunction, graph(), TVertex.class), new FramingTraversalFunction(reduceFunction, graph(),
						TVertex.class));
	}

	@Override
	public VertexTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction) {

		return (VertexTraversal) super.groupBy(
				new FramingTraversalFunction(keyFunction, graph(), TVertex.class), new FramingTraversalFunction(
						valueFunction, graph(), TVertex.class));
	}

	@Override
	public VertexTraversal groupBy(Map reduceMap, TraversalFunction keyFunction, TraversalFunction valueFunction,
			TraversalFunction reduceFunction) {
		return (VertexTraversal) super.groupBy(reduceMap, new FramingTraversalFunction(keyFunction, graph(),
				TVertex.class), new FramingTraversalFunction(valueFunction, graph(), TVertex.class),
				new FramingTraversalFunction(reduceFunction, graph(), TVertex.class));
	}

	@Override
	public VertexTraversal groupBy(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return (VertexTraversal) super.groupBy(map, new FramingTraversalFunction(keyFunction, graph(),
				TVertex.class), new FramingTraversalFunction(valueFunction, graph(), TVertex.class));
	}

	@Override
	public VertexTraversal filter(TraversalFunction filterFunction) {
		return (VertexTraversal) super.filter(new FramingTraversalFunction(filterFunction, graph(),
				TVertex.class));

	}

	@Override
	public VertexTraversal aggregate() {
		return (VertexTraversal) super.aggregate(new FramingTraversalFunction(graph(), TVertex.class));

	}

	@Override
	public VertexTraversal aggregate(Collection aggregate) {
		return (VertexTraversal) super.aggregate(aggregate,
				new FramingTraversalFunction(graph(), TVertex.class));

	}

	@Override
	public VertexTraversal aggregate(Collection aggregate, TraversalFunction aggregateFunction) {
		return (VertexTraversal) super.aggregate(aggregate, new FramingTraversalFunction(aggregateFunction, graph(),
				TVertex.class));

	}

	@Override
	public VertexTraversal aggregate(TraversalFunction aggregateFunction) {
		return (VertexTraversal) super.aggregate(new FramingTraversalFunction(aggregateFunction, graph(),
				TVertex.class));

	}



	@Override
	public VertexTraversal sideEffect(SideEffectFunction sideEffectFunction) {
		return (VertexTraversal) super.sideEffect(new FramingSideEffectFunction<>(sideEffectFunction, graph(),
				TVertex.class));
	}

	@Override
	public VertexTraversal random(Double bias) {

		return (VertexTraversal) super.random(bias);
	}

	@Override
	public VertexTraversal dedup(TraversalFunction dedupFunction) {
		return (VertexTraversal) super.dedup(dedupFunction);
	}

	@Override
	public VertexTraversal except(String... namedSteps) {

		return (VertexTraversal) super.except(namedSteps);
	}

	@Override
	public VertexTraversal except(Collection collection) {
		return (VertexTraversal) super.except(unwrap(collection));
	}

	@Override
	public VertexTraversal range(int low, int high) {
		return (VertexTraversal) super.range(low, high);
	}

	@Override
	public VertexTraversal and(TraversalFunction... pipes) {
		Collection<Pipe> extractedPipes = Collections2.transform(Arrays.asList(pipes), new Function<TraversalFunction, Pipe>() {

			@Override
			public Pipe apply(TraversalFunction input) {
				return ((TraversalBase) input.compute(new TVertex())).pipeline();
			}
		});
		pipeline().and(extractedPipes.toArray(new Pipe[extractedPipes.size()]));
		return this;
	}
	
	@Override
	public VertexTraversal or(TraversalFunction... pipes) {
		Collection<Pipe> extractedPipes = Collections2.transform(Arrays.asList(pipes), new Function<TraversalFunction, Pipe>() {

			@Override
			public Pipe apply(TraversalFunction input) {
				return ((TraversalBase) input.compute(new TVertex())).pipeline();
			}
		});
		pipeline().or(extractedPipes.toArray(new Pipe[extractedPipes.size()]));
		return this;
	}
	
	@Override
	public VertexTraversal order() {
		return (VertexTraversal) super.order();
	}

	@Override
	public VertexTraversal order(Order order) {
		return (VertexTraversal) super.order(order);
	}

	@Override
	public VertexTraversal order(Comparator compareFunction) {
		return (VertexTraversal) super.order(new FramingComparator(compareFunction, graph(), TVertex.class));
	}

	@Override
	public VertexTraversal order(T order) {
		return (VertexTraversal) super.order(order);
	}

	@Override
	public VertexTraversal dedup() {

		return (VertexTraversal) super.dedup();
	}

	@Override
	public VertexTraversal retain(Collection collection) {
		return (VertexTraversal) super.retain(unwrap(collection));

	}

	@Override
	public VertexTraversal retain(String... namedSteps) {
		return (VertexTraversal) super.retain(namedSteps);
	}

	@Override
	public VertexTraversal simplePath() {
		return (VertexTraversal) super.simplePath();
	}

	@Override
	public VertexTraversal memoize(String namedStep) {
		return (VertexTraversal) super.memoize(namedStep);

	}

	@Override
	public VertexTraversal memoize(String namedStep, Map map) {
		return (VertexTraversal) super.memoize(namedStep, map);

	}

	@Override
	public VertexTraversal out(int branchFactor, String... labels) {
		pipeline().out(branchFactor, labels);
		return this;
	}

	@Override
	public VertexTraversal out(String... labels) {
		pipeline().out(labels);
		return this;
	}

	@Override
	public VertexTraversal in(int branchFactor, String... labels) {
		pipeline().in(branchFactor, labels);
		return this;
	}

	@Override
	public VertexTraversal in(String... labels) {
		pipeline().in(labels);
		return this;
	}

	@Override
	public VertexTraversal both(int branchFactor, String... labels) {
		pipeline().both(branchFactor, labels);
		return this;
	}

	@Override
	public VertexTraversal both(String... labels) {
		pipeline().both(labels);
		return this;
	}

	@Override
	public EdgeTraversal outE(int branchFactor, String... labels) {
		pipeline().outE(branchFactor, labels);
		return castToEdges();
	}

	@Override
	public EdgeTraversal outE(String... labels) {
		pipeline().outE(labels);
		return castToEdges();
	}

	@Override
	public EdgeTraversal inE(int branchFactor, String... labels) {
		pipeline().inE(branchFactor, labels);
		return castToEdges();
	}

	@Override
	public EdgeTraversal inE(String... labels) {
		pipeline().inE(labels);
		return castToEdges();
	}

	@Override
	public EdgeTraversal bothE(int branchFactor, String... labels) {
		pipeline().bothE(branchFactor, labels);
		return castToEdges();
	}

	@Override
	public EdgeTraversal bothE(String... labels) {
		pipeline().bothE(labels);
		return castToEdges();
	}

	@Override
	public VertexTraversal interval(String key, Comparable startValue, Comparable endValue) {
		return (VertexTraversal) super.interval(key, startValue, endValue);
	}

	@Override
	public FramedVertex next(Class kind) {
		return (FramedVertex) graph().frameElement((Element) pipeline().next(), kind);
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
	public List toList(final Class kind) {
		return Lists.transform(pipeline().toList(), new Function() {

			public Object apply(Object e) {
				return graph().frameElement((Element) e, kind);
			}
		});
	}

	@Override
	public VertexTraversal has(String key) {
		return (VertexTraversal) super.has(key);
	}

	@Override
	public VertexTraversal has(String key, Object value) {
		return (VertexTraversal) super.has(key, value);
	}

	@Override
	public VertexTraversal has(String key, Predicate predicate, Object value) {
		return (VertexTraversal) super.has(key, predicate, value);
	}

	@Override
	public VertexTraversal has(String key, com.tinkerpop.gremlin.Tokens.T compareToken, Object value) {
		return (VertexTraversal) super.has(key, compareToken, value);
	}

	@Override
	public VertexTraversal hasNot(String key) {
		return (VertexTraversal) super.hasNot(key);
	}

	@Override
	public VertexTraversal hasNot(String key, Object value) {
		return (VertexTraversal) super.hasNot(key, value);
	}

	@Override
	public VertexTraversal as(String name) {
		return (VertexTraversal) super.as(name);
	}

	@Override
	public VertexTraversal identity() {
		return (VertexTraversal) super.identity();
	}

	@Override
	public VertexTraversal linkOut(String label, String namedStep) {
		pipeline().linkOut(label, namedStep);
		return this;
	}

	@Override
	public VertexTraversal linkIn(String label, String namedStep) {
		pipeline().linkIn(label, namedStep);
		return this;
	}

	@Override
	public VertexTraversal linkBoth(String label, String namedStep) {
		pipeline().linkBoth(label, namedStep);
		return this;
	}

	@Override
	public VertexTraversal linkOut(String label, Vertex other) {
		pipeline().linkOut(label, other);
		return this;
	}

	@Override
	public VertexTraversal linkIn(String label, Vertex other) {
		pipeline().linkIn(label, other);
		return this;
	}

	@Override
	public VertexTraversal linkBoth(String label, Vertex other) {
		pipeline().linkBoth(label, other);
		return this;
	}

	@Override
	public VertexTraversal linkOut(String label, FramedVertex other) {
		pipeline().linkOut(label, other.element());
		return this;
	}

	@Override
	public VertexTraversal linkIn(String label, FramedVertex other) {
		pipeline().linkIn(label, other.element());
		return this;
	}

	@Override
	public VertexTraversal linkBoth(String label, FramedVertex other) {
		pipeline().linkBoth(label, other.element());
		return this;
	}

	@Override
	public Collection fill(Collection collection) {
		return super.fill(new FramingCollection<>(collection, graph(), TVertex.class));
	}

	@Override
	public Collection fill(Collection collection, Class clazz) {

		return super.fill(new FramingCollection<>(collection, graph(), clazz));
	}

	@Override
	public Iterator iterator() {
		return Iterators.transform(pipeline(), new Function() {

			public Object apply(Object e) {
				return graph().frameElement((Element) e, TVertex.class);
			}
		});
	}
	
	@Override
	public VertexTraversal gatherScatter() {
		return (VertexTraversal) super.gatherScatter();
	}
}

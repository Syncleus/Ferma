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

public abstract class FramedVertexTraversalImpl extends FramedTraversalBase implements FramedVertexTraversal {
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
		return (FramedVertexTraversal) super.store(new FramingTraversalFunction(graph(), GenericFramedVertex.class));
	}

	@Override
	public FramedVertexTraversal store(Collection storage) {
		return (FramedVertexTraversal) super.store(storage, new FramingTraversalFunction(graph(), GenericFramedVertex.class));
	}

	@Override
	public FramedVertexTraversal store(Collection storage, TraversalFunction storageFunction) {
		return (FramedVertexTraversal) super.store(storage, new FramingTraversalFunction(storageFunction, graph(),
				GenericFramedVertex.class));
	}

	@Override
	public FramedVertexTraversal store(TraversalFunction storageFunction) {
		return (FramedVertexTraversal) super.store(new FramingTraversalFunction(storageFunction, graph(),
				GenericFramedVertex.class));
	}

	@Override
	public FramedVertexTraversal groupCount() {
		return (FramedVertexTraversal) super.groupCount(new FramingMap(new HashMap(), graph()));
	}

	@Override
	public FramedVertexTraversal groupCount(Map map) {
		return (FramedVertexTraversal) super.groupCount(new FramingMap(map, graph()));
	}

	@Override
	public FramedVertexTraversal groupCount(Map map, TraversalFunction keyFunction) {
		return (FramedVertexTraversal) super.groupCount(map, new FramingTraversalFunction(keyFunction, graph(),
				GenericFramedVertex.class));
	}

	@Override
	public FramedVertexTraversal groupCount(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return (FramedVertexTraversal) super.groupCount(map, new FramingTraversalFunction(keyFunction, graph(),
				GenericFramedVertex.class), new FramingTraversalFunction(valueFunction, graph(), GenericFramedVertex.class));
	}

	@Override
	public FramedVertexTraversal groupCount(TraversalFunction keyFunction) {
		return (FramedVertexTraversal) super.groupCount(new FramingTraversalFunction(keyFunction, graph(),
				GenericFramedVertex.class));
	}

	@Override
	public FramedVertexTraversal groupCount(TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return (FramedVertexTraversal) super.groupCount(new FramingTraversalFunction(keyFunction, graph(),
				GenericFramedVertex.class), new FramingTraversalFunction(valueFunction, graph(), GenericFramedVertex.class));
	}

	@Override
	public FramedVertexTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction,
			TraversalFunction reduceFunction) {
		return (FramedVertexTraversal) super.groupBy(
				new FramingTraversalFunction(keyFunction, graph(), GenericFramedVertex.class), new FramingTraversalFunction(
						valueFunction, graph(), GenericFramedVertex.class), new FramingTraversalFunction(reduceFunction, graph(),
						GenericFramedVertex.class));
	}

	@Override
	public FramedVertexTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction) {

		return (FramedVertexTraversal) super.groupBy(
				new FramingTraversalFunction(keyFunction, graph(), GenericFramedVertex.class), new FramingTraversalFunction(
						valueFunction, graph(), GenericFramedVertex.class));
	}

	@Override
	public FramedVertexTraversal groupBy(Map reduceMap, TraversalFunction keyFunction, TraversalFunction valueFunction,
			TraversalFunction reduceFunction) {
		return (FramedVertexTraversal) super.groupBy(reduceMap, new FramingTraversalFunction(keyFunction, graph(),
				GenericFramedVertex.class), new FramingTraversalFunction(valueFunction, graph(), GenericFramedVertex.class),
				new FramingTraversalFunction(reduceFunction, graph(), GenericFramedVertex.class));
	}

	@Override
	public FramedVertexTraversal groupBy(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return (FramedVertexTraversal) super.groupBy(map, new FramingTraversalFunction(keyFunction, graph(),
				GenericFramedVertex.class), new FramingTraversalFunction(valueFunction, graph(), GenericFramedVertex.class));
	}

	@Override
	public FramedVertexTraversal filter(TraversalFunction filterFunction) {
		return (FramedVertexTraversal) super.filter(new FramingTraversalFunction(filterFunction, graph(),
				GenericFramedVertex.class));

	}

	@Override
	public FramedVertexTraversal aggregate() {
		return (FramedVertexTraversal) super.aggregate(new FramingTraversalFunction(graph(), GenericFramedVertex.class));

	}

	@Override
	public FramedVertexTraversal aggregate(Collection aggregate) {
		return (FramedVertexTraversal) super.aggregate(aggregate,
				new FramingTraversalFunction(graph(), GenericFramedVertex.class));

	}

	@Override
	public FramedVertexTraversal aggregate(Collection aggregate, TraversalFunction aggregateFunction) {
		return (FramedVertexTraversal) super.aggregate(aggregate, new FramingTraversalFunction(aggregateFunction, graph(),
				GenericFramedVertex.class));

	}

	@Override
	public FramedVertexTraversal aggregate(TraversalFunction aggregateFunction) {
		return (FramedVertexTraversal) super.aggregate(new FramingTraversalFunction(aggregateFunction, graph(),
				GenericFramedVertex.class));

	}



	@Override
	public FramedVertexTraversal sideEffect(SideEffectFunction sideEffectFunction) {
		return (FramedVertexTraversal) super.sideEffect(new FramingSideEffectFunction<>(sideEffectFunction, graph(),
				GenericFramedVertex.class));
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
	public FramedVertexTraversal and(TraversalFunction... pipes) {
		Collection<Pipe> extractedPipes = Collections2.transform(Arrays.asList(pipes), new Function<TraversalFunction, Pipe>() {

			@Override
			public Pipe apply(TraversalFunction input) {
				return ((FramedTraversalImpl) input.compute(new GenericFramedVertex())).pipeline().get(0);
			}
		});
		pipeline().and(extractedPipes.toArray(new Pipe[extractedPipes.size()]));
		return this;
	}
	
	@Override
	public FramedVertexTraversal or(TraversalFunction... pipes) {
		Collection<Pipe> extractedPipes = Collections2.transform(Arrays.asList(pipes), new Function<TraversalFunction, Pipe>() {

			@Override
			public Pipe apply(TraversalFunction input) {
				return ((FramedTraversalImpl) input.compute(new GenericFramedVertex())).pipeline().get(0);
			}
		});
		pipeline().or(extractedPipes.toArray(new Pipe[extractedPipes.size()]));
		return this;
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
		return (FramedVertexTraversal) super.order(new FramingComparator(compareFunction, graph(), GenericFramedVertex.class));
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
		return asEdges();
	}

	@Override
	public FramedEdgeTraversal outE(String... labels) {
		pipeline().outE(labels);
		return asEdges();
	}

	@Override
	public FramedEdgeTraversal inE(int branchFactor, String... labels) {
		pipeline().inE(branchFactor, labels);
		return asEdges();
	}

	@Override
	public FramedEdgeTraversal inE(String... labels) {
		pipeline().inE(labels);
		return asEdges();
	}

	@Override
	public FramedEdgeTraversal bothE(int branchFactor, String... labels) {
		pipeline().bothE(branchFactor, labels);
		return asEdges();
	}

	@Override
	public FramedEdgeTraversal bothE(String... labels) {
		pipeline().bothE(labels);
		return asEdges();
	}

	@Override
	public FramedVertexTraversal interval(String key, Comparable startValue, Comparable endValue) {
		return (FramedVertexTraversal) super.interval(key, startValue, endValue);
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
		return super.fill(new FramingCollection<>(collection, graph(), GenericFramedVertex.class));
	}

	@Override
	public Collection fill(Collection collection, Class clazz) {

		return super.fill(new FramingCollection<>(collection, graph(), clazz));
	}

	@Override
	public Iterator iterator() {
		return Iterators.transform(pipeline(), new Function() {

			public Object apply(Object e) {
				return graph().frameElement((Element) e, GenericFramedVertex.class);
			}
		});
	}
	
	@Override
	public FramedVertexTraversal gatherScatter() {
		return (FramedVertexTraversal) super.gatherScatter();
	}
}

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
package com.syncleus.ferma;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.Tokens.T;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.transform.TransformPipe.Order;
import com.tinkerpop.pipes.util.structures.Table;
import com.tinkerpop.pipes.util.structures.Tree;

abstract class VertexTraversalImpl extends TraversalBase implements VertexTraversal {
	@Override
	public TVertex next() {
		return graph().frameElement((Vertex) pipeline().next(), TVertex.class);
	}

	@Override
	public TVertex nextOrAdd() {

		return (TVertex) nextOrAdd(TVertex.class);
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
	public VertexTraversal tree(Tree tree) {
		return (VertexTraversal) super.tree(tree);
	}

	@Override
	public VertexTraversal store() {
		return (VertexTraversal) super.store(new FramingTraversalFunction(graph(), TVertex.class));
	}

	@Override
	public VertexTraversal store(Collection storage) {
		return (VertexTraversal) super.store(storage);
	}

	@Override
	public VertexTraversal store(Collection storage, TraversalFunction storageFunction) {
		return (VertexTraversal) super.store(storage);
	}

	@Override
	public VertexTraversal store(TraversalFunction storageFunction) {
		return (VertexTraversal) super.store(storageFunction);
	}

	@Override
	public VertexTraversal groupCount() {
		return (VertexTraversal) super.groupCount();
	}

	@Override
	public VertexTraversal groupCount(Map map) {
		return (VertexTraversal) super.groupCount(map);
	}

	@Override
	public VertexTraversal groupCount(Map map, TraversalFunction keyFunction) {
		return (VertexTraversal) super.groupCount(map, keyFunction);
	}

	@Override
	public VertexTraversal groupCount(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return (VertexTraversal) super.groupCount(map, keyFunction, valueFunction);
	}

	@Override
	public VertexTraversal groupCount(TraversalFunction keyFunction) {
		return (VertexTraversal) super.groupCount(keyFunction);
	}

	@Override
	public VertexTraversal groupCount(TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return (VertexTraversal) super.groupCount(keyFunction, valueFunction);
	}

	@Override
	public VertexTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction,
			TraversalFunction reduceFunction) {
		return (VertexTraversal) super.groupBy(keyFunction, valueFunction, reduceFunction);
	}

	@Override
	public VertexTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction) {

		return (VertexTraversal) super.groupBy(keyFunction, valueFunction);
	}

	@Override
	public VertexTraversal groupBy(Map reduceMap, TraversalFunction keyFunction, TraversalFunction valueFunction,
			TraversalFunction reduceFunction) {
		return (VertexTraversal) super.groupBy(reduceMap, keyFunction, valueFunction, reduceFunction);
	}

	@Override
	public VertexTraversal groupBy(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return (VertexTraversal) super.groupBy(map, keyFunction, valueFunction);
	}

	@Override
	public VertexTraversal filter(TraversalFunction filterFunction) {
		return (VertexTraversal) super.filter(filterFunction);

	}

	@Override
	public VertexTraversal aggregate() {
		return (VertexTraversal) super.aggregate();

	}

	@Override
	public VertexTraversal aggregate(Collection aggregate) {
		return (VertexTraversal) super.aggregate(aggregate);

	}

	@Override
	public VertexTraversal aggregate(Collection aggregate, TraversalFunction aggregateFunction) {
		return (VertexTraversal) super.aggregate(aggregate);

	}

	@Override
	public VertexTraversal aggregate(TraversalFunction aggregateFunction) {
		return (VertexTraversal) super.aggregate(aggregateFunction);

	}

	@Override
	public VertexTraversal sideEffect(SideEffectFunction sideEffectFunction) {
		return (VertexTraversal) super.sideEffect(sideEffectFunction);
	}

	@Override
	public VertexTraversal random(double bias) {

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
	public VertexTraversal except(Iterable collection) {
		return (VertexTraversal) super.except(Lists.newArrayList(collection));
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
		return (VertexTraversal) super.order(compareFunction);
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
	public Object nextOrDefault(Class kind, Object defaultValue) {
		if (pipeline().hasNext()) {
			return next(kind);
		} else {
			return defaultValue;
		}
	}

	@Override
	public FramedVertex nextOrAdd(Class kind) {
		try {
			return (FramedVertex) graph().frameElement((Element) pipeline().next(), kind);
		} catch (NoSuchElementException e) {
			return graph().addVertex(kind);
		}

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
	public List toList() {
		return toList(TVertex.class);
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
		return super.fill(collection);
	}

	@Override
	public Collection fill(Collection collection, Class clazz) {

		return pipeline().fill(new FramingCollection<>(collection, graph(), clazz));
	}

	@Override
	public VertexTraversal gatherScatter() {
		return (VertexTraversal) super.gatherScatter();
	}

	@Override
	public VertexTraversal divert(SideEffectFunction sideEffect) {
		return (VertexTraversal) super.divert(sideEffect);
	}

	@Override
	public VertexTraversal retain(FramedVertex... vertices) {

		return (VertexTraversal) super.retain(Arrays.asList(vertices));
	}

	@Override
	public VertexTraversal except(FramedVertex... vertices) {

		return (VertexTraversal) super.except(Arrays.asList(vertices));
	}

	@Override
	public VertexTraversal shuffle() {

		return (VertexTraversal) super.shuffle();
	}

	@Override
	public VertexTraversal retain(Iterable vertices) {
		return (VertexTraversal) super.retain(Lists.newArrayList(vertices));
	}

	@Override
	public VertexTraversal mark() {

		return (VertexTraversal) super.mark();
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
						return ((TraversalBase) input.compute(new TVertex())).pipeline();
					}
				});
		pipeline().copySplit(extractedPipes.toArray(new Pipe[extractedPipes.size()]));
		return castToSplit();
	}

	@Override
	public VertexTraversal tree() {

		return (VertexTraversal) super.tree();
	}

	@Override
	public VertexTraversal loop(TraversalFunction input) {
		return (VertexTraversal) super.loop(input);
	}

	@Override
	public VertexTraversal loop(TraversalFunction input, int depth) {
		return (VertexTraversal) super.loop(input, depth);

	}

	@Override
	public VertexTraversal limit(int limit) {
		return (VertexTraversal) super.limit(limit);
	}
}

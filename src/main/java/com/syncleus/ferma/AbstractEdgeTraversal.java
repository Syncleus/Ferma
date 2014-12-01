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

/*
 * Part or all of this source file was forked from a third-party project, the details of which are listed below.
 *
 * Source Project: Totorom
 * Source URL: https://github.com/BrynCooke/totorom
 * Source License: Apache Public License v2.0
 * When: November, 20th 2014
 */
package com.syncleus.ferma;

import java.util.*;

import com.google.common.base.Function;
import com.google.common.collect.*;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.gremlin.Tokens.T;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.branch.LoopPipe;
import com.tinkerpop.pipes.transform.TransformPipe.Order;
import com.tinkerpop.pipes.util.structures.Table;
import com.tinkerpop.pipes.util.structures.Tree;

/**
 * Edge specific traversal.
 * 
 * @param <C> The cap of the current pipe.
 * @param <S> The SideEffect of the current pipe.
 * @param <M> The current mark'ed type for the current pipe.
 */
abstract class AbstractEdgeTraversal<C, S, M> extends AbstractTraversal<EdgeFrame, C, S, M> implements EdgeTraversal<C, S, M> {

	@Override
	public EdgeFrame next() {

		return graph().frameElement((Edge) pipeline().next(), EdgeFrame.class);
	}

	@Override
	public List<? extends EdgeFrame> toList() {
		return toListExplicit(EdgeFrame.class);
	}

	@Override
	public Set toSet() {
		return toSetExplicit(EdgeFrame.class);
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
	public Object next(Class kind) {
		return graph().frameElement((Element) pipeline().next(), kind);
	}

	@Override
	public Object nextExplicit(Class kind) {
		return graph().frameElementExplicit((Element) pipeline().next(), kind);
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
	public List nextExplicit(int amount, final Class kind) {
		return Lists.transform(pipeline().next(amount), new Function() {

			public Object apply(Object e) {
				return graph().frameElementExplicit((Element) e, kind);
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
	public Iterable frameExplicit(final Class kind) {
		return Iterables.transform(pipeline(), new Function() {

			public Object apply(Object e) {
				return graph().frameElementExplicit((Element) e, kind);
			}
		});
	}

	@Override
	public <T> List<? extends T> toList(final Class<T> kind) {
		return Lists.transform(pipeline().toList(), new Function() {

			public Object apply(Object e) {
				return graph().frameElement((Element) e, kind);
			}
		});
	}

	@Override
	public <T> List<? extends T> toListExplicit(final Class<T> kind) {
		return Lists.transform(pipeline().toList(), new Function() {

			public Object apply(Object e) {
				return graph().frameElementExplicit((Element) e, kind);
			}
		});
	}

	@Override
	public <T> Set<? extends T> toSet(Class<T> kind) {
		return Sets.newHashSet(toList(kind));
	}

	@Override
	public <T> Set<? extends T> toSetExplicit(Class<T> kind) {
		return Sets.newHashSet(toListExplicit(kind));
	}

	@Override
	public Traversal<String, ?, ?, M> label() {
		pipeline().label();
		return castToTraversal();
	}

	@Override
	public EdgeTraversal<?, ?, M> filter(TraversalFunction<EdgeFrame, Boolean> filterFunction) {
		return (EdgeTraversal<?, ?, M>) super.filter(filterFunction);

	}

	@Override
	public EdgeTraversal<Collection<? extends EdgeFrame>, Collection<? extends EdgeFrame>, M> aggregate() {
		return (EdgeTraversal<Collection<? extends EdgeFrame>, Collection<? extends EdgeFrame>, M>) super.aggregate();

	}

	@Override
	public EdgeTraversal<Collection<? extends EdgeFrame>, Collection<? extends EdgeFrame>, M> aggregate(Collection<? super EdgeFrame> aggregate) {
		return (EdgeTraversal<Collection<? extends EdgeFrame>, Collection<? extends EdgeFrame>, M>) super.aggregate(aggregate);

	}

	@Override
	public <N> EdgeTraversal<Collection<? extends N>, Collection<? extends N>, M> aggregate(Collection<? super N> aggregate, TraversalFunction<EdgeFrame, ? extends N> aggregateFunction) {
		return (EdgeTraversal<Collection<? extends N>, Collection<? extends N>, M>) super.aggregate(aggregate, aggregateFunction);

	}

	@Override
	public <N> EdgeTraversal<Collection<? extends N>, Collection<? extends N>, M> aggregate(TraversalFunction<EdgeFrame, ? extends N> aggregateFunction) {
		return (EdgeTraversal<Collection<? extends N>, Collection<? extends N>, M>) super.aggregate(aggregateFunction);

	}

	@Override
	public EdgeTraversal<?, ?, M> and(TraversalFunction<EdgeFrame, Traversal<?, ?, ?, ?>>... pipes) {
		Collection<Pipe> extractedPipes = Collections2.transform(Arrays.asList(pipes), new Function<TraversalFunction, Pipe>() {

			@Override
			public Pipe apply(TraversalFunction input) {
				return ((AbstractTraversal) input.compute(new TEdge())).pipeline();
			}
		});
		pipeline().and(extractedPipes.toArray(new Pipe[extractedPipes.size()]));
		return this;
	}

	@Override
	public EdgeTraversal<?, ?, M> or(TraversalFunction<EdgeFrame, Traversal<?, ?, ?, ?>>... pipes) {
		Collection<Pipe> extractedPipes = Collections2.transform(Arrays.asList(pipes), new Function<TraversalFunction, Pipe>() {

			@Override
			public Pipe apply(TraversalFunction input) {
				return ((AbstractTraversal) input.compute(new TEdge())).pipeline();
			}
		});
		pipeline().or(extractedPipes.toArray(new Pipe[extractedPipes.size()]));
		return this;
	}

	@Override
	public EdgeTraversal<?, ?, M> sideEffect(SideEffectFunction<EdgeFrame> sideEffectFunction) {
		return (EdgeTraversal<?, ?, M>) super.sideEffect(sideEffectFunction);
	}

	@Override
	public EdgeTraversal<?, ?, M> random(double bias) {

		return (EdgeTraversal<?, ?, M>) super.random(bias);
	}

	@Override
	public EdgeTraversal<?, ?, M> dedup(TraversalFunction<EdgeFrame, ?> dedupFunction) {
		return (EdgeTraversal<?, ?, M>) super.dedup(dedupFunction);
	}

	@Override
	public EdgeTraversal<?, ?, M> except(String... namedSteps) {

		return (EdgeTraversal<?, ?, M>) super.except(namedSteps);
	}

	@Override
	public EdgeTraversal<?, ?, M> except(Iterable<?> collection) {
		return (EdgeTraversal) super.except(Lists.newArrayList(collection));
	}

	@Override
	public EdgeTraversal<?, ?, M> range(int low, int high) {
		return (EdgeTraversal<?, ?, M>) super.range(low, high);
	}

	@Override
	public EdgeTraversal<?, ?, M> order() {
		return (EdgeTraversal<?, ?, M>) super.order();
	}

	@Override
	public EdgeTraversal<?, ?, M> order(Order order) {
		return (EdgeTraversal<?, ?, M>) super.order(order);
	}

	@Override
	public EdgeTraversal<?, ?, M> order(Comparator<EdgeFrame> compareFunction) {
		return (EdgeTraversal<?, ?, M>) super.order(compareFunction);
	}

	@Override
	public EdgeTraversal<?, ?, M> order(T order) {
		return (EdgeTraversal<?, ?, M>) super.order(order);
	}

	@Override
	public EdgeTraversal<?, ?, M> dedup() {

		return (EdgeTraversal<?, ?, M>) super.dedup();
	}

	@Override
	public EdgeTraversal<?, ?, M> retain(String... namedSteps) {
		return (EdgeTraversal<?, ?, M>) super.retain(namedSteps);
	}

	@Override
	public EdgeTraversal<?, ?, M> simplePath() {
		return (EdgeTraversal<?, ?, M>) super.simplePath();
	}

	@Override
	public EdgeTraversal<?, ?, M> memoize(String namedStep) {
		return (EdgeTraversal<?, ?, M>) super.memoize(namedStep);

	}

	@Override
	public EdgeTraversal<?, ?, M> memoize(String namedStep, Map<?,?> map) {
		return (EdgeTraversal<?, ?, M>) super.memoize(namedStep, map);

	}

	@Override
	public Collection<EdgeFrame> fill(Collection<? super EdgeFrame> collection) {
		return super.fill(collection);
	}

	@Override
	public <N> Collection<N> fill(Collection<? super N> collection, Class<N> kind) {

		return pipeline().fill(new FramingCollection(collection, graph(), kind));
	}

	@Override
	public <N> Collection<N> fillExplicit(Collection<? super N> collection, Class<N> kind) {

		return pipeline().fill(new FramingCollection(collection, graph(), kind, true));
	}

	public java.util.Iterator<EdgeFrame> iterator() {
		return Iterators.transform(pipeline(), new Function() {

			public Object apply(Object e) {
				return graph().frameElement((Element) e, EdgeFrame.class);
			}
		});
	};

	@Override
	public EdgeTraversal<?, ?, M> gatherScatter() {

		return (EdgeTraversal<?, ?, M>) super.gatherScatter();
	}

	@Override
	public EdgeTraversal<?, ?, M> divert(SideEffectFunction<S> sideEffect) {
		return (EdgeTraversal<?, ?, M>) super.divert(sideEffect);
	}

	@Override
	public EdgeTraversal<?, ?, M> retain(EdgeFrame... edges) {

		return (EdgeTraversal<?, ?, M>) super.retain(Arrays.asList(edges));
	}

	@Override
	public EdgeTraversal<?, ?, M> shuffle() {
		return (EdgeTraversal<?, ?, M>) super.shuffle();
	}

	@Override
	public EdgeTraversal<?, ?, M> except(EdgeFrame... edges) {
		return (EdgeTraversal<?, ?, M>) super.retain(Arrays.asList(edges));
	}

	@Override
	public EdgeTraversal<?, ?, M> retain(Iterable<?> collection) {

		return (EdgeTraversal<?, ?, M>) super.retain(Lists.newArrayList(collection));
	}

	@Override
	public EdgeTraversal<C, S, ? extends EdgeTraversal<C, S, M>> mark() {

		return (EdgeTraversal<C, S, ? extends EdgeTraversal<C, S, M>>) super.mark();
	}

	@Override
	public void removeAll() {
		pipeline().remove();
	}

	@Override
	public <N> SplitTraversal<? extends Traversal<N, ?, ?, M>> copySplit(TraversalFunction<EdgeFrame, ? extends Traversal<N, ?, ?, ?>>... traversals) {
		Collection<Pipe> extractedPipes = Collections2.transform(Arrays.asList(traversals),
				new Function<TraversalFunction, Pipe>() {

					@Override
					public Pipe apply(TraversalFunction input) {
						return ((AbstractTraversal) input.compute(new TEdge())).pipeline();
					}
				});
		pipeline().copySplit(extractedPipes.toArray(new Pipe[extractedPipes.size()]));
		return castToSplit();
	}

	@Override
	public EdgeTraversal<Tree<EdgeFrame>, Tree<EdgeFrame>, M> tree() {

		return (EdgeTraversal<Tree<EdgeFrame>, Tree<EdgeFrame>, M>) super.tree();
	}
	
	@Override
	public EdgeTraversal<?, ?, M> loop(TraversalFunction<EdgeFrame, ? extends EdgeTraversal<?, ?, ?>> input) {
		GremlinPipeline pipeline = ((AbstractTraversal) input.compute(new TEdge())).pipeline();
		pipeline().add(new LoopPipe(pipeline, LoopPipe.createTrueFunction(), null));

		return this;
	}

	@Override
	public EdgeTraversal<?, ?, M> loop(TraversalFunction<EdgeFrame, ? extends EdgeTraversal<?, ?, ?>> input, int depth) {
		GremlinPipeline pipeline = ((AbstractTraversal) input.compute(new TEdge())).pipeline();
		pipeline().add(new LoopPipe(pipeline, LoopPipe.createLoopsFunction(depth), null));

		return this;
	}
	
	@Override
	public EdgeTraversal<?, ?, M> limit(int limit) {
		return (EdgeTraversal<?, ?, M>) super.limit(limit);
	}
}

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
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.Tokens.T;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.branch.LoopPipe;
import com.tinkerpop.pipes.transform.TransformPipe.Order;
import com.tinkerpop.pipes.util.structures.Pair;
import com.tinkerpop.pipes.util.structures.Table;
import com.tinkerpop.pipes.util.structures.Tree;

/**
 * Vertex specific traversal.
 *
 * @param <C> The cap of the current pipe.
 * @param <S> The SideEffect of the current pipe.
 * @param <M> The current mark'ed type for the current pipe.
 */
abstract class AbstractVertexTraversal<C, S, M> extends AbstractTraversal<VertexFrame, C, S, M> implements VertexTraversal<C, S, M> {
	@Override
	public VertexFrame next() {
		return graph().frameElement((Vertex) pipeline().next(), VertexFrame.class);
	}

	@Override
	public VertexFrame nextOrAdd() {

		return (VertexFrame) nextOrAdd(VertexFrame.class);
	}

	@Override
	public <N> Iterable<? extends N> frameExplicit(final Class<N> kind) {
		return Iterables.transform(pipeline(), new Function() {

			public Object apply(final Object e) {
				return graph().frameElementExplicit((Element) e, kind);
			}
		});
	}

	@Override
	public VertexTraversal<Table, Table, M> table() {
		return (VertexTraversal<Table, Table, M>) super.table();
	}

	@Override
	public VertexTraversal<Table, Table, M> table(final TraversalFunction... columnFunctions) {

		return (VertexTraversal<Table, Table, M>) super.table(columnFunctions);
	}

	@Override
	public VertexTraversal<Table, Table, M> table(final Table table) {

		return (VertexTraversal<Table, Table, M>) super.table(table);
	}

	@Override
	public VertexTraversal<Table, Table, M> table(final Table table, final Collection stepNames, final TraversalFunction... columnFunctions) {

		return (VertexTraversal<Table, Table, M>) super.table(table, stepNames, columnFunctions);
	}

	@Override
	public VertexTraversal<Table, Table, M> table(final Table table, final TraversalFunction<?, ?>... columnFunctions) {
		return (VertexTraversal) super.table(table, columnFunctions);
	}

	@Override
	public <N> VertexTraversal<Tree<N>, Tree<N>, M> tree(final Tree<N> tree) {
		return (VertexTraversal) super.tree(tree);
	}

	@Override
	public VertexTraversal<Collection<VertexFrame>, VertexFrame, M> store() {
		return (VertexTraversal<Collection<VertexFrame>, VertexFrame, M>) super.store(new FramingTraversalFunction(graph(), VertexFrame.class));
	}

	@Override
	public <N> VertexTraversal<Collection<N>, N, M> store(final Collection<N> storage) {
		return (VertexTraversal<Collection<N>, N, M>) super.store(storage);
	}

	@Override
	public <N> VertexTraversal<Collection<N>, N, M> store(final Collection<N> storage,
															 final TraversalFunction<VertexFrame, N> storageFunction) {
		return (VertexTraversal<Collection<N>, N, M>) super.store(storage);
	}

	@Override
	public <N> VertexTraversal<Collection<N>, N, M> store(final TraversalFunction<VertexFrame, N> storageFunction) {
		return (VertexTraversal) super.store(storageFunction);
	}

	@Override
	public VertexTraversal<Map<VertexFrame, Long>, Map<VertexFrame, Long>, M> groupCount() {
		return (VertexTraversal) super.groupCount();
	}

	@Override
	public VertexTraversal<Map<VertexFrame, Long>, Map<VertexFrame, Long>, M> groupCount(final Map<VertexFrame, Long> map) {
		return (VertexTraversal<Map<VertexFrame, Long>, Map<VertexFrame, Long>, M>) super.groupCount(map);
	}

	@Override
	public <K> VertexTraversal<Map<K, Long>, Map<K, Long>, M> groupCount(final Map<K, Long> map, final TraversalFunction<VertexFrame, K> keyFunction) {
		return (VertexTraversal<Map<K, Long>, Map<K, Long>, M>) super.groupCount(map, keyFunction);
	}

	@Override
	public <K> VertexTraversal<Map<K, Long>, Map<K, Long>, M> groupCount(final Map<K, Long> map, final TraversalFunction<VertexFrame, K> keyFunction, final TraversalFunction<Pair<VertexFrame, Long>, Long> valueFunction) {
		return (VertexTraversal<Map<K, Long>, Map<K, Long>, M>) super.groupCount(map, keyFunction, valueFunction);
	}

	@Override
	public <K> VertexTraversal<Map<K, Long>, Map<K, Long>, M> groupCount(final TraversalFunction<VertexFrame, K> keyFunction) {
		return (VertexTraversal<Map<K, Long>, Map<K, Long>, M>) super.groupCount(keyFunction);
	}

	@Override
	public <K> VertexTraversal<Map<K, Long>, Map<K, Long>, M> groupCount(final TraversalFunction<VertexFrame, K> keyFunction, final TraversalFunction<Pair<VertexFrame, Long>, Long> valueFunction) {
		return (VertexTraversal<Map<K, Long>, Map<K, Long>, M>) super.groupCount(keyFunction, valueFunction);
	}

	@Override
	public <K, V, V2> VertexTraversal<Map<K, V2>, Map<K, V2>, M> groupBy(final TraversalFunction<VertexFrame, K> keyFunction, final TraversalFunction<VertexFrame, Iterator<V>> valueFunction, final TraversalFunction<List<V>, V2> reduceFunction) {
		return (VertexTraversal<Map<K, V2>, Map<K, V2>, M>) super.groupBy(keyFunction, valueFunction, reduceFunction);
	}

	@Override
	public <K, V> VertexTraversal<Map<K, List<V>>, Map<K, List<V>>, M> groupBy(final TraversalFunction<VertexFrame, K> keyFunction, final TraversalFunction<VertexFrame, Iterator<V>> valueFunction) {

		return (VertexTraversal<Map<K, List<V>>, Map<K, List<V>>, M>) super.groupBy(keyFunction, valueFunction);
	}

	@Override
	public <K, V, V2> VertexTraversal<Map<K, V2>, Map<K, V2>, M> groupBy(final Map<K, V2> reduceMap, final TraversalFunction<VertexFrame, K> keyFunction, final TraversalFunction<VertexFrame, Iterator<V>> valueFunction, final TraversalFunction<List<V>, V2> reduceFunction) {
		return (VertexTraversal<Map<K, V2>, Map<K, V2>, M>) super.groupBy(reduceMap, keyFunction, valueFunction, reduceFunction);
	}

	@Override
	public <K, V> VertexTraversal<Map<K, List<V>>, Map<K, List<V>>, M> groupBy(final Map<K, List<V>> map, final TraversalFunction<VertexFrame, K> keyFunction, final TraversalFunction<VertexFrame, Iterator<V>> valueFunction) {
		return (VertexTraversal<Map<K, List<V>>, Map<K, List<V>>, M>) super.groupBy(map, keyFunction, valueFunction);
	}

	@Override
	public VertexTraversal<?, ?, M> filter(final TraversalFunction<VertexFrame, Boolean> filterFunction) {
		return (VertexTraversal<?, ?, M>) super.filter(filterFunction);

	}

	@Override
	public VertexTraversal<Collection<? extends VertexFrame>, Collection<? extends VertexFrame>, M> aggregate() {
		return (VertexTraversal<Collection<? extends VertexFrame>, Collection<? extends VertexFrame>, M>) super.aggregate();

	}

	@Override
	public VertexTraversal<Collection<? extends VertexFrame>, Collection<? extends VertexFrame>, M> aggregate(final Collection<? super VertexFrame> aggregate) {
		return (VertexTraversal<Collection<? extends VertexFrame>, Collection<? extends VertexFrame>, M>) super.aggregate(aggregate);

	}

	@Override
	public <N> VertexTraversal<Collection<? extends N>, Collection<? extends N>, M> aggregate(final Collection<? super N> aggregate, final TraversalFunction<VertexFrame, ? extends N> aggregateFunction) {
		return (VertexTraversal<Collection<? extends N>, Collection<? extends N>, M>) super.aggregate(aggregate, aggregateFunction);

	}

	@Override
	public <N> VertexTraversal<Collection<? extends N>, Collection<? extends N>, M> aggregate(final TraversalFunction<VertexFrame, ? extends N> aggregateFunction) {
		return (VertexTraversal<Collection<? extends N>, Collection<? extends N>, M>) super.aggregate(aggregateFunction);

	}

	@Override
	public VertexTraversal<?, ?, M> sideEffect(final SideEffectFunction<VertexFrame> sideEffectFunction) {
		return (VertexTraversal<?, ?, M>) super.sideEffect(sideEffectFunction);
	}

	@Override
	public VertexTraversal<?, ?, M> random(final double bias) {

		return (VertexTraversal<?, ?, M>) super.random(bias);
	}

	@Override
	public VertexTraversal<?, ?, M> dedup(final TraversalFunction<VertexFrame, ?> dedupFunction) {
		return (VertexTraversal) super.dedup(dedupFunction);
	}

	@Override
	public VertexTraversal<?, ?, M> except(final String... namedSteps) {

		return (VertexTraversal<?, ?, M>) super.except(namedSteps);
	}

	@Override
	public VertexTraversal<?, ?, M> except(final Iterable<?> collection) {
		return (VertexTraversal<?, ?, M>) super.except(Lists.newArrayList(collection));
	}

	@Override
	public VertexTraversal<?, ?, M> range(final int low, final int high) {
		return (VertexTraversal<?, ?, M>) super.range(low, high);
	}

	@Override
	public VertexTraversal<?, ?, M> and(final TraversalFunction<VertexFrame, Traversal<?, ?, ?, ?>>... pipes) {
		final Collection<Pipe> extractedPipes = Collections2.transform(Arrays.asList(pipes), new Function<TraversalFunction, Pipe>() {

			@Override
			public Pipe apply(final TraversalFunction input) {
				return ((AbstractTraversal) input.compute(new TVertex())).pipeline();
			}
		});
		pipeline().and(extractedPipes.toArray(new Pipe[extractedPipes.size()]));
		return this;
	}

	@Override
	public VertexTraversal<?, ?, M> or(final TraversalFunction<VertexFrame, Traversal<?, ?, ?, ?>>... pipes) {
		final Collection<Pipe> extractedPipes = Collections2.transform(Arrays.asList(pipes), new Function<TraversalFunction, Pipe>() {

			@Override
			public Pipe apply(final TraversalFunction input) {
				return ((AbstractTraversal) input.compute(new TVertex())).pipeline();
			}
		});
		pipeline().or(extractedPipes.toArray(new Pipe[extractedPipes.size()]));
		return this;
	}

	@Override
	public VertexTraversal<?, ?, M> order() {
		return (VertexTraversal<?, ?, M>) super.order();
	}

	@Override
	public VertexTraversal<?, ?, M> order(final Order order) {
		return (VertexTraversal<?, ?, M>) super.order(order);
	}

	@Override
	public VertexTraversal<?, ?, M> order(final Comparator<VertexFrame> compareFunction) {
		return (VertexTraversal<?, ?, M>) super.order(compareFunction);
	}

	@Override
	public VertexTraversal<?, ?, M> order(final T order) {
		return (VertexTraversal<?, ?, M>) super.order(order);
	}

	@Override
	public VertexTraversal<?, ?, M> dedup() {

		return (VertexTraversal<?, ?, M>) super.dedup();
	}

	@Override
	public VertexTraversal<?, ?, M> retain(final String... namedSteps) {
		return (VertexTraversal<?, ?, M>) super.retain(namedSteps);
	}

	@Override
	public VertexTraversal<?, ?, M> simplePath() {
		return (VertexTraversal<?, ?, M>) super.simplePath();
	}

	@Override
	public VertexTraversal<?, ?, M> memoize(final String namedStep) {
		return (VertexTraversal<?, ?, M>) super.memoize(namedStep);

	}

	@Override
	public VertexTraversal<?, ?, M> memoize(final String namedStep, final Map<?,?> map) {
		return (VertexTraversal<?, ?, M>) super.memoize(namedStep, map);

	}

	@Override
	public VertexTraversal<?, ?, M> out(final int branchFactor, final String... labels) {
		pipeline().out(branchFactor, labels);
		return this;
	}

	@Override
	public VertexTraversal<?, ?, M> out(final String... labels) {
		pipeline().out(labels);
		return this;
	}

	@Override
	public VertexTraversal<?, ?, M> in(final int branchFactor, final String... labels) {
		pipeline().in(branchFactor, labels);
		return this;
	}

	@Override
	public VertexTraversal<?, ?, M> in(final String... labels) {
		pipeline().in(labels);
		return this;
	}

	@Override
	public VertexTraversal<?, ?, M> both(final int branchFactor, final String... labels) {
		pipeline().both(branchFactor, labels);
		return this;
	}

	@Override
	public VertexTraversal<?, ?, M> both(final String... labels) {
		pipeline().both(labels);
		return this;
	}

	@Override
	public EdgeTraversal<?, ?, M> outE(final int branchFactor, final String... labels) {
		pipeline().outE(branchFactor, labels);
		return castToEdges();
	}

	@Override
	public EdgeTraversal<?, ?, M> outE(final String... labels) {
		pipeline().outE(labels);
		return castToEdges();
	}

	@Override
	public EdgeTraversal<?, ?, M> inE(final int branchFactor, final String... labels) {
		pipeline().inE(branchFactor, labels);
		return castToEdges();
	}

	@Override
	public EdgeTraversal<?, ?, M> inE(final String... labels) {
		pipeline().inE(labels);
		return castToEdges();
	}

	@Override
	public EdgeTraversal<?, ?, M> bothE(final int branchFactor, final String... labels) {
		pipeline().bothE(branchFactor, labels);
		return castToEdges();
	}

	@Override
	public EdgeTraversal<?, ?, M> bothE(final String... labels) {
		pipeline().bothE(labels);
		return castToEdges();
	}

	@Override
	public <C> VertexTraversal<?, ?, M> interval(final String key, final Comparable<C> startValue, final Comparable<C> endValue) {
		return (VertexTraversal<?, ?, M>) super.interval(key, startValue, endValue);
	}

	@Override
	public <N> N next(final Class<N> kind) {
		return graph().frameElement((Element) pipeline().next(), kind);
	}

	@Override
	public <N> N nextExplicit(final Class<N> kind) {
		return graph().frameElementExplicit((Element) pipeline().next(), kind);
	}

	@Override
	public <N> N nextOrDefault(final Class<N> kind, final N defaultValue) {
		if (pipeline().hasNext()) {
			return next(kind);
		} else {
			return defaultValue;
		}
	}

	@Override
	public <N> N nextOrDefaultExplicit(final Class<N> kind, final N defaultValue) {
		if (pipeline().hasNext()) {
			return nextExplicit(kind);
		} else {
			return defaultValue;
		}
	}

	@Override
	public <N> N nextOrAdd(final Class<N> kind) {
		try {
			return graph().frameElement((Element) pipeline().next(), kind);
		} catch (final NoSuchElementException e) {
			return graph().addFramedVertex(kind);
		}

	}

	@Override
	public <N> N nextOrAddExplicit(final Class<N> kind) {
		try {
			return graph().frameElementExplicit((Element) pipeline().next(), kind);
		} catch (final NoSuchElementException e) {
			return graph().addFramedVertex(kind);
		}

	}

	@Override
	public <N> List<N> next(final int amount, final Class<N> kind) {
		return Lists.transform(pipeline().next(amount), new Function() {

			public Object apply(final Object e) {
				return graph().frameElement((Element) e, kind);
			}
		});
	}

	@Override
	public <N> List<N> nextExplicit(final int amount, final Class<N> kind) {
		return Lists.transform(pipeline().next(amount), new Function() {

			public Object apply(final Object e) {
				return graph().frameElementExplicit((Element) e, kind);
			}
		});
	}

	@Override
	public <N> Iterable<N> frame(final Class<N> kind) {
		return Iterables.transform(pipeline(), new Function() {

			public Object apply(final Object e) {
				return graph().frameElement((Element) e, kind);
			}
		});
	}

	@Override
	public <N> List<? extends N> toList(final Class<N> kind) {
		return Lists.transform(pipeline().toList(), new Function() {

			public Object apply(final Object e) {
				return graph().frameElement((Element) e, kind);
			}
		});
	}

	@Override
	public <N> List<? extends N> toListExplicit(final Class<N> kind) {
		return Lists.transform(pipeline().toList(), new Function() {

			public Object apply(final Object e) {
				return graph().frameElementExplicit((Element) e, kind);
			}
		});
	}

	@Override
	public <N> Set<? extends N> toSet(final Class<N> kind) {
		return Sets.newHashSet(toList(kind));
	}

	@Override
	public <N> Set<? extends N> toSetExplicit(final Class<N> kind) {
		return Sets.newHashSet(toListExplicit(kind));
	}

	@Override
	public List<? extends VertexFrame> toList() {
		return toListExplicit(VertexFrame.class);
	}

	@Override
	public Set<? extends VertexFrame> toSet() {
		return toSetExplicit(VertexFrame.class);
	}

	@Override
	public VertexTraversal<?, ?, M> has(final String key) {
		return (VertexTraversal<?, ?, M>) super.has(key);
	}

	@Override
	public VertexTraversal has(final String key, final Object value) {
		return (VertexTraversal) super.has(key, value);
	}

	@Override
	public VertexTraversal<?, ?, M> has(final String key, final Predicate predicate, final Object value) {
		return (VertexTraversal<?, ?, M>) super.has(key, predicate, value);
	}

	@Override
	public VertexTraversal<?, ?, M> has(final String key, final com.tinkerpop.gremlin.Tokens.T compareToken, final Object value) {
		return (VertexTraversal<?, ?, M>) super.has(key, compareToken, value);
	}

	@Override
	public VertexTraversal<?, ?, M> hasNot(final String key) {
		return (VertexTraversal<?, ?, M>) super.hasNot(key);
	}

	@Override
	public VertexTraversal<?, ?, M> hasNot(final String key, final Object value) {
		return (VertexTraversal<?, ?, M>) super.hasNot(key, value);
	}

	@Override
	public VertexTraversal<?, ?, M> as(final String name) {
		return (VertexTraversal<?, ?, M>) super.as(name);
	}

	@Override
	public VertexTraversal<?, ?, M> identity() {
		return (VertexTraversal<?, ?, M>) super.identity();
	}

	@Override
	public VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkOut(final String label, final String namedStep) {
		pipeline().linkOut(label, namedStep);
		return (VertexTraversal<List<EdgeFrame>, EdgeFrame, M>) this;
	}

	@Override
	public VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkIn(final String label, final String namedStep) {
		pipeline().linkIn(label, namedStep);
		return (VertexTraversal<List<EdgeFrame>, EdgeFrame, M>) this;
	}

	@Override
	public VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkBoth(final String label, final String namedStep) {
		pipeline().linkBoth(label, namedStep);
		return (VertexTraversal<List<EdgeFrame>, EdgeFrame, M>) this;
	}

	@Override
	public VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkOut(final String label, final Vertex other) {
		pipeline().linkOut(label, other);
		return (VertexTraversal<List<EdgeFrame>, EdgeFrame, M>) this;
	}

	@Override
	public VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkIn(final String label, final Vertex other) {
		pipeline().linkIn(label, other);
		return (VertexTraversal<List<EdgeFrame>, EdgeFrame, M>) this;
	}

	@Override
	public VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkBoth(final String label, final Vertex other) {
		pipeline().linkBoth(label, other);
		return (VertexTraversal<List<EdgeFrame>, EdgeFrame, M>) this;
	}

	@Override
	public VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkOut(final String label, final VertexFrame other) {
		pipeline().linkOut(label, other.element());
		return (VertexTraversal<List<EdgeFrame>, EdgeFrame, M>) this;
	}

	@Override
	public VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkIn(final String label, final VertexFrame other) {
		pipeline().linkIn(label, other.element());
		return (VertexTraversal<List<EdgeFrame>, EdgeFrame, M>) this;
	}

	@Override
	public VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkBoth(final String label, final VertexFrame other) {
		pipeline().linkBoth(label, other.element());
		return (VertexTraversal<List<EdgeFrame>, EdgeFrame, M>) this;
	}

	@Override
	public <N extends VertexFrame> Collection<N> fill(final Collection<? super N> collection, final Class<N> kind) {

		return pipeline().fill(new FramingCollection<>(collection, graph(), kind));
	}

	@Override
	public <N extends VertexFrame> Collection<N> fillExplicit(final Collection<? super N> collection, final Class<N> kind) {

		return pipeline().fill(new FramingCollection<>(collection, graph(), kind, true));
	}

	@Override
	public VertexTraversal<?, ?, M> gatherScatter() {
		return (VertexTraversal<?, ?, M>) super.gatherScatter();
	}

	@Override
	public VertexTraversal<?, ?, M> divert(final SideEffectFunction<S> sideEffectFunction) {
		return (VertexTraversal<?, ?, M>) super.divert(sideEffectFunction);
	}

	@Override
	public VertexTraversal<?, ?, M> retain(final VertexFrame... vertices) {

		return (VertexTraversal<?, ?, M>) super.retain(Arrays.asList(vertices));
	}

	@Override
	public VertexTraversal<?, ?, M> except(final VertexFrame... vertices) {

		return (VertexTraversal<?, ?, M>) super.except(Arrays.asList(vertices));
	}

	@Override
	public VertexTraversal<?, ?, M> shuffle() {

		return (VertexTraversal<?, ?, M>) super.shuffle();
	}

	@Override
	public VertexTraversal<?, ?, M> retain(final Iterable<?> vertices) {
		return (VertexTraversal<?, ?, M>) super.retain(Lists.newArrayList(vertices));
	}

	@Override
	public VertexTraversal<C, S, ? extends VertexTraversal<C, S, M>> mark() {

		return (VertexTraversal<C, S, VertexTraversal<C, S, M>>) super.mark();
	}

	@Override
	public void removeAll() {
		pipeline().remove();
	}

	@Override
	public <N> SplitTraversal<? extends Traversal<N, ?, ?, M>> copySplit(final TraversalFunction<VertexFrame, ? extends Traversal<N, ?, ?, ?>>... traversals) {
		final Collection<Pipe> extractedPipes = Collections2.transform(Arrays.asList(traversals),
				new Function<TraversalFunction, Pipe>() {

					@Override
					public Pipe apply(final TraversalFunction input) {
						return ((AbstractTraversal) input.compute(new TVertex())).pipeline();
					}
				});
		pipeline().copySplit(extractedPipes.toArray(new Pipe[extractedPipes.size()]));
		return castToSplit();
	}

	@Override
	public VertexTraversal<Tree<VertexFrame>, Tree<VertexFrame>, M> tree() {

		return (VertexTraversal<Tree<VertexFrame>, Tree<VertexFrame>, M>) super.tree();
	}

	@Override
	public VertexTraversal<?, ?, M> loop(final TraversalFunction<VertexFrame, ? extends VertexTraversal<?, ?, ?>> input) {
		final GremlinPipeline pipeline = ((AbstractTraversal) input.compute(new TVertex())).pipeline();
		pipeline().add(new LoopPipe(pipeline, LoopPipe.createTrueFunction(), null));

		return this;
	}

	@Override
	public VertexTraversal<?, ?, M> loop(final TraversalFunction<VertexFrame, ? extends VertexTraversal<?, ?, ?>> input, final int depth) {
		final GremlinPipeline pipeline = ((AbstractTraversal) input.compute(new TVertex())).pipeline();
		pipeline().add(new LoopPipe(pipeline, LoopPipe.createLoopsFunction(depth), null));

		return this;
	}

	@Override
	public VertexTraversal<?, ?, M> limit(final int limit) {
		return (VertexTraversal) super.limit(limit);
	}
}

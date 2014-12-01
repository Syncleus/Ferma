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

			public Object apply(Object e) {
				return graph().frameElementExplicit((Element) e, kind);
			}
		});
	}

	@Override
	public VertexTraversal<Table, Table, M> table() {
		return (VertexTraversal<Table, Table, M>) super.table();
	}

	@Override
	public VertexTraversal<Table, Table, M> table(TraversalFunction... columnFunctions) {

		return (VertexTraversal<Table, Table, M>) super.table(columnFunctions);
	}

	@Override
	public VertexTraversal<Table, Table, M> table(Table table) {

		return (VertexTraversal<Table, Table, M>) super.table(table);
	}

	@Override
	public VertexTraversal<Table, Table, M> table(Table table, Collection stepNames, TraversalFunction... columnFunctions) {

		return (VertexTraversal<Table, Table, M>) super.table(table, stepNames, columnFunctions);
	}

	@Override
	public VertexTraversal<Table, Table, M> table(Table table, TraversalFunction<?, ?>... columnFunctions) {
		return (VertexTraversal) super.table(table, columnFunctions);
	}

	@Override
	public <N> VertexTraversal<Tree<N>, Tree<N>, M> tree(Tree<N> tree) {
		return (VertexTraversal) super.tree(tree);
	}

	@Override
	public VertexTraversal<Collection<VertexFrame>, VertexFrame, M> store() {
		return (VertexTraversal<Collection<VertexFrame>, VertexFrame, M>) super.store(new FramingTraversalFunction(graph(), VertexFrame.class));
	}

	@Override
	public <N> VertexTraversal<Collection<N>, N, M> store(Collection<N> storage) {
		return (VertexTraversal<Collection<N>, N, M>) super.store(storage);
	}

	@Override
	public <N> VertexTraversal<Collection<N>, N, M> store(Collection<N> storage,
															 TraversalFunction<VertexFrame, N> storageFunction) {
		return (VertexTraversal<Collection<N>, N, M>) super.store(storage);
	}

	@Override
	public <N> VertexTraversal<Collection<N>, N, M> store(TraversalFunction<VertexFrame, N> storageFunction) {
		return (VertexTraversal) super.store(storageFunction);
	}

	@Override
	public VertexTraversal<Map<VertexFrame, Long>, Map<VertexFrame, Long>, M> groupCount() {
		return (VertexTraversal) super.groupCount();
	}

	@Override
	public VertexTraversal<Map<VertexFrame, Long>, Map<VertexFrame, Long>, M> groupCount(Map<VertexFrame, Long> map) {
		return (VertexTraversal<Map<VertexFrame, Long>, Map<VertexFrame, Long>, M>) super.groupCount(map);
	}

	@Override
	public <K> VertexTraversal<Map<K, Long>, Map<K, Long>, M> groupCount(Map<K, Long> map, TraversalFunction<VertexFrame, K> keyFunction) {
		return (VertexTraversal<Map<K, Long>, Map<K, Long>, M>) super.groupCount(map, keyFunction);
	}

	@Override
	public <K> VertexTraversal<Map<K, Long>, Map<K, Long>, M> groupCount(Map<K, Long> map, TraversalFunction<VertexFrame, K> keyFunction, TraversalFunction<Pair<VertexFrame, Long>, Long> valueFunction) {
		return (VertexTraversal<Map<K, Long>, Map<K, Long>, M>) super.groupCount(map, keyFunction, valueFunction);
	}

	@Override
	public <K> VertexTraversal<Map<K, Long>, Map<K, Long>, M> groupCount(TraversalFunction<VertexFrame, K> keyFunction) {
		return (VertexTraversal<Map<K, Long>, Map<K, Long>, M>) super.groupCount(keyFunction);
	}

	@Override
	public <K> VertexTraversal<Map<K, Long>, Map<K, Long>, M> groupCount(TraversalFunction<VertexFrame, K> keyFunction, TraversalFunction<Pair<VertexFrame, Long>, Long> valueFunction) {
		return (VertexTraversal<Map<K, Long>, Map<K, Long>, M>) super.groupCount(keyFunction, valueFunction);
	}

	@Override
	public <K, V, V2> VertexTraversal<Map<K, V2>, Map<K, V2>, M> groupBy(TraversalFunction<VertexFrame, K> keyFunction, TraversalFunction<VertexFrame, Iterator<V>> valueFunction, TraversalFunction<List<V>, V2> reduceFunction) {
		return (VertexTraversal<Map<K, V2>, Map<K, V2>, M>) super.groupBy(keyFunction, valueFunction, reduceFunction);
	}

	@Override
	public <K, V> VertexTraversal<Map<K, List<V>>, Map<K, List<V>>, M> groupBy(TraversalFunction<VertexFrame, K> keyFunction, TraversalFunction<VertexFrame, Iterator<V>> valueFunction) {

		return (VertexTraversal<Map<K, List<V>>, Map<K, List<V>>, M>) super.groupBy(keyFunction, valueFunction);
	}

	@Override
	public <K, V, V2> VertexTraversal<Map<K, V2>, Map<K, V2>, M> groupBy(Map<K, V2> reduceMap, TraversalFunction<VertexFrame, K> keyFunction, TraversalFunction<VertexFrame, Iterator<V>> valueFunction, TraversalFunction<List<V>, V2> reduceFunction) {
		return (VertexTraversal<Map<K, V2>, Map<K, V2>, M>) super.groupBy(reduceMap, keyFunction, valueFunction, reduceFunction);
	}

	@Override
	public <K, V> VertexTraversal<Map<K, List<V>>, Map<K, List<V>>, M> groupBy(Map<K, List<V>> map, TraversalFunction<VertexFrame, K> keyFunction, TraversalFunction<VertexFrame, Iterator<V>> valueFunction) {
		return (VertexTraversal<Map<K, List<V>>, Map<K, List<V>>, M>) super.groupBy(map, keyFunction, valueFunction);
	}

	@Override
	public VertexTraversal<?, ?, M> filter(TraversalFunction<VertexFrame, Boolean> filterFunction) {
		return (VertexTraversal<?, ?, M>) super.filter(filterFunction);

	}

	@Override
	public VertexTraversal<Collection<? extends VertexFrame>, Collection<? extends VertexFrame>, M> aggregate() {
		return (VertexTraversal<Collection<? extends VertexFrame>, Collection<? extends VertexFrame>, M>) super.aggregate();

	}

	@Override
	public VertexTraversal<Collection<? extends VertexFrame>, Collection<? extends VertexFrame>, M> aggregate(Collection<? super VertexFrame> aggregate) {
		return (VertexTraversal<Collection<? extends VertexFrame>, Collection<? extends VertexFrame>, M>) super.aggregate(aggregate);

	}

	@Override
	public <N> VertexTraversal<Collection<? extends N>, Collection<? extends N>, M> aggregate(Collection<? super N> aggregate, TraversalFunction<VertexFrame, ? extends N> aggregateFunction) {
		return (VertexTraversal<Collection<? extends N>, Collection<? extends N>, M>) super.aggregate(aggregate, aggregateFunction);

	}

	@Override
	public <N> VertexTraversal<Collection<? extends N>, Collection<? extends N>, M> aggregate(TraversalFunction<VertexFrame, ? extends N> aggregateFunction) {
		return (VertexTraversal<Collection<? extends N>, Collection<? extends N>, M>) super.aggregate(aggregateFunction);

	}

	@Override
	public VertexTraversal<?, ?, M> sideEffect(SideEffectFunction<VertexFrame> sideEffectFunction) {
		return (VertexTraversal<?, ?, M>) super.sideEffect(sideEffectFunction);
	}

	@Override
	public VertexTraversal<?, ?, M> random(double bias) {

		return (VertexTraversal<?, ?, M>) super.random(bias);
	}

	@Override
	public VertexTraversal<?, ?, M> dedup(TraversalFunction<VertexFrame, ?> dedupFunction) {
		return (VertexTraversal) super.dedup(dedupFunction);
	}

	@Override
	public VertexTraversal<?, ?, M> except(String... namedSteps) {

		return (VertexTraversal<?, ?, M>) super.except(namedSteps);
	}

	@Override
	public VertexTraversal<?, ?, M> except(Iterable<?> collection) {
		return (VertexTraversal<?, ?, M>) super.except(Lists.newArrayList(collection));
	}

	@Override
	public VertexTraversal<?, ?, M> range(int low, int high) {
		return (VertexTraversal<?, ?, M>) super.range(low, high);
	}

	@Override
	public VertexTraversal<?, ?, M> and(TraversalFunction<VertexFrame, Traversal<?, ?, ?, ?>>... pipes) {
		Collection<Pipe> extractedPipes = Collections2.transform(Arrays.asList(pipes), new Function<TraversalFunction, Pipe>() {

			@Override
			public Pipe apply(TraversalFunction input) {
				return ((AbstractTraversal) input.compute(new TVertex())).pipeline();
			}
		});
		pipeline().and(extractedPipes.toArray(new Pipe[extractedPipes.size()]));
		return this;
	}

	@Override
	public VertexTraversal<?, ?, M> or(TraversalFunction<VertexFrame, Traversal<?, ?, ?, ?>>... pipes) {
		Collection<Pipe> extractedPipes = Collections2.transform(Arrays.asList(pipes), new Function<TraversalFunction, Pipe>() {

			@Override
			public Pipe apply(TraversalFunction input) {
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
	public VertexTraversal<?, ?, M> order(Order order) {
		return (VertexTraversal<?, ?, M>) super.order(order);
	}

	@Override
	public VertexTraversal<?, ?, M> order(Comparator<VertexFrame> compareFunction) {
		return (VertexTraversal<?, ?, M>) super.order(compareFunction);
	}

	@Override
	public VertexTraversal<?, ?, M> order(T order) {
		return (VertexTraversal<?, ?, M>) super.order(order);
	}

	@Override
	public VertexTraversal<?, ?, M> dedup() {

		return (VertexTraversal<?, ?, M>) super.dedup();
	}

	@Override
	public VertexTraversal<?, ?, M> retain(String... namedSteps) {
		return (VertexTraversal<?, ?, M>) super.retain(namedSteps);
	}

	@Override
	public VertexTraversal<?, ?, M> simplePath() {
		return (VertexTraversal<?, ?, M>) super.simplePath();
	}

	@Override
	public VertexTraversal<?, ?, M> memoize(String namedStep) {
		return (VertexTraversal<?, ?, M>) super.memoize(namedStep);

	}

	@Override
	public VertexTraversal<?, ?, M> memoize(String namedStep, Map<?,?> map) {
		return (VertexTraversal<?, ?, M>) super.memoize(namedStep, map);

	}

	@Override
	public VertexTraversal<?, ?, M> out(int branchFactor, String... labels) {
		pipeline().out(branchFactor, labels);
		return this;
	}

	@Override
	public VertexTraversal<?, ?, M> out(String... labels) {
		pipeline().out(labels);
		return this;
	}

	@Override
	public VertexTraversal<?, ?, M> in(int branchFactor, String... labels) {
		pipeline().in(branchFactor, labels);
		return this;
	}

	@Override
	public VertexTraversal<?, ?, M> in(String... labels) {
		pipeline().in(labels);
		return this;
	}

	@Override
	public VertexTraversal<?, ?, M> both(int branchFactor, String... labels) {
		pipeline().both(branchFactor, labels);
		return this;
	}

	@Override
	public VertexTraversal<?, ?, M> both(String... labels) {
		pipeline().both(labels);
		return this;
	}

	@Override
	public EdgeTraversal<?, ?, M> outE(int branchFactor, String... labels) {
		pipeline().outE(branchFactor, labels);
		return castToEdges();
	}

	@Override
	public EdgeTraversal<?, ?, M> outE(String... labels) {
		pipeline().outE(labels);
		return castToEdges();
	}

	@Override
	public EdgeTraversal<?, ?, M> inE(int branchFactor, String... labels) {
		pipeline().inE(branchFactor, labels);
		return castToEdges();
	}

	@Override
	public EdgeTraversal<?, ?, M> inE(String... labels) {
		pipeline().inE(labels);
		return castToEdges();
	}

	@Override
	public EdgeTraversal<?, ?, M> bothE(int branchFactor, String... labels) {
		pipeline().bothE(branchFactor, labels);
		return castToEdges();
	}

	@Override
	public EdgeTraversal<?, ?, M> bothE(String... labels) {
		pipeline().bothE(labels);
		return castToEdges();
	}

	@Override
	public <C> VertexTraversal<?, ?, M> interval(String key, Comparable<C> startValue, Comparable<C> endValue) {
		return (VertexTraversal<?, ?, M>) super.interval(key, startValue, endValue);
	}

	@Override
	public <N> N next(Class<N> kind) {
		return graph().frameElement((Element) pipeline().next(), kind);
	}

	@Override
	public <N> N nextExplicit(Class<N> kind) {
		return graph().frameElementExplicit((Element) pipeline().next(), kind);
	}

	@Override
	public <N> N nextOrDefault(Class<N> kind, N defaultValue) {
		if (pipeline().hasNext()) {
			return next(kind);
		} else {
			return defaultValue;
		}
	}

	@Override
	public <N> N nextOrDefaultExplicit(Class<N> kind, N defaultValue) {
		if (pipeline().hasNext()) {
			return nextExplicit(kind);
		} else {
			return defaultValue;
		}
	}

	@Override
	public <N> N nextOrAdd(Class<N> kind) {
		try {
			return graph().frameElement((Element) pipeline().next(), kind);
		} catch (NoSuchElementException e) {
			return graph().addFramedVertex(kind);
		}

	}

	@Override
	public <N> N nextOrAddExplicit(Class<N> kind) {
		try {
			return graph().frameElementExplicit((Element) pipeline().next(), kind);
		} catch (NoSuchElementException e) {
			return graph().addFramedVertex(kind);
		}

	}

	@Override
	public <N> List<N> next(int amount, final Class<N> kind) {
		return Lists.transform(pipeline().next(amount), new Function() {

			public Object apply(Object e) {
				return graph().frameElement((Element) e, kind);
			}
		});
	}

	@Override
	public <N> List<N> nextExplicit(int amount, final Class<N> kind) {
		return Lists.transform(pipeline().next(amount), new Function() {

			public Object apply(Object e) {
				return graph().frameElementExplicit((Element) e, kind);
			}
		});
	}

	@Override
	public <N> Iterable<N> frame(final Class<N> kind) {
		return Iterables.transform(pipeline(), new Function() {

			public Object apply(Object e) {
				return graph().frameElement((Element) e, kind);
			}
		});
	}

	@Override
	public <N> List<? extends N> toList(final Class<N> kind) {
		return Lists.transform(pipeline().toList(), new Function() {

			public Object apply(Object e) {
				return graph().frameElement((Element) e, kind);
			}
		});
	}

	@Override
	public <N> List<? extends N> toListExplicit(final Class<N> kind) {
		return Lists.transform(pipeline().toList(), new Function() {

			public Object apply(Object e) {
				return graph().frameElementExplicit((Element) e, kind);
			}
		});
	}

	@Override
	public <N> Set<? extends N> toSet(Class<N> kind) {
		return Sets.newHashSet(toList(kind));
	}

	@Override
	public <N> Set<? extends N> toSetExplicit(Class<N> kind) {
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
	public VertexTraversal<?, ?, M> has(String key) {
		return (VertexTraversal<?, ?, M>) super.has(key);
	}

	@Override
	public VertexTraversal has(String key, Object value) {
		return (VertexTraversal) super.has(key, value);
	}

	@Override
	public VertexTraversal<?, ?, M> has(String key, Predicate predicate, Object value) {
		return (VertexTraversal<?, ?, M>) super.has(key, predicate, value);
	}

	@Override
	public VertexTraversal<?, ?, M> has(String key, com.tinkerpop.gremlin.Tokens.T compareToken, Object value) {
		return (VertexTraversal<?, ?, M>) super.has(key, compareToken, value);
	}

	@Override
	public VertexTraversal<?, ?, M> hasNot(String key) {
		return (VertexTraversal<?, ?, M>) super.hasNot(key);
	}

	@Override
	public VertexTraversal<?, ?, M> hasNot(String key, Object value) {
		return (VertexTraversal<?, ?, M>) super.hasNot(key, value);
	}

	@Override
	public VertexTraversal<?, ?, M> as(String name) {
		return (VertexTraversal<?, ?, M>) super.as(name);
	}

	@Override
	public VertexTraversal<?, ?, M> identity() {
		return (VertexTraversal<?, ?, M>) super.identity();
	}

	@Override
	public VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkOut(String label, String namedStep) {
		pipeline().linkOut(label, namedStep);
		return (VertexTraversal<List<EdgeFrame>, EdgeFrame, M>) this;
	}

	@Override
	public VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkIn(String label, String namedStep) {
		pipeline().linkIn(label, namedStep);
		return (VertexTraversal<List<EdgeFrame>, EdgeFrame, M>) this;
	}

	@Override
	public VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkBoth(String label, String namedStep) {
		pipeline().linkBoth(label, namedStep);
		return (VertexTraversal<List<EdgeFrame>, EdgeFrame, M>) this;
	}

	@Override
	public VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkOut(String label, Vertex other) {
		pipeline().linkOut(label, other);
		return (VertexTraversal<List<EdgeFrame>, EdgeFrame, M>) this;
	}

	@Override
	public VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkIn(String label, Vertex other) {
		pipeline().linkIn(label, other);
		return (VertexTraversal<List<EdgeFrame>, EdgeFrame, M>) this;
	}

	@Override
	public VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkBoth(String label, Vertex other) {
		pipeline().linkBoth(label, other);
		return (VertexTraversal<List<EdgeFrame>, EdgeFrame, M>) this;
	}

	@Override
	public VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkOut(String label, VertexFrame other) {
		pipeline().linkOut(label, other.element());
		return (VertexTraversal<List<EdgeFrame>, EdgeFrame, M>) this;
	}

	@Override
	public VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkIn(String label, VertexFrame other) {
		pipeline().linkIn(label, other.element());
		return (VertexTraversal<List<EdgeFrame>, EdgeFrame, M>) this;
	}

	@Override
	public VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkBoth(String label, VertexFrame other) {
		pipeline().linkBoth(label, other.element());
		return (VertexTraversal<List<EdgeFrame>, EdgeFrame, M>) this;
	}

	@Override
	public <N extends VertexFrame> Collection<N> fill(Collection<? super N> collection, Class<N> kind) {

		return pipeline().fill(new FramingCollection<>(collection, graph(), kind));
	}

	@Override
	public <N extends VertexFrame> Collection<N> fillExplicit(Collection<? super N> collection, Class<N> kind) {

		return pipeline().fill(new FramingCollection<>(collection, graph(), kind, true));
	}

	@Override
	public VertexTraversal<?, ?, M> gatherScatter() {
		return (VertexTraversal<?, ?, M>) super.gatherScatter();
	}

	@Override
	public VertexTraversal<?, ?, M> divert(SideEffectFunction<S> sideEffectFunction) {
		return (VertexTraversal<?, ?, M>) super.divert(sideEffectFunction);
	}

	@Override
	public VertexTraversal<?, ?, M> retain(VertexFrame... vertices) {

		return (VertexTraversal<?, ?, M>) super.retain(Arrays.asList(vertices));
	}

	@Override
	public VertexTraversal<?, ?, M> except(VertexFrame... vertices) {

		return (VertexTraversal<?, ?, M>) super.except(Arrays.asList(vertices));
	}

	@Override
	public VertexTraversal<?, ?, M> shuffle() {

		return (VertexTraversal<?, ?, M>) super.shuffle();
	}

	@Override
	public VertexTraversal<?, ?, M> retain(Iterable<?> vertices) {
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
	public <N> SplitTraversal<? extends Traversal<N, ?, ?, M>> copySplit(TraversalFunction<VertexFrame, ? extends Traversal<N, ?, ?, ?>>... traversals) {
		Collection<Pipe> extractedPipes = Collections2.transform(Arrays.asList(traversals),
				new Function<TraversalFunction, Pipe>() {

					@Override
					public Pipe apply(TraversalFunction input) {
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
	public VertexTraversal<?, ?, M> loop(TraversalFunction<VertexFrame, ? extends VertexTraversal<?, ?, ?>> input) {
		GremlinPipeline pipeline = ((AbstractTraversal) input.compute(new TVertex())).pipeline();
		pipeline().add(new LoopPipe(pipeline, LoopPipe.createTrueFunction(), null));

		return this;
	}

	@Override
	public VertexTraversal<?, ?, M> loop(TraversalFunction<VertexFrame, ? extends VertexTraversal<?, ?, ?>> input, int depth) {
		GremlinPipeline pipeline = ((AbstractTraversal) input.compute(new TVertex())).pipeline();
		pipeline().add(new LoopPipe(pipeline, LoopPipe.createLoopsFunction(depth), null));

		return this;
	}

	@Override
	public VertexTraversal<?, ?, M> limit(int limit) {
		return (VertexTraversal) super.limit(limit);
	}
}

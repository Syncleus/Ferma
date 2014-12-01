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

import com.google.common.collect.Sets;
import com.syncleus.ferma.pipes.DivertPipe;
import com.syncleus.ferma.pipes.TraversalFunctionPipe;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.Tokens;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.sideeffect.SideEffectPipe;
import com.tinkerpop.pipes.transform.TransformPipe.Order;
import com.tinkerpop.pipes.util.FluentUtility;
import com.tinkerpop.pipes.util.structures.Pair;
import com.tinkerpop.pipes.util.structures.Row;
import com.tinkerpop.pipes.util.structures.Table;
import com.tinkerpop.pipes.util.structures.Tree;

abstract class AbstractTraversal<T, C, S, M> implements Traversal<T, C, S, M> {

	protected abstract FramedGraph graph();

	protected abstract GremlinPipeline pipeline();

	@Override
	public VertexTraversal<?, ?, M> v() {
		pipeline().V();
		return castToVertices();
	}

	@Override
	public EdgeTraversal<?, ?, M> e() {
		pipeline().E();
		return castToEdges();
	}

	@Override
	public VertexTraversal<?, ?, M> v(Object... ids) {
		return (VertexTraversal) graph().v(ids);
	}

	@Override
	public EdgeTraversal<?, ?, M> e(Object... ids) {
		return (EdgeTraversal) graph().e(ids);
	}

	@Override
	public long count() {
		return pipeline().count();
	}

	@Override
	public Traversal<T, ?, ?, M> as(String name) {
		pipeline().as(name);
		return this;
	}

	@Override
	public void iterate() {
		pipeline().iterate();
	}

	protected Traversal<?, ?, ?, M> has(String key) {
		pipeline().has(key);
		return this;
	}

	protected Traversal<?, ?, ?, M> has(String key, Object value) {
		if(value instanceof Enum) {
			value = value.toString();
		}
		pipeline().has(key, value);
		return this;
	}

	protected Traversal<?, ?, ?, M> has(String key, Tokens.T compareToken, Object value) {
		if (value.getClass().isArray()) {
			value = Arrays.asList((Object[]) value);
		}
		pipeline().has(key, compareToken, value);
		return this;
	}

	protected Traversal<?, ?, ?, M> has(String key, Predicate predicate, Object value) {
		if(value instanceof Enum) {
			value = value.toString();
		}
		pipeline().has(key, predicate, value);
		return this;
	}

	protected Traversal<?, ?, ?, M> hasNot(String key) {
		pipeline().hasNot(key);
		return this;
	}

	protected Traversal<?, ?, ?, M> hasNot(String key, Object value) {
		if(value instanceof Enum) {
			value = value.toString();
		}
		pipeline().hasNot(key, value);
		return this;
	}

	protected <C> Traversal<T, ?, ?, M> interval(String key, Comparable<C> startValue, Comparable<C> endValue) {
		Comparable pipelineStart = startValue;
		if(startValue instanceof Enum)
			pipelineStart = startValue.toString();

		Comparable pipelineEnd = endValue;
		if(endValue instanceof Enum)
			pipelineEnd = endValue.toString();

		pipeline().interval(key, pipelineStart, pipelineEnd);
		return this;
	}

	@Override
	public Traversal<T, ?, ?, M> identity() {
		pipeline()._();
		return this;
	}

	public Traversal<T, ?, ?, M> except(Iterable<?> collection) {
		pipeline().except(unwrap(Lists.newArrayList(collection)));
		return this;
	}

	@Override
	public Traversal<Map<String, Object>, ?, ?, M> map(String... keys) {
		pipeline().map(keys);
		return castToTraversal();
	}

	// @Override
	// public Traversal back(String namedStep) {
	// pipeline().back(namedStep);
	// return asTraversal();
	// }
	//
	// @Override
	// public Traversal back(String namedStep, Class clazz) {
	// pipeline().back(namedStep);
	// return asTraversal();
	// }

	@Override
	public Traversal<T, ?, ?, M> dedup() {
		pipeline().dedup();
		return this;
	}

	@Override
	public Traversal<T, ?, ?, M> dedup(TraversalFunction<T, ?> dedupFunction) {
		pipeline().dedup(dedupFunction);
		return this;
	}

	@Override
	public Traversal<T, ?, ?, M> except(String... namedSteps) {
		pipeline().except(namedSteps);
		return this;
	}

	@Override
	public Traversal<T, ?, ?, M> filter(TraversalFunction<T, Boolean> filterFunction) {
		pipeline().filter(new FramingTraversalFunction(filterFunction, graph()));
		return this;
	}

	@Override
	public Traversal<T, ?, ?, M> random(double bias) {
		pipeline().random(bias);
		return this;
	}

	@Override
	public Traversal<T, ?, ?, M> range(int low, int high) {
		pipeline().range(low, high);
		return this;
	}

	@Override
	public Traversal<T, ?, ?, M> retain(Iterable<?> collection) {
		pipeline().retain(unwrap(Lists.newArrayList(collection)));
		return this;
	}

	@Override
	public Traversal<T, ?, ?, M> retain(String... namedSteps) {
		pipeline().retain(namedSteps);
		return this;
	}

	@Override
	public Traversal<T, ?, ?, M> simplePath() {
		pipeline().simplePath();
		return this;
	}

	@Override
	public Traversal<T, Collection<? extends T>, Collection<? extends T>, M> aggregate() {
		return this.aggregate(new ArrayList());

	}

	@Override
	public Traversal<T, Collection<? extends T>, Collection<? extends T>, M> aggregate(Collection<? super T> aggregate) {
		pipeline().aggregate(aggregate, new FramingTraversalFunction<>(graph()));
		return (Traversal<T, Collection<? extends T>, Collection<? extends T>, M>) this;
	}

	@Override
	public <N> Traversal<T, Collection<? extends N>, Collection<? extends N>, M> aggregate(Collection<? super N> aggregate, TraversalFunction<T, ? extends N> aggregateFunction) {
		pipeline().aggregate(aggregate, new FramingTraversalFunction<>(aggregateFunction, graph()));
		return (Traversal<T, Collection<? extends N>, Collection<? extends N>, M>) this;
	}

	@Override
	public <N> Traversal<T, Collection<? extends N>, Collection<? extends N>, M> aggregate(TraversalFunction<T, ? extends N> aggregateFunction) {
		pipeline().aggregate(new FramingTraversalFunction<>(aggregateFunction, graph()));
		return (Traversal<T, Collection<? extends N>, Collection<? extends N>, M>) this;
	}

	@Override
	public <K, V> Traversal<T, Map<K, List<V>>, Map<K, List<V>>, M> groupBy(Map<K, List<V>> map, TraversalFunction<T, K> keyFunction, TraversalFunction<T, Iterator<V>> valueFunction) {
		pipeline().groupBy(map, new FramingTraversalFunction<>(keyFunction, graph()),
				new TraversalFunctionPipe(new FramingTraversalFunction<>(valueFunction, graph())));
		return (Traversal<T, Map<K, List<V>>, Map<K, List<V>>, M>) this;
	}

	@Override
	public <K, V> Traversal<T, Map<K, List<V>>, Map<K, List<V>>, M> groupBy(TraversalFunction<T, K> keyFunction, TraversalFunction<T, Iterator<V>> valueFunction) {

		pipeline().groupBy(new FramingTraversalFunction<>(keyFunction, graph()),
				new TraversalFunctionPipe(new FramingTraversalFunction<>(valueFunction, graph())));
		return (Traversal<T, Map<K, List<V>>, Map<K, List<V>>, M>) this;
	}

	@Override
	public <K, V, V2> Traversal<T, Map<K, V2>, Map<K, V2>, M> groupBy(Map<K, V2> reduceMap, TraversalFunction<T, K> keyFunction, TraversalFunction<T, Iterator<V>> valueFunction, TraversalFunction<List<V>, V2> reduceFunction) {
		pipeline().groupBy(reduceMap, new FramingTraversalFunction<>(keyFunction, graph()),
				new TraversalFunctionPipe(new FramingTraversalFunction<>(valueFunction, graph())), reduceFunction);
		return (Traversal<T, Map<K, V2>, Map<K, V2>, M>) this;
	}

	@Override
	public <K, V, V2> Traversal<T, Map<K, V2>, Map<K, V2>, M> groupBy(TraversalFunction<T, K> keyFunction, TraversalFunction<T, Iterator<V>> valueFunction, TraversalFunction<List<V>, V2> reduceFunction) {
		pipeline().groupBy(new FramingTraversalFunction<>(keyFunction, graph()),
				new TraversalFunctionPipe(new FramingTraversalFunction<>(valueFunction, graph())), reduceFunction);
		return (Traversal<T, Map<K, V2>, Map<K, V2>, M>) this;
	}

	@Override
	public  <K> Traversal<T, Map<K, Long>, Map<K, Long>, M> groupCount(Map<K, Long> map, TraversalFunction<T, K> keyFunction, TraversalFunction<Pair<T, Long>, Long> valueFunction) {
		pipeline().groupCount(map, new FramingTraversalFunction<>(keyFunction, graph()),
				new TraversalFunctionPipe(new FramingTraversalFunction<>(valueFunction, graph())));
		return (Traversal<T, Map<K, Long>, Map<K, Long>, M>) this;
	}

	@Override
	public <K> Traversal<T, Map<K, Long>, Map<K, Long>, M> groupCount(TraversalFunction<T, K> keyFunction, TraversalFunction<Pair<T, Long>, Long> valueFunction) {
		pipeline().groupCount(new FramingTraversalFunction<>(keyFunction, graph()),
				new FramingTraversalFunction<>(valueFunction, graph()));
		return (Traversal<T, Map<K, Long>, Map<K, Long>, M>) this;
	}

	@Override
	public <K> Traversal<T, Map<K, Long>, Map<K, Long>, M> groupCount(Map<K, Long> map, TraversalFunction<T, K> keyFunction) {
		pipeline().groupCount(new FramingMap<>(map, graph()), new FramingTraversalFunction<>(keyFunction, graph()));
		return (Traversal<T, Map<K, Long>, Map<K, Long>, M>) this;
	}

	@Override
	public <K> Traversal<T, Map<K, Long>, Map<K, Long>, M> groupCount(TraversalFunction<T, K> keyFunction) {
		pipeline().groupCount(new FramingTraversalFunction<>(keyFunction, graph()));
		return (Traversal<T, Map<K, Long>, Map<K, Long>, M>) this;
	}

	@Override
	public Traversal<T, Map<T, Long>, Map<T, Long>, M> groupCount(Map<T, Long> map) {
		pipeline().groupCount(new FramingMap<>(map, graph()));
		return (Traversal<T, Map<T, Long>, Map<T, Long>, M>) this;
	}

	@Override
	public Traversal<T, Map<T, Long>, Map<T, Long>, M> groupCount() {
		return this.groupCount(new HashMap());

	}

	@Override
	public EdgeTraversal<?, ?, M> idEdge(Graph graph) {
		pipeline().idEdge(graph);
		return castToEdges();
	}

	@Override
	public <N> Traversal<N, ?, ?, M> id() {
		pipeline().id();
		return castToTraversal();
	}

	@Override
	public <N> Traversal<? extends N,?,?, M> id(Class<N> c) {
		return (Traversal<? extends N,?,?, M>) this.id();
	}

	@Override
	public VertexTraversal<?, ?, M> idVertex(Graph graph) {
		pipeline().idVertex(graph);
		return castToVertices();
	}

	@Override
	public Traversal<T, ?, ?, M> sideEffect(SideEffectFunction<T> sideEffectFunction) {
		final FramingSideEffectFunction function = new FramingSideEffectFunction<>(sideEffectFunction, graph());
		pipeline().sideEffect(new TraversalFunction() {

			@Override
			public Object compute(Object argument) {
				function.execute(argument);
				return null;
			}

		});
		return this;
	}

	@Override
	public <N> Traversal<T, Collection<N>, N, M> store(Collection<N> storage) {
		pipeline().store(storage, new FramingTraversalFunction<>(graph()));
		return (Traversal<T, Collection<N>, N, M>) this;
	}

	@Override
	public <N> Traversal<T, Collection<N>, N, M> store(Collection<N> storage, TraversalFunction<T, N> storageFunction) {
		pipeline().store(storage, new FramingTraversalFunction<>(storageFunction, graph()));
		return (Traversal<T, Collection<N>, N, M>) this;
	}

	@Override
	public Traversal<T, Collection<T>, T, M> store() {
		return this.store(new ArrayList<T>());
	}

	@Override
	public <N> Traversal<T, Collection<N>, N, M> store(TraversalFunction<T, N> storageFunction) {
		pipeline().store(new FramingTraversalFunction<>(storageFunction, graph()));
		return (Traversal<T, Collection<N>, N, M>) this;
	}

	@Override
	public Traversal<T, Table, Table, M> table(Table table, Collection<String> stepNames, TraversalFunction<?, ?>... columnFunctions) {
		pipeline().table(table, stepNames, this.<Object, Object, Object>wrap(columnFunctions));
		return (Traversal<T, Table, Table, M>) this;
	}

	@Override
	public Traversal<T, Table, Table, M> table(Table table, TraversalFunction<?, ?>... columnFunctions) {
		pipeline().table(table, wrap(columnFunctions));
		return (Traversal<T, Table, Table, M>) this;
	}

	@Override
	public Traversal<T, Table, Table, M> table(TraversalFunction<?, ?>... columnFunctions) {
		pipeline().table(wrap(columnFunctions));
		return (Traversal<T, Table, Table, M>) this;
	}

	@Override
	public Traversal<T, Table, Table, M> table(Table table) {
		pipeline().table(table, new FramingTraversalFunction<>(graph()));
		return (Traversal<T, Table, Table, M>) this;
	}

	@Override
	public Traversal<T, Table, Table, M> table() {
		pipeline().table(new FramingTraversalFunction<>(graph()));
		return (Traversal<T, Table, Table, M>) this;
	}

	public <N> Traversal<T, Tree<N>, Tree<N>, M> tree(Tree<N> tree) {
		pipeline().tree(tree, new FramingTraversalFunction<>(graph()));
		return (Traversal<T, Tree<N>, Tree<N>, M>) this;
	}

	public Traversal<T, Tree<T>, Tree<T>, M> tree() {
		pipeline().tree(new FramingTraversalFunction<>(graph()));
		return (Traversal<T, Tree<T>, Tree<T>, M>) this;
	}

	@Override
	public Traversal<T, ?, ?, M> memoize(String namedStep) {
		pipeline().memoize(namedStep);
		return this;
	}

	@Override
	public Traversal<T, ?, ?, M> memoize(String namedStep, Map<?,?> map) {
		pipeline().memoize(namedStep, map);
		return this;
	}

	@Override
	public Traversal<T, ?, ?, M> order() {
		pipeline().order();
		return this;
	}

	@Override
	public Traversal<T, ?, ?, M> order(Order order) {
		pipeline().order(order);
		return this;
	}

	@Override
	public Traversal<T, ?, ?, M> order(Tokens.T order) {
		pipeline().order(order);
		return this;
	}

	@Override
	public Traversal<T, ?, ?, M> order(final Comparator<T> compareFunction) {
		final FramingComparator framingComparator = new FramingComparator(compareFunction, graph());
		pipeline().order(new TraversalFunction<Pair<Object, Object>, Integer>() {

			@Override
			public Integer compute(Pair<Object, Object> argument) {
				return framingComparator.compare(argument.getA(), argument.getB());
			}
		});
		return this;
	}

	@Override
	public Traversal<Path, ? , ?, M> path(TraversalFunction<?, ?>... pathFunctions) {
		if (pathFunctions.length == 0) {
			pipeline().path(new FramingTraversalFunction<>(graph()));
		} else {
			pipeline().path(wrap(pathFunctions));
		}
		return castToTraversal();
	}

	@Override
	public Traversal<Row<?>, ?, ?, M> select(Collection<String> stepNames, TraversalFunction<?, ?>... columnFunctions) {
		pipeline().select(stepNames, wrap(columnFunctions));
		return castToTraversal();
	}

	@Override
	public Traversal<Row<?>, ?, ?, M> select(TraversalFunction<?, ?>... columnFunctions) {
		pipeline().select(wrap(columnFunctions));
		return castToTraversal();
	}

	@Override
	public Traversal<Row<?>, ?, ?, M> select() {
		pipeline().select(new FramingTraversalFunction<>(graph()));
		return castToTraversal();
	}

	@Override
	public Traversal<T, ?, ?, M> shuffle() {
		pipeline().shuffle();
		return this;
	}

	@Override
	public C cap() {
		pipeline().cap();
		Object next = castToTraversal().next();
		if (next instanceof FramingMap) {
			next = ((FramingMap) next).getDelegate();
		}
		if (next instanceof FramingCollection) {
			next = ((FramingCollection) next).getDelegate();
		}
		return (C) next;
	}

	@Override
	public Traversal<T, ?, ?, M> divert(SideEffectFunction<S> sideEffectFunction) {

		final FramingSideEffectFunction framingSideEffectFunction = new FramingSideEffectFunction(sideEffectFunction, graph());
		pipeline().add(
				new DivertPipe((SideEffectPipe) FluentUtility.removePreviousPipes(pipeline(), 1).get(0), new TraversalFunction() {

					@Override
					public Object compute(Object argument) {
						framingSideEffectFunction.execute(argument);
						return null;
					}

				}));
		return this;
	}

	@Override
	public <N> Traversal<? extends N, ?, ?, M> transform(TraversalFunction<T, N> function) {
		pipeline().transform(new FramingTraversalFunction(function, graph()));
		return castToTraversal();
	}

	@Override
	public <N> Traversal<N, ?, ?, M> start(N object) {
		pipeline().start(object);
		return (Traversal<N, ?, ?, M>) this;
	}

	@Override
	public List<? extends T> next(int number) {
		return Lists.transform(pipeline().next(number), new Function() {

			public Object apply(Object e) {
				if (e instanceof Edge) {
					return graph().frameElement((Element) e, TEdge.class);
				} else if (e instanceof Vertex) {
					return graph().frameElement((Element) e, TVertex.class);
				}
				return e;
			}
		});

	}

	@Override
	public List<? extends T> toList() {

		return Lists.transform(pipeline().toList(), new Function() {

			public Object apply(Object e) {
				if (e instanceof Edge) {
					return graph().frameElementExplicit((Element) e, TEdge.class);
				} else if (e instanceof Vertex) {
					return graph().frameElementExplicit((Element) e, TVertex.class);
				}
				return e;
			}
		});

	}

	@Override
	public Set<? extends T> toSet() {

		return Sets.newHashSet(toList());

	}

	@Override
	public Collection<T> fill(Collection<? super T> collection) {
		return pipeline().fill(new FramingCollection<>(collection, graph()));
	}

	@Override
	public Traversal<T, C, S, M> enablePath() {
		pipeline().enablePath();
		return this;
	}

	@Override
	public Traversal<T, C, S, M> optimize(boolean optimize) {
		pipeline().optimize(optimize);
		return this;
	}

	@Override
	public T next() {
		Object e = pipeline().next();
		if (e instanceof Edge) {
			return (T) graph().frameElementExplicit((Element) e, TEdge.class);
		} else if (e instanceof Vertex) {
			return (T) graph().frameElementExplicit((Element) e, TVertex.class);
		}
		return (T) e;
	}

	@Override
	public T nextOrDefault(T defaultValue) {
		if (pipeline().hasNext()) {
			return next();
		} else {
			return defaultValue;
		}
	}

	@Override
	public EdgeTraversal<?, ?, M> start(EdgeFrame object) {
		pipeline().start(object);
		return castToEdges();
	}

	@Override
	public VertexTraversal<?, ?, M> start(VertexFrame object) {
		pipeline().start(object);
		return castToVertices();
	}

	@Override
	public <N> Traversal<N, ?, ?, M> property(String key) {
		pipeline().property(key);
		return castToTraversal();
	}

	@Override
	public <N> Traversal<? extends N, ?, ?, M> property(String key, Class<N> type) {
		return property(key);

	}

	protected abstract <W,X,Y,Z> Traversal<W,X,Y,Z> castToTraversal();

	@Override
	public boolean hasNext() {
		return pipeline().hasNext();
	}

	@Override
	public Iterator<T> iterator() {
		return Iterators.transform(pipeline(), new Function() {

			public Object apply(Object e) {
				if (e instanceof Element) {
					return graph().frameElement((Element) e, TVertex.class);
				}

				return e;
			}
		});
	}

	private HashSet<? extends Element> unwrap(Collection<?> collection) {
		HashSet unwrapped = new HashSet(Collections2.transform(collection, new Function<Object, Object>() {

			@Override
			public Object apply(Object o) {
				if (o instanceof VertexFrame) {
					return ((VertexFrame) o).element();
				}
				if (o instanceof EdgeFrame) {
					return ((EdgeFrame) o).element();
				}
				return o;
			}
		}));
		return unwrapped;
	}

	private <X,Y,Z> TraversalFunction<? extends Z, ? extends Y>[] wrap(TraversalFunction<? extends X,? extends Y>... branchFunctions) {
		Collection<TraversalFunction<Z,Y>> wrapped = Collections2.transform(Arrays.<TraversalFunction<? extends X, ? extends Y>>asList(branchFunctions),
				new Function<TraversalFunction<? extends X, ? extends Y>, TraversalFunction<Z, Y>>() {

					@Override
					public TraversalFunction<Z, Y> apply(TraversalFunction<? extends X, ? extends Y> input) {
						return new FramingTraversalFunction(input, graph());
					}
				});
		TraversalFunction<Z,Y>[] wrappedArray = wrapped.toArray(new TraversalFunction[wrapped.size()]);
		return wrappedArray;
	}

	@Override
	public Traversal<T, ?, ?, M> gatherScatter() {
		pipeline().gather().scatter();
		return this;
	}

	@Override
	public Traversal<T, C, S, ? extends Traversal<T, C, S, M>> mark() {
		MarkId mark = pushMark();
		pipeline().as(mark.id);

		return (Traversal<T, C, S, ? extends Traversal<T, C, S, M>>) this;
	}

	@Override
	public M back() {
		MarkId mark = popMark();
		pipeline().back(mark.id);
		return (M) mark.traversal;
	}

	@Override
	public M optional() {
		MarkId mark = popMark();
		pipeline().optional(mark.id);
		return (M) mark.traversal;
	}

	/**
	 * Cast the traversal as a split traversal
	 * 
	 * @return
	 */
	protected abstract <N> SplitTraversal<N> castToSplit();

	/**
	 * Cast the traversal as a vertex traversal
	 * 
	 * @return
	 */
	protected abstract VertexTraversal<C, S, M> castToVertices();

	/**
	 * Cast the traversal to an edge traversalT
	 * 
	 * @return
	 */
	protected abstract EdgeTraversal<C, S, M> castToEdges();

	protected abstract <T, Cap, SideEffect, Mark> MarkId<T, Cap, SideEffect, Mark> pushMark();

	protected abstract <T, Cap, SideEffect, Mark> MarkId<T, Cap, SideEffect, Mark> popMark();

	protected static class MarkId<T, Cap, SideEffect, Mark> {
		Traversal<T, Cap, SideEffect, Mark> traversal;
		String id;
	}

	@Override
	public VertexTraversal<?, ?, M> v(Collection<?> ids) {
		return (VertexTraversal) graph().v(ids);
	}

	@Override
	public VertexTraversal<?, ?, M> v(String key, Object value) {
		pipeline().V(key, value);
		return castToVertices();
	}

	@Override
	public EdgeTraversal<?, ?, M> e(Collection<?> ids) {
		return (EdgeTraversal) graph().e(ids);
	}

	@Override
	public Traversal<T, ?, ?, M> limit(int limit) {
		return range(0, limit - 1);
	}
}

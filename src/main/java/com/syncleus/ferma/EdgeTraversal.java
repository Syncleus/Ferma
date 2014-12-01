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

import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.gremlin.Tokens;
import com.tinkerpop.pipes.transform.TransformPipe;
import com.tinkerpop.pipes.util.structures.Pair;
import com.tinkerpop.pipes.util.structures.Table;
import com.tinkerpop.pipes.util.structures.Tree;

/**
 * Edge specific traversal.
 *
 * @param <C> The cap of the current pipe.
 * @param <S> The SideEffect of the current pipe.
 * @param <M> The current mark'ed type for the current pipe.
 */
public interface EdgeTraversal<C, S, M> extends Traversal<EdgeFrame, C, S, M> {
	/**
	 * Check if the element has a property with provided key.
	 *
	 * @param key
	 *            the property key to check
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, M> has(String key);

	/**
	 * Add an IdFilterPipe, LabelFilterPipe, or PropertyFilterPipe to the end of
	 * the Pipeline. If the incoming element has the provided key/value as check
	 * with .equals(), then let the element pass. If the key is id or label,
	 * then use respect id or label filtering.
	 *
	 * @param key
	 *            the property key to check
	 * @param value
	 *            the object to filter on (in an OR manner)
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, M> has(String key, Object value);

	/**
	 * Add an IdFilterPipe, LabelFilterPipe, or PropertyFilterPipe to the end of
	 * the Pipeline. If the incoming element has the provided key/value as check
	 * with .equals(), then let the element pass. If the key is id or label,
	 * then use respect id or label filtering.
	 *
	 * @param key
	 *            the property key to check
	 * @param compareToken
	 *            the comparison to use
	 * @param value
	 *            the object to filter on
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, M> has(String key, Tokens.T compareToken, Object value);

	/**
	 * Add an IdFilterPipe, LabelFilterPipe, or PropertyFilterPipe to the end of
	 * the Pipeline. If the incoming element has the provided key/value as check
	 * with .equals(), then let the element pass. If the key is id or label,
	 * then use respect id or label filtering.
	 *
	 * @param key
	 *            the property key to check
	 * @param predicate
	 *            the comparison to use
	 * @param value
	 *            the object to filter on
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, M> has(String key, Predicate predicate, Object value);

	/**
	 * Check if the element does not have a property with provided key.
	 *
	 * @param key
	 *            the property key to check
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, M> hasNot(String key);

	/**
	 * Add an IdFilterPipe, LabelFilterPipe, or PropertyFilterPipe to the end of
	 * the Pipeline. If the incoming element has the provided key/value as check
	 * with .equals(), then filter the element. If the key is id or label, then
	 * use respect id or label filtering.
	 *
	 * @param key
	 *            the property key to check
	 * @param value
	 *            the objects to filter on (in an OR manner)
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, M> hasNot(String key, Object value);

	/**
	 * Add an IntervalFilterPipe to the end of the Pipeline. If the incoming
	 * element has a value that is within the interval value range specified,
	 * then the element is allows to pass. If hte incoming element's value for
	 * the key is null, the element is filtered.
	 *
	 * @param key
	 *            the property key to check
	 * @param startValue
	 *            the start of the interval (inclusive)
	 * @param endValue
	 *            the end of the interval (exclusive)
	 * @return the extended Pipeline
	 */
	public <C> EdgeTraversal<?, ?, M> interval(String key, Comparable<C> startValue, Comparable<C> endValue);

	/**
	 * Add an InVertexPipe to the end of the Pipeline. Emit the head vertex of
	 * the incoming edge.
	 *
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, M> inV();

	/**
	 * Add an OutVertexPipe to the end of the Pipeline. Emit the tail vertex of
	 * the incoming edge.
	 *
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, M> outV();

	/**
	 * Emit both the tail and head vertices of the incoming edge.
	 *
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, M> bothV();

	/**
	 * Get the next object emitted from the pipeline. If no such object exists,
	 * then a NoSuchElementException is thrown.
	 * 
	 * @param kind
	 *            The type of frame for the element.
	 * @return the next emitted object
	 */
	public <T> T next(Class<T> kind);

	/**
	 * Get the next object emitted from the pipeline. If no such object exists,
	 * then a NoSuchElementException is thrown.
	 *
	 * This will bypass the default type resolution and use the untyped resolver
	 * instead. This method is useful for speeding up a look up when type resolution
	 * isn't required.
	 *
	 * @param kind
	 *            The type of frame for the element.
	 * @return the next emitted object
	 */
	public <T> T nextExplicit(Class<T> kind);

	/**
	 * Return the next X objects in the traversal as a list.
	 * 
	 * @param amount
	 *            the number of objects to return
	 * @param kind
	 *            the type of frame to for each element.
	 * @return a list of X objects (if X objects occur)
	 */
	public <T> List<T> next(int amount, Class<T> kind);

	/**
	 * Return the next X objects in the traversal as a list.
	 *
	 * This will bypass the default type resolution and use the untyped resolver
	 * instead. This method is useful for speeding up a look up when type resolution
	 * isn't required.
	 *
	 * @param amount
	 *            the number of objects to return
	 * @param kind
	 *            the type of frame to for each element.
	 * @return a list of X objects (if X objects occur)
	 */
	public <T> List<T> nextExplicit(int amount, Class<T> kind);

	/**
	 * Return an iterator of framed elements.
	 * 
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return An iterator of framed elements.
	 */
	public <T> Iterable<T> frame(Class<T> kind);

	/**
	 * Return an iterator of framed elements.
	 *
	 * This will bypass the default type resolution and use the untyped resolver
	 * instead. This method is useful for speeding up a look up when type resolution
	 * isn't required.
	 *
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return An iterator of framed elements.
	 */
	public <T> Iterable<T> frameExplicit(Class<T> kind);

	/**
	 * Return a list of all the objects in the pipeline.
	 * 
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return a list of all the objects
	 */
	public <T> List<? extends T> toList(Class<T> kind);

	/**
	 * Return a set of all the objects in the pipeline.
	 *
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return a set of all the objects
	 */
	public <T> Set<? extends T> toSet(Class<T> kind);

	/**
	 * Return a set of all the objects in the pipeline.
	 *
	 * This will bypass the default type resolution and use the untyped resolver
	 * instead. This method is useful for speeding up a look up when type resolution
	 * isn't required.
	 *
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return a set of all the objects
	 */
	public <T> Set<? extends T> toSetExplicit(Class<T> kind);

	/**
	 * Return a list of all the objects in the pipeline.
	 *
	 * This will bypass the default type resolution and use the untyped resolver
	 * instead. This method is useful for speeding up a look up when type resolution
	 * isn't required.
	 *
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return a list of all the objects
	 */
	public <T> List<? extends T> toListExplicit(Class<T> kind);

	/**
	 * Add an LabelPipe to the end of the Pipeline. Emit the label of the
	 * incoming edge.
	 *
	 * @return the extended Pipeline
	 */
	public abstract Traversal<String, ?, ?, M> label();

	@Override
	public abstract EdgeTraversal<?, ?, M> dedup();

	@Override
	public abstract EdgeTraversal<?, ?, M> dedup(TraversalFunction<EdgeFrame, ?> dedupFunction);

	@Override
	public abstract EdgeTraversal<?, ?, M> except(Iterable<?> collection);

	/**
	 * Add an ExceptFilterPipe to the end of the Pipeline. Will only emit the
	 * object if it is not in the provided array.
	 *
	 * @param edges
	 *            the edges to reject from the stream
	 * @return the extended Pipeline
	 */
	public abstract EdgeTraversal<?, ?, M> except(EdgeFrame... edges);

	@Override
	public abstract EdgeTraversal<?, ?, M> except(String... namedSteps);

	@Override
	public abstract EdgeTraversal<?, ?, M> filter(TraversalFunction<EdgeFrame, Boolean> filterFunction);

	@Override
	public abstract EdgeTraversal<?, ?, M> random(double bias);

	@Override
	public abstract EdgeTraversal<?, ?, M> range(int low, int high);
	
	@Override
	public abstract EdgeTraversal<?, ?, M> limit(int limit);

	@Override
	public abstract EdgeTraversal<?, ?, M> retain(Iterable<?> collection);

	/**
	 * Will emit the object only if it is in the provided array.
	 *
	 * @param edges
	 *            the edges to retain
	 * @return the extended Pipeline
	 */
	public abstract EdgeTraversal<?, ?, M> retain(EdgeFrame... edges);

	@Override
	public abstract EdgeTraversal<?, ?, M> retain(String... namedSteps);

	@Override
	public abstract EdgeTraversal<Collection<? extends EdgeFrame>, Collection<? extends EdgeFrame>, M> aggregate();

	@Override
	public abstract EdgeTraversal<Collection<? extends EdgeFrame>, Collection<? extends EdgeFrame>, M> aggregate(Collection<? super EdgeFrame> aggregate);

	@Override
	public abstract <N> EdgeTraversal<Collection<? extends N>, Collection<? extends N>, M> aggregate(Collection<? super N> aggregate, TraversalFunction<EdgeFrame, ? extends N> aggregateFunction);

	@Override
	public abstract <N> EdgeTraversal<Collection<? extends N>, Collection<? extends N>, M> aggregate(TraversalFunction<EdgeFrame, ? extends N> aggregateFunction);

	@Override
	public abstract <K, V> EdgeTraversal<Map<K, List<V>>, Map<K, List<V>>, M> groupBy(Map<K, List<V>> map,
			TraversalFunction<EdgeFrame, K> keyFunction, TraversalFunction<EdgeFrame, Iterator<V>> valueFunction);

	@Override
	public abstract <K, V> EdgeTraversal<Map<K, List<V>>, Map<K, List<V>>, M> groupBy(TraversalFunction<EdgeFrame, K> keyFunction,
			TraversalFunction<EdgeFrame, Iterator<V>> valueFunction);

	@Override
	public abstract <K, V, V2> EdgeTraversal<Map<K, V2>, Map<K, V2>, M> groupBy(Map<K, V2> reduceMap,
			TraversalFunction<EdgeFrame, K> keyFunction, TraversalFunction<EdgeFrame, Iterator<V>> valueFunction,
			TraversalFunction<List<V>, V2> reduceFunction);

	@Override
	public abstract <K, V, V2> EdgeTraversal<Map<K, V2>, Map<K, V2>, M> groupBy(TraversalFunction<EdgeFrame, K> keyFunction,
			TraversalFunction<EdgeFrame, Iterator<V>> valueFunction, TraversalFunction<List<V>, V2> reduceFunction);

	@Override
	public abstract <K> EdgeTraversal<Map<K, Long>, Map<K, Long>, M> groupCount(Map<K, Long> map,
			TraversalFunction<EdgeFrame, K> keyFunction, TraversalFunction<Pair<EdgeFrame, Long>, Long> valueFunction);

	@Override
	public abstract <K> EdgeTraversal<Map<K, Long>, Map<K, Long>, M> groupCount(TraversalFunction<EdgeFrame, K> keyFunction,
			TraversalFunction<Pair<EdgeFrame, Long>, Long> valueFunction);

	@Override
	public abstract <K> EdgeTraversal<Map<K, Long>, Map<K, Long>, M> groupCount(Map<K, Long> map,
			TraversalFunction<EdgeFrame, K> keyFunction);

	@Override
	public abstract <K> EdgeTraversal<Map<K, Long>, Map<K, Long>, M> groupCount(TraversalFunction<EdgeFrame, K> keyFunction);

	@Override
	public abstract EdgeTraversal<Map<EdgeFrame, Long>, Map<EdgeFrame, Long>, M> groupCount(Map<EdgeFrame, Long> map);

	@Override
	public abstract EdgeTraversal<Map<EdgeFrame, Long>, Map<EdgeFrame, Long>, M> groupCount();

	@Override
	public abstract EdgeTraversal<?, ?, M> sideEffect(SideEffectFunction<EdgeFrame> sideEffectFunction);

	@Override
	public abstract <N> EdgeTraversal<Collection<N>, N, M> store(Collection<N> storage);

	@Override
	public abstract <N> EdgeTraversal<Collection<N>, N, M> store(Collection<N> storage,
			TraversalFunction<EdgeFrame, N> storageFunction);

	@Override
	public abstract EdgeTraversal<Collection<EdgeFrame>, EdgeFrame, M> store();

	@Override
	public abstract <N> EdgeTraversal<Collection<N>, N, M> store(TraversalFunction<EdgeFrame, N> storageFunction);

	@Override
	public abstract EdgeTraversal<Table, Table, M> table(Table table, Collection<String> stepNames,
			TraversalFunction<?, ?>... columnFunctions);

	@Override
	public abstract EdgeTraversal<Table, Table, M> table(Table table, TraversalFunction<?, ?>... columnFunctions);

	@Override
	public abstract EdgeTraversal<Table, Table, M> table(TraversalFunction<?, ?>... columnFunctions);

	@Override
	public abstract EdgeTraversal<Table, Table, M> table(Table table);

	@Override
	public abstract EdgeTraversal<Table, Table, M> table();

	@Override
	public abstract <N> EdgeTraversal<Tree<N>, Tree<N>, M> tree(Tree<N> tree);

	@Override
	public abstract EdgeTraversal<?, ?, M> identity();

	@Override
	public abstract EdgeTraversal<?, ?, M> memoize(String namedStep);

	@Override
	public abstract EdgeTraversal<?, ?, M> memoize(String namedStep, Map<?, ?> map);

	@Override
	public abstract EdgeTraversal<?, ?, M> order();

	@Override
	public abstract EdgeTraversal<?, ?, M> order(TransformPipe.Order order);

	@Override
	public abstract EdgeTraversal<?, ?, M> order(Comparator<EdgeFrame> compareFunction);

	@Override
	public abstract EdgeTraversal<?, ?, M> as(String name);

	@Override
	public abstract EdgeTraversal<?, ?, M> simplePath();

	/**
	 * Fill the provided collection with the objects in the pipeline.
	 *
	 * @param collection
	 *            the collection to fill
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return the collection filled
	 */
	public abstract <N> Collection<N> fill(Collection<? super N> collection, Class<N> kind);

	/**
	 * Fill the provided collection with the objects in the pipeline.
	 *
	 * This will bypass the default type resolution and use the untyped resolver
	 * instead. This method is useful for speeding up a look up when type resolution
	 * isn't required.
	 *
	 * @param collection
	 *            the collection to fill
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return the collection filled
	 */
	public abstract <N> Collection<N> fillExplicit(Collection<? super N> collection, Class<N> kind);

	@Override
	public abstract EdgeTraversal<?, ?, M> gatherScatter();

	/**
	 * Add an AndFilterPipe to the end the Pipeline. If the internal pipes all
	 * yield objects, then the object is not filtered. The provided pipes are
	 * provided the object as their starts.
	 *
	 * @param pipes
	 *            the internal pipes of the AndFilterPipe
	 * @return the extended Pipeline
	 */
	public abstract EdgeTraversal<?, ?, M> and(TraversalFunction<EdgeFrame, Traversal<?, ?, ?, ?>>... pipes);

	/**
	 * Add an OrFilterPipe to the end the Pipeline. Will only emit the object if
	 * one or more of the provides pipes yields an object. The provided pipes
	 * are provided the object as their starts.
	 *
	 * @param pipes
	 *            the internal pipes of the OrFilterPipe
	 * @return the extended Pipeline
	 */
	public abstract EdgeTraversal<?, ?, M> or(TraversalFunction<EdgeFrame, Traversal<?, ?, ?, ?>>... pipes);

	@Override
	public abstract EdgeTraversal<?, ?, M> divert(SideEffectFunction<S> sideEffect);

	@Override
	public EdgeTraversal<?, ?, M> shuffle();

	@Override
	public EdgeTraversal<C, S, ? extends EdgeTraversal<C, S, M>> mark();

	/**
	 * Remove every element at the end of this Pipeline.
	 */
	public abstract void removeAll();

	// /**
	// * Remove the current element at the end of this Pipeline.
	// */
	// public abstract void remove();

	/**
	 * The incoming objects are copied to the provided pipes. This "split-pipe"
	 * is used in conjunction with some type of "merge-pipe."
	 *
	 * @param traversals
	 *            the internal pipes of the CopySplitPipe
	 * @return the extended Pipeline
	 */
	public abstract <N> SplitTraversal<? extends Traversal<N, ?, ?, M>> copySplit(TraversalFunction<EdgeFrame, ? extends Traversal<N, ?, ?, ?>>... traversals);
	
	/**
	 * The pipeline loops over the supplied traversal.
	 *
	 * @return the extended Pipeline
	 */
	public abstract EdgeTraversal<?, ?, M> loop(TraversalFunction<EdgeFrame, ? extends EdgeTraversal<?, ?, ?>> traversal);
	
	/**
	 * The pipeline loops over the supplied traversal up to a maximum depth.
	 *
	 * @param depth The maximum depth to loop to
	 * @return the extended Pipeline
	 */
	public abstract EdgeTraversal<?, ?, M> loop(TraversalFunction<EdgeFrame, ? extends EdgeTraversal<?, ?, ?>> traversal, int depth);
}
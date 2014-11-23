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

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.gremlin.Tokens;
import com.tinkerpop.pipes.transform.TransformPipe;
import com.tinkerpop.pipes.util.structures.Pair;
import com.tinkerpop.pipes.util.structures.Table;
import com.tinkerpop.pipes.util.structures.Tree;

/**
 * Edge specific traversal.
 * 
 * @author bryn
 *
 * @param <Cap>
 */
public interface EdgeTraversal<Cap, SideEffect, Mark> extends Traversal<TEdge, Cap, SideEffect, Mark> {
	/**
	 * Check if the element has a property with provided key.
	 *
	 * @param key
	 *            the property key to check
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, Mark> has(String key);

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
	public EdgeTraversal<?, ?, Mark> has(String key, Object value);

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
	public EdgeTraversal<?, ?, Mark> has(String key, Tokens.T compareToken, Object value);

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
	public EdgeTraversal<?, ?, Mark> has(String key, Predicate predicate, Object value);

	/**
	 * Check if the element does not have a property with provided key.
	 *
	 * @param key
	 *            the property key to check
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, Mark> hasNot(String key);

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
	public EdgeTraversal<?, ?, Mark> hasNot(String key, Object value);

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
	public <C> EdgeTraversal<?, ?, Mark> interval(String key, Comparable<C> startValue, Comparable<C> endValue);

	/**
	 * Add an InVertexPipe to the end of the Pipeline. Emit the head vertex of
	 * the incoming edge.
	 *
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, Mark> inV();

	/**
	 * Add an OutVertexPipe to the end of the Pipeline. Emit the tail vertex of
	 * the incoming edge.
	 *
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, Mark> outV();

	/**
	 * Emit both the tail and head vertices of the incoming edge.
	 *
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, Mark> bothV();

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
	 * Return the next X objects in the traversal as a list.
	 * 
	 * @param number
	 *            the number of objects to return
	 * @param kind
	 *            the type of frame to for each element.
	 * @return a list of X objects (if X objects occur)
	 */
	public <T> List<T> next(int amount, Class<T> kind);

	/**
	 * Return an iterator of framed elements.
	 * 
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return An iterator of framed elements.
	 */
	public <T> Iterable<T> frame(Class<T> kind);

	/**
	 * Return a list of all the objects in the pipeline.
	 * 
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return a list of all the objects
	 */
	public <T> List<T> toList(Class<T> kind);

	/**
	 * Add an LabelPipe to the end of the Pipeline. Emit the label of the
	 * incoming edge.
	 *
	 * @return the extended Pipeline
	 */
	public abstract Traversal<String, ?, ?, Mark> label();

	@Override
	public abstract EdgeTraversal<?, ?, Mark> dedup();

	@Override
	public abstract EdgeTraversal<?, ?, Mark> dedup(TraversalFunction<TEdge, ?> dedupFunction);

	@Override
	public abstract EdgeTraversal<?, ?, Mark> except(Iterable<?> collection);

	/**
	 * Add an ExceptFilterPipe to the end of the Pipeline. Will only emit the
	 * object if it is not in the provided array.
	 *
	 * @param edges
	 *            the edges to reject from the stream
	 * @return the extended Pipeline
	 */
	public abstract EdgeTraversal<?, ?, Mark> except(FramedEdge... edges);

	@Override
	public abstract EdgeTraversal<?, ?, Mark> except(String... namedSteps);

	@Override
	public abstract EdgeTraversal<?, ?, Mark> filter(TraversalFunction<TEdge, Boolean> filterFunction);

	@Override
	public abstract EdgeTraversal<?, ?, Mark> random(double bias);

	@Override
	public abstract EdgeTraversal<?, ?, Mark> range(int low, int high);
	
	@Override
	public abstract EdgeTraversal<?, ?, Mark> limit(int limit);

	@Override
	public abstract EdgeTraversal<?, ?, Mark> retain(Iterable<?> collection);

	/**
	 * Will emit the object only if it is in the provided array.
	 *
	 * @param edges
	 *            the edges to retain
	 * @return the extended Pipeline
	 */
	public abstract EdgeTraversal<?, ?, Mark> retain(FramedEdge... edges);

	@Override
	public abstract EdgeTraversal<?, ?, Mark> retain(String... namedSteps);

	@Override
	public abstract EdgeTraversal<Collection<TEdge>, Collection<TEdge>, Mark> aggregate();

	@Override
	public abstract EdgeTraversal<Collection<TEdge>, Collection<TEdge>, Mark> aggregate(Collection<TEdge> aggregate);

	@Override
	public abstract <N> EdgeTraversal<Collection<N>, Collection<N>, Mark> aggregate(Collection<TEdge> aggregate,
			TraversalFunction<TEdge, N> aggregateFunction);

	@Override
	public abstract <N> EdgeTraversal<Collection<N>, Collection<N>, Mark> aggregate(TraversalFunction<TEdge, N> aggregateFunction);

	@Override
	public abstract <K, V> EdgeTraversal<Map<K, List<V>>, Map<K, List<V>>, Mark> groupBy(Map<K, List<V>> map,
			TraversalFunction<TEdge, K> keyFunction, TraversalFunction<TEdge, Iterator<V>> valueFunction);

	@Override
	public abstract <K, V> EdgeTraversal<Map<K, List<V>>, Map<K, List<V>>, Mark> groupBy(TraversalFunction<TEdge, K> keyFunction,
			TraversalFunction<TEdge, Iterator<V>> valueFunction);

	@Override
	public abstract <K, V, V2> EdgeTraversal<Map<K, V2>, Map<K, V2>, Mark> groupBy(Map<K, V2> reduceMap,
			TraversalFunction<TEdge, K> keyFunction, TraversalFunction<TEdge, Iterator<V>> valueFunction,
			TraversalFunction<List<V>, V2> reduceFunction);

	@Override
	public abstract <K, V, V2> EdgeTraversal<Map<K, V2>, Map<K, V2>, Mark> groupBy(TraversalFunction<TEdge, K> keyFunction,
			TraversalFunction<TEdge, Iterator<V>> valueFunction, TraversalFunction<List<V>, V2> reduceFunction);

	@Override
	public abstract <K> EdgeTraversal<Map<K, Long>, Map<K, Long>, Mark> groupCount(Map<K, Long> map,
			TraversalFunction<TEdge, K> keyFunction, TraversalFunction<Pair<TEdge, Long>, Long> valueFunction);

	@Override
	public abstract <K> EdgeTraversal<Map<K, Long>, Map<K, Long>, Mark> groupCount(TraversalFunction<TEdge, K> keyFunction,
			TraversalFunction<Pair<TEdge, Long>, Long> valueFunction);

	@Override
	public abstract <K> EdgeTraversal<Map<K, Long>, Map<K, Long>, Mark> groupCount(Map<K, Long> map,
			TraversalFunction<TEdge, K> keyFunction);

	@Override
	public abstract <K> EdgeTraversal<Map<K, Long>, Map<K, Long>, Mark> groupCount(TraversalFunction<TEdge, K> keyFunction);

	@Override
	public abstract EdgeTraversal<Map<TEdge, Long>, Map<TEdge, Long>, Mark> groupCount(Map<TEdge, Long> map);

	@Override
	public abstract EdgeTraversal<Map<TEdge, Long>, Map<TEdge, Long>, Mark> groupCount();

	@Override
	public abstract EdgeTraversal<?, ?, Mark> sideEffect(SideEffectFunction<TEdge> sideEffectFunction);

	@Override
	public abstract EdgeTraversal<Collection<TEdge>, TEdge, Mark> store(Collection<TEdge> storage);

	@Override
	public abstract <N> EdgeTraversal<Collection<N>, N, Mark> store(Collection<N> storage,
			TraversalFunction<TEdge, N> storageFunction);

	@Override
	public abstract EdgeTraversal<Collection<TEdge>, TEdge, Mark> store();

	@Override
	public abstract <N> EdgeTraversal<Collection<N>, N, Mark> store(TraversalFunction<TEdge, N> storageFunction);

	@Override
	public abstract EdgeTraversal<Table, Table, Mark> table(Table table, Collection<String> stepNames,
			TraversalFunction<?, ?>... columnFunctions);

	@Override
	public abstract EdgeTraversal<Table, Table, Mark> table(Table table, TraversalFunction<?, ?>... columnFunctions);

	@Override
	public abstract EdgeTraversal<Table, Table, Mark> table(TraversalFunction<?, ?>... columnFunctions);

	@Override
	public abstract EdgeTraversal<Table, Table, Mark> table(Table table);

	@Override
	public abstract EdgeTraversal<Table, Table, Mark> table();

	@Override
	public abstract <N> EdgeTraversal<Tree<N>, Tree<N>, Mark> tree(Tree<N> tree);

	@Override
	public abstract EdgeTraversal<?, ?, Mark> identity();

	@Override
	public abstract EdgeTraversal<?, ?, Mark> memoize(String namedStep);

	@Override
	public abstract EdgeTraversal<?, ?, Mark> memoize(String namedStep, Map<?, ?> map);

	@Override
	public abstract EdgeTraversal<?, ?, Mark> order();

	@Override
	public abstract EdgeTraversal<?, ?, Mark> order(TransformPipe.Order order);

	@Override
	public abstract EdgeTraversal<?, ?, Mark> order(Comparator<TEdge> compareFunction);

	@Override
	public abstract EdgeTraversal<?, ?, Mark> as(String name);

	@Override
	public abstract EdgeTraversal<?, ?, Mark> simplePath();

	/**
	 * Fill the provided collection with the objects in the pipeline.
	 *
	 * @param collection
	 *            the collection to fill
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return the collection filled
	 */
	public abstract <N> Collection<N> fill(Collection<N> collection, Class<N> kind);

	@Override
	public abstract EdgeTraversal<?, ?, Mark> gatherScatter();

	/**
	 * Add an AndFilterPipe to the end the Pipeline. If the internal pipes all
	 * yield objects, then the object is not filtered. The provided pipes are
	 * provided the object as their starts.
	 *
	 * @param pipes
	 *            the internal pipes of the AndFilterPipe
	 * @return the extended Pipeline
	 */
	public abstract EdgeTraversal<?, ?, Mark> and(TraversalFunction<TEdge, Traversal<?, ?, ?, ?>>... pipes);

	/**
	 * Add an OrFilterPipe to the end the Pipeline. Will only emit the object if
	 * one or more of the provides pipes yields an object. The provided pipes
	 * are provided the object as their starts.
	 *
	 * @param pipes
	 *            the internal pipes of the OrFilterPipe
	 * @return the extended Pipeline
	 */
	public abstract EdgeTraversal<?, ?, Mark> or(TraversalFunction<TEdge, Traversal<?, ?, ?, ?>>... pipes);

	@Override
	public abstract EdgeTraversal<?, ?, Mark> divert(SideEffectFunction<SideEffect> sideEffect);

	@Override
	public EdgeTraversal<?, ?, Mark> shuffle();

	@SuppressWarnings("unchecked")
	@Override
	public EdgeTraversal<Cap, SideEffect, EdgeTraversal<Cap, SideEffect, Mark>> mark();

	/**
	 * Add an IdPipe to the end of the Pipeline. Emit the id of the incoming
	 * element.
	 *
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<N, ?, ?, Mark> id();

	/**
	 * Add an IdPipe to the end of the Pipeline. Emit the id of the incoming
	 * element.
	 *
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<N, ?, ?, Mark> id(Class<N> type);

	/**
	 * Emit the respective property of the incoming element.
	 *
	 * @param key
	 *            the property key
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<N, ?, ?, Mark> property(String key);

	/**
	 * Emit the respective property of the incoming element.
	 *
	 * @param key
	 *            the property key
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<N, ?, ?, Mark> property(String key, Class<N> type);

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
	 * @param pipes
	 *            the internal pipes of the CopySplitPipe
	 * @return the extended Pipeline
	 */
	public abstract <N> SplitTraversal<Traversal<N, ?, ?, Mark>> copySplit(
			TraversalFunction<TEdge, Traversal<N, ?, ?, ?>>... traversals);
	
	/**
	 * The pipeline loops over the supplied traversal.
	 *
	 * @return the extended Pipeline
	 */
	public abstract <T> EdgeTraversal<?, ?, Mark> loop(TraversalFunction<TEdge, EdgeTraversal<?, ?, ?>> traversal);
	
	/**
	 * The pipeline loops over the supplied traversal up to a maximum depth.
	 *
	 * @param depth The maximum depth to loop to
	 * @return the extended Pipeline
	 */
	public abstract <T> EdgeTraversal<?, ?, Mark> loop(TraversalFunction<TEdge, EdgeTraversal<?, ?, ?>> traversal, int depth);
}
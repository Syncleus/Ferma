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
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.Tokens;
import com.tinkerpop.pipes.transform.TransformPipe;
import com.tinkerpop.pipes.util.structures.Pair;
import com.tinkerpop.pipes.util.structures.Table;
import com.tinkerpop.pipes.util.structures.Tree;

public interface VertexTraversal<Cap, SideEffect, Mark> extends Traversal<TVertex, Cap, SideEffect, Mark> {

	/**
	 * Check if the element has a property with provided key.
	 *
	 * @param key
	 *            the property key to check
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, Mark> has(String key);

	/**
	 * If the incoming element has the provided key/value as check with
	 * .equals(), then let the element pass. If the key is id or label, then use
	 * respect id or label filtering.
	 *
	 * @param key
	 *            the property key to check.
	 * @param value
	 *            the object to filter on (in an OR manner)
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, Mark> has(String key, Object value);

	/**
	 * If the incoming element has the provided key/value as check with
	 * .equals(), then let the element pass. If the key is id or label, then use
	 * respect id or label filtering.
	 *
	 * @param key
	 *            the property key to check
	 * @param compareToken
	 *            the comparison to use
	 * @param value
	 *            the object to filter on
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, Mark> has(String key, Tokens.T compareToken, Object value);

	/**
	 * If the incoming element has the provided key/value as check with
	 * .equals(), then let the element pass. If the key is id or label, then use
	 * respect id or label filtering.
	 *
	 * @param key
	 *            the property key to check
	 * @param predicate
	 *            the comparison to use
	 * @param value
	 *            the object to filter on
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, Mark> has(String key, Predicate predicate, Object value);

	/**
	 * Check if the element does not have a property with provided key.
	 *
	 * @param key
	 *            the property key to check
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, Mark> hasNot(String key);

	/**
	 * If the incoming element has the provided key/value as check with
	 * .equals(), then filter the element. If the key is id or label, then use
	 * respect id or label filtering.
	 *
	 * @param key
	 *            the property key to check
	 * @param value
	 *            the objects to filter on (in an OR manner)
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, Mark> hasNot(String key, Object value);

	/**
	 * If the incoming element has a value that is within the interval value
	 * range specified, then the element is allows to pass. If hte incoming
	 * element's value for the key is null, the element is filtered.
	 *
	 * @param key
	 *            the property key to check
	 * @param startValue
	 *            the start of the interval (inclusive)
	 * @param endValue
	 *            the end of the interval (exclusive)
	 * @return the extended Pipeline
	 */
	public <C> VertexTraversal<?, ?, Mark> interval(String key, Comparable<C> startValue, Comparable<C> endValue);

	/**
	 * Emit the adjacent outgoing vertices of the incoming vertex.
	 *
	 * @param branchFactor
	 *            the number of max adjacent vertices for each incoming vertex
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, Mark> out(int branchFactor, String... labels);

	/**
	 * Emit the adjacent outgoing vertices of the incoming vertex.
	 *
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, Mark> out(String... labels);

	/**
	 * Emit the adjacent incoming vertices for the incoming vertex.
	 *
	 * @param branchFactor
	 *            the number of max adjacent vertices for each incoming vertex
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, Mark> in(int branchFactor, String... labels);

	/**
	 * Emit the adjacent incoming vertices for the incoming vertex.
	 *
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, Mark> in(String... labels);

	/**
	 * Emit both the incoming and outgoing adjacent vertices for the incoming
	 * vertex.
	 *
	 * @param branchFactor
	 *            the number of max adjacent vertices for each incoming vertex
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, Mark> both(int branchFactor, String... labels);

	/**
	 * Emit both the incoming and outgoing adjacent vertices for the incoming
	 * vertex.
	 *
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, Mark> both(String... labels);

	/**
	 * Emit the outgoing edges for the incoming vertex.
	 *
	 * @param branchFactor
	 *            the number of max incident edges for each incoming vertex
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, Mark> outE(int branchFactor, String... labels);

	/**
	 * Emit the outgoing edges for the incoming vertex.
	 *
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, Mark> outE(String... labels);

	/**
	 * Emit the incoming edges for the incoming vertex.
	 *
	 * @param branchFactor
	 *            the number of max incident edges for each incoming vertex
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, Mark> inE(int branchFactor, String... labels);

	/**
	 * Emit the incoming edges for the incoming vertex.
	 *
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, Mark> inE(String... labels);

	/**
	 * Emit both incoming and outgoing edges for the incoming vertex.
	 *
	 * @param branchFactor
	 *            the number of max incident edges for each incoming vertex
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, Mark> bothE(int branchFactor, String... labels);

	/**
	 * Emit both incoming and outgoing edges for the incoming vertex.
	 *
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, Mark> bothE(String... labels);

	/**
	 * Get the next object emitted from the pipeline. If no such object exists,
	 * then a NoSuchElementException is thrown.
	 * 
	 * @param kind
	 *            The type of frame for the element.
	 * @return the next emitted object
	 */
	public <N> N next(Class<N> kind);

	/**
	 * Get the next object emitted from the pipeline. If no such object exists,
	 * then a the default value is returned.
	 * 
	 * @param kind
	 *            The type of frame for the element.
	 * @param defaultValue
	 *            The object to return if no next object exists.
	 * @return the next emitted object
	 */
	public <N> N nextOrDefault(Class<N> kind, N defaultValue);

	/**
	 * Get the next object emitted from the pipeline. If no such object exists a
	 * new vertex is created.
	 * 
	 * @return the next emitted object
	 */
	public TVertex nextOrAdd();

	/**
	 * Get the next object emitted from the pipeline. If no such object exists a
	 * new vertex is created.
	 * 
	 * @param kind
	 *            The type of frame for the element.
	 * @return the next emitted object
	 */
	public <N> N nextOrAdd(Class<N> kind);

	/**
	 * Return the next X objects in the traversal as a list.
	 * 
	 * @param amount
	 *            the number of objects to return
	 * @param kind
	 *            the type of frame to for each element.
	 * @return a list of X objects (if X objects occur)
	 */
	public <N> List<N> next(int amount, Class<N> kind);

	/**
	 * Return an iterator of framed elements.
	 * 
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return An iterator of framed elements.
	 */
	public <N> Iterable<N> frame(Class<N> kind);

	/**
	 * Return a list of all the objects in the pipeline.
	 * 
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return a list of all the objects
	 */
	public <N> List<N> toList(Class<N> kind);

	/**
	 * Emit the incoming vertex, but have other vertex provide an outgoing edge
	 * to incoming vertex.
	 *
	 * @param label
	 *            the edge label
	 * @param namedStep
	 *            the step name that has the other vertex to link to
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<List<TEdge>, TEdge, Mark> linkOut(String label, String namedStep);

	/**
	 * Emit the incoming vertex, but have other vertex provide an incoming edge
	 * to incoming vertex.
	 *
	 * @param label
	 *            the edge label
	 * @param namedStep
	 *            the step name that has the other vertex to link to
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<List<TEdge>, TEdge, Mark> linkIn(String label, String namedStep);

	/**
	 * Emit the incoming vertex, but have other vertex provide an incoming and
	 * outgoing edge to incoming vertex.
	 *
	 * @param label
	 *            the edge label
	 * @param namedStep
	 *            the step name that has the other vertex to link to
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<List<TEdge>, TEdge, Mark> linkBoth(String label, String namedStep);

	/**
	 * Emit the incoming vertex, but have other vertex provide an outgoing edge
	 * to incoming vertex.
	 *
	 * @param label
	 *            the edge label
	 * @param other
	 *            the other vertex
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<List<TEdge>, TEdge, Mark> linkOut(String label, Vertex other);

	/**
	 * Emit the incoming vertex, but have other vertex provide an outgoing edge
	 * to incoming vertex.
	 *
	 * @param label
	 *            the edge label
	 * @param other
	 *            the other vertex
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<List<TEdge>, TEdge, Mark> linkOut(String label, FramedVertex other);

	/**
	 * Emit the incoming vertex, but have other vertex provide an incoming edge
	 * to incoming vertex.
	 *
	 * @param label
	 *            the edge label
	 * @param other
	 *            the other vertex
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<List<TEdge>, TEdge, Mark> linkIn(String label, Vertex other);

	/**
	 * Emit the incoming vertex, but have other vertex provide an incoming and
	 * outgoing edge to incoming vertex.
	 *
	 * @param label
	 *            the edge label
	 * @param other
	 *            the other vertex
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<List<TEdge>, TEdge, Mark> linkBoth(String label, Vertex other);

	/**
	 * Emit the incoming vertex, but have other vertex provide an incoming edge
	 * to incoming vertex.
	 *
	 * @param label
	 *            the edge label
	 * @param other
	 *            the other vertex
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<List<TEdge>, TEdge, Mark> linkIn(String label, FramedVertex other);

	/**
	 * Emit the incoming vertex, but have other vertex provide an incoming and
	 * outgoing edge to incoming vertex.
	 *
	 * @param label
	 *            the edge label
	 * @param other
	 *            the other vertex
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<List<TEdge>, TEdge, Mark> linkBoth(String label, FramedVertex other);

	@Override
	public abstract VertexTraversal<?, ?, Mark> dedup();

	@Override
	public abstract VertexTraversal<?, ?, Mark> dedup(TraversalFunction<TVertex, ?> dedupFunction);

	@Override
	public abstract VertexTraversal<?, ?, Mark> except(Iterable<?> collection);

	/**
	 * Will only emit the object if it is not in the provided collection.
	 *
	 * @param collection
	 *            the collection except from the stream
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<?, ?, Mark> except(FramedVertex... vertices);

	/**
	 * Will only emit the object if it is not equal to any of the objects
	 * contained at the named steps.
	 *
	 * @param namedSteps
	 *            the named steps in the pipeline
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<?, ?, Mark> except(String... namedSteps);

	@Override
	public abstract VertexTraversal<?, ?, Mark> filter(TraversalFunction<TVertex, Boolean> filterFunction);

	@Override
	public abstract VertexTraversal<?, ?, Mark> random(double bias);

	@Override
	public abstract VertexTraversal<?, ?, Mark> range(int low, int high);
	
	@Override
	public abstract VertexTraversal<?, ?, Mark> limit(int limit);

	/**
	 * Will emit the object only if it is in the provided collection.
	 *
	 * @param vertices
	 *            the collection to retain
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<?, ?, Mark> retain(FramedVertex... vertices);

	@Override
	public abstract VertexTraversal<?, ?, Mark> retain(Iterable<?> vertices);

	@Override
	public abstract VertexTraversal<?, ?, Mark> retain(String... namedSteps);

	@Override
	public abstract VertexTraversal<Collection<TVertex>, Collection<TVertex>, Mark> aggregate();

	@Override
	public abstract VertexTraversal<Collection<TVertex>, Collection<TVertex>, Mark> aggregate(Collection<TVertex> aggregate);

	@Override
	public abstract <N> VertexTraversal<Collection<N>, Collection<N>, Mark> aggregate(Collection<TVertex> aggregate,
			TraversalFunction<TVertex, N> aggregateFunction);

	@Override
	public abstract <N> VertexTraversal<Collection<N>, Collection<N>, Mark> aggregate(
			TraversalFunction<TVertex, N> aggregateFunction);

	@Override
	public abstract <K, V> VertexTraversal<Map<K, List<V>>, Map<K, List<V>>, Mark> groupBy(Map<K, List<V>> map,
			TraversalFunction<TVertex, K> keyFunction, TraversalFunction<TVertex, Iterator<V>> valueFunction);

	@Override
	public abstract <K, V> VertexTraversal<Map<K, List<V>>, Map<K, List<V>>, Mark> groupBy(
			TraversalFunction<TVertex, K> keyFunction, TraversalFunction<TVertex, Iterator<V>> valueFunction);

	@Override
	public abstract <K, V, V2> VertexTraversal<Map<K, V2>, Map<K, V2>, Mark> groupBy(Map<K, V2> reduceMap,
			TraversalFunction<TVertex, K> keyFunction, TraversalFunction<TVertex, Iterator<V>> valueFunction,
			TraversalFunction<List<V>, V2> reduceFunction);

	@Override
	public abstract <K, V, V2> VertexTraversal<Map<K, V2>, Map<K, V2>, Mark> groupBy(TraversalFunction<TVertex, K> keyFunction,
			TraversalFunction<TVertex, Iterator<V>> valueFunction, TraversalFunction<List<V>, V2> reduceFunction);

	@Override
	public abstract <K> VertexTraversal<Map<K, Long>, Map<K, Long>, Mark> groupCount(Map<K, Long> map,
			TraversalFunction<TVertex, K> keyFunction, TraversalFunction<Pair<TVertex, Long>, Long> valueFunction);

	@Override
	public abstract <K> VertexTraversal<Map<K, Long>, Map<K, Long>, Mark> groupCount(TraversalFunction<TVertex, K> keyFunction,
			TraversalFunction<Pair<TVertex, Long>, Long> valueFunction);

	@Override
	public abstract <K> VertexTraversal<Map<K, Long>, Map<K, Long>, Mark> groupCount(Map<K, Long> map,
			TraversalFunction<TVertex, K> keyFunction);

	@Override
	public abstract <K> VertexTraversal<Map<K, Long>, Map<K, Long>, Mark> groupCount(TraversalFunction<TVertex, K> keyFunction);

	@Override
	public abstract VertexTraversal<Map<TVertex, Long>, Map<TVertex, Long>, Mark> groupCount(Map<TVertex, Long> map);

	@Override
	public abstract VertexTraversal<Map<TVertex, Long>, Map<TVertex, Long>, Mark> groupCount();

	@Override
	public abstract VertexTraversal<?, ?, Mark> sideEffect(SideEffectFunction<TVertex> sideEffectFunction);

	@Override
	public abstract VertexTraversal<Collection<TVertex>, TVertex, Mark> store(Collection<TVertex> storage);

	@Override
	public abstract <N> VertexTraversal<Collection<N>, N, Mark> store(Collection<N> storage,
			TraversalFunction<TVertex, N> storageFunction);

	@Override
	public abstract VertexTraversal<Collection<TVertex>, TVertex, Mark> store();

	@Override
	public abstract <N> VertexTraversal<Collection<N>, N, Mark> store(TraversalFunction<TVertex, N> storageFunction);

	@Override
	public abstract VertexTraversal<Table, Table, Mark> table(Table table, Collection<String> stepNames,
			TraversalFunction<?, ?>... columnFunctions);

	@Override
	public abstract VertexTraversal<Table, Table, Mark> table(Table table, TraversalFunction<?, ?>... columnFunctions);

	@Override
	public abstract VertexTraversal<Table, Table, Mark> table(TraversalFunction<?, ?>... columnFunctions);

	@Override
	public abstract VertexTraversal<Table, Table, Mark> table(Table table);

	@Override
	public abstract VertexTraversal<Table, Table, Mark> table();

	@Override
	public abstract VertexTraversal<Tree<TVertex>, Tree<TVertex>, Mark> tree();

	@Override
	public abstract <N> VertexTraversal<Tree<N>, Tree<N>, Mark> tree(Tree<N> tree);

	@Override
	public abstract VertexTraversal<?, ?, Mark> identity();

	@Override
	public abstract VertexTraversal<?, ?, Mark> memoize(String namedStep);

	@Override
	public abstract VertexTraversal<?, ?, Mark> memoize(String namedStep, Map<?, ?> map);

	@Override
	public abstract VertexTraversal<?, ?, Mark> order();

	@Override
	public abstract VertexTraversal<?, ?, Mark> order(Comparator<TVertex> compareFunction);

	@Override
	public abstract VertexTraversal<?, ?, Mark> order(TransformPipe.Order order);

	@Override
	public abstract VertexTraversal<?, ?, Mark> order(Tokens.T order);

	@Override
	public abstract VertexTraversal<?, ?, Mark> as(String name);

	@Override
	public abstract VertexTraversal<?, ?, Mark> simplePath();

	/**
	 * Fill the provided collection with the objects in the pipeline.
	 *
	 * @param collection
	 *            the collection to fill
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return the collection filled
	 */
	public abstract <N extends FramedVertex> Collection<N> fill(Collection<N> collection, Class<N> kind);

	@Override
	public abstract VertexTraversal<?, ?, Mark> gatherScatter();

	/**
	 * If the internal pipes all yield objects, then the object is not filtered.
	 * The provided pipes are provided the object as their starts.
	 *
	 * @param pipes
	 *            the internal pipes of the AndFilterPipe
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<?, ?, Mark> and(TraversalFunction<TVertex, Traversal<?, ?, ?, ?>>... traversals);

	/**
	 * Will only emit the object if one or more of the provides pipes yields an
	 * object. The provided pipes are provided the object as their starts.
	 *
	 * @param traversals
	 *            the internal pipes of the OrFilterPipe
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<?, ?, Mark> or(TraversalFunction<TVertex, Traversal<?, ?, ?, ?>>... traversals);

	@Override
	public abstract VertexTraversal<?, ?, Mark> divert(SideEffectFunction<SideEffect> sideEffectFunction);

	@Override
	public VertexTraversal<?, ?, Mark> shuffle();

	@SuppressWarnings("unchecked")
	@Override
	public VertexTraversal<Cap, SideEffect, VertexTraversal<Cap, SideEffect, Mark>> mark();

	/**
	 * Emit the id of the incoming element.
	 *
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<N, ?, ?, Mark> id();

	/**
	 * Emit the id of the incoming element.
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
	 * @param type
	 *            the property type;
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
	 * @param traversals
	 *            the internal pipes of the CopySplitPipe
	 * @return the extended Pipeline
	 */
	public abstract <N> SplitTraversal<Traversal<N, ?, ?, Mark>> copySplit(
			TraversalFunction<TVertex, Traversal<N, ?, ?, ?>>... traversals);

	
	/**
	 * The pipeline loops over the supplied traversal.
	 *
	 * @return the extended Pipeline
	 */
	public abstract <T> VertexTraversal<?, ?, Mark> loop(TraversalFunction<TVertex, VertexTraversal<?, ?, ?>> traversal);
	
	/**
	 * The pipeline loops over the supplied traversal up to a maximum depth.
	 *
	 * @param depth The maximum depth to loop to
	 * @return the extended Pipeline
	 */
	public abstract <T> VertexTraversal<?, ?, Mark> loop(TraversalFunction<TVertex, VertexTraversal<?, ?, ?>> traversal, int depth);
}
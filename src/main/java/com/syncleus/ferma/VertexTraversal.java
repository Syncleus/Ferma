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
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.Tokens;
import com.tinkerpop.pipes.transform.TransformPipe;
import com.tinkerpop.pipes.util.structures.Pair;
import com.tinkerpop.pipes.util.structures.Table;
import com.tinkerpop.pipes.util.structures.Tree;

public interface VertexTraversal<C, S, M> extends Traversal<VertexFrame, C, S, M> {

	/**
	 * Check if the element has a property with provided key.
	 *
	 * @param key
	 *            the property key to check
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, M> has(String key);

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
	public VertexTraversal<?, ?, M> has(String key, Object value);

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
	public VertexTraversal<?, ?, M> has(String key, Tokens.T compareToken, Object value);

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
	public VertexTraversal<?, ?, M> has(String key, Predicate predicate, Object value);

	/**
	 * Check if the element does not have a property with provided key.
	 *
	 * @param key
	 *            the property key to check
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, M> hasNot(String key);

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
	public VertexTraversal<?, ?, M> hasNot(String key, Object value);

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
	public <C> VertexTraversal<?, ?, M> interval(String key, Comparable<C> startValue, Comparable<C> endValue);

	/**
	 * Emit the adjacent outgoing vertices of the incoming vertex.
	 *
	 * @param branchFactor
	 *            the number of max adjacent vertices for each incoming vertex
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, M> out(int branchFactor, String... labels);

	/**
	 * Emit the adjacent outgoing vertices of the incoming vertex.
	 *
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, M> out(String... labels);

	/**
	 * Emit the adjacent incoming vertices for the incoming vertex.
	 *
	 * @param branchFactor
	 *            the number of max adjacent vertices for each incoming vertex
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, M> in(int branchFactor, String... labels);

	/**
	 * Emit the adjacent incoming vertices for the incoming vertex.
	 *
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, M> in(String... labels);

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
	public VertexTraversal<?, ?, M> both(int branchFactor, String... labels);

	/**
	 * Emit both the incoming and outgoing adjacent vertices for the incoming
	 * vertex.
	 *
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public VertexTraversal<?, ?, M> both(String... labels);

	/**
	 * Emit the outgoing edges for the incoming vertex.
	 *
	 * @param branchFactor
	 *            the number of max incident edges for each incoming vertex
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, M> outE(int branchFactor, String... labels);

	/**
	 * Emit the outgoing edges for the incoming vertex.
	 *
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, M> outE(String... labels);

	/**
	 * Emit the incoming edges for the incoming vertex.
	 *
	 * @param branchFactor
	 *            the number of max incident edges for each incoming vertex
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, M> inE(int branchFactor, String... labels);

	/**
	 * Emit the incoming edges for the incoming vertex.
	 *
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, M> inE(String... labels);

	/**
	 * Emit both incoming and outgoing edges for the incoming vertex.
	 *
	 * @param branchFactor
	 *            the number of max incident edges for each incoming vertex
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, M> bothE(int branchFactor, String... labels);

	/**
	 * Emit both incoming and outgoing edges for the incoming vertex.
	 *
	 * @param labels
	 *            the edge labels to traverse
	 * @return the extended Pipeline
	 */
	public EdgeTraversal<?, ?, M> bothE(String... labels);

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
	public <N> N nextExplicit(Class<N> kind);

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
	 * Get the next object emitted from the pipeline. If no such object exists,
	 * then a the default value is returned.
	 *
	 * This will bypass the default type resolution and use the untyped resolver
	 * instead. This method is useful for speeding up a look up when type resolution
	 * isn't required.
	 *
	 * @param kind
	 *            The type of frame for the element.
	 * @param defaultValue
	 *            The object to return if no next object exists.
	 * @return the next emitted object
	 */
	public <N> N nextOrDefaultExplicit(Class<N> kind, N defaultValue);

	/**
	 * Get the next object emitted from the pipeline. If no such object exists a
	 * new vertex is created.
	 * 
	 * @return the next emitted object
	 */
	public VertexFrame nextOrAdd();

	/**
	 * Get the next object emitted from the pipeline. If no such object exists a
	 * new vertex is created.
	 *
	 * This will bypass the default type resolution and use the untyped resolver
	 * instead. This method is useful for speeding up a look up when type resolution
	 * isn't required.
	 * 
	 * @param kind
	 *            The type of frame for the element.
	 * @return the next emitted object
	 */
	public <N> N nextOrAddExplicit(Class<N> kind);

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
	public <N> List<N> nextExplicit(int amount, Class<N> kind);

	/**
	 * Return an iterator of framed elements.
	 * 
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return An iterator of framed elements.
	 */
	public <N> Iterable<N> frame(Class<N> kind);

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
	public <N> Iterable<? extends N> frameExplicit(Class<N> kind);

	/**
	 * Return a list of all the objects in the pipeline.
	 * 
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return a list of all the objects
	 */
	public <N> List<? extends N> toList(Class<N> kind);

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
	public <N> List<? extends N> toListExplicit(Class<N> kind);

	/**
	 * Return a set of all the objects in the pipeline.
	 *
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return a set of all the objects
	 */
	public <N> Set<? extends N> toSet(Class<N> kind);

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
	public <N> Set<? extends N> toSetExplicit(Class<N> kind);

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
	public abstract VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkOut(String label, String namedStep);

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
	public abstract VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkIn(String label, String namedStep);

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
	public abstract VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkBoth(String label, String namedStep);

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
	public abstract VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkOut(String label, Vertex other);

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
	public abstract VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkOut(String label, VertexFrame other);

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
	public abstract VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkIn(String label, Vertex other);

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
	public abstract VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkBoth(String label, Vertex other);

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
	public abstract VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkIn(String label, VertexFrame other);

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
	public abstract VertexTraversal<List<EdgeFrame>, EdgeFrame, M> linkBoth(String label, VertexFrame other);

	@Override
	public abstract VertexTraversal<?, ?, M> dedup();

	@Override
	public abstract VertexTraversal<?, ?, M> dedup(TraversalFunction<VertexFrame, ?> dedupFunction);

	@Override
	public abstract VertexTraversal<?, ?, M> except(Iterable<?> collection);

	/**
	 * Will only emit the object if it is not in the provided collection.
	 *
	 * @param vertices
	 *            the collection except from the stream
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<?, ?, M> except(VertexFrame... vertices);

	/**
	 * Will only emit the object if it is not equal to any of the objects
	 * contained at the named steps.
	 *
	 * @param namedSteps
	 *            the named steps in the pipeline
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<?, ?, M> except(String... namedSteps);

	@Override
	public abstract VertexTraversal<?, ?, M> filter(TraversalFunction<VertexFrame, Boolean> filterFunction);

	@Override
	public abstract VertexTraversal<?, ?, M> random(double bias);

	@Override
	public abstract VertexTraversal<?, ?, M> range(int low, int high);
	
	@Override
	public abstract VertexTraversal<?, ?, M> limit(int limit);

	/**
	 * Will emit the object only if it is in the provided collection.
	 *
	 * @param vertices
	 *            the collection to retain
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<?, ?, M> retain(VertexFrame... vertices);

	@Override
	public abstract VertexTraversal<?, ?, M> retain(Iterable<?> vertices);

	@Override
	public abstract VertexTraversal<?, ?, M> retain(String... namedSteps);

	@Override
	public abstract VertexTraversal<Collection<? extends VertexFrame>, Collection<? extends VertexFrame>, M> aggregate();

	@Override
	public abstract VertexTraversal<Collection<? extends VertexFrame>, Collection<? extends VertexFrame>, M> aggregate(Collection<? super VertexFrame> aggregate);

	@Override
	public abstract <N> VertexTraversal<Collection<? extends N>, Collection<? extends N>, M> aggregate(Collection<? super N> aggregate, TraversalFunction<VertexFrame, ? extends N> aggregateFunction);

	@Override
	public abstract <N> VertexTraversal<Collection<? extends N>, Collection<? extends N>, M> aggregate(TraversalFunction<VertexFrame, ? extends N> aggregateFunction);

	@Override
	public abstract <K, V> VertexTraversal<Map<K, List<V>>, Map<K, List<V>>, M> groupBy(Map<K, List<V>> map, TraversalFunction<VertexFrame, K> keyFunction, TraversalFunction<VertexFrame, Iterator<V>> valueFunction);

	@Override
	public abstract <K, V> VertexTraversal<Map<K, List<V>>, Map<K, List<V>>, M> groupBy(TraversalFunction<VertexFrame, K> keyFunction, TraversalFunction<VertexFrame, Iterator<V>> valueFunction);

	@Override
	public abstract <K, V, V2> VertexTraversal<Map<K, V2>, Map<K, V2>, M> groupBy(Map<K, V2> reduceMap, TraversalFunction<VertexFrame, K> keyFunction, TraversalFunction<VertexFrame, Iterator<V>> valueFunction, TraversalFunction<List<V>, V2> reduceFunction);

	@Override
	public abstract <K, V, V2> VertexTraversal<Map<K, V2>, Map<K, V2>, M> groupBy(TraversalFunction<VertexFrame, K> keyFunction, TraversalFunction<VertexFrame, Iterator<V>> valueFunction, TraversalFunction<List<V>, V2> reduceFunction);

	@Override
	public abstract <K> VertexTraversal<Map<K, Long>, Map<K, Long>, M> groupCount(Map<K, Long> map, TraversalFunction<VertexFrame, K> keyFunction, TraversalFunction<Pair<VertexFrame, Long>, Long> valueFunction);

	@Override
	public abstract <K> VertexTraversal<Map<K, Long>, Map<K, Long>, M> groupCount(TraversalFunction<VertexFrame, K> keyFunction, TraversalFunction<Pair<VertexFrame, Long>, Long> valueFunction);

	@Override
	public abstract <K> VertexTraversal<Map<K, Long>, Map<K, Long>, M> groupCount(Map<K, Long> map, TraversalFunction<VertexFrame, K> keyFunction);

	@Override
	public abstract <K> VertexTraversal<Map<K, Long>, Map<K, Long>, M> groupCount(TraversalFunction<VertexFrame, K> keyFunction);

	@Override
	public abstract VertexTraversal<Map<VertexFrame, Long>, Map<VertexFrame, Long>, M> groupCount(Map<VertexFrame, Long> map);

	@Override
	public abstract VertexTraversal<Map<VertexFrame, Long>, Map<VertexFrame, Long>, M> groupCount();

	@Override
	public abstract VertexTraversal<?, ?, M> sideEffect(SideEffectFunction<VertexFrame> sideEffectFunction);

	@Override
	public abstract <N> VertexTraversal<Collection<N>, N, M> store(Collection<N> storage);

	@Override
	public abstract <N> VertexTraversal<Collection<N>, N, M> store(Collection<N> storage,
			TraversalFunction<VertexFrame, N> storageFunction);

	@Override
	public abstract VertexTraversal<Collection<VertexFrame>, VertexFrame, M> store();

	@Override
	public abstract <N> VertexTraversal<Collection<N>, N, M> store(TraversalFunction<VertexFrame, N> storageFunction);

	@Override
	public abstract VertexTraversal<Table, Table, M> table(Table table, Collection<String> stepNames,
			TraversalFunction<?, ?>... columnFunctions);

	@Override
	public abstract VertexTraversal<Table, Table, M> table(Table table, TraversalFunction<?, ?>... columnFunctions);

	@Override
	public abstract VertexTraversal<Table, Table, M> table(TraversalFunction<?, ?>... columnFunctions);

	@Override
	public abstract VertexTraversal<Table, Table, M> table(Table table);

	@Override
	public abstract VertexTraversal<Table, Table, M> table();

	@Override
	public abstract VertexTraversal<Tree<VertexFrame>, Tree<VertexFrame>, M> tree();

	@Override
	public abstract <N> VertexTraversal<Tree<N>, Tree<N>, M> tree(Tree<N> tree);

	@Override
	public abstract VertexTraversal<?, ?, M> identity();

	@Override
	public abstract VertexTraversal<?, ?, M> memoize(String namedStep);

	@Override
	public abstract VertexTraversal<?, ?, M> memoize(String namedStep, Map<?, ?> map);

	@Override
	public abstract VertexTraversal<?, ?, M> order();

	@Override
	public abstract VertexTraversal<?, ?, M> order(Comparator<VertexFrame> compareFunction);

	@Override
	public abstract VertexTraversal<?, ?, M> order(TransformPipe.Order order);

	@Override
	public abstract VertexTraversal<?, ?, M> order(Tokens.T order);

	@Override
	public abstract VertexTraversal<?, ?, M> as(String name);

	@Override
	public abstract VertexTraversal<?, ?, M> simplePath();

	/**
	 * Fill the provided collection with the objects in the pipeline.
	 *
	 * @param collection
	 *            the collection to fill
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return the collection filled
	 */
	public abstract <N extends VertexFrame> Collection<N> fill(Collection<? super N> collection, Class<N> kind);

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
	public abstract <N extends VertexFrame> Collection<N> fillExplicit(Collection<? super N> collection, Class<N> kind);

	@Override
	public abstract VertexTraversal<?, ?, M> gatherScatter();

	/**
	 * If the internal pipes all yield objects, then the object is not filtered.
	 * The provided pipes are provided the object as their starts.
	 *
	 * @param traversals
	 *            the internal pipes of the AndFilterPipe
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<?, ?, M> and(TraversalFunction<VertexFrame, Traversal<?, ?, ?, ?>>... traversals);

	/**
	 * Will only emit the object if one or more of the provides pipes yields an
	 * object. The provided pipes are provided the object as their starts.
	 *
	 * @param traversals
	 *            the internal pipes of the OrFilterPipe
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<?, ?, M> or(TraversalFunction<VertexFrame, Traversal<?, ?, ?, ?>>... traversals);

	@Override
	public abstract VertexTraversal<?, ?, M> divert(SideEffectFunction<S> sideEffectFunction);

	@Override
	public VertexTraversal<?, ?, M> shuffle();

	@Override
	public VertexTraversal<C, S, ? extends VertexTraversal<C, S, M>> mark();

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
	public abstract <N> SplitTraversal<? extends Traversal<N, ?, ?, M>> copySplit(TraversalFunction<VertexFrame, ? extends Traversal<N, ?, ?, ?>>... traversals);

	
	/**
	 * The pipeline loops over the supplied traversal.
	 *
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<?, ?, M> loop(TraversalFunction<VertexFrame, ? extends VertexTraversal<?, ?, ?>> traversal);
	
	/**
	 * The pipeline loops over the supplied traversal up to a maximum depth.
	 *
	 * @param depth The maximum depth to loop to
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<?, ?, M> loop(TraversalFunction<VertexFrame, ? extends VertexTraversal<?, ?, ?>> traversal, int depth);
}
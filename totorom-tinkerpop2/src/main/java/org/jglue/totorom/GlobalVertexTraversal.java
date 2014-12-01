/**
 * Copyright 2014-Infinity Bryn Cooke
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 * This project is derived from code in the Tinkerpop project under the following licenses:
 *
 * Tinkerpop3
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Tinkerpop2
 * Copyright (c) 2009-Infinity, TinkerPop [http://tinkerpop.com]
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the TinkerPop nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL TINKERPOP BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.jglue.totorom;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.KeyIndexableGraph;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.gremlin.Tokens.T;
import com.tinkerpop.pipes.transform.TransformPipe.Order;
import com.tinkerpop.pipes.util.structures.Table;
import com.tinkerpop.pipes.util.structures.Tree;

/**
 * Specialized global vertex traversal that bypasses gremlin pipeline for simple
 * key value lookups. As soon as a more complex traversal is detected then it delegates toa full gremlin pipeline.
 * 
 * @author bryn
 *
 */
public class GlobalVertexTraversal implements VertexTraversal {

	private FramedGraph graph;
	private Graph delegate;
	private String key;
	private Object value;
	private VertexTraversal traversal;
	private Iterator iterator;

	public GlobalVertexTraversal(FramedGraph graph, Graph delegate) {
		this.graph = graph;
		this.delegate = delegate;

	}

	/**
	 * We've dropped out of what can be optimized. Time to create a propper
	 * traversal.
	 * 
	 * @return
	 */
	private VertexTraversal delegate() {
		if (traversal == null) {

			if (key != null) {
				traversal = new TraversalImpl(graph, delegate).V().has(key, value);
			} else {
				traversal = new TraversalImpl(graph, delegate).V();
			}
		}
		return traversal;
	}

	/**
	 * The traversal is still has a simple query, but is passed on to other
	 * gremlin pipeline steps. It is safe to use the simple iterator.
	 * 
	 * @return
	 */
	private VertexTraversal simpleDelegate() {
		if (traversal == null) {
			traversal = new TraversalImpl(graph, simpleIterator()).castToVertices();
		}
		return traversal;
	}

	/**
	 * Used for simple iteration, uses the key index if available, but will defer to graph query if not.
	 * 
	 * @return
	 */
	private Iterator simpleIterator() {
		if (iterator == null) {
			
			if (key != null) {
				if(delegate instanceof TinkerGraph) {
					//Tinker graph will do it's own check to see if it supports the key
					iterator = delegate.getVertices(key, value).iterator();
				}
				else if (delegate instanceof KeyIndexableGraph) {
					if (((KeyIndexableGraph) delegate).getIndexedKeys(Vertex.class).contains(key)) {
						// This graph supports lookups for this key
						iterator = delegate.getVertices(key, value).iterator();
					} else {
						// This graph does not support lookup of this key, but
						// it may still be supported via the query interface.
						iterator = delegate.query().has(key, value).vertices().iterator();
					}
				} else {

					// This graph does not support lookup of this key, but it
					// may still be supported via the query interface.
					iterator = delegate.query().has(key, value).vertices().iterator();

				}
			} else {
				//There is no key so it is a full traversal.
				iterator = delegate.getVertices().iterator();
			}
		}
		return iterator;
	}

	public VertexTraversal has(String key) {
		return delegate().has(key);
	}

	public Iterator iterator() {
		return Iterators.transform(simpleIterator(), new Function() {

			public Object apply(Object e) {
				return graph.frameElement((Element) e, FramedVertex.class);
			}
		});
	}

	public VertexTraversal V() {
		return this;
	}

	public VertexTraversal has(String key, Object value) {
		if (this.key == null) {
			this.key = key;
			this.value = value;
			return this;
		} else {
			return delegate().has(key, value);
		}

	}

	public boolean hasNext() {
		return simpleIterator().hasNext();
	}

	public EdgeTraversal E() {
		return simpleDelegate().E();
	}

	public VertexTraversal v(Object... ids) {
		return simpleDelegate().v(ids);
	}

	public VertexTraversal has(String key, T compareToken, Object value) {
		return delegate().has(key, compareToken, value);
	}

	public VertexTraversal v(Collection ids) {
		return simpleDelegate().v(ids);
	}

	public EdgeTraversal e(Object... ids) {
		return simpleDelegate().e(ids);
	}

	public VertexTraversal has(String key, Predicate predicate, Object value) {
		return delegate().has(key, predicate, value);
	}

	public EdgeTraversal e(Collection ids) {
		return simpleDelegate().e(ids);
	}

	public void iterate() {
		Iterators.size(simpleIterator());
	}

	public Traversal map(String... keys) {
		return simpleDelegate().map(keys);
	}

	public VertexTraversal hasNot(String key) {
		return delegate().hasNot(key);
	}

	public VertexTraversal hasNot(String key, Object value) {
		return delegate().hasNot(key, value);
	}

	public VertexTraversal interval(String key, Comparable startValue, Comparable endValue) {
		return delegate().interval(key, startValue, endValue);
	}

	public VertexTraversal out(int branchFactor, String... labels) {
		return simpleDelegate().out(branchFactor, labels);
	}

	public VertexTraversal out(String... labels) {
		return simpleDelegate().out(labels);
	}

	public VertexTraversal in(int branchFactor, String... labels) {
		return simpleDelegate().in(branchFactor, labels);
	}

	public VertexTraversal in(String... labels) {
		return simpleDelegate().in(labels);
	}

	public VertexTraversal both(int branchFactor, String... labels) {
		return simpleDelegate().both(branchFactor, labels);
	}

	public VertexTraversal both(String... labels) {
		return simpleDelegate().both(labels);
	}

	public EdgeTraversal outE(int branchFactor, String... labels) {
		return simpleDelegate().outE(branchFactor, labels);
	}

	public EdgeTraversal outE(String... labels) {
		return simpleDelegate().outE(labels);
	}

	public EdgeTraversal inE(int branchFactor, String... labels) {
		return simpleDelegate().inE(branchFactor, labels);
	}

	public EdgeTraversal inE(String... labels) {
		return simpleDelegate().inE(labels);
	}

	public EdgeTraversal bothE(int branchFactor, String... labels) {
		return simpleDelegate().bothE(branchFactor, labels);
	}

	public EdgeTraversal bothE(String... labels) {
		return simpleDelegate().bothE(labels);
	}

	public FramedVertex next(Class kind) {
		return graph.frameElement((Element) simpleIterator().next(), kind);
	}

	public FramedVertex nextOrDefault(Class kind, FramedVertex defaultValue) {
		if(simpleIterator().hasNext()) {
			return next(kind);
		}
		else {
			return defaultValue;
		}
	}

	public TVertex nextOrAdd() {
		return delegate().nextOrAdd();
	}

	public FramedVertex nextOrAdd(Class kind) {
		return delegate().nextOrAdd(kind);
	}

	public List next(int amount, Class kind) {
		return delegate().next(amount, kind);
	}

	public Iterable frame(final Class kind) {
		final Iterator transform = Iterators.transform(simpleIterator(), new Function() {

			public Object apply(Object e) {
				return graph.frameElement((Element) e, kind);
			}
		});
		return new Iterable<T>() {

			@Override
			public Iterator<T> iterator() {
				return transform;
			}
		};
	}

	public List toList(Class kind) {
		return simpleDelegate().toList(kind);
	}

	public Set toSet(Class kind) {
		return simpleDelegate().toSet(kind);
	}

	public VertexTraversal linkOut(String label, String namedStep) {
		return simpleDelegate().linkOut(label, namedStep);
	}

	public VertexTraversal linkIn(String label, String namedStep) {
		return simpleDelegate().linkIn(label, namedStep);
	}

	public VertexTraversal linkBoth(String label, String namedStep) {
		return simpleDelegate().linkBoth(label, namedStep);
	}

	public VertexTraversal linkOut(String label, Vertex other) {
		return simpleDelegate().linkOut(label, other);
	}

	public VertexTraversal linkOut(String label, FramedVertex other) {
		return simpleDelegate().linkOut(label, other);
	}

	public VertexTraversal linkIn(String label, Vertex other) {
		return delegate().linkIn(label, other);
	}

	public VertexTraversal linkBoth(String label, Vertex other) {
		return simpleDelegate().linkBoth(label, other);
	}

	public VertexTraversal linkIn(String label, FramedVertex other) {
		return simpleDelegate().linkIn(label, other);
	}

	public VertexTraversal linkBoth(String label, FramedVertex other) {
		return simpleDelegate().linkBoth(label, other);
	}

	public VertexTraversal dedup() {
		return simpleDelegate().dedup();
	}

	public VertexTraversal dedup(TraversalFunction dedupFunction) {
		return simpleDelegate().dedup(dedupFunction);
	}

	public VertexTraversal except(Iterable collection) {
		return simpleDelegate().except(collection);
	}

	public VertexTraversal except(FramedVertex... vertices) {
		return simpleDelegate().except(vertices);
	}

	public VertexTraversal except(String... namedSteps) {
		return simpleDelegate().except(namedSteps);
	}

	public VertexTraversal filter(TraversalFunction filterFunction) {
		return simpleDelegate().filter(filterFunction);
	}

	public VertexTraversal random(double bias) {
		return simpleDelegate().random(bias);
	}

	public VertexTraversal range(int low, int high) {
		return simpleDelegate().range(low, high);
	}

	public VertexTraversal limit(int limit) {
		return simpleDelegate().limit(limit);
	}

	public VertexTraversal retain(FramedVertex... vertices) {
		return simpleDelegate().retain(vertices);
	}

	public VertexTraversal retain(Iterable vertices) {
		return simpleDelegate().retain(vertices);
	}

	public VertexTraversal retain(String... namedSteps) {
		return simpleDelegate().retain(namedSteps);
	}

	public VertexTraversal aggregate() {
		return simpleDelegate().aggregate();
	}

	public VertexTraversal aggregate(Collection aggregate) {
		return simpleDelegate().aggregate(aggregate);
	}

	public VertexTraversal aggregate(Collection aggregate, TraversalFunction aggregateFunction) {
		return simpleDelegate().aggregate(aggregate, aggregateFunction);
	}

	public VertexTraversal aggregate(TraversalFunction aggregateFunction) {
		return simpleDelegate().aggregate(aggregateFunction);
	}

	public VertexTraversal groupBy(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return simpleDelegate().groupBy(map, keyFunction, valueFunction);
	}

	public VertexTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return simpleDelegate().groupBy(keyFunction, valueFunction);
	}

	public VertexTraversal groupBy(Map reduceMap, TraversalFunction keyFunction, TraversalFunction valueFunction,
			TraversalFunction reduceFunction) {
		return simpleDelegate().groupBy(reduceMap, keyFunction, valueFunction, reduceFunction);
	}

	public VertexTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction,
			TraversalFunction reduceFunction) {
		return simpleDelegate().groupBy(keyFunction, valueFunction, reduceFunction);
	}

	public VertexTraversal groupCount(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return simpleDelegate().groupCount(map, keyFunction, valueFunction);
	}

	public VertexTraversal groupCount(TraversalFunction keyFunction, TraversalFunction valueFunction) {
		return simpleDelegate().groupCount(keyFunction, valueFunction);
	}

	public VertexTraversal groupCount(Map map, TraversalFunction keyFunction) {
		return simpleDelegate().groupCount(map, keyFunction);
	}

	public VertexTraversal groupCount(TraversalFunction keyFunction) {
		return simpleDelegate().groupCount(keyFunction);
	}

	public VertexTraversal groupCount(Map map) {
		return simpleDelegate().groupCount(map);
	}

	public VertexTraversal groupCount() {
		return simpleDelegate().groupCount();
	}

	public VertexTraversal sideEffect(SideEffectFunction sideEffectFunction) {
		return simpleDelegate().sideEffect(sideEffectFunction);
	}

	public VertexTraversal store(Collection storage) {
		return simpleDelegate().store(storage);
	}

	public VertexTraversal store(Collection storage, TraversalFunction storageFunction) {
		return simpleDelegate().store(storage, storageFunction);
	}

	public VertexTraversal store() {
		return simpleDelegate().store();
	}

	public VertexTraversal store(TraversalFunction storageFunction) {
		return simpleDelegate().store(storageFunction);
	}

	public VertexTraversal table(Table table, Collection stepNames, TraversalFunction... columnFunctions) {
		return simpleDelegate().table(table, stepNames, columnFunctions);
	}

	public VertexTraversal table(Table table, TraversalFunction... columnFunctions) {
		return simpleDelegate().table(table, columnFunctions);
	}

	public VertexTraversal table(TraversalFunction... columnFunctions) {
		return simpleDelegate().table(columnFunctions);
	}

	public VertexTraversal table(Table table) {
		return simpleDelegate().table(table);
	}

	public VertexTraversal table() {
		return simpleDelegate().table();
	}

	public VertexTraversal tree() {
		return simpleDelegate().tree();
	}

	public VertexTraversal tree(Tree tree) {
		return simpleDelegate().tree(tree);
	}

	public VertexTraversal identity() {
		return simpleDelegate().identity();
	}

	public VertexTraversal memoize(String namedStep) {
		return simpleDelegate().memoize(namedStep);
	}

	public VertexTraversal memoize(String namedStep, Map map) {
		return simpleDelegate().memoize(namedStep, map);
	}

	public VertexTraversal order() {
		return simpleDelegate().order();
	}

	public VertexTraversal order(Comparator compareFunction) {
		return simpleDelegate().order(compareFunction);
	}

	public VertexTraversal order(Order order) {
		return simpleDelegate().order(order);
	}

	public VertexTraversal order(T order) {
		return simpleDelegate().order(order);
	}

	public VertexTraversal as(String name) {
		return simpleDelegate().as(name);
	}

	public VertexTraversal simplePath() {
		return simpleDelegate().simplePath();
	}

	public Collection fill(Collection collection, Class kind) {
		return simpleDelegate().fill(collection, kind);
	}

	public VertexTraversal gatherScatter() {
		return simpleDelegate().gatherScatter();
	}

	public VertexTraversal and(TraversalFunction... traversals) {
		return simpleDelegate().and(traversals);
	}

	public VertexTraversal or(TraversalFunction... traversals) {
		return simpleDelegate().or(traversals);
	}

	public VertexTraversal divert(SideEffectFunction sideEffectFunction) {
		return simpleDelegate().divert(sideEffectFunction);
	}

	public VertexTraversal shuffle() {
		return simpleDelegate().shuffle();
	}

	public Object back() {
		return simpleDelegate().back();
	}

	public VertexTraversal mark() {
		return simpleDelegate().mark();
	}

	public Traversal id() {
		return simpleDelegate().id();
	}

	public Traversal id(Class type) {
		return simpleDelegate().id(type);
	}

	public Traversal property(String key) {
		return simpleDelegate().property(key);
	}

	public Traversal property(String key, Class type) {
		return simpleDelegate().property(key, type);
	}

	public Traversal path(TraversalFunction... pathFunctions) {
		return simpleDelegate().path(pathFunctions);
	}

	public void removeAll() {
		simpleDelegate().removeAll();
	}

	public SplitTraversal copySplit(TraversalFunction... traversals) {
		return simpleDelegate().copySplit(traversals);
	}

	public Traversal select(Collection stepNames, TraversalFunction... columnFunctions) {
		return simpleDelegate().select(stepNames, columnFunctions);
	}

	public VertexTraversal loop(TraversalFunction traversal) {
		return simpleDelegate().loop(traversal);
	}

	public VertexTraversal loop(TraversalFunction traversal, int depth) {
		return simpleDelegate().loop(traversal, depth);
	}

	public Traversal select(TraversalFunction... columnFunctions) {
		return simpleDelegate().select(columnFunctions);
	}

	public Traversal select() {
		return simpleDelegate().select();
	}

	public Object cap() {
		return simpleDelegate().cap();
	}

	public Traversal transform(TraversalFunction function) {
		return simpleDelegate().transform(function);
	}

	public Traversal start(Object object) {
		return simpleDelegate().start(object);
	}

	public VertexTraversal start(FramedVertex object) {
		return simpleDelegate().start(object);
	}

	public EdgeTraversal start(FramedEdge object) {
		return simpleDelegate().start(object);
	}

	public long count() {
		return Iterators.size(simpleIterator());
	}

	public Object next() {
		return next(TVertex.class);
	}

	public Object nextOrDefault(Object defaultValue) {
		return nextOrDefault(TVertex.class, (FramedVertex) defaultValue);
	}

	public List next(int number) {
		return simpleDelegate().next(number);
	}

	public List toList() {
		return simpleDelegate().toList();
	}

	public Set toSet() {
		return simpleDelegate().toSet();
	}

	public Traversal enablePath() {
		return simpleDelegate().enablePath();
	}

	public Traversal optimize(boolean optimize) {
		return simpleDelegate().optimize(optimize);
	}

	public Collection fill(Collection collection) {
		return simpleDelegate().fill(collection);
	}

	public Object optional() {
		return simpleDelegate().optional();
	}

	public EdgeTraversal idEdge(Graph graph) {
		return simpleDelegate().idEdge(graph);
	}

	public VertexTraversal idVertex(Graph graph) {
		return simpleDelegate().idVertex(graph);
	}

}

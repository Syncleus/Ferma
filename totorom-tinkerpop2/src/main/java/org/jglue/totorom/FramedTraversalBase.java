package org.jglue.totorom;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.gremlin.Tokens;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.transform.TransformPipe.Order;
import com.tinkerpop.pipes.util.structures.Table;
import com.tinkerpop.pipes.util.structures.Tree;

public abstract class FramedTraversalBase<T> implements FramedTraversal<T> {

	

	protected abstract FramedGraph graph();

	protected abstract GremlinPipeline pipeline();

	@Override
	public FramedVertexTraversal V() {
		return graph().V();
	}

	@Override
	public FramedEdgeTraversal E() {
		return graph().E();
	}

	@Override
	public FramedVertexTraversal v(Object... ids) {
		return graph().v(ids);
	}

	@Override
	public FramedEdgeTraversal e(Object... ids) {
		return graph().e(ids);
	}

	@Override
	public long count() {
		return pipeline().count();
	}

	@Override
	public FramedTraversal as(String name) {
		pipeline().as(name);
		return this;
	}

	@Override
	public void iterate() {
		pipeline().iterate();
	}

	public FramedTraversal has(String key) {
		pipeline().has(key);
		return this;
	}

	public FramedTraversal has(String key, Object value) {
		pipeline().has(key, value);
		return this;
	}

	public FramedTraversal has(String key, Tokens.T compareToken, Object value) {
		pipeline().has(key, compareToken, value);
		return this;
	}

	FramedTraversal has(String key, Predicate predicate, Object value) {
		pipeline().has(key, predicate, value);
		return this;
	}

	FramedTraversal hasNot(String key) {
		pipeline().hasNot(key);
		return this;
	}

	FramedTraversal hasNot(String key, Object value) {
		pipeline().hasNot(key, value);
		return this;
	}

	public FramedTraversal interval(String key, Comparable startValue, Comparable endValue) {
		pipeline().interval(key, startValue, endValue);
		return this;
	}

	@Override
	public FramedTraversal identity() {
		pipeline()._();
		return this;
	}

	public FramedTraversal except(Collection collection) {
		pipeline().except(collection);
		return this;
	}

	@Override
	public FramedTraversal map(String... keys) {
		pipeline().map(keys);
		return this;
	}

	@Override
	public FramedTraversal property(String key) {
		pipeline().property(key);
		return this;
	}

	@Override
	public FramedTraversal step(PipeFunction function) {
		pipeline().step(function);
		return this;
	}

	@Override
	public FramedTraversal step(Pipe pipe) {
		pipeline().step(pipe);
		return this;
	}

	@Override
	public FramedTraversal copySplit(Pipe... pipes) {
		pipeline().copySplit(pipes);
		return this;
	}

	@Override
	public FramedTraversal exhaustMerge() {
		pipeline().exhaustMerge();
		return this;
	}

	@Override
	public FramedTraversal fairMerge() {
		pipeline().fairMerge();
		return this;
	}

	@Override
	public FramedTraversal ifThenElse(PipeFunction ifFunction, PipeFunction thenFunction, PipeFunction elseFunction) {
		pipeline().ifThenElse(ifFunction, thenFunction, elseFunction);
		return this;
	}



	@Override
	public FramedTraversal loop(String namedStep, PipeFunction whileFunction) {
		pipeline().loop(namedStep, whileFunction);
		return this;
	}



	@Override
	public FramedTraversal loop(String namedStep, PipeFunction whileFunction, PipeFunction emitFunction) {
		pipeline().loop(namedStep, whileFunction, emitFunction);
		return this;
	}

	@Override
	public FramedTraversal and(Pipe... pipes) {
		pipeline().and(pipes);
		return this;
	}

	

	@Override
	public FramedTraversal back(String namedStep) {
		pipeline().back(namedStep);
		return this;
	}

	@Override
	public FramedTraversal dedup() {
		pipeline().dedup();
		return this;
	}

	@Override
	public FramedTraversal dedup(PipeFunction dedupFunction) {
		pipeline().dedup(dedupFunction);
		return this;
	}

	@Override
	public FramedTraversal except(String... namedSteps) {
		pipeline().except(namedSteps);
		return this;
	}

	@Override
	public FramedTraversal filter(PipeFunction filterFunction) {
		pipeline().filter(filterFunction);
		return this;
	}

	@Override
	public FramedTraversal or(Pipe... pipes) {
		pipeline().or(pipes);
		return this;
	}

	@Override
	public FramedTraversal random(Double bias) {
		pipeline().random(bias);
		return this;
	}

	@Override
	public FramedTraversal range(int low, int high) {
		pipeline().range(low, high);
		return this;
	}

	@Override
	public FramedTraversal retain(Collection collection) {
		HashSet unwrapped = new HashSet(Collections2.transform(collection, new Function<Object, Object>() {

			@Override
			public Object apply(Object o) {
				if(o instanceof FramedVertex) {
					return ((FramedVertex) o).element();
				}
				if(o instanceof FramedEdge) {
					return ((FramedEdge) o).element();
				}
				return o;
			}
		}));
		
		
		pipeline().retain(unwrapped);
		return this;
	}

	@Override
	public FramedTraversal retain(String... namedSteps) {
		pipeline().retain(namedSteps);
		return this;
	}

	@Override
	public FramedTraversal simplePath() {
		pipeline().simplePath();
		return this;
	}

	@Override
	public FramedTraversal aggregate() {
		pipeline().aggregate();
		return this;
	}

	@Override
	public FramedTraversal aggregate(Collection aggregate) {
		pipeline().aggregate(aggregate);
		return this;
	}

	@Override
	public FramedTraversal aggregate(Collection aggregate, PipeFunction aggregateFunction) {
		pipeline().aggregate(aggregate, aggregateFunction);
		return this;
	}

	@Override
	public FramedTraversal aggregate(PipeFunction aggregateFunction) {
		pipeline().aggregate(aggregateFunction);
		return this;
	}

	

	@Override
	public FramedTraversal optional(String namedStep) {
		pipeline().optional(namedStep);
		return this;
	}

	@Override
	public FramedTraversal groupBy(Map map, PipeFunction keyFunction, PipeFunction valueFunction) {
		pipeline().groupBy(map, keyFunction, valueFunction);
		return this;
	}

	@Override
	public FramedTraversal groupBy(PipeFunction keyFunction, PipeFunction valueFunction) {
		pipeline().groupBy(keyFunction, valueFunction);
		return this;
	}

	@Override
	public FramedTraversal groupBy(Map reduceMap, PipeFunction keyFunction, PipeFunction valueFunction,
			PipeFunction reduceFunction) {
		pipeline().groupBy(reduceMap, keyFunction, valueFunction, reduceFunction);
		return this;
	}

	@Override
	public FramedTraversal groupBy(PipeFunction keyFunction, PipeFunction valueFunction, PipeFunction reduceFunction) {
		pipeline().groupBy(keyFunction, valueFunction, reduceFunction);
		return this;
	}

	@Override
	public FramedTraversal groupCount(Map map, PipeFunction keyFunction, PipeFunction valueFunction) {
		pipeline().groupCount(map, keyFunction, valueFunction);
		return this;
	}

	@Override
	public FramedTraversal groupCount(PipeFunction keyFunction, PipeFunction valueFunction) {
		pipeline().groupCount(keyFunction, valueFunction);
		return this;
	}

	@Override
	public FramedTraversal groupCount(Map map, PipeFunction keyFunction) {
		pipeline().groupCount(map, keyFunction);
		return this;
	}

	@Override
	public FramedTraversal groupCount(PipeFunction keyFunction) {
		pipeline().groupCount(keyFunction);
		return this;
	}

	@Override
	public FramedTraversal groupCount(Map map) {
		pipeline().groupCount(map);
		return this;
	}

	@Override
	public FramedTraversal groupCount() {
		pipeline().groupCount();
		return this;
	}

	@Override
	public FramedEdgeTraversal idEdge(Graph graph) {
		pipeline().idEdge(graph);
		return asEdges();
	}

	@Override
	public FramedTraversal id() {
		pipeline().id();
		return this;
	}

	@Override
	public FramedVertexTraversal idVertex(Graph graph) {
		pipeline().idVertex(graph);
		return asVertices();
	}

	@Override
	public FramedTraversal sideEffect(PipeFunction sideEffectFunction) {
		pipeline().sideEffect(sideEffectFunction);
		return this;
	}

	@Override
	public FramedTraversal store(Collection storage) {
		pipeline().store(storage);
		return this;
	}

	@Override
	public FramedTraversal store(Collection storage, PipeFunction storageFunction) {
		pipeline().store(storage, storageFunction);
		return this;
	}

	@Override
	public FramedTraversal store() {
		pipeline().store();
		return this;
	}

	@Override
	public FramedTraversal store(PipeFunction storageFunction) {
		pipeline().store(storageFunction);
		return this;
	}

	@Override
	public FramedTraversal table(Table table, Collection stepNames, PipeFunction... columnFunctions) {
		pipeline().table(table, stepNames, columnFunctions);
		return this;
	}

	@Override
	public FramedTraversal table(Table table, PipeFunction... columnFunctions) {
		pipeline().table(table, columnFunctions);
		return this;
	}

	@Override
	public FramedTraversal table(PipeFunction... columnFunctions) {
		pipeline().table(columnFunctions);
		return this;
	}

	@Override
	public FramedTraversal table(Table table) {
		pipeline().table(table);
		return this;
	}

	@Override
	public FramedTraversal table() {
		pipeline().table();
		return this;
	}

	@Override
	public FramedTraversal tree(Tree tree, PipeFunction... branchFunctions) {
		pipeline().tree(tree, branchFunctions);
		return this;
	}

	@Override
	public FramedTraversal tree(PipeFunction... branchFunctions) {
		pipeline().tree(branchFunctions);
		return this;
	}

	@Override
	public FramedTraversal gather() {
		pipeline().gather();
		return this;
	}

	@Override
	public FramedTraversal gather(PipeFunction function) {
		pipeline().gather(function);
		return this;
	}

	@Override
	public FramedTraversal memoize(String namedStep) {
		pipeline().memoize(namedStep);
		return this;
	}


	@Override
	public FramedTraversal memoize(String namedStep, Map map) {
		pipeline().memoize(namedStep, map);
		return this;
	}

	

	@Override
	public FramedTraversal order() {
		pipeline().order();
		return this;
	}

	@Override
	public FramedTraversal order(Order order) {
		pipeline().order(order);
		return this;
	}

	@Override
	public FramedTraversal order(Tokens.T order) {
		pipeline().order(order);
		return this;
	}

	@Override
	public FramedTraversal order(PipeFunction compareFunction) {
		pipeline().order(compareFunction);
		return this;
	}

	@Override
	public FramedTraversal path(PipeFunction... pathFunctions) {
		pipeline().path(pathFunctions);
		return this;
	}

	@Override
	public FramedTraversal scatter() {
		pipeline().scatter();
		return this;
	}

	@Override
	public FramedTraversal select(Collection stepNames, PipeFunction... columnFunctions) {
		pipeline().select(stepNames, columnFunctions);
		return this;
	}

	@Override
	public FramedTraversal select(PipeFunction... columnFunctions) {
		pipeline().select(columnFunctions);
		return this;
	}

	@Override
	public FramedTraversal select() {
		pipeline().select();
		return this;
	}

	@Override
	public FramedTraversal shuffle() {
		pipeline().shuffle();
		return this;
	}

	@Override
	public FramedTraversal cap() {
		pipeline().cap();
		return this;
	}

	@Override
	public FramedTraversal orderMap(Order order) {
		pipeline().orderMap(order);
		return this;
	}

	@Override
	public FramedTraversal orderMap(Tokens.T order) {
		pipeline().orderMap(order);
		return this;
	}

	@Override
	public FramedTraversal orderMap(PipeFunction compareFunction) {
		pipeline().orderMap(compareFunction);
		return this;
	}

	@Override
	public FramedTraversal transform(PipeFunction function) {
		pipeline().transform(function);
		return this;
	}

	@Override
	public FramedTraversal start(Object object) {
		pipeline().start(object);
		return this;
	}

	@Override
	public List next(int number) {
		return pipeline().next(number);
	}

	@Override
	public List toList() {
		return pipeline().toList();
	}

	@Override
	public Collection fill(Collection collection) {
		return pipeline().fill(collection);
	}

	@Override
	public FramedTraversal enablePath() {
		pipeline().enablePath();
		return this;
	}

	@Override
	public FramedTraversal optimize(boolean optimize) {
		pipeline().optimize(optimize);
		return this;
	}

	@Override
	public void remove() {
		pipeline().remove();
	}

	
	
}

package org.jglue.totorom;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.gremlin.Tokens;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.transform.TransformPipe.Order;
import com.tinkerpop.pipes.util.structures.Pair;
import com.tinkerpop.pipes.util.structures.Table;
import com.tinkerpop.pipes.util.structures.Tree;

@SuppressWarnings("rawtypes")
abstract class FramedTraversalBase<T, SE, SideEffectParam1, SideEffectParam2> implements FramedTraversal<T, SE, SideEffectParam1, SideEffectParam2> {

	protected abstract FramedGraph graph();

	protected abstract GremlinPipeline pipeline();

	@Override
	public FramedVertexTraversal V() {
		pipeline().V();
		return castToVertices();
	}

	@Override
	public FramedEdgeTraversal E() {
		pipeline().E();
		return castToEdges();
	}

	@Override
	public FramedVertexTraversal v(Object... ids) {
		return (FramedVertexTraversal) graph().v(ids);
	}

	@Override
	public FramedEdgeTraversal e(Object... ids) {
		return (FramedEdgeTraversal) graph().e(ids);
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

	public FramedTraversal has(String key, Predicate predicate, Object value) {
		pipeline().has(key, predicate, value);
		return this;
	}

	public FramedTraversal hasNot(String key) {
		pipeline().hasNot(key);
		return this;
	}

	public FramedTraversal hasNot(String key, Object value) {
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
		return asTraversal();
	}

	@Override
	public FramedTraversal property(String key) {
		pipeline().property(key);
		return asTraversal();
	}


	@Override
	public FramedTraversal copySplit(Pipe... pipes) {
		pipeline().copySplit(pipes);
		return asTraversal();
	}

	@Override
	public FramedTraversal exhaustMerge() {
		pipeline().exhaustMerge();
		return asTraversal();
	}

	@Override
	public FramedTraversal fairMerge() {
		pipeline().fairMerge();
		return asTraversal();
	}

	@Override
	public FramedTraversal ifThenElse(TraversalFunction ifFunction, TraversalFunction thenFunction,
			TraversalFunction elseFunction, Class clazz) {
		pipeline().ifThenElse(ifFunction, thenFunction, elseFunction);
		return asTraversal();
	}

	@Override
	public FramedTraversal loop(String namedStep, TraversalFunction whileFunction, Class clazz) {
		pipeline().loop(namedStep, whileFunction);
		return asTraversal();
	}

	@Override
	public FramedTraversal loop(String namedStep, TraversalFunction whileFunction, TraversalFunction emitFunction, Class clazz) {
		pipeline().loop(namedStep, whileFunction, emitFunction);
		return asTraversal();
	}



	@Override
	public FramedTraversal back(String namedStep) {
		pipeline().back(namedStep);
		return asTraversal();
	}

	@Override
	public FramedTraversal back(String namedStep, Class clazz) {
		pipeline().back(namedStep);
		return asTraversal();
	}

	@Override
	public FramedTraversal dedup() {
		pipeline().dedup();
		return this;
	}

	@Override
	public FramedTraversal dedup(TraversalFunction dedupFunction) {
		pipeline().dedup(dedupFunction);
		return this;
	}

	@Override
	public FramedTraversal except(String... namedSteps) {
		pipeline().except(namedSteps);
		return this;
	}

	@Override
	public FramedTraversal filter(TraversalFunction filterFunction) {
		pipeline().filter(filterFunction);
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
		pipeline().retain(collection);
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
	public FramedTraversal aggregate(Collection aggregate, TraversalFunction aggregateFunction) {
		pipeline().aggregate(aggregate, aggregateFunction);
		return this;
	}

	@Override
	public FramedTraversal aggregate(TraversalFunction aggregateFunction) {
		pipeline().aggregate(aggregateFunction);
		return this;
	}

	@Override
	public FramedTraversal optional(String namedStep) {
		pipeline().optional(namedStep);
		return this;
	}

	@Override
	public FramedTraversal groupBy(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
		pipeline().groupBy(map, keyFunction, valueFunction);
		return this;
	}

	@Override
	public FramedTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction) {
		pipeline().groupBy(keyFunction, valueFunction);
		return this;
	}

	@Override
	public FramedTraversal groupBy(Map reduceMap, TraversalFunction keyFunction, TraversalFunction valueFunction,
			TraversalFunction reduceFunction) {
		pipeline().groupBy(reduceMap, keyFunction, valueFunction, reduceFunction);
		return this;
	}

	@Override
	public FramedTraversal groupBy(TraversalFunction keyFunction, TraversalFunction valueFunction,
			TraversalFunction reduceFunction) {
		pipeline().groupBy(keyFunction, valueFunction, reduceFunction);
		return this;
	}

	@Override
	public FramedTraversal groupCount(Map map, TraversalFunction keyFunction, TraversalFunction valueFunction) {
		pipeline().groupCount(map, keyFunction, valueFunction);
		return this;
	}

	@Override
	public FramedTraversal groupCount(TraversalFunction keyFunction, TraversalFunction valueFunction) {
		pipeline().groupCount(keyFunction, valueFunction);
		return this;
	}

	@Override
	public FramedTraversal groupCount(Map map, TraversalFunction keyFunction) {
		pipeline().groupCount(map, keyFunction);
		return this;
	}

	@Override
	public FramedTraversal groupCount(TraversalFunction keyFunction) {
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
		return castToEdges();
	}

	@Override
	public FramedTraversal id() {
		pipeline().id();
		return this;
	}

	@Override
	public FramedVertexTraversal idVertex(Graph graph) {
		pipeline().idVertex(graph);
		return castToVertices();
	}

	@Override
	public FramedTraversal sideEffect(final SideEffectFunction sideEffectFunction) {
		pipeline().sideEffect(new TraversalFunction() {

			@Override
			public Object compute(Object argument) {
				sideEffectFunction.execute(argument);
				return null;
			}

		});
		return this;
	}

	@Override
	public FramedTraversal store(Collection storage) {
		pipeline().store(storage);
		return this;
	}

	@Override
	public FramedTraversal store(Collection storage, TraversalFunction storageFunction) {
		pipeline().store(storage, storageFunction);
		return this;
	}

	@Override
	public FramedTraversal store() {
		pipeline().store();
		return this;
	}

	@Override
	public FramedTraversal store(TraversalFunction storageFunction) {
		pipeline().store(storageFunction);
		return this;
	}

	@Override
	public FramedTraversal table(Table table, Collection stepNames, TraversalFunction... columnFunctions) {
		pipeline().table(table, stepNames, wrap(columnFunctions));
		return this;
	}

	@Override
	public FramedTraversal table(Table table, TraversalFunction... columnFunctions) {
		pipeline().table(table, wrap(columnFunctions));
		return this;
	}

	@Override
	public FramedTraversal table(TraversalFunction... columnFunctions) {
		pipeline().table(wrap(columnFunctions));
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
	public FramedTraversal tree(Tree tree, TraversalFunction... branchFunctions) {
		pipeline().tree(tree, wrap(branchFunctions));
		return this;
	}

	@Override
	public FramedTraversal tree(TraversalFunction... branchFunctions) {
		pipeline().tree(wrap(branchFunctions));
		return this;
	}

	@Override
	public FramedTraversal gather() {
		pipeline().gather();
		return asTraversal();
	}

	@Override
	public FramedTraversal gather(TraversalFunction function) {
		pipeline().gather(function);
		return asTraversal();
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
	public FramedTraversal order(final Comparator compareFunction) {
		pipeline().order(new TraversalFunction<Pair<Object, Object>, Integer>() {

			@Override
			public Integer compute(Pair<Object, Object> argument) {
				return compareFunction.compare(argument.getA(), argument.getB());
			}
		});
		return this;
	}

	@Override
	public FramedTraversal path(TraversalFunction... pathFunctions) {
		pipeline().path(wrap(pathFunctions));
		return asTraversal();
	}

	@Override
	public FramedTraversal scatter() {
		pipeline().scatter();
		return asTraversal();
	}

	@Override
	public FramedTraversal select(Collection stepNames, TraversalFunction... columnFunctions) {
		pipeline().select(stepNames, wrap(columnFunctions));
		return asTraversal();
	}

	@Override
	public FramedTraversal select(TraversalFunction... columnFunctions) {
		pipeline().select(wrap(columnFunctions));
		return asTraversal();
	}

	@Override
	public FramedTraversal select() {
		pipeline().select();
		return asTraversal();
	}

	@Override
	public FramedTraversal shuffle() {
		pipeline().shuffle();
		return asTraversal();
	}

	@Override
	public FramedTraversal cap() {
		pipeline().cap();
		return asTraversal();
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
	public FramedTraversal orderMap(final Comparator compareFunction) {
		final Comparator wrapped = new FramingComparator(compareFunction, graph());
		pipeline().orderMap(new TraversalFunction<Pair<Object, Object>, Integer>() {

			@Override
			public Integer compute(Pair<Object, Object> argument) {
				return wrapped.compare(argument.getA(), argument.getB());
			}
		});
		return this;
	}

	@Override
	public FramedTraversal transform(TraversalFunction function) {
		pipeline().transform(function);
		return asTraversal();
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

	@Override
	public T next() {

		return (T) pipeline().next();
	}

	@Override
	public FramedEdgeTraversal start(FramedEdge object) {
		pipeline().start(object);
		return castToEdges();
	}

	@Override
	public FramedVertexTraversal start(FramedVertex object) {
		pipeline().start(object);
		return castToVertices();
	}

	@Override
	public  FramedTraversal property(String key, Class type) {
		return (FramedTraversal) property(key);
	}

	protected abstract FramedTraversal asTraversal();

	@Override
	public boolean hasNext() {
		return pipeline().hasNext();
	}

	@Override
	public Iterator<T> iterator() {

		return pipeline().iterator();
	}

	protected HashSet unwrap(Collection collection) {
		HashSet unwrapped = new HashSet(Collections2.transform(collection, new Function<Object, Object>() {

			@Override
			public Object apply(Object o) {
				if (o instanceof FramedVertex) {
					return ((FramedVertex) o).element();
				}
				if (o instanceof FramedEdge) {
					return ((FramedEdge) o).element();
				}
				return o;
			}
		}));
		return unwrapped;
	}

	private TraversalFunction[] wrap(TraversalFunction... branchFunctions) {
		Collection<TraversalFunction> wrapped = Collections2.transform(Arrays.asList(branchFunctions),
				new Function<TraversalFunction, TraversalFunction>() {

					@Override
					public TraversalFunction apply(TraversalFunction input) {
						return new FramingTraversalFunction(input, graph());
					}

				});
		TraversalFunction[] wrappedArray = wrapped.toArray(new TraversalFunction[wrapped.size()]);
		return wrappedArray;
	}

	@Override
	public FramedTraversal gatherScatter() {
		pipeline().gather().scatter();
		return this;
	}
	
	@Override
	public FramedTraversal cast(Class<T> clazz) {
	
		return this;
	}
}

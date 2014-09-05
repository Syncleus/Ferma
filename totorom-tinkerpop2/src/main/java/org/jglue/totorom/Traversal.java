package org.jglue.totorom;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.gremlin.Tokens;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.branch.LoopPipe;
import com.tinkerpop.pipes.transform.TransformPipe;
import com.tinkerpop.pipes.util.structures.Pair;
import com.tinkerpop.pipes.util.structures.Row;
import com.tinkerpop.pipes.util.structures.Table;
import com.tinkerpop.pipes.util.structures.Tree;

/**
 * The root traversal class. Wraps a Tinkerpop {@link GremlinPipeline}
 * 
 * @author bryn
 *
 * @param <T>
 * @param <Cap>
 */
public interface Traversal<T, Cap, SideEffect> extends Iterator<T>, Iterable<T> {

	/**
	 * Traverse over all the vertices in the graph.
	 * 
	 * @return
	 */
	public VertexTraversal<?, ?> V();

	/**
	 * Traverse over all the edges in the graph.
	 * 
	 * @return
	 */
	public EdgeTraversal<?, ?> E();

	/**
	 * Traversal over a list of vertices in the graph.
	 * 
	 * @param ids
	 *            The ids of the vertices.
	 * @return The traversal.
	 */
	public VertexTraversal<?, ?> v(Object... ids);

	/**
	 * Traversal over a list of edges in the graph.
	 * 
	 * @param ids
	 *            The ids of the edges.
	 * @return The traversal.
	 */
	public EdgeTraversal<?, ?> e(Object... ids);

	/**
	 * Completely drain the pipeline of its objects. Useful when a sideEffect of
	 * the pipeline is desired.
	 */
	public void iterate();

	/**
	 * Add a PropertyMapPipe to the end of the Pipeline. Emit the properties of
	 * the incoming element as a java.util.Map.
	 *
	 * @param keys
	 *            the keys to get from the element (if none provided, all keys
	 *            retrieved)
	 * @return the extended Pipeline
	 */
	public abstract Traversal<Map<String, Object>, ?, ?> map(String... keys);

	/**
	 * Add a PropertyPipe to the end of the Pipeline. Emit the respective
	 * property of the incoming element.
	 *
	 * @param key
	 *            the property key
	 * @return the extended Pipeline
	 */
	public abstract Traversal<Object, ?, ?> property(String key);

	/**
	 * Add a PropertyPipe to the end of the Pipeline. Emit the respective
	 * property of the incoming element.
	 *
	 * @param key
	 *            the property key
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<N, ?, ?> property(String key, Class<N> type);

	/**
	 * Add a CopySplitPipe to the end of the pipeline. The incoming objects are
	 * copied to the provided pipes. This "split-pipe" is used in conjunction
	 * with some type of "merge-pipe."
	 *
	 * @param pipes
	 *            the internal pipes of the CopySplitPipe
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<N, ?, ?> copySplit(Pipe<T, N>... pipes);

	/**
	 * Add an ExhaustMergePipe to the end of the pipeline. The one-step previous
	 * MetaPipe in the pipeline's pipes are used as the internal pipes. The
	 * pipes' emitted objects are merged where the first pipe's objects are
	 * exhausted, then the second, etc.
	 *
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<N, ?, ?> exhaustMerge();

	/**
	 * Add a FairMergePipe to the end of the Pipeline. The one-step previous
	 * MetaPipe in the pipeline's pipes are used as the internal pipes. The
	 * pipes' emitted objects are merged in a round robin fashion.
	 *
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<N, ?, ?> fairMerge();

	/**
	 * Add an IfThenElsePipe to the end of the Pipeline. If the ifFunction is
	 * true, then the results of the thenFunction are emitted. If the ifFunction
	 * is false, then the results of the elseFunction are emitted.
	 *
	 * @param ifFunction
	 *            the function denoting the "if" part of the pipe
	 * @param thenFunction
	 *            the function denoting the "then" part of the pipe
	 * @param elseFunction
	 *            the function denoting the "else" part of the pipe
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<N, ?, ?> ifThenElse(TraversalFunction<T, Boolean> ifFunction,
			TraversalFunction<T, N> thenFunction, TraversalFunction<T, N> elseFunction, Class<N> clazz);

	/**
	 * Add a LoopPipe to the end of the Pipeline. Looping is useful for
	 * repeating a section of a pipeline. The provided whileFunction determines
	 * when to drop out of the loop. The whileFunction is provided a LoopBundle
	 * object which contains the object in loop along with other useful
	 * metadata.
	 *
	 * @param namedStep
	 *            the name of the step to loop back to
	 * @param whileFunction
	 *            whether or not to continue looping on the current object
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<N, ?, ?> loop(String namedStep,
			TraversalFunction<LoopPipe.LoopBundle<T>, Boolean> whileFunction, Class<N> clazz);

	/**
	 * Add a LoopPipe to the end of the Pipeline. Looping is useful for
	 * repeating a section of a pipeline. The provided whileFunction determines
	 * when to drop out of the loop. The provided emitFunction can be used to
	 * emit objects that are still going through a loop. The whileFunction and
	 * emitFunctions are provided a LoopBundle object which contains the object
	 * in loop along with other useful metadata.
	 *
	 * @param namedStep
	 *            the number of steps to loop back to
	 * @param whileFunction
	 *            whether or not to continue looping on the current object
	 * @param emitFunction
	 *            whether or not to emit the current object (irrespective of
	 *            looping)
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<N, ?, ?> loop(String namedStep,
			TraversalFunction<LoopPipe.LoopBundle<T>, Boolean> whileFunction,
			TraversalFunction<LoopPipe.LoopBundle<T>, Boolean> emitFunction, Class<N> clazz);

	/**
	 * Add a DuplicateFilterPipe to the end of the Pipeline. Will only emit the
	 * object if it has not been seen before.
	 *
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> dedup();

	/**
	 * Add a DuplicateFilterPipe to the end of the Pipeline. Will only emit the
	 * object if the object generated by its function hasn't been seen before.
	 *
	 * @param dedupFunction
	 *            a function to call on the object to yield the object to dedup
	 *            on
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> dedup(TraversalFunction<T, ?> dedupFunction);

	/**
	 * Add an ExceptFilterPipe to the end of the Pipeline. Will only emit the
	 * object if it is not in the provided collection.
	 *
	 * @param collection
	 *            the collection except from the stream
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> except(Iterable<?> collection);

	/**
	 * Add an ExceptFilterPipe to the end of the Pipeline. Will only emit the
	 * object if it is not equal to any of the objects contained at the named
	 * steps.
	 *
	 * @param namedSteps
	 *            the named steps in the pipeline
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> except(String... namedSteps);

	/**
	 * Add an FilterFunctionPipe to the end of the Pipeline. The serves are an
	 * arbitrary filter where the filter criteria is provided by the
	 * filterFunction.
	 *
	 * @param filterFunction
	 *            the filter function of the pipe
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> filter(TraversalFunction<T, Boolean> filterFunction);

	/**
	 * Add a RandomFilterPipe to the end of the Pipeline. A biased coin toss
	 * determines if the object is emitted or not.
	 *
	 * @param bias
	 *            the bias of the random coin
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> random(double bias);

	/**
	 * Add a RageFilterPipe to the end of the Pipeline. Analogous to a high/low
	 * index lookup.
	 *
	 * @param low
	 *            the low end of the range
	 * @param high
	 *            the high end of the range
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> range(int low, int high);

	/**
	 * Add a RetainFilterPipe to the end of the Pipeline. Will emit the object
	 * only if it is in the provided collection.
	 *
	 * @param collection
	 *            the collection to retain
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> retain(Iterable<?> collection);
	


	/**
	 * Add a RetainFilterPipe to the end of the Pipeline. Will only emit the
	 * object if it is equal to any of the objects contained at the named steps.
	 *
	 * @param namedSteps
	 *            the named steps in the pipeline
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> retain(String... namedSteps);

	/**
	 * Add an AggregatePipe to the end of the Pipeline. The objects prior to
	 * aggregate are greedily collected into an ArrayList.
	 *
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, Collection<T>, Collection<T>> aggregate();

	/**
	 * Add an AggregatePipe to the end of the Pipeline. The objects prior to
	 * aggregate are greedily collected into the provided collection.
	 *
	 * @param aggregate
	 *            the collection to aggregate results into
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, Collection<T>, Collection<T>> aggregate(Collection<T> aggregate);

	/**
	 * Add an AggregatePipe to the end of the Pipeline. The results of the
	 * function evaluated on the objects prior to the aggregate are greedily
	 * collected into the provided collection.
	 *
	 * @param aggregate
	 *            the collection to aggregate results into
	 * @param aggregateFunction
	 *            the function to run over each object prior to insertion into
	 *            the aggregate
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<T, Collection<N>, Collection<N>> aggregate(Collection<T> aggregate,
			TraversalFunction<T, N> aggregateFunction);

	/**
	 * Add an AggregatePipe to the end of the Pipeline. The results of the
	 * function evaluated on the objects prior to the aggregate are greedily
	 * collected into an ArrayList.
	 *
	 * @param aggregateFunction
	 *            the function to run over each object prior to insertion into
	 *            the aggregate
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<T, Collection<N>, Collection<N>> aggregate(TraversalFunction<T, N> aggregateFunction);

	/**
	 * Add a GroupByPipe to the end of the Pipeline. Group the objects inputted
	 * objects according to a key generated from the object and a value
	 * generated from the object. The grouping map has values that are Lists.
	 *
	 * @param map
	 *            the map to store the grouping in
	 * @param keyFunction
	 *            the function that generates the key from the object
	 * @param valueFunction
	 *            the function that generates the value from the function
	 * @return the extended Pipeline
	 */
	public abstract <K, V> Traversal<T, Map<K, List<V>>, Map<K, List<V>>> groupBy(Map<K, List<V>> map,
			TraversalFunction<T, K> keyFunction, TraversalFunction<T, Iterator<V>> valueFunction);

	/**
	 * Add a GroupByPipe to the end of the Pipeline. Group the objects inputted
	 * objects according to a key generated from the object and a value
	 * generated from the object. The grouping map has values that are Lists.
	 *
	 * @param keyFunction
	 *            the function that generates the key from the object
	 * @param valueFunction
	 *            the function that generates the value from the function
	 * @return the extended Pipeline
	 */
	public abstract <K, V> Traversal<T, Map<K, List<V>>, Map<K, List<V>>> groupBy(TraversalFunction<T, K> keyFunction,
			TraversalFunction<T, Iterator<V>> valueFunction);

	/**
	 * Add a GroupByReducePipe to the end of the Pipeline. Group the objects
	 * inputted objects according to a key generated from the object and a value
	 * generated from the object. The grouping map has values that are Lists.
	 * When the pipe has no more incoming objects, apply the reduce function to
	 * the keyed Lists. The sideEffect is only fully available when the pipe is
	 * empty.
	 *
	 * @param reduceMap
	 *            a map to perform the reduce operation on (good for having a
	 *            later reference)
	 * @param keyFunction
	 *            the function that generates the key from the object
	 * @param valueFunction
	 *            the function that generates the value from the function
	 * @param reduceFunction
	 *            the function that reduces the value lists
	 * @return the extended Pipeline
	 */
	public abstract <K, V, V2> Traversal<T, Map<K, V2>, Map<K, V2>> groupBy(Map<K, V2> reduceMap,
			TraversalFunction<T, K> keyFunction, TraversalFunction<T, Iterator<V>> valueFunction,
			TraversalFunction<List<V>, V2> reduceFunction);

	/**
	 * Add a GroupByReducePipe to the end of the Pipeline. Group the objects
	 * inputted objects according to a key generated from the object and a value
	 * generated from the object. The grouping map has values that are Lists.
	 * When the pipe has no more incoming objects, apply the reduce function to
	 * the keyed Lists. The sideEffect is only fully available when the pipe is
	 * empty.
	 *
	 * @param keyFunction
	 *            the function that generates the key from the object
	 * @param valueFunction
	 *            the function that generates the value from the function
	 * @param reduceFunction
	 *            the function that reduces the value lists
	 * @return the extended Pipeline
	 */
	public abstract <K, V, V2> Traversal<T, Map<K, V2>, Map<K, V2>> groupBy(TraversalFunction<T, K> keyFunction,
			TraversalFunction<T, Iterator<V>> valueFunction, TraversalFunction<List<V>, V2> reduceFunction);

	/**
	 * Add a GroupCountPipe or GroupCountFunctionPipe to the end of the
	 * Pipeline. A map is maintained of counts. The map keys are determined by
	 * the function on the incoming object. The map values are determined by the
	 * function on the incoming object (getA()) and the previous value (getB()).
	 *
	 * @param map
	 *            a provided count map
	 * @param keyFunction
	 *            the key function to determine map key
	 * @param valueFunction
	 *            the value function to determine map value
	 * @return the extended Pipeline
	 */
	public abstract <K> Traversal<T, Map<K, Long>, Map<K, Long>> groupCount(Map<K, Long> map,
			TraversalFunction<T, K> keyFunction, TraversalFunction<Pair<T, Long>, Long> valueFunction);

	/**
	 * Add a GroupCountPipe or GroupCountFunctionPipe to the end of the
	 * Pipeline. map is maintained of counts. The map keys are determined by the
	 * function on the incoming object. The map values are determined by the
	 * function on the incoming object (getA()) and the previous value (getB()).
	 *
	 * @param keyFunction
	 *            the key function to determine map key
	 * @param valueFunction
	 *            the value function to determine map value
	 * @return the extended Pipeline
	 */
	public abstract <K> Traversal<T, Map<K, Long>, Map<K, Long>> groupCount(TraversalFunction<T, K> keyFunction,
			TraversalFunction<Pair<T, Long>, Long> valueFunction);

	/**
	 * Add a GroupCountPipe or GroupCountFunctionPipe to the end of the
	 * Pipeline. A map is maintained of counts. The map keys are determined by
	 * the function on the incoming object. The map values are 1 plus the
	 * previous value for that key.
	 *
	 * @param map
	 *            a provided count map
	 * @param keyFunction
	 *            the key function to determine map key
	 * @return the extended Pipeline
	 */
	public abstract <K> Traversal<T, Map<K, Long>, Map<K, Long>> groupCount(Map<K, Long> map, TraversalFunction<T, K> keyFunction);

	/**
	 * Add a GroupCountPipe or GroupCountFunctionPipe to the end of the
	 * Pipeline. A map is maintained of counts. The map keys are determined by
	 * the function on the incoming object. The map values are 1 plus the
	 * previous value for that key.
	 *
	 * @param keyFunction
	 *            the key function to determine map key
	 * @return the extended Pipeline
	 */
	public abstract <K> Traversal<T, Map<K, Long>, Map<K, Long>> groupCount(TraversalFunction<T, K> keyFunction);

	/**
	 * Add a GroupCountPipe to the end of the Pipeline. A map is maintained of
	 * counts. The map keys are the incoming objects. The map values are 1 plus
	 * the previous value for that key.
	 *
	 * @param map
	 *            a provided count map
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, Map<T, Long>, Map<T, Long>> groupCount(Map<T, Long> map);

	/**
	 * Add a GroupCountPipe to the end of the Pipeline. A map is maintained of
	 * counts. The map keys are the incoming objects. The map values are 1 plus
	 * the previous value for that key.
	 *
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, Map<T, Long>, Map<T, Long>> groupCount();

	/**
	 * Add a SideEffectFunctionPipe to the end of the Pipeline. The provided
	 * function is evaluated and the incoming object is the outgoing object.
	 *
	 * @param sideEffectFunction
	 *            the function of the pipe
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> sideEffect(SideEffectFunction<T> sideEffectFunction);

	/**
	 * Add a StorePipe to the end of the Pipeline. Lazily store the incoming
	 * objects into the provided collection.
	 *
	 * @param storage
	 *            the collection to store results into
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, Collection<T>, T> store(Collection<T> storage);

	/**
	 * Add a StorePipe to the end of the Pipeline. Lazily store the object
	 * returned by the function over the incoming object into the provided
	 * collection.
	 *
	 * @param storage
	 *            the collection to store results into
	 * @param storageFunction
	 *            the function to run over each object prior to insertion into
	 *            the storage collection
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<T, Collection<N>, N> store(Collection<N> storage, TraversalFunction<T, N> storageFunction);

	/**
	 * Add an StorePipe to the end of the Pipeline. An ArrayList storage
	 * collection is provided and filled lazily with incoming objects.
	 *
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, Collection<T>, T> store();

	/**
	 * Add a StorePipe to the end of the Pipeline. An ArrayList storage
	 * collection is provided and filled lazily with the return of the function
	 * evaluated over the incoming objects.
	 *
	 * @param storageFunction
	 *            the function to run over each object prior to insertion into
	 *            the storage collection
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<T, Collection<N>, N> store(TraversalFunction<T, N> storageFunction);

	/**
	 * Add a TablePipe to the end of the Pipeline. This step is used for
	 * grabbing previously seen objects the pipeline as identified by as-steps.
	 *
	 * @param table
	 *            the table to fill
	 * @param stepNames
	 *            the partition steps to include in the filling
	 * @param columnFunctions
	 *            the post-processing function for each column
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, Table, Table> table(Table table, Collection<String> stepNames,
			TraversalFunction<?, ?>... columnFunctions);

	/**
	 * Add a TablePipe to the end of the Pipeline. This step is used for
	 * grabbing previously seen objects the pipeline as identified by as-steps.
	 *
	 * @param table
	 *            the table to fill
	 * @param columnFunctions
	 *            the post-processing function for each column
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, Table, Table> table(Table table, TraversalFunction<?, ?>... columnFunctions);

	/**
	 * Add a TablePipe to the end of the Pipeline. This step is used for
	 * grabbing previously seen objects the pipeline as identified by as-steps.
	 *
	 * @param columnFunctions
	 *            the post-processing function for each column
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, Table, Table> table(TraversalFunction<?, ?>... columnFunctions);

	/**
	 * Add a TablePipe to the end of the Pipeline. This step is used for
	 * grabbing previously seen objects the pipeline as identified by as-steps.
	 *
	 * @param table
	 *            the table to fill
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, Table, Table> table(Table table);

	/**
	 * Add a TablePipe to the end of the Pipeline. This step is used for
	 * grabbing previously seen objects the pipeline as identified by as-steps.
	 *
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, Table, Table> table();

	/**
	 * Add a TreePipe to the end of the Pipeline This step maintains an internal
	 * tree representation of the paths that have flowed through the step.
	 *
	 * @param tree
	 *            an embedded Map data structure to store the tree
	 *            representation in
	 * @param branchFunctions
	 *            functions to apply to each path object in a round robin
	 *            fashion
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<T, Tree<N>, Tree<N>> tree(Tree<N> tree, TraversalFunction<?, N>... branchFunctions);

	/**
	 * Add a TreePipe to the end of the Pipeline This step maintains an internal
	 * tree representation of the paths that have flowed through the step.
	 *
	 * @param branchFunctions
	 *            functions to apply to each path object in a round robin
	 *            fashion
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<T, Tree<N>, Tree<N>> tree(TraversalFunction<?, N>... branchFunctions);

	/**
	 * Add a TreePipe to the end of the Pipeline This step maintains an internal
	 * tree representation of the paths that have flowed through the step.
	 *
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, Tree<T>, Tree<T>> tree();
	
	/**
	 * Add an IdentityPipe to the end of the Pipeline. Useful in various
	 * situations where a step is needed without processing. For example, useful
	 * when two as-steps are needed in a row.
	 *
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> identity();

	/**
	 * Add a MemoizePipe to the end of the Pipeline. This step will hold a Map
	 * of the objects that have entered into its pipeline section. If an input
	 * is seen twice, then the map stored output is emitted instead of
	 * recomputing the pipeline section.
	 *
	 * @param namedStep
	 *            the name of the step previous to memoize to
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> memoize(String namedStep);

	/**
	 * Add a MemoizePipe to the end of the Pipeline. This step will hold a Map
	 * of the objects that have entered into its pipeline section. If an input
	 * is seen twice, then the map stored output is emitted instead of
	 * recomputing the pipeline section.
	 *
	 * @param namedStep
	 *            the name of the step previous to memoize to
	 * @param map
	 *            the memoization map
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> memoize(String namedStep, Map<?, ?> map);

	/**
	 * Add an OrderPipe to the end of the Pipeline. This step will sort the
	 * objects in the stream in a default Comparable order.
	 *
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> order();

	/**
	 * Add an OrderPipe to the end of the Pipeline. This step will sort the
	 * objects in the stream in a default Comparable order.
	 *
	 * @param order
	 *            if the stream is composed of comparable objects, then
	 *            increment or decrement can be specified
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> order(TransformPipe.Order order);

	/**
	 * Add an OrderPipe to the end of the Pipeline. This step will sort the
	 * objects in the stream in a default Comparable order.
	 *
	 * @param order
	 *            if the stream is composed of comparable objects, then
	 *            increment or decrement can be specified
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> order(Tokens.T order);

	/**
	 * Add an OrderPipe to the end of the Pipeline. This step will sort the
	 * objects in the stream according to a comparator defined in the provided
	 * function.
	 *
	 * @param compareFunction
	 *            a comparator function of two objects of type E
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> order(Comparator<T> compareFunction);

	/**
	 * Wrap the previous step in an AsPipe. Useful for naming steps and is used
	 * in conjunction with various other steps including: loop, select, back,
	 * table, etc.
	 *
	 * @param name
	 *            the name of the AsPipe
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> as(String name);

	/**
	 * Add a CyclicPathFilterPipe to the end of the Pipeline. If the object's
	 * path is repeating (looping), then the object is filtered. Thus, what is
	 * emitted are those objects whose history is composed of unique objects.
	 *
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> simplePath();

	/**
	 * Add a BackFilterPipe to the end of the Pipeline. The object that was seen
	 * namedSteps ago is emitted.
	 * 
	 * Note: Using back is discouraged. Instead use 'and'.
	 *
	 * @param namedStep
	 *            the name of the step previous to back up to
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<N, ?, ?> back(String namedStep);

	/**
	 * Add a BackFilterPipe to the end of the Pipeline. The object that was seen
	 * namedSteps ago is emitted.
	 *
	 * Note: Using back is discouraged. Instead use 'and'.
	 *
	 * @param namedStep
	 *            the name of the step previous to back up to
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<N, ?, ?> back(String namedStep, Class<N> type);

	/**
	 * Causes the pipeline to be greedy up to this step.
	 *
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> gatherScatter();

	/**
	 * Add a PathPipe to the end of the Pipeline. This will emit the path that
	 * has been seen thus far. If path functions are provided, then they are
	 * evaluated in a round robin fashion on the objects of the path.
	 *
	 * @param pathFunctions
	 *            the path function of the PathPipe
	 * @return the extended Pipeline
	 */
	public abstract Iterator<List<?>> path(TraversalFunction<?, ?>... pathFunctions);

	/**
	 * Add a SelectPipe to the end of the Pipeline. The objects of the named
	 * steps (via as) previous in the pipeline are emitted as a Row object. A
	 * Row object extends ArrayList and simply provides named columns and some
	 * helper methods. If column functions are provided, then they are evaluated
	 * in a round robin fashion on the objects of the Row.
	 *
	 * @param stepNames
	 *            the name of the steps in the expression to retrieve the
	 *            objects from
	 * @param columnFunctions
	 *            the functions to apply to the column objects prior to filling
	 *            the Row
	 * @return the extended Pipeline
	 */
	public abstract Iterator<Row<?>> select(Collection<String> stepNames, TraversalFunction<?, ?>... columnFunctions);

	/**
	 * Add a SelectPipe to the end of the Pipeline. The objects of the named
	 * steps (via as) previous in the pipeline are emitted as a Row object. A
	 * Row object extends ArrayList and simply provides named columns and some
	 * helper methods. If column functions are provided, then they are evaluated
	 * in a round robin fashion on the objects of the Row.
	 *
	 * @param columnFunctions
	 *            the functions to apply to the column objects prior to filling
	 *            the Row
	 * @return the extended Pipeline
	 */
	public abstract Iterator<Row<?>> select(TraversalFunction<?, ?>... columnFunctions);

	/**
	 * Add a SelectPipe to the end of the Pipeline. The objects of the named
	 * steps (via as) previous in the pipeline are emitted as a Row object. A
	 * Row object extends ArrayList and simply provides named columns and some
	 * helper methods.
	 *
	 * @return the extended Pipeline
	 */
	public abstract Iterator<Row<?>> select();

	/**
	 * Add a ShufflePipe to the end of the Pipeline. All the objects previous to
	 * this step are aggregated in a greedy fashion, their order randomized and
	 * emitted.
	 *
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> shuffle();

	/**
	 * Add a SideEffectCapPipe to the end of the Pipeline. When the previous
	 * step in the pipeline is implements SideEffectPipe, then it has a method
	 * called getSideEffect(). The cap step will greedily iterate the pipeline
	 * and then, when its empty, emit the side effect of the previous pipe.
	 *
	 * @return the extended Pipeline
	 */
	public abstract Cap cap();

	/**
	 * This step calls emits the input but also calls the sideEffectFunction function with the side effect of
	 * the previous step when it is ready.
	 *
	 * @return the extended Pipeline
	 */
	public abstract Traversal<T, ?, ?> divert(SideEffectFunction<SideEffect> sideEffectFunction);

	/**
	 * Add a TransformFunctionPipe to the end of the Pipeline. Given an input,
	 * the provided function is computed on the input and the output of that
	 * function is emitted.
	 *
	 * @param function
	 *            the transformation function of the pipe
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<N, ?, ?> transform(TraversalFunction<T, N> function);

	/**
	 * Add a StartPipe to the end of the pipeline. Though, in practice, a
	 * StartPipe is usually the beginning. Moreover, the constructor of the
	 * Pipeline will internally use StartPipe.
	 *
	 * @param object
	 *            the object that serves as the start of the pipeline
	 *            (iterator/iterable are unfolded)
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<N, ?, ?> start(N object);

	/**
	 * Add a StartPipe to the end of the pipeline. Though, in practice, a
	 * StartPipe is usually the beginning. Moreover, the constructor of the
	 * Pipeline will internally use StartPipe.
	 *
	 * @param object
	 *            the object that serves as the start of the pipeline
	 *            (iterator/iterable are unfolded)
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<?, ?> start(FramedVertex object);

	/**
	 * Add a StartPipe to the end of the pipeline. Though, in practice, a
	 * StartPipe is usually the beginning. Moreover, the constructor of the
	 * Pipeline will internally use StartPipe.
	 *
	 * @param object
	 *            the object that serves as the start of the pipeline
	 *            (iterator/iterable are unfolded)
	 * @return the extended Pipeline
	 */
	public abstract EdgeTraversal<?, ?> start(FramedEdge object);

	/**
	 * Return the number of objects iterated through the pipeline.
	 *
	 * @return the number of objects iterated
	 */
	public abstract long count();

	/**
	 * Return the next object in the pipeline.
	 *
	 */
	public abstract T next();

	/**
	 * Return the next X objects in the pipeline as a list.
	 *
	 * @param number
	 *            the number of objects to return
	 * @return a list of X objects (if X objects occur)
	 */
	public abstract List<T> next(int number);

	/**
	 * Return a list of all the objects in the pipeline.
	 *
	 * @return a list of all the objects
	 */
	public abstract List<T> toList();

	/**
	 * Enable path calculations within the Pipeline. This is typically done
	 * automatically and in rare occasions needs to be called.
	 *
	 * @return the Pipeline with path calculations enabled
	 */
	public abstract Traversal<T, Cap, SideEffect> enablePath();

	/**
	 * When possible, Gremlin takes advantage of certain sequences of pipes in
	 * order to make a more concise, and generally more efficient expression.
	 * This method will turn on and off query optimization from this stage in
	 * the pipeline on.
	 *
	 * @param optimize
	 *            whether to optimize the pipeline from here on or not
	 * @return The GremlinPipeline with the optimization turned off
	 */
	public abstract Traversal<T, Cap, SideEffect> optimize(boolean optimize);

	/**
	 * Remove every element at the end of this Pipeline.
	 */
	public abstract void remove();

	/**
	 * Fill the provided collection with the objects in the pipeline.
	 *
	 * @param collection
	 *            the collection to fill
	 * @return the collection filled
	 */
	public abstract Collection<T> fill(Collection<T> collection);

	/**
	 * Cast the traversal as a vertex traversal
	 * 
	 * @return
	 */
	public abstract Traversal<T, Cap, SideEffect> cast(Class<T> clazz);

	/**
	 * Cast the traversal as a vertex traversal
	 * 
	 * @return
	 */
	public abstract VertexTraversal<Cap, SideEffect> castToVertices();

	/**
	 * Cast the traversal to an edge traversalT
	 * 
	 * @return
	 */
	public abstract EdgeTraversal<Cap, SideEffect> castToEdges();

	/**
	 * Add an OptionalPipe to the end of the Pipeline. The section of pipeline
	 * back to the partition step is evaluated.
	 *
	 * @param namedStep
	 *            the name of the step previous to optional back to
	 * @return the extended Pipeline
	 */
	public abstract <N> Traversal<N, ?, ?> optional(String namedStep);

	/**
	 * Add an IdEdgePipe to the end of the Pipeline. Emit the edges of the graph
	 * whose ids are those of the incoming id objects.
	 *
	 * @param graph
	 *            the graph of the pipe
	 * @return the extended Pipeline
	 */
	public abstract EdgeTraversal<?, ?> idEdge(Graph graph);

	/**
	 * Add an IdPipe to the end of the Pipeline. Emit the id of the incoming
	 * element.
	 *
	 * @return the extended Pipeline
	 */
	public abstract Traversal<Object, ?, ?> id();

	/**
	 * Add an IdVertexPipe to the end of the Pipeline. Emit the vertices of the
	 * graph whose ids are those of the incoming id objects.
	 *
	 * @param graph
	 *            the graph of the pipe
	 * @return the extended Pipeline
	 */
	public abstract VertexTraversal<?, ?> idVertex(Graph graph);

}
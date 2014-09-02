package org.jglue.totorom;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.Tokens;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.transform.TransformPipe;
import com.tinkerpop.pipes.util.structures.Pair;
import com.tinkerpop.pipes.util.structures.Table;
import com.tinkerpop.pipes.util.structures.Tree;

public interface FramedEdgeTraversal<SE> extends FramedTraversal<Edge, SE> {
	/**
     * Check if the element has a property with provided key.
     *
     * @param key the property key to check
     * @return the extended Pipeline
     */
	public FramedEdgeTraversal<?> has(String key);

	  /**
     * Add an IdFilterPipe, LabelFilterPipe, or PropertyFilterPipe to the end of the Pipeline.
     * If the incoming element has the provided key/value as check with .equals(), then let the element pass.
     * If the key is id or label, then use respect id or label filtering.
     *
     * @param key   the property key to check
     * @param value the object to filter on (in an OR manner)
     * @return the extended Pipeline
     */
	public FramedEdgeTraversal<?> has(String key, Object value);

	/**
     * Add an IdFilterPipe, LabelFilterPipe, or PropertyFilterPipe to the end of the Pipeline.
     * If the incoming element has the provided key/value as check with .equals(), then let the element pass.
     * If the key is id or label, then use respect id or label filtering.
     *
     * @param key          the property key to check
     * @param compareToken the comparison to use
     * @param value        the object to filter on
     * @return the extended Pipeline
     */
	public FramedEdgeTraversal<?> has(String key, Tokens.T compareToken,
			Object value);

	 /**
     * Add an IdFilterPipe, LabelFilterPipe, or PropertyFilterPipe to the end of the Pipeline.
     * If the incoming element has the provided key/value as check with .equals(), then let the element pass.
     * If the key is id or label, then use respect id or label filtering.
     *
     * @param key       the property key to check
     * @param predicate the comparison to use
     * @param value     the object to filter on
     * @return the extended Pipeline
     */
	public FramedEdgeTraversal<?> has(String key, Predicate predicate,
			Object value);

	   /**
     * Check if the element does not have a property with provided key.
     *
     * @param key the property key to check
     * @return the extended Pipeline
     */
	public FramedEdgeTraversal<?> hasNot(String key);

	   /**
  * Add an IdFilterPipe, LabelFilterPipe, or PropertyFilterPipe to the end of the Pipeline.
  * If the incoming element has the provided key/value as check with .equals(), then filter the element.
  * If the key is id or label, then use respect id or label filtering.
  *
  * @param key   the property key to check
  * @param value the objects to filter on (in an OR manner)
  * @return the extended Pipeline
  */
	public FramedEdgeTraversal<?> hasNot(String key, Object value);

	/**
     * Add an IntervalFilterPipe to the end of the Pipeline.
     * If the incoming element has a value that is within the interval value range specified, then the element is allows to pass.
     * If hte incoming element's value for the key is null, the element is filtered.
     *
     * @param key        the property key to check
     * @param startValue the start of the interval (inclusive)
     * @param endValue   the end of the interval (exclusive)
     * @return the extended Pipeline
     */
	public <C> FramedEdgeTraversal<?> interval(String key,
			Comparable<C> startValue, Comparable<C> endValue);

	

    /**
     * Add an InVertexPipe to the end of the Pipeline.
     * Emit the head vertex of the incoming edge.
     *
     * @return the extended Pipeline
     */
	public FramedVertexTraversal<?> inV();

	   /**
     * Add an OutVertexPipe to the end of the Pipeline.
     * Emit the tail vertex of the incoming edge.
     *
     * @return the extended Pipeline
     */
	public FramedVertexTraversal<?> outV();
    /**
     * Add a BothVerticesPipe to the end of the Pipeline.
     * Emit both the tail and head vertices of the incoming edge.
     *
     * @return the extended Pipeline
     */
	public FramedVertexTraversal<?> bothV();

	/**
	 * Get the next object emitted from the pipeline. If no such object exists,
	 * then a NoSuchElementException is thrown.
	 * 
	 * @param kind
	 *            The type of frame for the element.
	 * @return the next emitted object
	 */
	public <T extends FramedEdge> T next(Class<T> kind);

	/**
	 * Return the next X objects in the traversal as a list.
	 * 
	 * @param number
	 *            the number of objects to return
	 * @param kind
	 *            the type of frame to for each element.
	 * @return a list of X objects (if X objects occur)
	 */
	public <T extends FramedEdge> List<T> next(int amount, Class<T> kind);

	/**
	 * Return an iterator of framed elements.
	 * 
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return An iterator of framed elements.
	 */
	public <T extends FramedEdge> Iterator<T> frame(Class<T> kind);

	/**
	 * Return a list of all the objects in the pipeline.
	 * 
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return a list of all the objects
	 */
	public <T extends FramedEdge> List<T> toList(Class<T> kind);

	
	
	/**
	 * Add an LabelPipe to the end of the Pipeline.
	 * Emit the label of the incoming edge.
	 *
	 * @return the extended Pipeline
	 */
	public abstract FramedTraversal<String, SE> label();

	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * Add an AndFilterPipe to the end the Pipeline.
	 * If the internal pipes all yield objects, then the object is not filtered.
	 * The provided pipes are provided the object as their starts.
	 *
	 * @param pipes the internal pipes of the AndFilterPipe
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> and(Pipe<Edge, ?>... pipes);


	/**
	 * Add a DuplicateFilterPipe to the end of the Pipeline.
	 * Will only emit the object if it has not been seen before.
	 *
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> dedup();

	/**
	 * Add a DuplicateFilterPipe to the end of the Pipeline.
	 * Will only emit the object if the object generated by its function hasn't been seen before.
	 *
	 * @param dedupFunction a function to call on the object to yield the object to dedup on
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> dedup(PipeFunction<Edge, ?> dedupFunction);

	/**
	 * Add an ExceptFilterPipe to the end of the Pipeline.
	 * Will only emit the object if it is not in the provided collection.
	 *
	 * @param collection the collection except from the stream
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> except(Collection<Edge> collection);

	/**
	 * Add an ExceptFilterPipe to the end of the Pipeline.
	 * Will only emit the object if it is not equal to any of the objects contained at the named steps.
	 *
	 * @param namedSteps the named steps in the pipeline
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> except(String... namedSteps);

	/**
	 * Add an FilterFunctionPipe to the end of the Pipeline.
	 * The serves are an arbitrary filter where the filter criteria is provided by the filterFunction.
	 *
	 * @param filterFunction the filter function of the pipe
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> filter(PipeFunction<Edge, Boolean> filterFunction);

	/**
	 * Add an OrFilterPipe to the end the Pipeline.
	 * Will only emit the object if one or more of the provides pipes yields an object.
	 * The provided pipes are provided the object as their starts.
	 *
	 * @param pipes the internal pipes of the OrFilterPipe
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> or(Pipe<Edge, ?>... pipes);

	/**
	 * Add a RandomFilterPipe to the end of the Pipeline.
	 * A biased coin toss determines if the object is emitted or not.
	 *
	 * @param bias the bias of the random coin
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> random(Double bias);

	/**
	 * Add a RageFilterPipe to the end of the Pipeline.
	 * Analogous to a high/low index lookup.
	 *
	 * @param low  the low end of the range
	 * @param high the high end of the range
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> range(int low, int high);

	/**
	 * Add a RetainFilterPipe to the end of the Pipeline.
	 * Will emit the object only if it is in the provided collection.
	 *
	 * @param collection the collection to retain
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> retain(Collection<?> collection);

	/**
	 * Add a RetainFilterPipe to the end of the Pipeline.
	 * Will only emit the object if it is equal to any of the objects contained at the named steps.
	 *
	 * @param namedSteps the named steps in the pipeline
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> retain(String... namedSteps);


	
	
	/**
	 * Add an AggregatePipe to the end of the Pipeline.
	 * The objects prior to aggregate are greedily collected into an ArrayList.
	 *
	 * @return the extended Pipeline
	 */
	public abstract FramedTraversal<Edge, Collection<Edge>> aggregate();

	/**
	 * Add an AggregatePipe to the end of the Pipeline.
	 * The objects prior to aggregate are greedily collected into the provided collection.
	 *
	 * @param aggregate the collection to aggregate results into
	 * @return the extended Pipeline
	 */
	public abstract FramedTraversal<Edge, Collection<Edge>> aggregate(Collection<Edge> aggregate);

	/**
	 * Add an AggregatePipe to the end of the Pipeline.
	 * The results of the function evaluated on the objects prior to the aggregate are greedily collected into the provided collection.
	 *
	 * @param aggregate         the collection to aggregate results into
	 * @param aggregateFunction the function to run over each object prior to insertion into the aggregate
	 * @return the extended Pipeline
	 */
	public abstract <N> FramedTraversal<Edge, Collection<N>> aggregate(Collection<Edge> aggregate, PipeFunction<Edge, N> aggregateFunction);

	/**
	 * Add an AggregatePipe to the end of the Pipeline.
	 * The results of the function evaluated on the objects prior to the aggregate are greedily collected into an ArrayList.
	 *
	 * @param aggregateFunction the function to run over each object prior to insertion into the aggregate
	 * @return the extended Pipeline
	 */
	public abstract <N> FramedTraversal<Edge, Collection<N>> aggregate(PipeFunction<Edge, N> aggregateFunction);




	/**
	 * Add a GroupByPipe to the end of the Pipeline.
	 * Group the objects inputted objects according to a key generated from the object and a value generated from the object.
	 * The grouping map has values that are Lists.
	 *
	 * @param map           the map to store the grouping in
	 * @param keyFunction   the function that generates the key from the object
	 * @param valueFunction the function that generates the value from the function
	 * @return the extended Pipeline
	 */
	public abstract <K, V> FramedTraversal<Edge, Map<K, List<V>>> groupBy(Map<K, List<V>> map, PipeFunction<Edge, K> keyFunction, PipeFunction<Edge, V> valueFunction);

	/**
	 * Add a GroupByPipe to the end of the Pipeline.
	 * Group the objects inputted objects according to a key generated from the object and a value generated from the object.
	 * The grouping map has values that are Lists.
	 *
	 * @param keyFunction   the function that generates the key from the object
	 * @param valueFunction the function that generates the value from the function
	 * @return the extended Pipeline
	 */
	public abstract <K, V> FramedTraversal<Edge, Map<K, List<V>>> groupBy(PipeFunction<Edge, K> keyFunction, PipeFunction<Edge, V> valueFunction);

	/**
	 * Add a GroupByReducePipe to the end of the Pipeline.
	 * Group the objects inputted objects according to a key generated from the object and a value generated from the object.
	 * The grouping map has values that are Lists.
	 * When the pipe has no more incoming objects, apply the reduce function to the keyed Lists.
	 * The sideEffect is only fully available when the pipe is empty.
	 *
	 * @param reduceMap      a map to perform the reduce operation on (good for having a later reference)
	 * @param keyFunction    the function that generates the key from the object
	 * @param valueFunction  the function that generates the value from the function
	 * @param reduceFunction the function that reduces the value lists
	 * @return the extended Pipeline
	 */
	public abstract <K, V, V2> FramedTraversal<Edge, Map<K, V2>> groupBy(Map<K, V2> reduceMap, PipeFunction<Edge, K> keyFunction, PipeFunction<Edge, V> valueFunction,
			PipeFunction<List<V>, V2> reduceFunction);

	/**
	 * Add a GroupByReducePipe to the end of the Pipeline.
	 * Group the objects inputted objects according to a key generated from the object and a value generated from the object.
	 * The grouping map has values that are Lists.
	 * When the pipe has no more incoming objects, apply the reduce function to the keyed Lists.
	 * The sideEffect is only fully available when the pipe is empty.
	 *
	 * @param keyFunction    the function that generates the key from the object
	 * @param valueFunction  the function that generates the value from the function
	 * @param reduceFunction the function that reduces the value lists
	 * @return the extended Pipeline
	 */
	public abstract <K, V, V2> FramedTraversal<Edge, Map<K, V2>> groupBy(PipeFunction<Edge, K> keyFunction, PipeFunction<Edge, V> valueFunction,
			PipeFunction<List<V>, V2> reduceFunction);

	/**
	 * Add a GroupCountPipe or GroupCountFunctionPipe to the end of the Pipeline.
	 * A map is maintained of counts.
	 * The map keys are determined by the function on the incoming object.
	 * The map values are determined by the function on the incoming object (getA()) and the previous value (getB()).
	 *
	 * @param map           a provided count map
	 * @param keyFunction   the key function to determine map key
	 * @param valueFunction the value function to determine map value
	 * @return the extended Pipeline
	 */
	public abstract <K> FramedTraversal<Edge, Map<K, Number>> groupCount(Map<K, Number> map, PipeFunction<Edge, K> keyFunction,
			PipeFunction<Pair<Edge, Number>, Number> valueFunction);

	/**
	 * Add a GroupCountPipe or GroupCountFunctionPipe to the end of the Pipeline.
	 * map is maintained of counts.
	 * The map keys are determined by the function on the incoming object.
	 * The map values are determined by the function on the incoming object (getA()) and the previous value (getB()).
	 *
	 * @param keyFunction   the key function to determine map key
	 * @param valueFunction the value function to determine map value
	 * @return the extended Pipeline
	 */
	public abstract <K> FramedTraversal<Edge, Map<K, Number>> groupCount(PipeFunction<Edge, K> keyFunction, PipeFunction<Pair<Edge, Number>, Number> valueFunction);

	/**
	 * Add a GroupCountPipe or GroupCountFunctionPipe to the end of the Pipeline.
	 * A map is maintained of counts.
	 * The map keys are determined by the function on the incoming object.
	 * The map values are 1 plus the previous value for that key.
	 *
	 * @param map         a provided count map
	 * @param keyFunction the key function to determine map key
	 * @return the extended Pipeline
	 */
	public abstract <K> FramedTraversal<Edge, Map<K, Number>> groupCount(Map<K, Number> map, PipeFunction<Edge, K> keyFunction);

	/**
	 * Add a GroupCountPipe or GroupCountFunctionPipe to the end of the Pipeline.
	 * A map is maintained of counts.
	 * The map keys are determined by the function on the incoming object.
	 * The map values are 1 plus the previous value for that key.
	 *
	 * @param keyFunction the key function to determine map key
	 * @return the extended Pipeline
	 */
	public abstract <K> FramedTraversal<Edge, Map<K, Number>> groupCount(PipeFunction<Edge, K> keyFunction);

	/**
	 * Add a GroupCountPipe to the end of the Pipeline.
	 * A map is maintained of counts.
	 * The map keys are the incoming objects.
	 * The map values are 1 plus the previous value for that key.
	 *
	 * @param map a provided count map
	 * @return the extended Pipeline
	 */
	public abstract FramedTraversal<Edge, Map<Edge, Number>> groupCount(Map<Edge, Number> map);

	/**
	 * Add a GroupCountPipe to the end of the Pipeline.
	 * A map is maintained of counts.
	 * The map keys are the incoming objects.
	 * The map values are 1 plus the previous value for that key.
	 *
	 * @return the extended Pipeline
	 */
	public abstract FramedTraversal<Edge, Map<Edge, Number>> groupCount();
	
	

	/**
	 * Add a SideEffectFunctionPipe to the end of the Pipeline.
	 * The provided function is evaluated and the incoming object is the outgoing object.
	 *
	 * @param sideEffectFunction the function of the pipe
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> sideEffect(PipeFunction<Edge, ?> sideEffectFunction);

	/**
	 * Add a StorePipe to the end of the Pipeline.
	 * Lazily store the incoming objects into the provided collection.
	 *
	 * @param storage the collection to store results into
	 * @return the extended Pipeline
	 */
	public abstract FramedTraversal<Edge, Collection<Edge>> store(Collection<Edge> storage);

	/**
	 * Add a StorePipe to the end of the Pipeline.
	 * Lazily store the object returned by the function over the incoming object into the provided collection.
	 *
	 * @param storage         the collection to store results into
	 * @param storageFunction the function to run over each object prior to insertion into the storage collection
	 * @return the extended Pipeline
	 */
	public abstract <N> FramedTraversal<Edge, Collection<N>> store(Collection<N> storage, PipeFunction<Edge, N> storageFunction);

	/**
	 * Add an StorePipe to the end of the Pipeline.
	 * An ArrayList storage collection is provided and filled lazily with incoming objects.
	 *
	 * @return the extended Pipeline
	 */
	public abstract FramedTraversal<Edge, Collection<Object>> store();

	/**
	 * Add a StorePipe to the end of the Pipeline.
	 * An ArrayList storage collection is provided and filled lazily with the return of the function evaluated over the incoming objects.
	 *
	 * @param storageFunction the function to run over each object prior to insertion into the storage collection
	 * @return the extended Pipeline
	 */
	public abstract <N> FramedTraversal<Edge, Collection<N>> store(PipeFunction<Edge, N> storageFunction);

	/**
	 * Add a TablePipe to the end of the Pipeline.
	 * This step is used for grabbing previously seen objects the pipeline as identified by as-steps.
	 *
	 * @param table           the table to fill
	 * @param stepNames       the partition steps to include in the filling
	 * @param columnFunctions the post-processing function for each column
	 * @return the extended Pipeline
	 */
	public abstract FramedTraversal<Edge, Table> table(Table table, Collection<String> stepNames, PipeFunction<?, ?>... columnFunctions);

	/**
	 * Add a TablePipe to the end of the Pipeline.
	 * This step is used for grabbing previously seen objects the pipeline as identified by as-steps.
	 *
	 * @param table           the table to fill
	 * @param columnFunctions the post-processing function for each column
	 * @return the extended Pipeline
	 */
	public abstract FramedTraversal<Edge, Table> table(Table table, PipeFunction<?, ?>... columnFunctions);

	/**
	 * Add a TablePipe to the end of the Pipeline.
	 * This step is used for grabbing previously seen objects the pipeline as identified by as-steps.
	 *
	 * @param columnFunctions the post-processing function for each column
	 * @return the extended Pipeline
	 */
	public abstract FramedTraversal<Edge, Table> table(PipeFunction<?, ?>... columnFunctions);

	/**
	 * Add a TablePipe to the end of the Pipeline.
	 * This step is used for grabbing previously seen objects the pipeline as identified by as-steps.
	 *
	 * @param table the table to fill
	 * @return the extended Pipeline
	 */
	public abstract FramedTraversal<Edge, Table> table(Table table);

	/**
	 * Add a TablePipe to the end of the Pipeline.
	 * This step is used for grabbing previously seen objects the pipeline as identified by as-steps.
	 *
	 * @return the extended Pipeline
	 */
	public abstract FramedTraversal<Edge, Table> table();

	/**
	 * Add a TreePipe to the end of the Pipeline
	 * This step maintains an internal tree representation of the paths that have flowed through the step.
	 *
	 * @param tree            an embedded Map data structure to store the tree representation in
	 * @param branchFunctions functions to apply to each path object in a round robin fashion
	 * @return the extended Pipeline
	 */
	public abstract <N> FramedTraversal<Edge, Tree<N>> tree(Tree<N> tree, PipeFunction<?, ?>... branchFunctions);

	/**
	 * Add a TreePipe to the end of the Pipeline
	 * This step maintains an internal tree representation of the paths that have flowed through the step.
	 *
	 * @param branchFunctions functions to apply to each path object in a round robin fashion
	 * @return the extended Pipeline
	 */
	public abstract FramedTraversal<Edge, Tree<Object>> tree(PipeFunction<?, ?>... branchFunctions);

	/**
	 * Add a GatherPipe to the end of the Pipeline.
	 * All the objects previous to this step are aggregated in a greedy fashion and emitted as a List.
	 *
	 * @return the extended Pipeline
	 */
	public abstract FramedTraversal<List<Edge>, ?> gather();

	
	/**
	 * Add an IdentityPipe to the end of the Pipeline.
	 * Useful in various situations where a step is needed without processing.
	 * For example, useful when two as-steps are needed in a row.
	 *
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> identity();

	/**
	 * Add a MemoizePipe to the end of the Pipeline.
	 * This step will hold a Map of the objects that have entered into its pipeline section.
	 * If an input is seen twice, then the map stored output is emitted instead of recomputing the pipeline section.
	 *
	 * @param namedStep the name of the step previous to memoize to
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> memoize(String namedStep);


	/**
	 * Add a MemoizePipe to the end of the Pipeline.
	 * This step will hold a Map of the objects that have entered into its pipeline section.
	 * If an input is seen twice, then the map stored output is emitted instead of recomputing the pipeline section.
	 *
	 * @param namedStep the name of the step previous to memoize to
	 * @param map       the memoization map
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> memoize(String namedStep, Map<?, ?> map);


	/**
	 * Add an OrderPipe to the end of the Pipeline.
	 * This step will sort the objects in the stream in a default Comparable order.
	 *
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> order();

	/**
	 * Add an OrderPipe to the end of the Pipeline.
	 * This step will sort the objects in the stream in a default Comparable order.
	 *
	 * @param order if the stream is composed of comparable objects, then increment or decrement can be specified
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> order(TransformPipe.Order order);

	/**
	 * Add an OrderPipe to the end of the Pipeline.
	 * This step will sort the objects in the stream in a default Comparable order.
	 *
	 * @param order if the stream is composed of comparable objects, then increment or decrement can be specified
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> order(Tokens.T order);

	/**
	 * Add an OrderPipe to the end of the Pipeline.
	 * This step will sort the objects in the stream according to a comparator defined in the provided function.
	 *
	 * @param compareFunction a comparator function of two objects of type E
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> order(PipeFunction<Pair<Edge, Edge>, Integer> compareFunction);

	
	/**
	 * Wrap the previous step in an AsPipe.
	 * Useful for naming steps and is used in conjunction with various other steps including: loop, select, back, table, etc.
	 *
	 * @param name the name of the AsPipe
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> as(String name);
	
	
	/**
	 * Add a CyclicPathFilterPipe to the end of the Pipeline.
	 * If the object's path is repeating (looping), then the object is filtered.
	 * Thus, what is emitted are those objects whose history is composed of unique objects.
	 *
	 * @return the extended Pipeline
	 */
	public abstract FramedEdgeTraversal<?> simplePath();

}
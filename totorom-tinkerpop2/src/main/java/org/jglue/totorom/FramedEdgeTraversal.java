package org.jglue.totorom;

import java.util.Iterator;
import java.util.List;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.gremlin.Tokens;

public interface FramedEdgeTraversal extends FramedTraversal<Edge> {

	public FramedEdgeTraversal has(String key);

	public FramedEdgeTraversal has(String key, Object value);

	public FramedEdgeTraversal has(String key, Tokens.T compareToken,
			Object value);

	public FramedEdgeTraversal has(String key, Predicate predicate,
			Object value);

	public FramedEdgeTraversal hasNot(String key);

	public FramedEdgeTraversal hasNot(String key, Object value);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tinkerpop.gremlin.java.GremlinPipeline#interval(java.lang.String,
	 * java.lang.Comparable, java.lang.Comparable)
	 */
	public <C> FramedEdgeTraversal interval(String key,
			Comparable<C> startValue, Comparable<C> endValue);

	/**
	 * Add an identity step.
	 * 
	 * @return The traversal.
	 */
	public FramedEdgeTraversal identity();

	public FramedVertexTraversal inV();

	public FramedVertexTraversal outV();

	public FramedVertexTraversal bothV();

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

	public FramedEdgeTraversal as(String name);
	
	/**
	 * Add an LabelPipe to the end of the Pipeline.
	 * Emit the label of the incoming edge.
	 *
	 * @return the extended Pipeline
	 */
	public abstract FramedTraversal label();


}
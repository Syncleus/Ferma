package org.jglue.totorom;

import java.util.Iterator;
import java.util.List;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.Tokens;

public interface FramedVertexTraversal<S, E> extends FramedTraversal<S, E> {

	public FramedVertexTraversal<S, E> has(String key);

	public FramedVertexTraversal<S, E> has(String key, Object value);

	public FramedVertexTraversal<S, E> has(String key, Tokens.T compareToken,
			Object value);

	public FramedVertexTraversal<S, E> has(String key, Predicate predicate,
			Object value);

	public FramedVertexTraversal<S, E> hasNot(String key);

	public FramedVertexTraversal<S, E> hasNot(String key, Object value);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tinkerpop.gremlin.java.GremlinPipeline#interval(java.lang.String,
	 * java.lang.Comparable, java.lang.Comparable)
	 */
	public <C> FramedVertexTraversal<S, E> interval(String key,
			Comparable<C> startValue, Comparable<C> endValue);

	/**
	 * Add an identity step.
	 * 
	 * @return The traversal.
	 */
	public FramedVertexTraversal<S, E> identity();

	public FramedVertexTraversal<S, E> out(int branchFactor, String... labels);

	public FramedVertexTraversal<S, E> out(String... labels);

	public FramedVertexTraversal<S, E> in(int branchFactor, String... labels);

	public FramedVertexTraversal<S, E> in(String... labels);

	public FramedVertexTraversal<S, E> both(int branchFactor, String... labels);

	public FramedVertexTraversal<S, E> both(String... labels);

	public FramedEdgeTraversal<S, E> outE(int branchFactor, String... labels);

	public FramedEdgeTraversal<S, E> outE(String... labels);

	public FramedEdgeTraversal<S, E> inE(int branchFactor, String... labels);

	public FramedEdgeTraversal<S, E> inE(String... labels);

	public FramedEdgeTraversal<S, E> bothE(int branchFactor, String... labels);

	public FramedEdgeTraversal<S, E> bothE(String... labels);


	/**
	 * Get the next object emitted from the pipeline. If no such object exists,
	 * then a NoSuchElementException is thrown.
	 * 
	 * @param kind
	 *            The type of frame for the element.
	 * @return the next emitted object
	 */
	public <T extends FramedVertex> T nextVertex(Class<T> kind);

	/**
	 * Return the next X objects in the traversal as a list.
	 * 
	 * @param number
	 *            the number of objects to return
	 * @param kind
	 *            the type of frame to for each element.
	 * @return a list of X objects (if X objects occur)
	 */
	public <T extends FramedVertex> List<T> next(int amount, Class<T> kind);

	/**
	 * Return an iterator of framed elements.
	 * 
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return An iterator of framed elements.
	 */
	public <T extends FramedVertex> Iterator<T> frame(Class<T> kind);

	/**
	 * Return a list of all the objects in the pipeline.
	 * 
	 * @param kind
	 *            The kind of framed elements to return.
	 * @return a list of all the objects
	 */
	public <T extends FramedVertex> List<T> toList(Class<T> kind);

}
package org.jglue.totorom;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.Tokens;
import com.tinkerpop.pipes.util.Pipeline;

public interface FramedVertexTraversal extends FramedTraversal<Vertex> {

	public FramedVertexTraversal has(String key);

	public FramedVertexTraversal has(String key, Object value);

	public FramedVertexTraversal has(String key, Tokens.T compareToken, Object value);

	public FramedVertexTraversal has(String key, Predicate predicate, Object value);

	public FramedVertexTraversal hasNot(String key);

	public FramedVertexTraversal hasNot(String key, Object value);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tinkerpop.gremlin.java.GremlinPipeline#interval(java.lang.String,
	 * java.lang.Comparable, java.lang.Comparable)
	 */
	
	public <C> FramedVertexTraversal interval(String key, Comparable<C> startValue, Comparable<C> endValue);

	/**
	 * Add an identity step.
	 * 
	 * @return The traversal.
	 */
	public FramedVertexTraversal identity();

	public FramedVertexTraversal out(int branchFactor, String... labels);

	public FramedVertexTraversal out(String... labels);

	public FramedVertexTraversal in(int branchFactor, String... labels);

	public FramedVertexTraversal in(String... labels);

	public FramedVertexTraversal both(int branchFactor, String... labels);

	public FramedVertexTraversal both(String... labels);

	public FramedEdgeTraversal outE(int branchFactor, String... labels);

	public FramedEdgeTraversal outE(String... labels);

	public FramedEdgeTraversal inE(int branchFactor, String... labels);

	public FramedEdgeTraversal inE(String... labels);

	public FramedEdgeTraversal bothE(int branchFactor, String... labels);

	public FramedEdgeTraversal bothE(String... labels);

	/**
	 * Get the next object emitted from the pipeline. If no such object exists,
	 * then a NoSuchElementException is thrown.
	 * 
	 * @param kind
	 *            The type of frame for the element.
	 * @return the next emitted object
	 */
	public <T extends FramedVertex> T next(Class<T> kind);

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


	/**
	 * Add a LinkPipe to the end of the Pipeline.
	 * Emit the incoming vertex, but have other vertex provide an outgoing edge to incoming vertex.
	 *
	 * @param label     the edge label
	 * @param namedStep the step name that has the other vertex to link to
	 * @return the extended Pipeline
	 */
	public abstract FramedVertexTraversal linkOut(String label, String namedStep);

	/**
	 * Add a LinkPipe to the end of the Pipeline.
	 * Emit the incoming vertex, but have other vertex provide an incoming edge to incoming vertex.
	 *
	 * @param label     the edge label
	 * @param namedStep the step name that has the other vertex to link to
	 * @return the extended Pipeline
	 */
	public abstract FramedVertexTraversal linkIn(String label, String namedStep);

	/**
	 * Add a LinkPipe to the end of the Pipeline.
	 * Emit the incoming vertex, but have other vertex provide an incoming and outgoing edge to incoming vertex.
	 *
	 * @param label     the edge label
	 * @param namedStep the step name that has the other vertex to link to
	 * @return the extended Pipeline
	 */
	public abstract FramedVertexTraversal linkBoth(String label, String namedStep);

	/**
	 * Add a LinkPipe to the end of the Pipeline.
	 * Emit the incoming vertex, but have other vertex provide an outgoing edge to incoming vertex.
	 *
	 * @param label the edge label
	 * @param other the other vertex
	 * @return the extended Pipeline
	 */
	public abstract FramedVertexTraversal linkOut(String label, Vertex other);
	
	/**
	 * Add a LinkPipe to the end of the Pipeline.
	 * Emit the incoming vertex, but have other vertex provide an outgoing edge to incoming vertex.
	 *
	 * @param label the edge label
	 * @param other the other vertex
	 * @return the extended Pipeline
	 */
	public abstract FramedVertexTraversal linkOut(String label, FramedVertex other);

	/**
	 * Add a LinkPipe to the end of the Pipeline.
	 * Emit the incoming vertex, but have other vertex provide an incoming edge to incoming vertex.
	 *
	 * @param label the edge label
	 * @param other the other vertex
	 * @return the extended Pipeline
	 */
	public abstract FramedVertexTraversal linkIn(String label, Vertex other);

	/**
	 * Add a LinkPipe to the end of the Pipeline.
	 * Emit the incoming vertex, but have other vertex provide an incoming and outgoing edge to incoming vertex.
	 *
	 * @param label the edge label
	 * @param other the other vertex
	 * @return the extended Pipeline
	 */
	public abstract FramedVertexTraversal linkBoth(String label, Vertex other);

	/**
	 * Add a LinkPipe to the end of the Pipeline.
	 * Emit the incoming vertex, but have other vertex provide an incoming edge to incoming vertex.
	 *
	 * @param label the edge label
	 * @param other the other vertex
	 * @return the extended Pipeline
	 */
	FramedVertexTraversal linkIn(String label, FramedVertex other);
	
	/**
	 * Add a LinkPipe to the end of the Pipeline.
	 * Emit the incoming vertex, but have other vertex provide an incoming and outgoing edge to incoming vertex.
	 *
	 * @param label the edge label
	 * @param other the other vertex
	 * @return the extended Pipeline
	 */
	FramedVertexTraversal linkBoth(String label, FramedVertex other);
	
	public FramedVertexTraversal as(String name);


}
package org.jglue.totorom;

import java.util.Iterator;
import java.util.List;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.Tokens;

public interface FramedTraversal<S, E> {

	public FramedVertexTraversal<S, E> V();

	public FramedEdgeTraversal<S, E> E();

	/**
	 * Traversal over a of vertices in the graph.
	 * 
	 * @param ids
	 *            The ids of the vertices.
	 * @return The traversal.
	 */
	public FramedVertexTraversal<S, E> v(Object... ids);

	/**
	 * Traversal over a list of edges in the graph.
	 * 
	 * @param ids
	 *            The ids of the edges.
	 * @return The traversal.
	 */
	public FramedEdgeTraversal<S, E> e(Object... ids);

	
	/**
	 * Add an identity step.
	 * 
	 * @return The traversal.
	 */
	public FramedTraversal<S, E> identity();

	public long count();
	
}
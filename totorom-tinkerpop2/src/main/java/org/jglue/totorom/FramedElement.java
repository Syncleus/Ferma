package org.jglue.totorom;

import java.util.Set;

import com.tinkerpop.blueprints.Element;

/**
 * The base of all framed elements.
 * @author Bryn Cooke (http://jglue.org)
 */

public abstract class FramedElement {

	private Element element;
	private FramedGraph graph;

	protected void init(FramedGraph graph, Element element) {
		this.graph = graph;
		this.element = element;
	}

	protected void init() {

	}

	/**
	 * @return The id of this element.
	 */
	protected <N> N getId() {
		return (N)element.getId();
	}

	/**
	 * @return The property keys of this element.
	 */
	protected Set<String> getPropertyKeys() {
		return element.getPropertyKeys();
	}

	/**
	 * Remove this element from the graph.
	 */
	protected void remove() {
		element.remove();
	}

	/**
	 * @return The underlying element.
	 */
	protected Element element() {
		return element;
	}

	/**
	 * @return The underlying graph.
	 */
	protected FramedGraph graph() {
		return graph;
	}

	/**
	 * Return a property value.
	 * 
	 * @param name
	 *            The name of the property.
	 * @return the value of the property or null if none was present.
	 */
	protected <T> T getProperty(String name) {
		return element.getProperty(name);
	}

	/**
	 * Set a property value.
	 * 
	 * @param name
	 *            The name of the property.
	 * @param value
	 *            The value of the property.
	 */
	protected void setProperty(String name, Object value) {
		element.setProperty(name, value);
	}

	/**
	 * Query over all vertices in the graph.
	 * 
	 * @return The query.
	 */
	protected FramedVertexTraversal<?, ?, ?> V() {
		return graph.V();
	}

	/**
	 * Query over all edges in the graph.
	 * 
	 * @return The query.
	 */
	protected FramedEdgeTraversal<?, ?, ?> E() {
		return graph.E();
	}

	/**
	 * Query over a list of vertices in the graph.
	 * 
	 * @param ids
	 *            The ids of the vertices.
	 * @return The query.
	 */
	protected FramedVertexTraversal<?, ?, ?> v(final Object... ids) {
		return graph.v(ids);
	}

	/**
	 * Query over a list of edges in the graph.
	 * 
	 * @param ids
	 *            The ids of the edges.
	 * @return The query.
	 */
	protected FramedEdgeTraversal<?, ?, ?> e(final Object... ids) {
		return graph.e(ids);
	}

	@Override
	public int hashCode() {
		return element.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FramedElement other = (FramedElement) obj;
		if (element == null) {
			if (other.element != null)
				return false;
		} else if (!element.equals(other.element))
			return false;
		return true;
	}

	protected <N> N getId(Class<N> clazz) {

		return (N) getId();
	}

	@Override
	public String toString() {
	
		return element().toString();
	}
}

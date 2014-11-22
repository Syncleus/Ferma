package com.syncleus.ferma;

import java.util.Set;

import com.tinkerpop.blueprints.Element;

/**
 * The base of all framed elements.
 * 
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
	public <N> N getId() {
		return (N) element.getId();
	}

	/**
	 * @return The property keys of this element.
	 */
	public Set<String> getPropertyKeys() {
		return element.getPropertyKeys();
	}

	/**
	 * Remove this element from the graph.
	 */
	public void remove() {
		element.remove();
	}

	/**
	 * @return The underlying element.
	 */
	public Element element() {
		return element;
	}

	/**
	 * @return The underlying graph.
	 */
	public FramedGraph graph() {
		return graph;
	}

	/**
	 * Return a property value.
	 * 
	 * @param name
	 *            The name of the property.
	 * @return the value of the property or null if none was present.
	 */
	public <T> T getProperty(String name) {
		return element.getProperty(name);
	}

	/**
	 * Return a property value.
	 * 
	 * @param name
	 *            The name of the property.
	 * @param type
	 *            The type of the property.
	 * 
	 * @return the value of the property or null if none was present.
	 */
	public <T> T getProperty(String name, Class<T> type) {
		if (type.isEnum()) {
			return (T) Enum.valueOf((Class<Enum>) type, (String) element.getProperty(name));
		}

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
	public void setProperty(String name, Object value) {
		if (value == null) {
			element.removeProperty(name);
		} else {
			if (value instanceof Enum) {
				element.setProperty(name, value.toString());
			} else {
				element.setProperty(name, value);
			}
		}
	}

	/**
	 * Query over all vertices in the graph.
	 * 
	 * @return The query.
	 */
	public VertexTraversal<?, ?, ?> V() {
		return graph.V();
	}

	/**
	 * Query over all edges in the graph.
	 * 
	 * @return The query.
	 */
	public EdgeTraversal<?, ?, ?> E() {
		return graph.E();
	}

	/**
	 * Query over a list of vertices in the graph.
	 * 
	 * @param ids
	 *            The ids of the vertices.
	 * @return The query.
	 */
	public VertexTraversal<?, ?, ?> v(final Object... ids) {
		return graph.v(ids);
	}

	/**
	 * Query over a list of edges in the graph.
	 * 
	 * @param ids
	 *            The ids of the edges.
	 * @return The query.
	 */
	public EdgeTraversal<?, ?, ?> e(final Object... ids) {
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

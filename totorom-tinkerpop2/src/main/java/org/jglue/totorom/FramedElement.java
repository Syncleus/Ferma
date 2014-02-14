package org.jglue.totorom;

import java.util.Set;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Bryn Cooke (http://jglue.org)
 */

public abstract class FramedElement<E extends Element> {
    private E element;
    private FramedGraph graph;


    @SuppressWarnings("unchecked")
	protected void init(FramedGraph graph, Element element) {
        this.graph = graph;
        this.element = (E)element;
    }


    protected void init() {

    }


    /**
     * @return The id of this element.
     */
    protected Object getId() {
        return element.getId();
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
    protected E element() {
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
     * @param name The name of the property.
     * @return the value of the property or null if none was present.
     */
    protected <T> T getProperty(String name) {
        return element.getProperty(name);
    }

    /**
     * Set a property value.
     * @param name The name of the property.
     * @param value The value of the property.
     */
    protected void setProperty(String name, Object value) {
        element.setProperty(name, value);
    }

    /**
     * Query over all vertices in the graph.
     * @return The query.
     */
    protected FramedTraversal<Vertex, Vertex> V() {
        return graph.V();
    }

    /**
     * Query over all edges in the graph.
     * @return The query.
     */
    protected FramedTraversal<Edge, Edge> E() {
        return graph.E();
    }

    /**
     * Query over a list of vertices in the graph.
     * @param ids The ids of the vertices.
     * @return The query.
     */
    public FramedTraversal<Vertex, Vertex> v(final Object... ids) {
    	return graph.v(ids);
    }

    /**
     * Query over a list of edges in the graph.
     * @param ids The ids of the edges.
     * @return The query. 
     */
    public FramedTraversal<Edge, Edge> e(final Object... ids) {
    	return graph.e(ids);
    }


}

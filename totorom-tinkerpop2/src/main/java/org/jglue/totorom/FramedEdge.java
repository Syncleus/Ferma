package org.jglue.totorom;

import com.tinkerpop.blueprints.Edge;

/**
 * The base class that all edge frames must extend.
 * @author Bryn Cooke (http://jglue.org)
 */
public abstract class FramedEdge extends FramedElement {


	protected Edge element() {
		return (Edge) super.element();
	};
	
    /**
     * @return The label associated with this edge
     */
    protected String getLabel() {
        return element().getLabel();
    }

    /**
     * @return The in vertex for this edge.
     */
    protected VertexTraversal<?, ?, ?> inV() {
        return new TraversalImpl(graph(), this).castToEdges().inV();
    }

    /**
     * @return The out vertex of this edge.
     */
    protected VertexTraversal<?, ?, ?> outV() {
        return new TraversalImpl(graph(), this).castToEdges().outV();
    }

    /**
     * @return The vertices for this edge.
     */
    protected VertexTraversal<?, ?, ?> bothV() {
        return new TraversalImpl(graph(), this).castToEdges().bothV();
    }

    /**
	 * Shortcut to get frameTraversal of current element
	 * 
	 * @return
	 */
	protected EdgeTraversal<?, ?, ?> traversal() {
		return new TraversalImpl(graph(), this).castToEdges();
	}
}

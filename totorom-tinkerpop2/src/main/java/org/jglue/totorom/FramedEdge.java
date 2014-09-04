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
    protected FramedVertexTraversal<?, ?, ?> inV() {
        return new FramedTraversalImpl(graph(), this).castToEdges().inV();
    }

    /**
     * @return The out vertex of this edge.
     */
    protected FramedVertexTraversal<?, ?, ?> outV() {
        return new FramedTraversalImpl(graph(), this).castToEdges().outV();
    }

    /**
     * @return The vertices for this edge.
     */
    protected FramedVertexTraversal<?, ?, ?> bothV() {
        return new FramedTraversalImpl(graph(), this).castToEdges().bothV();
    }

    /**
	 * Shortcut to get frameTraversal of current element
	 * 
	 * @return
	 */
	protected FramedEdgeTraversal<?, ?, ?> traversal() {
		return new FramedTraversalImpl(graph(), this).castToEdges();
	}
}

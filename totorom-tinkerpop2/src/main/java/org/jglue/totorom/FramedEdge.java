package org.jglue.totorom;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Vertex;

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
    protected FramedVertexTraversal<Edge, Vertex> inV() {
        return new FramedTraversalImpl<Edge, Vertex>(graph(), element()).inV();
    }

    /**
     * @return The out vertex of this edge.
     */
    protected FramedVertexTraversal<Edge, Vertex> outV() {
        return new FramedTraversalImpl<Edge, Vertex>(graph(), element()).outV();
    }

    /**
     * @return The vertices for this edge.
     */
    protected FramedVertexTraversal<Edge, Vertex> bothV() {
        return new FramedTraversalImpl<Edge, Vertex>(graph(), element()).bothV();
    }


}

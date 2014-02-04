package com.tinkerpop.frames;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Bryn Cooke (http://jglue.org)
 */

public abstract class FramedEdge extends FramedElement<Edge> {


    protected String getLabel() {
        return element().getLabel();
    }

    protected FramedGremlin<Edge, Vertex> inV() {
        return new FramedGremlin(graph(), element()).inV();
    }

    protected FramedGremlin<Edge, Vertex> outV() {
        return new FramedGremlin(graph(), element()).outV();
    }

    protected FramedGremlin<Edge, Vertex> bothV() {
        return new FramedGremlin(graph(), element()).bothV();
    }


}

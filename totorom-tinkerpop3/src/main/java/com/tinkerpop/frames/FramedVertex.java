package com.tinkerpop.frames;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Property;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.query.VertexQuery;

import java.util.Collection;

/**
 * @author Bryn Cooke (http://jglue.org)
 */

public abstract class FramedVertex extends FramedElement<Vertex> {



    protected <T extends FramedEdge> T addEdge(String label, FramedVertex inVertex, Class<T> kind) {

        Edge edge = element().addEdge(label, inVertex.element());
        T framedEdge = graph().frameElement(edge, kind);
        framedEdge.init();
        return framedEdge;

    }


    protected FramedGremlin<Vertex, Vertex> out(final int branchFactor, final String... labels) {
        return new FramedGremlin<>(graph(), element()).out(branchFactor, labels);
    }

    protected FramedGremlin<Vertex, Vertex> out(final String... labels) {
        return new FramedGremlin<>(graph(), element()).out(labels);
    }

    protected FramedGremlin<Vertex, Vertex> in(final int branchFactor, final String... labels) {
        return new FramedGremlin<>(graph(), element()).in(branchFactor, labels);
    }

    protected FramedGremlin<Vertex, Vertex> in(final String... labels) {
        return new FramedGremlin<>(graph(), element()).in(labels);
    }

    protected FramedGremlin<Vertex, Vertex> both(final int branchFactor, final String... labels) {
        return new FramedGremlin<>(graph(), element()).both(branchFactor, labels);
    }

    protected FramedGremlin<Vertex, Vertex> both(final String... labels) {
        return new FramedGremlin<>(graph(), element()).both(labels);
    }

    protected FramedGremlin<Vertex, Edge> outE(final int branchFactor, final String... labels) {
        return new FramedGremlin<>(graph(), element()).outE(branchFactor, labels);
    }

    protected FramedGremlin<Vertex, Edge> outE(final String... labels) {
        return new FramedGremlin<>(graph(), element()).outE(labels);
    }

    protected FramedGremlin<Vertex, Edge> inE(final int branchFactor, final String... labels) {
        return new FramedGremlin<>(graph(), element()).inE(branchFactor, labels);
    }

    protected FramedGremlin<Vertex, Edge> inE(final String... labels) {
        return new FramedGremlin<>(graph(), element()).inE(labels);
    }

    protected FramedGremlin<Vertex, Edge> bothE(final int branchFactor, final String... labels) {
        return new FramedGremlin<>(graph(), element()).bothE(branchFactor, labels);
    }

    protected FramedGremlin<Vertex, Edge> bothE(final String... labels) {
        return new FramedGremlin<>(graph(), element()).bothE(labels);
    }


}

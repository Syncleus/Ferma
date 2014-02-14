package org.jglue.totorom;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * The base class that all vertex frames must extend.
 * @author Bryn Cooke (http://jglue.org)
 */
public abstract class FramedVertex extends FramedElement<Vertex> {



    protected <T extends FramedEdge> T addEdge(String label, FramedVertex inVertex, Class<T> kind) {

        Edge edge = element().addEdge(label, inVertex.element());
        T framedEdge = graph().frameNewElement(edge, kind);
        framedEdge.init();
        return framedEdge;

    }


    protected FramedTraversal<Vertex, Vertex> out(final int branchFactor, final String... labels) {
        return new FramedTraversal<Vertex, Vertex>(graph(), element()).out(branchFactor, labels);
    }

    protected FramedTraversal<Vertex, Vertex> out(final String... labels) {
        return new FramedTraversal<Vertex, Vertex>(graph(), element()).out(labels);
    }

    protected FramedTraversal<Vertex, Vertex> in(final int branchFactor, final String... labels) {
        return new FramedTraversal<Vertex, Vertex>(graph(), element()).in(branchFactor, labels);
    }

    protected FramedTraversal<Vertex, Vertex> in(final String... labels) {
        return new FramedTraversal<Vertex, Vertex>(graph(), element()).in(labels);
    }

    protected FramedTraversal<Vertex, Vertex> both(final int branchFactor, final String... labels) {
        return new FramedTraversal<Vertex, Vertex>(graph(), element()).both(branchFactor, labels);
    }

    protected FramedTraversal<Vertex, Vertex> both(final String... labels) {
        return new FramedTraversal<Vertex, Vertex>(graph(), element()).both(labels);
    }

    protected FramedTraversal<Vertex, Edge> outE(final int branchFactor, final String... labels) {
        return new FramedTraversal<Vertex, Edge>(graph(), element()).outE(branchFactor, labels);
    }

    protected FramedTraversal<Vertex, Edge> outE(final String... labels) {
        return new FramedTraversal<Vertex, Edge>(graph(), element()).outE(labels);
    }

    protected FramedTraversal<Vertex, Edge> inE(final int branchFactor, final String... labels) {
        return new FramedTraversal<Vertex, Edge>(graph(), element()).inE(branchFactor, labels);
    }

    protected FramedTraversal<Vertex, Edge> inE(final String... labels) {
        return new FramedTraversal<Vertex, Edge>(graph(), element()).inE(labels);
    }

    protected FramedTraversal<Vertex, Edge> bothE(final int branchFactor, final String... labels) {
        return new FramedTraversal<Vertex, Edge>(graph(), element()).bothE(branchFactor, labels);
    }

    protected FramedTraversal<Vertex, Edge> bothE(final String... labels) {
        return new FramedTraversal<Vertex, Edge>(graph(), element()).bothE(labels);
    }


}

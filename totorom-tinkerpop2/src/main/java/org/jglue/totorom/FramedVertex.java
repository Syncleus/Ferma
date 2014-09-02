package org.jglue.totorom;

import com.google.common.collect.Lists;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * The base class that all vertex frames must extend.
 * 
 * @author Bryn Cooke (http://jglue.org)
 */
public abstract class FramedVertex extends FramedElement {

	protected Vertex element() {
		return (Vertex) super.element();
	};

	protected <T extends FramedEdge> T addEdge(String label, FramedVertex inVertex, Class<T> kind) {

		Edge edge = element().addEdge(label, inVertex.element());
		T framedEdge = graph().frameNewElement(edge, kind);
		framedEdge.init();
		return framedEdge;
	}

	protected FramedVertexTraversal<?> out(final int branchFactor, final String... labels) {
		return new FramedTraversalImpl(graph(), element()).asVertices().out(branchFactor, labels);
	}

	protected FramedVertexTraversal<?> out(final String... labels) {
		return new FramedTraversalImpl(graph(), element()).asVertices().out(labels);
	}

	protected FramedVertexTraversal<?> in(final int branchFactor, final String... labels) {
		return new FramedTraversalImpl(graph(), element()).asVertices().in(branchFactor, labels);
	}

	protected FramedVertexTraversal<?> in(final String... labels) {
		return new FramedTraversalImpl(graph(), element()).asVertices().in(labels);
	}

	protected FramedVertexTraversal<?> both(final int branchFactor, final String... labels) {
		return new FramedTraversalImpl(graph(), element()).asVertices().both(branchFactor, labels);
	}

	protected FramedVertexTraversal<?> both(final String... labels) {
		return new FramedTraversalImpl(graph(), element()).asVertices().both(labels);
	}

	protected FramedEdgeTraversal<?> outE(final int branchFactor, final String... labels) {
		return new FramedTraversalImpl(graph(), element()).asVertices().outE(branchFactor, labels);
	}

	protected FramedEdgeTraversal<?> outE(final String... labels) {
		return new FramedTraversalImpl(graph(), element()).asVertices().outE(labels);
	}

	protected FramedEdgeTraversal<?> inE(final int branchFactor, final String... labels) {
		return new FramedTraversalImpl(graph(), element()).asVertices().inE(branchFactor, labels);
	}

	protected FramedEdgeTraversal<?> inE(final String... labels) {
		return new FramedTraversalImpl(graph(), element()).asVertices().inE(labels);
	}

	protected FramedEdgeTraversal<?> bothE(final int branchFactor, final String... labels) {
		return new FramedTraversalImpl(graph(), element()).asVertices().bothE(branchFactor, labels);
	}

	protected FramedEdgeTraversal<?> bothE(final String... labels) {
		return new FramedTraversalImpl(graph(), element()).asVertices().bothE(labels);
	}

	protected void linkOut(FramedVertex vertex, String... labels) {
		for (String label : labels) {
			traversal().linkOut(label, vertex).iterate();
		}
	}

	protected void linkIn(FramedVertex vertex, String... labels) {
		for (String label : labels) {
			traversal().linkIn(label, vertex).iterate();
		}
	}

	protected void linkBoth(FramedVertex vertex, String... labels) {
		for (String label : labels) {
			traversal().linkBoth(label, vertex).iterate();
		}
	}


    protected void unlinkOut(FramedVertex vertex, String ...labels){
        FramedEdgeTraversal<?> pipeline = outE(labels).as("e");
        if(vertex != null){
            pipeline = pipeline.inV().retain(Lists.newArrayList(vertex.element())).back("e").asEdges();
        }
        pipeline.remove();
    }

    protected void unlinkIn(FramedVertex vertex, String ...labels){
        FramedEdgeTraversal<?> pipeline = inE(labels).as("e");
        if(vertex != null){
            pipeline = pipeline.outV().retain(Lists.newArrayList(vertex.element())).back("e").asEdges();
        }
        pipeline.remove();
    }

    protected void unlinkBoth(FramedVertex vertex, String ...labels){
        FramedEdgeTraversal<?> pipeline = bothE(labels).as("e");
        if(vertex != null){
            pipeline = pipeline.bothV().retain(Lists.newArrayList(vertex.element())).back("e").asEdges();
        }
        pipeline.remove();
    }

	protected void setLinkIn(FramedVertex vertex, String... labels) {
		unlinkIn(null, labels);
		linkIn(vertex, labels);
	}

	protected void setLinkOut(FramedVertex vertex, String... labels) {
		unlinkOut(null, labels);
		linkOut(vertex, labels);
	}

	protected void setLinkBoth(FramedVertex vertex, String... labels) {
		unlinkBoth(null, labels);
		linkBoth(vertex, labels);
	}

	protected <K extends FramedVertex> FramedVertex setLinkOut(Class<K> kind, String... labels) {
		K vertex = graph().addVertex(kind);
		setLinkOut(vertex, labels);
		return vertex;
	}

	protected <K extends FramedVertex> FramedVertex setLinkIn(Class<K> kind, String... labels) {
		K vertex = graph().addVertex(kind);
		setLinkIn(vertex, labels);
		return vertex;
	}

	protected <K extends FramedVertex> FramedVertex setLinkBoth(Class<K> kind, String... labels) {
		K vertex = graph().addVertex(kind);
		setLinkBoth(vertex, labels);
		return vertex;
	}

	/**
	 * Shortcut to get frameTraversal of current element
	 * 
	 * @return
	 */
	protected FramedVertexTraversal<?> traversal() {
		return new FramedTraversalImpl(graph(), element()).asVertices();
	}
}

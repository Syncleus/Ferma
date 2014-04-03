package org.jglue.totorom;

import com.google.common.collect.Lists;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinFluentPipeline;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.PipeFunction;

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

    protected void linkOut(FramedVertex vertex, String ...labels){
        for(String label : labels){
            traversal().linkOut(label, vertex.element()).next();
        }
    }

    protected void linkIn(FramedVertex vertex, String ...labels){
        for(String label : labels){
            traversal().linkIn(label, vertex.element()).next();
        }
    }

    protected void linkBoth(FramedVertex vertex, String ...labels){
        for(String label : labels){
            traversal().linkBoth(label, vertex.element()).next();
        }
    }

    protected void unlinkOut(FramedVertex vertex, String ...labels){
        GremlinPipeline pipeline = outE(labels).as("e");
        if(vertex != null){
            pipeline = pipeline.inV().retain(Lists.newArrayList(vertex.element())).back("e");
        }
        pipeline.remove();
    }

    protected void unlinkIn(FramedVertex vertex, String ...labels){
        GremlinPipeline pipeline = inE(labels).as("e");
        if(vertex != null){
            pipeline = pipeline.outV().retain(Lists.newArrayList(vertex.element())).back("e");
        }
        pipeline.remove();
    }

    protected void unlinkBoth(FramedVertex vertex, String ...labels){
        GremlinPipeline pipeline = bothE(labels).as("e");
        if(vertex != null){
            pipeline = pipeline.bothV().retain(Lists.newArrayList(vertex.element())).back("e");
        }
        pipeline.remove();
    }

    protected void setLinkIn(FramedVertex vertex, String ...labels){
       unlinkIn(null, labels);
       linkIn(vertex, labels);
    }

    protected void setLinkOut(FramedVertex vertex, String ...labels){
       unlinkOut(null, labels);
       linkOut(vertex, labels);
    }

    protected void setLinkBoth(FramedVertex vertex, String ...labels){
        unlinkBoth(null, labels);
        linkBoth(vertex, labels);
    }

    protected  <K extends FramedVertex> FramedVertex setLinkOut(Class<K> kind, String ...labels){
        K vertex = graph().addVertex(kind);
        setLinkOut(vertex, labels);
        return vertex;
    }

    protected  <K extends FramedVertex> FramedVertex setLinkIn(Class<K> kind, String ...labels){
        K vertex = graph().addVertex(kind);
        setLinkIn(vertex, labels);
        return vertex;
    }

    protected  <K extends FramedVertex> FramedVertex setLinkBoth(Class<K> kind, String ...labels){
        K vertex = graph().addVertex(kind);
        setLinkBoth(vertex, labels);
        return vertex;
    }


}

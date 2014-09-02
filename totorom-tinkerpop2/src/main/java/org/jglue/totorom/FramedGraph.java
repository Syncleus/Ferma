package org.jglue.totorom;

import java.io.Closeable;
import java.util.Iterator;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Bryn Cooke (http://jglue.org)
 */

public class FramedGraph {
    private Graph delegate;

    private TypeResolver resolver;
    private FrameFactory builder;


    /**
     * Construct a framed graph.
     * @param delegate The graph to wrap.
     * @param builder The builder that will construct frames.
     * @param resolver The type resolver that will decide the final frame type.  
     */
    public FramedGraph(Graph delegate, FrameFactory builder, TypeResolver resolver) {
        this.delegate = delegate;
        this.resolver = resolver;
        this.builder = builder;
    }

    /**
     * Construct an untyped framed graph using no special frame construction. 
     * @param delegate The graph to wrap.
     */
    public FramedGraph(Graph delegate) {
        this(delegate, FrameFactory.Default, TypeResolver.Untyped);
    }


    /**
     * @return A transaction object that is {@link Closeable}. 
     */
    public Transaction tx() {
    	if(delegate instanceof TransactionalGraph) {
    		return new Transaction((TransactionalGraph)delegate);
    	}
    	else {
    		throw new UnsupportedOperationException("Underlying graph is not transactional");
    	}
    }


    /**
     * Close the delegate graph.
     * @throws Exception
     */
    public void close() throws Exception {
        delegate.shutdown();
    }


    <T extends FramedElement> T frameElement(Element e, Class<T> kind) {

        Class<T> frameType = resolver.resolve(e, kind);
        T framedElement = builder.create(e, frameType);
        framedElement.init(this, e);
        return framedElement;
    }
    
    <T extends FramedElement> T frameNewElement(Element e, Class<T> kind) {
    	T t = frameElement(e, kind);
    	resolver.init(e, kind);
        return t;
    }

    <T extends FramedElement> Iterator<T> frame(Iterator<? extends Element> pipeline, final Class<T> kind) {
    	return Iterators.transform(pipeline, new Function<Element, T>() {

			@Override
			public T apply(Element element) {
				return frameElement(element, kind);
			}
    		
    	});
    }


    /**
     * Add a vertex to the graph
     * @param kind The kind of the frame.
     * @return The framed vertex.
     */
    public <T extends FramedVertex> T addVertex(Class<T> kind) {
        T framedVertex = frameNewElement(delegate.addVertex(null), kind);
        framedVertex.init();
        return framedVertex;
    }
    
    /**
     * Query over all vertices in the graph.
     * @return The query.
     */
    public FramedVertexTraversal<?> V() {
        return new FramedTraversalImpl(this, delegate).V();
    }

    /**
     * Query over all edges in the graph.
     * @return The query.
     */
    public FramedEdgeTraversal<?> E() {
    	return new FramedTraversalImpl(this, delegate).E();
    }

    
    /**
     * Query over a list of vertices in the graph.
     * @param ids The ids of the vertices.
     * @return The query.
     */
    public FramedVertexTraversal<?> v(final Object... ids) {
    	return new FramedTraversalImpl(this, Iterators.transform(Iterators.forArray(ids), new Function<Object, Vertex>() {

			@Override
			public Vertex apply(Object id) {
				return delegate.getVertex(id);
			}

		})).asVertices();
    }
    
    /**
     * Query over a list of edges in the graph.
     * @param ids The ids of the edges.
     * @return The query. 
     */
    public FramedEdgeTraversal<?> e(final Object... ids) {
    	return new FramedTraversalImpl(this, Iterators.transform(Iterators.forArray(ids), new Function<Object, Edge>() {

			@Override
			public Edge apply(Object id) {
				return delegate.getEdge(id);
			}

		})).asEdges();
    }


}

package com.tinkerpop.frames;

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
    private FrameBuilder builder;


    public FramedGraph(Graph delegate, TypeResolver resolver, FrameBuilder builder) {
        this.delegate = delegate;
        this.resolver = resolver;
        this.builder = builder;
    }

    public FramedGraph(Graph delegate) {
        this(delegate, TypeResolver.Untyped, FrameBuilder.Default);
    }


    public Transaction tx() {
    	if(delegate instanceof TransactionalGraph) {
    		return new Transaction((TransactionalGraph)delegate);
    	}
    	else {
    		throw new UnsupportedOperationException("Graph was not transactional");
    	}
    }


    public void close() throws Exception {
        delegate.shutdown();
    }


    <T extends FramedElement> T frameElement(Element e, Class<T> kind) {

        Class<T> frameType = resolver.resolve(e, kind);
        T framedElement = builder.create(frameType);
        framedElement.init(this, e);
        return framedElement;
    }
    
    <T extends FramedElement> T frameNewElement(Element e, Class<T> kind) {
    	T t = frameElement(e, kind);
    	resolver.init(e, kind);
        return t;
    }


    Graph getDelegate() {
        return delegate;
    }

    public <T extends FramedElement> Iterator<T> frame(Iterator<? extends Element> pipeline, final Class<T> kind) {
    	return Iterators.transform(pipeline, new Function<Element, T>() {

			@Override
			public T apply(Element element) {
				return frameElement(element, kind);
			}
    		
    	});
    }


    public <T extends FramedVertex> T addVertex(Class<T> kind) {
        T framedVertex = frameNewElement(delegate.addVertex(null), kind);
        framedVertex.init();
        return framedVertex;
    }
    
    public FramedGremlin<Vertex, Vertex> V() {
        return new FramedGremlin(this, delegate).V();
    }

    public FramedGremlin<Edge, Edge> E() {
    	return new FramedGremlin(this, delegate).E();
    }

    
    //Todo lazy iterator
//    public FramedGremlin<Vertex, Vertex> v(final Object... ids) {
//    	
//        return (FramedGremlin<Vertex, Vertex>)super.v(ids);
//    }
//
//    public FramedGremlin<Edge, Edge> e(final Object... ids) {
//        return (FramedGremlin<Edge, Edge>)super.e(ids);
//    }


}

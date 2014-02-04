package com.tinkerpop.frames;

import com.tinkerpop.blueprints.*;
import com.tinkerpop.blueprints.util.StreamFactory;
import com.tinkerpop.gremlin.Holder;


import java.io.IOException;
import java.util.Iterator;

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
        return delegate.tx();
    }


    public void close() throws Exception {
        delegate.close();
    }


    <T extends FramedElement> T frameElement(Element e, Class<T> kind) {

        Class<T> frameType = resolver.resolve(e, kind);
        T framedElement = builder.create(frameType);
        framedElement.init(this, e);
        return framedElement;


    }


    Graph getDelegate() {
        return delegate;
    }

    public <T extends FramedElement> Iterator<T> frame(Iterator<? extends Element> pipeline, final Class<T> kind) {
        return StreamFactory.stream(pipeline).map(e -> frameElement(e, kind)).iterator();
    }


    public <T extends FramedVertex> T addVertex(Class<T> kind) {
        T framedVertex = frameElement(delegate.addVertex(), kind);
        framedVertex.init();
        return framedVertex;
    }


}

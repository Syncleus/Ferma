package com.tinkerpop.frames;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.Gremlin;
import com.tinkerpop.gremlin.Holder;


import java.util.*;

/**
 * @author Bryn Cooke (http://jglue.org)
 */

public abstract class FramedElement<E extends Element> {
    private E element;
    private FramedGraph graph;


    protected void init(FramedGraph graph, E element) {
        this.graph = graph;
        this.element = element;
    }


    protected void init() {

    }


    protected Object getId() {
        return element.getId();
    }


    protected Set<String> getPropertyKeys() {
        return element.getPropertyKeys();
    }


    protected void remove() {
        element.remove();
    }

    protected E element() {
        return element;
    }

    protected FramedGraph graph() {
        return graph;
    }

    protected <T> T getProperty(String name) {
        return (T) element.getProperty(name).get();
    }

    protected void setProperty(String name, Object value) {
        element.setProperty(name, value);
    }

    protected FramedGremlin<Vertex, Vertex> V() {
        return new FramedGremlin<>(graph, element).V();
    }

    protected FramedGremlin<Edge, Edge> E() {
        return new FramedGremlin<>(graph, element).E();
    }

    protected FramedGremlin<Vertex, Vertex> v(final Object... ids) {
        return new FramedGremlin<>(graph, element).v(ids);
    }

    protected FramedGremlin<Edge, Edge> e(final Object... ids) {
        return new FramedGremlin<>(graph, element).e(ids);
    }



}

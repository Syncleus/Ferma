package com.tinkerpop.frames;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.Gremlin;
import com.tinkerpop.gremlin.Holder;
import com.tinkerpop.gremlin.SimpleHolder;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by bryn on 19/01/14.
 */
public class FramedGremlin<S extends Element, E extends Element> extends Gremlin<S, E>{
    private FramedGraph graph;

    protected FramedGremlin(FramedGraph graph, S starts) {
        super(graph.getDelegate(), true);
        Holder<S> holder = new SimpleHolder(starts);
        addStarts(Collections.singleton(holder).iterator());
        this.graph = graph;
    }
    public FramedGremlin<Vertex, Vertex> V() {
        return (FramedGremlin<Vertex, Vertex>)super.V();
    }

    public FramedGremlin<Edge, Edge> E() {
        return (FramedGremlin<Edge, Edge>)super.E();
    }

    public FramedGremlin<Vertex, Vertex> v(final Object... ids) {
        return (FramedGremlin<Vertex, Vertex>)super.v(ids);
    }

    public FramedGremlin<Edge, Edge> e(final Object... ids) {
        return (FramedGremlin<Edge, Edge>)super.e(ids);
    }


    public FramedGremlin<S, E> identity() {
        return (FramedGremlin<S, E>)super.identity();
    }

    public FramedGremlin<S, Vertex> out(final int branchFactor, final String... labels) {
        return (FramedGremlin<S, Vertex>)super.out(branchFactor, labels);
    }

    public FramedGremlin<S, Vertex> out(final String... labels) {
        return (FramedGremlin<S, Vertex>)super.out(labels);
    }

    public FramedGremlin<S, Vertex> in(final int branchFactor, final String... labels) {
        return (FramedGremlin<S, Vertex>)super.in(branchFactor, labels);
    }

    public FramedGremlin<S, Vertex> in(final String... labels) {
        return (FramedGremlin<S, Vertex>)super.in(labels);
    }

    public FramedGremlin<S, Vertex> both(final int branchFactor, final String... labels) {
        return (FramedGremlin<S, Vertex>)super.both(branchFactor, labels);
    }

    public FramedGremlin<S, Vertex> both(final String... labels) {
        return (FramedGremlin<S, Vertex>)super.both(labels);
    }

    public FramedGremlin<S, Edge> outE(final int branchFactor, final String... labels) {
        return (FramedGremlin<S, Edge>)super.outE(branchFactor, labels);
    }

    public FramedGremlin<S, Edge> outE(final String... labels) {
        return (FramedGremlin<S, Edge>)super.outE(labels);
    }

    public FramedGremlin<S, Edge> inE(final int branchFactor, final String... labels) {
        return (FramedGremlin<S, Edge>)super.inE(branchFactor, labels);
    }

    public FramedGremlin<S, Edge> inE(final String... labels) {
        return (FramedGremlin<S, Edge>)super.inE(labels);
    }

    public FramedGremlin<S, Edge> bothE(final int branchFactor, final String... labels) {
        return (FramedGremlin<S, Edge>)super.bothE(branchFactor, labels);
    }

    public FramedGremlin<S, Edge> bothE(final String... labels) {
        return (FramedGremlin<S, Edge>)super.bothE(labels);
    }

    public FramedGremlin<S, Vertex> inV() {
        return (FramedGremlin<S, Vertex>)super.inV();
    }

    public FramedGremlin<S, Vertex> outV() {
        return (FramedGremlin<S, Vertex>)super.outV();
    }

    public FramedGremlin<S, Vertex> bothV() {
        return (FramedGremlin<S, Vertex>)super.bothV();
    }

    public <T extends FramedElement> T next(Class<T> kind) {

        return graph.frameElement(super.next(), kind);
    }


    public <T extends FramedElement> List<T> next(int amount, Class<T> kind) {
        return super.next(amount).stream().map(new Function<E, T>() {
            @Override
            public T apply(E e) {
                return graph.frameElement(e, kind);
            }
        }).collect(Collectors.toList());

    }

    public <T extends FramedElement> Iterator<T> frame(Class<T> kind) {
        return graph.frame(this, kind);
    }

    public <T extends FramedElement> List<T> toList(Class<T> kind) {
        return super.toList().stream().map(new Function<E, T>() {
            @Override
            public T apply(E e) {
                return graph.frameElement(e, kind);
            }
        }).collect(Collectors.toList());

    }
}

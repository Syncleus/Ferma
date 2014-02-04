package com.tinkerpop.frames;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.Tokens;
import com.tinkerpop.gremlin.java.GremlinFluentUtility;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.filter.IdFilterPipe;
import com.tinkerpop.pipes.filter.LabelFilterPipe;
import com.tinkerpop.pipes.filter.PropertyFilterPipe;

/**
 * Created by bryn on 19/01/14.
 */
public class FramedGremlin<S, E> extends GremlinPipeline<S, E>{
    private FramedGraph graph;

    protected FramedGremlin(FramedGraph graph, S starts) {
        super(starts, true);
        this.graph = graph;
    }
    public FramedGremlin<S, Vertex> V() {
        return (FramedGremlin<S, Vertex>)super.V();
    }

    public FramedGremlin<S, Edge> E() {
        return (FramedGremlin<S, Edge>)super.E();
    }
//
//    public FramedGremlin<Vertex, Vertex> v(final Object... ids) {
//        return (FramedGremlin<Vertex, Vertex>)super.v(ids);
//    }
//
//    public FramedGremlin<Edge, Edge> e(final Object... ids) {
//        return (FramedGremlin<Edge, Edge>)super.e(ids);
//    }
    
    public FramedGremlin<S, ? extends Element> has(final String key) {
        return (FramedGremlin<S, ? extends Element>) super.has(key);
    }

    public FramedGremlin<S, ? extends Element> has(final String key, final Object value) {
    	return (FramedGremlin<S, ? extends Element>) super.has(key, value);
    }


    public FramedGremlin<S, ? extends Element> has(final String key, final Tokens.T compareToken, final Object value) {
    	return (FramedGremlin<S, ? extends Element>) super.has(key, compareToken, value);
    }

    public FramedGremlin<S, ? extends Element> has(final String key, final Predicate predicate, final Object value) {
    	return (FramedGremlin<S, ? extends Element>) super.has(key, predicate, value);
    }

    public FramedGremlin<S, ? extends Element> hasNot(final String key) {
    	return (FramedGremlin<S, ? extends Element>) super.hasNot(key);
    }

    public FramedGremlin<S, ? extends Element> hasNot(final String key, final Object value) {
        return (FramedGremlin<S, ? extends Element>) super.hasNot(key, value);
    }

    public FramedGremlin<S, ? extends Element> interval(final String key, final Comparable startValue, final Comparable endValue) {
    	return (FramedGremlin<S, ? extends Element>) super.interval(key, startValue, endValue);
    }

    public FramedGremlin<S, E> identity() {
        return (FramedGremlin<S, E>)super._();
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

        return graph.frameElement((Element)super.next(), kind);
    }


    public <T extends FramedElement> List<T> next(int amount, final Class<T> kind) {
        return Lists.transform(super.next(amount), new Function<E, T>() {
        	
            @Override
            public T apply(E e) {
                return graph.frameElement((Element)e, kind);
            }
        });

    }

    public <T extends FramedElement> Iterator<T> frame(Class<T> kind) {
        return graph.frame((Iterator<Element>)this, kind);
    }

    public <T extends FramedElement> List<T> toList(final Class<T> kind) {
        return Lists.transform(super.toList(), new Function<E, T>() {
        	
            @Override
            public T apply(E e) {
                return graph.frameElement((Element)e, kind);
            }
        });

    }
}

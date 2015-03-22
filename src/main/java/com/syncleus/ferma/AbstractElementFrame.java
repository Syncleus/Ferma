/******************************************************************************
 *                                                                             *
 *  Copyright: (c) Syncleus, Inc.                                              *
 *                                                                             *
 *  You may redistribute and modify this source code under the terms and       *
 *  conditions of the Open Source Community License - Type C version 1.0       *
 *  or any later version as published by Syncleus, Inc. at www.syncleus.com.   *
 *  There should be a copy of the license included with this file. If a copy   *
 *  of the license is not included you are granted no right to distribute or   *
 *  otherwise use this file except through a legal and valid license. You      *
 *  should also contact Syncleus, Inc. at the information below if you cannot  *
 *  find a license:                                                            *
 *                                                                             *
 *  Syncleus, Inc.                                                             *
 *  2604 South 12th Street                                                     *
 *  Philadelphia, PA 19148                                                     *
 *                                                                             *
 ******************************************************************************/

/*
 * Part or all of this source file was forked from a third-party project, the details of which are listed below.
 *
 * Source Project: Totorom
 * Source URL: https://github.com/BrynCooke/totorom
 * Source License: Apache Public License v2.0
 * When: November, 20th 2014
 */
package com.syncleus.ferma;

import java.util.Set;

import com.syncleus.ferma.framefactories.annotation.CachesReflection;
import com.tinkerpop.blueprints.Element;

/**
 * The base of all framed elements.
 */
public abstract class AbstractElementFrame implements ElementFrame {

    private Element element;
    private FramedGraph graph;

    /**
     * This method is called anytime an element is instantiated. If the element is a new element or an existing element
     * this method will be called.
     *
     * @param graph The graph this element exists in.
     * @param element The raw blueprints element.
     */
    protected void init(final FramedGraph graph, final Element element) {
        this.graph = graph;
        this.element = element;
    }

    /**
     * This method is only called when creating new elements that don't already exist in the graph. This method should
     * be overridden to initalize any properties of an element on its creation. If an element is being framed that
     * already exists in the graph this method will not be called.
     */
    protected void init() {

    }

    @Override
    public <N> N getId() {
        return (N) element.getId();
    }

    @Override
    public Set<String> getPropertyKeys() {
        return element.getPropertyKeys();
    }

    @Override
    public Class<?> getTypeResolution() {
        return this.graph.getTypeResolver().resolve(element);
    }

    @Override
    public void setTypeResolution(final Class<?> type) {
        this.graph.getTypeResolver().init(element, type);
    }

    @Override
    public void removeTypeResolution() {
        this.graph.getTypeResolver().deinit(element);
    }

    @Override
    public void remove() {
        element.remove();
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public FramedGraph getGraph() {
        return graph;
    }

    @Override
    public <T> T getProperty(final String name) {
        return element.getProperty(name);
    }

    @Override
    public <T> T getProperty(final String name, final Class<T> type) {
        if (type.isEnum())
            return (T) Enum.valueOf((Class<Enum>) type, (String) element.getProperty(name));

        return element.getProperty(name);
    }

    @Override
    public void setProperty(final String name, final Object value) {
        if (value == null)
            element.removeProperty(name);
        else if (value instanceof Enum)
            element.setProperty(name, value.toString());
        else
            element.setProperty(name, value);
    }

    @Override
    public VertexTraversal<?, ?, ?> v() {
        return graph.v();
    }

    @Override
    public EdgeTraversal<?, ?, ?> e() {
        return graph.e();
    }

    @Override
    public VertexTraversal<?, ?, ?> v(final Object... ids) {
        return graph.v(ids);
    }

    @Override
    public EdgeTraversal<?, ?, ?> e(final Object... ids) {
        return graph.e(ids);
    }

    @Override
    public int hashCode() {
        return element.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        final AbstractElementFrame other = (AbstractElementFrame) o;
        if (element == null) {
            if (other.element != null)
                return false;
        }
        else if (!element.equals(other.element))
            return false;
        return true;
    }

    protected <N> N getId(final Class<N> clazz) {

        return (N) getId();
    }

    @Override
    public String toString() {

        return getElement().toString();
    }

}

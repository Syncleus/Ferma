/**
 * Copyright: (c) Syncleus, Inc.
 *
 * You may redistribute and modify this source code under the terms and
 * conditions of the Open Source Community License - Type C version 1.0
 * or any later version as published by Syncleus, Inc. at www.syncleus.com.
 * There should be a copy of the license included with this file. If a copy
 * of the license is not included you are granted no right to distribute or
 * otherwise use this file except through a legal and valid license. You
 * should also contact Syncleus, Inc. at the information below if you cannot
 * find a license:
 *
 * Syncleus, Inc.
 * 2604 South 12th Street
 * Philadelphia, PA 19148
 */
/*
 * Part or all of this source file was forked from a third-party project, the details of which are listed below.
 *
 * Source Project: TinkerPop Frames
 * Source URL: https://github.com/tinkerpop/frames
 * Source License: BSD 3-clause
 * When: November, 25th 2014
 */
package com.syncleus.ferma;

import com.tinkerpop.blueprints.Element;

import java.util.Iterator;

public abstract class FramingIterable<T, E extends Element> implements Iterable<T> {

    private final Class<T> kind;
    private final Iterable<E> iterable;
    private final FramedGraph framedGraph;
    private final boolean explicit;

    public FramingIterable(final FramedGraph framedGraph, final Iterable<E> iterable, final Class<T> kind) {
        this.framedGraph = framedGraph;
        this.iterable = iterable;
        this.kind = kind;
        this.explicit = false;
    }

    public FramingIterable(final FramedGraph framedGraph, final Iterable<E> iterable, final Class<T> kind, final boolean explicit) {
        this.framedGraph = framedGraph;
        this.iterable = iterable;
        this.kind = kind;
        this.explicit = explicit;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private final Iterator<E> iterator = iterable.iterator();

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean hasNext() {
                return this.iterator.hasNext();
            }

            @Override
            public T next() {
                if (explicit)
                    return framedGraph.frameElementExplicit(this.iterator.next(), kind);
                else
                    return framedGraph.frameElement(this.iterator.next(), kind);
            }
        };
    }
}

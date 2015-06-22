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
 * Source Project: Totorom
 * Source URL: https://github.com/BrynCooke/totorom
 * Source License: Apache Public License v2.0
 * When: November, 20th 2014
 */
package com.syncleus.ferma.traversals;

import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.traversals.FrameMaker;
import java.util.Collection;
import java.util.Iterator;

/**
 * Frames elements as they are inserted in to the delegate.
 *
 * @param <E> The type of values to store in the collection.
 * @param <K> The type to frame the values as.
 */
class FramingCollection<E, K> extends FrameMaker implements Collection<E> {

    private final Collection<? super E> delegate;
    private final boolean explicit;

    public FramingCollection(final Collection<? super E> delegate, final FramedGraph graph, final Class<K> kind) {
        super(graph, kind);
        this.delegate = delegate;
        this.explicit = false;
    }

    public FramingCollection(final Collection<? super E> delegate, final FramedGraph graph) {
        super(graph);
        this.delegate = delegate;
        this.explicit = false;
    }

    public FramingCollection(final Collection<? super E> delegate, final FramedGraph graph, final Class<K> kind, final boolean explicit) {
        super(graph, kind);
        this.delegate = delegate;
        this.explicit = explicit;
    }

    public FramingCollection(final Collection<? super E> delegate, final FramedGraph graph, final boolean explicit) {
        super(graph);
        this.delegate = delegate;
        this.explicit = explicit;
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(final Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(final T[] ts) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        e = (this.explicit ? this.<E>makeFrameExplicit(e) : this.<E>makeFrame(e));

        return delegate.add(e);
    }

    @Override
    public boolean remove(final Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(final Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(final Collection<? extends E> collection) {
        boolean modified = false;
        for (final E e : collection)
            modified |= add(e);
        return modified;
    }

    @Override
    public boolean removeAll(final Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(final Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(final Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    public Collection<? super E> getDelegate() {

        return delegate;
    }

}

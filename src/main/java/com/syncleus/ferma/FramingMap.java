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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

class FramingMap<T extends ElementFrame> extends FrameMaker implements Map {

    public FramingMap(final Map delegate, final FramedGraph graph) {
        super(graph);
        this.delegate = delegate;
    }

    private final Map delegate;

    public Map getDelegate() {
        return delegate;
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
    public boolean containsKey(final Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsValue(final Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object get(final Object o) {
        return removeFrame(delegate.get(makeFrame(o)));
    }

    @Override
    public Object put(final Object k, final Object v) {
        return delegate.put(makeFrame(k), makeFrame(v));
    }

    @Override
    public Object remove(final Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(final Map map) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set entrySet() {
        return delegate.entrySet();
    }

    @Override
    public String toString() {

        return delegate.toString();
    }

}

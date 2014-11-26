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
package com.syncleus.ferma;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import java.util.Iterator;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class FramedVertexIterable<T> implements Iterable<T> {
    protected final Class<T> kind;
    protected final Iterable<Vertex> iterable;
    protected final FramedGraph framedGraph;

    public FramedVertexIterable(final FramedGraph framedGraph, final Iterable<Vertex> iterable, final Class<T> kind) {
        this.framedGraph = framedGraph;
        this.iterable = iterable;
        this.kind = kind;
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Iterator<Vertex> iterator = iterable.iterator();

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public boolean hasNext() {
                return this.iterator.hasNext();
            }

            public T next() {
                return framedGraph.frameElement(this.iterator.next(), kind);
            }
        };
    }
}
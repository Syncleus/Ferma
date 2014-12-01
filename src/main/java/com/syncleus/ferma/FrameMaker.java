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

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.pipes.util.structures.Pair;

class FrameMaker {

    private final FramedGraph graph;
    private final Class<?> kind;

    public FrameMaker(final FramedGraph graph, final Class<? extends ElementFrame> kind) {
        this.graph = graph;
        this.kind = kind;
    }

    public FrameMaker(final FramedGraph graph) {
        this(graph, null);
    }

    <N> N makeFrame(Object o) {
        if (o instanceof FramingMap)
            o = ((FramingMap) o).getDelegate();
        if (o instanceof Pair) {
            final Pair pair = (Pair) o;
            o = new Pair(makeFrame(pair.getA()), makeFrame(pair.getB()));
        }
        if (kind == null) {
            if (o instanceof Edge)
                o = graph.frameElement((Element) o, TEdge.class);
            else if (o instanceof Vertex)
                o = graph.frameElement((Element) o, TVertex.class);
        }
        else if (o instanceof Element)
            o = graph.frameElement((Element) o, (Class<ElementFrame>) kind);
        return (N) o;
    }

    <N> N makeFrameExplicit(Object o) {
        if (o instanceof FramingMap)
            o = ((FramingMap) o).getDelegate();
        if (o instanceof Pair) {
            final Pair pair = (Pair) o;
            o = new Pair(makeFrameExplicit(pair.getA()), makeFrameExplicit(pair.getB()));
        }
        if (kind == null) {
            if (o instanceof Edge)
                o = graph.frameElementExplicit((Element) o, TEdge.class);
            else if (o instanceof Vertex)
                o = graph.frameElementExplicit((Element) o, TVertex.class);
        }
        else if (o instanceof Element)
            o = graph.frameElementExplicit((Element) o, (Class<ElementFrame>) kind);
        return (N) o;
    }

    protected Object removeFrame(final Object object) {
        if (object instanceof ElementFrame)
            return ((ElementFrame) object).element();
        return object;
    }

}

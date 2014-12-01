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

import com.tinkerpop.pipes.PipeFunction;

/**
 * Frames parameters to a traversal function.
 *
 * @param <A>
 * @param <B>
 */
class FramingTraversalFunction<A, B, C> extends FrameMaker implements TraversalFunction<C, B> {

    private PipeFunction<A, ? extends B> delegate;

    public FramingTraversalFunction(final PipeFunction<A, ? extends B> delegate, final FramedGraph graph, final Class<? extends ElementFrame> kind) {
        super(graph, kind);
        this.delegate = delegate;
    }

    public FramingTraversalFunction(final PipeFunction<A, ? extends B> delegate, final FramedGraph graph) {
        super(graph);
        this.delegate = delegate;
    }

    public <T extends ElementFrame> FramingTraversalFunction(final FramedGraph graph, final Class<T> kind) {
        super(graph, kind);
    }

    public FramingTraversalFunction(final FramedGraph graph) {
        super(graph);
    }

    @Override
    public B compute(C argument) {

        argument = makeFrame(argument);

        if (delegate == null)
            return (B) argument;

        return delegate.compute((A) argument);

    }

}

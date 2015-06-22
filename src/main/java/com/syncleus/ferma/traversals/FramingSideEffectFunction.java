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

import com.syncleus.ferma.ElementFrame;
import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.traversals.FrameMaker;

/**
 * Frames the argument before delegation.
 *
 * @param <T>
 */
class FramingSideEffectFunction<T, K extends ElementFrame> extends FrameMaker implements SideEffectFunction<T> {

    private final SideEffectFunction<T> delegate;

    public FramingSideEffectFunction(final SideEffectFunction<T> delegate, final FramedGraph graph, final Class<K> kind) {
        super(graph, kind);
        this.delegate = delegate;

    }

    public FramingSideEffectFunction(final SideEffectFunction<T> delegate, final FramedGraph graph) {
        super(graph);
        this.delegate = delegate;

    }

    @Override
    public void execute(T o) {
        o = makeFrame(o);

        delegate.execute(o);
    }

}

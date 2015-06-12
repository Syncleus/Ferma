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
package com.syncleus.ferma.pipes;

import com.tinkerpop.blueprints.Element;
import java.util.List;

import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.util.FluentUtility;

public class FermaGremlinPipeline<S, E> extends com.tinkerpop.gremlin.java.GremlinPipeline<S, E> {

    private E current;

    public FermaGremlinPipeline() {
        super();
    }

    public FermaGremlinPipeline(final Object starts, final boolean doQueryOptimization) {
        super(starts, doQueryOptimization);
    }

    public FermaGremlinPipeline(final Object starts) {
        super(starts);
    }

    @Override
    public com.tinkerpop.gremlin.java.GremlinPipeline<S, List> path(final PipeFunction... pathFunctions) {

        return this.add(new PathPipe<>(FluentUtility.prepareFunctions(this.asMap, pathFunctions)));
    }

    @Override
    public void remove() {
        if (this.current instanceof Element) {
            ((Element) this.current).remove();
            this.current = null;
        }
        else
            throw new UnsupportedOperationException("Current must be an element to remove");
    }

    @Override
    public E next() {

        this.current = super.next();
        return this.current;
    }

    @Override
    public List<E> next(final int number) {
        this.current = null;
        return super.next(number);
    }

    public void removeAll() {
        super.remove();
    }
}

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

import com.tinkerpop.blueprints.TransactionalGraph;

import java.util.Collection;

public class DelegatingFramedTransactionalGraph extends DelegatingFramedGraph implements FramedTransactionalGraph {

    public DelegatingFramedTransactionalGraph(TransactionalGraph delegate, FrameFactory builder, TypeResolver defaultResolver) {
        super(delegate, builder, defaultResolver);
    }

    public DelegatingFramedTransactionalGraph(TransactionalGraph delegate) {
        super(delegate);
    }

    public DelegatingFramedTransactionalGraph(TransactionalGraph delegate, ReflectionCache reflections) {
        super(delegate, reflections);
    }

    public DelegatingFramedTransactionalGraph(TransactionalGraph delegate, TypeResolver defaultResolver) {
        super(delegate, defaultResolver);
    }

    public DelegatingFramedTransactionalGraph(TransactionalGraph delegate, boolean typeResolution, boolean annotationsSupported) {
        super(delegate, typeResolution, annotationsSupported);
    }

    public DelegatingFramedTransactionalGraph(TransactionalGraph delegate, ReflectionCache reflections, boolean typeResolution, boolean annotationsSupported) {
        super(delegate, reflections, typeResolution, annotationsSupported);
    }

    public DelegatingFramedTransactionalGraph(TransactionalGraph delegate, Collection<? extends Class<?>> types) {
        super(delegate, types);
    }

    public DelegatingFramedTransactionalGraph(TransactionalGraph delegate, boolean typeResolution, Collection<? extends Class<?>> types) {
        super(delegate, typeResolution, types);
    }

    @Override
    public void stopTransaction(TransactionalGraph.Conclusion conclusion) {
        ((TransactionalGraph)this.getDelegate()).stopTransaction(conclusion);
    }

    @Override
    public void commit() {
        ((TransactionalGraph)this.getDelegate()).commit();
    }

    @Override
    public void rollback() {
        ((TransactionalGraph)this.getDelegate()).rollback();
    }
}

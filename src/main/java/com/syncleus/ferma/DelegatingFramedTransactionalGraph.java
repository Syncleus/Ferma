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

import com.syncleus.ferma.framefactories.FrameFactory;
import com.syncleus.ferma.typeresolvers.TypeResolver;
import com.tinkerpop.blueprints.TransactionalGraph;

import java.util.Collection;

public class DelegatingFramedTransactionalGraph<G extends TransactionalGraph> extends DelegatingFramedGraph<G> implements WrapperFramedTransactionalGraph<G> {

    public DelegatingFramedTransactionalGraph(final G delegate, final FrameFactory builder, final TypeResolver defaultResolver) {
        super(delegate, builder, defaultResolver);
    }

    public DelegatingFramedTransactionalGraph(final G delegate) {
        super(delegate);
    }

    public DelegatingFramedTransactionalGraph(final G delegate, final ReflectionCache reflections) {
        super(delegate, reflections);
    }

    public DelegatingFramedTransactionalGraph(final G delegate, final TypeResolver defaultResolver) {
        super(delegate, defaultResolver);
    }

    public DelegatingFramedTransactionalGraph(final G delegate, final boolean typeResolution, final boolean annotationsSupported) {
        super(delegate, typeResolution, annotationsSupported);
    }

    public DelegatingFramedTransactionalGraph(final G delegate, final ReflectionCache reflections, final boolean typeResolution, final boolean annotationsSupported) {
        super(delegate, reflections, typeResolution, annotationsSupported);
    }

    public DelegatingFramedTransactionalGraph(final G delegate, final Collection<? extends Class<?>> types) {
        super(delegate, types);
    }

    public DelegatingFramedTransactionalGraph(final G delegate, final boolean typeResolution, final Collection<? extends Class<?>> types) {
        super(delegate, typeResolution, types);
    }

    @Override
    public void stopTransaction(final TransactionalGraph.Conclusion conclusion) {
        ((TransactionalGraph) this.getBaseGraph()).stopTransaction(conclusion);
    }

    @Override
    public void commit() {
        ((TransactionalGraph) this.getBaseGraph()).commit();
    }

    @Override
    public void rollback() {
        ((TransactionalGraph) this.getBaseGraph()).rollback();
    }
}

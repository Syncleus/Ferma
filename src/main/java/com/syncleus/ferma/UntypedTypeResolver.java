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

import com.tinkerpop.blueprints.Element;

/**
 * This type resolver simply returns the type requested by the client.
 */
public class UntypedTypeResolver implements TypeResolver {

    @Override
    public <T> Class<? extends T> resolve(final Element element, final Class<T> kind) {
        if (VertexFrame.class.equals(kind) || AbstractVertexFrame.class.equals(kind))
            return (Class<? extends T>) TVertex.class;
        else if (EdgeFrame.class.equals(kind) || AbstractEdgeFrame.class.equals(kind))
            return (Class<? extends T>) TEdge.class;
        return kind;
    }
    
    @Override
    public Class<?> resolve(final Element element) {
        return null;
    }

    @Override
    public void init(final Element element, final Class<?> kind) {
    }
    
    @Override
    public void deinit(final Element element) {
    }
    
    @Override
    public VertexTraversal<?,?,?> hasSubtypes(final VertexTraversal<?,?,?> traverser, final Class<?> type) {
        return traverser;
    }
    
    @Override
    public EdgeTraversal<?,?,?> hasSubtypes(final EdgeTraversal<?,?,?> traverser, final Class<?> type) {
        return traverser;
    }
}

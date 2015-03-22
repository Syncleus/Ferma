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

import com.tinkerpop.blueprints.Element;

/**
 * Type resolvers resolve the frame type from the element being requested and
 * may optionally store metadata about the frame type on the element.
 */
public interface TypeResolver {
    /**
     * Resolve the type of frame that a an element should be.
     * 
     * @param <T> The type used to frame the element.
     * @param element
     *            The element that is being framed.
     * @param kind
     *            The kind of frame that is being requested by the client code.
     * @return The kind of frame
     */
    <T> Class<? extends T> resolve(Element element, Class<T> kind);
    
    /**
     * Resolve the type of frame that a an element should be.
     * 
     * @param element
     *            The element that is being framed.
     * @return The kind of frame, null if no type resolution properties exist.
     */
    Class<?> resolve(Element element);

    /**
     * Called to initialize an element with type resolution properties.
     * 
     * @param element
     *            The element that was created.
     * @param kind
     *            The kind of frame that was resolved.
     */
    void init(Element element, Class<?> kind);
    
    /**
     * Called to remove the type resolution properties from an element
     * 
     * @param element
     *            The element to remove the property from.
     */
    void deinit(Element element);
    
    /**
     * Filters the objects on the traversal that satisfy a requested type.
     * 
     * @param traverser A traversal pointing to the current set of vertex to be
     * filtered
     * @param type The type of vertex to filter by.
     * @return The traversal stream filtered by the desired type.
     */
    VertexTraversal<?,?,?> hasSubtypes(VertexTraversal<?,?,?> traverser, Class<?> type);
    
    /**
     * Filters the objects on the traversal that satisfy a requested type.
     * 
     * @param traverser A traversal pointing to the current set of vertex to be
     * filtered
     * @param type The type of vertex to filter by.
     * @return The traversal stream filtered by the desired type.
     */
    EdgeTraversal<?,?,?> hasSubtypes(EdgeTraversal<?,?,?> traverser, Class<?> type);
}

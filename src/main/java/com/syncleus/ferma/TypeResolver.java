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
	 * @param element
	 *            The element that is being framed.
	 * @param kind
	 *            The kind of frame that is being requested by the client code.
	 * @return The kind of frame
	 */
	public <T> Class<T> resolve(Element element, Class<T> kind);

	/**
	 * Called when a new element is created on the graph. Initialization can be
	 * performed, for instance to save the Java type of the frame on the
	 * underlying element.
	 * 
	 * @param element
	 *            The element that was created.
	 * @param kind
	 *            The kind of frame that was resolved.
	 */
	public <T> void init(Element element, Class<T> kind);
}

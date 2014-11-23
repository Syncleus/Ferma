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

import java.util.Comparator;

/**
 * Framed elements before delegation.
 * 
 * @author bryn
 *
 * @param <T>
 */
class FramingComparator<T, K extends FramedElement> extends FrameMaker implements Comparator<T> {

	private Comparator<T> delegate;

	public FramingComparator(Comparator<T> delegate, FramedGraph graph) {
		super(graph);
		this.delegate = delegate;

	}

	public FramingComparator(Comparator<T> delegate, FramedGraph graph, Class<K> kind) {
		super(graph, kind);
		this.delegate = delegate;
	}

	@Override
	public int compare(T o1, T o2) {

		o1 = makeFrame(o1);
		o2 = makeFrame(o2);

		return delegate.compare(o1, o2);
	}

}

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

import java.util.Collection;
import java.util.Iterator;

/**
 * Frames elements as they are inserted in to the delegate.
 *
 * @param <E>
 * @param <K>
 */
class FramingCollection<E, K extends AbstractElementFrame> extends FrameMaker implements Collection<E> {

	private Collection<E> delegate;
	private final boolean explicit;

	public FramingCollection(Collection<E> delegate, FramedGraph graph, Class<K> kind) {
		super(graph, kind);
		this.delegate = delegate;
		this.explicit = false;
	}

	public FramingCollection(Collection<E> delegate, FramedGraph graph) {
		super(graph);
		this.delegate = delegate;
		this.explicit = false;
	}

	public FramingCollection(Collection<E> delegate, FramedGraph graph, Class<K> kind, boolean explicit) {
		super(graph, kind);
		this.delegate = delegate;
		this.explicit = explicit;
	}

	public FramingCollection(Collection<E> delegate, FramedGraph graph, boolean explicit) {
		super(graph);
		this.delegate = delegate;
		this.explicit = explicit;
	}

	public int size() {
		throw new UnsupportedOperationException();
	}

	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	public boolean contains(Object o) {
		throw new UnsupportedOperationException();
	}

	public Iterator<E> iterator() {
		throw new UnsupportedOperationException();
	}

	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	public boolean add(E e) {
		e = (this.explicit ? this.<E>makeFrameExplicit(e) : this.<E>makeFrame(e));

		return delegate.add(e);
	}

	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	public boolean addAll(Collection<? extends E> c) {
		boolean modified = false;
		for (E e : c) {
			modified |= add(e);
		}
		return modified;
	}

	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public boolean equals(Object o) {
		throw new UnsupportedOperationException();
	}

	public int hashCode() {
		throw new UnsupportedOperationException();
	}

	public Collection<E> getDelegate() {

		return delegate;
	}

}

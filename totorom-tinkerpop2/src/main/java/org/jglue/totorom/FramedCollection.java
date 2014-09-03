package org.jglue.totorom;

import java.util.Collection;
import java.util.Iterator;

import com.tinkerpop.blueprints.Element;

public class FramedCollection<E, F extends FramedElement> implements Collection<E> {

	private Collection<E> delegate;
	private FramedGraph graph;
	private Class<F> kind;

	public FramedCollection(Collection<E> delegate, FramedGraph graph, Class<F> kind) {
		this.delegate = delegate;
		this.graph = graph;
		this.kind = kind;
		
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
		if(e instanceof Element) {
			e = (E)graph.frameElement((Element)e, kind);
		}
		
		return delegate.add(e);
	}

	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
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

}

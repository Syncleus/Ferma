package org.jglue.totorom;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class FramingMap<T extends FramedElement> extends FrameMaker<T> implements Map {

	public FramingMap(Map delegate, FramedGraph graph) {
		super(graph);
		this.delegate = delegate;
	}

	private Map delegate;
	
	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsKey(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object get(Object key) {
		return removeFrame(delegate.get(makeFrame(key)));
	}

	@Override
	public Object put(Object key, Object value) {
		return delegate.put(makeFrame(key), makeFrame(value));
	}

	@Override
	public Object remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(Map m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set keySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection values() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set entrySet() {
		return delegate.entrySet();
	}
	
	@Override
	public String toString() {
	
		return delegate.toString();
	}

}

package org.jglue.totorom;

import java.util.Comparator;

/**
 * Framed elements before delegation.
 * 
 * @author bryn
 *
 * @param <T>
 */
class FramingComparator<T, K extends FramedElement> extends FrameMaker<K> implements Comparator<T> {

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

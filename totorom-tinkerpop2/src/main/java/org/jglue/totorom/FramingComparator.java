package org.jglue.totorom;

import java.util.Comparator;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Vertex;

/**
 * Framed elements before delegation.
 * 
 * @author bryn
 *
 * @param <T>
 */
class FramingComparator<T, K extends FramedElement> implements Comparator<T> {

	private Comparator<T> delegate;
	private FramedGraph graph;
	private Class<K> kind;

	public FramingComparator(Comparator<T> delegate, FramedGraph graph, Class<K> kind) {
		this.delegate = delegate;
		this.graph = graph;
		this.kind = kind;
	}

	@Override
	public int compare(T o1, T o2) {

		if (o1 instanceof Element) {
			o1 = (T) graph.frameElement((Element) o1, kind);
		}

		if (o2 instanceof Element) {
			o2 = (T) graph.frameElement((Element) o2, kind);
		} 

		return delegate.compare(o1, o2);
	}

}

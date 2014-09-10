package org.jglue.totorom;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.pipes.util.structures.Pair;

class FrameMaker {
	private FramedGraph graph;
	private Class<?> kind;

	public <T extends FramedElement> FrameMaker(FramedGraph graph, Class<T> kind) {
		this.graph = graph;
		this.kind = kind;
	}

	public FrameMaker(FramedGraph graph) {
		this(graph, null);
	}

	<N> N makeFrame(Object o) {
		if (o instanceof FramingMap) {
			o = ((FramingMap) o).getDelegate();
		}
		if (o instanceof Pair) {
			Pair pair = (Pair) o;
			o = new Pair(makeFrame(pair.getA()), makeFrame(pair.getB()));
		}
		if (kind == null) {
			if (o instanceof Edge) {
				o = graph.frameElement((Element) o, TEdge.class);
			} else if (o instanceof Vertex) {
				o = graph.frameElement((Element) o, TVertex.class);
			}
		} else {
			if (o instanceof Element) {
				o = graph.frameElement((Element) o, (Class<FramedElement>)kind);
			}
		}
		return (N) o;
	}

	protected Object removeFrame(Object object) {
		if (object instanceof FramedElement) {
			return ((FramedElement) object).element();
		}
		return object;
	}

}

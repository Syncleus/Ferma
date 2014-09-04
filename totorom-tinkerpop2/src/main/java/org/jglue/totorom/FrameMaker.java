package org.jglue.totorom;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Vertex;

class FrameMaker<T extends FramedElement> {
	private FramedGraph graph;
	private Class<T> kind;

	public FrameMaker(FramedGraph graph, Class<T> kind) {
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
		if (kind == null) {
			if (o instanceof Edge) {
				o = graph.frameElement((Element) o, TEdge.class);
			} else if (o instanceof Vertex) {
				o = graph.frameElement((Element) o, TVertex.class);
			}
		}
		else {
			if(o instanceof Element) {
				o = graph.frameElement((Element) o, kind);
			}
		}
		return (N) o;
	}

	protected Object removeFrame(Object object) {
		if(object instanceof FramedElement) {
			return ((FramedElement) object).element();
		}
		return object;
	}
	
}

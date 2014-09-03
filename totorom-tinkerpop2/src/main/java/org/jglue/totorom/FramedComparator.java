package org.jglue.totorom;

import java.util.Comparator;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class FramedComparator<T> implements Comparator<T>{

	private Comparator<T> delegate;
	private FramedGraph graph;
	
	public FramedComparator(Comparator<T> delegate, FramedGraph graph) {
		this.delegate = delegate;
		this.graph = graph;
	}

	@Override
	public int compare(T o1, T o2) {
		
		if(o1 instanceof Edge) {
			o1 = (T)graph.frameElement((Edge)o1, GenericFramedEdge.class);
		}
		else if(o1 instanceof Vertex) {
			o1 = (T)graph.frameElement((Vertex)o1, GenericFramedVertex.class);
		}
		
		if(o2 instanceof Edge) {
			o2 = (T)graph.frameElement((Edge)o2, GenericFramedEdge.class);
		}
		else if(o2 instanceof Vertex) {
			o2 = (T)graph.frameElement((Vertex)o2, GenericFramedVertex.class);
		}
		
		return delegate.compare(o1, o2);
	}

}

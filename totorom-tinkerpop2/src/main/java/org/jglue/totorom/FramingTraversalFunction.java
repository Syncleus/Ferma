package org.jglue.totorom;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.pipes.PipeFunction;

/**
 * Frames parameters to a traversal function.
 * @author bryn
 *
 * @param <A>
 * @param <B>
 */
class FramingTraversalFunction<A extends FramedElement, B> implements TraversalFunction<A, B>{
	private PipeFunction<A, B> delegate;
	private FramedGraph graph;
	private Class<A> kind;

	public FramingTraversalFunction(PipeFunction<A, B> delegate, FramedGraph graph, Class<A> kind) {
		super();
		this.delegate = delegate;
		this.graph = graph;
		this.kind = kind;
	}

	public FramingTraversalFunction(FramedGraph graph, Class<A> kind) {
		this.graph = graph;
	}

	@Override
	public B compute(A argument) {
		if(kind != null && argument instanceof Element) {
			argument = (A)graph.frameElement((Element)argument, kind);
		}
		else {
			if(argument instanceof Edge) {
				argument = (A)graph.frameElement((Element)argument, GenericFramedEdge.class);
			}
			else if(argument instanceof Vertex) {
				argument = (A)graph.frameElement((Element)argument, GenericFramedVertex.class);
			}
		}
		
		
		if(delegate == null) {
			return (B)argument;
		}
	
		return delegate.compute(argument);
		
		
		
		
		
		
	}
	
	
}

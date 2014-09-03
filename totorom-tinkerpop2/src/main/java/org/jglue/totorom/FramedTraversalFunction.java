package org.jglue.totorom;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.pipes.PipeFunction;

class FramedTraversalFunction<A, B> implements TraversalFunction<A, B>{
	private PipeFunction<A, B> delegate;
	private FramedGraph graph;

	public FramedTraversalFunction(PipeFunction<A, B> delegate, FramedGraph graph) {
		super();
		this.delegate = delegate;
		this.graph = graph;
	}

	public FramedTraversalFunction(FramedGraph graph2) {
		this.graph = graph;
	}

	@Override
	public B compute(A argument) {
		if(argument instanceof Edge) {
			argument = (A)graph.frameElement((Element)argument, GenericFramedEdge.class);

		}
		if(argument instanceof Vertex) {
			argument = (A)graph.frameElement((Element)argument, GenericFramedVertex.class);
		}
		
		if(delegate == null) {
			return (B)argument;
		}
	
		return delegate.compute(argument);
		
		
		
		
		
		
	}
	
	
}

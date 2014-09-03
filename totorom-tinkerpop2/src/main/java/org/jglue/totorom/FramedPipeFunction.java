package org.jglue.totorom;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.pipes.PipeFunction;

class FramedPipeFunction<A, B> implements PipeFunction<A, B>{
	private PipeFunction<A, B> delegate;
	private FramedGraph graph;

	public FramedPipeFunction(PipeFunction<A, B> delegate, FramedGraph graph) {
		super();
		this.delegate = delegate;
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
		B result = delegate.compute(argument);
		
		if(result instanceof FramedElement) {
			result = (B)((FramedElement) result).element();
		}
		return result;
		
		
		
		
		
		
	}
	
	
}

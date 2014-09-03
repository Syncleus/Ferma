package org.jglue.totorom;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.Tokens.T;

public class FramedSideEffectFunction<T> implements SideEffectFunction<T> {
	private SideEffectFunction<T> delegate;
	private FramedGraph graph;
	
	

	public FramedSideEffectFunction(SideEffectFunction<T> delegate, FramedGraph graph) {
		this.delegate = delegate;
		this.graph = graph;
	}



	@Override
	public void execute(T argument) {
		if(argument instanceof Edge) {
			argument = (T)graph.frameElement((Element)argument, GenericFramedEdge.class);

		}
		if(argument instanceof Vertex) {
			argument = (T)graph.frameElement((Element)argument, GenericFramedVertex.class);
		}
		delegate.execute(argument);
	}

}

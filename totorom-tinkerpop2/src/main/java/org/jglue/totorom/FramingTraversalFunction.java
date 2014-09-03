package org.jglue.totorom;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.pipes.PipeFunction;

/**
 * Frames parameters to a traversal function.
 * 
 * @author bryn
 *
 * @param <A>
 * @param <B>
 */
class FramingTraversalFunction<A extends FramedElement, B> extends FrameMaker<A> implements TraversalFunction<A, B> {
	private PipeFunction<A, B> delegate;

	public FramingTraversalFunction(PipeFunction<A, B> delegate, FramedGraph graph, Class<A> kind) {
		super(graph, kind);
		this.delegate = delegate;
	}

	public FramingTraversalFunction(PipeFunction<A, B> delegate, FramedGraph graph) {
		super(graph);
		this.delegate = delegate;
	}

	public FramingTraversalFunction(FramedGraph graph, Class<A> kind) {
		super(graph, kind);
	}

	@Override
	public B compute(A argument) {

		argument = makeFrame(argument);

		if (delegate == null) {
			return (B) argument;
		}

		return delegate.compute(argument);

	}

}

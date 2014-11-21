package com.syncleus.ferma;

import com.tinkerpop.pipes.PipeFunction;

/**
 * Frames parameters to a traversal function.
 * 
 * @author bryn
 *
 * @param <A>
 * @param <B>
 */
class FramingTraversalFunction<A, B, C> extends FrameMaker implements TraversalFunction<C, B> {
	private PipeFunction<A, B> delegate;

	public <T extends FramedElement> FramingTraversalFunction(PipeFunction<A, B> delegate, FramedGraph graph, Class<T> kind) {
		super(graph, kind);
		this.delegate = delegate;
	}

	public FramingTraversalFunction(PipeFunction<A, B> delegate, FramedGraph graph) {
		super(graph);
		this.delegate = delegate;
	}

	public <T extends FramedElement> FramingTraversalFunction(FramedGraph graph, Class<T> kind) {
		super(graph, kind);
	}

	public FramingTraversalFunction(FramedGraph graph) {
		super(graph);
	}

	@Override
	public B compute(C argument) {

		argument = makeFrame(argument);

		if (delegate == null) {
			return (B) argument;
		}

		return delegate.compute((A) argument);

	}

}

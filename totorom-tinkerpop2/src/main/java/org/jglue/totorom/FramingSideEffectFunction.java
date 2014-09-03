package org.jglue.totorom;

import com.tinkerpop.blueprints.Element;

/**
 * Frames the argument before delegation.
 * @author bryn
 *
 * @param <T>
 */
class FramingSideEffectFunction<T, K extends FramedElement> extends FrameMaker<K> implements SideEffectFunction<T> {
	private SideEffectFunction<T> delegate;
		

	public FramingSideEffectFunction(SideEffectFunction<T> delegate, FramedGraph graph, Class<K> kind) {
		super(graph, kind);
		this.delegate = delegate;
		
	}



	@Override
	public void execute(T argument) {
		argument = makeFrame(argument);
		
		delegate.execute(argument);
	}

}

package org.jglue.totorom;

import com.tinkerpop.blueprints.Element;

/**
 * Frames the argument before delegation.
 * @author bryn
 *
 * @param <T>
 */
class FramingSideEffectFunction<T, K extends FramedElement> implements SideEffectFunction<T> {
	private SideEffectFunction<T> delegate;
	private FramedGraph graph;
	private Class<K> kind;
	
	

	public FramingSideEffectFunction(SideEffectFunction<T> delegate, FramedGraph graph, Class<K> kind) {
		this.delegate = delegate;
		this.graph = graph;
		this.kind = kind;
	}



	@Override
	public void execute(T argument) {
		if(argument instanceof Element) {
			argument = (T)graph.frameElement((Element)argument, kind);
		}
		
		delegate.execute(argument);
	}

}

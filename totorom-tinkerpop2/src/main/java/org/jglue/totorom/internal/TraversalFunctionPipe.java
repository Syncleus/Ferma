package org.jglue.totorom.internal;

import java.util.Iterator;
import java.util.List;

import org.jglue.totorom.TraversalFunction;

import com.tinkerpop.pipes.Pipe;

public class TraversalFunctionPipe implements TraversalFunction {

	private TraversalFunction delegate;

	public TraversalFunctionPipe(TraversalFunction delegate) {
		this.delegate = delegate;
	}

	@Override
	public Object compute(Object argument) {
		Object result = delegate.compute(argument);
		if(result instanceof Iterator) {
			final Iterator i = (Iterator) result; 
			return new Pipe() {

				@Override
				public boolean hasNext() {
					return i.hasNext();
				}

				@Override
				public Object next() {
					return i.next();
				}

				@Override
				public Iterator iterator() {
					return null;
				}

				@Override
				public void setStarts(Iterator starts) {
					
				}

				@Override
				public void setStarts(Iterable starts) {
					
				}

				@Override
				public List getCurrentPath() {
					return null;
				}

				@Override
				public void enablePath(boolean enable) {
					
				}

				@Override
				public void reset() {
					
				}
			};
		}
		return result;
	}

}

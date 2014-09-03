package org.jglue.totorom;

/**
 * 
 * Takes a value but does not return anything.
 * @author bryn
 *
 * @param <T>
 */
public interface SideEffectFunction<T> {

	void execute(T o);
	
}

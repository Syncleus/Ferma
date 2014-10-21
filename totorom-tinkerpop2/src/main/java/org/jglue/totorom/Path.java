package org.jglue.totorom;

import java.util.ArrayList;

import com.tinkerpop.gremlin.Tokens.T;

public class Path extends ArrayList<Object> {

	public <T> T get(int index, Class<T> clazz) {
		
		T object = (T) get(index);
		if(object instanceof TVertex) {
			return (T) ((TVertex) object).reframe((Class)clazz);
		}
		if(object instanceof TEdge) {
			return (T) ((TEdge) object).reframe((Class)clazz);
		}
		return object;
	}

}

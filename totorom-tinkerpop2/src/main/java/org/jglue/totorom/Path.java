package org.jglue.totorom;

import java.util.ArrayList;

import com.tinkerpop.gremlin.Tokens.T;

public class Path extends ArrayList<Object> {

	public <T> T get(int index, Class<T> clazz) {
		return (T) get(index);
	}

}

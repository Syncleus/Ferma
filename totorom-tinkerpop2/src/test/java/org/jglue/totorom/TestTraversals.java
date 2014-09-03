package org.jglue.totorom;

import java.util.Comparator;
import java.util.Map;

import org.junit.Test;

import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;

public class TestTraversals {

	@Test
	public void test() {
		FramedGraph graph = new FramedGraph(TinkerGraphFactory.createTinkerGraph());
		
		System.out.println(graph.V().out().groupCount().cap().orderMap(new Comparator<Map.Entry<GenericFramedVertex, Number>>() {
			
			@Override
			public int compare(Map.Entry<GenericFramedVertex, Number> o1, Map.Entry<GenericFramedVertex, Number> o2) {
				String id1 = ((GenericFramedVertex)o1.getKey()).getId();
				String id2 = ((GenericFramedVertex)o2.getKey()).getId();
				return id1.compareTo(id2);
			}
		}).toList());
	}
}

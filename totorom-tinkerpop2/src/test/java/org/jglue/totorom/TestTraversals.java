package org.jglue.totorom;

import java.util.Comparator;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;

public class TestTraversals {
	private FramedGraph graph = new FramedGraph(TinkerGraphFactory.createTinkerGraph());

	@Test
	public void testOrderMap() {

		Assert.assertEquals(4,
				graph.V().out().groupCount().cap().orderMap(new Comparator<Map.Entry<GenericFramedVertex, Number>>() {

					@Override
					public int compare(Map.Entry<GenericFramedVertex, Number> o1, Map.Entry<GenericFramedVertex, Number> o2) {
						String id1 = o1.getKey().getId();
						String id2 = o2.getKey().getId();
						return id1.compareTo(id2);
					}
				}).count());

	}

	@Test
	public void testAnd() {
		Assert.assertEquals(3, graph.V().and(new TraversalFunction<GenericFramedVertex, FramedTraversal<?, ?, ?, ?>>() {

			@Override
			public FramedTraversal<?, ?, ?, ?> compute(GenericFramedVertex argument) {
				return argument.out().has("name");
			}
		}).count());
	}
	
	@Test
	public void testOr() {
		Assert.assertEquals(3, graph.V().or(new TraversalFunction<GenericFramedVertex, FramedTraversal<?, ?, ?, ?>>() {

			@Override
			public FramedTraversal<?, ?, ?, ?> compute(GenericFramedVertex argument) {
				return argument.out().has("name");
			}
		}).count());
	}
}

package org.jglue.totorom;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;

public class TestTraversals {
	private FramedGraph graph = new FramedGraph(TinkerGraphFactory.createTinkerGraph());

	@Test
	public void testGroupCount() {

		Assert.assertEquals(4,
				graph.V().out().groupCount().cap().size());
		
		Assert.assertEquals(6,
				graph.V().out().groupCount().cap(new SideEffectFunction<Map<TVertex,Long>>() {
					
					@Override
					public void execute(Map<TVertex, Long> o) {
						Assert.assertEquals(4, o.size());
					}
				}).count());

	}

	@Test
	public void testAnd() {
		Assert.assertEquals(3, graph.V().and(new TraversalFunction<TVertex, Traversal<?, ?, ?>>() {

			@Override
			public Traversal<?, ?, ?> compute(TVertex argument) {
				return argument.out().has("name");
			}
		}).count());
	}
	
	@Test
	public void testOr() {
		Assert.assertEquals(3, graph.V().or(new TraversalFunction<TVertex, Traversal<?, ?, ?>>() {

			@Override
			public Traversal<?, ?, ?> compute(TVertex argument) {
				return argument.out().has("name");
			}
		}).count());
	}
}

package com.syncleus.ferma;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import com.tinkerpop.gremlin.Tokens.T;
import com.tinkerpop.pipes.transform.TransformPipe.Order;
import com.tinkerpop.pipes.util.structures.Row;
import com.tinkerpop.pipes.util.structures.Table;
import com.tinkerpop.pipes.util.structures.Tree;

public class TestTraversals {
	private FramedGraph graph = new FramedGraph(TinkerGraphFactory.createTinkerGraph());

	@Test
	public void testCopySplit() {
		List<TVertex> fair = graph.v(1).out("knows").copySplit(new TraversalFunction<TVertex, Traversal<TVertex, ?, ?, ?>>() {

			@Override
			public Traversal<TVertex, ?, ?, ?> compute(TVertex v) {
				return v.traversal();
			}
		}, new TraversalFunction<TVertex, Traversal<TVertex, ?, ?, ?>>() {

			@Override
			public Traversal<TVertex, ?, ?, ?> compute(TVertex v) {
				return v.out();
			}
		}).fairMerge().toList();
		
		List<TVertex> exhaust = graph.v(1).out("knows").copySplit(new TraversalFunction<TVertex, Traversal<TVertex, ?, ?, ?>>() {

			@Override
			public Traversal<TVertex, ?, ?, ?> compute(TVertex v) {
				return v.traversal();
			}
		}, new TraversalFunction<TVertex, Traversal<TVertex, ?, ?, ?>>() {

			@Override
			public Traversal<TVertex, ?, ?, ?> compute(TVertex v) {
				return v.out();
			}
		}).exhaustMerge().toList();
		
		Assert.assertEquals(exhaust.get(0), fair.get(0));
		Assert.assertNotEquals(exhaust.get(1), fair.get(1));
		Assert.assertEquals(exhaust.get(2), fair.get(1));
		Assert.assertEquals(exhaust.get(1), fair.get(2));
		Assert.assertTrue(exhaust.get(0) instanceof TVertex);
		
		
	}
	
	@Test
	public void testMarkBack() {

		Assert.assertEquals(29, graph.V().mark().outE("knows").inV().has("age", T.gt, 30).back().property("age").next());
	}

	@Test
	public void testMarkOptional() {
		List<TVertex> aggregate = new ArrayList<>();
		Assert.assertEquals(6, graph.V().mark().outE("knows").inV().has("age", T.gt, 30).aggregate(aggregate).optional()
				.property("age").count());
		Assert.assertEquals(graph.v(4).next(), aggregate.get(0));
	}

	@Test
	public void testBoth() {
		Assert.assertEquals(3, graph.v(4).both().count());
		Assert.assertEquals(1, graph.v(4).both("knows").count());
		Assert.assertEquals(3, graph.v(4).both("knows", "created").count());
		Assert.assertEquals(1, graph.v(4).both(1, "knows", "created").count());

	}

	@Test
	public void testBothE() {
		Assert.assertEquals(3, graph.v(4).bothE().count());
		Assert.assertEquals(1, graph.v(4).bothE("knows").count());
		Assert.assertEquals(3, graph.v(4).bothE("knows", "created").count());
		Assert.assertEquals(1, graph.v(4).bothE(1, "knows", "created").count());

	}

	@Test
	public void testBothV() {

		Assert.assertEquals(2, graph.e(12).bothV().count());

	}

	@Test
	public void testCap() {

		Assert.assertEquals(3, graph.V().has("lang", "java").in("created").groupCount().cap().size());
	}

	@Test
	public void testId() {
		Assert.assertEquals("1", graph.V().has("name", "marko").id().next());
	}

	@Test
	public void testIn() {
		Assert.assertEquals(1, graph.v(4).in().count());
		Assert.assertEquals(3, graph.v(3).in("created").count());
		Assert.assertEquals(2, graph.v(3).in(2, "created").count());

	}

	@Test
	public void testInE() {
		Assert.assertEquals(1, graph.v(4).inE().count());
		Assert.assertEquals(3, graph.v(3).inE("created").count());
		Assert.assertEquals(2, graph.v(3).inE(2, "created").count());

	}

	@Test
	public void testInV() {

		Assert.assertEquals(graph.v(3).next(), graph.e(12).inV().next());

	}

	@Test
	public void testOutV() {
		Assert.assertEquals(graph.v(6).next(), graph.e(12).outV().next());

	}

	@Test
	public void testProperty() {
		Assert.assertEquals("marko", graph.v(1).property("name").next());

	}

	@Test
	public void testLabel() {
		Assert.assertEquals("created", graph.v(6).outE().label().next());

	}

	@Test
	public void testMap() {
		Assert.assertEquals(2, graph.v(1).map().next().size());
		Assert.assertEquals(6, graph.V().map("id", "age").count());

	}

	@Test
	public void testMemoize() {
		Assert.assertEquals(2, graph.V().out().as("here").out().memoize("here").property("name").count());
		Map<?, ?> map = new HashMap<>();
		Assert.assertEquals(2, graph.V().out().as("here").out().memoize("here", map).property("name").count());
		Assert.assertEquals(4, map.size());
	}

	@Test
	public void testOrder() {
		Assert.assertEquals("josh", graph.V().property("name").order().next());
		Assert.assertEquals("vadas", graph.V().property("name").order(Order.DECR).next());

		Assert.assertEquals(graph.v(2).next(), graph.V().order(Comparators.<TVertex> property("name")).out("knows").next());

	}

	@Test
	public void testOut() {

		Assert.assertEquals(3, graph.v(1).out().count());
		Assert.assertEquals(2, graph.v(1).out("knows").count());
		Assert.assertEquals(1, graph.v(1).out(1, "knows").count());

	}

	@Test
	public void testOutE() {

		Assert.assertEquals(3, graph.v(1).outE().count());
		Assert.assertEquals(2, graph.v(1).outE("knows").count());
		Assert.assertEquals(1, graph.v(1).outE(1, "knows").count());

	}

	@Test
	public void testPath() {

		Path path = graph.v(1).out().path().next();

		Assert.assertEquals(2, path.size());
		Assert.assertTrue(path.get(0, TVertex.class) instanceof TVertex);
		Assert.assertTrue(path.get(1, TVertex.class) instanceof TVertex);

	}

	@Test
	public void testGatherScatter() {

		Assert.assertEquals(6, graph.V().gatherScatter().out().count());

		Assert.assertEquals(3, graph.V().range(0, 2).gatherScatter().out().count());

	}

	@Test
	public void testSelect() {
		Row<?> row = graph.v(1).as("x").out("knows").as("y").select().next();
		Assert.assertEquals(graph.v(1).next(), row.get(0));
		Assert.assertEquals(graph.v(2).next(), row.get(1));
	}

	@Test
	public void testShuffle() {
		Assert.assertTrue(graph.v(1).out().shuffle().next() instanceof TVertex);

	}

	@Test
	public void testTransform() {
		Assert.assertEquals(new Integer(1), graph.v(1).transform(new TraversalFunction<TVertex, Integer>() {

			@Override
			public Integer compute(TVertex argument) {

				return 1;
			}
		}).next());

	}

	@Test
	public void testRange() {
		Assert.assertEquals(graph.v(2).next(), graph.v(1).out().range(1, 1).next());

	}

	@Test
	public void testAnd() {
		Assert.assertEquals(2, graph.V().and(new TraversalFunction<TVertex, Traversal<?, ?, ?, ?>>() {

			@Override
			public Traversal<?, ?, ?, ?> compute(TVertex v) {
				return v.both("knows");
			}
		}, new TraversalFunction<TVertex, Traversal<?, ?, ?, ?>>() {

			@Override
			public Traversal<?, ?, ?, ?> compute(TVertex v) {
				return v.both("created");
			}
		}).count());
	}

	@Test
	public void testOr() {
		Assert.assertEquals(3, graph.V().or(new TraversalFunction<TVertex, Traversal<?, ?, ?, ?>>() {

			@Override
			public Traversal<?, ?, ?, ?> compute(TVertex argument) {
				return argument.out().has("name");
			}
		}).count());
	}

	@Test
	public void testDedup() {
		Assert.assertEquals(3, graph.v(1).out().in().dedup().count());
	}

	@Test
	public void testExcept() {
		Assert.assertEquals(5, graph.V().except(graph.v(1)).count());
	}

	@Test
	public void testFilter() {
		Assert.assertEquals(2, graph.V().filter(new TraversalFunction<TVertex, Boolean>() {

			@Override
			public Boolean compute(TVertex argument) {
				return argument.getProperty("age") != null && argument.getProperty("age", Integer.class) > 29;
			}
		}).count());
	}

	@Test
	public void testHas() {
		Assert.assertEquals(1, graph.V().has("name", "marko").count());
	}

	@Test
	public void testHasNot() {
		Assert.assertEquals(5, graph.V().hasNot("name", "marko").count());
	}

	@Test
	public void testInterval() {
		Assert.assertEquals(3, graph.E().interval("weight", 0.3f, 0.9f).count());
	}

	@Test
	public void testRandom() {
		// Assert.assertEquals(3, graph.V().random(0.5).count()); //Can't test
		// this :)
	}

	@Test
	public void testRetain() {
		Assert.assertEquals(1, graph.V().retain(graph.v(1)).count());
	}

	@Test
	public void testSimplePath() {
		Assert.assertEquals(2, graph.v(1).out().in().simplePath().count());
	}

	@Test
	public void testAggregate() {

		List<TVertex> x = new ArrayList<TVertex>();
		Assert.assertEquals(graph.v(3).next(), graph.v(1).out().aggregate(x).next());
		Assert.assertEquals(3, graph.v(1).out().aggregate().cap().size());
		Assert.assertEquals(3, x.size());
		Assert.assertTrue(x.get(0) instanceof TVertex);
	}

	@Test
	public void testGroupCount() {

		Map<TVertex, Long> cap = graph.V().out().groupCount().cap();
		Assert.assertEquals(4, cap.size());
		Assert.assertTrue(cap.keySet().iterator().next() instanceof TVertex);

		Assert.assertEquals(4, graph.V().out().groupCount(new TraversalFunction<TVertex, String>() {

			@Override
			public String compute(TVertex argument) {
				return argument.getId();

			}
		}).cap().size());

		Assert.assertEquals(6, graph.V().out().groupCount().divert(new SideEffectFunction<Map<TVertex, Long>>() {

			@Override
			public void execute(Map<TVertex, Long> o) {
				Assert.assertEquals(4, o.size());
			}
		}).count());

	}

	@Test
	public void testGroupBy() {

		Map<TVertex, List<TVertex>> cap = graph.V()
				.groupBy(TraversalFunctions.<TVertex> identity(), new TraversalFunction<TVertex, Iterator<TVertex>>() {

					@Override
					public Iterator<TVertex> compute(TVertex argument) {
						return argument.out();
					}

				}).cap();
		Assert.assertEquals(6, cap.size());
		Assert.assertEquals(3, cap.get(graph.v(1).next()).size());
		Assert.assertTrue(cap.get(graph.v(1).next()).iterator().next() instanceof TVertex);

	}

	@Test
	public void testOptional() {

	}

	@Test
	public void testSideEffect() {
		final List<TVertex> collected = new ArrayList<TVertex>();
		graph.v(1).sideEffect(new SideEffectFunction<TVertex>() {

			@Override
			public void execute(TVertex o) {
				collected.add(o);

			}
		}).iterate();
		Assert.assertEquals(1, collected.size());

	}

	@Test
	public void testStore() {
		Collection<TVertex> x = new ArrayList<>();
		graph.v(1).store(x).next();
		Assert.assertEquals(graph.v(1).next(), x.iterator().next());
		Assert.assertEquals(graph.v(1).next(), graph.v(1).store().cap().iterator().next());
	}

	@Test
	public void testTable() {

		Table table = graph.V().as("vertex").mark().property("name").as("name").back().property("age").as("age").table().cap();
		Assert.assertEquals(6, table.size());
		Assert.assertTrue(table.get(0).get(0) instanceof TVertex);

	}

	@Test
	public void testTree() {

		Tree<TVertex> tree = graph.v(1).out().out().tree().cap();
		Assert.assertEquals(1, tree.get(graph.v(1).next()).size());


	}
	
	
	@Test
	public void testLoop() {

		List<TVertex> list = graph.v(1).loop(new TraversalFunction<TVertex, VertexTraversal<?, ?, ?>>() {
			
			@Override
			public VertexTraversal<?, ?, ?> compute(TVertex argument) {
				return argument.out();
			}
		}, 3).toList();
		Assert.assertEquals(2, list.size());
	}
	
	@Test
	public void testNext() {
		Assert.assertEquals(graph.v(3).next(), graph.v(6).out("created").next());
	}

	@Test(expected = NoSuchElementException.class)
	public void testNextNoSuchElement() {
		graph.v(6).out("knows").next();
	}

	@Test
	public void testNextOrDefault() {
		TVertex defaultValue = new TVertex(); 
		Assert.assertEquals(graph.v(3).next(), graph.v(6).out("created").nextOrDefault(defaultValue));
	}

	@Test
	public void testNextOrDefaultNoSuchElement() {
		TVertex defaultValue = new TVertex(); 
		Assert.assertEquals(defaultValue, graph.v(6).out("knows").nextOrDefault(defaultValue));
	}
	
	@Test
	public void testNextOrDefaultWithKind() {
		Program defaultValue = new Program(); 
		Assert.assertEquals(graph.v(3).next(Program.class), graph.v(6).out("created").nextOrDefault(Program.class, defaultValue));
	}

	@Test
	public void testNextOrDefaultWithKindNoSuchElement() {
		Person defaultValue = new Person(); 
		Assert.assertEquals(defaultValue, graph.v(6).out("knows").nextOrDefault(Person.class, defaultValue));
	}
	
	@Test
	public void testLimit() {
		Assert.assertEquals(1, graph.V().limit(1).count());
		Assert.assertEquals(2, graph.V().limit(2).count());
	}
}

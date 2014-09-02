package org.jglue.totorom;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.Tokens.T;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.util.structures.Row;

public class FramedTraversalImpl extends FramedTraversalBase implements FramedTraversal {

	private FramedGraph graph;
	private GremlinPipeline pipeline;

	private FramedEdgeTraversal edgeTraversal = new FramedEdgeTraversalImpl();
	private FramedVertexTraversal vertexTraversal = new FramedVertexTraversalImpl();

	private FramedTraversalImpl(FramedGraph graph, GremlinPipeline pipeline) {
		this.graph = graph;
		this.pipeline = pipeline;

	}

	protected FramedTraversalImpl(FramedGraph graph, Graph delegate) {
		this(graph, new GremlinPipeline<>(delegate));
	}

	protected FramedTraversalImpl(FramedGraph graph, Iterator starts) {
		this(graph, new GremlinPipeline<>(starts));
	}

	protected FramedTraversalImpl(FramedGraph graph, Element starts) {
		this(graph, new GremlinPipeline<>(starts));
	}

	@Override
	public FramedVertexTraversal V() {
		pipeline.V();
		return vertexTraversal;
	}

	@Override
	public FramedEdgeTraversal E() {
		pipeline.E();
		return edgeTraversal;
	}

	@Override
	public FramedVertexTraversal v(Object... ids) {
		return (FramedVertexTraversal) graph.v(ids);
	}

	@Override
	public FramedEdgeTraversal e(Object... ids) {
		return (FramedEdgeTraversal) graph.e(ids);
	}

	@Override
	public FramedTraversal identity() {
		pipeline._();
		return this;
	}

	@Override
	public long count() {
		return pipeline.count();
	}

	/**
	 * @return Cast the traversal to a {@link FramedVertexTraversal}
	 */
	public FramedVertexTraversal asVertices() {
		return vertexTraversal;
	}

	/**
	 * @return Cast the traversal to a {@link FramedEdgeTraversal}
	 */
	public FramedEdgeTraversal asEdges() {
		return edgeTraversal;
	}

	@Override
	protected GremlinPipeline pipeline() {

		return pipeline;
	}

	@Override
	protected FramedGraph graph() {

		return graph;
	}

	protected class FramedEdgeTraversalImpl extends FramedTraversalBase<Edge> implements FramedEdgeTraversal {
		@Override
		public FramedEdgeTraversal has(String key) {
			return (FramedEdgeTraversal)super.has(key);
		}

		@Override
		public FramedEdgeTraversal has(String key, Object value) {
			return (FramedEdgeTraversal)super.has(key, value);
		}

		@Override
		public FramedEdgeTraversal has(String key, Predicate predicate, Object value) {
			return (FramedEdgeTraversal)super.has(key, predicate, value);
		}

		@Override
		public FramedEdgeTraversal has(String key, com.tinkerpop.gremlin.Tokens.T compareToken, Object value) {
			return (FramedEdgeTraversal)super.has(key, compareToken, value);
		}

		@Override
		public FramedEdgeTraversal hasNot(String key) {
			return (FramedEdgeTraversal) super.hasNot(key);
		}

		@Override
		public FramedEdgeTraversal hasNot(String key, Object value) {
			return (FramedEdgeTraversal) super.hasNot(key, value);
		}

		@Override
		public FramedEdgeTraversal as(String name) {
			return (FramedEdgeTraversal) super.as(name);
		}
		
		@Override
		public FramedEdgeTraversal identity() {
		
			return (FramedEdgeTraversal)super.identity();
		}
		
		@Override
		public FramedEdgeTraversal interval(String key, Comparable startValue, Comparable endValue) {
			return (FramedEdgeTraversal)super.interval(key, startValue, endValue);
		}
		
		

		@Override
		public FramedVertexTraversal inV() {
			pipeline.inV();
			return vertexTraversal;
		}

		@Override
		public FramedVertexTraversal outV() {
			pipeline.outV();
			return vertexTraversal;
		}

		@Override
		public FramedVertexTraversal bothV() {
			pipeline.bothV();
			return asVertices();
		}

		@Override
		public FramedEdge next(Class kind) {
			return graph.frameElement((Element) pipeline.next(), kind);
		}

		@Override
		public List next(int amount, final Class kind) {
			return Lists.transform(pipeline.next(amount), new Function() {

				public Object apply(Object e) {
					return graph.frameElement((Element) e, kind);
				}
			});
		}

		@Override
		public Iterator frame(final Class kind) {
			return Iterators.transform(pipeline, new Function() {

				public Object apply(Object e) {
					return graph.frameElement((Element) e, kind);
				}
			});
		}

		@Override
		public List<T> toList(final Class kind) {
			return Lists.transform(pipeline.toList(), new Function() {

				public Object apply(Object e) {
					return graph.frameElement((Element) e, kind);
				}
			});
		}

		@Override
		public FramedVertexTraversal asVertices() {
			return vertexTraversal;
		}

		@Override
		public FramedEdgeTraversal asEdges() {
			return edgeTraversal;
		}

		@Override
		protected FramedGraph graph() {
			return graph;
		}

		@Override
		protected GremlinPipeline pipeline() {
			return pipeline;
		}

		@Override
		public FramedTraversal label() {
			pipeline.label();
			return this;
		}

	}

	protected class FramedVertexTraversalImpl extends FramedTraversalBase<Vertex> implements FramedVertexTraversal {

		@Override
		public FramedVertexTraversal out(int branchFactor, String... labels) {
			pipeline.out(branchFactor, labels);
			return this;
		}

		@Override
		public FramedVertexTraversal out(String... labels) {
			pipeline.out(labels);
			return this;
		}

		@Override
		public FramedVertexTraversal in(int branchFactor, String... labels) {
			pipeline.in(branchFactor, labels);
			return this;
		}

		@Override
		public FramedVertexTraversal in(String... labels) {
			pipeline.in(labels);
			return this;
		}

		@Override
		public FramedVertexTraversal both(int branchFactor, String... labels) {
			pipeline.both(branchFactor, labels);
			return this;
		}

		@Override
		public FramedVertexTraversal both(String... labels) {
			pipeline.both(labels);
			return this;
		}

		@Override
		public FramedEdgeTraversal outE(int branchFactor, String... labels) {
			pipeline.outE(branchFactor, labels);
			return edgeTraversal;
		}

		@Override
		public FramedEdgeTraversal outE(String... labels) {
			pipeline.outE(labels);
			return edgeTraversal;
		}

		@Override
		public FramedEdgeTraversal inE(int branchFactor, String... labels) {
			pipeline.inE(branchFactor, labels);
			return edgeTraversal;
		}

		@Override
		public FramedEdgeTraversal inE(String... labels) {
			pipeline.inE(labels);
			return edgeTraversal;
		}

		@Override
		public FramedEdgeTraversal bothE(int branchFactor, String... labels) {
			pipeline.bothE(branchFactor, labels);
			return edgeTraversal;
		}

		@Override
		public FramedEdgeTraversal bothE(String... labels) {
			pipeline.bothE(labels);
			return edgeTraversal;
		}

		@Override
		public FramedVertexTraversal interval(String key, Comparable startValue, Comparable endValue) {
			return (FramedVertexTraversal)super.interval(key, startValue, endValue);
		}
		
		
		@Override
		public <T extends FramedVertex> T next(Class<T> kind) {
			return graph.frameElement((Element) pipeline.next(), kind);
		}

		@Override
		public <T extends FramedVertex> List<T> next(int amount, final Class<T> kind) {
			return Lists.transform(pipeline.next(amount), new Function() {

				public Object apply(Object e) {
					return graph.frameElement((Element) e, kind);
				}
			});
		}

		@Override
		public <T extends FramedVertex> Iterator<T> frame(final Class<T> kind) {
			return Iterators.transform(pipeline, new Function() {

				public Object apply(Object e) {
					return graph.frameElement((Element) e, kind);
				}
			});
		}

		@Override
		public <T extends FramedVertex> List<T> toList(final Class<T> kind) {
			return Lists.transform(pipeline.toList(), new Function() {

				public Object apply(Object e) {
					return graph.frameElement((Element) e, kind);
				}
			});
		}

		

	

		@Override
		public FramedVertexTraversal asVertices() {
			return vertexTraversal;
		}

		@Override
		public FramedEdgeTraversal asEdges() {
			return edgeTraversal;
		}

		@Override
		protected FramedGraph graph() {
			return graph;
		}

		@Override
		protected GremlinPipeline pipeline() {
			return pipeline;
		}



		
		@Override
		public FramedVertexTraversal has(String key) {
			return (FramedVertexTraversal)super.has(key);
		}

		@Override
		public FramedVertexTraversal has(String key, Object value) {
			return (FramedVertexTraversal)super.has(key, value);
		}

		@Override
		public FramedVertexTraversal has(String key, Predicate predicate, Object value) {
			return (FramedVertexTraversal)super.has(key, predicate, value);
		}

		@Override
		public FramedVertexTraversal has(String key, com.tinkerpop.gremlin.Tokens.T compareToken, Object value) {
			return (FramedVertexTraversal)super.has(key, compareToken, value);
		}

		@Override
		public FramedVertexTraversal hasNot(String key) {
			return (FramedVertexTraversal) super.hasNot(key);
		}

		@Override
		public FramedVertexTraversal hasNot(String key, Object value) {
			return (FramedVertexTraversal) super.hasNot(key, value);
		}

		@Override
		public FramedVertexTraversal as(String name) {
			return (FramedVertexTraversal) super.as(name);
		}
		@Override
		public FramedVertexTraversal identity() {
			return (FramedVertexTraversal)super.identity();
		}

		@Override
		public FramedVertexTraversal linkOut(String label, String namedStep) {
			pipeline().linkOut(label, namedStep);
			return this;
		}

		@Override
		public FramedVertexTraversal linkIn(String label, String namedStep) {
			pipeline().linkIn(label, namedStep);
			return this;
		}

		@Override
		public FramedVertexTraversal linkBoth(String label, String namedStep) {
			pipeline().linkBoth(label, namedStep);
			return this;
		}

		@Override
		public FramedVertexTraversal linkOut(String label, Vertex other) {
			pipeline().linkOut(label, other);
			return this;
		}

		@Override
		public FramedVertexTraversal linkIn(String label, Vertex other) {
			pipeline().linkIn(label, other);
			return this;
		}

		@Override
		public FramedVertexTraversal linkBoth(String label, Vertex other) {
			pipeline().linkBoth(label, other);
			return this;
		}

		@Override
		public FramedVertexTraversal linkOut(String label, FramedVertex other) {
			pipeline().linkOut(label, other.element());
			return this;
		}
		
		@Override
		public FramedVertexTraversal linkIn(String label, FramedVertex other) {
			pipeline().linkIn(label, other.element());
			return this;
		}
		
		@Override
		public FramedVertexTraversal linkBoth(String label, FramedVertex other) {
			pipeline().linkBoth(label, other.element());
			return this;
		}
		

	
	}

}

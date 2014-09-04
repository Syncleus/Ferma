package org.jglue.totorom;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
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
import com.tinkerpop.pipes.transform.TransformPipe.Order;
import com.tinkerpop.pipes.util.structures.Table;
import com.tinkerpop.pipes.util.structures.Tree;

/**
 * The implementation of 
 * @author bryn
 *
 */
@SuppressWarnings("rawtypes")
class FramedTraversalImpl extends FramedTraversalBase implements FramedTraversal {

	private FramedGraph graph;
	private GremlinPipeline pipeline;

	private FramedEdgeTraversal edgeTraversal = new FramedEdgeTraversalImpl() {

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
		protected FramedTraversal asTraversal() {
			return FramedTraversalImpl.this;
		}
	};
	private FramedVertexTraversal vertexTraversal = new FramedVertexTraversalImpl(){
		@Override
		public FramedVertexTraversal asVertices() {
			return vertexTraversal;
		}

		@Override
		public FramedEdgeTraversal asEdges() {
			return edgeTraversal;
		}
		
		@Override
		protected FramedTraversal asTraversal() {
			return FramedTraversalImpl.this;
		}
		

		@Override
		protected FramedGraph graph() {
			return graph;
		}

		@Override
		protected GremlinPipeline pipeline() {
			return pipeline;
		}
		
	};

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

	protected FramedTraversalImpl(FramedGraph graph, FramedElement starts) {
		this(graph, new GremlinPipeline<>(starts.element()));
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
	protected FramedTraversal asTraversal() {
		return this;
	}

	@Override
	protected GremlinPipeline pipeline() {

		return pipeline;
	}

	@Override
	protected FramedGraph graph() {

		return graph;
	}






}

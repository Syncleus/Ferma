package org.jglue.totorom;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

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
 * 
 * @author bryn
 *
 */
@SuppressWarnings("rawtypes")
class TraversalImpl extends TraversalBase implements Traversal {

	private FramedGraph graph;
	private GremlinPipeline pipeline;
	private Deque<MarkId> marks = new ArrayDeque<>();
	private int markId = 0;

	
	public MarkId pushMark(Traversal<?,?,?,?> traversal) {
		MarkId mark = new MarkId();
		mark.id = "traversalMark" + markId++;
		mark.traversal = traversal;
		marks.push(mark);
		
		return mark;
	}
	
	@Override
	public MarkId pushMark() {
	
		return pushMark(this);
	}
	
	@Override
	public MarkId popMark() {
		return marks.pop();
	}
	
	private EdgeTraversal edgeTraversal = new EdgeTraversalImpl() {

		@Override
		public VertexTraversal castToVertices() {
			return vertexTraversal;
		}

		@Override
		public EdgeTraversal castToEdges() {
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
		protected Traversal castToTraversal() {
			return TraversalImpl.this;
		}

		public TraversalBase.MarkId pushMark() {
			return TraversalImpl.this.pushMark(this);
		};
		
		public TraversalBase.MarkId popMark() {
			return TraversalImpl.this.popMark();
		};
	};
	private VertexTraversal vertexTraversal = new VertexTraversalImpl() {
		@Override
		public VertexTraversal castToVertices() {
			return vertexTraversal;
		}

		@Override
		public EdgeTraversal castToEdges() {
			return edgeTraversal;
		}

		@Override
		protected Traversal castToTraversal() {
			return TraversalImpl.this;
		}

		@Override
		protected FramedGraph graph() {
			return graph;
		}

		@Override
		protected GremlinPipeline pipeline() {
			return pipeline;
		}

		public TraversalBase.MarkId pushMark() {
			return TraversalImpl.this.pushMark(this);
		};
		
		public TraversalBase.MarkId popMark() {
			return TraversalImpl.this.popMark();
		};
	};

	private TraversalImpl(FramedGraph graph, GremlinPipeline pipeline) {
		this.graph = graph;
		this.pipeline = pipeline;

	}

	protected TraversalImpl(FramedGraph graph, Graph delegate) {
		this(graph, new GremlinPipeline<>(delegate));
	}

	protected TraversalImpl(FramedGraph graph, Iterator starts) {
		this(graph, new GremlinPipeline<>(starts));
	}

	protected TraversalImpl(FramedGraph graph, FramedElement starts) {
		this(graph, new GremlinPipeline<>(starts.element()));
	}

	

	/**
	 * @return Cast the traversal to a {@link VertexTraversal}
	 */
	public VertexTraversal castToVertices() {
		return vertexTraversal;
	}

	/**
	 * @return Cast the traversal to a {@link EdgeTraversal}
	 */
	public EdgeTraversal castToEdges() {
		return edgeTraversal;
	}

	@Override
	protected Traversal castToTraversal() {
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

/******************************************************************************
 *                                                                             *
 *  Copyright: (c) Syncleus, Inc.                                              *
 *                                                                             *
 *  You may redistribute and modify this source code under the terms and       *
 *  conditions of the Open Source Community License - Type C version 1.0       *
 *  or any later version as published by Syncleus, Inc. at www.syncleus.com.   *
 *  There should be a copy of the license included with this file. If a copy   *
 *  of the license is not included you are granted no right to distribute or   *
 *  otherwise use this file except through a legal and valid license. You      *
 *  should also contact Syncleus, Inc. at the information below if you cannot  *
 *  find a license:                                                            *
 *                                                                             *
 *  Syncleus, Inc.                                                             *
 *  2604 South 12th Street                                                     *
 *  Philadelphia, PA 19148                                                     *
 *                                                                             *
 ******************************************************************************/

/*
 * Part or all of this source file was forked from a third-party project, the details of which are listed below.
 *
 * Source Project: Totorom
 * Source URL: https://github.com/BrynCooke/totorom
 * Source License: Apache Public License v2.0
 * When: November, 20th 2014
 */
package com.syncleus.ferma;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import com.syncleus.ferma.pipes.GremlinPipeline;

import com.tinkerpop.blueprints.Graph;

@SuppressWarnings("rawtypes")
class TraversalImpl extends TraversalBase implements Traversal {

	private FramedGraph graph;
	private com.tinkerpop.gremlin.java.GremlinPipeline pipeline;
	private Deque<MarkId> marks = new ArrayDeque<>();
	private int markId = 0;

	public MarkId pushMark(Traversal<?, ?, ?, ?> traversal) {
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

	private SplitTraversal splitTraversal = new SplitTraversal() {

		@Override
		public Traversal exhaustMerge() {
			pipeline().exhaustMerge();
			return castToTraversal();
		}

		@Override
		public Traversal fairMerge() {
			pipeline().fairMerge();
			return castToTraversal();
		}
	};

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
		protected com.tinkerpop.gremlin.java.GremlinPipeline pipeline() {
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

		public SplitTraversal castToSplit() {
			return splitTraversal;
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
		protected com.tinkerpop.gremlin.java.GremlinPipeline pipeline() {
			return pipeline;
		}

		public TraversalBase.MarkId pushMark() {
			return TraversalImpl.this.pushMark(this);
		};

		public TraversalBase.MarkId popMark() {
			return TraversalImpl.this.popMark();
		};

		public SplitTraversal castToSplit() {
			return splitTraversal;
		};
	};

	private TraversalImpl(FramedGraph graph, com.tinkerpop.gremlin.java.GremlinPipeline pipeline) {
		this.graph = graph;
		this.pipeline = pipeline;

	}

	protected TraversalImpl(FramedGraph graph, Graph delegate) {
		this(graph, new GremlinPipeline<>(delegate));
	}

	protected TraversalImpl(FramedGraph graph, Iterator starts) {
		this(graph, new GremlinPipeline<>(starts));
	}

	protected TraversalImpl(FramedGraph graph, AbstractElementFrame starts) {
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
	protected com.tinkerpop.gremlin.java.GremlinPipeline pipeline() {

		return pipeline;
	}

	@Override
	protected FramedGraph graph() {

		return graph;
	}

	@Override
	protected SplitTraversal castToSplit() {
		return splitTraversal;
	}

}

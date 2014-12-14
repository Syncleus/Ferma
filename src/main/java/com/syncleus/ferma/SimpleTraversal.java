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

import com.syncleus.ferma.pipes.FermaGremlinPipeline;
import com.tinkerpop.blueprints.Graph;

/**
 * A simple element traversal.
 *
 * @param <T> The type of the objects coming off the pipe.
 * @param <C> The cap of the current pipe.
 * @param <S> The SideEffect of the current pipe.
 * @param <M> The current marked type for the current pipe.
 */
class SimpleTraversal<T, C, S, M> extends AbstractTraversal<T, C, S, M> {

    private final FramedGraph graph;
    private final FermaGremlinPipeline pipeline;
    private final Deque<MarkId> marks = new ArrayDeque<>();
    private int markId = 0;

    protected SimpleTraversal(final FramedGraph graph, final Graph delegate) {
        this(graph, new FermaGremlinPipeline<>(delegate));
    }

    protected SimpleTraversal(final FramedGraph graph, final Iterator starts) {
        this(graph, new FermaGremlinPipeline<>(starts));
    }

    protected SimpleTraversal(final FramedGraph graph, final ElementFrame starts) {
        this(graph, new FermaGremlinPipeline<>(starts.getElement()));
    }

    public MarkId pushMark(final Traversal<?, ?, ?, ?> traversal) {
        final MarkId mark = new MarkId();
        mark.id = "traversalMark" + markId++;
        mark.traversal = traversal;
        marks.push(mark);

        return mark;
    }

    @Override
    public <W, X, Y, Z> MarkId<W, X, Y, Z> pushMark() {

        return pushMark(this);
    }

    @Override
    public <W, X, Y, Z> MarkId<W, X, Y, Z> popMark() {
        return marks.pop();
    }

    private SimpleTraversal(final FramedGraph graph, final FermaGremlinPipeline pipeline) {
        this.graph = graph;
        this.pipeline = pipeline;

    }

    /**
     * @return Cast the traversal to a {@link VertexTraversal}
     */
    @Override
    public VertexTraversal<C, S, M> castToVertices() {
        return vertexTraversal;
    }

    /**
     * @return Cast the traversal to a {@link EdgeTraversal}
     */
    @Override
    public EdgeTraversal<C, S, M> castToEdges() {
        return edgeTraversal;
    }

    @Override
    protected <W, X, Y, Z> Traversal<W, X, Y, Z> castToTraversal() {
        return (Traversal<W, X, Y, Z>) this;
    }

    @Override
    protected FermaGremlinPipeline pipeline() {

        return pipeline;
    }

    @Override
    protected FramedGraph graph() {

        return graph;
    }

    @Override
    protected <N> SplitTraversal<N> castToSplit() {
        return splitTraversal;
    }

    private final SplitTraversal splitTraversal = new SplitTraversal() {

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

    private final EdgeTraversal edgeTraversal = new AbstractEdgeTraversal() {

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
        protected FermaGremlinPipeline pipeline() {
            return pipeline;
        }

        @Override
        protected Traversal castToTraversal() {
            return SimpleTraversal.this;
        }

        @Override
        public AbstractTraversal.MarkId pushMark() {
            return SimpleTraversal.this.pushMark(this);
        }

        @Override
        public AbstractTraversal.MarkId popMark() {
            return SimpleTraversal.this.popMark();
        }

        @Override
        public SplitTraversal castToSplit() {
            return splitTraversal;
        }
    };

    private final VertexTraversal vertexTraversal = new AbstractVertexTraversal() {
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
            return SimpleTraversal.this;
        }

        @Override
        protected FramedGraph graph() {
            return graph;
        }

        @Override
        protected FermaGremlinPipeline pipeline() {
            return pipeline;
        }

        @Override
        public AbstractTraversal.MarkId pushMark() {
            return SimpleTraversal.this.pushMark(this);
        }

        @Override
        public AbstractTraversal.MarkId popMark() {
            return SimpleTraversal.this.popMark();
        }

        @Override
        public SplitTraversal castToSplit() {
            return splitTraversal;
        }
    };
}

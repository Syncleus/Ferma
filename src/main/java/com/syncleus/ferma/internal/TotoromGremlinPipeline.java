package com.syncleus.ferma.internal;

import java.util.List;

import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.util.FluentUtility;

public class TotoromGremlinPipeline<S, E> extends GremlinPipeline<S, E> {

	public TotoromGremlinPipeline() {
		super();
	}

	public TotoromGremlinPipeline(Object starts, boolean doQueryOptimization) {
		super(starts, doQueryOptimization);
	}

	public TotoromGremlinPipeline(Object starts) {
		super(starts);
	}

	@Override
	public GremlinPipeline<S, List> path(PipeFunction... pathFunctions) {
	
		return this.add(new PathPipe<Object>(FluentUtility.prepareFunctions(this.asMap, pathFunctions)));
	}
}

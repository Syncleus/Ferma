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

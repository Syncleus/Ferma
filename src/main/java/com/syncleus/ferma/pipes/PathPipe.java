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
package com.syncleus.ferma.pipes;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.syncleus.ferma.Path;

import com.tinkerpop.pipes.AbstractPipe;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.transform.TransformPipe;

public class PathPipe<S> extends AbstractPipe<S, List> implements TransformPipe<S, List> {

    private final PipeFunction[] pathFunctions;

    public PathPipe(final PipeFunction... pathFunctions) {
        if (pathFunctions.length == 0)
            this.pathFunctions = null;
        else
            this.pathFunctions = pathFunctions;
    }

    @Override
    public void setStarts(final Iterator<S> starts) {
        super.setStarts(starts);
        this.enablePath(true);
    }

    @Override
    public List processNextStart() {
        if (this.starts instanceof Pipe) {
            this.starts.next();
            final List path = ((Pipe) this.starts).getCurrentPath();
            if (null == this.pathFunctions)
                return path;
            else {
                final List closedPath = new Path();
                int nextFunction = 0;
                for (final Object object : path) {
                    closedPath.add(this.pathFunctions[nextFunction].compute(object));
                    nextFunction = (nextFunction + 1) % this.pathFunctions.length;
                }
                return closedPath;
            }
        }
        else
            throw new NoSuchElementException("The start of this pipe was not a pipe");
    }

}

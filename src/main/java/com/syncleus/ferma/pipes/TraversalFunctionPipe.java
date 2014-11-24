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

import com.syncleus.ferma.TraversalFunction;

import com.tinkerpop.pipes.Pipe;

public class TraversalFunctionPipe implements TraversalFunction {

	private TraversalFunction delegate;

	public TraversalFunctionPipe(TraversalFunction delegate) {
		this.delegate = delegate;
	}

	@Override
	public Object compute(Object argument) {
		Object result = delegate.compute(argument);
		if(result instanceof Iterator) {
			final Iterator i = (Iterator) result; 
			return new Pipe() {

				@Override
				public boolean hasNext() {
					return i.hasNext();
				}

				@Override
				public Object next() {
					return i.next();
				}

				@Override
				public Iterator iterator() {
					return null;
				}

				@Override
				public void setStarts(Iterator starts) {
					
				}

				@Override
				public void setStarts(Iterable starts) {
					
				}

				@Override
				public List getCurrentPath() {
					return null;
				}

				@Override
				public void enablePath(boolean enable) {
					
				}

				@Override
				public void reset() {
					
				}
			};
		}
		return result;
	}

}

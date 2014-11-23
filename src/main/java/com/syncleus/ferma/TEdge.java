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
package com.syncleus.ferma;

import java.util.Set;

/**
 * A framed edge for use when you don't want to create a new frame class.
 * Typically used in traversals.
 * 
 * @author bryn
 *
 */
public final class TEdge extends FramedEdge {
	@Override
	public VertexTraversal<?, ?, ?> bothV() {
		return super.bothV();
	}

	@Override
	public VertexTraversal<?, ?, ?> inV() {
		return super.inV();
	}

	@Override
	public VertexTraversal<?, ?, ?> outV() {
		return super.outV();
	}

	@Override
	public EdgeTraversal<?, ?, ?> traversal() {
		return super.traversal();
	}

	@Override
	public VertexTraversal<?, ?, ?> v(Object... ids) {
		return super.v(ids);
	}

	@Override
	public EdgeTraversal<?, ?, ?> E() {
		return super.E();
	}

	@Override
	public VertexTraversal<?, ?, ?> V() {
		return super.V();
	}

	@Override
	public void remove() {
		super.remove();
	}

	@Override
	public String getLabel() {

		return super.getLabel();
	}

	@Override
	public <N> N getId() {

		return super.getId();
	}

	@Override
	public <T> T getProperty(String name) {

		return super.getProperty(name);
	}

	@Override
	public <T> T getProperty(String name, Class<T> clazz) {
		return super.getProperty(name, clazz);
	}

	@Override
	public Set<String> getPropertyKeys() {

		return super.getPropertyKeys();
	}

	@Override
	public void setProperty(String name, Object value) {

		super.setProperty(name, value);
	}

	@Override
	public <N> N getId(Class<N> clazz) {

		return (N) super.getId(clazz);
	}

	public <T extends FramedEdge> T reframe(Class<T> kind) {
		return graph().frameElement(element(), kind);
	}
	
}

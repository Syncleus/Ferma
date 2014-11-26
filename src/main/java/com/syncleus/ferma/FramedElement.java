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

import java.util.Set;

import com.tinkerpop.blueprints.Element;

/**
 * The base of all framed elements.
 */
public abstract class FramedElement {

	private Element element;
	private FramedGraph graph;

	protected void init(FramedGraph graph, Element element) {
		this.graph = graph;
		this.element = element;
	}

	protected void init() {

	}

	/**
	 * @return The id of this element.
	 */
	public <N> N getId() {
		return (N) element.getId();
	}

	/**
	 * @return The property keys of this element.
	 */
	public Set<String> getPropertyKeys() {
		return element.getPropertyKeys();
	}

	/**
	 * Remove this element from the graph.
	 */
	public void remove() {
		element.remove();
	}

	/**
	 * @return The underlying element.
	 */
	public Element element() {
		return element;
	}

	/**
	 * @return The underlying graph.
	 */
	public FramedGraph graph() {
		return graph;
	}

	/**
	 * Return a property value.
	 * 
	 * @param name
	 *            The name of the property.
	 * @return the value of the property or null if none was present.
	 */
	public <T> T getProperty(String name) {
		return element.getProperty(name);
	}

	/**
	 * Return a property value.
	 * 
	 * @param name
	 *            The name of the property.
	 * @param type
	 *            The type of the property.
	 * 
	 * @return the value of the property or null if none was present.
	 */
	public <T> T getProperty(String name, Class<T> type) {
		if (type.isEnum()) {
			return (T) Enum.valueOf((Class<Enum>) type, (String) element.getProperty(name));
		}

		return element.getProperty(name);
	}

	/**
	 * Set a property value.
	 * 
	 * @param name
	 *            The name of the property.
	 * @param value
	 *            The value of the property.
	 */
	public void setProperty(String name, Object value) {
		if (value == null) {
			element.removeProperty(name);
		} else {
			if (value instanceof Enum) {
				element.setProperty(name, value.toString());
			} else {
				element.setProperty(name, value);
			}
		}
	}

	/**
	 * Query over all vertices in the graph.
	 * 
	 * @return The query.
	 */
	public VertexTraversal<?, ?, ?> V() {
		return graph.v();
	}

	/**
	 * Query over all edges in the graph.
	 * 
	 * @return The query.
	 */
	public EdgeTraversal<?, ?, ?> E() {
		return graph.e();
	}

	/**
	 * Query over a list of vertices in the graph.
	 * 
	 * @param ids
	 *            The ids of the vertices.
	 * @return The query.
	 */
	public VertexTraversal<?, ?, ?> v(final Object... ids) {
		return graph.v(ids);
	}

	/**
	 * Query over a list of edges in the graph.
	 * 
	 * @param ids
	 *            The ids of the edges.
	 * @return The query.
	 */
	public EdgeTraversal<?, ?, ?> e(final Object... ids) {
		return graph.e(ids);
	}

	@Override
	public int hashCode() {
		return element.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FramedElement other = (FramedElement) obj;
		if (element == null) {
			if (other.element != null)
				return false;
		} else if (!element.equals(other.element))
			return false;
		return true;
	}

	protected <N> N getId(Class<N> clazz) {

		return (N) getId();
	}

	@Override
	public String toString() {

		return element().toString();
	}
	
	
}

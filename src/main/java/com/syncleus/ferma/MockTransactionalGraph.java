/******************************************************************************
 *                                                                            *
 * Copyright: (c) Syncleus, Inc.                                              *
 *                                                                            *
 * You may redistribute and modify this source code under the terms and       *
 * conditions of the Open Source Community License - Type C version 1.0       *
 * or any later version as published by Syncleus, Inc. at www.syncleus.com.   *
 * There should be a copy of the license included with this file. If a copy   *
 * of the license is not included you are granted no right to distribute or   *
 * otherwise use this file except through a legal and valid license. You      *
 * should also contact Syncleus, Inc. at the information below if you cannot  *
 * find a license:                                                            *
 *                                                                            *
 * Syncleus, Inc.                                                             *
 * 2604 South 12th Street                                                     *
 * Philadelphia, PA 19148                                                     *
 *                                                                            *
 ******************************************************************************/
package com.syncleus.ferma;

import com.tinkerpop.blueprints.*;

public class MockTransactionalGraph implements TransactionalGraph {
  private final Graph delegate;

  public MockTransactionalGraph(final Graph delegate) {
    this.delegate = delegate;
  }

  @Override
  public Features getFeatures() {
    return delegate.getFeatures();
  }

  @Override
  public Vertex addVertex(final Object id) {
    return delegate.addVertex(id);
  }

  @Override
  public Vertex getVertex(final Object id) {
    return delegate.getVertex(id);
  }

  @Override
  public void removeVertex(final Vertex vertex) {
    delegate.removeVertex(vertex);
  }

  @Override
  public Iterable<Vertex> getVertices() {
    return delegate.getVertices();
  }

  @Override
  public Iterable<Vertex> getVertices(final String key, final Object value) {
    return delegate.getVertices(key, value);
  }

  @Override
  public Edge addEdge(final Object id, final Vertex outVertex, final Vertex inVertex, final String label) {
    return delegate.addEdge(id, outVertex, inVertex, label);
  }

  @Override
  public Edge getEdge(final Object id) {
    return delegate.getEdge(id);
  }

  @Override
  public void removeEdge(final Edge edge) {
    delegate.removeEdge(edge);
  }

  @Override
  public Iterable<Edge> getEdges() {
    return delegate.getEdges();
  }

  @Override
  public Iterable<Edge> getEdges(final String key, final Object value) {
    return delegate.getEdges(key, value);
  }

  @Override
  public GraphQuery query() {
    return delegate.query();
  }

  @Override
  public void shutdown() {
    delegate.shutdown();
  }

  @Override
  public void stopTransaction(final Conclusion conclusion) {
  }

  @Override
  public void commit() {

  }

  @Override
  public void rollback() {

  }
}

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

 import com.google.gson.JsonObject;
 import com.tinkerpop.blueprints.Edge;

 public interface EdgeFrame extends ElementFrame {
  @Override
  public Edge element();

  /**
   * @return The label associated with this edge
   */
  public String getLabel();

  /**
   * @return The in vertex for this edge.
   */
  public VertexTraversal<?, ?, ?> inV();

  /**
   * @return The out vertex of this edge.
   */
  public VertexTraversal<?, ?, ?> outV();

  /**
   * @return The vertices for this edge.
   */
  public VertexTraversal<?, ?, ?> bothV();

  /**
   * Shortcut to get frameTraversal of current element
   *
   * @return
   */
  public EdgeTraversal<?, ?, ?> traversal();

  public JsonObject toJson();

  /**
   * Reframe this element as a different type of frame.
   *
   * @param kind The new kind of frame.
   * @return The new frame
   */
  public <T extends AbstractEdgeFrame> T reframe(Class<T> kind);

  /**
   * Reframe this element as a different type of frame.
   *
   * This will bypass the default type resolution and use the untyped resolver
   * instead. This method is useful for speeding up a look up when type resolution
   * isn't required.
   *
   * @param kind The new kind of frame.
   * @return The new frame
   */
  public <T extends AbstractEdgeFrame> T reframeExplicit(Class<T> kind);
}

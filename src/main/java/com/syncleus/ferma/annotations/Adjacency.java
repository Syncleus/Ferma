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
package com.syncleus.ferma.annotations;

import com.tinkerpop.blueprints.Direction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Adjacencies annotate getters and adders to represent a Vertex adjacent to a Vertex. This annotation extends the
 * TinkerPop built-in Adjacency annotation. It allows type arguments to be passed into the annotated method. This
 * ensures the returned type is of the specified type in the argument. The following method signatures are valid.
 *
 * T add*(Class&lt;T&gt; type)
 * T get*(Class&lt;T&gt; type)
 *
 * When annotating a get* class it ensures it only returns nodes of the specified type (including sub-classes). Any
 * Nodes which are not of this type will not be returned.
 *
 * @since 0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Adjacency {
    /**
     * The label of the edges making the adjacency between the vertices.
     *
     * @return the edge label
     * @since 0.1
     */
    String label();

    /**
     * The edge direction of the adjacency.
     *
     * @return the direction of the edges composing the adjacency
     * @since 0.1
     */
    Direction direction() default Direction.OUT;
}

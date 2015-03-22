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
package com.syncleus.ferma.framefactories.annotation;

import net.bytebuddy.dynamic.DynamicType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Allows handling of method on frames. Only the first method handler found is called.
 * Instances of this class should be threadsafe.
 */
public interface MethodHandler {

    /**
     * @return The annotation type that this handler responds to.
     */
    Class<? extends Annotation> getAnnotationType();

    /**
     * @param <E> The loaded type of the Byte Buddy Builder
     * @param method The method being called on the frame.
     * @param annotation The annotation
     * @param builder ByteBuddy Builder class to expand.
     * @return A return value for the method.
     */
    <E> DynamicType.Builder<E> processMethod(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation);
}

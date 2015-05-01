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

import com.syncleus.ferma.EdgeFrame;
import com.syncleus.ferma.annotations.InVertex;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.instrumentation.MethodDelegation;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.Origin;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.RuntimeType;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.This;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * A method handler that implemented the InVertex Annotation.
 *
 * @since 2.0.0
 */
public class InVertexMethodHandler implements MethodHandler {

    @Override
    public Class<InVertex> getAnnotationType() {
        return InVertex.class;
    }

    @Override
    public <E> DynamicType.Builder<E> processMethod(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        final java.lang.reflect.Parameter[] arguments = method.getParameters();

        if (ReflectionUtility.isGetMethod(method))
            if (arguments == null || arguments.length == 0)
                return this.getNode(builder, method, annotation);
            else
                throw new IllegalStateException(method.getName() + " was annotated with @InVertex but had arguments.");
        else
            throw new IllegalStateException(method.getName() + " was annotated with @InVertex but did not begin with: get");
    }

    private <E> DynamicType.Builder<E> getNode(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        return builder.method(ElementMatchers.is(method)).intercept(MethodDelegation.to(getVertexInterceptor.class));
    }

    public static final class getVertexInterceptor {

        @RuntimeType
        public static Object getVertex(@This final EdgeFrame thiz, @Origin final Method method) {
            return thiz.inV().next(method.getReturnType());
        }
    }
}

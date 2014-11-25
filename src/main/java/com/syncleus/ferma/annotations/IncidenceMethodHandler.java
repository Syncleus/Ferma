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

import com.syncleus.ferma.FramedEdge;
import com.syncleus.ferma.FramedVertex;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.gremlin.Tokens;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.instrumentation.MethodDelegation;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.Argument;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.Origin;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.RuntimeType;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.This;
import net.bytebuddy.instrumentation.method.matcher.MethodMatchers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * A TinkerPop method handler that implemented the Incidence Annotation.
 *
 * @since 0.1
 */
public class IncidenceMethodHandler implements MethodHandler {
    @Override
    public Class<Incidence> getAnnotationType() {
        return Incidence.class;
    }

    @Override
    public <E> DynamicType.Builder<E> processMethod(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        final java.lang.reflect.Parameter[] arguments = method.getParameters();

        if( ReflectionUtility.isGetMethod(method) ) {
            if( arguments == null  || arguments.length == 0 )
                if (Iterable.class.isAssignableFrom(method.getReturnType()))
                    return this.getEdgesDefault(builder, method, annotation);
                else
                    return this.getEdgeDefault(builder, method, annotation);
            else if( arguments.length == 1 ) {
                if (!(Class.class.isAssignableFrom(arguments[0].getType())))
                    throw new IllegalStateException(method.getName() + " was annotated with @Incidence, had a single argument, but that argument was not of the type Class");

                if (Iterable.class.isAssignableFrom(method.getReturnType()))
                    return this.getEdgesByType(builder, method, annotation);

                return this.getEdgeByType(builder, method, annotation);
            }
            else
                throw new IllegalStateException(method.getName() + " was annotated with @Incidence but had more than 1 arguments.");
        }
        else if( ReflectionUtility.isRemoveMethod(method) ) {
            if( arguments == null  || arguments.length == 0 )
                throw new IllegalStateException(method.getName() + " was annotated with @Incidence but had no arguments.");
            else if( arguments.length == 1 ) {
                return this.removeEdge(builder, method, annotation);
            }
            else
                throw new IllegalStateException(method.getName() + " was annotated with @Incidence but had more than 1 arguments.");
        }
        else
            throw new IllegalStateException(method.getName() + " was annotated with @Incidence but did not begin with: get");
    }

    private <E> DynamicType.Builder<E> getEdgesDefault(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        return builder.method(MethodMatchers.is(method)).intercept(MethodDelegation.to(GetEdgesDefaultInterceptor.class));
    }

    private <E> DynamicType.Builder<E> getEdgesByType(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        return builder.method(MethodMatchers.is(method)).intercept(MethodDelegation.to(GetEdgesByTypeInterceptor.class));
    }

    private <E> DynamicType.Builder<E> getEdgeDefault(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        return builder.method(MethodMatchers.is(method)).intercept(MethodDelegation.to(GetEdgeDefaultInterceptor.class));
    }

    private <E> DynamicType.Builder<E> getEdgeByType(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        return builder.method(MethodMatchers.is(method)).intercept(MethodDelegation.to(GetEdgeByTypeInterceptor.class));
    }

    private <E> DynamicType.Builder<E> removeEdge(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        return builder.method(MethodMatchers.is(method)).intercept(MethodDelegation.to(RemoveEdgeInterceptor.class));
    }

    public static final class GetEdgesDefaultInterceptor {
        @RuntimeType
        public static Iterable getEdges(@This final FramedVertex thiz, @Origin final Method method) {
            final Incidence annotation = method.getAnnotation(Incidence.class);
            final Direction direction = annotation.direction();
            final String label = annotation.label();

            switch (direction) {
                case BOTH:
                    return thiz.bothE(label).frame(FramedVertex.class);
                case IN:
                    return thiz.inE(label).frame(FramedVertex.class);
                //Assume out direction
                default:
                    return thiz.outE(label).frame(FramedVertex.class);
            }

        }
    }

    public static final class GetEdgesByTypeInterceptor {
        @RuntimeType
        public static Iterable getEdges(@This final FramedVertex thiz, @Origin final Method method, @RuntimeType @Argument(0) final Class type) {
            final Incidence annotation = method.getAnnotation(Incidence.class);
            final Direction direction = annotation.direction();
            final String label = annotation.label();
            final String typeName = type.getName();
            final Set<String> allAllowedValues = ((CachedHierarchy)thiz).getHierarchy().get(typeName);

            switch (direction) {
                case BOTH:
                    return thiz.bothE(label).has("implementation_type", Tokens.T.in, allAllowedValues).frame(type);
                case IN:
                    return thiz.inE(label).has("implementation_type", Tokens.T.in, allAllowedValues).frame(type);
                //Assume out direction
                default:
                    return thiz.outE(label).has("implementation_type", Tokens.T.in, allAllowedValues).frame(type);
            }

        }
    }

    public static final class GetEdgeDefaultInterceptor {
        @RuntimeType
        public static Object getEdges(@This final FramedVertex thiz, @Origin final Method method) {
            final Incidence annotation = method.getAnnotation(Incidence.class);
            final Direction direction = annotation.direction();
            final String label = annotation.label();

            switch (direction) {
                case BOTH:
                    return thiz.bothE(label).next(FramedVertex.class);
                case IN:
                    return thiz.inE(label).next(FramedVertex.class);
                //Assume out direction
                default:
                    return thiz.outE(label).next(FramedVertex.class);
            }

        }
    }

    public static final class GetEdgeByTypeInterceptor {
        @RuntimeType
        public static Object getEdge(@This final FramedVertex thiz, @Origin final Method method, @RuntimeType @Argument(0) final Class type) {
            final Incidence annotation = method.getAnnotation(Incidence.class);
            final Direction direction = annotation.direction();
            final String label = annotation.label();
            final String typeName = type.getName();
            final Set<String> allAllowedValues = ((CachedHierarchy)thiz).getHierarchy().get(typeName);

            switch (direction) {
                case BOTH:
                    return thiz.bothE(label).has("implementation_type", Tokens.T.in, allAllowedValues).next(type);
                case IN:
                    return thiz.inE(label).has("implementation_type", Tokens.T.in, allAllowedValues).next(type);
                //Assume out direction
                default:
                    return thiz.outE(label).has("implementation_type", Tokens.T.in, allAllowedValues).next(type);
            }

        }
    }

    public static final class RemoveEdgeInterceptor {
        @RuntimeType
        public static void removeEdge(@This final FramedVertex thiz, @Origin final Method method, @RuntimeType @Argument(0) final FramedEdge edge) {
            edge.remove();
        }
    }
}

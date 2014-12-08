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

import com.syncleus.ferma.*;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.gremlin.Tokens;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.instrumentation.MethodDelegation;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.Argument;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.Origin;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.RuntimeType;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.This;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * A method handler that implemented the Adjacency Annotation.
 *
 * @since 2.0.0
 */
public class AdjacencyMethodHandler implements MethodHandler {

    @Override
    public Class<Adjacency> getAnnotationType() {
        return Adjacency.class;
    }

    @Override
    public <E> DynamicType.Builder<E> processMethod(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        final java.lang.reflect.Parameter[] arguments = method.getParameters();

        if (ReflectionUtility.isAddMethod(method))
            if (arguments == null || arguments.length == 0)
                return this.addVertexDefault(builder, method, annotation);
            else if (arguments.length == 1)
                if (Class.class.isAssignableFrom(arguments[0].getType()))
                    return this.addVertexByTypeUntypedEdge(builder, method, annotation);
                else
                    return this.addVertexByObjectUntypedEdge(builder, method, annotation);
            else if (arguments.length == 2) {
                if (!(Class.class.isAssignableFrom(arguments[1].getType())))
                    throw new IllegalStateException(method.getName() + " was annotated with @Adjacency, had two arguments, but the second argument was not of the type Class");

                if (Class.class.isAssignableFrom(arguments[0].getType()))
                    return this.addVertexByTypeTypedEdge(builder, method, annotation);
                else
                    return this.addVertexByObjectTypedEdge(builder, method, annotation);
            }
            else
                throw new IllegalStateException(method.getName() + " was annotated with @Adjacency but had more than 1 arguments.");
        else if (ReflectionUtility.isGetMethod(method))
            if (arguments == null || arguments.length == 0) {
                if (Iterable.class.isAssignableFrom(method.getReturnType()))
                    return this.getVertexesDefault(builder, method, annotation);

                return this.getVertexDefault(builder, method, annotation);
            }
            else if (arguments.length == 1) {
                if (!(Class.class.isAssignableFrom(arguments[0].getType())))
                    throw new IllegalStateException(method.getName() + " was annotated with @Adjacency, had a single argument, but that argument was not of the type Class");

                if (Iterable.class.isAssignableFrom(method.getReturnType()))
                    return this.getVertexesByType(builder, method, annotation);

                return this.getVertexByType(builder, method, annotation);
            }
            else
                throw new IllegalStateException(method.getName() + " was annotated with @Adjacency but had more than 1 arguments.");
        else if (ReflectionUtility.isRemoveMethod(method))
            if (arguments == null || arguments.length == 0)
                throw new IllegalStateException(method.getName() + " was annotated with @Adjacency but had no arguments.");
            else if (arguments.length == 1)
                return this.removeVertex(builder, method, annotation);
            else
                throw new IllegalStateException(method.getName() + " was annotated with @Adjacency but had more than 1 arguments.");
        else if (ReflectionUtility.isSetMethod(method))
            if (arguments == null || arguments.length == 0)
                throw new IllegalStateException(method.getName() + " was annotated with @Adjacency but had no arguments.");
            else if (arguments.length == 1) {
                if (!(Iterable.class.isAssignableFrom(arguments[0].getType())))
                    throw new IllegalStateException(method.getName() + " was annotated with @Adjacency, had a single argument, but that argument was not of the type Class");

                return this.setVertex(builder, method, annotation);
            }
            else
                throw new IllegalStateException(method.getName() + " was annotated with @Adjacency but had more than 1 arguments.");
        else
            throw new IllegalStateException(method.getName() + " was annotated with @Adjacency but did not begin with either of the following keywords: add, get, remove");
    }

    private <E> DynamicType.Builder<E> getVertexesDefault(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        return builder.method(ElementMatchers.is(method)).intercept(MethodDelegation.to(GetVertexesDefaultInterceptor.class));
    }

    private <E> DynamicType.Builder<E> getVertexDefault(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        return builder.method(ElementMatchers.is(method)).intercept(MethodDelegation.to(GetVertexDefaultInterceptor.class));
    }

    private <E> DynamicType.Builder<E> getVertexesByType(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        return builder.method(ElementMatchers.is(method)).intercept(MethodDelegation.to(GetVertexesByTypeInterceptor.class));
    }

    private <E> DynamicType.Builder<E> getVertexByType(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        return builder.method(ElementMatchers.is(method)).intercept(MethodDelegation.to(GetVertexByTypeInterceptor.class));
    }

    private <E> DynamicType.Builder<E> addVertexDefault(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        return builder.method(ElementMatchers.is(method)).intercept(MethodDelegation.to(AddVertexDefaultInterceptor.class));
    }

    private <E> DynamicType.Builder<E> addVertexByTypeUntypedEdge(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        return builder.method(ElementMatchers.is(method)).intercept(MethodDelegation.to(AddVertexByTypeUntypedEdgeInterceptor.class));
    }

    private <E> DynamicType.Builder<E> addVertexByObjectUntypedEdge(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        return builder.method(ElementMatchers.is(method)).intercept(MethodDelegation.to(AddVertexByObjectUntypedEdgeInterceptor.class));
    }

    private <E> DynamicType.Builder<E> addVertexByTypeTypedEdge(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        return builder.method(ElementMatchers.is(method)).intercept(MethodDelegation.to(AddVertexByTypeTypedEdgeInterceptor.class));
    }

    private <E> DynamicType.Builder<E> addVertexByObjectTypedEdge(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        return builder.method(ElementMatchers.is(method)).intercept(MethodDelegation.to(AddVertexByObjectTypedEdgeInterceptor.class));
    }

    private <E> DynamicType.Builder<E> setVertex(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        return builder.method(ElementMatchers.is(method)).intercept(MethodDelegation.to(SetVertexInterceptor.class));
    }

    private <E> DynamicType.Builder<E> removeVertex(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        return builder.method(ElementMatchers.is(method)).intercept(MethodDelegation.to(RemoveVertexInterceptor.class));
    }

    public static final class GetVertexesDefaultInterceptor {

        @RuntimeType
        public static Iterable getVertexes(@This final VertexFrame thiz, @Origin final Method method) {
            final Adjacency annotation = ((CachesReflection) thiz).getReflectionCache().getAnnotation(method, Adjacency.class);
            final Direction direction = annotation.direction();
            final String label = annotation.label();

            return new FramingVertexIterable(thiz.getGraph(), thiz.getElement().getVertices(direction, label), VertexFrame.class);
        }
    }

    public static final class GetVertexesByTypeInterceptor {

        @RuntimeType
        public static Iterable getVertexes(@This final VertexFrame thiz, @Origin final Method method, @RuntimeType @Argument(0) final Class type) {
            final Adjacency annotation = ((CachesReflection) thiz).getReflectionCache().getAnnotation(method, Adjacency.class);
            final Direction direction = annotation.direction();
            final String label = annotation.label();
            final String typeName = type.getName();
            final Set<? extends String> allAllowedValues = ((CachesReflection) thiz).getReflectionCache().getSubTypeNames(typeName);

            switch (direction) {
            case BOTH:
                return thiz.both(label).has(TypeResolver.TYPE_RESOLUTION_KEY, Tokens.T.in, allAllowedValues).frame(type);
            case IN:
                return thiz.in(label).has(TypeResolver.TYPE_RESOLUTION_KEY, Tokens.T.in, allAllowedValues).frame(type);
            //Assume out direction
            default:
                return thiz.out(label).has(TypeResolver.TYPE_RESOLUTION_KEY, Tokens.T.in, allAllowedValues).frame(type);
            }
        }
    }

    public static final class GetVertexDefaultInterceptor {

        @RuntimeType
        public static Object getVertexes(@This final VertexFrame thiz, @Origin final Method method) {
            final Adjacency annotation = ((CachesReflection) thiz).getReflectionCache().getAnnotation(method, Adjacency.class);
            final Direction direction = annotation.direction();
            final String label = annotation.label();

            switch (direction) {
            case BOTH:
                return thiz.both(label).next(VertexFrame.class);
            case IN:
                return thiz.in(label).next(VertexFrame.class);
            //Assume out direction
            default:
                return thiz.out(label).next(VertexFrame.class);
            }
        }
    }

    public static final class GetVertexByTypeInterceptor {

        @RuntimeType
        public static Object getVertex(@This final VertexFrame thiz, @Origin final Method method, @RuntimeType @Argument(0) final Class type) {
            final Adjacency annotation = ((CachesReflection) thiz).getReflectionCache().getAnnotation(method, Adjacency.class);
            final Direction direction = annotation.direction();
            final String label = annotation.label();
            final String typeName = type.getName();
            final Set<? extends String> allAllowedValues = ((CachesReflection) thiz).getReflectionCache().getSubTypeNames(typeName);

            switch (direction) {
            case BOTH:
                return thiz.both(label).has(TypeResolver.TYPE_RESOLUTION_KEY, Tokens.T.in, allAllowedValues).next(type);
            case IN:
                return thiz.in(label).has(TypeResolver.TYPE_RESOLUTION_KEY, Tokens.T.in, allAllowedValues).next(type);
            //Assume out direction
            default:
                return thiz.out(label).has(TypeResolver.TYPE_RESOLUTION_KEY, Tokens.T.in, allAllowedValues).next(type);
            }
        }
    }

    public static final class AddVertexDefaultInterceptor {

        @RuntimeType
        public static Object addVertex(@This final VertexFrame thiz, @Origin final Method method) {
            final VertexFrame newVertex = thiz.getGraph().addFramedVertex();

            final Adjacency annotation = ((CachesReflection) thiz).getReflectionCache().getAnnotation(method, Adjacency.class);
            final Direction direction = annotation.direction();
            final String label = annotation.label();

            switch (direction) {
            case BOTH:
                thiz.getGraph().addFramedEdge(newVertex, thiz, label);
                thiz.getGraph().addFramedEdge(thiz, newVertex, label);
                break;
            case IN:
                thiz.getGraph().addFramedEdge(newVertex, thiz, label);
                break;
            //Assume out direction
            default:
                thiz.getGraph().addFramedEdge(thiz, newVertex, label);
            }

            return newVertex;
        }
    }

    public static final class AddVertexByTypeUntypedEdgeInterceptor {

        @RuntimeType
        public static Object addVertex(@This final VertexFrame thiz, @Origin final Method method, @RuntimeType @Argument(0) final Class vertexType) {
            final Object newNode = thiz.getGraph().addFramedVertex(vertexType);
            assert newNode instanceof VertexFrame;
            final VertexFrame newVertex = ((VertexFrame) newNode);

            final Adjacency annotation = ((CachesReflection) thiz).getReflectionCache().getAnnotation(method, Adjacency.class);
            final Direction direction = annotation.direction();
            final String label = annotation.label();

            assert vertexType.isInstance(newNode);

            switch (direction) {
            case BOTH:
                thiz.getGraph().addFramedEdge(newVertex, thiz, label);
                thiz.getGraph().addFramedEdge(thiz, newVertex, label);
                break;
            case IN:
                thiz.getGraph().addFramedEdge(newVertex, thiz, label);
                break;
            //Assume out direction
            default:
                thiz.getGraph().addFramedEdge(thiz, newVertex, label);
            }

            return newNode;
        }
    }

    public static final class AddVertexByTypeTypedEdgeInterceptor {

        @RuntimeType
        public static Object addVertex(@This final VertexFrame thiz, @Origin final Method method, @RuntimeType @Argument(0) final Class vertexType, @RuntimeType @Argument(1) final Class edgeType) {
            final Object newNode = thiz.getGraph().addFramedVertex(vertexType);
            assert newNode instanceof VertexFrame;
            final VertexFrame newVertex = ((VertexFrame) newNode);

            final Adjacency annotation = ((CachesReflection) thiz).getReflectionCache().getAnnotation(method, Adjacency.class);
            final Direction direction = annotation.direction();
            final String label = annotation.label();

            assert vertexType.isInstance(newNode);

            switch (direction) {
            case BOTH:
                thiz.getGraph().addFramedEdge(newVertex, thiz, label, edgeType);
                thiz.getGraph().addFramedEdge(thiz, newVertex, label, edgeType);
                break;
            case IN:
                thiz.getGraph().addFramedEdge(newVertex, thiz, label, edgeType);
                break;
            //Assume out direction
            default:
                thiz.getGraph().addFramedEdge(thiz, newVertex, label, edgeType);
            }

            return newNode;
        }
    }

    public static final class AddVertexByObjectUntypedEdgeInterceptor {

        @RuntimeType
        public static Object addVertex(@This final VertexFrame thiz, @Origin final Method method, @RuntimeType @Argument(0) final VertexFrame newVertex) {

            final Adjacency annotation = ((CachesReflection) thiz).getReflectionCache().getAnnotation(method, Adjacency.class);
            final Direction direction = annotation.direction();
            final String label = annotation.label();

            switch (direction) {
            case BOTH:
                thiz.getGraph().addFramedEdge(newVertex, thiz, label);
                thiz.getGraph().addFramedEdge(thiz, newVertex, label);
                break;
            case IN:
                thiz.getGraph().addFramedEdge(newVertex, thiz, label);
                break;
            //Assume out direction
            default:
                thiz.getGraph().addFramedEdge(thiz, newVertex, label);
            }

            return newVertex;
        }
    }

    public static final class AddVertexByObjectTypedEdgeInterceptor {

        @RuntimeType
        public static Object addVertex(@This final VertexFrame thiz, @Origin final Method method, @RuntimeType @Argument(0) final VertexFrame newVertex, @RuntimeType @Argument(1) final Class edgeType) {

            final Adjacency annotation = ((CachesReflection) thiz).getReflectionCache().getAnnotation(method, Adjacency.class);
            final Direction direction = annotation.direction();
            final String label = annotation.label();

            switch (direction) {
            case BOTH:
                thiz.getGraph().addFramedEdge(newVertex, thiz, label, edgeType);
                thiz.getGraph().addFramedEdge(thiz, newVertex, label, edgeType);
                break;
            case IN:
                thiz.getGraph().addFramedEdge(newVertex, thiz, label, edgeType);
                break;
            //Assume out direction
            default:
                thiz.getGraph().addFramedEdge(thiz, newVertex, label, edgeType);
            }

            return newVertex;
        }
    }

    public static final class SetVertexInterceptor {

        @RuntimeType
        public static void setVertex(@This final VertexFrame thiz, @Origin final Method method, @RuntimeType @Argument(0) final Iterable vertexSet) {

            final Adjacency annotation = ((CachesReflection) thiz).getReflectionCache().getAnnotation(method, Adjacency.class);
            final Direction direction = annotation.direction();
            final String label = annotation.label();


            switch (direction) {
            case BOTH:
                for (final EdgeFrame existingEdge : thiz.bothE(label))
                    existingEdge.remove();
                for (final VertexFrame newVertex : (Iterable<? extends VertexFrame>) vertexSet) {
                    thiz.getGraph().addFramedEdge(newVertex, thiz, label);
                    thiz.getGraph().addFramedEdge(thiz, newVertex, label);
                }
                break;
            case IN:
                for (final EdgeFrame existingEdge : thiz.inE(label))
                    existingEdge.remove();
                for (final VertexFrame newVertex : (Iterable<? extends VertexFrame>) vertexSet)
                    thiz.getGraph().addFramedEdge(newVertex, thiz, label);
                break;
            //Assume out direction
            default:
                for (final EdgeFrame existingEdge : thiz.outE(label))
                    existingEdge.remove();
                for (final VertexFrame newVertex : (Iterable<? extends VertexFrame>) vertexSet)
                    thiz.getGraph().addFramedEdge(thiz, newVertex, label);
            }
        }
    }

    public static final class RemoveVertexInterceptor {

        @RuntimeType
        public static void removeVertex(@This final VertexFrame thiz, @Origin final Method method, @RuntimeType @Argument(0) final VertexFrame removeVertex) {

            final Adjacency annotation = ((CachesReflection) thiz).getReflectionCache().getAnnotation(method, Adjacency.class);
            final Direction direction = annotation.direction();
            final String label = annotation.label();

            switch (direction) {
            case BOTH:
                for (final EdgeFrame edge : thiz.bothE(label))
                    if (null == removeVertex || edge.outV().next().getId().equals(removeVertex.getId()) || edge.inV().next().getId().equals(removeVertex.getId()))
                        edge.remove();
                break;
            case IN:
                for (final EdgeFrame edge : thiz.inE(label))
                    if (null == removeVertex || edge.outV().next().getId().equals(removeVertex.getId()))
                        edge.remove();
                break;
            //Assume out direction
            default:
                for (final EdgeFrame edge : thiz.outE(label))
                    if (null == removeVertex || edge.inV().next().getId().equals(removeVertex.getId()))
                        edge.remove();
            }
        }
    }
}

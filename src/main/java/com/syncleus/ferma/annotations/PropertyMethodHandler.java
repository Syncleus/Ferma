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

import com.syncleus.ferma.FramedElement;
import com.syncleus.ferma.FramedVertex;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.instrumentation.MethodDelegation;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.Argument;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.Origin;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.RuntimeType;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.This;
import net.bytebuddy.instrumentation.method.matcher.MethodMatchers;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * A method handler that implemented the Property Annotation.
 *
 * @since 0.1
 */
public class PropertyMethodHandler implements MethodHandler {
    @Override
    public Class<Property> getAnnotationType() {
        return Property.class;
    }

    @Override
    public <E> DynamicType.Builder<E> processMethod(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        final java.lang.reflect.Parameter[] arguments = method.getParameters();

        if (ReflectionUtility.isSetMethod(method)) {
            if (arguments == null || arguments.length == 0)
                throw new IllegalStateException(method.getName() + " was annotated with @Property but had no arguments.");
            else if (arguments.length == 1)
                return this.setProperty(builder, method, annotation);
            else
                throw new IllegalStateException(method.getName() + " was annotated with @Property but had more than 1 arguments.");
        }
        else if (ReflectionUtility.isGetMethod(method)) {
            if (arguments == null || arguments.length == 0)
                return this.getProperty(builder, method, annotation);
            else
                throw new IllegalStateException(method.getName() + " was annotated with @Property but had arguments.");
        }
        else
            throw new IllegalStateException(method.getName() + " was annotated with @Property but did not begin with either of the following keywords: add, get");
    }

    private <E> DynamicType.Builder<E> setProperty(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        return builder.method(MethodMatchers.is(method)).intercept(MethodDelegation.to(SetPropertyInterceptor.class));
    }

    private <E> DynamicType.Builder<E> getProperty(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation) {
        return builder.method(MethodMatchers.is(method)).intercept(MethodDelegation.to(GetPropertyInterceptor.class));
    }

    private static Enum getValueAsEnum(final Method method, final Object value) {
        Class<Enum> en = (Class<Enum>) method.getReturnType();
        if (value != null)
            return Enum.valueOf(en, value.toString());

        return null;
    }

    public static final class GetPropertyInterceptor {
        @RuntimeType
        public static Object getProperty(@This final FramedVertex thiz, @Origin final Method method) {
            final Property annotation = method.getAnnotation(Property.class);
            final String value = annotation.value();

            final Object obj = thiz.getProperty(value);
            if (method.getReturnType().isEnum())
                return getValueAsEnum(method, obj);
            else
                return obj;
        }
    }

    public static final class SetPropertyInterceptor {
        @RuntimeType
        public static void setProperty(@This final FramedElement thiz, @Origin final Method method, @RuntimeType @Argument(0) final Object obj) {
            final Property annotation = method.getAnnotation(Property.class);
            final String value = annotation.value();

            if (obj.getClass().isEnum()) {
                thiz.setProperty(value, ((Enum<?>) obj).name());
            }
            else {
                thiz.setProperty(value, obj);
            }
        }
    }
}

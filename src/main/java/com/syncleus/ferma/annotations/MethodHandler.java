package com.syncleus.ferma.annotations;

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
    public Class<? extends Annotation> getAnnotationType();

    /**
     * @param method The method being called on the frame.
     * @param annotation The annotation
     * @param builder ByteBuddy Builder class to expand.
     * @return A return value for the method.
     */
    public <E> DynamicType.Builder<E> processMethod(final DynamicType.Builder<E> builder, final Method method, final Annotation annotation);
}
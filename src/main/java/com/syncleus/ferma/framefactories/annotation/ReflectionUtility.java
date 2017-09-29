/**
 * Copyright 2004 - 2016 Syncleus, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.syncleus.ferma.framefactories.annotation;

import com.syncleus.ferma.VertexFrame;
import com.syncleus.ferma.annotations.Adjacency;
import com.syncleus.ferma.annotations.Incidence;
import com.syncleus.ferma.annotations.Property;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.lang.reflect.*;
import java.util.*;

public class ReflectionUtility {

    private static final String SET = "set";
    private static final String GET = "get";
    private static final String REMOVE = "remove";
    private static final String ADD = "add";
    private static final String IS = "is";
    private static final String CAN = "can";

    public static boolean isGetMethod(final Method method) {
        final Property propertyAnnotation = method.getAnnotation(Property.class);
        if( propertyAnnotation != null ) {
            final Property.Operation operation = propertyAnnotation.operation();
            if( operation != null && operation != Property.Operation.AUTO ) {
                if( operation == Property.Operation.GET)
                    return true;
                else
                    return false;
            }
        }

        final Incidence incidenceAnnotation = method.getAnnotation(Incidence.class);
        if( incidenceAnnotation != null ) {
            final Incidence.Operation operation = incidenceAnnotation.operation();
            if( operation != null && operation != Incidence.Operation.AUTO ) {
                if( operation == Incidence.Operation.GET)
                    return true;
                else
                    return false;
            }
        }

        final Adjacency adjacencyAnnotation = method.getAnnotation(Adjacency.class);
        if( adjacencyAnnotation != null ) {
            final Adjacency.Operation operation = adjacencyAnnotation.operation();
            if( operation != null && operation != Adjacency.Operation.AUTO ) {
                if( operation == Adjacency.Operation.GET)
                    return true;
                else
                    return false;
            }
        }

        final Class<?> returnType = method.getReturnType();
        return (method.getName().startsWith(GET) || (returnType == Boolean.class || returnType == Boolean.TYPE) && (method.getName().startsWith(IS) || method.getName().startsWith(CAN)));
    }

    public static boolean isSetMethod(final Method method) {
        Property propertyAnnotation = method.getAnnotation(Property.class);
        if( propertyAnnotation != null ) {
            Property.Operation operation = propertyAnnotation.operation();
            if( operation != null && operation != Property.Operation.AUTO  ) {
                if( operation == Property.Operation.SET)
                    return true;
                else
                    return false;
            }
        }

        final Adjacency adjacencyAnnotation = method.getAnnotation(Adjacency.class);
        if( adjacencyAnnotation != null ) {
            final Adjacency.Operation operation = adjacencyAnnotation.operation();
            if( operation != null && operation != Adjacency.Operation.AUTO ) {
                if( operation == Adjacency.Operation.SET)
                    return true;
                else
                    return false;
            }
        }

        return method.getName().startsWith(SET);
    }

    public static boolean isRemoveMethod(final Method method) {
        Property propertyAnnotation = method.getAnnotation(Property.class);
        if( propertyAnnotation != null ) {
            Property.Operation operation = propertyAnnotation.operation();
            if( operation != null && operation != Property.Operation.AUTO  ) {
                if( operation == Property.Operation.REMOVE)
                    return true;
                else
                    return false;
            }
        }

        final Incidence incidenceAnnotation = method.getAnnotation(Incidence.class);
        if( incidenceAnnotation != null ) {
            final Incidence.Operation operation = incidenceAnnotation.operation();
            if( operation != null && operation != Incidence.Operation.AUTO ) {
                if( operation == Incidence.Operation.REMOVE)
                    return true;
                else
                    return false;
            }
        }

        final Adjacency adjacencyAnnotation = method.getAnnotation(Adjacency.class);
        if( adjacencyAnnotation != null ) {
            final Adjacency.Operation operation = adjacencyAnnotation.operation();
            if( operation != null && operation != Adjacency.Operation.AUTO ) {
                if( operation == Adjacency.Operation.REMOVE)
                    return true;
                else
                    return false;
            }
        }

        return method.getName().startsWith(REMOVE);
    }

    public static boolean isAddMethod(final Method method) {
        final Incidence incidenceAnnotation = method.getAnnotation(Incidence.class);
        if( incidenceAnnotation != null ) {
            final Incidence.Operation operation = incidenceAnnotation.operation();
            if( operation != null && operation != Incidence.Operation.AUTO ) {
                if( operation == Incidence.Operation.ADD)
                    return true;
                else
                    return false;
            }
        }

        final Adjacency adjacencyAnnotation = method.getAnnotation(Adjacency.class);
        if( adjacencyAnnotation != null ) {
            final Adjacency.Operation operation = adjacencyAnnotation.operation();
            if( operation != null && operation != Adjacency.Operation.AUTO ) {
                if( operation == Adjacency.Operation.ADD)
                    return true;
                else
                    return false;
            }
        }

        return method.getName().startsWith(ADD);
    }

    public static boolean acceptsIterator(final Method method, int parameterIndex) {
        return (parameterIndex + 1) == method.getParameterTypes().length && Iterator.class.isAssignableFrom(method.getParameterTypes()[parameterIndex]);
    }

    public static boolean acceptsIterable(final Method method, int parameterIndex) {
        return (parameterIndex + 1) == method.getParameterTypes().length && Iterable.class.isAssignableFrom(method.getParameterTypes()[parameterIndex]);
    }

    public static boolean acceptsVertexFrame(final Method method, int parameterIndex) {
        return (parameterIndex + 1) == method.getParameterTypes().length && VertexFrame.class.isAssignableFrom(method.getParameterTypes()[parameterIndex]);
    }

    public static boolean returnsIterator(final Method method) {
        return Iterator.class.isAssignableFrom(method.getReturnType());
    }

    public static boolean returnsList(final Method method) {
        return List.class.isAssignableFrom(method.getReturnType());
    }

    public static boolean returnsSet(final Method method) {
        return Set.class.isAssignableFrom(method.getReturnType());
    }

    public static Type getType(final Type[] types, final int pos) {
        if (pos >= types.length)
            throw new IndexOutOfBoundsException("No type can be found at position " + pos);
        return types[pos];
    }

    public static Class<?> getActualType(Type genericType, final int pos) {

        if (genericType == null)
            return null;
        if (!ParameterizedType.class.isAssignableFrom(genericType.getClass())) {
            if (genericType instanceof TypeVariable)
                genericType = getType(((TypeVariable<?>) genericType).getBounds(), pos);
            else if (genericType instanceof WildcardType) {
                final WildcardType wildcardType = (WildcardType) genericType;
                Type[] bounds = wildcardType.getLowerBounds();
                if (bounds.length == 0)
                    bounds = wildcardType.getUpperBounds();
                genericType = getType(bounds, pos);
            }

            final Class<?> cls = (Class<?>) genericType;
            return cls.isArray() ? cls.getComponentType() : cls;
        }
        final ParameterizedType paramType = (ParameterizedType) genericType;
        final Type t = getType(paramType.getActualTypeArguments(), pos);
        return t instanceof Class ? (Class<?>) t : getActualType(t, pos);
    }

    @SuppressWarnings("rawtypes")
    public static Class getGenericClass(final Method method) {
        final Type returnType = method.getGenericReturnType();
        return getActualType(returnType, 0);

    }
}

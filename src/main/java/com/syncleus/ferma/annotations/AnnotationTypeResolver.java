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
import com.syncleus.ferma.ReflectionCache;
import com.syncleus.ferma.TypeResolver;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Vertex;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.ClassLoadingStrategy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.instrumentation.FieldAccessor;
import net.bytebuddy.modifier.FieldManifestation;
import net.bytebuddy.modifier.Visibility;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class AnnotationTypeResolver implements TypeResolver {
    private final ReflectionCache reflectionCache;
    /**
     * Creates a new GrailTypeResolver with a typing engine that can recognize the specified types. While these types
     * still need to be included in a separate TypedModule they must be created here as well to ensure proper look-ups
     * occur.
     *
     * @since 0.1
     */
    public AnnotationTypeResolver(final ReflectionCache reflectionCache) {
        this.reflectionCache = reflectionCache;
    }

    @Override
    public <T> Class<T> resolve(final Element element, final Class<T> kind) {
        final String nodeClazz = element.getProperty("implementation_type");
        if( nodeClazz == null )
                return kind;

        Class<T> nodeKind = (Class<T>) this.reflectionCache.forName(nodeClazz);

        if(kind.isAssignableFrom(nodeKind) || kind.equals(FramedVertex.class) || kind.equals(FramedEdge.class) || kind.equals(Object.class))
            return nodeKind;
        else
            return kind;
    }

    @Override
    public <T> void init(final Element element, final Class<T> kind) {
        final String clazz = element.getProperty("implementation_type");
        if (clazz == null) {
            element.setProperty("implementation_type", kind.getName());
        }
    }
}

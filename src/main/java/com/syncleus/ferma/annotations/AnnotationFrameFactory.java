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

import com.syncleus.ferma.FrameFactory;
import com.syncleus.ferma.FramedEdge;
import com.syncleus.ferma.FramedVertex;
import com.syncleus.ferma.ReflectionCache;
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

public class AnnotationFrameFactory implements FrameFactory {
    private final Map<Class<? extends Annotation>, MethodHandler> methodHandlers = new HashMap<>();
    private final Map<Class, Class> constructedClassCache = new HashMap<>();
    private final ReflectionCache reflectionCache;

    public AnnotationFrameFactory(final ReflectionCache reflectionCache) {
        this.reflectionCache = reflectionCache;

        final PropertyMethodHandler propertyHandler = new PropertyMethodHandler();
        methodHandlers.put(propertyHandler.getAnnotationType(), propertyHandler);

        final InVertexMethodHandler inVertexHandler = new InVertexMethodHandler();
        methodHandlers.put(inVertexHandler.getAnnotationType(), inVertexHandler);

        final OutVertexMethodHandler outVertexHandler = new OutVertexMethodHandler();
        methodHandlers.put(outVertexHandler.getAnnotationType(), outVertexHandler);

        final AdjacencyMethodHandler adjacencyHandler = new AdjacencyMethodHandler();
        methodHandlers.put(adjacencyHandler.getAnnotationType(), adjacencyHandler);

        final IncidenceMethodHandler incidenceHandler = new IncidenceMethodHandler();
        methodHandlers.put(incidenceHandler.getAnnotationType(), incidenceHandler);
    }

    @Override
    public <T> T create(final Element element, final Class<T> kind) {

        Class<? extends T> resolvedKind = kind;
        if( isAbstract(resolvedKind) )
            resolvedKind = constructClass(element, kind);
        try {
            T object = resolvedKind.newInstance();
            if (object instanceof CachesReflection)
                ((CachesReflection) object).setReflectionCache(this.reflectionCache);
            return object;
        } catch (InstantiationException caught) {
            throw new IllegalArgumentException("kind could not be instantiated", caught);
        } catch (IllegalAccessException caught) {
            throw new IllegalArgumentException("kind could not be instantiated", caught);
        }
    }

    private static final boolean isAbstract(final Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
    }

    private static final boolean isAbstract(final Method method) {
        return Modifier.isAbstract(method.getModifiers());
    }

    private final <E> Class<? extends E> constructClass(final Element element, final Class<E> clazz) {
        Class constructedClass = constructedClassCache.get(clazz);
        if(constructedClass != null )
            return constructedClass;

        DynamicType.Builder<? extends E> classBuilder;
        if( clazz.isInterface() ) {
            if( element instanceof Vertex)
                classBuilder = (DynamicType.Builder<? extends E>) new ByteBuddy().withImplementing(clazz).subclass(FramedVertex.class);
            else if( element instanceof Edge)
                classBuilder = (DynamicType.Builder<? extends E>) new ByteBuddy().withImplementing(clazz).subclass(FramedEdge.class);
            else
                throw new IllegalStateException("class is neither an Edge or a vertex!");
        }
        else {
            if( element instanceof Vertex && ! FramedVertex.class.isAssignableFrom(clazz) )
                throw new IllegalStateException(clazz.getName() + " Class is not a type of FramedVertex");
            if( element instanceof Edge && ! FramedEdge.class.isAssignableFrom(clazz) )
                throw new IllegalStateException(clazz.getName() + " Class is not a type of FramedEdge");
            classBuilder = (DynamicType.Builder<? extends E>) new ByteBuddy().subclass(clazz);
        }

        classBuilder = classBuilder.defineField("reflectionCache", ReflectionCache.class, Visibility.PRIVATE, FieldManifestation.PLAIN).implement(CachesReflection.class).intercept(FieldAccessor.ofBeanProperty());

        //try and construct any abstract methods that are left
        for(final Method method : clazz.getMethods() ) {
            if( isAbstract(method) ) {
                annotation_loop:
                for (final Annotation annotation : method.getAnnotations()) {
                    final MethodHandler handler = methodHandlers.get(annotation.annotationType());
                    if (handler != null) {
                        classBuilder = handler.processMethod(classBuilder, method, annotation);
                        break annotation_loop;
                    }
                }
            }
        }

        constructedClass = classBuilder.make().load(AnnotationFrameFactory.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER).getLoaded();
        this.constructedClassCache.put(clazz, constructedClass);
        return constructedClass;
    }
}

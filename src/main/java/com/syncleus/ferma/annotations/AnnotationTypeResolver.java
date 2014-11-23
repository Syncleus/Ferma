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
    private final Map<Class<? extends Annotation>, MethodHandler> methodHandlers = new HashMap<>();

    /**
     * Creates a new GrailTypeResolver with a typing engine that can recognize the specified types. While these types
     * still need to be included in a separate TypedModule they must be created here as well to ensure proper look-ups
     * occur.
     *
     * @since 0.1
     */
    public AnnotationTypeResolver() {
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
    public <T> Class<T> resolve(final Element element, final Class<T> kind) {
        final String nodeClazz = element.getProperty("implementation_type");
        if( nodeClazz == null ) {
            if (!isAbstract(kind))
                return kind;
            else
                return (Class<T>) constructClass(element, kind);
        }

        final Class<T> nodeKind;
        try {
             nodeKind = (Class<T>) Class.forName(nodeClazz);
        }
        catch (ClassNotFoundException e) {
            throw new IllegalStateException("The class " + nodeClazz + " cannot be found");
        }

        Class<T> resolvedKind;
        if(kind.isAssignableFrom(nodeKind) || kind.equals(FramedVertex.class) || kind.equals(FramedEdge.class) || kind.equals(Object.class))
            resolvedKind = nodeKind;
        else
            resolvedKind = kind;

        if( ! isAbstract(resolvedKind) )
            return resolvedKind;
        else
            return (Class<T>) constructClass(element, resolvedKind);
    }

    @Override
    public <T> void init(final Element element, final Class<T> kind) {
        final String clazz = element.getProperty("implementation_type");
        if (clazz == null) {
            element.setProperty("implementation_type", kind.getName());
        }
    }

    private static final boolean isAbstract(final Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
    }

    private static final boolean isAbstract(final Method method) {
        return Modifier.isAbstract(method.getModifiers());
    }

    private final <E> Class<? extends E> constructClass(final Element element, final Class<E> clazz) {
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

        classBuilder = classBuilder.defineField("hierarchy", Map.class, Visibility.PRIVATE, FieldManifestation.PLAIN).implement(CachedHierarchy.class).intercept(FieldAccessor.ofBeanProperty());

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

        return classBuilder.make().load(AnnotationTypeResolver.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER).getLoaded();
    }
}

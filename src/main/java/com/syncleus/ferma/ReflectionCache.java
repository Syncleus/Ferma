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
package com.syncleus.ferma;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class ReflectionCache extends Reflections {
    private final Map<String, Set<String>> hierarchy;
    private final Map<Method, Map<Class<Annotation>, Annotation>> annotationCache = new HashMap<>();
    private final Map<String, Class> classStringCache = new HashMap<>();

    public ReflectionCache() {
        super();

        this.hierarchy = new HashMap<>();
    }

    public ReflectionCache(final Collection<? extends Class<?>> annotatedTypes) {
        super(assembleConfig(assembleClassUrls(annotatedTypes)));

        this.hierarchy = constructHierarchy(annotatedTypes);
    }

    public Set<? extends String> getSubTypeNames(final Class<?> type) {
        Set<String> subtypes = this.hierarchy.get(type.getName());
        if( subtypes == null )
            subtypes = Collections.singleton(type.getName());
        return Collections.unmodifiableSet(subtypes);
    }

    public Set<? extends String> getSubTypeNames(final String typeName) {
        Set<String> subtypes = this.hierarchy.get(typeName);
        if( subtypes == null )
            subtypes = Collections.singleton(typeName);
        return Collections.unmodifiableSet(subtypes);
    }

    public <E extends Annotation> E getAnnotation(final Method method, final Class<E> annotationType) {
        Map<Class<Annotation>, Annotation> annotationsPresent = annotationCache.get(method);
        if( annotationsPresent == null ) {
            annotationsPresent = new HashMap<>();
            annotationCache.put(method, annotationsPresent);
        }

        E annotation = (E) annotationsPresent.get(annotationType);
        if( annotation == null ) {
            annotation = method.getAnnotation(annotationType);
            annotationsPresent.put((Class<Annotation>)annotationType, annotation);
        }
        return annotation;
    }

    public Class forName(final String className) {
        Class type = this.classStringCache.get(className);
        if(type == null) {
            try {
                type = Class.forName(className);
                classStringCache.put(className, type);
            }
            catch (final ClassNotFoundException e) {
                throw new IllegalStateException("The class " + className + " cannot be found");
            }
        }
        return type;
    }

    private static final ConfigurationBuilder assembleConfig(final Set<URL> toScanUrls) {
        final ConfigurationBuilder reflectionConfig = new ConfigurationBuilder();
        reflectionConfig.addUrls(toScanUrls);
        reflectionConfig.setScanners(new TypeAnnotationsScanner(), new SubTypesScanner());
        return reflectionConfig;
    }

    private static final Set<URL> assembleClassUrls(final Collection<? extends Class<?>> annotatedTypes) {
        final Set<URL> toScanUrls = new HashSet<>();
        for(final Class<?> annotatedType : annotatedTypes)
            toScanUrls.add(ClasspathHelper.forClass(annotatedType));
        return toScanUrls;
    }

    private static final Set<URL> assembleClassUrls(final Collection<? extends Class<?>> annotatedTypes, final Collection<?> packages) {
        final Set<URL> toScanUrls = new HashSet<>();
        for(final Class<?> annotatedType : annotatedTypes)
            toScanUrls.add(ClasspathHelper.forClass(annotatedType));
        for(final Object packageObj : packages)
            toScanUrls.addAll(ClasspathHelper.forPackage(packageObj.toString()));
        return toScanUrls;
    }

    private static Map<String, Set<String>> constructHierarchy(final Collection<? extends Class<?>> types) {
        final Map<String, Set<String>> hierarchy = new HashMap<>();

        for (final Class<?> parentType : types) {
            Set<String> children = hierarchy.get(parentType.getName());
            if (children == null) {
                children = new HashSet<>();
                hierarchy.put(parentType.getName(), children);
            }

            for (final Class<?> childType : types) {
                if (parentType.isAssignableFrom(childType)) {
                    children.add(childType.getName());
                }
            }
        }

        return hierarchy;
    }
}

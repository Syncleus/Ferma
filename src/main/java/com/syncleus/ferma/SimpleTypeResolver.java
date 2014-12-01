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

import com.tinkerpop.blueprints.Element;

/**
 * This type resolver will use the Java class stored in the 'java_class' on
 * the element.
 */
public class SimpleTypeResolver implements TypeResolver {

    private final ReflectionCache reflectionCache;

    private final String typeKey;

    /**
     * Creates a new SimpleTypeResolver with a typing engine that can recognize the specified types. While these types
     * still need to be included in a separate TypedModule they must be created here as well to ensure proper look-ups
     * occur.
     *
     * @since 0.1
     */
    public SimpleTypeResolver() {
        this.reflectionCache = new ReflectionCache();
        this.typeKey = SYSTEM_DEFAULT_TYPE_KEY;
    }

    /**
     * Creates a new SimpleTypeResolver with a typing engine that can recognize the specified types. While these types
     * still need to be included in a separate TypedModule they must be created here as well to ensure proper look-ups
     * occur.
     *
     * @param typeKey The property name to use to represent the types in the graph.
     * @since 0.1
     */
    public SimpleTypeResolver(final String typeKey) {
        if( typeKey == null )
            throw new IllegalArgumentException("typeKey can not be null.");
        else if( typeKey.isEmpty() )
            throw new IllegalArgumentException("typeKey can not be an empty string.");

        this.reflectionCache = new ReflectionCache();
        this.typeKey = typeKey;
    }

    /**
     * Creates a new SimpleTypeResolver with a typing engine that can recognize the specified types. While these types
     * still need to be included in a separate TypedModule they must be created here as well to ensure proper look-ups
     * occur.
     *
     * @param reflectionCache a ReflectionCache object used to perform and cache reflections on classes.
     * @since 0.1
     */
    public SimpleTypeResolver(final ReflectionCache reflectionCache) {
        if( reflectionCache == null )
            throw new IllegalArgumentException("reflectionCache can not be null");
        
        this.reflectionCache = reflectionCache;
        this.typeKey = SYSTEM_DEFAULT_TYPE_KEY;
    }

    /**
     * Creates a new SimpleTypeResolver with a typing engine that can recognize the specified types. While these types
     * still need to be included in a separate TypedModule they must be created here as well to ensure proper look-ups
     * occur.
     *
     * @param reflectionCache a ReflectionCache object used to perform and cache reflections on classes.
     * @param typeKey The property name to use to represent the types in the graph.
     * @since 0.1
     */
    public SimpleTypeResolver(final ReflectionCache reflectionCache, final String typeKey) {
        if( typeKey == null )
            throw new IllegalArgumentException("typeKey can not be null.");
        else if( typeKey.isEmpty() )
            throw new IllegalArgumentException("typeKey can not be an empty string.");
        else if( reflectionCache == null )
            throw new IllegalArgumentException("reflectionCache can not be null");

        this.reflectionCache = reflectionCache;
        this.typeKey = typeKey;
    }

    @Override
    public <T> Class<? extends T> resolve(final Element element, final Class<T> kind) {
        final String nodeClazz = element.getProperty(SYSTEM_DEFAULT_TYPE_KEY);
        if (nodeClazz == null)
            return kind;

        final Class<T> nodeKind = (Class<T>) this.reflectionCache.forName(nodeClazz);

        if (kind.isAssignableFrom(nodeKind) || kind.equals(VertexFrame.class) || kind.equals(EdgeFrame.class) || kind.equals(AbstractVertexFrame.class) || kind.equals(AbstractEdgeFrame.class) || kind.
              equals(Object.class))
            return nodeKind;
        else
            return kind;
    }

    @Override
    public void init(final Element element, final Class<?> kind) {
        final String clazz = element.getProperty(SYSTEM_DEFAULT_TYPE_KEY);
        if (clazz == null)
            element.setProperty(SYSTEM_DEFAULT_TYPE_KEY, kind.getName());
    }
}

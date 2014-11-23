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
import com.syncleus.ferma.FramedElement;
import com.tinkerpop.blueprints.Element;

import java.util.*;

public class AnnotationFrameFactory implements FrameFactory {
    private final Map<String, Set<String>> hierarchy;

    public AnnotationFrameFactory(final Collection<? extends Class<?>> types) {
        this.hierarchy = AnnotationFrameFactory.constructHierarchy(types);
    }

    @Override
    public <T> T create(final Element e, final Class<T> kind) {
        try {
            T object = kind.newInstance();
            if( object instanceof CachedHierarchy)
                ((CachedHierarchy)object).setHierarchy(this.hierarchy);
            return object;
        } catch (InstantiationException caught) {
            throw new IllegalArgumentException("kind could not be instantiated", caught);
        } catch (IllegalAccessException caught) {
            throw new IllegalArgumentException("kind could not be instantiated", caught);
        }
    }

    private static Map<String, Set<String>> constructHierarchy(final Collection<? extends Class<?>> types) {
        final Map<String, Set<String>> hierarchy = new HashMap<>();

        for(final Class<?> parentType : types) {
            Set<String> children = hierarchy.get(parentType.getName());
            if( children == null ) {
                children = new HashSet<>();
                hierarchy.put(parentType.getName(), children);
            }

            for(final Class<?> childType : types) {
                if( parentType.isAssignableFrom(childType) ) {
                    children.add(childType.getName());
                }
            }
        }

        return hierarchy;
    }
}

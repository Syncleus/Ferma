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
    public <T extends FramedElement> T create(final Element e, final Class<T> kind) {
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

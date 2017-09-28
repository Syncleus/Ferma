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
package com.syncleus.ferma.typeresolvers;

import com.syncleus.ferma.AbstractEdgeFrame;
import com.syncleus.ferma.AbstractVertexFrame;
import com.syncleus.ferma.EdgeFrame;
import com.syncleus.ferma.ReflectionCache;
import com.syncleus.ferma.VertexFrame;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.Traverser;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Property;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 * This type resolver will use the Java class stored in the 'java_class' on
 * the element.
 */
public class PolymorphicTypeResolver implements TypeResolver {
    public final static String TYPE_RESOLUTION_KEY = "ferma_type";

    private final ReflectionCache reflectionCache;
    private final String typeResolutionKey;

    /**
     * Creates a new SimpleTypeResolver with a typing engine that can recognize the specified types. While these types
     * still need to be included in a separate TypedModule they must be created here as well to ensure proper look-ups
     * occur.
     *
     * @since 2.0.0
     */
    public PolymorphicTypeResolver() {
        this.reflectionCache = new ReflectionCache();
	this.typeResolutionKey = TYPE_RESOLUTION_KEY;
    }

    /**
     * Creates a new SimpleTypeResolver with a typing engine that can recognize the specified types. While these types
     * still need to be included in a separate TypedModule they must be created here as well to ensure proper look-ups
     * occur.
     *
     * @param typeResolutionKey The key used to identfy a element's type.
     * @since 2.0.0
     */
    public PolymorphicTypeResolver(final String typeResolutionKey) {
        this.reflectionCache = new ReflectionCache();
	this.typeResolutionKey = typeResolutionKey;
    }

    /**
     * Creates a new SimpleTypeResolver with a typing engine that can recognize the specified types. While these types
     * still need to be included in a separate TypedModule they must be created here as well to ensure proper look-ups
     * occur.
     *
     * @param reflectionCache the ReflectionCache used to examine the type hierarchy and do general reflection.
     * @since 2.0.0
     */
    public PolymorphicTypeResolver(final ReflectionCache reflectionCache) {
        this.reflectionCache = reflectionCache;
	this.typeResolutionKey = TYPE_RESOLUTION_KEY;
    }

    /**
     * Creates a new SimpleTypeResolver with a typing engine that can recognize the specified types. While these types
     * still need to be included in a separate TypedModule they must be created here as well to ensure proper look-ups
     * occur.
     *
     * @param reflectionCache the ReflectionCache used to examine the type hierarchy and do general reflection.
     * @param typeResolutionKey The key used to identfy a element's type.
     * @since 2.0.0
     */
    public PolymorphicTypeResolver(final ReflectionCache reflectionCache, final String typeResolutionKey) {
        this.reflectionCache = reflectionCache;
	this.typeResolutionKey = typeResolutionKey;
    }

    @Override
    public <T> Class<? extends T> resolve(final Element element, final Class<T> kind) {
        final Property<String> nodeClazzProperty = element.<String>property(this.typeResolutionKey);
        final String nodeClazz;
        if( nodeClazzProperty.isPresent() )
            nodeClazz = nodeClazzProperty.value();
        else
            return kind;

        final Class<T> nodeKind = (Class<T>) this.reflectionCache.forName(nodeClazz);

        if (kind.isAssignableFrom(nodeKind) || kind.equals(VertexFrame.class) || kind.equals(EdgeFrame.class) || kind.equals(AbstractVertexFrame.class) || kind.equals(AbstractEdgeFrame.class) || kind.
              equals(Object.class))
            return nodeKind;
        else
            return kind;
    }
    
    @Override
    public Class<?> resolve(final Element element) {
        final Property<String> typeResolutionName = element.<String>property(this.typeResolutionKey);

        if( typeResolutionName.isPresent() )
            return this.reflectionCache.forName(typeResolutionName.value());
        else
            return null;
    }

    @Override
    public void init(final Element element, final Class<?> kind) {
        element.property(this.typeResolutionKey, kind.getName());
    }
    
    @Override
    public void deinit(final Element element) {
        element.property(this.typeResolutionKey).remove();
    }

    @Override
    public <P extends Element, T extends Element> GraphTraversal<P, T> hasType(final GraphTraversal<P, T> traverser, final Class<?> type) {
        final Set<? extends String> allAllowedValues = this.reflectionCache.getSubTypeNames(type.getName());
        return traverser.has(typeResolutionKey, org.apache.tinkerpop.gremlin.process.traversal.P.within(allAllowedValues));
    }

    @Override
    public <P extends Element, T extends Element> GraphTraversal<P, T> hasNotType(final GraphTraversal<P, T> traverser, final Class<?> type) {
        final Set<? extends String> allAllowedValues = this.reflectionCache.getSubTypeNames(type.getName());
        return traverser.filter(new Predicate<Traverser<T>>() {
            @Override
            public boolean test(final Traverser<T> toCheck) {
                final Property<String> property = toCheck.get().property(typeResolutionKey);
                if( !property.isPresent() )
                    return true;

                final String resolvedType = property.value();
                if( allAllowedValues.contains(resolvedType) )
                    return false;
                else
                    return true;
            }
        });
    }

}

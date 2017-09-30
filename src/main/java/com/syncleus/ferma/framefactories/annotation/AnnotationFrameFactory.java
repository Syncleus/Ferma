/**
 * Copyright 2004 - 2017 Syncleus, Inc.
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

import com.syncleus.ferma.framefactories.FrameFactory;
import com.syncleus.ferma.*;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.FieldManifestation;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FieldAccessor;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class AnnotationFrameFactory extends AbstractAnnotationFrameFactory {

    public AnnotationFrameFactory(final ReflectionCache reflectionCache) {
        super(reflectionCache, collectHandlers(null));
    }

    /**
     * Subclasses can use this constructor to add additional custom method handlers.
     *
     * @param reflectionCache The reflection cache used to inspect annotations.
     * @param handlers The handlers used to generate new annotation support.
     */
    protected AnnotationFrameFactory(final ReflectionCache reflectionCache, Set<MethodHandler> handlers) {
        super(reflectionCache, collectHandlers(handlers));
    }

    private static final Set<MethodHandler> collectHandlers(Set<MethodHandler> additionalHandlers) {
        final Set<MethodHandler> methodHandlers = new HashSet<>();

        final PropertyMethodHandler propertyHandler = new PropertyMethodHandler();
        methodHandlers.add(propertyHandler);

        final InVertexMethodHandler inVertexHandler = new InVertexMethodHandler();
        methodHandlers.add(inVertexHandler);

        final OutVertexMethodHandler outVertexHandler = new OutVertexMethodHandler();
        methodHandlers.add(outVertexHandler);

        final AdjacencyMethodHandler adjacencyHandler = new AdjacencyMethodHandler();
        methodHandlers.add(adjacencyHandler);

        final IncidenceMethodHandler incidenceHandler = new IncidenceMethodHandler();
        methodHandlers.add(incidenceHandler);

        if(additionalHandlers != null)
            methodHandlers.addAll(additionalHandlers);

        return methodHandlers;
    }

}

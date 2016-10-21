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
/*
 * Part or all of this source file was forked from a third-party project, the details of which are listed below.
 *
 * Source Project: Totorom
 * Source URL: https://github.com/BrynCooke/totorom
 * Source License: Apache Public License v2.0
 * When: November, 20th 2014
 */
package com.syncleus.ferma;

import com.google.common.base.Function;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Element;

import javax.annotation.Nullable;
import java.util.Iterator;

/**
 * The base class that all edge frames must extend.
 */
public abstract class AbstractEdgeFrame extends AbstractElementFrame implements EdgeFrame {

    @Override
    public Edge getElement() {
        return (Edge) super.getElement();
    }

    @Override
    public String getLabel() {
        return getElement().label();
    }

    @Override
    public JsonObject toJson() {
        final JsonObject json = new JsonObject();
        if (getId() instanceof Number)
            json.addProperty("id", getId(Number.class));
        if (getId() instanceof String)
            json.addProperty("id", getId(String.class));
        json.addProperty("elementClass", "edge");
        json.addProperty("label", getLabel());
        for (final String key : getPropertyKeys()) {

            final Object value = getProperty(key);
            if (value instanceof Number)
                json.addProperty(key, (Number) value);
            else if (value instanceof String)
                json.addProperty(key, (String) value);
        }
        return json;
    }

    @Override
    public String toString() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(toJson());
    }

    @Override
    public <T> T reframe(final Class<T> kind) {
        return getGraph().frameElement(getElement(), kind);
    }

    @Override
    public <T> T reframeExplicit(final Class<T> kind) {
        return getGraph().frameElementExplicit(getElement(), kind);
    }

    @Override
    public <T> Iterable<? extends T> traverse(final Function<GraphTraversal<? extends Edge, ? extends Edge>, Iterator<? extends Element>> traverser, final ClassInitializer<T> initializer) {
        return this.getGraph().traverse(new Function<GraphTraversalSource, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversalSource input) {
                return traverser.apply(input.E(getElement().id()));
            }
        }, initializer);
    }

    @Override
    public <T> Iterable<? extends T> traverse(Function<GraphTraversal<? extends Edge, ? extends Edge>, Iterator<? extends Element>> traverser, Class<T> kind, boolean isNew) {
        return this.getGraph().traverse(new Function<GraphTraversalSource, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversalSource input) {
                return traverser.apply(input.E(getElement().id()));
            }
        }, kind, isNew);
    }

    @Override
    public <T> Iterable<? extends T> traverseExplicit(Function<GraphTraversal<? extends Edge, ? extends Edge>, Iterator<? extends Element>> traverser, ClassInitializer<T> initializer) {
        return this.getGraph().traverseExplicit(new Function<GraphTraversalSource, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversalSource input) {
                return traverser.apply(input.E(getElement().id()));
            }
        }, initializer);
    }

    @Override
    public <T> Iterable<? extends T> traverseExplicit(Function<GraphTraversal<? extends Edge, ? extends Edge>, Iterator<? extends Element>> traverser, Class<T> kind, boolean isNew) {
        return this.getGraph().traverseExplicit(new Function<GraphTraversalSource, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversalSource input) {
                return traverser.apply(input.E(getElement().id()));
            }
        }, kind, isNew);
    }

    @Override
    public <T> T traverseSingleton(Function<GraphTraversal<? extends Edge, ? extends Edge>, Iterator<? extends Element>> traverser, ClassInitializer<T> initializer) {
        return this.getGraph().traverseSingleton(new Function<GraphTraversalSource, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversalSource input) {
                return traverser.apply(input.E(getElement().id()));
            }
        }, initializer);
    }

    @Override
    public <T> T traverseSingleton(Function<GraphTraversal<? extends Edge, ? extends Edge>, Iterator<? extends Element>> traverser, Class<T> kind, boolean isNew) {
        return this.getGraph().traverseSingleton(new Function<GraphTraversalSource, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversalSource input) {
                return traverser.apply(input.E(getElement().id()));
            }
        }, kind, isNew);
    }

    @Override
    public <T> T traverseSingletonExplicit(Function<GraphTraversal<? extends Edge, ? extends Edge>, Iterator<? extends Element>> traverser, ClassInitializer<T> initializer) {
        return this.getGraph().traverseSingletonExplicit(new Function<GraphTraversalSource, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversalSource input) {
                return traverser.apply(input.E(getElement().id()));
            }
        }, initializer);
    }

    @Override
    public <T> T traverseSingletonExplicit(Function<GraphTraversal<? extends Edge, ? extends Edge>, Iterator<? extends Element>> traverser, Class<T> kind, boolean isNew) {
        return this.getGraph().traverseSingletonExplicit(new Function<GraphTraversalSource, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversalSource input) {
                return traverser.apply(input.E(getElement().id()));
            }
        }, kind, isNew);
    }

    @Override
    public void traverse(VoidFunction<GraphTraversal<? extends Edge, ? extends Edge>> traverser) {
        this.getGraph().traverse(new VoidFunction<GraphTraversalSource>() {
            @Override
            public void apply(GraphTraversalSource input) {
                traverser.apply(input.E(getElement().id()));
            }
        });
    }
}

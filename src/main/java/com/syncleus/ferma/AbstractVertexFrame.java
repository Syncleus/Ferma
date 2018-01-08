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
/*
 * Part or all of this source file was forked from a third-party project, the details of which are listed below.
 *
 * Source Project: Totorom
 * Source URL: https://github.com/BrynCooke/totorom
 * Source License: Apache Public License v2.0
 * When: November, 20th 2014
 */
package com.syncleus.ferma;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.*;
import org.apache.tinkerpop.gremlin.structure.util.wrapped.WrappedElement;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * The base class that all vertex frames must extend.
 */
public abstract class AbstractVertexFrame extends AbstractElementFrame implements VertexFrame {

    @Override
    public Vertex getElement() {
        if(super.getElement() instanceof WrappedElement) {
            return (Vertex) ((WrappedElement)super.getElement()).getBaseElement();
        } else {
            return (Vertex) super.getElement();
        }
    }

    @Override
    public <T> T addFramedEdge(final String label, final VertexFrame inVertex, final ClassInitializer<T> initializer) {

        final Edge edge = getElement().addEdge(label, inVertex.getElement());
        final T framedEdge = getGraph().frameNewElement(edge, initializer);
        return framedEdge;
    }
    
    @Override
    public <T> T addFramedEdge(final String label, final VertexFrame inVertex, final Class<T> kind) {
        return this.addFramedEdge(label, inVertex, new DefaultClassInitializer<>(kind));
    }

    @Override
    public <T> T addFramedEdgeExplicit(final String label, final VertexFrame inVertex, final ClassInitializer<T> initializer) {

        final Edge edge = getElement().addEdge(label, inVertex.getElement());
        final T framedEdge = getGraph().frameNewElementExplicit(edge, initializer);
        return framedEdge;
    }
    
    @Override
    public <T> T addFramedEdgeExplicit(final String label, final VertexFrame inVertex, final Class<T> kind) {
        return this.addFramedEdgeExplicit(label, inVertex, new DefaultClassInitializer<>(kind));
    }

    @Override
    public TEdge addFramedEdge(final String label, final VertexFrame inVertex) {
        return addFramedEdge(label, inVertex, TEdge.DEFAULT_INITIALIZER);
    }

    @Override
    public TEdge addFramedEdgeExplicit(final String label, final VertexFrame inVertex) {
        return addFramedEdgeExplicit(label, inVertex, TEdge.DEFAULT_INITIALIZER);
    }

    @Override
    public void linkOut(final VertexFrame vertex, final String... labels) {
        for (final String label : labels)
            this.getElement().addEdge(label, vertex.getElement());
    }

    @Override
    public void linkIn(final VertexFrame vertex, final String... labels) {
        for (final String label : labels)
            vertex.getElement().addEdge(label, this.getElement());
    }

    @Override
    public void linkBoth(final VertexFrame vertex, final String... labels) {
        for (final String label : labels) {
            vertex.getElement().addEdge(label, this.getElement());
            this.getElement().addEdge(label, vertex.getElement());
        }
    }

    @Override
    public void unlinkOut(final VertexFrame vertex, final String... labels) {
        final Iterator<Edge> edges = this.getRawTraversal().outE(labels);
        edges.forEachRemaining(new Consumer<Edge>() {
            @Override
            public void accept(final Edge edge) {
                if( vertex == null )
                    edge.remove();
                else if( edge.inVertex().equals(vertex.getElement()) )
                    edge.remove();
            }
        });
    }

    @Override
    public void unlinkIn(final VertexFrame vertex, final String... labels) {
        final Iterator<Edge> edges = this.getRawTraversal().inE(labels);
        edges.forEachRemaining(new Consumer<Edge>() {
            @Override
            public void accept(final Edge edge) {
                if( vertex == null ) {
                    edge.remove();
                }
                else if( edge.outVertex().id().equals(vertex.getElement().id()) ) {
                    edge.remove();
                }
            }
        });
    }

    @Override
    public void unlinkBoth(final VertexFrame vertex, final String... labels) {
        this.unlinkIn(vertex, labels);
        this.unlinkOut(vertex, labels);
    }

    @Override
    public void setLinkOut(final VertexFrame vertex, final String... labels) {
        unlinkOut(null, labels);
        if (vertex != null)
            linkOut(vertex, labels);
    }

    @Override
    public void setLinkIn(final VertexFrame vertex, final String... labels) {
        unlinkIn(null, labels);
        if (vertex != null)
            linkIn(vertex, labels);
    }

    @Override
    public void setLinkBoth(final VertexFrame vertex, final String... labels) {
        unlinkBoth(null, labels);
        if (vertex != null)
            linkBoth(vertex, labels);
    }

    @Override
    public <K> K setLinkOut(final ClassInitializer<K> initializer, final String... labels) {
        final K vertex = getGraph().addFramedVertex(initializer);
        setLinkOut((VertexFrame) vertex, labels);
        return vertex;
    }
    
    @Override
    public <K> K setLinkOut(final Class<K> kind, final String... labels) {
        return this.setLinkOut(new DefaultClassInitializer<>(kind), labels);
    }

    @Override
    public <K> K setLinkOutExplicit(final ClassInitializer<K> initializer, final String... labels) {
        final K vertex = getGraph().addFramedVertexExplicit(initializer);
        setLinkOut((VertexFrame) vertex, labels);
        return vertex;
    }
    
    @Override
    public <K> K setLinkOutExplicit(final Class<K> kind, final String... labels) {
        return this.setLinkOutExplicit(new DefaultClassInitializer<>(kind), labels);
    }

    @Override
    public <K> K setLinkIn(final ClassInitializer<K> initializer, final String... labels) {
        final K vertex = getGraph().addFramedVertex(initializer);
        setLinkIn((VertexFrame) vertex, labels);
        return vertex;
    }
    
    @Override
    public <K> K setLinkIn(final Class<K> kind, final String... labels) {
        return this.setLinkIn(new DefaultClassInitializer<>(kind), labels);
    }

    @Override
    public <K> K setLinkInExplicit(final ClassInitializer<K> initializer, final String... labels) {
        final K vertex = getGraph().addFramedVertexExplicit(initializer);
        setLinkIn((VertexFrame) vertex, labels);
        return vertex;
    }
    
    @Override
    public <K> K setLinkInExplicit(final Class<K> kind, final String... labels) {
        return this.setLinkInExplicit(new DefaultClassInitializer<>(kind), labels);
    }

    @Override
    public <K> K setLinkBoth(final ClassInitializer<K> initializer, final String... labels) {
        final K vertex = getGraph().addFramedVertex(initializer);
        setLinkBoth((VertexFrame) vertex, labels);
        return vertex;
    }
    
    @Override
    public <K> K setLinkBoth(final Class<K> kind, final String... labels) {
        return this.setLinkBoth(new DefaultClassInitializer<>(kind), labels);
    }

    @Override
    public <K> K setLinkBothExplicit(final ClassInitializer<K> initializer, final String... labels) {
        final K vertex = getGraph().addFramedVertexExplicit(initializer);
        setLinkBoth((VertexFrame) vertex, labels);
        return vertex;
    }
    
    @Override
    public <K> K setLinkBothExplicit(final Class<K> kind, final String... labels) {
        return this.setLinkBothExplicit(new DefaultClassInitializer<>(kind), labels);
    }

    private Object getPropertySupportingMultiproperty(final String name) {
        final Iterator<VertexProperty<Object>> propertyIterator = getElement().properties(name);
        List<Object> results = new ArrayList<>();
        while (propertyIterator.hasNext()) {
            Property<Object> property = propertyIterator.next();
            if (property.isPresent()) {
                results.add(property.value());
            }
        }

        if (results.size() == 1) {
            return results.get(0);
        } else {
            return results;
        }
    }

    @Override
    public JsonObject toJson() {
        final JsonObject json = new JsonObject();
        if (getId() instanceof Number)
            json.addProperty("id", getId(Number.class));
        if (getId() instanceof String)
            json.addProperty("id", getId(String.class));
        json.addProperty("elementClass", "vertex");
        for (final String key : getPropertyKeys()) {
            Object value = getPropertySupportingMultiproperty(key);
            if (value instanceof Number)
                json.addProperty(key, (Number) value);
            else if(value instanceof Boolean)
                json.addProperty(key, (Boolean) value);
            else if(value instanceof Character)
                json.addProperty(key, (Character) value);
            else if (value instanceof List) {
                JsonArray jsonArray = new JsonArray();
                ((List) value).forEach(item -> {
                    if (item instanceof Number)
                        jsonArray.add((Number) item);
                    else if(item instanceof Boolean)
                        jsonArray.add((Boolean) item);
                    else if(item instanceof Character)
                        jsonArray.add((Character) item);
                    else
                        jsonArray.add(item.toString());
                });
                json.add(key, jsonArray);
            } else
                json.addProperty(key, value.toString());
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
    public <T extends Traversable<?, ?>> T traverse(final Function<GraphTraversal<Vertex, Vertex>, GraphTraversal<?, ?>> traverser) {
        return this.getGraph().traverse(input -> traverser.apply(input.V(getElement().id())));
    }

    @Override
    public GraphTraversal<? extends Vertex, ? extends Vertex> getRawTraversal() {
        return this.getGraph().getRawTraversal().V(getElement().id());
    }
}

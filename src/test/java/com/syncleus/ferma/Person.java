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
package com.syncleus.ferma;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class Person extends AbstractVertexFrame {
    static final ClassInitializer<Person> DEFAULT_INITIALIZER = new DefaultClassInitializer(Person.class);

    public String getName() {
        return getProperty("name");
    }

    public void setName(final String name) {
        setProperty("name", name);
    }

    public Iterator<? extends Person> getKnows() {

        return this.<Person>traverse(new Function<GraphTraversal<? extends Vertex, ? extends Vertex>, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversal<? extends Vertex, ? extends Vertex> input) {
                return input.out("knows");
            }
        }, Person.class, false).iterator();
    }

    public Iterator<? extends Person> getKnowsExplicit() {
        return this.<Person>traverseExplicit(new Function<GraphTraversal<? extends Vertex, ? extends Vertex>, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversal<? extends Vertex, ? extends Vertex> input) {
                return input.out("knows");
            }
        }, Person.class, false).iterator();
    }

    public List<? extends Knows> getKnowsList() {
        final Iterator<? extends Knows> edges = this.traverse(new Function<GraphTraversal<? extends Vertex, ? extends Vertex>, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversal<? extends Vertex, ? extends Vertex> input) {
                return input.outE("knows");
            }
        }, Knows.class, false).iterator();

        return Lists.<Knows>newArrayList(edges);
    }

    public List<? extends Knows> getKnowsListExplicit() {
        final Iterator<? extends Knows> edges = this.traverseExplicit(new Function<GraphTraversal<? extends Vertex, ? extends Vertex>, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversal<? extends Vertex, ? extends Vertex> input) {
                return input.outE("knows");
            }
        }, Knows.class, false).iterator();

        return Lists.<Knows>newArrayList(edges);
    }

    public List<? extends Person> getKnowsCollectionVertices() {
        return Lists.<Person>newArrayList(getKnows());
    }

    public List<? extends Person> getKnowsCollectionVerticesExplicit() {
        return Lists.<Person>newArrayList(getKnowsExplicit());
    }

    public Person getFirst() {
        return this.<Person>traverseSingleton(new Function<GraphTraversal<? extends Vertex, ? extends Vertex>, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversal<? extends Vertex, ? extends Vertex> input) {
                return input.out("knows");
            }
        }, Person.class, false);
    }

    public Person getFirstExplicit() {
        return this.<Person>traverseSingletonExplicit(new Function<GraphTraversal<? extends Vertex, ? extends Vertex>, Iterator<? extends Element>>() {
            @Nullable
            @Override
            public Iterator<? extends Element> apply(@Nullable GraphTraversal<? extends Vertex, ? extends Vertex> input) {
                return input.out("knows");
            }
        }, Person.class, false);
    }

    public Knows addKnows(final Person friend) {
        return addFramedEdge("knows", friend, Knows.DEFAULT_INITIALIZER);
    }

    public Knows addKnowsExplicit(final Person friend) {
        return addFramedEdgeExplicit("knows", friend, Knows.DEFAULT_INITIALIZER);
    }

}

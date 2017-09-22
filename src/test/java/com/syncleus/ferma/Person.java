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

import java.util.function.Function;
import com.google.common.collect.Lists;
import com.syncleus.ferma.annotations.GraphElement;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

@GraphElement
public class Person extends AbstractVertexFrame {
    static final ClassInitializer<Person> DEFAULT_INITIALIZER = new DefaultClassInitializer(Person.class);

    public String getName() {
        return getProperty("name");
    }

    public void setName(final String name) {
        setProperty("name", name);
    }

    public Iterator<? extends Person> getKnows() {
        return this.traverse(input -> input.out("knows")).frame(Person.class);
    }

    public Iterator<? extends Person> getKnowsExplicit() {
        return this.traverse(input -> input.out("knows")).frameExplicit(Person.class);
    }

    public List<? extends Knows> getKnowsList() {
        return this.traverse(input -> input.outE("knows")).toList(Knows.class);
    }

    public List<? extends Knows> getKnowsListExplicit() {
        return this.traverse(input -> input.outE("knows")).toListExplicit(Knows.class);
    }

    public List<? extends Person> getKnowsCollectionVertices() {
        return Lists.<Person>newArrayList(getKnows());
    }

    public List<? extends Person> getKnowsCollectionVerticesExplicit() {
        return Lists.<Person>newArrayList(getKnowsExplicit());
    }

    public Person getFirst() {
        return this.traverse(input -> input.out("knows")).next(Person.class);
    }

    public Person getFirstExplicit() {
        return this.traverse(input -> input.out("knows")).nextExplicit(Person.class);
    }

    public Knows addKnows(final Person friend) {
        return addFramedEdge("knows", friend, Knows.DEFAULT_INITIALIZER);
    }

    public Knows addKnowsExplicit(final Person friend) {
        return addFramedEdgeExplicit("knows", friend, Knows.DEFAULT_INITIALIZER);
    }
}

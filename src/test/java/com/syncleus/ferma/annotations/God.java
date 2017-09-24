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
package com.syncleus.ferma.annotations;

import com.syncleus.ferma.*;
import org.apache.tinkerpop.gremlin.structure.Direction;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@GraphElement
public interface God extends VertexFrame {
    static final ClassInitializer<God> DEFAULT_INITIALIZER = new DefaultClassInitializer(God.class);

    @Property("name")
    String getName();

    @Property(value = "name", operation = Property.Operation.GET)
    String obtainName();

    @Property("name")
    void setName(String newName);

    @Property(value = "name", operation = Property.Operation.SET)
    void applyName(String newName);

    @Property("name")
    void removeName();

    @Property(value = "name", operation = Property.Operation.REMOVE)
    void deleteName();

    @Property("age")
    Integer getAge();

    @Property("type")
    String getType();

    @Adjacency(label = "father", direction = Direction.IN)
    Iterator<? extends God> getSons();

    @Adjacency(label = "father", direction = Direction.IN)
    List<? extends God> getSonsList();

    @Adjacency(label = "father", direction = Direction.IN)
    Set<? extends God> getSonsSet();

    @Adjacency(label = "father", direction = Direction.IN, operation = Adjacency.Operation.GET)
    Iterator<? extends God> obtainSons();

    @Adjacency(label = "father", direction = Direction.IN)
    God getSon();

    @Adjacency(label = "father", direction = Direction.IN)
    <N extends God> Iterator<? extends N> getSons(Class<? extends N> type);

    @Adjacency(label = "father", direction = Direction.IN)
    <N extends God> List<? extends N> getSonsList(Class<? extends N> type);

    @Adjacency(label = "father", direction = Direction.IN)
    <N extends God> Set<? extends N> getSonsSet(Class<? extends N> type);

    @Adjacency(label = "father", direction = Direction.OUT)
    <N extends God> Iterator<? extends N> getParents();

    @Adjacency(label = "father", direction = Direction.IN)
    <N extends God> N getSon(Class<? extends N> type);

    @Adjacency(label = "father", direction = Direction.IN)
    <N extends God> N addSon(ClassInitializer<? extends N> type);

    @Adjacency(label = "father", direction = Direction.IN, operation = Adjacency.Operation.ADD)
    <N extends God> N includeSon(ClassInitializer<? extends N> type);

    @Adjacency(label = "father", direction = Direction.IN)
    <N extends God> N addSon(ClassInitializer<? extends N> type, ClassInitializer<? extends FatherEdge> edge);

    @Adjacency(label = "father", direction = Direction.IN)
    God addSon(God son);

    @Adjacency(label = "father", direction = Direction.IN)
    VertexFrame addSon();

    @Adjacency(label = "father", direction = Direction.IN)
    God addSon(God son, ClassInitializer<? extends FatherEdge> edge);

    @Adjacency(label = "father", direction = Direction.IN)
    void setSons(Iterator<? extends God> vertexSet);

    @Adjacency(label = "father", direction = Direction.IN)
    void setSon(God vertex);

    @Adjacency(label = "father", direction = Direction.IN)
    void setSonsList(List<? extends God> vertexList);

    @Adjacency(label = "father", direction = Direction.IN, operation = Adjacency.Operation.SET)
    void applySons(Iterator<? extends God> vertexSet);

    @Adjacency(label = "father", direction = Direction.IN)
    void removeSon(God son);

    @Adjacency(label = "father", direction = Direction.IN, operation = Adjacency.Operation.REMOVE)
    void deleteSon(God son);

    @Incidence(label = "father", direction = Direction.IN)
    Iterator<? extends EdgeFrame> getSonEdges();

    @Incidence(label = "father", direction = Direction.IN)
    List<? extends EdgeFrame> getSonEdgesList();

    @Incidence(label = "father", direction = Direction.IN)
    Set<? extends EdgeFrame> getSonEdgesSet();

    @Incidence(label = "father", direction = Direction.IN)
    <N extends FatherEdge> Iterator<? extends N> getSonEdges(Class<? extends N> type);

    @Incidence(label = "father", direction = Direction.IN)
    <N extends FatherEdge> List<? extends N> getSonEdgesList(Class<? extends N> type);

    @Incidence(label = "father", direction = Direction.IN)
    <N extends FatherEdge> Set<? extends N> getSonEdgesSet(Class<? extends N> type);

    @Incidence(label = "father", direction = Direction.IN)
    EdgeFrame getSonEdge();

    @Incidence(label = "father", direction = Direction.IN)
    <N extends FatherEdge> N getSonEdge(Class<? extends N> type);

    @Incidence(label = "father", direction = Direction.IN)
    void removeSonEdge(FatherEdge edge);

    @Adjacency(label = "father", direction = Direction.IN)
    void removeEverySon();

    @Incidence(label = "father", direction = Direction.IN, operation = Incidence.Operation.GET)
    <N extends FatherEdge> Iterator<? extends N> obtainSonEdges(Class<? extends N> type);

    @Incidence(label = "father", direction = Direction.IN, operation = Incidence.Operation.REMOVE)
    void deleteSonEdge(FatherEdge edge);

    @Incidence(label = "father", direction = Direction.IN, operation = Incidence.Operation.ADD)
    void includeSonEdge(God son, ClassInitializer<? extends FatherEdge> type);
}

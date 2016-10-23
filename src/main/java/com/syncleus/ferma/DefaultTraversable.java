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
import com.google.common.collect.Sets;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Element;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class DefaultTraversable<PE, E> implements Traversable<PE, E>{
    final private GraphTraversal<PE, E> baseTraversal;
    final private FramedGraph parentGraph;

    @Override
    public GraphTraversal<PE, E> getRawTraversal() {
        return this.baseTraversal;
    }

    public DefaultTraversable(final GraphTraversal<PE, E> baseTraversal, final FramedGraph parentGraph) {
        this.baseTraversal = baseTraversal;
        this.parentGraph = parentGraph;
    }

    @Override
    public <T extends Traversable<?, ?>> T traverse(Function<GraphTraversal<PE, E>, GraphTraversal<?, ?>> traverser) {
        GraphTraversal<?,?> result = traverser.apply(this.baseTraversal);
        return (T) new DefaultTraversable(result, this.parentGraph);
    }

    @Override
    public <N> N next(Class<N> kind) {
        final E nextObject = baseTraversal.next();
        return this.parentGraph.frameElement((Element) nextObject, kind);
    }

    @Override
    public <N> N nextExplicit(Class<N> kind) {
        final E nextObject = baseTraversal.next();
        return this.parentGraph.frameElementExplicit((Element) nextObject, kind);
    }

    @Override
    public <N> N nextOrDefault(Class<N> kind, N defaultValue) {
        if (baseTraversal.hasNext())
            return next(kind);
        else
            return defaultValue;
    }

    @Override
    public <N> N nextOrDefaultExplicit(Class<N> kind, N defaultValue) {
        if (baseTraversal.hasNext())
            return nextExplicit(kind);
        else
            return defaultValue;
    }

    @Override
    public VertexFrame nextOrAdd() {
        try {
            return this.parentGraph.frameElement((Element) baseTraversal.next(), VertexFrame.class);
        }
        catch (final NoSuchElementException e) {
            return this.parentGraph.addFramedVertex(null, VertexFrame.class);
        }
    }

    @Override
    public <N> N nextOrAddExplicit(ClassInitializer<N> initializer) {
        try {
            return this.parentGraph.frameElementExplicit((Element) baseTraversal.next(), initializer.getInitializationType());
        }
        catch (final NoSuchElementException e) {
            return this.parentGraph.addFramedVertexExplicit(initializer);
        }
    }

    @Override
    public <N> N nextOrAddExplicit(Class<N> kind) {
        try {
            return this.parentGraph.frameElementExplicit((Element) baseTraversal.next(), kind);
        }
        catch (final NoSuchElementException e) {
            return this.parentGraph.addFramedVertexExplicit(kind);
        }
    }

    @Override
    public <N> N nextOrAdd(ClassInitializer<N> initializer) {
        try {
            return this.parentGraph.frameElement((Element) baseTraversal.next(), initializer.getInitializationType());
        }
        catch (final NoSuchElementException e) {
            return this.parentGraph.addFramedVertex(initializer);
        }
    }

    @Override
    public <N> N nextOrAdd(Class<N> kind) {
        try {
            return this.parentGraph.frameElement((Element) baseTraversal.next(), kind);
        }
        catch (final NoSuchElementException e) {
            return this.parentGraph.addFramedVertex(kind);
        }
    }

    @Override
    public <N> List<? extends N> next(int amount, Class<N> kind) {
        return Lists.transform((List<Element>) this.baseTraversal.next(amount), new Function<Element, N>() {
            @Override
            public N apply(final Element input) {
                return parentGraph.frameElement(input, kind);
            }
        });
    }

    @Override
    public <N> List<? extends N> nextExplicit(int amount, Class<N> kind) {
        return Lists.transform((List<Element>) this.baseTraversal.next(amount), new Function<Element, N>() {
            @Override
            public N apply(final Element input) {
                return parentGraph.frameElementExplicit(input, kind);
            }
        });
    }

    @Override
    public <N> Iterator<N> frame(Class<N> kind) {
        return new Iterator<N>() {
            @Override
            public boolean hasNext() {
                return baseTraversal.hasNext();
            }

            @Override
            public N next() {
                return parentGraph.frameElement((Element) baseTraversal.next(), kind);
            }
        };
    }

    @Override
    public <N> Iterator<? extends N> frameExplicit(Class<N> kind) {
        return new Iterator<N>() {
            @Override
            public boolean hasNext() {
                return baseTraversal.hasNext();
            }

            @Override
            public N next() {
                return parentGraph.frameElementExplicit((Element) baseTraversal.next(), kind);
            }
        };
    }

    @Override
    public <N> List<? extends N> toList(Class<N> kind) {
        return Lists.transform((List<Element>) this.baseTraversal.toList(), new Function<Element, N>() {
            @Override
            public N apply(final Element input) {
                return parentGraph.frameElement(input, kind);
            }
        });
    }

    @Override
    public <N> List<? extends N> toListExplicit(Class<N> kind) {
        return Lists.transform((List<Element>) this.baseTraversal.toList(), new Function<Element, N>() {
            @Override
            public N apply(final Element input) {
                return parentGraph.frameElementExplicit(input, kind);
            }
        });
    }

    @Override
    public <N> Set<? extends N> toSet(Class<N> kind) {
        return Sets.newHashSet(toList(kind));
    }

    @Override
    public <N> Set<? extends N> toSetExplicit(Class<N> kind) {
        return Sets.newHashSet(toListExplicit(kind));
    }
}

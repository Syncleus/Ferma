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

import java.util.*;
import java.util.function.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Element;

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
    public <T extends Traversable<?, ?>> T traverse(final Function<GraphTraversal<PE, E>, GraphTraversal<?, ?>> traverser) {
        final GraphTraversal<?,?> result = traverser.apply(this.baseTraversal);
        return (T) new DefaultTraversable(result, this.parentGraph);
    }

    @Override
    public <N> N next(final Class<N> kind) {
        final E nextObject = baseTraversal.next();
        return this.parentGraph.frameElement((Element) nextObject, kind);
    }

    @Override
    public <N> N nextExplicit(final Class<N> kind) {
        final E nextObject = baseTraversal.next();
        return this.parentGraph.frameElementExplicit((Element) nextObject, kind);
    }

    @Override
    public <N> N nextOrDefault(final Class<N> kind, final N defaultValue) {
        try {
            if (baseTraversal.hasNext())
                return next(kind);
            else
                return defaultValue;
        } catch (IllegalAccessError e) {
            return defaultValue;
        }
    }

    @Override
    public <N> N nextOrDefaultExplicit(final Class<N> kind, final N defaultValue) {
        try {
            if (baseTraversal.hasNext())
                return nextExplicit(kind);
            else
                return defaultValue;
        } catch (IllegalAccessError e) {
            return defaultValue;
        }
    }

    @Override
    public VertexFrame nextOrAdd() {
        try {
            return this.parentGraph.frameElement((Element) baseTraversal.next(), VertexFrame.class);
        }
        catch (final NoSuchElementException e) {
            return this.parentGraph.addFramedVertex(TVertex.DEFAULT_INITIALIZER, null);
        }
    }

    @Override
    public <N> N nextOrAddExplicit(final ClassInitializer<N> initializer) {
        try {
            return this.parentGraph.frameElementExplicit((Element) baseTraversal.next(), initializer.getInitializationType());
        }
        catch (final NoSuchElementException e) {
            return this.parentGraph.addFramedVertexExplicit(initializer);
        }
    }

    @Override
    public <N> N nextOrAddExplicit(final Class<N> kind) {
        try {
            return this.parentGraph.frameElementExplicit((Element) baseTraversal.next(), kind);
        }
        catch (final NoSuchElementException e) {
            return this.parentGraph.addFramedVertexExplicit(kind);
        }
    }

    @Override
    public <N> N nextOrAdd(final ClassInitializer<N> initializer) {
        try {
            return this.parentGraph.frameElement((Element) baseTraversal.next(), initializer.getInitializationType());
        }
        catch (final NoSuchElementException e) {
            return this.parentGraph.addFramedVertex(initializer);
        }
    }

    @Override
    public <N> N nextOrAdd(final Class<N> kind) {
        try {
            return this.parentGraph.frameElement((Element) baseTraversal.next(), kind);
        }
        catch (final NoSuchElementException e) {
            return this.parentGraph.addFramedVertex(kind);
        }
    }

    @Override
    public <N> List<? extends N> next(final int amount, final Class<N> kind) {
        try {
            return Lists.transform((List<Element>) this.baseTraversal.next(amount), new com.google.common.base.Function<Element, N>() {
                @Override
                public N apply(final Element input) {
                    return parentGraph.frameElement(input, kind);
                }
            });
        } catch (IllegalAccessError e) {
            return Collections.emptyList();
        }
    }

    @Override
    public <N> List<? extends N> nextExplicit(final int amount, final Class<N> kind) {
        try {
            return Lists.transform((List<Element>) this.baseTraversal.next(amount), new com.google.common.base.Function<Element, N>() {
                @Override
                public N apply(final Element input) {
                    return parentGraph.frameElementExplicit(input, kind);
                }
            });
        } catch (IllegalAccessError e) {
            return Collections.emptyList();
        }
    }

    @Override
    public <N> Iterator<N> frame(final Class<N> kind) {
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
    public <N> Iterator<? extends N> frameExplicit(final Class<N> kind) {
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
    public <N> List<? extends N> toList(final Class<N> kind) {
        try {
            return Lists.transform((List<Element>) this.baseTraversal.toList(), new com.google.common.base.Function<Element, N>() {
                @Override
                public N apply(final Element input) {
                    return parentGraph.frameElement(input, kind);
                }
            });
        } catch (IllegalAccessError e) {
            return Collections.emptyList();
        }
    }

    @Override
    public <N> List<? extends N> toListExplicit(final Class<N> kind) {
        try {
            return Lists.transform((List<Element>) this.baseTraversal.toList(), new com.google.common.base.Function<Element, N>() {
                @Override
                public N apply(final Element input) {
                    return parentGraph.frameElementExplicit(input, kind);
                }
            });
        } catch (IllegalAccessError e) {
            return Collections.emptyList();
        }
    }

    @Override
    public <N> Set<? extends N> toSet(final Class<N> kind) {
        return Sets.newHashSet(toList(kind));
    }

    @Override
    public <N> Set<? extends N> toSetExplicit(final Class<N> kind) {
        return Sets.newHashSet(toListExplicit(kind));
    }
}

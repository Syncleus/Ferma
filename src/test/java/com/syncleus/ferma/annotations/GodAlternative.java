package com.syncleus.ferma.annotations;

import com.tinkerpop.blueprints.Direction;

public interface GodAlternative {
    @Adjacency(label="father", direction= Direction.IN)
    <N extends God> Iterable<? extends N> getSons(Class<? extends N> type);

    @Adjacency(label="father", direction= Direction.IN)
    <N extends God> N getSon(Class<? extends N> type);

    @Adjacency(label="father", direction= Direction.IN)
    <N extends God> N addSon(Class<? extends N> type);

    @Adjacency(label="")
    <N extends God> Iterable<? extends N> getNoLabel(Class<? extends N> type);
}

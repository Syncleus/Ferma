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
package com.syncleus.ferma.tx;

import org.apache.tinkerpop.gremlin.structure.Graph;

import com.syncleus.ferma.DelegatingFramedGraph;

public class DummyGraph extends DelegatingFramedGraph<Graph> implements FramedTxGraph {

    private int totalTxCreated = 0;

    public DummyGraph(Graph delegate) {
        super(delegate);
    }
    
    @Override
    public Tx tx() {
        return FramedTxGraph.super.tx();
    }

    @Override
    public Tx createTx() {
        Tx created = new DummyTransaction(null, this);
        totalTxCreated += 1;
        return created;
    }

    public int getTotalTxCreated() {
        return totalTxCreated;
    }
    
}

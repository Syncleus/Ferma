/******************************************************************************
 *                                                                             *
 *  Copyright: (c) Syncleus, Inc.                                              *
 *                                                                             *
 *  You may redistribute and modify this source code under the terms and       *
 *  conditions of the Open Source Community License - Type C version 1.0       *
 *  or any later version as published by Syncleus, Inc. at www.syncleus.com.   *
 *  There should be a copy of the license included with this file. If a copy   *
 *  of the license is not included you are granted no right to distribute or   *
 *  otherwise use this file except through a legal and valid license. You      *
 *  should also contact Syncleus, Inc. at the information below if you cannot  *
 *  find a license:                                                            *
 *                                                                             *
 *  Syncleus, Inc.                                                             *
 *  2604 South 12th Street                                                     *
 *  Philadelphia, PA 19148                                                     *
 *                                                                             *
 ******************************************************************************/
package com.syncleus.ferma.annotations;

import com.syncleus.ferma.typeresolvers.TypeResolver;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.ElementHelper;

/**
 * Example Graph factory that creates a graph based on roman mythology.
 */
public class GodGraphLoader {
    protected final static String TYPE_RESOLUTION_KEY = "ferma_type";

    public static void load(final Graph graph) {

        // vertices

        final Vertex saturn = graph.addVertex(null);
        saturn.setProperty("name", "saturn");
        saturn.setProperty("age", 10000);
        saturn.setProperty("type", "titan");
        saturn.setProperty(TYPE_RESOLUTION_KEY, God.class.getName());

        final Vertex sky = graph.addVertex(null);
        ElementHelper.setProperties(sky, "name", "sky", "type", "location", "other", "more useless info");

        final Vertex sea = graph.addVertex(null);
        ElementHelper.setProperties(sea, "name", "sea", "type", "location");

        final Vertex jupiter = graph.addVertex(null);
        ElementHelper.setProperties(jupiter, "name", "jupiter", "age", 5000, "type", "god", TYPE_RESOLUTION_KEY, God.class.getName());

        final Vertex neptune = graph.addVertex(null);
        ElementHelper.setProperties(neptune, "name", "neptune", "age", 4500, "type", "god", TYPE_RESOLUTION_KEY, God.class.getName());

        final Vertex hercules = graph.addVertex(null);
        ElementHelper.setProperties(hercules, "name", "hercules", "age", 30, "type", "demigod", TYPE_RESOLUTION_KEY, GodExtended.class.getName());

        final Vertex alcmene = graph.addVertex(null);
        ElementHelper.setProperties(alcmene, "name", "alcmene", "age", 45, "type", "human", TYPE_RESOLUTION_KEY, God.class.getName());

        final Vertex pluto = graph.addVertex(null);
        ElementHelper.setProperties(pluto, "name", "pluto", "age", 4000, "type", "god", TYPE_RESOLUTION_KEY, God.class.getName());

        final Vertex nemean = graph.addVertex(null);
        ElementHelper.setProperties(nemean, "name", "nemean", "type", "monster", TYPE_RESOLUTION_KEY, God.class.getName());

        final Vertex hydra = graph.addVertex(null);
        ElementHelper.setProperties(hydra, "name", "hydra", "type", "monster", TYPE_RESOLUTION_KEY, God.class.getName());

        final Vertex cerberus = graph.addVertex(null);
        ElementHelper.setProperties(cerberus, "name", "cerberus", "type", "monster", TYPE_RESOLUTION_KEY, God.class.getName());

        final Vertex tartarus = graph.addVertex(null);
        ElementHelper.setProperties(tartarus, "name", "tartarus", "type", "location", TYPE_RESOLUTION_KEY, God.class.getName());

        // edges

        ElementHelper.setProperties(jupiter.addEdge("father", saturn), TYPE_RESOLUTION_KEY, FatherEdge.class.getName());
        jupiter.addEdge("lives", sky).setProperty("reason", "loves fresh breezes");
        jupiter.addEdge("brother", neptune);
        jupiter.addEdge("brother", pluto);

        ElementHelper.setProperties(neptune.addEdge("father", saturn), TYPE_RESOLUTION_KEY, FatherEdge.class.getName());
        neptune.addEdge("lives", sea).setProperty("reason", "loves waves");
        neptune.addEdge("brother", jupiter);
        neptune.addEdge("brother", pluto);

        ElementHelper.setProperties(hercules.addEdge("father", jupiter), TYPE_RESOLUTION_KEY, FatherEdgeExtended.class.getName());
        hercules.addEdge("lives", sky).setProperty("reason", "loves heights");
        ElementHelper.setProperties(hercules.addEdge("battled", nemean), "time", 1);
        ElementHelper.setProperties(hercules.addEdge("battled", hydra), "time", 2);
        ElementHelper.setProperties(hercules.addEdge("battled", cerberus), "time", 12);

        ElementHelper.setProperties(pluto.addEdge("father", saturn), TYPE_RESOLUTION_KEY, FatherEdge.class.getName());
        pluto.addEdge("brother", jupiter);
        pluto.addEdge("brother", neptune);
        pluto.addEdge("lives", tartarus).setProperty("reason", "no fear of death");
        pluto.addEdge("pet", cerberus);

        cerberus.addEdge("lives", tartarus);
        ElementHelper.setProperties(cerberus.addEdge("battled", alcmene), "time", 5);

        // commit the transaction to disk
        if (graph instanceof TransactionalGraph)
            ((TransactionalGraph) graph).commit();
    }
}

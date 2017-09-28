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
package com.syncleus.ferma.graphtypes.filesystem;

import com.syncleus.ferma.DelegatingFramedGraph;
import com.syncleus.ferma.FramedGraph;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

/**
 *
 * @author rqpa
 */
public class FileSystemGraphLoader {
    
    private static final Set<Class<?>> FILE_SYSTEM_TYPES = new HashSet<>(Arrays.asList(FileVertex.class, DirectoryVertex.class, ParentEdge.class));
    public static final FileSystemGraphLoader INSTANCE = new FileSystemGraphLoader();
    public static final String USER_NAME = "some_user";
    protected final static String TYPE_RESOLUTION_KEY = "ferma_type";
    
    protected FileSystemGraphLoader() {
        // Singleton
    }
    
    public FramedGraph load() {
        Graph graph = TinkerGraph.open();
        final FramedGraph framedGraph = new DelegatingFramedGraph(graph, true, FILE_SYSTEM_TYPES);
        // Vertices
        final DirectoryVertex root = makeDirectory(framedGraph, "");
        final DirectoryVertex home = makeSubDirectory(framedGraph, root, "home");
        makeSubDirectory(framedGraph, root, "etc");
        final DirectoryVertex userDir = makeSubDirectory(framedGraph, home, USER_NAME);
        makeSubFile(framedGraph, userDir, "adress_book.xlsx");
        makeSubFile(framedGraph, userDir, "notes.txt");
        
        return framedGraph;
    }
    
    private FileVertex makeSubFile(final FramedGraph graph, DirectoryVertex parent, String fileName) {
        FileVertex file = makeFile(graph, fileName);
        setOwner(parent, file);
        return file;
    }
    
    private DirectoryVertex makeSubDirectory(final FramedGraph graph, DirectoryVertex parent, String subDirName) {
        DirectoryVertex subDir = makeDirectory(graph, subDirName);
        setOwner(parent, subDir);
        return subDir;
    }
    
    private ParentEdge setOwner(DirectoryVertex parent, FileVertex child) {
        return child.addFramedEdge("parent", parent, ParentEdge.class);
    }
    
    private DirectoryVertex makeDirectory(final FramedGraph graph, String name) {
        return makeFileSystemNode(graph, name, DirectoryVertex.class);
    }
    
    private FileVertex makeFile(final FramedGraph graph, String name) {
        return makeFileSystemNode(graph, name, FileVertex.class);
    }
    
    private <VertexType extends FileVertex> VertexType makeFileSystemNode(
            final FramedGraph graph, 
            final String name, 
            final Class<VertexType> type) {
        
        final VertexType vertex = graph.addFramedVertex(type);
        vertex.setName(name);
        return vertex;
    }
}

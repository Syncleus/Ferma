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

import com.syncleus.ferma.graphtypes.filesystem.ParentEdge;
import com.syncleus.ferma.graphtypes.filesystem.FileSystemGraphLoader;
import com.syncleus.ferma.graphtypes.filesystem.FileVertex;
import com.syncleus.ferma.graphtypes.filesystem.DirectoryVertex;
import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.TEdge;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author rqpa
 */
public class IncidenceHandlerWithFileVerticesTest {

    private static final String NEW_USER_NAME = "newUser";
    private static final String ADDR_BOOK_FILE_NAME = "address_book.xlsx";

    @Test
    public void testAddFileParentEdgeDefault() {

        final FramedGraph framedGraph = FileSystemGraphLoader.INSTANCE.load();
        final DirectoryVertex home = findVertex(framedGraph, "name", "home", DirectoryVertex.class);
        final TEdge newUserTEdge = home.createSubDirParentEdge();
        final ParentEdge newUserEdge = newUserTEdge.reframe(ParentEdge.class);
        final FileVertex userDir = newUserEdge.getChild();
        userDir.setName(NEW_USER_NAME);
        Assert.assertEquals(1, userDir.getParents().size());
        Assert.assertEquals("home", userDir.getParents().get(0).getName());
    }

    @Test
    public void testAddItemEdgeTyped() {
        final FramedGraph framedGraph = FileSystemGraphLoader.INSTANCE.load();
        final DirectoryVertex home = findVertex(framedGraph, "name", "home", DirectoryVertex.class);
        final TEdge newUserDirTEdge = home.addItemEdge(DirectoryVertex.DEFAULT_INITIALIZER);
        final DirectoryVertex newUserDir = (DirectoryVertex) newUserDirTEdge.reframe(ParentEdge.class).getChild();
        newUserDir.setName(NEW_USER_NAME);
        final TEdge newUserAddrBookTEdge = newUserDir.addItemEdge(FileVertex.DEFAULT_INITIALIZER);
        final FileVertex newUserAddrBook = newUserAddrBookTEdge.reframe(ParentEdge.class).getChild();
        newUserAddrBook.setName(ADDR_BOOK_FILE_NAME);

        assertNewUserAndAddrBook(framedGraph);
    }

    @Test
    public void testAddItemParentEdgeTypedTyped() {
        final FramedGraph framedGraph = FileSystemGraphLoader.INSTANCE.load();
        final DirectoryVertex home = findVertex(framedGraph, "name", "home", DirectoryVertex.class);
        final ParentEdge newUserDirParentEdge = home.addItemEdge(DirectoryVertex.DEFAULT_INITIALIZER, ParentEdge.DEFAULT_INITIALIZER);
        final DirectoryVertex newUserDir = (DirectoryVertex) newUserDirParentEdge.getChild();
        newUserDir.setName(NEW_USER_NAME);
        final ParentEdge newUserAddrBookParentEdge = newUserDir.addItemEdge(FileVertex.DEFAULT_INITIALIZER, ParentEdge.DEFAULT_INITIALIZER);
        final FileVertex newUserAddrBook = newUserAddrBookParentEdge.getChild();
        newUserAddrBook.setName(ADDR_BOOK_FILE_NAME);

        assertNewUserAndAddrBook(framedGraph);
    }

    @Test
    public void testSetChildItemEdgeByObjectUntypedEdge() {
        final FramedGraph framedGraph = FileSystemGraphLoader.INSTANCE.load();
        final DirectoryVertex home = findVertex(framedGraph, "name", "home", DirectoryVertex.class);
        final ParentEdge newUserDirParentEdge = home.addItemEdge(DirectoryVertex.DEFAULT_INITIALIZER, ParentEdge.DEFAULT_INITIALIZER);
        final DirectoryVertex newUserDir = (DirectoryVertex) newUserDirParentEdge.getChild();
        newUserDir.setName(NEW_USER_NAME);
        final FileVertex newUserAddrBook = framedGraph.addFramedVertex(FileVertex.class);
        newUserAddrBook.setName(ADDR_BOOK_FILE_NAME);
        newUserDir.addChild(newUserAddrBook);
        assertNewUserAndAddrBook(framedGraph);
    }

    @Test
    public void testGetParentsGetEdgesByType() {
        final FramedGraph framedGraph = FileSystemGraphLoader.INSTANCE.load();
        final DirectoryVertex defaultUserHome = findVertex(framedGraph, "name", FileSystemGraphLoader.USER_NAME, DirectoryVertex.class);
        Iterator<ParentEdge> userHomeParentEdgesIterator = defaultUserHome.getOutEdges(ParentEdge.class);
        Assert.assertTrue(userHomeParentEdgesIterator.hasNext());
        final FileVertex homeDir = userHomeParentEdgesIterator.next().getParent();
        Assert.assertEquals("home", homeDir.getName());
        Assert.assertFalse(userHomeParentEdgesIterator.hasNext());
    }

    private void assertNewUserAndAddrBook(FramedGraph framedGraph) {
        final DirectoryVertex newUserDirFound = findVertex(framedGraph, "name", NEW_USER_NAME, DirectoryVertex.class);
        final FileVertex newUserAddrBookFound = findVertex(framedGraph, "name", ADDR_BOOK_FILE_NAME, FileVertex.class);

        Assert.assertEquals(1, newUserDirFound.getParents().size());
        Assert.assertEquals("home", newUserDirFound.getParents().get(0).getName());
        Assert.assertEquals(1, newUserDirFound.getItems().size());
        Assert.assertEquals(ADDR_BOOK_FILE_NAME, newUserAddrBookFound.getName());
        Assert.assertEquals(newUserAddrBookFound.getName(), newUserDirFound.getItems().get(0).getName());
    }

    private <VertexType, PropertyType> VertexType findVertex(FramedGraph g, String propName, PropertyType propValue, Class<VertexType> itemType) {
        List<? extends VertexType> items = g.traverse(
                input -> input.V().has(propName, propValue)).toList(itemType);

        return items.isEmpty() ? null : items.get(0);
    }
}

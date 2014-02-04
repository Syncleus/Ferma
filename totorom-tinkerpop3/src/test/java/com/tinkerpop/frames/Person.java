package com.tinkerpop.frames;

import com.tinkerpop.blueprints.Vertex;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author Bryn Cooke (http://jglue.org)
 */

public class Person extends FramedVertex {




    public String getName() {
        return getProperty("name");
    }

    public void setName(String name) {
        setProperty("name", name);
    }

    public Iterator<Person> getKnows() {

        return out("knows").frame(Person.class);
    }

    public List<Person> getKnowsCollection() {
        return out("knows").toList(Person.class);
    }


    public Person getFirst() {
        return out("knows").next(Person.class);
    }

    public Knows addKnows(Person friend) {
        return addEdge("knows", friend, Knows.class);
    }


}

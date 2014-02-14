package org.jglue.totorom;

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

    public List<Knows> getKnowsList() {
        return outE("knows").toList(Knows.class);
    }
    
    public List<Person> getKnowsCollectionVertices() {
        return out("knows").toList(Person.class);
    }


    public Person getFirst() {
        return out("knows").next(Person.class);
    }

    public Knows addKnows(Person friend) {
        return addEdge("knows", friend, Knows.class);
    }


}

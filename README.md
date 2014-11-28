# Ferma

An ORM / OGM for the TinkerPop graph stack.

The Ferma project has been created as an alternative to the TinkerPop Frames project. Redesigned for performance and
additional features. Unlike with Frames, annotated classes in Ferma have their abstract methods implemented using code
generation during start-up with Byte Buddy, avoiding the need for proxy classes. This in turn significantly
improves performance when compared with TinkerPop Frames. Ferma offers several new annotated method types in
addition to those provided by TinkerPop Frames. Ferma is designed to easily replace TinkerPop Frames in existing code,
as such, the annotations provided by Ferma are a super-set of those provided by TinkerPop Frames.

Ferma is built directly on TinkerPop Blueprints with no dependency on TinkerPop Frames. This ensures all the TinkerPop
features and compatabilies are preserved, but with a high-performance drop-in replacement for Frames. The TinkerPop
suite provides several tools which can be used to work with the Ferma engine.

* **Furnace** - Graph analysis utilities
* **Pipes** - A data-flow framework for splitting, merging, filtering, and transforming of data
* **Gremlin** - A graph query language
* **Blueprints** - A standard graph API

Ferma also supports any of the many databases compatible with TinkerPop including the following.

 * [Titan](http://thinkaurelius.github.io/titan/)
 * [Neo4j](http://neo4j.com)
 * [OrientDB](http://www.orientechnologies.com/orientdb/)
 * [MongoDB](http://www.mongodb.org)
 * [Oracle NoSQL](http://www.oracle.com/us/products/database/nosql/overview/index.html)
 * [TinkerGraph](https://github.com/tinkerpop/blueprints/wiki/TinkerGraph)

## Dependency

To include Ferma in your project of choice include the following maven dependency into your build.

    <dependency>
        <groupId>com.syncleus.ferma</groupId>
        <artifactId>ferma</artifactId>
        <version>2.0-SNAPSHOT</version>
    </dependency>

    <repositories>
        <repository>
            <id>syncleus.snapshots</id>
            <name>Syncleus Maven Snapshot Repository</name>
            <url>http://repo.syncleus.com/repository/snapshots/</url>
            <releases>
                <enabled>false</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>
    

## Getting Started

Ferma provides three levels of type resolution: untyped, simple, and annotated. In untyped mode Ferma doesnt handle
typing at all, instead the type must be explicitly indicated whenever querying. In simple mode Ferma provides type
context encoded as graph element properties which ensures the same type comes out that goes in to a graph. In annotated
mode all the features of simple mode are provided as well as enabling the use of annotations on abstract methods to
instruct Ferma to dynamically construct byte-code to implement the abstract methods at start up.

### Untyped Mode Example

In untyped mode there is no automatic typing. Whatever class is explicitly indicated is the type that will be
instantiated when performing queries. Lets start with a simple example domain.

    public class Person extends FramedVertex {
      public String getName() {
        return getProperty("name");
      }

      public void setName(String name) {
        setProperty("name", name);
      }

      public List<Knows> getKnowsList() {
        return outE("knows").toList(Knows.class);
      }

      public List<Person> getFriendsOfFriends() {
        return out("knows").out("knows").except(this).toList(Person.class);
      }

      public Knows addKnows(Person friend) {
        return addEdge("knows", friend, Knows.class);
      }
    }

    public class Knows extends FramedEdge {

      public void setYears(int years) {
        setProperty("years", years);
      }

      public int getYears() {
        return getProperty("years");
      }
    }

And here is how you interact with the framed elements:

    public void testUntyped() {
      Graph g = new TinkerGraph();

      // implies untyped mode
      FramedGraph fg = new FramedGraph(g);

      Person p1 = fg.addFramedVertex(Person.class);
      p1.setName("Jeff");

      Person p2 = fg.addFramedVertex(Person.class);
      p2.setName("Julia");
      Knows knows = p1.addKnows(p2);
      knows.setYears(15);

      Person jeff = fg.v().has("name", "Jeff").next(Person.class);


      Assert.assertEquals("Jeff", jeff.getName());
      Assert.assertEquals(15, jeff.getKnowsList().get(0).getYears());
    }

### Simple Mode Example

In simple mode you must provide concrete classes, no abstract or interfaces allowed, and the class should always extend
from a FramedVertex or FramedEdge. Simple mode doesn't provide any annotations either. The purpose of simple mode is to
provide type resolution. Basically the type of object you use when adding to the graph is the same type you get out when
reading from the graph.

Say we extend the Person class with the Programmer class.
    
    public class Programmer extends Person {
    }
    
Using TypeResolver.SIMPLE will save the type of Java class the element was created with for use later:
    
    public void testSimpleTyping() {
      Graph g = new TinkerGraph();

      // implies simple mode
      FramedGraph fg = new FramedGraph(g, true, false);
      
      Person p1 = fg.addFramedVertex(Programmer.class);
      p1.setName("Jeff");
      
      Person p2 = fg.addFramedVertex(Person.class);
      p2.setName("Julia");
      
      Person jeff = fg.v().has("name", "Jeff").next(Person.class);
      Person julia = fg.v().has("name", "Julia").next(Person.class);
      
      Assert.assertEquals(Programmer.class, jeff.getClass());
      Assert.assertEquals(Person.class, julia.getClass());
    }

### Annotated Mode Example

In annotated mode you can either provide concrete classes, abstract classes, or even interfaces. Abstract classes and
concrete classes must extend from FramedVertex or FramedEdge, however, interfaces do not have this restriction.
Annotated mode also provides a set of annotations which must be used to define any abstract methods that are to be
implemented by the engine. Annotated mode provides the same type resolution as provided by simple mode with a bit more
power to determine parent-child relationships at runtime.

The same example as above done with annotations would look something like this.

    public abstract class Person extends FramedVertex {
      @Property("name")
      public abstract String getName();

      @Property("name")
      public abstract void setName(String name);

      @Adjacency("knows")
      public abstract Iterable<Person> getKnowsPeople();

      @Incidence("knows")
      public abstract Iterable<Knows> getKnows();

      @Incidence("knows")
      public abstract Knows addKnows(Person friend);

      public List<Person> getFriendsOfFriends() {
        return out("knows").out("knows").except(this).toList(Person.class);
      }
    }

    public abstract class Knows extends FramedEdge {

      @Property("years")
      public abstract void setYears(int years);

      @Property("years")
      public abstract int getYears();

      @InVertex
      public abstract Person getIn();

      @OutVertex
      public abstract Person getIn();
    }


    public abstract class Programmer extends Person {
    }

If we pass a collection of Class objects to the FramedGraph constructor then the annotated type resolver will be used.
In this mode you want to tell the engine what classes you will be using so it can handle type resolution properly and
construct the byte-code for any abstract annotated methods.

    public void testAnnotatedTyping() {
      Set<Class<?>> types = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{
                                                                        Person.class,
                                                                        Programmer.class,
                                                                        Knows.class}));
      Graph g = new TinkerGraph();

      //implies annotated mode
      FramedGraph fg = new FramedGraph(g, true, types);

      Person p1 = fg.addFramedVertex(Programmer.class);
      p1.setName("Jeff");

      Person p2 = fg.addFramedVertex(Person.class);
      p2.setName("Julia");

      Person jeff = fg.v().has("name", "Jeff").next(Person.class);
      Person julia = fg.v().has("name", "Julia").next(Person.class);

      Assert.assertEquals(Programmer.class, jeff.getClass());
      Assert.assertEquals(Person.class, julia.getClass());
    }

## Obtaining the Source

The official source repository for Ferma is located on the Syncleus Gerrit instance and can be cloned using the
following command.

```
git clone http://gerrit.syncleus.com/Ferma
```

We also maintain a GitHub clone of the official repository which can be found
[here](https://github.com/Syncleus/Ferma). Finally Syncleus also hosts an instance of GitLab which has a
clone of the repository which can be found [here](http://gitlab.syncleus.com/syncleus/Ferma).
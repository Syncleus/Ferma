# Ferma

[![Build Status](https://travis-ci.org/Syncleus/Ferma.svg?branch=master)](https://travis-ci.org/Syncleus/Ferma)
[![Coverage](https://codecov.io/gh/Syncleus/Ferma/branch/master/graph/badge.svg)](https://codecov.io/gh/Syncleus/Ferma)
[![Dependencies](https://www.versioneye.com/user/projects/57e37d4279806f0039830884/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/57e37d4279806f0039830884)
[![Codacy](https://api.codacy.com/project/badge/Grade/e287e33b94734124ada41efd0ae48652)](https://www.codacy.com/app/freemo/Ferma?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Syncleus/Ferma&amp;utm_campaign=Badge_Grade)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.syncleus.ferma/ferma/badge.png?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.syncleus.ferma/ferma/)
[![Gitter](https://badges.gitter.im/Syncleus/Ferma.svg)](https://gitter.im/Syncleus/Ferma?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

**Licensed under the Apache Software License v2**

An ORM / OGM for the TinkerPop graph stack.

The Ferma project has been created as an alternative to the TinkerPop Frames project. Redesigned for performance and
additional features. Unlike with TinkerPop Frames, annotated classes in Ferma have their abstract methods implemented
using code generation during start-up with Byte Buddy, avoiding the need for proxy classes. This in turn significantly
improves performance when compared with TinkerPop Frames. Ferma offers many new features including several
annotated method types supplementing those provided by TinkerPop Frames. Ferma is designed to easily replace TinkerPop
Frames in existing code, as such, the annotations provided by Ferma are a super-set of those provided by TinkerPop
Frames.

Ferma is built directly on TinkerPop Blueprints with no dependency on TinkerPop Frames. This ensures all the TinkerPop
features and compatibilities are preserved, but with a high-performance drop-in replacement for Frames. The TinkerPop
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

For Additional documentation and information please use the [official Ferma wiki](https://github.com/Syncleus/Ferma/wiki).

For support please use the [official Ferma mailing list](https://groups.google.com/a/syncleus.com/forum/#!forum/ferma-list).

Please file bugs and feature requests on [the official issue tracker](https://github.com/Syncleus/Ferma/issues).

## Dependency

To include Ferma in your project of choice include the following Maven dependency into your build.

    <dependency>
        <groupId>com.syncleus.ferma</groupId>
        <artifactId>ferma</artifactId>
        <version>2.2.1</version>
    </dependency>
    

## Getting Started

Ferma provides three levels of type resolution: untyped, simple, and annotated. In untyped mode Ferma doesn't handle
typing at all, instead the type must be explicitly indicated whenever querying. In simple mode Ferma provides type
context encoded as graph element properties which ensures the same type comes out that goes in to a graph. In annotated
mode all the features of simple mode are provided as well as enabling the use of annotations on abstract methods to
instruct Ferma to dynamically construct byte-code to implement the abstract methods at start up.

### Untyped Mode Example

In untyped mode there is no automatic typing. Whatever class is explicitly indicated is the type that will be
instantiated when performing queries. Lets start with a simple example domain.

    public class Person extends VertexFrame {
      public String getName() {
        return getProperty("name");
      }

      public void setName(String name) {
        setProperty("name", name);
      }

      public List<? extends Knows> getKnowsList() {
        return outE("knows").toList(Knows.class);
      }

      public List<? extends Person> getFriendsOfFriends() {
        return out("knows").out("knows").except(this).toList(Person.class);
      }

      public Knows addKnows(Person friend) {
        return addEdge("knows", friend, Knows.class);
      }
    }

    public class Knows extends EdgeFrame {
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
      FramedGraph fg = new DelegatingFramedGraph(g);

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
    
Using simple mode will save the type of Java class the element was created with for use later:
    
    public void testSimpleTyping() {
      Graph g = new TinkerGraph();

      // implies simple mode
      FramedGraph fg = new DelegatingFramedGraph(g, true, false);
      
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

    public abstract class Person extends VertexFrame {
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

    public abstract class Knows extends EdgeFrame {
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
      FramedGraph fg = new DelegatingFramedGraph(g, true, types);

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

The official source repository for Ferma is located on the Syncleus Github instance and can be cloned using the
following command.

```
git clone https://github.com/Syncleus/Ferma.git
```

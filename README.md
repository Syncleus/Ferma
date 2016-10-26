# Ferma

[![Build Status](https://travis-ci.org/Syncleus/Ferma.svg?branch=master)](https://travis-ci.org/Syncleus/Ferma)
[![Coverage](https://codecov.io/gh/Syncleus/Ferma/branch/master/graph/badge.svg)](https://codecov.io/gh/Syncleus/Ferma)
[![Dependencies](https://www.versioneye.com/user/projects/57e37d4279806f0039830884/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/57e37d4279806f0039830884)
[![Codacy](https://api.codacy.com/project/badge/Grade/e287e33b94734124ada41efd0ae48652)](https://www.codacy.com/app/freemo/Ferma?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Syncleus/Ferma&amp;utm_campaign=Badge_Grade)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.syncleus.ferma/ferma/badge.png?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.syncleus.ferma/ferma/)
[![Gitter](https://badges.gitter.im/Syncleus/Ferma.svg)](https://gitter.im/Syncleus/Ferma?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

An ORM / OGM for the TinkerPop graph stack.

**Licensed under the Apache Software License v2**

The Ferma project was originally created as an alternative to the TinkerPop2 Frames project. Which at the time lacked
features needed by the community, and its performance was crippilingly slow. Today Ferma is a robust framework that
takes on a role similar to Object-relational Model (ORM) libraries for traditional databases. Ferma is often refered to
as a Object-graph Model (OGM) library, and maps Java objects to elements in a graph such as Vertexs and Edges.

Ferma 3.x **Supports Tinkerpop3**. For tinkerpop2 support use Ferma version 2.x.

Annotated classes in Ferma have their abstract methods implemented using code generation during start-up with Byte
Buddy, avoiding the need for proxy classes. This in turn significantly improves performance when compared with TinkerPop
Frames and other frameworks. Ferma offers many features including several annotated method types as well handling Java
typing completely transparently. This ensures whatever the type of the object is when you persist it to the graph the
same Java type will be used when instantiaing a class off of the graph.

Ferma is designed to easily replace TinkerPop Frames in existing code, as such, the annotations provided by Ferma are a
super-set of those provided by TinkerPop Frames.

Ferma is built directly on top of TinkerPop3 and allows access to all of the Tinkerpop internals. This ensures all the
TinkerPop features are preserved. The TinkerPop suite provides several tools which can be used to work with the Ferma
engine.

* **Gremlin**, a database agnostic query language for Graph Databases.
* **Gremlin Server**, a server that provides an interface for executing Gremlin on remote machines.
* a data-flow framework for splitting, merging, filtering, and transforming of data
* **Graph Computer**, a frammework for running algorithms against a Graph Database.
* Support for both **OLTP** and **OLAP** engines.
* **TinkerGraph** a Graph Database and the reference implementation for Tinkerpop.
* Native **Gephi** integration for visualizing graphs.
* Interfaces for most major Graph Compute Engines including **Hadoop M/R**. **Spark**, and **Giraph**.

Ferma also supports any of the many databases compatible with TinkerPop including the following.

 * [Titan](http://thinkaurelius.github.io/titan/)
 * [Neo4j](http://neo4j.com)
 * [OrientDB](http://www.orientechnologies.com/orientdb/)
 * [MongoDB](http://www.mongodb.org)
 * [Oracle NoSQL](http://www.oracle.com/us/products/database/nosql/overview/index.html)
 * TinkerGraph

For support please use [Gitter](https://badges.gitter.im/Syncleus/Ferma.svg)](https://gitter.im/Syncleus/Ferma?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
or the [official Ferma mailing list](https://groups.google.com/a/syncleus.com/forum/#!forum/ferma-list).

Please file bugs and feature requests on [Github](https://github.com/Syncleus/Ferma).

## Dependency

To include Ferma in your project of choice include the following Maven dependency into your build.

    <dependency>
        <groupId>com.syncleus.ferma</groupId>
        <artifactId>ferma</artifactId>
        <version>3.0.0</version>
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
        return traverse((v) -> v.outE("knows")).toList(Knows.class);
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

      Person jeff = fg.traverse((g) -> v().has("name", "Jeff")).next(Person.class);


      Assert.assertEquals("Jeff", jeff.getName());
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
      
      Person jeff = fg.traverse((g) -> g.v().has("name", "Jeff")).next(Person.class);
      Person julia = fg.traverse((g) -> g.v().has("name", "Julia")).next(Person.class);
      
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
      public abstract Iterator<Person> getKnowsPeople();

      @Incidence("knows")
      public abstract Iterator<Knows> getKnows();

      @Incidence("knows")
      public abstract Knows addKnows(Person friend);

      public List<Person> getFriendsNamedBill() {
        return traverse((v) -> v.out("knows").has("name", "bill").toList(Person.class);
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

      Person jeff = fg.traverse((g) -> g.v().has("name", "Jeff")).next(Person.class);
      Person julia = fg.traverse((g) -> g.v().has("name", "Julia")).next(Person.class);

      Assert.assertEquals(Programmer.class, jeff.getClass());
      Assert.assertEquals(Person.class, julia.getClass());
    }

## Obtaining the Source

The official source repository for Ferma is located in the Syncleus Github repository and can be cloned using the
following command.

```
git clone https://github.com/Syncleus/Ferma.git
```

![](http://syncleus.com/Ferma/images/ferma-logo-text.svg)

[![Build Status](https://travis-ci.org/Syncleus/Ferma.svg?branch=master)](https://travis-ci.org/Syncleus/Ferma)
[![Coverage](https://codecov.io/gh/Syncleus/Ferma/branch/master/graph/badge.svg)](https://codecov.io/gh/Syncleus/Ferma)
[![Dependencies](https://www.versioneye.com/user/projects/57e37d4279806f0039830884/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/57e37d4279806f0039830884)
[![Codacy](https://api.codacy.com/project/badge/Grade/e287e33b94734124ada41efd0ae48652)](https://www.codacy.com/app/freemo/Ferma?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Syncleus/Ferma&amp;utm_campaign=Badge_Grade)
[![Javadocs](http://www.javadoc.io/badge/com.syncleus.ferma/ferma.svg)](http://www.javadoc.io/doc/com.syncleus.ferma/ferma)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.syncleus.ferma/ferma/badge.png?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.syncleus.ferma/ferma/)
[![Gitter](https://badges.gitter.im/Syncleus/Ferma.svg)](https://gitter.im/Syncleus/Ferma?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

An ORM / OGM for the TinkerPop graph stack.

**Licensed under the Apache Software License v2**

The Ferma project was originally created as an alternative to the TinkerPop2 Frames project. Which at the time lacked
features needed by the community, and its performance was cripplingly slow. Today Ferma is a robust framework that
takes on a role similar to an Object-relational Model (ORM) library for traditional databases. Ferma is often referred to
as a Object-graph Model (OGM) library, and maps Java objects to elements in a graph such as a Vertex or an Edge. In
short it allows a schema to be defined using java interfaces and classes which provides a level of abstraction for
interacting with the underlying graph.

Ferma 3.x **Supports TinkerPop3**. For TinkerPop2 support use Ferma version 2.x.

Annotated classes in Ferma have their abstract methods implemented using code generation during start-up with Byte
Buddy, avoiding the need for proxy classes. This in turn significantly improves performance when compared with TinkerPop
Frames and other frameworks. Ferma offers many features including several annotation types to reduce the need for
boilerplate code as well as handling Java typing transparently. This ensures whatever the type of the object is when you
persist it to the graph the same Java type will be used when instantiating a class off of the graph.

Ferma is designed to easily replace TinkerPop Frames in existing code, as such, the annotations provided by Ferma are a
super-set of those provided by TinkerPop Frames.

Ferma is built directly on top of TinkerPop and allows access to all of the internals. This ensures all the
TinkerPop features are available to the end-user. The TinkerPop stack provides several tools which can be used to work
with the Ferma engine.

* **Gremlin**, a database agnostic query language for Graph Databases.
* **Gremlin Server**, a server that provides an interface for executing Gremlin on remote machines.
* a data-flow framework for splitting, merging, filtering, and transforming of data
* **Graph Computer**, a framework for running algorithms against a Graph Database.
* Support for both **OLTP** and **OLAP** engines.
* **TinkerGraph** a Graph Database and the reference implementation for TinkerPop.
* Native **Gephi** integration for visualizing graphs.
* Interfaces for most major Graph Compute Engines including **Hadoop M/R**. **Spark**, and **Giraph**.

Ferma also supports any of the many databases compatible with TinkerPop including the following.

 * [Titan](http://thinkaurelius.github.io/titan/)
 * [Neo4j](http://neo4j.com)
 * [OrientDB](http://www.orientechnologies.com/orientdb/)
 * [MongoDB](http://www.mongodb.org)
 * [Oracle NoSQL](http://www.oracle.com/us/products/database/nosql/overview/index.html)
 * TinkerGraph

For documentation refer to our [project page](http://syncleus.com/Ferma) as well as the
[latest Javadocs](http://www.javadoc.io/doc/com.syncleus.ferma/ferma).

For support please use [Gitter](https://gitter.im/Syncleus/Ferma?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
or the [official Ferma mailing list](https://groups.google.com/a/syncleus.com/forum/#!forum/ferma-list).

Please file bugs and feature requests on [Github](https://github.com/Syncleus/Ferma/issues).

## Dependency

To include Ferma in your project of choice include the following Maven dependency into your build.

```xml
<dependency>
    <groupId>com.syncleus.ferma</groupId>
    <artifactId>ferma</artifactId>
    <version>3.1.0</version>
</dependency>
```
    

## Getting Started

Ferma provides three levels of type resolution: untyped, simple, and annotated. In untyped mode Ferma doesn't handle
typing at all, instead the type must be explicitly indicated whenever querying. In simple mode Ferma provides type
context encoded as graph element properties which ensures the same type comes out that goes in to a graph. In annotated
mode all the features of simple mode are provided as well as enabling the use of annotations on abstract methods to
instruct Ferma to dynamically construct byte-code to implement the abstract methods at start up.

### Untyped Mode Example

In untyped mode there is no automatic typing. Whatever class is explicitly indicated is the type that will be
instantiated when performing queries. Lets start with a simple example domain.

```java
public class Person extends AbstractVertexFrame {
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
    return addFramedEdge("knows", friend, Knows.class);
  }
}

public class Knows extends AbstractEdgeFrame {
  public void setYears(int years) {
    setProperty("years", years);
  }

  public int getYears() {
    return getProperty("years");
  }
}
```

And here is how you interact with the framed elements:

```java
public void testUntyped() {
  Graph graph = TinkerGraph.open();

  // implies untyped mode
  FramedGraph fg = new DelegatingFramedGraph(graph);

  Person p1 = fg.addFramedVertex(Person.class);
  p1.setName("Jeff");

  Person p2 = fg.addFramedVertex(Person.class);
  p2.setName("Julia");
  Knows knows = p1.addKnows(p2);
  knows.setYears(15);

  Person jeff = fg.traverse((g) -> g.V().has("name", "Jeff")).next(Person.class);


  Assert.assertEquals("Jeff", jeff.getName());
}
```

### Simple Mode Example

In simple mode you must provide concrete classes, no abstract or interfaces allowed, and the class should always extend
from a FramedVertex or FramedEdge. Simple mode doesn't provide any annotations either. The purpose of simple mode is to
provide type resolution. Basically the type of object you use when adding to the graph is the same type you get out when
reading from the graph.

Say we extend the Person class with the Programmer class.
    
```java
public class Programmer extends Person {
}
```
    
Using simple mode will save the type of Java class the element was created with for use later:
    
```java
public void testSimpleTyping() {
  Graph graph = TinkerGraph.open();

  // implies simple mode
  FramedGraph fg = new DelegatingFramedGraph(graph, true, false);

  Person p1 = fg.addFramedVertex(Programmer.class);
  p1.setName("Jeff");

  Person p2 = fg.addFramedVertex(Person.class);
  p2.setName("Julia");

  Person jeff = fg.traverse((g) -> g.V().has("name", "Jeff")).next(Person.class);
  Person julia = fg.traverse((g) -> g.V().has("name", "Julia")).next(Person.class);

  Assert.assertEquals(Programmer.class, jeff.getClass());
  Assert.assertEquals(Person.class, julia.getClass());
}
```

### Annotated Mode Example

In annotated mode you can either provide concrete classes, abstract classes, or even interfaces. Abstract classes and
concrete classes must extend from FramedVertex or FramedEdge, however, interfaces do not have this restriction.
Annotated mode also provides a set of annotations which must be used to define any abstract methods that are to be
implemented by the engine. Annotated mode provides the same type resolution as provided by simple mode with a bit more
power to determine parent-child relationships at runtime.

The same example as above done with annotations would look something like this.

```java
public abstract class Person extends AbstractVertexFrame {
  @Property("name")
  public abstract String getName();

  @Property("name")
  public abstract void setName(String name);

  @Adjacency(label = "knows")
  public abstract List<Person> getKnowsPeople();

  @Incidence(label = "knows")
  public abstract List<Knows> getKnows();

  @Incidence(label = "knows")
  public abstract Knows addKnows(Person friend);

  public List<? extends Person> getFriendsNamedBill() {
      return this.traverse(input -> input.out("knows").has("name", "bill")).toList(Person.class);
  }
}

public abstract class Knows extends AbstractEdgeFrame {
  @Property("years")
  public abstract void setYears(int years);

  @Property("years")
  public abstract int getYears();

  @InVertex
  public abstract Person getIn();

  @OutVertex
  public abstract Person getOut();
}

public abstract class Programmer extends Person {
}
```

If we pass a collection of Class objects to the FramedGraph constructor then the annotated type resolver will be used.
In this mode you want to tell the engine what classes you will be using so it can handle type resolution properly and
construct the byte-code for any abstract annotated methods.

```java
public void testAnnotatedTyping() {
  Set<Class<?>> types = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{
                Person.class,
                Programmer.class,
                Knows.class}));
  Graph graph = TinkerGraph.open();

  //implies annotated mode
  FramedGraph fg = new DelegatingFramedGraph(graph, true, types);

  Person jeff = fg.addFramedVertex(Programmer.class);
  jeff.setName("Jeff");

  Person julia = fg.addFramedVertex(Person.class);
  julia.setName("Julia");
  julia.addKnows(p1)

  Person juliaAgain = fg.traverse((g) -> g.V().has("name", "Julia")).next(Person.class);
  Person jeffAgain = juliaAgain.getKnowsPeople().get(0);

  Assert.assertTrue(Programmer.class.isAssignableFrom(jeffAgain.getClass()));
  Assert.assertTrue(Person.class.isAssignableFrom(juliaAgain.getClass()));
}
```

## Obtaining the Source

The official source repository for Ferma is located in the Syncleus Github repository and can be cloned using the
following command.

```bash
git clone https://github.com/Syncleus/Ferma.git
```

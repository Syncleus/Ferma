---
title: Comparing the Alternatives
permalink: comparing_the_alternatives.html
sidebar: main_sidebar
tags: [getting_started]
keywords: getting started
last_updated: October 30, 2016
toc: true
folder: documentation
---

There are several OGM/ORM options out there. For the purposes of this document we will focus only on those that have a stable release, or are close to a stable release. At the time of this writing those are: Tinkerpop Framed and Totorom.

Benchmarks
==========

We maintain an informal project for benchmarking Ferma against other OGM available, you can find the [source here](https://github.com/Syncleus/Ferma-benchmark). However below is a matrix breakdown of the results. Instead of showing raw execution time we show the ratio of each OGM compared to Ferma. Therefore if the table lists 1x then it means the framework has the same execution time as Ferma, if it lists 2x then it took twice as long to execute, and if it indicates 0.5x then it took half the time to execute. Obviously any value less than 1x indicates the OGM out performed Ferma and any value greater than 1x indicates Ferma had the superior performance tiimes.

|                                           | **Blueprints** | **Gremlin Pipeline** | **Tinkerpop3** | **Frames**  | **Totorom** | **Peapod**  |
|-------------------------------------------|----------------|----------------------|----------------|-------------|-------------|-------------|
| **Get adjacencies via annotation**        | Not capable    | Not capable          | Not capable    | x2.09       | Not capable | x2.65       |
| **Get verticies (untyped)**               | x0.89          | x3.94                | x16.98         | Not capable | x4.24       | Not capable |
| **Get verticies (typed)**                 | x0.92          | x3.94                | Not capable    | x0.96       | x4.20       | x20.74      |
| **Get verticies and call next (untyped)** | x0.79          | x3.87                | x11.74         | Not capable | x4.81       | Not capable |
| **Get verticies and call next (typed)**   | x0.72          | x2.91                | Not capable    | x1.94       | x3.31       | x16.70      |

As can be seen Ferma out performs all the alternative solutions considerably by several orders of magnitude. While results do vary slightly from system to system these results are pretty close to typical. Go ahead, check out the benchmark program and run it for yourself!

Feature Breakdown
=================

Despite the superior performance of Ferma it also supports all the features provided by the alternatives out there, not to mention several novel features. The following gives a quick breakdown of the features of the various frameworks. We also include a bit later in the document some Ferma examples showing the various features in action. All of the examples below use the domain model [found here](Ferma:Domain_Example).

|                                                                                                                  | **Ferma**     | **Frames**    | **Totorom**   | **Peapod**    |
|------------------------------------------------------------------------------------------------------------------|---------------|---------------|---------------|---------------|
| **"JPA-like Annotations":Ferma:Creating\_Annotated\_Domain\_Models**                                             | Supported     | Supported     | Not Supported | Supported     |
| **"Type information encoded into graph.":\#Type\_information\_encoded\_into\_graph**                             | Supported     | Supported     | Supported     | Supported     |
| **"Framing of elements instantiated according to type hierarchy.":\#Framing\_instantiated\_by\_type\_hierarchy** | Supported     | Supported     | Supported     | Supported     |
| **"Element queried by type hierarchy.":\#Element\_queried\_by\_type\_hierarchy**                                 | Supported     | Not Supported | Not Supported | Partial \*    |
| **"Turning off type resolution on a per-call basis.":\#Turning\_off\_type\_resolution\_per\_call**               | Supported     | Not Supported | Not Supported | Not Supported |
| **"Changing the encoded graph type already stored in the database.":\#Changing\_type\_encoded\_in\_the\_graph**  | Supported     | Not Supported | Not Supported | Not Supported |
| **"Customizing the way type information is stored in the graph":\#Customizing\_how\_types\_are\_encoded**        | Supported     | Not Supported | Not Supported | Not Supported |
| **Tinkerpop 2 support**                                                                                          | Supported     | Supported     | Supported     | Not Supported |
| **Tinkerpop 3 support**                                                                                          | Not Supported | Not Supported | Not Supported | Supported     |

\* While Peapod does support querying for all instances of a type, and its subtypes, it does not support a mechanism to query for a specific type while excluding subtypes.

Type information encoded into graph
-----------------------------------

```java
Set<Class<?>> types = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{Person.class}));
Graph g = new TinkerGraph();
FramedGraph fg = new DelegatingFramedGraph(g, types);

fg.addFramedVertex(Person.class);
Person person = fg.v().next(Program.class);

String personClassName = Person.class.getName();
String encodedClassName = person.getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY)
assert(personClassName.equals(encodedClassName));
```

Framing instantiated by type hierarchy
--------------------------------------

```java
Set<Class<?>> types = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{Person.class,
                                                                         Programmer.class}));
TinkerGraph g = new TinkerGraph();
FramedGraph fg = new DelegatingFramedGraph(g, types);

fg.addFramedVertex(Programmer.class);

//make sure the newly added node is actually a programmer
Person programmer = fg.v().next(Person.class);
assert(programmer instanceof Programmer);
```

Element queried by type hierarchy
---------------------------------

```java
Set<Class<?>> types = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{Person.class,
                                                                         Programmer.class}));
TinkerGraph g = new TinkerGraph();
FramedGraph fg = new DelegatingFramedGraph(g, types);

fg.addFramedVertex(Programmer.class);
fg.addFramedVertex(Person.class);

//counts how many people (or subclasses thereof) in the graph.
assert(fg.v().has(Person.class).count() == 2);
//counts how many programmers are in the graph
assert(fg.v().has(Programmer.class).count() == 1);
```

Turning off type resolution per call
------------------------------------

```java
Set<Class<?>> types = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{Person.class,
                                                                         Programmer.class}));
TinkerGraph g = new TinkerGraph();
FramedGraph fg = new DelegatingFramedGraph(g, types);

fg.addFramedVertex(Programmer.class);

//With type resolution is active it should be a programmer
assert(fg.v().next(Person.class) instanceof Programmer);
//With type resolution bypassed it is no longer a programmer
assert(!(fg.v().nextExplicit(Person.class) instanceof Programmer));
```

Changing type encoded in the graph
----------------------------------

```java
Set<Class<?>> types = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{Person.class,
                                                                         Programmer.class}));
TinkerGraph g = new TinkerGraph();
FramedGraph fg = new DelegatingFramedGraph(g, types);

fg.addFramedVertex(Programmer.class);

//make sure the newly added node is actually a programmer
Person programmer = fg.v().next(Person.class);
assert(programmer instanceof Programmer);

//change the type resolution to person
programmer.setTypeResolution(Person.class);

//make sure the newly added node is actually a programmer
Person person = fg.v().next(Person.class);
assert(person instanceof Person);
assert(!(person instanceof Programmer));
```

Customizing how types are encoded
---------------------------------

```java
Set<Class<?>> types = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{Person.class}));
final ReflectionCache cache = new ReflectionCache(types);
FrameFactory factory = new AnnotationFrameFactory(cache);
TypeResolver resolver = new PolymorphicTypeResolver(cache, "customTypeKey");
Graph g = new TinkerGraph();
FramedGraph fg = new DelegatingFramedGraph(g, factory, resolver);

fg.addFramedVertex(Person.class);
Person person = fg.v().next(Program.class);

String personClassName = Person.class.getName();
String encodedClassName = person.getProperty("customTypeKey")
assert(personClassName.equals(encodedClassName));
```


# Ferma Changelog

## 3.3.0

* Added support for properties with mulitiple values for annotated property getters.
* An adjacency getter (annotated) will now return null instead of an exception if no elements present.
* Setters now take null as a valid argument.
* Fixed a bug that caused exceptions in the case of a vertex property with a cardinality other than single.
* Updated the following dependencies
  * gson 2.8.2 -> 2.8.5
  * guava 23.0 -> 26.0-jre
  * byte-buddy 1.7.5 -> 1.8.20
  * gremlin-core 3.3.0 -> 3.3.3
  * tinkergraph-gremlin 3.3.0 -> 3.3.3
  * mockito-all 1.10.19 -> 2.0.2-beta


## 3.2.1

* Removed `throws exception` from the signature of the TxActions.
* Fixed a bug where the `VertexFrame.toJson()` and `EdgeFrame.toJson()` methods wouldn't encode properties that werent
  of the type `Number` or `String`
* When constructing a `DelegatingFramedGraph` if a the delegate graph argument is null the constructor now throws an
  exception.
* `AbstractAnnotationFrameFactory.constructClass()` method now throws an exception if the element argument is neither an
  `Edge` or a `Vertex`.
* Added several more unit tests bringing test coverage up an additional 5%.

## 3.2.0

* Added nexus staging deployment plugin.
* Removed explicit version from licensing plugin.
* Pom updated to require maven 3.0.4
* Added additional constructor to DelegatingFramedGraph which accepts a package name to scan instead of needing to
  explicitly pass all the model's classes as a set.
* Added `operation` parameter to the following annotations: `@Adjacency`, `@Incidence`, `@Property`. Setting the
  parameter will override the auto discovery of the method prefix previously used to discovery the operation of the
  method.
* Getter methods annotated with the `@Adjacency` annotation can now return a `List` or a `Set` in addition to the usual
  `Iterator` return type.
* Setter methods annotated with the `@Adjacency` annotation can now accept `Iterable` parameters such as `Collection`,
  `List`, or `Set` in addition to the usual `Iterator` type.
* Remove methods annotated with the `@Adjacency` annotation can now have no parameters and they will remove all edges
  matching the specified label and direction.
* Setter methods annotated with the `@Adjacency` annotation can now accept `VertexFrame` parameters including other
  vertex in the users custom model.
* Methods annotated with the `@Incidence` annotation can now return a `List` or a `Set` in addition to the usual
  `Iterator` return type.
* Removed the upper bound on traversal generics. The travese function's argument has changed from
  `GraphTraversal<? extends Vertex, ? extends Vertex>` to `GraphTraversal<Vertex, Vertex>`.
* Expanded documentation to cover all the annotations availible in greater detail, including recent changes.
* `AnnotationFrameFactory` was redesigned to make it easier to inherit from the class and add support for additional
  custom annotations.
* `AbstractAnnotationFrameFactory` was created to help create custom annotations that replace the existing standard
  annotations provided by `AnnotationFrameFactory`.
* Fixed a `NullPointerException` thrown any time `DelegatingFramedGraph.addFramedEdgeExplicit` was called.
* Fixed a `IllegalStateException` thrown anytime `DefaultTraversable.nextOrAdd()` was called.
* Fixed a `IllegalStateException` thrown when `PolymorphicTypeResolver.resolve()` is called on a class without type
  information.
* Updated the following dependencies
  * gson: 2.8.1 -> 2.8.2

## 3.1.0

* Added abstraction code for tinkerpop transactions which ease usage of transactions within ferma.
  The new code provides functional interfaces for transactions and methods which allow
  access to transactions from nested functions without the need to pass the original transaction object along.
  Once a transaction has been opened it can be accessed from anywhere within the same thread
  via the `Tx.getActive()` method.

  The `TxFactoryTest` class and the [ferma-orientdb extension](https://github.com/syncleus/ferma-orientdb)
  contain examples how these classes and methods can be used.

* Updated the following dependencies
  * guava 22.0 -> 23.0
  * byte-buddy 1.7.1 -> 1.7.5
  * gremlin-core 3.2.5 -> 3.3.0
  * tinkergraph-gremlin 3.2.4 -> 3.3.0

* Methods set with the Property annotation now accept null values.

## 3.0.3

* Updated the following dependencies
  * gson: 2.8.0 -> 2.8.1
  * guava: 20.0 -> 22.0
  * byte-buddy: 1.5.12 -> 1.7.1
  * gremlin-core: 3.2.3 -> 3.2.4
  * tinkergraph-gremlin: 3.2.4 -> 3.2.4
  * reflections: 0.9.10 -> 0.9.11
  * maven-license-plugin: 1.9.0 -> 1.10.b1
* Changed internal traversal code to use the new lambda syntax.

## 3.0.2

* Updated package dependencies, specifically Byte Buddy, mostly performance improvements.

## 3.0.1

* Removed the following deprecated classes: FramingIterable, FramingVertexIterable, Path, RunMe, Storage, and VoidFunction.

## 3.0.0

* Tinkerpop3 is now supported.
* Removed several of the pipline methods from exiting classes, this is now replaced with the traverse method.
* Ferma specific pipelines are now replaced with Tinkerpop3 traversals.

## 2.2.0

* Added id parameter to ```fg.addFramedVertex``` and ```fg.addFramedEdge``` methods to prepare for orientdb custom vertex and edge types.
* Added getFramedVertexExplicit method which can be used to load a framed vertex by id.
* The internal references to the nested graph and the nested element within a element frame have been changed in order to enable custom getElement/getGraph methods which may be desired in some cases.
  * [Example usecase](https://github.com/Syncleus/Ferma/issues/10)
* Changed the projects license to Apache Software License v2.

## 2.1.0

* Fixed a bug where an exception was thrown when using the @Adjacency annotation on outgoing adjacencies for add*, set*, and remove*.
* Added has and hasNot keyed to type to the gremlin pipeline.
* Switched over to using the Syncleus parent POM.
* renamed the pipeline map method to propertyMap to reflect the TP3 interfaces.

## 2.0.6

* Added the ability to use the @Incidence annotation on add* methods. These work the same as with adjacency but return the edge rather than the node.
* Added additional metadata to the pom file.
* Added multi-threaded transaction support.
* Added support for tinkerpop style transaction handling.
* Added additional argument sanity checking for fail-fast exceptions.
* Implemented WrappedGraph interface for DelegatingFramedGraph.
* The pom site goal now executes successfully.
* Added badges to the readme to report project state.

## 2.0.5

* The type resolution key can now be easily customized.
* Packages have been refactored to make the classes more organized and easier to work with.

## 2.0.4

* Added back the ability to specify Class arguments in addition to the ClassInitializer arguments.
* Fixed some mistakes in the javadocs.

## 2.0.3

* Framed graph will return null when framing a null object (as opposed to a  NullPointerException as before).
* remove() functionality added to traversals and iterators.
* Significant performance improvements when accessing the Framed graph V().has(key,value) method as well as numerous other related performance improvements.
* Comparators class is now properly implemented as utility class and can no longer be constructed.
* Replaced the Class argument in several methods which create new graph elements with a ClassInitializer argument. This provides additional functionality giving more control over how elements are instantiated.

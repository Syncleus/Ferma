# Ferma Changelog

## 2.4.0

* Renamed functional interfaces for transactions from TxHandler to TxAction to conform with Ferma 3.1.0 changes.
* Methods annotated with the Property annotation can now accept null arguments.
* Updated the following dependencies
  * syncleus-pom: 3 -> 4
  * guava: 22.0 -> 23.0
  * byte-buddy: 1.7.1 -> 1.7.5

## 2.3.0

* Updated Ferma 2.3.0 to utilize Java 8.
* Added transaction interfaces which are used by ferma extensions.

## 2.2.2

* Updated the following dependencies
  * gson: 2.8.0 -> 2.8.1
  * guava: 20.0 -> 22.0
  * byte-buddy: 1.5.12 -> 1.7.1
  * reflections: 0.9.10 -> 0.9.11
  * maven-license-plugin: 1.9.0 -> 1.10.b1

## 2.2.1

* Updated package dependencies, specifically Byte Buddy, mostly performance improvements.

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

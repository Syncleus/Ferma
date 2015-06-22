# Ferma Changelog

## 2.1.0

* Added the ability to use as and back gremline pipe methods with named marks.
* Added hasType to the gremlin pipeline.
* Switched over to using the Syncleus parent POM.

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

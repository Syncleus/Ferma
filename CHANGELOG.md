# maven-mongodb-plugin Changelog

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

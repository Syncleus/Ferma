The Ferma schema is defined by a collection of interfaces and classes written by the user. Each method will interact
with the underlying graph to either modify the graph in some way, or to retrieve an element or property from the graph.
There are two techniques for defining how these methods behave. Either you can explicitly implement the method, or you
can leave the method as abstract and annotate the method in order to allow Ferma to implement the method for you. Here
we will define the annotations available to you and how they work, along with a few examples.

The behavior of an annotated method is dictated not only by the annotation applied to it but also the method's
signature. Therefore an annotated method will behave differently if it's return type, arguments, or even if the method
name were to change. It is important to note that when a method is explicitly defined (doesnt use an annotation) then
the method signature can be anything.

Method names that are annotated must have one of the following prefixes: add, get, remove, set, is. Exactly which 
prefixes are varies from one annotation to the next so see the annotations detailed documentation to make that
determination. It is also possible to override this behavior by setting the operation argument availible on most
annotations which defaults to `AUTO` when not specified.

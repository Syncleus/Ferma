---
title: Core Annotations
permalink: core_annotations.html
sidebar: main_sidebar
tags: [reference]
keywords: Reference, Annotations
last_updated: October 30, 2016
toc: true
folder: documentation
---

The Ferma schema is defined by a collection of interfaces and classes written by the user. Each method will interact with the underlying graph to either modify the graph in some way, or to retrieve an element or property from the graph. There are two techniques for defining how these methods behave. Either you can explicitly implement the method, or you can leave the method as abstract and annotate the method in order to allow Ferma to implement the method for you. Here we will define the annotations available to you and how they work, along with a few examples.

The behavior of an annotated method is dictated not only by the annotation applied to it but also the method's signature. Therefore an annotated method will behave differently if it's return type, arguments, or even if the method name were to change. It is important to note that when a method is explicitly defined (doesnt use an annotation) then the method signature can be anything.

Method names that are annotated must have one of the following prefixes: add, get, remove, set, is, can.

Below specifies that annotations that can be used when defining a Frame's interface. By specifying the method argument and return types, the underlying graph is constrained to the interface specification.

## Property annotation

Valid on frames: Edge and Vertex

Allowed prefixes: `get`, `is`, `can`, `set`, `remove`

Annotation arguments:

`value` - The name of the property

The following would bind the method it is used on to the property named `foo`:

```java
@Property("foo")
//Method declared here
```

### get prefix

Valid method arguments: `()`

#### ()

Valid return types: *Any*

Get the property value of an element. Used when property is not a boolean value.

example:

```java
@Property("Foo")
Foobar getFoobar()
```

### is prefix

Valid method arguments: `()`

#### ()

Valid return types: `boolean`

Get the property value of an element. Used when property is a boolean value.

example:

```java
@Property("Foobared")
boolean isFoobared()
```

### set prefix

Valid method arguments: `(Object)`

#### (Object)

Valid return types: *None*

Set the property value of an element.

example:

```java
@Property("Foo")
void setFoobar(Foobar foobar)
```

### remove prefix

Valid method arguments: `()`

#### ()

Valid return types: *None*

Remove the property of an element.

example:

```java
@Property("Foo")
void removeFoobar()
```

## Adjacency annotation

Valid on frames: Vertex

Allowed prefixes: `add`, `get`, `remove`, `set`

Annotation arguments:

`label` - The label assigned to the edge which connects the adjacent nodes.

`direction` - The direction for the edge which creates the adjacency. It can be assigned any of the values from @org.apache.tinkerpop.gremlin.structure.Direction@.

### add prefix

Valid method arguments: `()`, `(VertexFrame)`, `(ClassInitializer)`, `(ClassInitializer, ClassInitializer)`

Adds a node as an adjacency to the current node, and the returns the newly connected node.

#### ()

Valid return types: `VertexFrame`

Creates a new vertex without any type information as well as an untyped edge to connect to it. The newly created VertexFrame is returned.

example:

```java
@Adjacency("Foo")
VertexFrame addFoobar()
```

#### (VertexFrame)

Valid return types: `VertexFrame`

Creates a new edge without any type information and connects it between this vertex the vertex specified as an argument to the method.

example:

```java
@Adjacency("Foo")
VertexFrame addFoobar(VertexFrame existingVertex)
```

#### (ClassInitializer)

Valid return types: `VertexFrame`

Creates a new edge without any type information and connects it between this vertex and a newly created vertex. The newly created vertex will have a type, as well as be initiated, according to the details specified in the ClassInitializer argument. Java generics can, and should, be used to narrow the return type.

example:

```java
@Adjacency("Foo")
<E extends VertexFrame> E addFoobar(ClassInitializer<E> vertexInitializer)
```

#### (ClassInitializer, ClassInitializer)

Valid return types: `VertexFrame`

Creates a new edge and connects this to a new vertex. The newly created vertex will have a type, as well as be initiated, according to the details specified in the first ClassInitializer argument. Similarly the newly created edge will hava type, and be initiated using, the second ClassInitializer argument. Java generics can, and should, be used to narrow the return type.

example:

```java
@Adjacency("Foo")
<E extends VertexFrame> E addFoobar(ClassInitializer<E> vertexInitializer,
                                    ClassInitializer<? extends EdgeFrame> edgeInitializer)
```

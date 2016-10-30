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

# Valid on all frames

## Property annotation

Allowed prefixes: `get`, `is`, `can`, `set`, `remove`

Annotation arguments:
@value@ - The name of the property

### get prefix

Valid return types: *Any*
Valid method arguments: `()`

Get the property value of an element. Used when property is not a boolean value.

example:

```java
@Property("Foo")
Foobar getFoobar()
```

### is prefix

Valid return types: `boolean`
Valid method arguments: `()`

Get the property value of an element. Used when property is a boolean value.

example:

```java
@Property("Foobared")
boolean isFoobared()
```

### set prefix

Valid return types: *None*
Valid method arguments: `(Object)`

Set the property value of an element.

example:

```java
@Property("Foo")
void setFoobar(Foobar foobar)
```

### remove prefix

Valid return types: *None*
Valid method arguments: `()`

Remove the property of an element.

example:

```java
@Property("Foo")
void removeFoobar()
```

# Valid on vertices (VertexFrame)

## Adjacency annotation

Allowed prefixes: `add`, `get`, `remove`, `set`

Annotation arguments:
`label` - The label assigned to the edge which connects the adjacent nodes.
`direction` - The direction for the edge which creates the adjacency. It can be assigned any of the values from @org.apache.tinkerpop.gremlin.structure.Direction@.

### add prefix

Valid method arguments: `()`, `(ClassInitializer)`, `(ClassInitializer, ClassInitializer)`, `(VertexFrame)`

Adds a node as an adjacency to the current node, and the returns the newly connected node.

#### () arguments

Valid return types: `VertexFrame`

Creates a new vertex without any type information as well as an untyped edge to connect to it. The newly created VertexFrame is returned.

example:

```java
@Adjacency("Foo")
VertexFrame addFoobar()
```

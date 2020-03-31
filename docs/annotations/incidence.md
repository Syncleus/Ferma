Valid on frames: **Vertex**

Allowed prefixes when operation is AUTO: `add`, `get`, `remove`

Annotation arguments:

`label` - The label assigned to the edge which connects the adjacent nodes.

`direction` - The direction for the edge which creates the adjacency. It can be assigned any of the values from
              `org.apache.tinkerpop.gremlin.structure.Direction`.

`operation` - The operation the method will perform. Must be one of the following: `GET`, `ADD`, `SET`, `REMOVE`,
              `AUTO`. Defaults to `AUTO`.

example:

```java
@Incidence("foo")
//Method declared here
```

## ADD Operation

Valid method signatures: `( )`, `(VertexFrame)`, `(ClassInitializer)`, `(VertexFrame, ClassInitializer)`,
`(ClassInitializer, ClassInitializer)`

Adds an edge to a node and returns the new `EdgeFrame`.

### Signature: `( )`

Valid return types: `EdgeFrame`

Creates a new vertex without any type information as well as an untyped edge to connect to it. The newly created
`TEdge` is returned.

!!! note
    Since the returned `VertexFrame` is always untyped the return type must be either `VertexFrame` or `TVertex`
    specifically.

```java
@Incidence("Foo")
EdgeFrame addFoobar();
```

```java
@Incidence(value = "Foo", operation = Adjacency.Operation.ADD)
TEdge addFoobar();
```

### Signature: `(VertexFrame)`

Valid return types: `EdgeFrame`

Creates a new edge without any type information and connects it between this vertex the vertex specified as an argument
to the method. The frame returned is the newly created `TEdge`.

examples:

```java
@Incidence("Foo")
FooEdge addFoobar(BarVertex existingVertex);
```

```java
@Incidence("Foo")
<E extends FooEdge> E addFoobar(BarVertex existingVertex);
```

```java
@Incidence("Foo")
EdgeFrame addFoobar(BarVertex existingVertex);
```

```java
@Incidence("Foo", direction = Direction.IN)
<E extends EdgeFrame> E addFoobar(BarVertex existingVertex);
```

```java
@Incidence(value = "Foo", operation = Adjacency.Operation.ADD)
FooEdge includeFoobar(BarVertex existingVertex);
```

### Signature: `(ClassInitializer)`

Valid return types: `EdgeFrame`

Creates a new edge without any type information and connects it between this vertex and a newly created vertex. The
newly created vertex will have a type, as well as be initiated, according to the details specified in the
ClassInitializer argument. Java generics can, and should, be used to narrow the return type. The returned object will be
the newly created `TEdge`.

example:

```java
@Incidence("Foo")
FooEdge addFoobar(ClassInitializer<? extends FooEdge> vertexInitializer);
```

```java
@Incidence("Foo")
<E extends FooEdge> E addFoobar(ClassInitializer<? extends BarVertex> vertexInitializer);
```

```java
@Incidence("Foo", direction = Direction.IN)
<E extends FooEdge> E addFoobar(ClassInitializer<? extends BarVertex> vertexInitializer);
```

```java
@Incidence(value = "Foo", operation = Adjacency.Operation.ADD)
TEdge includeFoobar(ClassInitializer<? extends BarVertex> vertexInitializer);
```

### Signature: `(VertexFrame, ClassInitializer)`

Valid return types: `EdgeFrame`

Creates a new edge and connects this to an existing vertex. The newly created edge will have a type, as well as be
initiated, according to the details specified in the ClassInitializer argument. Java generics can, and should, be used
to narrow the return type. The returned object will be the newly created `EdgeFrame`.

example:

```java
@Incidence("Foo")
FooEdge addFoobar(BarVertex bar,
                  ClassInitializer<? extends FooEdge> edgeInitializer);
```

```java
@Incidence("Foo")
<E extends FooEdge> E addFoobar(BarVertex bar,
                                ClassInitializer<? extends E> edgeInitializer);
```

```java
@Incidence("Foo", direction = Direction.IN)
<E extends EdgeFrame> E addFoobar(VertexFrame vertex,
                                  ClassInitializer<? extends E> edgeInitializer);
```

```java
@Incidence(value = "Foo", operation = Adjacency.Operation.ADD)
FooEdge includeFoobar(BarVertex bar,
                      ClassInitializer<? extends FooEdge> edgeInitializer);
```

### Signature: `(ClassInitializer, ClassInitializer)`

Valid return types: `EdgeFrame`

Creates a new edge and connects this to a new vertex. The newly created vertex will have a type, as well as be
initiated, according to the details specified in the first ClassInitializer argument. Similarly the newly created edge
will hava a type, and be initiated, using the second ClassInitializer argument. Java generics can, and should, be used
to narrow the return type. The returned object will be the newly created `EdgeFrame`.

example:

```java
@Incidence("Foo")
FooEdge addFoobar(ClassInitializer<? extends BarVertex> vertexInitializer,
                    ClassInitializer<? extends FooEdge> edgeInitializer);
```

```java
@Incidence("Foo")
<E extends FooEdge> E addFoobar(ClassInitializer<? extends BarVertex> vertexInitializer,
                                  ClassInitializer<? extends E> edgeInitializer);
```

```java
@Incidence("Foo", direction = Direction.IN)
<E extends EdgeFrame> E addFoobar(ClassInitializer<? extends BarVertex> vertexInitializer,
                                  ClassInitializer<? extends E> edgeInitializer);
```

```java
@Incidence(value = "Foo", operation = Adjacency.Operation.ADD)
FooEdge includeFoobar(ClassInitializer<? extends BarVertex> vertexInitializer,
                      ClassInitializer<? extends FooEdge> edgeInitializer);
```

## GET Operation

Valid method signatures: `()`, `(Class)`

Get's one or more adjacent edgesd from the graph.


### Signature: `( )`

Valid return types: `VertexFrame` or `Iterator` or `List` or `Set`

Retrieves one or more of the adjacent vertex. If the return type is a single Frame then only the first instance is
returned. If the return type is an `Iterator` or `List` or `Set` then it will supply all matching edges. When using an
`Iterator` or `List` or `Set` it is encouraged, but not required, to use generics. The returned frames will always be
instantiated as the type encoded in the graph if there is one.

!!! note
    If a type is encoded in the in the graph is a superclass of the returned element then an exception will be thrown.
    Therefore the return type specifed should always by the same type, or a superclass, of the expected return type.
    VertexFrame is always a safe return type for this method.

example:

```java
@Incidence("Foo")
FooEdge getFoobar();
```

```java
@Incidence("Foo")
<E extends FooEdge> E getFoobar();
```

```java
@Incidence("Foo")
<E extends EdgeFrame> E getFoobar();
```

```java
@Incidence("Foo")
Iterator<FooEdge> getFoobar();
```

```java
@Incidence("Foo")
<E extends FooEdge> Iterator<E> getFoobar();
```

```java
@Incidence("Foo")
<E extends EdgeFrame> Iterator<E> getFoobar();
```

```java
@Incidence("Foo")
List<FooEdge> getFoobar();
```

```java
@Incidence("Foo", direction = Direction.IN)
Set<FooEdge> getFoobar();
```

```java
@Incidence(value = "Foo", operation = Adjacency.Operation.GET)
FooEdge obtainFoobar();
```


### Signature: `(Class)`

Valid return types: `VertexFrame` or `Iterator` or `List` or `Set`

Retrieves one or more of the adjacent edges. If the return type is a single `EdgeFrame` then only the first instance
is returned. If the return type is an `Iterator` then it will iterate over all matching vertex. When using an Iterator
it is encouraged to use generics.

!!! note
    The Class argument of the method specifes a filter such that only vertex which are of a matching type, or a subtype,
    to that of the argument will be returned.

example:

```java
@Incidence("Foo")
FooEdge getFoobar(Class<? extends FooEdge> filter);
```

```java
@Incidence("Foo")
<E extends FooEdge> E getFoobar(Class<? extends E> filter);
```

```java
@Incidence("Foo")
<E extends FooEdge> E getFoobar(Class<? extends E> filter);
```

```java
@Incidence("Foo")
Iterator<FooEdge> getFoobar(Class<? extends FooEdge> filter);
```

```java
@Incidence("Foo")
<E extends FooEdge> Iterator<E> getFoobar(Class<? extends E> filter);
```

```java
@Incidence("Foo")
<E extends EdgeFrame> Iterator<E> getFoobar(Class<? extends E> filter);
```

```java
@Incidence("Foo")
List<FooEdge> getFoobar(Class<? extends FooEdge> filter);
```

```java
@Incidence("Foo", direction = Direction.IN)
Set<FooEdge> getFoobar(Class<? extends FooEdge> filter);
```

```java
@Incidence(value = "Foo", operation = Adjacency.Operation.GET)
FooEdge obtainFoobar(Class<? extends FooEdge> filter);
```

## REMOVE Operation

Valid method signatures: `(EdgeFrame)`

Removes an edges which cause an adjacency, leaving the vertex in place.

### Signature: `(EdgeFrame)`

Valid return types: `void`

Removes the edge specified in the argument. This is entirely equivelant to just calling the `remove()` method on the
`EdgeFrame`

example:

```java
@Incidence("Foo")
void removeFoobar(FooEdge edge);
```

```java
@Incidence("Foo")
<E extends FooEdge> void removeFoobar(E edge);
```

```java
@Incidence("Foo", direction = Direction.IN)
<E extends EdgeFrame> void removeFoobar(E edge);
```

```java
@Incidence(value = "Foo", operation = Adjacency.Operation.REMOVE)
void removeFoobar(FooEdge edge);
```

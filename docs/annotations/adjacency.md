Valid on frames: Vertex

Allowed prefixes when operation is AUTO: `add`, `get`, `remove`, `set`

Annotation arguments:

`value` - The label assigned to the edge which connects the adjacent nodes.

`direction` - The direction for the edge which creates the adjacency. It can be assigned any of the values from @org.apache.tinkerpop.gremlin.structure.Direction@.

`operation` - The operation the method will perform. Must be one of the following: `GET`, `ADD`, `SET`, `REMOVE`, `AUTO`.
Defaults to `AUTO`.

```java
@Adjacency("foo")
//Method declared here
```

## ADD Operation

Valid method signatures: `( )`, `(VertexFrame)`, `(ClassInitializer)`, `(ClassInitializer, ClassInitializer)`

Adds a node as an adjacency to the current node, and the returns the newly connected node.


### Signature: `( )`

Valid return types: `VertexFrame`

Creates a new vertex without any type information as well as an untyped edge to connect to it. The newly created VertexFrame is returned.

Since the returned `VertexFrame` is always untyped the return type must be either `VertexFrame` or `TVertex` specifically.

example:

```java
@Adjacency("Foo")
VertexFrame addFoobar();
```

```java
@Adjacency(value = "Foo", operation = Adjacency.Operation.ADD)
VertexFrame addFoobar();
```


### Signature: `(VertexFrame)`

Valid return types: `VertexFrame`

Creates a new edge without any type information and connects it between this vertex the vertex specified as an argument to the method. The frame returned is the same as the frame given in the argument, it is only there for compatability with other add methods. This method can also have a `void` return type.

examples:

```java
@Adjacency("Foo")
Bar addFoobar(Bar existingVertex);
```

```java
@Adjacency("Foo")
<E extends Bar> E addFoobar(E existingVertex);
```

```java
@Adjacency("Foo")
<E extends VertexFrame> E addFoobar(E existingVertex);
```

```java
@Adjacency(value = "Foo", operation = Adjacency.Operation.ADD)
Bar E includeFoobar(Bar existingVertex);
```


### Signature: `(ClassInitializer)`

Valid return types: `VertexFrame`

Creates a new edge without any type information and connects it between this vertex and a newly created vertex. The newly created vertex will have a type, as well as be initiated, according to the details specified in the ClassInitializer argument. Java generics can, and should, be used to narrow the return type.

example:

```java
@Adjacency("Foo")
Bar addFoobar(ClassInitializer<? extends Bar> vertexInitializer);
```

```java
@Adjacency("Foo")
<E extends Bar> E addFoobar(ClassInitializer<? extends E> vertexInitializer);
```

```java
@Adjacency("Foo")
<E extends VertexFrame> E addFoobar(ClassInitializer<? extends E> vertexInitializer);
```

```java
@Adjacency(value = "Foo", operation = Adjacency.Operation.ADD)
Bar includeFoobar(ClassInitializer<? extends Bar> vertexInitializer);
```

### Signature: `(ClassInitializer, ClassInitializer)`

Valid return types: `VertexFrame`

Creates a new edge and connects this to a new vertex. The newly created vertex will have a type, as well as be initiated, according to the details specified in the first ClassInitializer argument. Similarly the newly created edge will hava type, and be initiated using, the second ClassInitializer argument. Java generics can, and should, be used to narrow the return type.

example:

```java
@Adjacency("Foo")
Bar addFoobar(ClassInitializer<? extends Bar> vertexInitializer,
              ClassInitializer<?> edgeInitializer);
```

```java
@Adjacency("Foo")
<E extends Bar> E addFoobar(ClassInitializer<? extends E> vertexInitializer,
                            ClassInitializer<?> edgeInitializer);
```

```java
@Adjacency("Foo")
<E extends VertexFrame> E addFoobar(ClassInitializer<? extends E> vertexInitializer,
                                    ClassInitializer<?> edgeInitializer);
```

```java
@Adjacency(value = "Foo", operation = Adjacency.Operation.ADD)
Bar includeFoobar(ClassInitializer<? extends Bar> vertexInitializer,
              ClassInitializer<?> edgeInitializer);
```

## GET Operation

Valid method signatures: `()`, `(Class)`

Get's one or more adjacent vertex from the graph.


### Signature: `( )`

Valid return types: `VertexFrame` or `Iterator` or `List` or `Set`

Retrieves one or more of the adjacent vertex. If the return type is a single Frame then only the first instance is returned. If the return type is an `Iterator` or `Iterable` then it will supply all matching vertex. When using an `Iterator` or `Iterable` it is encouraged, but not required, to use generics. The returned frames will always be instantiated as the type encoded in the graph if there is one.

**Note:** If a type is specified in the arguments is a superclass of the returned element then an exception will be thrown. Therefore the return type specifed should always by the same type, or a superclass, of the expected return type. VertexFrame is always a safe return type for this method.

example:

```java
@Adjacency("Foo")
Bar getFoobar();
```

```java
@Adjacency("Foo")
<E extends Bar> E getFoobar();
```

```java
@Adjacency("Foo")
<E extends VertexFrame> E getFoobar();
```

```java
@Adjacency("Foo")
Iterator<Bar> getFoobar();
```

```java
@Adjacency("Foo")
<E extends Bar> Iterator<E> getFoobar();
```

```java
@Adjacency("Foo")
<E extends VertexFrame> Iterator<E> getFoobar();
```

```java
@Adjacency("Foo")
List<Bar> getFoobar();
```

```java
@Adjacency("Foo")
Set<Bar> getFoobar();
```

```java
@Adjacency(value = "Foo", operation = Adjacency.Operation.GET)
Bar obtainFoobar();
```


### Signature: `(Class)`

Valid return types: `VertexFrame` or `Iterator` or `List` or `Set`

Retrieves one or more of the adjacent vertex. If the return type is a specific Frame, an `Object`, or a `VertexFrame` then only the first instance is returned. If the return type is an iterator then it will iterate over all matches vertex. When using an Iterator it is encouraged to use generics.

The Class argument of the method specifes a filter such that only vertex which are of a matching type, or a subtype, to that of the argument will be returned.

example:

```java
@Adjacency("Foo")
Bar getFoobar(Class<? extends Bar> filter);
```

```java
@Adjacency("Foo")
<E extends Bar> E getFoobar(Class<? extends E> filter);
```

```java
@Adjacency("Foo")
<E extends VertexFrame> E getFoobar(Class<? extends E> filter);
```

```java
@Adjacency("Foo")
Iterator<Bar> getFoobar(Class<? extends Bar> filter);
```

```java
@Adjacency("Foo")
<E extends Bar> Iterator<E> getFoobar(Class<? extends E> filter);
```

```java
@Adjacency("Foo")
<E extends VertexFrame> Iterator<E> getFoobar(Class<? extends E> filter);
```

```java
@Adjacency("Foo")
List<Bar> getFoobar(Class<? extends Bar> filter);
```

```java
@Adjacency("Foo")
Set<Bar> getFoobar(Class<? extends Bar> filter);
```

```java
@Adjacency(value = "Foo", operation = Adjacency.Operation.GET)
Bar obtainFoobar(Class<? extends Bar> filter);
```


## REMOVE Operation

Valid method signatures: `( )`, `(VertexFrame)`

Removes any edges which cause an adjacency, leaving the vertex in place.

### Signature: `( )`

Valid return types: `void`

Removes all edges which create any adjacency between the current vertex and any other vertex using the specified label.

example:

```java
@Adjacency("Foo")
void removeFoobar();
```

```java
@Adjacency(value = "Foo", operation = Adjacency.Operation.REMOVE)
void deleteAllFoobar(E vertex);
```


### Signature: `(VertexFrame)`

Valid return types: `void`

Removes all edges which create an adjacency between the current vertex and the vertex specified in the methods argument
and has the specified label.

example:

```java
@Adjacency("Foo")
void removeFoobar(Bar vertex);
```

```java
@Adjacency("Foo")
<E extends Bar> void removeFoobar(E vertex);
```

```java
@Adjacency("Foo")
<E extends VertexFrame> void removeFoobar(E vertex);
```

```java
@Adjacency(value = "Foo", operation = Adjacency.Operation.REMOVE)
void removeFoobar(Bar vertex);
```


## SET Operation

Valid method signatures: `(VertexFrame)`, `(Iterator)`, `(Iterable)`

Creates new edges connected to several vertex and at the same time removes any existing edges. If the any of the vertex
being set are already an adjacency then the edge will be preserved as-is.

### Signature: `(VertexFrame)`

Valid return types: `void`

The argument for this method must be a `VertexFrame` or a class or interface which inherits from that class.

This method will drop any existing edges with the specified label and create a single new edge to the vertex specified.
If the specified vertex is already an adjacency than any edges already connected to it will be preserved. Any newly
created edges will not encode a type.

example:

```java
@Adjacency("Foo")
void setFoobar(Bar vertex);
```

```java
@Adjacency("Foo")
<E extends Bar> void setFoobar(E vertex);
```

```java
@Adjacency(value = "Foo", operation = Adjacency.Operation.SET)
void assignFoobar(Bar vertex);
```

### Signature: `(Iterator)`

Valid return types: `void`

The argument for this method must be an `Iterator` which iterates over vertex Frames. It is suggested you specify a Generic Type for the Iterator for usability.

This method will iterate over all the vertex specified in the Iterator argument and create new edges to connect to it. The edges in the graph will not encode a type.

Any existing edges matching the specified label that do not connect to one of the `VertexFrame` provided by the iterator will be removed.

example:

```java
@Adjacency("Foo")
void setFoobar(Iterator<Bar> vertex);
```

```java
@Adjacency("Foo")
<E extends Bar> void setFoobar(Iterator<? extends E> vertex);
```

```java
@Adjacency("Foo")
<E extends VertexFrame> void setFoobar(Iterator<? extends E> vertex);
```

### Signature: `(Iterable)`

Valid return types: `void`

The argument for this method must be an `Iterable` or a subclass of `Iterable` which iterates over vertex Frames. It is suggested you specify a Generic Type for the Iterator for usability.

Since all Java collections inherit from the `Iterable` interface they can also be used as parameters to these methods.

This method will iterate over all the vertex specified in the `Iterable` argument and create new edges to connect to it. The edges in the graph will not encode a type.

Any existing edges matching the specified label that do not connect to one of the `VertexFrame` provided by the iterator will be removed.

example:

```java
@Adjacency("Foo")
void setFoobar(Iterable<Bar> vertex);
```

```java
@Adjacency("Foo")
void setFoobar(Collection<Bar> vertex);
```

```java
@Adjacency("Foo")
void setFoobar(List<Bar> vertex);
```

```java
@Adjacency("Foo")
void setFoobar(Set<Bar> vertex);
```

```java
@Adjacency("Foo")
<E extends Bar> void setFoobar(Iterable<? extends E> vertex);
```

```java
@Adjacency("Foo")
<E extends VertexFrame> void setFoobar(Iterable<? extends E> vertex);
```


Valid on frames: Vertex

Allowed prefixes: `add`, `get`, `remove`, `set`

Annotation arguments:

`label` - The label assigned to the edge which connects the adjacent nodes.

`direction` - The direction for the edge which creates the adjacency. It can be assigned any of the values from @org.apache.tinkerpop.gremlin.structure.Direction@.


## add prefix

Valid method signatures: `()`, `(<Any Vertex Frame>)`, `(ClassInitializer)`, `(ClassInitializer, ClassInitializer)`

Adds a node as an adjacency to the current node, and the returns the newly connected node.


### ()

Valid return types: `Object` or `VertexFrame`

Creates a new vertex without any type information as well as an untyped edge to connect to it. The newly created VertexFrame is returned. Since it is untyped the return type of the signature can either be `Object` or `VertexFrame`.

example:

```java
@Adjacency("Foo")
VertexFrame addFoobar()
```


### (&lt;Any Vertex Frame&gt;)

Valid return types: *Any Vertex Frame*

Creates a new edge without any type information and connects it between this vertex the vertex specified as an argument to the method. The frame returned is the same as the frame given in the argument, it is only there for compatability with other add methods. This method can also have a `void` return type.

examples:

```java
@Adjacency("Foo")
Bar addFoobar(Bar existingVertex)
```

```java
@Adjacency("Foo")
<E extends Bar> E addFoobar(E existingVertex)
```

```java
@Adjacency("Foo")
<E extends VertexFrame> E addFoobar(E existingVertex)
```


### (ClassInitializer)

Valid return types: *Any Vertex Frame*

Creates a new edge without any type information and connects it between this vertex and a newly created vertex. The newly created vertex will have a type, as well as be initiated, according to the details specified in the ClassInitializer argument. Java generics can, and should, be used to narrow the return type.

example:

```java
@Adjacency("Foo")
Bar addFoobar(ClassInitializer<? extends Bar> vertexInitializer)
```

```java
@Adjacency("Foo")
<E extends Bar> E addFoobar(ClassInitializer<? extends E> vertexInitializer)
```

```java
@Adjacency("Foo")
<E extends VertexFrame> E addFoobar(ClassInitializer<? extends E> vertexInitializer)
```


### (ClassInitializer, ClassInitializer)

Valid return types: *Any Vertex Frame*

Creates a new edge and connects this to a new vertex. The newly created vertex will have a type, as well as be initiated, according to the details specified in the first ClassInitializer argument. Similarly the newly created edge will hava type, and be initiated using, the second ClassInitializer argument. Java generics can, and should, be used to narrow the return type.

example:

```java
@Adjacency("Foo")
Bar addFoobar(ClassInitializer<? extends Bar> vertexInitializer,
              ClassInitializer<?> edgeInitializer)
```

```java
@Adjacency("Foo")
<E extends Bar> E addFoobar(ClassInitializer<? extends E> vertexInitializer,
                            ClassInitializer<?> edgeInitializer)
```

```java
@Adjacency("Foo")
<E extends VertexFrame> E addFoobar(ClassInitializer<? extends E> vertexInitializer,
                                    ClassInitializer<?> edgeInitializer)
```


## get prefix

Valid method signatures: `()`, `(Class)`

Get's one or more adjacent vertex from the graph.


### ()

Valid return types: *Any Vertex Frame* or `Object` or `VertexFrame` or `Iterator`

Retrieves one or more of the adjacent vertex. If the return type is a specific Frame, an `Object`, or a `VertexFrame` then only the first instance is returned. If the return type is an iterator then it will iterate over all matching vertex. When using an Iterator it is encouraged to use generics. The returned frames will always be instantiated as the type encoded in the graph if there is one.

**Note:** If a type is specified that is more specific than the type of the returned element then an exception will be thrown. Therefore the return type specifed should always by the same type, or a super-type, of the expected return type. VertexFrame is always a safe return type for this method.

example:

```java
@Adjacency("Foo")
Bar getFoobar()
```

```java
@Adjacency("Foo")
<E extends Bar> E getFoobar()
```

```java
@Adjacency("Foo")
<E extends VertexFrame> E getFoobar()
```

```java
@Adjacency("Foo")
Iterator<Bar> getFoobar()
```

```java
@Adjacency("Foo")
<E extends Bar> Iterator<E> getFoobar()
```

```java
@Adjacency("Foo")
<E extends VertexFrame> Iterator<E> getFoobar()
```


### (Class)

Valid return types: *Any Vertex Frame* or `Object` or `VertexFrame` or `Iterator`

Retrieves one or more of the adjacent vertex. If the return type is a specific Frame, an `Object`, or a `VertexFrame` then only the first instance is returned. If the return type is an iterator then it will iterate over all matches vertex. When using an Iterator it is encouraged to use generics.

The Class argument of the method specifes a filter such that only vertex which are of a matching type, or a subtype, to that of the argument will be returned.

example:

```java
@Adjacency("Foo")
Bar getFoobar(Class<? extends Bar> filter)
```

```java
@Adjacency("Foo")
<E extends Bar> E getFoobar(Class<? extends E> filter)
```

```java
@Adjacency("Foo")
<E extends VertexFrame> E getFoobar(Class<? extends E> filter)
```

```java
@Adjacency("Foo")
Iterator<Bar> getFoobar(Class<? extends Bar> filter)
```

```java
@Adjacency("Foo")
<E extends Bar> Iterator<E> getFoobar(Class<? extends E> filter)
```

```java
@Adjacency("Foo")
<E extends VertexFrame> Iterator<E> getFoobar(Class<? extends E> filter)
```


## remove prefix

Valid method signatures: `(<Any Vertex Frame>)`

Removes any edges which cause an adjacency.


### (&lt;Any Vertex Frame&gt;)

Valid return types: `void`

Removes any edges which create an adjacency between the vurrent vertex and the vertex specified in the methods argument.

example:

```java
@Adjacency("Foo")
void removeFoobar(Bar vertex)
```

```java
@Adjacency("Foo")
<E extends Bar> void removeFoobar(E vertex)
```

```java
@Adjacency("Foo")
<E extends VertexFrame> void removeFoobar(E vertex)
```


## set prefix

Valid method signatures: `(Iterator)`

Creates new edges connected to several vertex.


### (Iterator)

Valid return types: `void`

The argument for this method must be an Iterator which iterates over any vertex Frames. It is suggested you specify a Generic Type for the Iterator for usability.

This method will iterate over all the vertex specified in the Iterator argument and create new edges to connect to it. The edges in the graph will not encode a type.

example:

```java
@Adjacency("Foo")
void setFoobar(Iterator<Bar> vertex)
```

```java
@Adjacency("Foo")
<E extends Bar> void setFoobar(Iterator<? extends E> vertex)
```

```java
@Adjacency("Foo")
<E extends VertexFrame> void setFoobar(Iterator<? extends E> vertex)
```


Çerveler üzerinde geçerli:**Köşe**

İşlem otomatik olduğunda izin verilen önekler: `add`, `get`, `remove`, `set`

Açıklama argümanları:

`label`- Bitişik düğümleri birbirine bağlayan kenara atanan etiketler.

`direction` - The direction for the edge which creates the adjacency. It can be assigned any of the values from `org.apache.tinkerpop.gremlin.structure.Direction`.

`operation`- Yöntemin gerçekleştireceği süreç. Aşağıdakilerden biri olmalıdır: `GET`, `ADD`, `SET`, `REMOVE`, `AUTO`. Varsayılan değer `AUTO`.

örnek:

```java
@Adjacency("foo")
//Method declared here
```

## İşlem EKLEME

Valid method signatures: `( )`, `(VertexFrame)`, `(ClassInitializer)`, `(ClassInitializer, ClassInitializer)`, `(VertexFrame, ClassInitializer)`

Adds a node as an adjacency to the current node, and the returns the newly connected node.

### İmza: `( )`

Geçerli dönüş tipleri: `VertexFrame`

Creates a new vertex without any type information as well as an untyped edge to connect to it. The newly created `VertexFrame` is returned.

!!! note Since the returned `VertexFrame` is always untyped the return type must be either `VertexFrame` or `TVertex` specifically.

örnek:

```java
@Adjacency("Foo")
VertexFrame addFoobar();
```

```java
@Adjacency(value = "Foo", operation = Adjacency.Operation.ADD)
VertexFrame includeFoobar();
```

### İmza: `(VertexFrame)`

Geçerli dönüş tipleri: `VertexFrame` or `void`

Creates a new edge without any type information and connects it between this vertex the vertex specified as an argument to the method. The frame returned is the same as the frame given in the argument, it is only there for compatability with other add methods. Bu yöntem `void` dönüş tipine de sahip olabilir.

örnek:

```java
@Adjacency("Foo")
BarVertex addFoobar(BarVertex existingVertex);
```

```java
@Adjacency("Foo")
<E extends BarVertex> E addFoobar(E existingVertex);
```

```java
@Adjacency("Foo", direction = Direction.IN)
<E extends VertexFrame> E addFoobar(E existingVertex);
```

```java
@Adjacency(value = "Foo", operation = Adjacency.Operation.ADD)
void includeFoobar(BarVertex existingVertex);
```

### Signature: `(ClassInitializer)`

Geçerli dönüş tipleri: `VertexFrame`

Herhangi bir tür bilgisi olmadan yeni bir kenar oluşturur ve bu köşe ile yeni oluşturulan bir köşe arasına bağlar. The newly created vertex will have a type, as well as be initiated, according to the details specified in the ClassInitializer argument. Java generics can, and should, be used to narrow the return type.

örnek:

```java
@Adjacency("Foo")
BarVertex addFoobar(ClassInitializer<? extends BarVertex> vertexInitializer);
```

```java
@Adjacency("Foo")
<E extends BarVertex> E addFoobar(ClassInitializer<? extends E> vertexInitializer);
```

```java
@Adjacency("Foo", direction = Direction.IN)
<E extends VertexFrame> E addFoobar(ClassInitializer<? extends E> vertexInitializer);
```

```java
@Adjacency(value = "Foo", operation = Adjacency.Operation.ADD)
BarVertex includeFoobar(ClassInitializer<? extends BarVertex> vertexInitializer);
```

### Signature: `(ClassInitializer, ClassInitializer)`

Valid return types: `VertexFrame`

Yeni bir kenar oluşturur ve bunu yeni bir köşeye bağlar. The newly created vertex will have a type, as well as be initiated, according to the details specified in the first ClassInitializer argument. Similarly the newly created edge will hava a type, and be initiated, using the second ClassInitializer argument. Java generics can, and should, be used to narrow the return type.

example:

```java
@Adjacency("Foo")
BarVertex addFoobar(ClassInitializer<? extends BarVertex> vertexInitializer,
                    ClassInitializer<? extends FooEdge> edgeInitializer);
```

```java
@Adjacency("Foo")
<E extends BarVertex> E addFoobar(ClassInitializer<? extends E> vertexInitializer,
                                  ClassInitializer<? extends FooEdge> edgeInitializer);
```

```java
@Adjacency("Foo", direction = Direction.IN)
<E extends VertexFrame> E addFoobar(ClassInitializer<? extends E> vertexInitializer,
                                    ClassInitializer<? extends FooEdge> edgeInitializer);
```

```java
@Adjacency(value = "Foo", operation = Adjacency.Operation.ADD)
BarVertex includeFoobar(ClassInitializer<? extends BarVertex> vertexInitializer,
                        ClassInitializer<? extends FooEdge> edgeInitializer);
```

### Signature: `(VertexFrame, ClassInitializer)`

Valid return types: `VertexFrame` or `void`

Creates a new edge and connects this to an existing vertex. The newly created edge will have a type, as well as be initiated, according to the details specified in the `ClassInitializer` argument. The `VertexFrame` specified in the first argument will simply be returned. A `void` return type is also valid. Java generics can, and should, be used to narrow the return type as well as to restrict the `ClassInitializer`.

example:

```java
@Adjacency("Foo")
BarVertex addFoobar(BarVertex vertex,
                    ClassInitializer<? extends FooEdge> edgeInitializer);
```

```java
@Adjacency("Foo")
<E extends BarVertex> E addFoobar(E vertex,
                                  ClassInitializer<? extends FooEdge> edgeInitializer);
```

```java
@Adjacency("Foo", direction = Direction.IN)
<E extends VertexFrame> E addFoobar(BarVertex vertex,
                                    ClassInitializer<? extends FooEdge> edgeInitializer);
```

```java
@Adjacency(value = "Foo", operation = Adjacency.Operation.ADD)
void includeFoobar(BarVertex vertex,
                        ClassInitializer<? extends FooEdge> edgeInitializer);
```

## GET Operation

Valid method signatures: `()`, `(Class)`

Get's one or more adjacent vertex from the graph.

### Signature: `( )`

Valid return types: `VertexFrame` or `Iterator` or `List` or `Set`

Retrieves one or more of the adjacent edges. If the return type is a single Frame then only the first instance is returned. If the return type is an `Iterator` or `List` or `Set` then it will supply all matching vertex. When using an `Iterator` or `List` or `Set` it is encouraged, but not required, to use generics. The returned frames will always be instantiated as the type encoded in the graph if there is one.

!!! note If a type is encoded in the in the graph is a superclass of the returned element then an exception will be thrown. Therefore the return type specifed should always by the same type, or a superclass, of the expected return type. VertexFrame is always a safe return type for this method.

example:

```java
@Adjacency("Foo")
BarVertex getFoobar();
```

```java
@Adjacency("Foo")
<E extends BarVertex> E getFoobar();
```

```java
@Adjacency("Foo")
<E extends VertexFrame> E getFoobar();
```

```java
@Adjacency("Foo")
Iterator<BarVertex> getFoobar();
```

```java
@Adjacency("Foo")
<E extends BarVertex> Iterator<E> getFoobar();
```

```java
@Adjacency("Foo")
<E extends VertexFrame> Iterator<E> getFoobar();
```

```java
@Adjacency("Foo")
List<BarVertex> getFoobar();
```

```java
@Adjacency("Foo", direction = Direction.IN)
Set<BarVertex> getFoobar();
```

```java
@Adjacency(value = "Foo", operation = Adjacency.Operation.GET)
BarVertex obtainFoobar();
```

### Signature: `(Class)`

Valid return types: `VertexFrame` or `Iterator` or `List` or `Set`

Retrieves one or more of the adjacent vertex. If the return type is a single `VertexFrame` then only the first instance is returned. If the return type is an `Iterator` then it will iterate over all matching vertex. When using an Iterator it is encouraged to use generics.

!!! note The Class argument of the method specifes a filter such that only vertex which are of a matching type, or a subtype, to that of the argument will be returned.

example:

```java
@Adjacency("Foo")
BarVertex getFoobar(Class<? extends BarVertex> filter);
```

```java
@Adjacency("Foo")
<E extends BarVertex> E getFoobar(Class<? extends E> filter);
```

```java
@Adjacency("Foo")
<E extends VertexFrame> E getFoobar(Class<? extends E> filter);
```

```java
@Adjacency("Foo")
Iterator<BarVertex> getFoobar(Class<? extends BarVertex> filter);
```

```java
@Adjacency("Foo")
<E extends BarVertex> Iterator<E> getFoobar(Class<? extends E> filter);
```

```java
@Adjacency("Foo")
<E extends VertexFrame> Iterator<E> getFoobar(Class<? extends E> filter);
```

```java
@Adjacency("Foo")
List<BarVertex> getFoobar(Class<? extends BarVertex> filter);
```

```java
@Adjacency("Foo", direction = Direction.IN)
Set<BarVertex> getFoobar(Class<? extends BarVertex> filter);
```

```java
@Adjacency(value = "Foo", operation = Adjacency.Operation.GET)
BarVertex obtainFoobar(Class<? extends BarVertex> filter);
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

Removes all edges which create an adjacency between the current vertex and the vertex specified in the methods argument and has the specified label.

example:

```java
@Adjacency("Foo")
void removeFoobar(BarVertex vertex);
```

```java
@Adjacency("Foo")
<E extends BarVertex> void removeFoobar(E vertex);
```

```java
@Adjacency("Foo", direction = Direction.IN)
<E extends VertexFrame> void removeFoobar(E vertex);
```

```java
@Adjacency(value = "Foo", operation = Adjacency.Operation.REMOVE)
void removeFoobar(BarVertex vertex);
```

## SET Operation

Valid method signatures: `(VertexFrame)`, `(Iterator)`, `(Iterable)`

Creates new edges connected to several vertex and at the same time removes any existing edges. If the any of the vertex being set are already an adjacency then the edge will still be removed and recreated.

!!! warning The set operation methods are equivelant to removing all existing edges of the specified label and direction connected to the current vertex and then call add on all the vertex specified by the method's parameters. You will loose any existing properties set on all dropped edges as well.

### Signature: `(VertexFrame)`

Valid return types: `void`

The argument for this method must be a `VertexFrame` or a class or interface which inherits from that class.

This method will drop all existing edges with the specified label and create a single new edge to the vertex specified. Of course if the direction is set to both then two new edges are created instead, one in each direction. Any newly created edges will not encode a type.

!!! warning If the vertex specified in the method's argument already has an edge forming an adjacency with this vertex and has matching direction and label attributes then that edge will be removed and recreated as a blank untyped edge with the same label.

example:

```java
@Adjacency("Foo")
void setFoobar(BarVertex vertex);
```

```java
@Adjacency("Foo", direction = Direction.IN)
<E extends BarVertex> void setFoobar(E vertex);
```

```java
@Adjacency(value = "Foo", operation = Adjacency.Operation.SET)
void assignFoobar(BarVertex vertex);
```

### Signature: `(Iterator)`

Valid return types: `void`

The argument for this method must be an `Iterator` which iterates over Vertex Frames. It is suggested you specify a Generic Type for the Iterator for usability.

This method will drop all existing edges with the specified lable then iterate over all the vertex specified in the `Iterator` argument and create new edges to connect to them. The edges in the graph will not encode a type.

!!! warning If any of the vertex specified by this method's argument already has an edge forming an adjacency with this vertex and has matching direction and label attributes then that edge will be removed and recreated as a blank untyped edge with the same label.

example:

```java
@Adjacency("Foo")
void setFoobar(Iterator<BarVertexr> vertex);
```

```java
@Adjacency("Foo", direction = Direction.IN)
<E extends BarVertex> void setFoobar(Iterator<? extends E> vertex);
```

```java
@Adjacency("Foo", operation = Adjacency.Operation.SET)
<E extends VertexFrame> void includeFoobar(Iterator<? extends E> vertex);
```

### Signature: `(Iterable)`

Valid return types: `void`

The argument for this method must be an `Iterable` or a subclass of `Iterable` which iterates over vertex Frames. It is suggested you specify a Generic Type for the Iterator for usability. Since all Java collections inherit from the `Iterable` interface any collection type can also be used as a parameter to this methods.

This method will drop all existing edges with the specified lable then iterate over all the vertex specified in the `Iterable` argument and create new edges to connect to them. The edges in the graph will not encode a type.

!!! warning If any of the vertex specified by this method's argument already has an edge forming an adjacency with this vertex and has matching direction and label attributes then that edge will be removed and recreated as a blank untyped edge with the same label.

example:

```java
@Adjacency("Foo")
void setFoobar(Iterable<BarVertex> vertex);
```

```java
@Adjacency("Foo")
void setFoobar(Collection<BarVertex> vertex);
```

```java
@Adjacency("Foo")
void setFoobar(List<BarVertex> vertex);
```

```java
@Adjacency("Foo")
void setFoobar(Set<BarVertex> vertex);
```

```java
@Adjacency("Foo", direction = Direction.IN)
<E extends BarVertex> void setFoobar(Iterable<? extends E> vertex);
```

```java
@Adjacency("Foo", operation = Adjacency.Operation.SET)
<E extends VertexFrame> void includeFoobar(Iterable<? extends E> vertex);
```
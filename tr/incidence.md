Çerveler üzerinde geçerli:**Vertex**

İşlem AUTO olduğunda izin verilen önekler: `add`, `get`, `remove`

Açıklama argümanları:

`label` - Bitişik düğümleri birbirine bağlayan kenara atanan etiket.

`direction` - Bitişiklik oluşturan kenar yönü. `org.apache.tinkerpop.gremlin.structure.Direction` değerlerinden herhangi biri atanabilir.

`operation` - Yöntemin gerçekleştireceği işlem. Şunlardan biri olmalı: `GET`, `ADD`, `SET`, `REMOVE`, `AUTO`. Varsayılan değer `AUTO`.

örnek:

```java
@Incidence("foo")
//Method declared here
```

## İşlem EKLEME

Geçerli yöntem imzaları: `( )`, `(VertexFrame)`, `(ClassInitializer)`, `(VertexFrame, ClassInitializer)`, `(ClassInitializer, ClassInitializer)`

Bir düğüme bir kenar ekler ve yeni `EdgeFrame`'i döndürür.

### İmza: `( )`

Geçerli dönüş tipleri: `EdgeFrame`

Herhangi bir tür bilgisi olmaksızın yeni bir vertex ve ona bağlantı için türsüz bir kenar oluşturur. Yeni oluşturulmuş `TEdge` geri döndürülür.

!!! not Döndükten sonra `VertexFrame` daima türsüz olduğundan dönüş türünün özellikle `VertexFrame` veya `TVertex` olması gerekiyor.

```java
@Incidence("Foo")
EdgeFrame addFoobar();
```

```java
@Incidence(value = "Foo", operation = Adjacency.Operation.ADD)
TEdge addFoobar();
```

### İmza: `(VertexFrame)`

Geçerli dönüş tipleri: `EdgeFrame`

Herhangi bir tür bilgisi olmaksızın yeni bir kenar oluşturur ve onu, bu vertex'in parametre olarak belirtilen yöntemine bağlar. Döndürülen çerçeve, yeni oluşturulan `TEdge`'dir.

örnekler:

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

### İmza: `(ClassInitializer)`

Geçerli dönüş tipleri: `EdgeFrame`

Herhangi bir tür bilgisi olmaksızın yeni bir kenar oluşturur ve bu vertex ile yeni oluşturulan bir vertex arasına bağlar. Yeni oluşturulan vertex, ClassInitializer değişkeninde belirtilen ayrıntılara göre başlatılacağı gibi bir türe sahip olacaktır. Java generics dönüş türünü daraltmak için kullanılabilir ve kullanılması gerekir. Döndürülen nesne yeni oluşturulan `TEdge` olacaktır.

örnek:

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

### İmza: `(VertexFrame, ClassInitializer)`

Geçerli dönüş tipleri: `EdgeFrame`

Yeni bir kenar oluşturur ve bunu mevcut bir vertex'e bağlar. Yeni oluşturulan kenar, ClassInitializer değişkeninde belirtilen ayrıntılara göre başlatılacağı gibi bir türe sahip olacaktır. Java generics dönüş türünü daraltmak için kullanılabilir ve kullanılması gerekir. Döndürülen nesne, yeni oluşturulan `EdgeFrame` olacaktır.

örnek:

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

Yeni bir kenar oluşturur ve bunu yeni bir köşeye bağlar. The newly created vertex will have a type, as well as be initiated, according to the details specified in the first ClassInitializer argument. Similarly the newly created edge will hava a type, and be initiated, using the second ClassInitializer argument. Java generics can, and should, be used to narrow the return type. Döndürülen nesne, yeni oluşturulan `EdgeFrame` olacaktır.

örnek:

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

## İŞLEM alma

Geçerli yöntem imzaları: `()`, `(Class)`

Grafikten bir veya daha fazla bitişik kenarı alın.

### İmza: `( )`

Valid return types: `VertexFrame` or `Iterator` or `List` or `Set`

Retrieves one or more of the adjacent vertex. If the return type is a single Frame then only the first instance is returned. If the return type is an `Iterator` or `List` or `Set` then it will supply all matching edges. When using an `Iterator` or `List` or `Set` it is encouraged, but not required, to use generics. The returned frames will always be instantiated as the type encoded in the graph if there is one.

!!! note If a type is encoded in the in the graph is a superclass of the returned element then an exception will be thrown. Therefore the return type specifed should always by the same type, or a superclass, of the expected return type. VertexFrame is always a safe return type for this method.

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

Retrieves one or more of the adjacent edges. If the return type is a single `EdgeFrame` then only the first instance is returned. If the return type is an `Iterator` then it will iterate over all matching vertex. When using an Iterator it is encouraged to use generics.

!!! note The Class argument of the method specifes a filter such that only vertex which are of a matching type, or a subtype, to that of the argument will be returned.

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

## İşlem KALDIRMA

Geçerli yöntem imzaları: `(EdgeFrame)`

Removes an edges which cause an adjacency, leaving the vertex in place.

### Signature: `(EdgeFrame)`

Valid return types: `void`

Removes the edge specified in the argument. This is entirely equivelant to just calling the `remove()` method on the `EdgeFrame`

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
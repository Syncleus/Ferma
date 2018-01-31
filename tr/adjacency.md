Çerveler üzerinde geçerli:**Köşe**

İşlem otomatik olduğunda izin verilen önekler: `add`, `get`, `remove`, `set`

Açıklama argümanları:

`label`- Bitişik düğümleri birbirine bağlayan kenara atanan etiketler.

`yön` - Bitişiklik oluşturan kenar yönü. `org.apache.tinkerpop.gremlin.structure.Direction` değerlerinden herhangi biri atanabilir.

`operation`- Yöntemin gerçekleştireceği süreç. Aşağıdakilerden biri olmalıdır: `GET`, `ADD`, `SET`, `REMOVE`, `AUTO`. Varsayılan değer `AUTO`.

örnek:

```java
@Adjacency("foo")
//Method declared here
```

## İşlem EKLEME

Valid method signatures: `( )`, `(VertexFrame)`, `(ClassInitializer)`, `(ClassInitializer, ClassInitializer)`, `(VertexFrame, ClassInitializer)`

Geçerli düğüme bitişiklik olarak bir düğüm ekler, ve yeni bağlantılı düğümü döndürür.

### İmza: `( )`

Geçerli dönüş tipleri: `VertexFrame`

Herhangi bir tür bilgisi olmaksızın yeni bir köşe oluşturur ve ona bağlantı için biçemsiz bir kenar da oluşturur. Yeni oluşturulmuş `VertexFrame` geri döndürülür.

!!! not Döndükten sonra `VertexFrame` daima türsüz olduğundan dönüş türünün özellikle `VertexFrame` veya `TVertex` olması gerekir.

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

Herhangi bir tür bilgisi olmaksızın yeni bir kenar oluşturur ve onu, bu vertex'in argüman olarak belirtilen yöntemine bağlar. Döndürülen çerçeve argümanda verilen çerçeveyle aynıdır, yalnızca diğer ekleme yöntemleriyle uyumluluk için vardır. Bu yöntem `void` dönüş tipine de sahip olabilir.

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

### İmza: `(ClassInitializer)`

Geçerli dönüş tipleri: `VertexFrame`

Herhangi bir tür bilgisi olmadan yeni bir kenar oluşturur ve bu köşe ile yeni oluşturulan bir köşe arasına bağlar. Yeni oluşturulan vertex, ClassInitializer değişkeninde belirtilen ayrıntılara göre başlatılacağı gibi bir türe sahip olacaktır. Java generics dönüş türünü daraltmak için kullanılabilir ve kullanılması gerekir.

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

### İmza: `(ClassInitializer, ClassInitializer)`

Geçerli dönüş tipleri: `VertexFrame`

Yeni bir kenar oluşturur ve bunu yeni bir köşeye bağlar. Yeni oluşturulan vertex, ilk ClassInitializer değişkeninde belirtilen ayrıntılara göre başlatılacağı gibi bir türe sahip olacaktır. Benzer şekilde yeni oluşturulan kenar bir tür olur ve ikinci ClassInitializer değişkeni kullanılarak başlatılır. Java generics dönüş türünü daraltmak için kullanılabilir ve kullanılması gerekir.

örnek:

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

### İmza: `(VertexFrame, ClassInitializer)`

Geçerli dönüş tipleri: `VertexFrame` veya `void`

Yeni bir kenar oluşturur ve bunu mevcut bir vertex'e bağlar. Yeni oluşturulan kenar, `ClassInitializer` değişkeninde belirtilen ayrıntılara göre başlatılacağı gibi bir türe sahip olacaktır. İlk değişkende belirtilen `VertexFrame` basitçe geri döndürülür. Bir `void` dönüş tipi de geçerlidir. Java generics, dönüş türünü daraltmanın yanı sıra `ClassInitializer` 'ı kısıtlamak için kullanılabilir ve kullanılması gerekir.

örnek:

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

## GET İşlemi

Geçerli yöntem imzaları: `()`, `(Class)`

Grafiğin bir veya daha fazla vertex'ini al.

### İmza: `( )`

Geçerli dönüş tipleri: `VertexFrame` veya `Iterator` veya `List` veya `Set`

Bitişik kenarlardan bir veya daha fazlasını alır. Dönüş türü tek bir Çerçeve ise, yalnızca ilk örneğini döndürür. Eğer dönüş tipi bir `Iterator` veya `List` veya `Set` ise, eşleşen tüm vertex'i sağlayacaktır. Bir `Iterator` veya `List` veya `Set` kullanırken, generics kullanmak teşvik edilir, ancak gerekli değildir. Döndürülen kareler her zaman, grafikte kodlanmış tür olarak örneği oluşturulur.

!!! note Grafikte bir tür kodlanmışsa, döndürülen öğenin bir üst sınıfı olur ve o zaman bir istisna fırlatılır. Bu nedenle, belirtilen dönüş türü her zaman aynı türde veya beklenen dönüş türünün bir üst sınıfıyla oluşturulmalıdır. VertexFrame bu yöntem için her zaman güvenli bir dönüş tipidir.

örnek:

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

### İmza: `(Class)`

Geçerli dönüş tipleri: `VertexFrame` veya `Iterator` veya `List` veya `Set`

Bitişik vertex'in bir veya daha fazlasını alır. Dönüş türü tek bir `VertexFrame` ise yalnızca ilk örneği döndürür. Dönüş türü bir `Iterator` ise, eşleşen tüm vertex üzerinde yinelenecektir. Bir Iterator kullanırken generics kullanılması için teşvik edilir.

!!! not Yöntemin Sınıf argümanı, yalnızca bir eşleme türünün ya da bir alt türün değişkeninin vertexinin döndürülmesine izin veren bir filtre belirtir.

örnek:

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

## REMOVE İşlemi

Geçerli yöntem imzaları: `( )`, `(VertexFrame)`

Vertex'i yerinde bırakarak, bir bitişiklik oluşturan kenarları kaldırır.

### İmza: `( )`

Geçerli dönüş tipleri: `void`

Belirtilen etiketi kullanarak geçerli vertex ile diğer herhangi bir vertex arasında herhangi bir bitişiklik oluşturan tüm kenarları kaldırır.

örnek:

```java
@Adjacency("Foo")
void removeFoobar();
```

```java
@Adjacency(value = "Foo", operation = Adjacency.Operation.REMOVE)
void deleteAllFoobar(E vertex);
```

### İmza: `(VertexFrame)`

Geçerli dönüş tipleri: `void`

Geçerli vertex ve methods değişkeninde belirtilen vertex arasında bir bitişiklik oluşturan ve belirtilen etikete sahip olan tüm kenarları kaldırır.

örnek:

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

## SET İşlemi

Geçerli yöntem imzları: `(VertexFrame)`, `(Iterator)`, `(Iterable)`

Birkaç vertex'e bağlı yeni kenarlar oluşturur ve aynı zamanda mevcut tüm kenarları kaldırır. Ayarlanan vertex'in herhangi biri önceden bir bitişikliğe sahipse, kenar yine de kaldırılacak ve yeniden oluşturulacaktır.

!!! warning The set operation methods are equivelant to removing all existing edges of the specified label and direction connected to the current vertex and then call add on all the vertex specified by the method's parameters. Düşen tüm kenarlar üzerinde var olan tüm özellikleri de kaybedeceksiniz.

### İmza: `(VertexFrame)`

Geçerli dönüş tipleri: `void`

Bu yöntemin değişkeni bir `VertexFrame` olmalıdır veya bir sınıf veya o sınıftan miras alan bir arayüz olmalıdır.

Bu yöntem, varolan tüm kenarları belirtilen etiketi kullanarak düşürür ve belirtilen vertex'e tek bir yeni kenar oluşturur. Of course if the direction is set to both then two new edges are created instead, one in each direction. Yeni oluşturulan kenarlar bir türe kodlamaz.

!!! uyarı Yöntem argümanında belirtilen vertex, bu vertex ile bir bitişiklik oluşturan bir kenara zaten sahipse ve yön ve etiket özniteliklerine uyuyorsa o kenar kaldırılacak ve aynı etiketle boş bir başlıksız kenar olarak yeniden oluşturulacaktır.

örnek:

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

### İmza: `(Iterator)`

Geçerli dönüş tipleri: `void`

Bu yöntemin argümanı, Vertex Frames üzerinde yinelenen bir `Iterator` olmalıdır. Kullanılabilirlik için Iterator'de bir Generic Tip belirtmeniz önerilir.

Bu yöntem var olan tüm kenarları belirtilen etiketi kullanarak düşürür, ardından `Iterator` değişkeninde belirtilen tüm vertex üzerinde tekrarlar ve onlara bağlanmak için yeni kenarlıklar oluşturur. Grafiğin kenarları bir türe kodlamaz.

!!! uyarı Bu yöntemin argümanı tarafından belirtilen vertex'in herhangi biri zaten bu köşeyle bir bitişiklik oluşturan bir kenara sahipse ve yön ve etiket özniteliklerine uyuyorsa o kenar kaldırılacak ve aynı etiketle birlikte boş bir başlıksız kenar olarak yeniden oluşturulacaktır.

örnek:

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

### İmza: `(Iterable)`

Geçerli dönüş tipleri: `void`

Bu yöntemin argümanı, `Iterable` veya `Iterable`'ın alt sınıfı olmalıdır ve bu da, vertex Çerçeveleri üzerinde yinelenir. It is suggested you specify a Generic Type for the Iterator for usability. Since all Java collections inherit from the `Iterable` interface any collection type can also be used as a parameter to this methods.

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
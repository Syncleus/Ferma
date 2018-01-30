Çerçeveler için geçerlidir: **Edge**

İşlem otomatik olduğunda izin verilen önekler: `get`

The `@OutVertex` takes no parameters and is used only on get methods that themself take no parameters. It specifies the `VertexFrame` at the tail of an edge.

örnek:

```java
@OutVertex
//Method declared here
```

## İŞLEM alma

Geçerli yöntem imzaları: `( )`

### İmza: `( )`

Geçerli dönüş tipleri: `VertexFrame`.

Get the tail vertex of the edge.

örnek:

```java
@OutVertex
BarVertex getFoobar();
```
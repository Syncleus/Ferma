Çerçeveler için geçerlidir: **Edge**

İşlem otomatik olduğunda izin verilen önekler: `get`

The `@InVertex` takes no parameters and is used only on get methods that themself take no parameters. It specifies the `VertexFrame` at the head of an edge.

example:

```java
@InVertex
//Method declared here
```

## İŞLEM alma

Geçerli yöntem imzaları: `( )`

### İmza: `( )`

Geçerli dönüş tipleri: `VertexFrame`.

Kenarın baş kısmını alın.

örnek:

```java
@InVertex
BarVertex getFoobar();
```
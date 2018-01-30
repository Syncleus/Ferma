Çerçeveler için geçerlidir: **Edge**

İşlem otomatik olduğunda izin verilen önekler: `get`

The `@OutVertex` takes no parameters and is used only on get methods that themself take no parameters. It specifies the `VertexFrame` at the tail of an edge.

örnek:

```java
@OutVertex
//Method declared here
```

## İŞLEM alma

Valid method signatures: `( )`

### Signature: `( )`

Valid return types: `VertexFrame`.

Get the tail vertex of the edge.

example:

```java
@OutVertex
BarVertex getFoobar();
```
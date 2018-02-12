Çerçeveler için geçerlidir: **Edge**

İşlem otomatik olduğunda izin verilen önekler: `get`

`@InVertex` parametre almaz ve yalnızca kendilerinin parametre almadığı get yöntemleri için kullanılır. Bir kenarın başındaki `VertexFrame`'i belirtir.

örnek:

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
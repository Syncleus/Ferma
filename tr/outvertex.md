Çerçeveler için geçerlidir: **Edge**

İşlem otomatik olduğunda izin verilen önekler: `get`

`@OutVertex` parametre almaz ve yalnızca kendilerinin parametre almadığı get yöntemleri için kullanılır. Bir kenarın sonundaki `VertexFrame`'i belirtir.

örnek:

```java
@OutVertex
//Method declared here
```

## İŞLEM alma

Geçerli yöntem imzaları: `( )`

### İmza: `( )`

Geçerli dönüş tipleri: `VertexFrame`.

Kenarın kuyruk vertex'ini al.

örnek:

```java
@OutVertex
BarVertex getFoobar();
```
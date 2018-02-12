Çerçeveler üzerinde geçerlidir: **Edge** and **vertex**

`@GraphElement` açıklaması herhangi bir parametre gerektirmez ve sınıf bildiriminizden önce bir `EdgeFrame` veya `VertexFrame`'ya yerleştirilir. Bu ek açıklama çoğunlukla, çerçeveleri tanımlamak için bir pakette sınıfları tararken kullanılır.

örnek:

```java
@GraphElement
public interface FooVertex extends VertexFrame {
    //Methods goes here
}
```
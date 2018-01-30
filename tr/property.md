Çerçeveler üzerinde geçerlidir: **Kenar** ve **köşe**

İşlem otomatik olduğunda izin verilen önekler: `add`, `get`, `remove`, `set`

Açıklama argümanları:

`value` - Özelliğin adı

`operation` - Yöntemin gerçekleştireceği işlem. Aşağıdakilerden biri olmalı: `GET`, `SET`, `REMOVE`, `AUTO`. Varsayılan değer `AUTO`.

Aşağıdakiler kullanılan yöntemin adlandırılmış özelliğe bağlanmasını sağlar `foo`:

```java
@Özellik("foo")
//Burada bildirilen yöntem
```

## İŞLEM alma

Geçerli yöntem imzaları: `( )`

### İmza: `( )`

Geçerli dönüş türleri: `Object` veya herhangi bir basit tür.

Get the property value of an element. Used when property is not a boolean value.

örnek:

```java
@Property("Foo")
Bar getFoobar();
```

```java
@Property("Foo")
<E extends Bar> E getFoobar();
```

```java
@Property("Foo")
<E> E getFoobar();
```

```java
@Property(value = "Foo", operation = Property.Operation.GET)
Bar obtainFoobar();
```

## İşlem ALMA (önektir)

Geçerli yöntem imzaları: `( )`

### İmza: `( )`

Geçerli dönüş tipleri: `boolean`

Bir öğenin özellik değerini alın. Özellik bir mantıksal değer olduğunda kullanılır.

örnek:

```java
@Property("Foobared")
boolean isFoobared();
```

```java
@Property(value = "Foo", operation = Property.Operation.GET)
boolean obtainFoobared();
```

## İşlem AYARLAMA

Geçerli yöntem imzaları: `(Object)`

### İmza: `(Object)`

Geçerli dönüş tipleri: `void`

Bir öğenin özellik değerini ayarlama. Bağımsız değişken, temel grafik tarafından kabul edilen herhangi bir sınıf olabilir.

örnek:

```java
@Property("Foo")
void setFoobar(Bar foobar);
```

```java
@Property("Foo")
<E extends Bar> void setFoobar(E foobar);
```

```java
@Property("Foo")
<E extends VectorFrame> void setFoobar(E foobar);
```

```java
@Property("Foo")
void setFoobar(Bar foobar);
```

```java
@Property(value = "Foo", operation = Property.Operation.SET)
void applyFoobar(Bar foobar);
```

## İşlem KALDIRMA

Geçerli yöntem imzaları: `( )`

### İmza: `( )`

Geçerli dönüş tipleri: `void`

Bir öğenin özelliklerini kaldırın.

example:

```java
@Property("Foo")
void removeFoobar();
```

```java
@Property(value = "Foo", operation = Property.Operation.REMOVE)
void removeFoobar();
```
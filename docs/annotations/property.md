Valid on frames: **Edge** and **Vertex**

Allowed prefixes when operation is AUTO: `get`, `is`, `can`, `set`, `remove`

Annotation arguments:

`value` - The name of the property

`operation` - The operation the method will perform. Must be one of the following: `GET`, `SET`, `REMOVE`, `AUTO`.
Defaults to `AUTO`.

The following would bind the method it is used on to the property named `foo`:

```java
@Property("foo")
//Method declared here
```


## GET Operation

Valid method signatures: `( )`


### Signature: `( )`

Valid return types: `Object` or any primitive.

Get the property value of an element. Used when property is not a boolean value.

example:

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


## GET Operation (is prefix)

Valid method signatures: `( )`


### Signature: `( )`

Valid return types: `boolean`

Get the property value of an element. Used when property is a boolean value.

example:

```java
@Property("Foobared")
boolean isFoobared();
```

```java
@Property(value = "Foo", operation = Property.Operation.GET)
boolean obtainFoobared();
```


## SET Operation

Valid method signatures: `(Object)`


### Signature: `(Object)`

Valid return types: `void`

Set the property value of an element. The argument can be any class accepted by the underlying graph.

example:

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


## REMOVE Operation

Valid method signatures: `( )`


### Signature: `( )`

Valid return types: `void`

Remove the property of an element.

example:

```java
@Property("Foo")
void removeFoobar();
```

```java
@Property(value = "Foo", operation = Property.Operation.REMOVE)
void removeFoobar();
```

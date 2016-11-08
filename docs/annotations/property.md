Valid on frames: Edge and Vertex

Allowed prefixes: `get`, `is`, `can`, `set`, `remove`

Annotation arguments:

`value` - The name of the property

The following would bind the method it is used on to the property named `foo`:

```java
@Property("foo")
//Method declared here
```


## get prefix

Valid method signatures: `()`


### ()

Valid return types: *Any Object*

Get the property value of an element. Used when property is not a boolean value.

example:

```java
@Property("Foo")
Bar getFoobar()
```

```java
@Property("Foo")
<E extends Bar> E getFoobar()
```

```java
@Property("Foo")
<E> E getFoobar()
```


## is prefix

Valid method signatures: `()`


### ()

Valid return types: `boolean`

Get the property value of an element. Used when property is a boolean value.

example:

```java
@Property("Foobared")
boolean isFoobared()
```


## set prefix

Valid method signatures: `(Object)`


### (Object)

Valid return types: `void`

Set the property value of an element. The argument can be any class accepted by the underlying graph.

example:

```java
@Property("Foo")
void setFoobar(Bar foobar)
```

```java
@Property("Foo")
<E extends Bar> void setFoobar(E foobar)
```

```java
@Property("Foo")
<E extends VectorFrame> void setFoobar(E foobar)
```


## remove prefix

Valid method signatures: `()`


### ()

Valid return types: `void`

Remove the property of an element.

example:

```java
@Property("Foo")
void removeFoobar()
```

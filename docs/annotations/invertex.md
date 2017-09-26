Valid on frames: **Edge**

Allowed prefixes when operation is AUTO: `get`

The `@InVertex` takes no parameters and is used only on get methods that themself take no parameters. It specifies the
`VertexFrame` at the head of an edge.

example:

```java
@InVertex
//Method declared here
```


## GET Operation

Valid method signatures: `( )`


### Signature: `( )`

Valid return types: `VertexFrame`.

Get the head vertex of the edge.

example:

```java
@InVertex
BarVertex getFoobar();
```

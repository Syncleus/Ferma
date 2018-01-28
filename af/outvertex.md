Valid on frames: **Edge**

Allowed prefixes when operation is AUTO: `get`

The `@OutVertex` takes no parameters and is used only on get methods that themself take no parameters. It specifies the `VertexFrame` at the tail of an edge.

example:

```java
@OutVertex
//Method declared here
```

## GET Operation

Valid method signatures: `( )`

### Signature: `( )`

Valid return types: `VertexFrame`.

Get the tail vertex of the edge.

example:

```java
@OutVertex
BarVertex getFoobar();
```
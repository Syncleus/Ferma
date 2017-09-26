Valid on frames: **Edge** and **vertex**

The `@GraphElement` annotation takes no parameters and is placed before your class decleration on an `EdgeFrame` or
`VertexFrame`. This annotation is mostly leveraged right now when scanning classes in a package to identify frames.

example:

```java
@GraphElement
public interface FooVertex extends VertexFrame {
    //Methods goes here
}
```

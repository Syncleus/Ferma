Ferma bir Nesne-grafik Modelidir (OGM). An Object-graph Model is to a Graph Database as an Object-relational Model (ORM) is to a Relational Database. That is to say that it maps Java Objects to edges and vertex in a graph database. As a natural consequence the Java types become an implied Schema for a Graph Database even if the underlying implementation doesnt support the notion of a schema.

The objects associated with the various types of Edges and Vertex in a graph are collectively called the Graph Data Model (GDM). Each Java type in the GDM will usually represent a class of Edges or Vertex in the underlying graph. All Edges in the model will extend from the `EdgeFrame` interface and all vertex will extend from the `VertexFrame` interface. The individual classes that comprise the GDM are usually simply refered to as frames.

The methods defined by a frame will represent interactions with the underlying graph via traversals that are relative, using the current edge or vertex as their starting point.

```java
public interface Person extends VertexFrame {
  String getName();
  List<? extends Person> getCoworkers();
}
```

In this example Person represents a vertex in the graph with a property indicating their name, and they are associated with other vertex in the graph of the same type that represent their coworkers.

When implementing a vertex as a concrete class you must instead inherit from `AbstractVertexFrame`.

```java
public class Person extends AbstractVertexFrame {
  public String getName() {
      return this.getProperty("name");
  }

  public List<? extends Person> getCoworkers() {
      return this.traverse(v -> v.out("coworker")).toList(Person.class);
  }
}
```

It is also possible to do the same with inheritance if you want a class and an interface defined.

```java
public class PersonImpl extends AbstractVertexFrame implements Person {
  @Override
  public String getName() {
      return this.getProperty("name");
  }

  @Override
  public List<? extends Person> getCoworkers() {
      return this.traverse(v -> v.out("coworker")).toList(Person.class);
  }
}
```

!!! Note When implementing a Frame a class or abstract class must always extend from either `AbstractEdgeFrame` or `AbstractVertexFrame`.

## Typing

There are two typing modes for ferma and each significantly effects how the user will determine the type of the objects pulled from the graph, these modes are called **Typed Mode** and **Untyped Mode**.

When performing a traversal on a frame there are several methods provided which automatically encapsulate the underlying graph element or elements into a framed equivelant such as a `VertexFrame` or an `EdgeFrame`. This may be either a single frame, or a group of frames provided by an `Iterator`, `Set`, or `List`.

In the earlier example we used a traversal to find all the coworkers and we used the `toList()` method to frame all the underlying vertex into the `Person` type.

```Java
this.traverse(v -> v.out("coworker")).toList(Person.class);
```

Traversals have several different methods availible that each frame and collect the underlying elements in different ways, those methods, members of the `Traversable` interface, are the following.

```Java
<N> N next(Class<N> kind);
<N> List<? extends N> next(int amount, Class<N> kind);
<N> N nextOrDefault(Class<N> kind, N defaultValue);
VertexFrame nextOrAdd();
<N> N nextOrAdd(ClassInitializer<N> initializer);
<N> N nextOrAdd(Class<N> kind);
<N> Iterator<N> frame(Class<N> kind);
<N> List<? extends N> toList(Class<N> kind);
<N> Set<? extends N> toSet(Class<N> kind);
```

!!! note Each of these methods also have an equivelant method with the suffix `Explicit`, we will discuss those later as they only become important when we begin to discuss the differences between Typed Mode and Untyped Mode.

Each of these methods has a slightly different behavior. For full details see the Ferma Javadocs for the Traversable class. However, in short, the `next(Class)` method returns any one of the matching elements and frames it as the specified type. It will throw an exception however if no vertex are found. The `nextOrDefault` varient avoids the exception by returning the default value when there are no matches, which can be `` or `null` for example. Similarly `nextOrAdd` will add a new vertex to the underlying graph if the traversal yields no matches. Finally `frame(Class)`, `toList(Class)`, and `toSet(Class)` will return all elements that match the traversal as either a `Iterator`, `List`, or `Set`.

The exact type returned from all the aforementioned calls will always be a Class of the type specified in the argument, or a subclass thereof. The exact type of the class instantiated will depend on which typing mode is being used.

### Untyped Mode

In untyped mode there is never any Java type information encoded into the underlying graph. This means when you take an object off the graph there is no way for Ferma to know what Java type it is associated with and the user must select the type manually. Since a Frame just defines a set of behaviors and properties exposed for a particular graph element it can sometimes be useful to pick which Frame to use to represent an element based on how you need to interact with that element rather than a one to one mapping of element to a specific type. In such a scenario Untyped Mode might be the ideal choice.

In this mode when framing elements from a traversal the type of the element is determined entierly from the parameters passed to the methods invoked on the Traversable class. The following is an example of how to frame a vertex as a `Person` class from above.

```Java
// Open an untyped Framed Graph
FramedGraph fg = new DelegatingFramedGraph(TinkerGraph.open());

//create a vertex with no type information and a single name property
VertexFrame vertex = fg.addFramedVertex(VertexFrame.class);
vertex.setProperty("name", "Jeff");

//retrieve the vertex we just created but this time frame it as a Person
Person person = fg.traverse(g -> g.V().property("name", "jeff")).nextExplicit(Person.class);
assert person.getName().equals("Jeff");
```

!!! note In untyped mode all the `Traversal` methods with the suffix of `Explicit` behave exactly the same as those methods without the suffix. Therefore when working in untyped mode it is suggested you only use explicit methods. This way if you ever decide to migrate over to typed mode it will not change the behavior of your existing code base and will make the migration process much easier.

### Typed Mode

Typed mode takes things one step further and allows type information about a frame to be encoded as a property on vertex and edges in the underlying graph. This behavior is governed by the `PolymorphicTypeResolver` which encodes the type in a property name which defaults to the value of `PolymorphicTypeResolver.TYPE_RESOLUTION_KEY` but can be explicitly set to any string value of the user's choice. When a class is framed the Type Resolution Key is read and the original type is determined, this in turn effects the type used to instantiate the new Frame and may be a specific type which is a subclass of the type requested. For example say we have the following model.

```Java
public class Person extends AbstractVertexFrame {
  public String getName() {
      return this.getProperty("name");
  }

  public List<? extends Person> getFriends() {
      return this.traverse(v -> v.out("friend")).toList(Person.class);
  }
}

public class Programmer extends Person {
  @Override
  public List<? extends Programmer> getFriends() {
      //Programmers don't have friends :(
      return Collections.emptyList();
  }
}
```

In this case we can encode a `Programmer` vertex into the graph and even if we try to retrieve and frame that vertex as a `VertexFrame` or `Person` in the future the instantiated type will still be `Programmer`. This allows for a truly polymorphic Graph Data Model that leverages method overriding and class inheritance functiuonality in the model. For example the following is possible now in Typed Mode.

```Java
// Open a Framed Graph in Typed Mode
FramedGraph fg = new DelegatingFramedGraph(TinkerGraph.open(), true, false);

//create a vertex with type information specifying it as the Programmer type
Programmer programmer = fg.addFramedVertex(Programmer.class);
programmer.setName("Jeff");

//retrieve the vertex we just created and check it is instantiated as a Programer
Person person = fg.traverse(g -> g.V().property("name", "jeff")).next(Person.class);
assert person instanceof Programmer;
assert person.getFriends().isEmpty();
```

The methods with the `Explicit` suffix are particularly meaningful for Typed Mode. In this mode they bypass the encoded typing completely and instantiate the frame as if in Untyped Mode. The following code snippet provides an example using the same model.

```Java
// Open typed Framed Graph
FramedGraph fg = new DelegatingFramedGraph(TinkerGraph.open(), true, false);

//create a vertex with type information specifying it as the Programmer type
Programmer programmer = fg.addFramedVertex(Programmer.class);
programmer.setName("Jeff");

//retrieve the vertex we just created, since we are using an excplicit method the type won't be Programmer this time.
Person person = fg.traverse(g -> g.V().property("name", "jeff")).nextExplicit(Person.class);
assert !(person instanceof Programmer);
```

The following are the list of explicit method types in the Traversable class.

```Java
<N> N nextExplicit(Class<N> kind);
<N> List<? extends N> nextExplicit(int amount, Class<N> kind);
<N> N nextOrDefaultExplicit(Class<N> kind, N defaultValue);
<N> N nextOrAddExplicit(ClassInitializer<N> initializer);
<N> N nextOrAddExplicit(Class<N> kind);
<N> Iterator<? extends N> frameExplicit(Class<N> kind);
<N> List<? extends N> toListExplicit(Class<N> kind);
<N> Set<? extends N> toSetExplicit(Class<N> kind);
```

It is also possible to change the type encoded in the underlying graph after the element has already been created. The following example demonstrates this feature.

```java
FramedGraph fg = new DelegatingFramedGraph(TinkerGraph.open(), true, false);

//create a vertex with type information specifying it as the Programmer type
Programmer programmer = fg.addFramedVertex(Programmer.class);
programmer.setName("Jeff");

//retrieve the vertex we just created and check it is instantiated as a Programer
Person person = fg.traverse(g -> g.V().property("name", "jeff")).next(Person.class);
assert person instanceof Programmer;

//change the type resolution to person
person.setTypeResolution(Person.class);

//retrieve the vertex again to show the type changed
person = fg.traverse(g -> g.V().property("name", "jeff")).next(Person.class);
assert(!(person instanceof Programmer));
assert(person instanceof Person);
```
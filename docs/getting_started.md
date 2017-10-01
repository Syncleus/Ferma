Ferma provides three levels of type resolution: untyped, simple, and annotated. In untyped mode Ferma doesn't handle
typing at all, instead the type must be explicitly indicated whenever querying. In simple mode Ferma provides type
context encoded as graph element properties which ensures the same type comes out that goes in to a graph. In annotated
mode all the features of simple mode are provided as well as enabling the use of annotations on abstract methods to
instruct Ferma to dynamically construct byte code to implement the abstract methods at start up.

## Dependency

To include Ferma in your project of choice include the following Maven dependency into your build.

```xml
<dependency>
    <groupId>com.syncleus.ferma</groupId>
    <artifactId>ferma</artifactId>
    <version>3.2.1</version>
</dependency>
```

## Untyped Mode Example

In untyped mode there is no automatic typing. Whatever class is explicitly indicated is the type that will be
instantiated when performing queries. Lets start with a simple example domain.

```java
public class Person extends AbstractVertexFrame {
  public String getName() {
    return getProperty("name");
  }

  public void setName(String name) {
    setProperty("name", name);
  }

  public List<? extends Knows> getKnowsList() {
    return traverse((v) -> v.outE("knows")).toList(Knows.class);
  }

  public Knows addKnows(Person friend) {
    return addFramedEdge("knows", friend, Knows.class);
  }
}

public class Knows extends AbstractEdgeFrame {
  public void setYears(int years) {
    setProperty("years", years);
  }

  public int getYears() {
    return getProperty("years");
  }
}
```

And here is how you interact with the framed elements:

```java
public void testUntyped() {
  Graph graph = TinkerGraph.open();

  // implies untyped mode
  FramedGraph fg = new DelegatingFramedGraph(graph);

  Person p1 = fg.addFramedVertex(Person.class);
  p1.setName("Jeff");

  Person p2 = fg.addFramedVertex(Person.class);
  p2.setName("Julia");
  Knows knows = p1.addKnows(p2);
  knows.setYears(15);

  Person jeff = fg.traverse((g) -> g.V().has("name", "Jeff")).next(Person.class);


  Assert.assertEquals("Jeff", jeff.getName());
}
```

## Simple Mode Example

In simple mode you must provide concrete classes, no abstract or interfaces allowed, and the class should always extend
from a FramedVertex or FramedEdge. Simple mode doesn't provide any annotations either. The purpose of simple mode is to
provide type resolution. Basically the type of object you use when adding to the graph is the same type you get out when
reading from the graph.

Say we extend the Person class with the Programmer class.

```java
public class Programmer extends Person {
}
```
    
Using simple mode will save the type of Java class the element was created with for use later:
    
```java
public void testSimpleTyping() {
  Graph graph = TinkerGraph.open();

  // implies simple mode
  FramedGraph fg = new DelegatingFramedGraph(graph, true, false);

  Person p1 = fg.addFramedVertex(Programmer.class);
  p1.setName("Jeff");

  Person p2 = fg.addFramedVertex(Person.class);
  p2.setName("Julia");

  Person jeff = fg.traverse((g) -> g.V().has("name", "Jeff")).next(Person.class);
  Person julia = fg.traverse((g) -> g.V().has("name", "Julia")).next(Person.class);

  Assert.assertEquals(Programmer.class, jeff.getClass());
  Assert.assertEquals(Person.class, julia.getClass());
}
```

## Annotated Mode Example

In annotated mode you can either provide concrete classes, abstract classes, or even interfaces. Abstract classes and
concrete classes must extend from FramedVertex or FramedEdge, however, interfaces do not have this restriction.
Annotated mode also provides a set of annotations which must be used to define any abstract methods that are to be
implemented by the engine. Annotated mode provides the same type resolution as provided by simple mode with a bit more
power to determine parent-child relationships at run time.

The same example as above done with annotations would look something like this.

```java
public abstract class Person extends AbstractVertexFrame {
  @Property("name")
  public abstract String getName();

  @Property("name")
  public abstract void setName(String name);

  @Adjacency(label = "knows")
  public abstract List<Person> getKnowsPeople();

  @Incidence(label = "knows")
  public abstract List<Knows> getKnows();

  @Incidence(label = "knows")
  public abstract Knows addKnows(Person friend);

  public List<? extends Person> getFriendsNamedBill() {
      return this.traverse(input -> input.out("knows").has("name", "bill")).toList(Person.class);
  }
}

public abstract class Knows extends AbstractEdgeFrame {
  @Property("years")
  public abstract void setYears(int years);

  @Property("years")
  public abstract int getYears();

  @InVertex
  public abstract Person getIn();

  @OutVertex
  public abstract Person getOut();
}

public abstract class Programmer extends Person {
}
```

If we pass a collection of Class objects to the FramedGraph constructor then the annotated type resolver will be used.
In this mode you want to tell the engine what classes you will be using so it can handle type resolution properly and
construct the byte code for any abstract annotated methods.

```java
public void testAnnotatedTyping() {
  Set<Class<?>> types = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{
                Person.class,
                Programmer.class,
                Knows.class}));
  Graph graph = TinkerGraph.open();

  //implies annotated mode
  FramedGraph fg = new DelegatingFramedGraph(graph, true, types);

  Person jeff = fg.addFramedVertex(Programmer.class);
  jeff.setName("Jeff");

  Person julia = fg.addFramedVertex(Person.class);
  julia.setName("Julia");
  julia.addKnows(jeff);

  Person juliaAgain = fg.traverse((g) -> g.V().has("name", "Julia")).next(Person.class);
  Person jeffAgain = juliaAgain.getKnowsPeople().get(0);

  Assert.assertTrue(Programmer.class.isAssignableFrom(jeffAgain.getClass()));
  Assert.assertTrue(Person.class.isAssignableFrom(juliaAgain.getClass()));
}
```

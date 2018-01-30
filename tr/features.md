Aşağıdaki örnekler Ferma çerçevesinin bazı özelliklerinin hızlı dökümünü vermektedir.

* [JPA- benzeri Ek Açıklamalar](#jpa-like-annotations)
* [Type information encoded into graph](#type-information-encoded-into-graph)
* [Framing of elements instantiated according to type hierarchy](#framing-instantiated-by-type-hierarchy)
* [Element queried by type hierarchy](#element-queried-by-type-hierarchy)
* [Turning off type resolution on a per call basis](#turning-off-type-resolution-per-call)
* [Changing the encoded graph type already stored in the database](#changing-type-encoded-in-the-graph)
* [Customizing the way type information is stored in the graph](#customizing-how-types-are-encoded)
* Tinkerpop 2 support
* Tinkerpop 3 support

## JPA-like Annotations

```Java
public abstract class Person extends AbstractVertexFrame {
  @Property("name")
  public abstract String getName();

  @Property("name")
  public abstract void setName(String name);

  @Adjacency(label = "knows")
  public abstract List<Person> getKnowsPeople();
}

public abstract class Programmer extends Person {
}

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

  assert jeffAgain instanceof Programmer;
  assert jeffAgain.getName().equals("Jeff");
}
```

## Type information encoded into graph

```java
Set<Class<?>> types = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{Person.class}));
Graph g = new TinkerGraph();
FramedGraph fg = new DelegatingFramedGraph(g, types);

fg.addFramedVertex(Person.class);
Person person = fg.traverse(g -> g.V()).next(Program.class);

String personClassName = Person.class.getName();
String encodedClassName = person.getProperty(PolymorphicTypeResolver.TYPE_RESOLUTION_KEY)
assert personClassName.equals(encodedClassName);
```

## Framing instantiated by type hierarchy

```java
Set<Class<?>> types = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{Person.class,
                                                                         Programmer.class}));
TinkerGraph g = new TinkerGraph();
FramedGraph fg = new DelegatingFramedGraph(g, types);

fg.addFramedVertex(Programmer.class);

//make sure the newly added node is actually a programmer
Person programmer = fg.traverse(g -> g.V()).next(Person.class);
assert programmer instanceof Programmer;
```

## Element queried by type hierarchy

```java
Set<Class<?>> types = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{Person.class,
                                                                         Programmer.class}));
TinkerGraph g = new TinkerGraph();
FramedGraph fg = new DelegatingFramedGraph(g, types);

fg.addFramedVertex(Programmer.class);
fg.addFramedVertex(Person.class);

//counts how many people (or subclasses thereof) in the graph.
assert fg.traverse(g -> g.getTypeResolver().hasType(g.V(), Person.class)).toList(Person.class).size() == 2;
//counts how many programmers are in the graph
assert fg.traverse(g -> g.getTypeResolver().hasType(g.V(), Programmer.class)).toList(Person.class).size() == 1;
```

## Turning off type resolution per call

```java
Set<Class<?>> types = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{Person.class,
                                                                         Programmer.class}));
TinkerGraph g = new TinkerGraph();
FramedGraph fg = new DelegatingFramedGraph(g, types);

fg.addFramedVertex(Programmer.class);

//With type resolution is active it should be a programmer
assert fg.traverse(g -> g.V()).next(Person.class) instanceof Programmer;
//With type resolution bypassed it is no longer a programmer
assert !(fg.traverse(g -> g.V()).nextExplicit(Person.class) instanceof Programmer);
```

## Changing type encoded in the graph

```java
Set<Class<?>> types = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{Person.class,
                                                                         Programmer.class}));
TinkerGraph g = new TinkerGraph();
FramedGraph fg = new DelegatingFramedGraph(g, types);

fg.addFramedVertex(Programmer.class);

//make sure the newly added node is actually a programmer
Person programmer = fg.traverse(g -> g.V()).next(Person.class);
assert programmer instanceof Programmer;

//change the type resolution to person
programmer.setTypeResolution(Person.class);

//make sure the newly added node is actually a programmer
Person person = fg.traverse(g -> g.V()).next(Person.class);
assert !(person instanceof Programmer);
```

## Customizing how types are encoded

```java
Set<Class<?>> types = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{Person.class}));
final ReflectionCache cache = new ReflectionCache(types);
FrameFactory factory = new AnnotationFrameFactory(cache);
TypeResolver resolver = new PolymorphicTypeResolver(cache, "customTypeKey");
Graph g = new TinkerGraph();
FramedGraph fg = new DelegatingFramedGraph(g, factory, resolver);

fg.addFramedVertex(Person.class);
Person person = fg.traverse(g -> g.V()).next(Programmer.class);

String personClassName = Person.class.getName();
String encodedClassName = person.getProperty("customTypeKey")
assert personClassName.equals(encodedClassName);
```
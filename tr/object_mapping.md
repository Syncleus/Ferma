Ferma bir Nesne-grafik Modelidir (OGM). Nesne-Grafik Modeli, bir Grafik Veritabanı iken Nesne İlişkisel Modeli (ORM) bir İlişkisel Veritabanıdır. Yani, Java Nesnelerini bir grafik veritabanındaki kenarlara ve köşeye eşleştirir. Doğal bir sonuç olarak Java türleri, temel uygulama şema kavramı desteklemiyor olsa bile grafik veritabanı için örtülü bir şema haline gelir.

Bir grafiğin çeşitli Kenarları ve Noktalarıyla ilişkili nesnelere toplu olarak Grafik Veri Modeli (GDM) adı verilir. Gdm'deki her Java türü genellikle alttaki grafikte kenar veya köşe sınıfını temsil eder. Modeldeki tüm Kenarlar `kenar çerçevesi` arayüzünden genişler ve tüm köşe, `köşe çerçevesi` arayüzünden genişler. GDM'yi oluşturan bireysel sınıflar genellikle çerçeveler olarak adlandırılır.

Bu çerçeveyle tanımlanan yöntemler, geçerli kenar veya köşe noktasını başlangıç noktaları olarak kullanarak, göreli geçişler yoluyla temel alınan grafikle olan etkileşimleri temsil edecektir.

```java
public interface Person extends VertexFrame {
  String getName();
  List<? extends Person> getCoworkers();
}
```

Bu örnekte; Kişi, grafiğin içinde isimleri belirten özelliği olan bir köşeyi temsil eder ve onlar meslektaşlarını temsil eden aynı türe ait grafikteki diğer köşeyle ilişkilendirilirler.

Bir köşeyi somut bir sınıf olarak uygularken bunun yerine `SoyutKöşeÇerçevesi` öğesinden devralmanız gerekir.

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

Eğer tanımlanmış bir arayüz ve sınıf istiyorsanız aynı şeyi devralma ile yapmak da mümkündür.

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

Bir Çerçeve uygulanırken bir sınıfın veya soyut sınıfın daima `SoyutKenarÇerçevesi` veya`SoyutKöşeÇerçevesi `'nden uzatılması gerekir.

## Yazarak

Ferma için iki yazım modu vardır ve her biri grafiğe çekilen nesnelerin türünü kullanıcının nasıl belirleyeceğini önemli derecede etkiler; bu modlara **Yazma Modu** ve **Türsüz Mod** denir.

Bir çerçeve üzerinde bir geçiş gerçekleştirirken, temel bir grafik ögesini veya ögeleri, `KöşeÇerçevesi` veya `KenarÇerçevesi` gibi çerçeveli bir eşgörünüm içine otomatik olarak kapsayan birkaç yöntem bulunur. Bu ya tek bir çerçeve ya da `Yineleyici`, `Alıcı` veya `Liste` tarafından sağlanan bir grup çerçeve olabilir.

Önceki örnekte, tüm meslektaşları bulmak için bir geçiş kullandık ve alttaki tüm köşeleri `Kişi` türüne çerçevelemek için `Listeleme()` yöntemini kullandık.

```Java
this.traverse(v -> v.out("coworker")).toList(Person.class);
```

Geçişlerde, her çerçevede mevcut olan birkaç farklı yöntem bulunur ve temel unsurları farklı şekillerde toplamanın yolları vardır, bunlar, `Geçilebilir` arayüzünün üyeleri aşağıda belirtilmiştir.

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

!!! not Bu yöntemlerin her biri aynı zamanda `Açık` adlı sonek ile eş değer bir yöntemi de içerir; bunları, sonradan yalnızca Yazma Modu ve Yazılmayan Mod arasındaki farkları tartışmaya başladığımızda önemli bir şekilde tartışacağız.

Bu yöntemlerin her biri biraz farklı bir davranışa sahiptir. Ayrıntılı bilgi için, Traversable sınıfı için Ferma Javadocs'a bakın. Ancak, kısaca `sonraki(Sınıf)` yöntemi, eşleşen öğelerden birini dönüştürür ve onu belirtilen türde çerçeveler. Bununla birlikte, herhangi bir köşe bulunmazsa, bir istisna atanır. `sonrakiVeyaVarsayılan` değişkeni, örneğin `` veya `null` olabilecek herhangi bir eşleşme olmadığında varsayılan değeri döndürerek istisnadan kaçınır. Benzer şekilde `sonrakiVeyaEkleme`, geçişte eşleşme oluşturmazsa, temel grafiğe yeni bir köşe ekler. Son olarak, `Çerçeve(Sınıfı)`, `Listeleme(Sınıfı)` ve `Alıcı(Sınıfı)`, geçişi eşleşen tüm öğeleri `Yineleyici`, `Liste` veya `Alıcı` olarak dönüştürür.

Belirtilen tüm çağrılardan dönüştürülen tam tip, daima argümanda veya alt sınıfında belirtilen türden bir Sınıf olacaktır. Oluşturulan sınıfın tam olarak tipi, hangi yazım modunun kullanıldığına bağlı olacaktır.

### Biçimsiz Mod

Biçimsiz modda, altta yatan grafiğe kodlanmış hiçbir Java tipinde bilgi yoktur. This means when you take an object off the graph there is no way for Ferma to know what Java type it is associated with and the user must select the type manually. Since a Frame just defines a set of behaviors and properties exposed for a particular graph element it can sometimes be useful to pick which Frame to use to represent an element based on how you need to interact with that element rather than a one to one mapping of element to a specific type. In such a scenario Untyped Mode might be the ideal choice.

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

!!! note In untyped mode all the `Traversal` methods with the suffix of `Explicit` behave exactly the same as those methods without the suffix. Bu sebepten dolayı, türü olmayan modda çalışırken yalnızca açık yöntemler kullanmanız önerilir. This way if you ever decide to migrate over to typed mode it will not change the behavior of your existing code base and will make the migration process much easier.

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

In this case we can encode a `Programmer` vertex into the graph and even if we try to retrieve and frame that vertex as a `VertexFrame` or `Person` in the future the instantiated type will still be `Programmer`. This allows for a truly polymorphic Graph Data Model that leverages method overriding and class inheritance functiuonality in the model. Örneğin, şu an ki durum Yazma Modun'da mümkündür.

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

`Explicit</O> sonekine sahip yöntemlerin, Yazma Modu için özellikle anlamı vardır. In this mode they bypass the encoded
typing completely and instantiate the frame as if in Untyped Mode. Aşağıdaki kod parçacığı, aynı modeli kullanarak bir örnek sağlar.</p>

<pre><code class="Java">// Open typed Framed Graph
FramedGraph fg = new DelegatingFramedGraph(TinkerGraph.open(), true, false);

//create a vertex with type information specifying it as the Programmer type
Programmer programmer = fg.addFramedVertex(Programmer.class);
programmer.setName("Jeff");

//retrieve the vertex we just created, since we are using an excplicit method the type won't be Programmer this time.
Person person = fg.traverse(g -> g.V().property("name", "jeff")).nextExplicit(Person.class);
assert !(person instanceof Programmer);
`</pre> 

Geçiş sınıfındaki açık yöntem türlerinin listesi aşağıdadır.

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

Eleman daha önce oluşturulsa bile daha sonradan temel grafiğe kodlanmış türünü değiştirmek de mümkündür. Aşağıdaki örnek, bu özelliği göstermektedir.

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
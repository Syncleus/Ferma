Ferma
========

An ORM / OGM for the Tinkerpop graph stack.

This project has been created as an alternative to the Tinkerpop Frames project.
If you like Gremlin and you like Java then you will like this!


    <dependency>
        <groupId>com.syncleus.ferma</groupId>
        <artifactId>ferma</artifactId>
        <version>2.0</version>
    </dependency>
    

It's just a way to give typed context to your gremlin queries:

    
    public class Person extends FramedVertex {
    
      public String getName() {
        return getProperty("name");
      }
      
      public void setName(String name) {
        setProperty("name", name); //Properties are simple method calls
      }

      public List<Knows> getKnowsList() {
        return outE("knows").toList(Knows.class); //Gremlin natively supported
      }
      
      public List<Person> getFriendsOfFriends() {
        return out("knows").out("knows").except(this).toList(Person.class); //Gremlin natively supported
      }
      
      public Knows addKnows(Person friend) {
        return addEdge("knows", friend, Knows.class); //Elements are automatically unwrapped
      }
    }
    
    public class Knows extends FramedEdge {
    
      public void setYears(int years) {
        setProperty("years", years);
      }
      
      public int getYears() {
        return getProperty("years");
      }
    }
    
    
    public class Programmer extends Person {
    
    }
    
    
And here is how you interact with the framed elements:
    
    public void testBasic() {
    
      Graph g = new TinkerGraph();
      FramedGraph fg = new FramedGraph(g);
      Person p1 = fg.addVertex(Person.class);
      p1.setName("Jeff");
      
      Person p2 = fg.addVertex(Person.class);
      p2.setName("Julia");
      Knows knows = p1.addKnows(p2);
      knows.setYears(15);
      
      Person jeff = fg.V().has("name", "Jeff").next(Person.class);
      
      
      Assert.assertEquals("Jeff", jeff.getName());
      Assert.assertEquals(15, jeff.getKnowsList().get(0).getYears());
      
    
    }
    
Using TypeResolver.Java will save the type of Java class the element was created with for use later:
    
    public void testJavaTyping() {
      Graph g = new TinkerGraph();
      FramedGraph fg = new FramedGraph(g, FrameFactory.Default, TypeResolver.Java);//Java type resolver
      //Also note FrameFactory.Default. Other options are CDI and Spring.
      
      Person p1 = fg.addVertex(Programmer.class);
      p1.setName("Jeff");
      
      Person p2 = fg.addVertex(Person.class);
      p2.setName("Julia");
      
      Person jeff = fg.V().has("name", "Jeff").next(Person.class);
      Person julia = fg.V().has("name", "Julia").next(Person.class);
      
      Assert.assertEquals(Programmer.class, jeff.getClass());
      Assert.assertEquals(Person.class, julia.getClass());
    }


This project uses code derived from the [Tinkerpop](http://www.tinkerpop.com/) project under the apache license and or
tinkerpop licence. As well as the [Totorom](https://github.com/BrynCooke/totorom) project under the apache license
version 2.0.
     

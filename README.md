Totorom
========

An ORM for the Tinkerpop graph stack.

Discussion at https://groups.google.com/forum/#!forum/totorom

This project has been created as an alternative to the Tinkerpop Frames project.
If you like Gremlin and you like Java then you will like this!


    <dependency>
        <groupId>org.jglue.totorom</groupId>
        <artifactId>totorom-tinkerpop2</artifactId>
        <version>0.5.0</version>
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
      p1.setName("Bryn");
      
      Person p2 = fg.addVertex(Person.class);
      p2.setName("Julia");
      Knows knows = p1.addKnows(p2);
      knows.setYears(15);
      
      Person bryn = fg.V().has("name", "Bryn").next(Person.class);
      
      
      Assert.assertEquals("Bryn", bryn.getName());
      Assert.assertEquals(15, bryn.getKnowsList().get(0).getYears());
      
    
    }
    
Using TypeResolver.Java will save the type of Java class the element was created with for use later:
    
    public void testJavaTyping() {
      Graph g = new TinkerGraph();
      FramedGraph fg = new FramedGraph(g, FrameFactory.Default, TypeResolver.Java);//Java type resolver
      //Also note FrameFactory.Default. Other options are CDI and Spring.
      
      Person p1 = fg.addVertex(Programmer.class);
      p1.setName("Bryn");
      
      Person p2 = fg.addVertex(Person.class);
      p2.setName("Julia");
      
      Person bryn = fg.V().has("name", "Bryn").next(Person.class);
      Person julia = fg.V().has("name", "Julia").next(Person.class);
      
      Assert.assertEquals(Programmer.class, bryn.getClass());
      Assert.assertEquals(Person.class, julia.getClass());
    }
    
This project uses code derived from the Tinkerpop project (http://www.tinkerpop.com/) under the apache licence and or tinkerpop licence.
     

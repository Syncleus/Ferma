![](images/ferma-logo-text.svg)

Apache TinkerPop ™ grafik yığını için bir ORM / OGM.

**Apache Yazılımı Lisansı v2 kapsamında lisanslanmıştır**

Ferma projesi başlangıçta TinkerPop2 Çerçeveler projesine alternatif olarak yaratıldı. O dönemde topluluk tarafından ihtiyaç duyulan özelliklerden yoksundu ve performansı korkutucu derecede yavaştı. Bugün Ferma geleneksel veritabanları için Nesne-ilişkisel Model (ORM) rolünde sağlam bir kütüphane yapısını oluşturuyor. Ferma is often referred to as a Object-graph Model (OGM) library, and maps Java objects to elements in a graph such as a Vertex or an Edges. In short it allows a schema to be defined using java interfaces and classes which provides a level of abstraction for interacting with the underlying graph.

Ferma 3.x **Supports TinkerPop3**. For tinkerPop2 support use Ferma version 2.x.

Annotated classes in Ferma have their abstract methods implemented using code generation during start-up with Byte Buddy, avoiding the need for proxy classes. This in turn significantly improves performance when compared with TinkerPop Frames and other frameworks. Ferma offers many features including several annotation types to reduce the need for boilerplate code as well as handling Java typing transparently. This ensures whatever the type of the object is when you persist it to the graph the same Java type will be used when instantiating a class off of the graph.

Ferma is designed to easily replace TinkerPop Frames in existing code, as such, the annotations provided by Ferma are a super-set of those provided by TinkerPop Frames.

Ferma direkt olarak TinkerPop'un üzerine inşa edilmiş ve tüm iç kısımlara erişim imkânı sağlamaktadır. Bu bütün TinkerPop özelliklerinin son kullanıcı tarafından kullanılmasını sağlar. TinkerPop yığını, Ferma makinesiyle çalışmak için kullanılabilecek çeşitli araçlar sağlar.

- **Gremlin**, a database agnostic query language for Graph Databases.
- **Gremlin Server**, uzaktaki makinelerde Gremlin'i çalıştırmak için bir arabirim sağlayan sunucudur.
- verilerin bölünmesi, birleştirilmesi, filtrelenmesi ve dönüştürülmesi için bir veri akışı çerçevesidir
- **Graph Computer**, a framework for running algorithms against a Graph Database.
- **OLTP** ve **OLAP** her iki makine için destek.
- **TinkerGraph** bir Grafik Veritabanı ve TinkerPop için referans uygulamasıdır.
- Native **Gephi** integration for visualizing graphs.
- Interfaces for most major Graph Compute Engines including **Hadoop M/R**. **Spark**, and **Giraph**.

Ferma, ayrıca aşağıdakileri de içeren TinkerPop ile uyumlu birçok veritabanını da desteklemektedir.

- [Titan](http://thinkaurelius.github.io/titan/)
- [Neo4j](http://neo4j.com)
- [OrientDB](http://www.orientechnologies.com/orientdb/)
- [MongoDB](http://www.mongodb.org)
- [Oracle NoSQL](http://www.oracle.com/us/products/database/nosql/overview/index.html)
- TinkerGraph

Ferma Javadocs: [latest](http://www.javadoc.io/doc/com.syncleus.ferma/ferma) - [3.2.1](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/3.2.1) - [3.2.0](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/3.2.0) - [3.1.0](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/3.1.0) - [3.0.3](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/3.0.3) - [3.0.2](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/3.0.2) - [3.0.1](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/3.0.1) - [3.0.0](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/3.0.0) - [2.4.0](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/2.4.0) - [2.3.0](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/2.3.0) - [2.2.2](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/2.2.2) - [2.2.1](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/2.2.1) - [2.2.0](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/2.2.0) - [2.1.0](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/2.1.0) - [2.0.6](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/2.0.6) - [2.0.5](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/2.0.5) - [2.0.4](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/2.0.4) - [2.0.3](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/2.0.3) - [2.0.2](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/2.0.2) - [2.0.1](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/2.0.1) - [2.0.0](http://www.javadoc.io/doc/com.syncleus.ferma/ferma/2.0.0)

For support please use [Gitter](https://gitter.im/Syncleus/Ferma?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge) or the [official Ferma mailing list](https://groups.google.com/a/syncleus.com/forum/#!forum/ferma-list).

Please file bugs and feature requests on [Github](https://github.com/Syncleus/Ferma/issues).

## Obtaining the Source

The official source repository for Ferma is located in the Syncleus Github repository and can be cloned using the following command.

    git clone https://github.com/Syncleus/Ferma.git
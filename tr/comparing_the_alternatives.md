Orada birkaç OGM/ORM seçeneği vardır. For the purposes of this document we will focus only on those that have a stable release, or are close to a stable release. At the time of this writing those are: Tinkerpop Framed and Totorom.

## Benchmarks

We maintain an informal project for benchmarking Ferma against other OGM available, you can find the [source here](https://github.com/Syncleus/Ferma-benchmark). However below is a matrix breakdown of the results. Ham çalıştırma süresini göstermek yerine Ferma ile karşılaştığında her OGM oranını gösteriyoruz. Bu nedenle tablo 1x listeleniyorsa çerçeve Ferma ile aynı yürütme süresine sahip demektir, eğer 2x listeleniyorsa yürütülmesi iki kat daha uzun sürer eğer 0.5x gösteriyorsa yürütmenin yarısı kadar zaman almış demektir. Obviously any value less than 1x indicates the OGM out performed Ferma and any value greater than 1x indicates Ferma had the superior performance tiimes.

|                                           | **Tinkerpop 2 Blueprints** | **Gremlin Pipeline** | **Tinkerpop3** | **Frames**  | **Totorom** | **Peapod**  |
| ----------------------------------------- | -------------------------- | -------------------- | -------------- | ----------- | ----------- | ----------- |
| **Get adjacencies via annotation**        | Not capable                | Not capable          | Not capable    | x2.09       | Not capable | x2.65       |
| **Get verticies (untyped)**               | x0.89                      | x3.94                | x16.98         | Not capable | x4.24       | Not capable |
| **Get verticies (typed)**                 | x0.92                      | x3.94                | Not capable    | x0.96       | x4.20       | x20.74      |
| **Get verticies and call next (untyped)** | x0.79                      | x3.87                | x11.74         | Not capable | x4.81       | Not capable |
| **Get verticies and call next (typed)**   | x0.72                      | x2.91                | Not capable    | x1.94       | x3.31       | x16.70      |

!!! note These bencharks were performed comparing our v2.x branch. These benchmarks need to be updated to reflect changes in Tinkerpop3 as well as the Ferma v3.x branch.

As can be seen Ferma out performs all the alternative solutions considerably by several orders of magnitude. While results do vary slightly from system to system these results are pretty close to typical. Go ahead, check out the benchmark program and run it for yourself!

## Feature Breakdown

Ferma also supports all the features provided by the alternatives out there, not to mention several novel features. Aşağıdaki çeşitli çerçevelerin özelliklerinin hızlı bir dökümü verilmektedir. We also link to some Ferma examples showing the various features in action.

| Feature                                                                                                                | **Ferma** | **Frames**    | **Totorom**   | **Peapod**    |
| ---------------------------------------------------------------------------------------------------------------------- | --------- | ------------- | ------------- | ------------- |
| **[JPA-like Annotations](features.md#jpa-like-annotations)**                                                           | Supported | Supported     | Not Supported | Supported     |
| **[Type information encoded into graph](features.md#type-information-encoded-into-graph)**                             | Supported | Supported     | Supported     | Supported     |
| **[Framing of elements instantiated according to type hierarchy](features.md#framing-instantiated-by-type-hierarchy)** | Supported | Supported     | Supported     | Supported     |
| **[Element queried by type hierarchy](features.md#element-queried-by-type-hierarchy)**                                 | Supported | Not Supported | Not Supported | Partial \*  |
| **[Turning off type resolution on a per call basis](features.md#turning-off-type-resolution-per-call)**                | Supported | Not Supported | Not Supported | Not Supported |
| **[Changing the encoded graph type already stored in the database](features.md#changing-type-encoded-in-the-graph)**   | Supported | Not Supported | Not Supported | Not Supported |
| **[Customizing the way type information is stored in the graph](features.md#customizing-how-types-are-encoded)**       | Supported | Not Supported | Not Supported | Not Supported |
| **Tinkerpop 2 support**                                                                                                | Supported | Supported     | Supported     | Not Supported |
| **Tinkerpop 3 support**                                                                                                | Supported | Not Supported | Not Supported | Supported     |

\* While Peapod does support querying for all instances of a type, and its subtypes, it does not support a mechanism to query for a specific type while excluding subtypes.
Orada birkaç OGM/ORM seçeneği vardır. Bu belgenin amaçları doğrultusunda yalnızca stabil bir şekilde serbest bırakılanlara ya da stabil bir şekilde serbest bırakılmaya yakın olanlara odaklanacağız. At the time of this writing those are: Tinkerpop Framed and Totorom.

## Kıyaslamalar

Ferma'yı OGM ile kıyaslamak için gayrı resmi bir proje hazırlıyoruz, [kaynağı burada](https://github.com/Syncleus/Ferma-benchmark) bulabilirsiniz. Ancak aşağıda sonuçların bir matris dökümü bulunmaktadır. Ham çalıştırma süresini göstermek yerine Ferma ile karşılaştığında her OGM oranını gösteriyoruz. Bu nedenle tablo 1x listeleniyorsa çerçeve Ferma ile aynı yürütme süresine sahip demektir, eğer 2x listeleniyorsa yürütülmesi iki kat daha uzun sürer eğer 0.5x gösteriyorsa yürütmenin yarısı kadar zaman almış demektir. Obviously any value less than 1x indicates the OGM out performed Ferma and any value greater than 1x indicates Ferma had the superior performance tiimes.

|                                           | **Tinkerpop 2 Blueprints** | **Gremlin Pipeline** | **Tinkerpop3** | **Frames**  | **Totorom** | **Peapod**  |
| ----------------------------------------- | -------------------------- | -------------------- | -------------- | ----------- | ----------- | ----------- |
| **Get adjacencies via annotation**        | Not capable                | Not capable          | Not capable    | x2.09       | Not capable | x2.65       |
| **Get verticies (untyped)**               | x0.89                      | x3.94                | x16.98         | Not capable | x4.24       | Not capable |
| **Get verticies (typed)**                 | x0.92                      | x3.94                | Not capable    | x0.96       | x4.20       | x20.74      |
| **Get verticies and call next (untyped)** | x0.79                      | x3.87                | x11.74         | Not capable | x4.81       | Not capable |
| **Get verticies and call next (typed)**   | x0.72                      | x2.91                | Not capable    | x1.94       | x3.31       | x16.70      |

!!! note These bencharks were performed comparing our v2.x branch. These benchmarks need to be updated to reflect changes in Tinkerpop3 as well as the Ferma v3.x branch.

Görüldüğü gibi Ferma dışarıdaki tüm alternatif çözümleri önem derecesine göre sırayla gerçekleştirir. Sonuçlar sistemden sisteme farklılık gösterirken, bu sonuçlar tipik olarak oldukça yakındır. Devam edin, kıyaslama programına bakın ve kendiniz uygulayın!

## Feature Breakdown

Ferma ayrıca alternatifler tarafından sağlanan tüm özellikleri de destekliyor, bazı yeni özelliklerden söz etmemektedir. Aşağıdaki çeşitli çerçevelerin özelliklerinin hızlı bir dökümü verilmektedir. Ayrıca hareket halindeki çeşitli özellikleri gösteren bazı Ferma örnekleriyle bağlantı kuruyoruz.

| Feature                                                                                                                | **Ferma**   | **Çerçeveler** | **Totorom**    | **Peapod**     |
| ---------------------------------------------------------------------------------------------------------------------- | ----------- | -------------- | -------------- | -------------- |
| **[JPA-like Annotations](features.md#jpa-like-annotations)**                                                           | Desteklenen | Desteklenen    | Desteklenmeyen | Desteklenen    |
| **[Type information encoded into graph](features.md#type-information-encoded-into-graph)**                             | Desteklenen | Desteklenen    | Desteklenen    | Desteklenen    |
| **[Framing of elements instantiated according to type hierarchy](features.md#framing-instantiated-by-type-hierarchy)** | Desteklenen | Desteklenen    | Desteklenen    | Desteklenen    |
| **[Element queried by type hierarchy](features.md#element-queried-by-type-hierarchy)**                                 | Desteklenen | Desteklenmeyen | Desteklenmeyen | Kısmi \*     |
| **[Turning off type resolution on a per call basis](features.md#turning-off-type-resolution-per-call)**                | Desteklenen | Desteklenmeyen | Desteklenmeyen | Desteklenmeyen |
| **[Changing the encoded graph type already stored in the database](features.md#changing-type-encoded-in-the-graph)**   | Desteklenen | Desteklenmeyen | Desteklenmeyen | Desteklenmeyen |
| **[Customizing the way type information is stored in the graph](features.md#customizing-how-types-are-encoded)**       | Desteklenen | Desteklenmeyen | Desteklenmeyen | Desteklenmeyen |
| **Tinkerpop 2 support**                                                                                                | Desteklenen | Desteklenen    | Desteklenen    | Desteklenmeyen |
| **Tinkerpop 3 support**                                                                                                | Desteklenen | Desteklenmeyen | Desteklenmeyen | Desteklenen    |

\* While Peapod does support querying for all instances of a type, and its subtypes, it does not support a mechanism to query for a specific type while excluding subtypes.
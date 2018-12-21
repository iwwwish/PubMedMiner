PubMedMiner
=============

PubMedMiner automatically retrieves a set of very recent *PubMed* articles for a given query word and facilitates further analysis that includes: *named entity recognition* and *co-occurrence analysis*. The application also builds a co-occurrence network which when visualized gives an idea about the relation between the query word and different biomedical concepts identified in the texts using different ontologies. Such analysis is especially helpful in relating an entity (for example a drug) of recent interest to its related biomedical concepts.

The following screenshots of the application give an idea about how the tool looks and what it can do.

**Fig 1**: Basic screenshot displaying 10 PubMed Abstracts for "diclofenac" with the chemical names in the text tagged (using OSCAR4 dictionary) in Yellow color.

![alt tag](http://i57.tinypic.com/xqfdjs.jpg)

**Fig 2**: A screenshot of the co-occurrence network created using the application for those chemical names which co-occur along with the query ("diclofenac").

![alt tag](http://i57.tinypic.com/vgkbpj.jpg)

**Fig 3**: A screenshot of the co-occurrence matrix or correlation matrix created using the application for the same as Fig 2. Darkly colored cells indicate more frequent co-occurrence of the corresponding terms.

![alt tag](http://i62.tinypic.com/2jbjnye.jpg)

* Note: The application currently retrieves only a maximum of 299 Abstracts per run due to the limitations in fetching higher number of Abstracts from PubMed with a single *http* request. The application will be developed further.

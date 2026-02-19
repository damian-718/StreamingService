Notes:

Monorepo for frontend+backend (for now).

Utilizing JPA/Hibernate
-   JPA defines how objects map to database tables (blueprint, the HOW)
    -   Provides annotations like @Entity, @Id, @OneToManyS
-   Hibernate implements JPA. Converts objects and annotations into SQL queries (engine)
    -   Handles table creation, caching, lazy loading, transactions, etc...

Repositories is a convienent way for spring to access JPA entities. so animerepository.save, is a function provided from the repository and then jpa can access the coupled entities and manage them.

With JPA/Hibernate, entities do not need a constructor. Since we arent managing objects manually in memory. Hibernate uses reflection where it loads a empty object and loads in parameters via table columns. If I wanted to create my own instance objects, then I can add in the constructors.

The flow: AnimeController handles REST API requests and utilizes functions in AnimeService. AnimeService utilizes AnimeRepository(JPA) for the built in DB/SQL queries. The AnimeRepository takes in a Anime object which is marked as @entity, giving it a mapping to a table in the DB.

Jar file is the backend compressed into a single runnable file. Compiled. class files and depedencies.
Dockerfile determines how that jar file is built and run.

'docker compose run' is one off command, starts up container, runs command, shuts down.
'docker compose up' is to start all services and keep containers running (logs etc...)

Using Jikan API opposed to official MAL API because I dont need user related data from MAL. Otherwise I would need to go through OAUTH. OAUTH is a way for my service to talk to an external service; it ensures the user can access that service so my backend can make calls on their behalf. The reason the user wont make api calls directly to the external service is because my service would in theory do additional enrichment on top of the external call.

OAUTH is NOT JWT. Oauth = flow/framework, JWT = type of token that can be generated through OAUTH.

In Spring Data JPA, Page and Pageable are built in tools for pgination. Load chunks of data instead of all at once. Pageable is request info for pagination (which page, items per page, sorting)

Repository in springboot is for accessing data, like from a database.

ElasticSearch:

- Utilzing elasticsearch for searches over querying Postgres directly. This is because, elastic search is an entire engine that can search based on keywords, word matches, "fuzzy" spelling, etc... Denormalized and quick lookups on documents compared to searching an entire database. Elasticsearch tokenizes "documents" (in this case anime titles, episode names). Each word in the document gets a token and then is reverse indexed. A search on a word returns documents it was a part of.
- Each document in elastic search refers to a JSON object from the backend (a piece of data). Every document has different fields and attributes, it maps to objects, there isnt one univeral object.
- In this app, episodes are a nested document inside anime. This means when a user searches for episodes, it returns the relevant anime with that episode name. A search will check both the high level (anime documents) and also nested level (episodes within a anime).
- Elasticsearch flow in this project is: user types search -> searchbar -> GET /anime/search?q=Gintama -> AnimeSearchController -> AnimeSearchService -> AnimeSearchRepository -> Elasticsearch -> string to JSON -> AnimeDocument -> controller returns list of documents -> frontend renders the AnimeCard
    - Document annotations on anime/episode define mapping, then when repo.save() (via syncservice), elasticsearch will create the anime/episode index if it doesnt exist. It will apply the mappings and store it as a document.

JUnit/Mockito:

- JUnit is a testing framework that gives us the @Test annotation on test cases, and asertEquals.
- Mockito lets us "mock" dependencies. For example, we have a searchservice that makes a database call with the repository class. Mockito can be used on the repository class, to mock it. Where when its calls by the service, intercept it, create a mock return value, then pass it back into the service so the service can do its remaining logic. Mockito will let us simply override that external call, but still do other service logic.

Core entities (rough draft): 

Anime{
    id -> UUID
    malId -> MyAnimeList ID
    title -> string
    description -> string
    rating -> float
    coverURL -> string or S3, not sure yet
    year -> int
    episodes -> list of episode objects
    genres -> list of genre objects
}

Episode{ 
    episodeNumber
    title
    videoURL -> point to s3
} : ManyToOne relationship with anime

Genre{
    id
    name
    manytomany mapping with anime
}

User (optional){
    uid
    username
    email
    onetomany watchlist (list)
}

package com.damian.animestream.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;

import com.damian.animestream.model.AnimeDocument;

public interface AnimeSearchRepository extends ElasticsearchRepository<AnimeDocument, Long> {
    List<AnimeDocument> findByTitleContainingIgnoreCase(String title);
    List<AnimeDocument> findByDescriptionContainingIgnoreCase(String text);

    // tells elastic search to use a custom query instead of the normal findby.
    // anime document has episodes array, each episode is considered nested. path specifies nested field to check. match episode title on ever episode in the array.
    // inner hit will return the anime that has the episode along with the episode that matched for that card
    @Query("""
        {
          "nested": {
            "path": "episodes",
            "query": {
              "match": { "episodes.title": "?0" }
            },
            "inner_hits": {}
          }
        }
    """)
    List<AnimeDocument> findByEpisodeTitle(String episodeTitle);
}
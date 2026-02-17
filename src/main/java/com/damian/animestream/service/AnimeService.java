package com.damian.animestream.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.damian.animestream.model.Anime;
import com.damian.animestream.model.Episode;
import com.damian.animestream.repository.AnimeRepository;
import com.damian.animestream.repository.EpisodeRepository;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

// Spring-manages service bean (singleton). Instantiate and inject dependencies
@Service
public class AnimeService {

    private final AnimeRepository animeRepository; // defining animeRepository as a JPA repository is what gives the CRUD operations to talk to database without writing SQL
    private final EpisodeRepository episodeRepository;
    private final JikanService jikanService;
    
    public AnimeService(AnimeRepository animeRepository, EpisodeRepository episodeRepository, JikanService jikanService) { // inject an instance of animerepository here via spring
        this.animeRepository = animeRepository;
        this.episodeRepository = episodeRepository;
        this.jikanService = jikanService;
    }

    
    public void importTopAnime() throws InterruptedException {

        String response = jikanService.fetchTopAnimeRaw();
        
        // JsonNode is a way to map json data into a tree structure
        // in Python, json is automatically read in as a dict, but in Java its not so simple
        // JsonNode is a way of giving the dict like structure
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        JsonNode data = root.get("data");

        for (JsonNode node : data) {
            Anime anime = new Anime(); // set the JIKAN response into a in memory object

            anime.setMalId(node.get("mal_id").asInt());
            anime.setTitle(node.get("title").asString());
            anime.setDescription(node.get("synopsis").asString());
            anime.setRating((float) node.get("score").asDouble());
            anime.setYear(node.get("year").asInt());
            anime.setCoverUrl(
                node.get("images").get("jpg").get("image_url").asString()
            );

            animeRepository.save(anime); // JPA sees that anime is @entity and comverts the anime object into row/columns in anime table
            
            // fetch episodes for each anime
            try {
                String episodesResponse = jikanService.fetchEpisodesRaw(anime.getMalId());
                JsonNode episodesData = mapper.readTree(episodesResponse).get("data");

                if (episodesData != null) {
                    int epNumber = 1;
                    for (JsonNode epNode : episodesData) {
                        Episode episode = new Episode();
                        episode.setNumber(epNumber++);
                        episode.setTitle(epNode.get("title").asString(""));
                        episode.setAnime(anime);
                        episodeRepository.save(episode);
                    }
                }

                // sleep to avoid rate limiting
                Thread.sleep(1500);

            } catch (WebClientResponseException.TooManyRequests e) {
                System.out.println("Rate limit hit for anime: " + anime.getMalId() + ". Waiting before retry...");
                Thread.sleep(3000); // wait longer if 429 too many requests error
            } catch (Exception e) {
                System.out.println("Failed to fetch episodes for anime " + anime.getMalId() + ": " + e.getMessage());
            }
        }
    }

    public Page<Episode> getEpisodesByAnimeId(UUID animeId, Pageable pageable) {
        return episodeRepository.findByAnimeIdOrderByNumberAsc(animeId, pageable);
    }

    // database calls
    // wont return raw rows but fully constructed anime objects for each row due to the @entity annotation
    // the models allow for a way for jpa to be able to map java objects to tables and rows and vice versa...
    public List<Anime> getAllAnime() {
        return animeRepository.findAll();
    }

    public Anime getAnimeById(UUID id) { // fetch via primary key
        return animeRepository.findById(id).orElse(null);
    }

    public Anime saveAnime(Anime anime) {
        return animeRepository.save(anime);
    }

    public void deleteAnime(UUID id) {
        animeRepository.deleteById(id);
    }
}

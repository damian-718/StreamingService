package com.damian.animestream.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.damian.animestream.model.Anime;
import com.damian.animestream.repository.AnimeRepository;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

// Spring-manages service bean (singleton). Instantiate and inject dependencies
@Service
public class AnimeService {

    private final AnimeRepository animeRepository; // defining animeRepository as a JPA repository is what gives the CRUD operations to talk to database without writing SQL
    private final JikanService jikanService;


    public AnimeService(AnimeRepository animeRepository, JikanService jikanService) { // inject an instance of animerepository here via spring
        this.animeRepository = animeRepository;
        this.jikanService = jikanService;
    }

    
    public void importTopAnime() {

        String response = jikanService.fetchTopAnimeRaw();

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
        }
    }

    // database calls
    // wont return raw rows but fully constructed anime objects for each row due to the @entity annotation
    // the models allow for a way for jpa to be able to map java objects to tables and rows and vice versa...
    public List<Anime> getAllAnime() {
        return animeRepository.findAll();
    }

    public Anime getAnimeById(UUID id) { // fetch via parimary key
        return animeRepository.findById(id).orElse(null);
    }

    public Anime saveAnime(Anime anime) {
        return animeRepository.save(anime);
    }

    public void deleteAnime(UUID id) {
        animeRepository.deleteById(id);
    }
}

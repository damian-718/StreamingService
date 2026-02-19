package com.damian.animestream.controller;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.damian.animestream.model.AnimeDocument;
import com.damian.animestream.service.AnimeSearchService;

// .andExpect can assert properties of the HTTP response, like status code, content type, and json fields. Basically assertEquals but for HTTP responses.
@WebMvcTest(AnimeSearchController.class)
@AutoConfigureMockMvc(addFilters = false) // disable spring security for testing.
class AnimeSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // create a mock instance of the spring managed beanand injects it.
    // controller thinks its talking to the real service, but its a mock we can control, real service wont run. Just verifies arguments are correct and that the controller is calling the right service.
    @MockitoBean
    private AnimeSearchService searchService;

    private AnimeDocument naruto;
    private AnimeDocument gintama;

    // JUnit annotation to run this before testcases. Creates sample anime documents, to then return as sanmple expected results in test cases.
    @BeforeEach
    void setUp() {
        naruto = new AnimeDocument();
        naruto.setMalId(1);
        naruto.setTitle("Naruto");
        naruto.setDescription("A ninja story");
        naruto.setRating(8.5f); // float literal, not double, since rating is a float in the model
        naruto.setYear(2002);
        naruto.setCoverUrl("http://example.com/naruto.jpg");

        gintama = new AnimeDocument();
        gintama.setMalId(2);
        gintama.setTitle("Gintama");
        gintama.setDescription("Funny samurai adventures");
        gintama.setRating(9.0f);
        gintama.setYear(2015);
        gintama.setCoverUrl("http://example.com/gintama.jpg");
    }

    // @test comes from junit, and is the individual test cases.
    @Test
    void searchReturnsAnimeList() throws Exception {
        // mock the service to return test data
        when(searchService.search("Naruto")).thenReturn(List.of(naruto));

        mockMvc.perform(get("/anime/search")
                        .param("q", "Naruto")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // status code 200
                .andExpect(jsonPath("$", hasSize(1))) // one result
                .andExpect(jsonPath("$[0].title", is("Naruto")))
                .andExpect(jsonPath("$[0].malId", is(1)));
    }

    @Test
    void searchReturnsMultipleResults() throws Exception {
        when(searchService.search("a")).thenReturn(List.of(naruto, gintama));

        mockMvc.perform(get("/anime/search")
                        .param("q", "a"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].title", is("Gintama")));
    }

    @Test
    void searchReturnsEmptyListForNoMatch() throws Exception {
        when(searchService.search("NoMatch")).thenReturn(List.of());

        mockMvc.perform(get("/anime/search")
                        .param("q", "NoMatch"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void searchReturnsBadRequestForMissingParam() throws Exception {
        mockMvc.perform(get("/anime/search"))
                .andExpect(status().isBadRequest());
    }

    // // test that if the service throws an exception, the controller returns a 500 error. this is important to ensure that exceptions are properly handled.
    // @Test
    // void searchHandlesServiceException() throws Exception {
    //     when(searchService.search("Naruto"))
    //             .thenThrow(new RuntimeException("Service failed"));

    //     mockMvc.perform(get("/anime/search")
    //                     .param("q", "Naruto"))
    //             .andExpect(status().isInternalServerError());
    // }
}

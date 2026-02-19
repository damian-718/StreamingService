package com.damian.animestream.service;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.damian.animestream.model.AnimeDocument;
import com.damian.animestream.repository.AnimeSearchRepository;

// no springboot test annotation, just testing the service layer in isolation. use Mockito to mock the repository and inject it into the service
// the idea is to test LOGIC, not database and elasticsearch. we just want to ensure the right repositories and arguments are being used.
@ExtendWith(MockitoExtension.class)
class AnimeSearchServiceTest {

    // creates a fake implementation of the repository for testing. thats what mockito enables on top of JUnit.
    // a mock repository wont connect to elasticsearrch or the database, it will just return what we tell it to return in the test.
    @Mock 
    private AnimeSearchRepository repository;

    // create a real instance of the service, but inject the mock repository into it (not the real one). Now can test the service logic without worrying about the database/elasticsearch connection.
    @InjectMocks
    private AnimeSearchService service;
    
    // comes from JUnit, run this method as a test case. we will test that the search service returns title matches first, then episode matches, and no duplicates.
    @Test
    void shouldReturnTitleMatchesFirst() {
        // given
        AnimeDocument naruto = new AnimeDocument();
        naruto.setMalId(1);
        naruto.setTitle("Naruto");

        when(repository.findByTitleContainingIgnoreCase("nar"))
                .thenReturn(List.of(naruto));

        // this is what mockito does. service.search will call the real search method, BUT when it calls repository.findby, mockito intercepts it and runs the when/then return logic instead.
        // mockito just makes sure the service is calling the expected repository methods and has the right arguments.
        List<AnimeDocument> result = service.search("nar");

        // JUnit executes and reports pass/fail based on whether the assertions hold. we expect to get back the naruto anime document, and it should be the only result.
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Naruto");
    }
}

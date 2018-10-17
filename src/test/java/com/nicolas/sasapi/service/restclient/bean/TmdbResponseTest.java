package com.nicolas.sasapi.service.restclient.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class TmdbResponseTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testTmdbResponseToObject() throws IOException {
        ClassPathResource resource = new ClassPathResource("tmdb_response.json");
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        TmdbResponse tmdbResponseDTO = objectMapper.readValue(resource.getURL(), TmdbResponse.class);

        Assertions.assertThat(tmdbResponseDTO.getPage()).isEqualTo(1);
        Assertions.assertThat(tmdbResponseDTO.getTotalPages()).isEqualTo(993L);
        Assertions.assertThat(tmdbResponseDTO.getTotalResults()).isEqualTo(19853L);
        Assertions.assertThat(tmdbResponseDTO.getTmdbMovies())
                .hasSize(20)
                .extracting("title", "imdbId", "voteCount")
                .contains(Tuple.tuple("Venom", 335983L, 1103));

    }
}
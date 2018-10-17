package com.nicolas.sasapi.service.restclient;

import static org.assertj.core.api.Assertions.assertThat;

import com.nicolas.sasapi.service.restclient.bean.TmdbResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import retrofit2.Call;

@RunWith(SpringJUnit4ClassRunner.class)
public class RetrofitTmdbApiServiceTest {

    private static final String TEST_KEY = "TEST_KEY";

    private static final String SAMPLE_URL = "http://example.com";

    private TmdbApiServiceBuilder apiServiceBuilder = new TmdbApiServiceBuilder(SAMPLE_URL);

    private RetrofitTmdbApiService retrofitTmdbApiService;

    @Before
    public void setUp() {
        retrofitTmdbApiService = new RetrofitTmdbApiService(apiServiceBuilder);
    }

    @Test
    public void testGetMostPopularServiceCreated() throws Exception {
        String expectedUrl = "http://example.com/3/movie/popular?api_key=TEST_KEY";
        Call<TmdbResponse> mostPopular = retrofitTmdbApiService.getMostPopular(TEST_KEY);

        assertThat(mostPopular).isNotNull();
        assertThat(mostPopular.isExecuted()).isFalse();
        assertThat(mostPopular.request().url().toString()).isEqualTo(expectedUrl);
    }

    @Test
    public void testSearchMovieNameServiceCreated() throws Exception {
        String expectedUrl = "http://example.com/3/search/movie?query=name&api_key=TEST_KEY";
        Call<TmdbResponse> mostPopular = retrofitTmdbApiService.searchByName("name", TEST_KEY);

        assertThat(mostPopular).isNotNull();
        assertThat(mostPopular.isExecuted()).isFalse();
        assertThat(mostPopular.request().url().toString()).isEqualTo(expectedUrl);
    }

}
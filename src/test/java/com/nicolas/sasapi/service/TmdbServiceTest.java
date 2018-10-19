package com.nicolas.sasapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.*;

import com.nicolas.sasapi.domainvalue.TmdbMovie;
import com.nicolas.sasapi.exception.TmdbClientException;
import com.nicolas.sasapi.service.restclient.TmdbApiService;
import com.nicolas.sasapi.service.restclient.bean.TmdbResponse;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.mock.Calls;

@RunWith(SpringJUnit4ClassRunner.class)
public class TmdbServiceTest {

    private static final String TEST_KEY = "TEST-KEY";

    private TmdbService tmdbService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private TmdbApiService tmdbApiService;

    private TmdbResponse tmdbApiResponse = testTmdbResponse();

    private TmdbMovie tmdbMovie = createTmdbMovie(30L, "sample");

    @Before
    public void setUp() {
        tmdbService = new TmdbService(TEST_KEY, tmdbApiService);
    }

    @Test
    public void testMostPopularRetrievedWhenTmdbAPIResponseWorks() throws Exception {
        Call<TmdbResponse> retrofitMockCaller = Calls.response(tmdbApiResponse);
        when(tmdbApiService.getMostPopular(TEST_KEY)).thenReturn(retrofitMockCaller);

        List<TmdbMovie> tmdbMostPopular = tmdbService.getTmdbMostPopularMovies();

        assertThat(tmdbMostPopular)
                .hasSize(2)
                .extracting("tmdbId", "title")
                .containsExactly(tuple(10L, "first"), tuple(20L, "second"));
    }

    @Test
    public void testMostPopularFailServerExceptionExpectsTmdbClientException() throws Exception {
        Call<TmdbResponse> retrofitMockCaller = Calls.failure(new UnknownHostException("unknown server"));
        when(tmdbApiService.getMostPopular(TEST_KEY)).thenReturn(retrofitMockCaller);

        thrown.expect(TmdbClientException.class);
        thrown.expectMessage("unknown server");

        tmdbService.getTmdbMostPopularMovies();
    }

    @Test
    public void testMostPopularNullBodyThrowsTmdbClientException() throws Exception {
        Response<TmdbResponse> emptyBody = Response.success(null);
        Call<TmdbResponse> retrofitMockCaller = Calls.response(emptyBody);
        when(tmdbApiService.getMostPopular(TEST_KEY)).thenReturn(retrofitMockCaller);

        thrown.expect(TmdbClientException.class);
        thrown.expectMessage("Invalid Json returned from TMDB API");

        tmdbService.getTmdbMostPopularMovies();
    }

    @Test
    public void testMostPopularFailedResponseThrowsTmdbClientException() throws Exception {
        ResponseBody errorBody = ResponseBody.create(MediaType.parse("application/json"), "{\"key\":[\"somevalue\"]}");
        Response<TmdbResponse> emptyBody = Response.error(403, errorBody);

        Call<TmdbResponse> retrofitMockCaller = Calls.response(emptyBody);
        when(tmdbApiService.getMostPopular(TEST_KEY)).thenReturn(retrofitMockCaller);

        thrown.expect(TmdbClientException.class);

        tmdbService.getTmdbMostPopularMovies();
    }

    @Test
    public void testSearchTmdbMovieRetrieveMoviesFromApi() throws Exception {
        Call<TmdbResponse> retrofitMockCaller = Calls.response(tmdbApiResponse);
        when(tmdbApiService.searchByName("name", TEST_KEY)).thenReturn(retrofitMockCaller);

        List<TmdbMovie> tmdbMostPopular = tmdbService.searchTmdbMovie("name");

        assertThat(tmdbMostPopular)
                .hasSize(2)
                .extracting("tmdbId", "title")
                .containsExactly(tuple(10L, "first"), tuple(20L, "second"));
    }

    @Test
    public void testSearchTmdbMovieServerExceptionExpectsTmdbClientException() throws Exception {
        Call<TmdbResponse> retrofitMockCaller = Calls.failure(new UnknownHostException("unknown server"));
        when(tmdbApiService.searchByName("name", TEST_KEY)).thenReturn(retrofitMockCaller);

        thrown.expect(TmdbClientException.class);
        thrown.expectMessage("unknown server");

        tmdbService.searchTmdbMovie("name");
    }

    @Test
    public void testFindTmdbMovieFromApi() throws Exception {
        Call<TmdbMovie> retrofitMockCaller = Calls.response(tmdbMovie);
        when(tmdbApiService.findByTmdbId(30L, TEST_KEY)).thenReturn(retrofitMockCaller);

        TmdbMovie tmdbMovie = tmdbService.findByTmdbId(30L);

        assertThat(tmdbMovie)
                .extracting("tmdbId", "title")
                .contains(30L, "sample");
    }

    @Test
    public void testFindTmdbMovieServerFailsExpectsTmdbClientException() throws Exception {
        Call<TmdbMovie> retrofitMockCaller = Calls.failure(new UnknownHostException("unknown server"));
        when(tmdbApiService.findByTmdbId(30L, TEST_KEY)).thenReturn(retrofitMockCaller);

        thrown.expect(TmdbClientException.class);
        thrown.expectMessage("unknown server");

        tmdbService.findByTmdbId(30L);
    }


    private TmdbResponse testTmdbResponse() {
        TmdbResponse response = new TmdbResponse();
        List<TmdbMovie> tmdbMovies = Arrays.asList(createTmdbMovie(10L, "first"), createTmdbMovie(20L, "second"));
        response.setTmdbMovies(tmdbMovies);
        return response;
    }

    private TmdbMovie createTmdbMovie(Long imdbId, String title) {
        return TmdbMovie.builder()
                .tmdbId(imdbId)
                .title(title).build();
    }


}
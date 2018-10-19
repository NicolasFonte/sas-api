package com.nicolas.sasapi.service.restclient;

import static org.assertj.core.api.Assertions.assertThat;

import okhttp3.Request;
import org.junit.Test;

public class TmdbApiServiceBuilderTest {

    @Test
    public void testApiTmdbServiceCreated() throws Exception {
        TmdbApiServiceBuilder tmdbApiServiceBuilder = new TmdbApiServiceBuilder("http://example.com");

        TmdbApiService tmdbApiService = tmdbApiServiceBuilder.buildTmdbApiService();
        Request request = tmdbApiService.getMostPopular("someKey").request();

        assertThat(request.url().host()).isEqualTo("example.com");
        assertThat(request.url().isHttps()).isFalse();
    }

}
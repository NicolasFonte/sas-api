package com.nicolas.sasapi.controller;

import static com.nicolas.sasapi.TestDataProvider.createUser;
import static com.nicolas.sasapi.controller.mapper.MovieMapper.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicolas.sasapi.controller.bean.TmdbIdRequest;
import com.nicolas.sasapi.controller.mapper.MovieMapper;
import com.nicolas.sasapi.controller.responsehandler.CustomizedResponseEntityExceptionHandler;
import com.nicolas.sasapi.exception.UserNotFoundException;
import com.nicolas.sasapi.service.UserService;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        UserController userController = new UserController(userService);
        mockMvc = standaloneSetup(userController)
                .setControllerAdvice(new CustomizedResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testAPIMarkFavoriteReturnCreatedWhenMovieAdded() throws Exception {
        TmdbIdRequest request = new TmdbIdRequest(10L);
        when(userService.addFavoriteMovie(1L, 10L)).thenReturn(createUser("john"));

        mockMvc.perform(post("/api/v1/users/1/favorites")
                .content(convertObjectToJsonBytes(request))
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.name", is("john")));

        verify(userService, only()).addFavoriteMovie(1L, 10L);
    }

    @Test
    public void testAPIMarkFavoriteWhenInvalidUserThrowsNotFound() throws Exception {
        TmdbIdRequest request = new TmdbIdRequest(10L);
        when(userService.addFavoriteMovie(1L, 10L)).thenThrow(new UserNotFoundException(1L));

        mockMvc.perform(post("/api/v1/users/1/favorites")
                .content(convertObjectToJsonBytes(request))
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("User 1 not exists in the system")));

        verify(userService, only()).addFavoriteMovie(1L, 10L);
    }

    @Test
    public void testAPIMarkFavoriteWhenInvalidJsonDataThrowsBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/users/1/favorites")
                .content(convertObjectToJsonBytes("some invalid data"))
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

        verify(userService, never()).addFavoriteMovie(1L, 10L);
    }

    @Test
    public void findUserFavoriteMovies() throws Exception {
        when(userService.findUserFavoriteMovies(1L)).thenReturn(createUser("john").getFavorites());

        mockMvc.perform(get("/api/v1/users/1/favorites"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[0].id", is(20)))
                .andExpect(jsonPath("$[0].title", is("name1")))
                .andExpect(jsonPath("$[0].popularity", is(10)))
                .andExpect(jsonPath("$[1].id", is(40)))
                .andExpect(jsonPath("$[1].title", is("name2")))
                .andExpect(jsonPath("$[1].popularity", is(1)));

        verify(userService, only()).findUserFavoriteMovies(1L);
    }

    private byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}
package com.nicolas.sasapi.service;

import static com.nicolas.sasapi.TestDataProvider.createFavorite;
import static com.nicolas.sasapi.TestDataProvider.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nicolas.sasapi.domain.FavoriteMovie;
import com.nicolas.sasapi.domain.User;
import com.nicolas.sasapi.domainvalue.TmdbMovie;
import com.nicolas.sasapi.exception.TmdbIdExistsException;
import com.nicolas.sasapi.exception.UserNotFoundException;
import com.nicolas.sasapi.repository.MovieRepository;
import com.nicolas.sasapi.repository.UserRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    private UserService userService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private UserRepository userRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private TmdbService tmdbService;

    @Before
    public void setUp() {
        userService = new UserService(userRepository, movieRepository, tmdbService);
    }

    @Test
    public void testFindUserFavoriteMovies() throws Exception {
        when(userRepository.findById(10L)).thenReturn(Optional.of(createUser("john")));

        List<FavoriteMovie> userFavoriteMovies = userService.findUserFavoriteMovies(10L);

        assertThat(userFavoriteMovies)
                .hasSize(2)
                .extracting("id", "tmdbId", "title", "popularity")
                .containsExactly(Tuple.tuple(10L, 20L, "name1", BigDecimal.TEN),
                        Tuple.tuple(30L, 40L, "name2", BigDecimal.ONE));
    }

    @Test
    public void testFindUserFavoriteMoviesThrowsUserNotFoundWhenDoesNotExists() throws Exception {
        when(userRepository.findById(10L)).thenReturn(Optional.empty());

        thrown.expect(UserNotFoundException.class);

        userService.findUserFavoriteMovies(10L);
    }

    @Test
    public void testAddFavoriteMovieAlreadySelectedThrowException() throws Exception {
        when(userRepository.findById(10L)).thenReturn(Optional.of(createUser("john")));

        thrown.expect(TmdbIdExistsException.class);

        userService.addFavoriteMovie(10L, 20L);
    }

    @Test
    public void testAddFavoriteMovieWhenMovieExistsInDb() throws Exception {
        when(movieRepository.findByTmdbId(50L))
                .thenReturn(Optional.of(createFavorite(10L, 50L, "sample", BigDecimal.TEN)));
        when(userRepository.findById(10L)).thenReturn(Optional.of(createUser("john")));

        userService.addFavoriteMovie(10L, 50L);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue().getFavorites())
                .hasSize(3)
                .extracting("id", "tmdbId")
                .contains(tuple(10L, 50L));
    }

    @Test
    public void testAddFavoriteMovieWhenMovieNotExistsInDb() throws Exception {
        TmdbMovie tmdbMovie = new TmdbMovie();
        tmdbMovie.setTmdbId(50L);
        tmdbMovie.setTitle("tmdbMovie");
        when(movieRepository.findByTmdbId(50L)).thenReturn(Optional.empty());
        when(userRepository.findById(10L)).thenReturn(Optional.of(createUser("john")));
        when(tmdbService.findByTmdbId(50L)).thenReturn(tmdbMovie);

        userService.addFavoriteMovie(10L, 50L);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue().getFavorites())
                .hasSize(3)
                .extracting("tmdbId", "title")
                .contains(tuple(50L, "tmdbMovie"));
    }

}
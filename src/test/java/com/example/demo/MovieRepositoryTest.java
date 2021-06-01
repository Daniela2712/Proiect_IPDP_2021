package com.example.demo;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.example.demo.movie.Movie;
import com.example.demo.movie.MovieRepository;
import com.example.demo.movie.MovieService;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class MovieRepositoryTest {

	@InjectMocks
	private MovieService movieService;

	@Mock
	private MovieRepository movieRepository;

	@Mock
	private TestEntityManager entityManager;


	@Test
	public void CreateMovie() {
		Movie movie = new Movie();
		movie.setTitlu("filmTest");
		movie.setGen("genTest");
		movie.setAn_aparitie(1999);
		movie.setDescription("DescriereTest");
		movieRepository.save(movie);
	}
	
	
	@Test
	public void getMoviesTest() {
		when(movieRepository.findAll()).thenReturn(Stream
				.of(new Movie(), new Movie()).collect(Collectors.toList()));
		assertEquals(2, movieService.getMovies().size());
	}

	@Test
	public void getMoviebyTitleTest() {
		String title = "coco";
		Movie movie= movieRepository.findByTitle(title);
		assertEquals(movie, movieService.getMoviebyTitle(title));
	}

	@Test
	public void deleteUserTest() {
		Movie movie = movieRepository.findByTitle("coco");
		movieRepository.delete(movie);
		verify(movieRepository, times(1)).delete(movie);
	}



}

package com.example.demo.movie;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.user.User;
@Component
public class MovieService {
	@Autowired
	MovieRepository movieRepository;
	
	public void saveMovie(Movie movie) {
		movieRepository.save(movie);
	}
	
	public void delete(Integer id) {
		movieRepository.deleteById(id);
		
	}
	public void saveImage(Movie imageGallery) {
		movieRepository.save(imageGallery);	
	}

	public List<Movie> getAllActiveImages() {
		return movieRepository.findAll();
	}

	public Optional<Movie> getImageById(Integer id) {
		return movieRepository.findById(id);
	}

	public List<Movie> getMovies() {
		 List<Movie> movies=movieRepository.findAll();
		return movies;
	}

	public Movie getMoviebyTitle(String title) {
		
		return movieRepository.findByTitle(title);
	}

	
}

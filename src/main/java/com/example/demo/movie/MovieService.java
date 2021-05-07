package com.example.demo.movie;

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
}

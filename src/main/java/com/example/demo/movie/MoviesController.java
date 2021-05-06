package com.example.demo.movie;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.user.User;

@Controller

public class MoviesController {
	@Autowired
	MovieRepository movieRepository;
	
	@GetMapping("/movies")
	public String ShowMovies(Model model) {
		 List<Movie> listMovies = movieRepository.findAll();
		    model.addAttribute("listMovies", listMovies).addAllAttributes(listMovies);
		return "movies.html";
		
	}
	@GetMapping("/movieAdministration")
	public String listUsers(Model model, HttpServletRequest request) {
	    List<Movie> listMovies = movieRepository.findAll();
	    model.addAttribute("listMovies", listMovies).addAllAttributes(listMovies);
	    
	    return "movieAdministration.html";
	}
 
}

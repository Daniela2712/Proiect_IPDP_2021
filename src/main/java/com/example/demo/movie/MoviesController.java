package com.example.demo.movie;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.user.User;

@Controller

public class MoviesController {
	@Autowired
	MovieRepository movieRepository;
	@Autowired
	MovieService movieService;
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
	
	@RequestMapping(value = "deleteMovie/{id}", method = RequestMethod.POST)
	public String delete(@PathVariable("id") Integer id) {
		movieService.delete(id);
		return "redirect:/movieAdministration";
	}
	@GetMapping("/editMoviePage/{id}")
	public String ShowEditMoviePage(ModelMap map, HttpServletRequest request, @PathVariable("id") Integer id) {
	    Optional<Movie> movie2 = movieRepository.findById(id); 
	    Movie movie=movie2.get();
	    map.addAttribute("id", movie.getId());
	    map.addAttribute("titlu", movie.getTitlu());
	    map.addAttribute("gen", movie.getGen());
	    map.addAttribute("an_aparitie", movie.getAn_aparitie());
	    map.addAttribute("description", movie.getDescription());
	    return "editMoviePage.html";
	}
	@PostMapping("/editMovie/{id}")
	public String saveEdit(Movie movie, @PathVariable("id") Integer id, @ModelAttribute("movie")  Model model) {
		//Optional<Movie> movie2=movieRepository.findById(id);
		
		 movie.setTitlu(movie.getTitlu());
		 movie.setGen(movie.getGen());
		 movie.setAn_aparitie(movie.getAn_aparitie());
		 movie.setDescription(movie.getDescription());
		// movie=movie2.get();
		 movieService.saveMovie(movie);
		 return "redirect:/movieAdministration";
	}
	
}

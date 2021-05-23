package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.movie.Movie;
import com.example.demo.movie.MovieRepository;
import com.example.demo.movie.MovieService;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;


@Controller
public class homeController  {
	@Autowired
	UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	MovieRepository movieRepository;
	@Autowired
	private  MovieService movieService;
	
	@RequestMapping("/home")
	public String showhomePage(Model model, Model map) {
		List<Movie> listMovies = movieRepository.findAll();
	    model.addAttribute("listMovies", listMovies).addAllAttributes(listMovies);
	    List<Movie> images = movieService.getAllActiveImages();
		map.addAttribute("images", images);
		return "home.html";
		
	}
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		
		return "signup.html";
	}
	@PostMapping("/process_register")
	public String saveUser( User user,  @RequestParam("ConfirmPassword") String confPass, @RequestParam("password") String pass) {
		 if(!(pass.contentEquals(confPass))) {
			 return ("redirect:/signup");
		 }
		 else {
			 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			 String encodedPassword = passwordEncoder.encode(user.getPassword());
			 user.setPassword(encodedPassword);
			 user.setEnabled(0);
			 user.setRole("USER");
			 userService.saveUser(user);
		return "home.html";
		 }
	}
	
	@PutMapping("/dologout/{id}" )
    public String logoutUser(User user, Model model)
    {
			Optional<User> user2=userRepository.findById(user.getId());
			List<Movie> listMovies = movieRepository.findAll();
		    model.addAttribute("listMovies", listMovies).addAllAttributes(listMovies);
	        user=user2.get();
	        user.setEnabled(0);
	        user.setFirst_name(user.getFirst_name());
	        user.setLast_name(user.getLast_name());
	        user.setPassword(user.getPassword());
	        user.setEmail(user.getEmail());
	        userService.saveUser(user);
  
        return "redirect:/home";
    }
}

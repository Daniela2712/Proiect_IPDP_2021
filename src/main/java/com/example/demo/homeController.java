package com.example.demo;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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

public class homeController implements ApplicationRunner   {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	MovieRepository movieRepository;
	@Autowired
	private  MovieService movieService;
	private static final Logger logger = LogManager.getLogger(homeController.class);
	 @Override
	    public void run(ApplicationArguments applicationArguments) throws Exception {
	        logger.debug("Debugging log");
	        logger.info("Info log");
	        logger.warn("Hey, This is a warning!");
	        logger.error("Oops! We have an Error. OK");
	        logger.fatal("FATALLLLLL");
	    }
	@RequestMapping("/home")
	public String showhomePage(Model model, Model map) {
		List<Movie> listMovies = movieRepository.findAll();
	    model.addAttribute("listMovies", listMovies).addAllAttributes(listMovies);
	    List<Movie> images = movieService.getAllActiveImages();
		map.addAttribute("images", images);
		logger.info("home view");
		return "home.html";
		
	}
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		logger.info("signup view");
		return "signup.html";
	}
	@PostMapping("/process_register")
	public String saveUser( User user,  @RequestParam("ConfirmPassword") String confPass, @RequestParam("password") String pass) {
		 if(!(pass.contentEquals(confPass))) {
			 logger.warn("Password and Confirm passsword are not the same!");
			 return ("redirect:/signup");
		 }
		 else {
			 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			 String encodedPassword = passwordEncoder.encode(user.getPassword());
			 user.setPassword(encodedPassword);
			 user.setEnabled(0);
			 user.setRole("USER");
			 userService.saveUser(user);
			 logger.info("home view");
			 return ("redirect:/home");
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
	        logger.info("logout success");
        return "redirect:/home";
    }
}

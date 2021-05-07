package com.example.demo.user;


import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.movie.Movie;
import com.example.demo.movie.MovieRepository;

@Controller
@ComponentScan(basePackages={"com.example.demo.user"})
public class UserController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired 
	private MovieRepository movieRepository;
	
	
	@GetMapping("/adminPage")
	public String showAdminPage(Model model, HttpServletRequest request) { 
	    Principal principal = request.getUserPrincipal();
	    User user=userRepository.findByEmail(principal.getName());
	    List<Movie> listMovies = movieRepository.findAll();
	    model.addAttribute("listMovies", listMovies).addAllAttributes(listMovies);
        user.setEnabled(1);
        user.setFirst_name(user.getFirst_name());
        user.setLast_name(user.getLast_name());
        user.setPassword(user.getPassword());
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        userService.saveUser(user);
	    return "adminPage.html";
	}
	
	@RequestMapping(value = {"/userPage"}, method = RequestMethod.GET)
	public ModelAndView userPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("userPage.html");
		return model;
	}
	
	@GetMapping("/userAdministration")
	public String listUsers(Model model, HttpServletRequest request) {
	    List<User> listUsers = userRepository.findAll();
	    model.addAttribute("listUsers", listUsers).addAllAttributes(listUsers);
	    
	    return "userAdministration.html";
	}
}

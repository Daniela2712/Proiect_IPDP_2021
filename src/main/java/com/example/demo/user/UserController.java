package com.example.demo.user;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class UserController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	private UserService userService;
	
	@PostMapping("/process_register")
	public String saveUser( User user) {
		 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		 String encodedPassword = passwordEncoder.encode(user.getPassword());
		 user.setPassword(encodedPassword);
		 userService.saveUser(user);
		return "home.html";
	}
	@GetMapping("/users")
	public String listUsers(Model model) {
	    List<User> listUsers = userRepository.findAll();
	    model.addAttribute("listUsers", listUsers);
	     
	    return "users";
	}
	
}

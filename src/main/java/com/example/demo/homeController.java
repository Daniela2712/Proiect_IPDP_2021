package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;


@Controller
public class homeController  {
	@Autowired
	UserRepository userRepository;
	@Autowired
	private UserService userService;
	
	@RequestMapping("/home")
	public String showhomePage() {
		return "home.html";
		
	}
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		
		return "signup.html";
	}
	@PostMapping("/process_register")
	public String saveUser( User user) {
		 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		 String encodedPassword = passwordEncoder.encode(user.getPassword());
		 user.setPassword(encodedPassword);
		 user.setEnabled(0);
		 user.setRole("USER");
		 userService.saveUser(user);
		return "home.html";
	}
	
	@PutMapping("/dologout/{id}" )
    public String logoutUser(User user)
    {
		Optional<User> user2=userRepository.findById(user.getId());
		
	        user=user2.get();
	        user.setEnabled(0);
	        user.setFirst_name(user.getFirst_name());
	        user.setLast_name(user.getLast_name());
	        user.setPassword(user.getPassword());
	        user.setUsername(user.getUsername());
	        user.setEmail(user.getEmail());
	        userService.saveUser(user);
  
        return "home.html";
    }
}

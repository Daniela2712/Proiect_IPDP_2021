package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@Controller
public class homeController {
	
	@Autowired
    private UserRepository userRepository;
	
	@RequestMapping("/home")
	public String showhomePage() {
		return "home.html";
		
	}
	@RequestMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		
		return "signup.html";
	}
	@PostMapping("/process_register")
	public String processRegister(User user) {
		
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(user.getPassword());
	    user.setPassword(encodedPassword);
	    userRepository.save(user);
	    
	    return "register_success.html";
	}
}

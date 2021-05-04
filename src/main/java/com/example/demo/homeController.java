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
	
	

//
//	    @Autowired
//	    public homeController(UserService service){
//	        userService = service;
//
//	    }
//
//	    //For getting all the transactions
//	    @RequestMapping(value = "/userss", method= RequestMethod.GET)
//	    public ResponseEntity<List<User>> findAll(){
//	        System.out.println(userService.findAllUsers().size());
//	        return new ResponseEntity<List<User>>(userService.findAllUsers(), HttpStatus.OK);
//
//	    }
//
//	    //For adding a transaction
//	    @RequestMapping(value = "/userss", method = RequestMethod.POST)
//	    public User addUser(@RequestBody User user){
//
//	        return (userService.saveUser(user));
//	    }
//
//	    //For updating a transaction
//	    @RequestMapping(value = "/dologout", method = RequestMethod.PUT,consumes = {"application/x-www-form-urlencoded"})
//	    public User updateUser( User user){
//	        User dbuser = userService.findUserById(user.getId());
//	        if (dbuser == null) {
//	            throw new RuntimeException("User to update doesn't exist");
//	        }else {
//	        	
//	        	user.setEnabled(5);
//	        	return (userService.saveUser(user));
//	        }
//	    }
//
//	    //For deleting a transaction
//	    @RequestMapping(value = "/userss/{id}", method = RequestMethod.DELETE)
//	    public String deleteTransaction(@PathVariable Integer id){
//	        User tempTransaction = userService.findUserById(id);
//	        if(tempTransaction == null){
//	            throw new RuntimeException("User Id not found");
//	        }
//	        userService.deleteUserById(id);
//	        return "deleted user id " + id;
//
//	    }
//	
}

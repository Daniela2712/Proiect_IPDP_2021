package com.example.demo.user;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@ComponentScan(basePackages={"com.example.demo.user"})
public class UserController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/users")
	public String listUsers(Model model) {
		
	    List<User> listUsers = userRepository.findAll();
	    model.addAttribute("listUsers", listUsers);
	   
	    return "users";
	}
	
	@RequestMapping(value = {"/userPage"}, method = RequestMethod.GET)
	public ModelAndView userPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("userPage.html");
		return model;
	}
	
	@RequestMapping("/adminPage")
		public String showAdminPage() {
			return "adminPage.html";			
		}	
}

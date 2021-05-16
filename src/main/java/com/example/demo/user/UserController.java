package com.example.demo.user;


import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.example.demo.movie.Movie;
import com.example.demo.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Value;

@Controller
@RequestMapping("/")
@ComponentScan(basePackages={"com.example.demo.user"})
public class UserController {
	 @Value("${mainstay.web.context:/}")
	  private String context;

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
	@RequestMapping(value = "deleteUser/{id}", method = RequestMethod.POST)
	public String delete(@PathVariable("id") Integer id) {
		userService.delete(id);
		return "redirect:/userAdministration";
	}
	
	@GetMapping("/upgradeUser/{id}")
	public String saveEdit(@ModelAttribute("user")  User user, @PathVariable("id") Integer id) {
		Optional<User> user2=userRepository.findById(id);
		User user3=user2.get();
		 user.setRole(user.getRole());
		 user.setEnabled(1);
	        user.setFirst_name(user3.getFirst_name());
	        user.setLast_name(user3.getLast_name());
	        user.setPassword(user3.getPassword());
	        user.setEmail(user3.getEmail());
	        userService.saveUser(user);
		 return "redirect:/userAdministration";
	}
	@GetMapping("/profile/{id}")
	public String ShowUserProfile(ModelMap map, HttpServletRequest request, @PathVariable("id") Integer id) {
	    Optional<User> user2 = userRepository.findById(id); 
	    User user=user2.get();
	    map.addAttribute("id", user.getId());
	    map.addAttribute("first_name", user.getFirst_name());
	    map.addAttribute("last_name", user.getLast_name());
	    map.addAttribute("email", user.getEmail());
	    map.addAttribute("password", user.getPassword());
	    return "userProfile.html";
	}
	@PostMapping("/editUser/{id}")
	public String saveEdit(User user, @RequestParam("e_first_name") String first_name, @RequestParam("e_last_name") String last_name,
			@RequestParam("e_email") String email,@RequestParam("e_password") String password, @PathVariable("id") Integer id){
			Optional<User> user2=userRepository.findById(id);
			 user=user2.get();
			 user.setFirst_name(first_name);
			 user.setLast_name(last_name);
			 user.setEmail(email);	
			 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			 String encodedPassword = passwordEncoder.encode(password);
			 user.setPassword(encodedPassword);
			 userService.saveUser(user);
			 return "redirect:/profile/{id}";
	}
	
	 @PostMapping("/users/save")
	    public RedirectView saveUser(User user,
	            @RequestParam("image") MultipartFile multipartFile) throws IOException {
	         
	        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	        user.setPhotos(fileName);
	         
	        User savedUser = userRepository.save(user);
	 
	        String uploadDir = "user-photos/" + savedUser.getId();
	 
	        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
	         
	        return new RedirectView("/home", true);
	    }
	 //
	 @ResponseBody
	 @PostMapping("/updatePassword")
	 //@PreAuthorize("hasRole('READ_PRIVILEGE')")
	 public String changeUserPassword(Locale locale, 
	   @RequestParam(value="pass") String password, 
	   @RequestParam(value="oldpass") String oldPassword,
	   @RequestParam(value="passConfirm") String passConfirm
	   ) {
	     User user = userService.findUserByEmail(
	       SecurityContextHolder.getContext().getAuthentication().getName());
	 
	     if(!(password.contentEquals(passConfirm)))
	    	 return "Password mismatch";
	     
	     else {
		     if (!userService.checkIfValidOldPassword(user, oldPassword)) {
		         throw new InvalidOldPasswordException();
		     }
		     userService.changeUserPassword(user, password);
		     return "success";
	     }
	 }
	 
}

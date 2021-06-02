package com.example.demo.movie;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.user.UserController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@Controller

public class MoviesController {
	@Autowired
	MovieRepository movieRepository;
	@Autowired
	MovieService movieService;
	
	private static final Logger log = LogManager.getLogger(MoviesController.class);
	
	@GetMapping("/movies")
	public String ShowMovies(Model model) {
		 List<Movie> listMovies = movieRepository.findAll();
		    model.addAttribute("listMovies", listMovies).addAllAttributes(listMovies);
		return "movies.html";
		
	}
	@GetMapping("/movieAdministration")
	public String listUsers(Model map, Model model, HttpServletRequest request) {
	    List<Movie> listMovies = movieRepository.findAll();
	    model.addAttribute("listMovies", listMovies).addAllAttributes(listMovies);
	    List<Movie> images = movieService.getAllActiveImages();
		map.addAttribute("images", images);
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
	public String saveEdit(Movie movie, @RequestParam("e_titlu") String titlu, @RequestParam("e_gen") String gen,
			@RequestParam("e_an_aparitie") Integer an_aparitie, @RequestParam("e_description") String description, @PathVariable("id") Integer id ) {
		Optional<Movie> movie2=movieRepository.findById(id);
		movie=movie2.get();
		 movie.setTitlu(titlu);
		 movie.setGen(gen);
		 movie.setAn_aparitie(an_aparitie);
		 movie.setDescription(description);
		 
		 movieService.saveMovie(movie);
		 
		 return "redirect:/movieAdministration";
	}
	@PostMapping("/addMovie")
	public String AddMoviePage(Movie movie,  @RequestParam("title") String titlu, @RequestParam("gen") String gen,
			@RequestParam("an_aparitie") Integer an_aparitie, @RequestParam("description") String description) {
		movie.setTitlu(titlu);
		 movie.setGen(gen);
		 movie.setAn_aparitie(an_aparitie);
		 movie.setDescription(description);
		 movieService.saveMovie(movie);
	    return "redirect:/movieAdministration";
	}

		private String uploadFolder;

		//private final Logger log = LoggerFactory.getLogger(this.getClass());
		@PostMapping("/image/saveImageDetails")
		public String createProduct( Model model, HttpServletRequest request
				,final @RequestParam("image") MultipartFile file,@RequestParam("title") String titlu, @RequestParam("gen") String gen,
				@RequestParam("an_aparitie") Integer an_aparitie, @RequestParam("description") String description) {
			try {
				String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
				String uploadDirectory1 = request.getServletContext().getRealPath(uploadFolder);
				log.info("uploadDirectory:: " + uploadDirectory1);
				String fileName = file.getOriginalFilename();
				String filePath = Paths.get(uploadDirectory1, fileName).toString();
				log.info("FileName: " + file.getOriginalFilename());
				if (fileName == null || fileName.contains("..")) {
					model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
					//return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.BAD_REQUEST);
					return "home.html";
				}
				Date createDate = new Date();
				try {
					File dir = new File(uploadDirectory1);
					if (!dir.exists()) {
						log.info("Folder Created");
						dir.mkdirs();
					}
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
					stream.write(file.getBytes());
					stream.close();
				} catch (Exception e) {
					log.info("in catch");
					e.printStackTrace();
				}
				byte[] imageData = file.getBytes();
				Movie imageGallery = new Movie();
				imageGallery.setTitlu(titlu);
				imageGallery.setGen(gen);
				imageGallery.setAn_aparitie(an_aparitie);
				imageGallery.setDescription(description);
				imageGallery.setImage(imageData);
				movieService.saveImage(imageGallery);
				log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
				return "redirect:/movieAdministration";
			} catch (Exception e) {
				e.printStackTrace();
				log.info("Exception: " + e);
				//return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				return "home.html";
			}
		}
		
		@GetMapping("/image/display/{id}")
		@ResponseBody
		void showImage(@PathVariable("id") Integer id, HttpServletResponse response, Optional<Movie> imageGallery)
				throws ServletException, IOException {
			log.info("Id :: " + id);
			imageGallery = movieService.getImageById(id);
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(imageGallery.get().getImage());
			response.getOutputStream().close();
		}

		@GetMapping("/image/imageDetails")
		String showProductDetails(@RequestParam("id") Integer id, Optional<Movie> imageGallery, Model model) {
			try {
				log.info("Id :: " + id);
				if (id != 0) {
					imageGallery = movieService.getImageById(id);
				
					log.info("products :: " + imageGallery);
					if (imageGallery.isPresent()) {
						model.addAttribute("id", imageGallery.get().getId());
						model.addAttribute("description", imageGallery.get().getDescription());
						model.addAttribute("titlu", imageGallery.get().getTitlu());
						model.addAttribute("an_aparitie", imageGallery.get().getAn_aparitie());
						model.addAttribute("gen", imageGallery.get().getGen());
						return "imagedetails";
					}
					return "redirect:/movieAdministration";
				}
			return "redirect:/movieAdministration";
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Exception: " + e);
				return "redirect:/movieAdministration";
			}	
		}

		@GetMapping("/image/show")
		String show(Model map) {
			List<Movie> images = movieService.getAllActiveImages();
			map.addAttribute("images", images);
			return "redirect:/movieAdministration";
		}
		@GetMapping("/action")
		public String actionview(Model model) {
			 List<Movie> listMovies = movieRepository.findAll();
			    model.addAttribute("listMovies", listMovies).addAllAttributes(listMovies);
			return "action.html";
}
		@GetMapping("/adventure")
		public String adventureview(Model model) {
			 List<Movie> listMovies = movieRepository.findAll();
			    model.addAttribute("listMovies", listMovies).addAllAttributes(listMovies);
			return "adventure.html";
}		
		@GetMapping("/animation")
		public String animationview(Model model) {
			 List<Movie> listMovies = movieRepository.findAll();
			    model.addAttribute("listMovies", listMovies).addAllAttributes(listMovies);
			return "animation.html";
}
		@GetMapping("/comedy")
		public String comedyview(Model model) {
			 List<Movie> listMovies = movieRepository.findAll();
			    model.addAttribute("listMovies", listMovies).addAllAttributes(listMovies);
			return "comedy.html";
}
		@GetMapping("/drama")
		public String dramaview(Model model) {
			 List<Movie> listMovies = movieRepository.findAll();
			    model.addAttribute("listMovies", listMovies).addAllAttributes(listMovies);
			return "drama.html";
}
		@GetMapping("/horror")
		public String horrorview(Model model) {
			 List<Movie> listMovies = movieRepository.findAll();
			    model.addAttribute("listMovies", listMovies).addAllAttributes(listMovies);
			return "horror.html";
}
		@GetMapping("/love")
		public String loveview(Model model) {
			 List<Movie> listMovies = movieRepository.findAll();
			    model.addAttribute("listMovies", listMovies).addAllAttributes(listMovies);
			return "love.html";
}
		@GetMapping("/userProfile")
		public String usersview(Model model) {
			 List<Movie> listMovies = movieRepository.findAll();
			    model.addAttribute("listMovies", listMovies).addAllAttributes(listMovies);
			return "userProfile.html";
}
}

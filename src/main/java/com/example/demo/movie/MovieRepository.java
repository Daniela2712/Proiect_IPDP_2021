package com.example.demo.movie;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.movie.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
	@Query("SELECT m FROM Movie m WHERE m.an_aparitie >= 2019")
    public Movie findByEmail(String titlu);
	
	 @Query("SELECT u FROM User u WHERE u.email = :email")
	    public Movie getUserByEmail(@Param("email") String email);

	 @Query("SELECT m FROM Movie m WHERE m.id = ?1")
	   public Optional<Movie> findById(Integer id);
	 
	 @Query("SELECT m FROM Movie m WHERE m.an_aparitie = ?1")
		public List<String> findByAnAparitie(Integer an_aparitie);
 
	 
} 

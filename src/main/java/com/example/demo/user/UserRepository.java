package com.example.demo.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);
	
	 @Query("SELECT u FROM User u WHERE u.email = :email")
	    public User getUserByEmail(@Param("email") String email);

	 @Query("SELECT u FROM User u WHERE u.id = ?1")
	   public Optional<User> findById(Integer id);
	 
	 @EntityGraph(attributePaths={"profilePicture"})
	    public User findWithPropertyPictureAttachedById(Integer id);

	
}

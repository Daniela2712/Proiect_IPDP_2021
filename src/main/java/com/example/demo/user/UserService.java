package com.example.demo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.demo.movie.Movie;

@Component
public interface UserService {
	

	public User saveUser(User user);
	public void delete(Integer id);
	public User findUserByEmail(String email);
	boolean checkIfValidOldPassword(User user, String oldPassword);
	void changeUserPassword(User user, String password);
	public Optional<User> getImageProfileById(Integer id);
	public void saveImage(User user);
	public List<User> getUsers();
	public User getUserbyEmail(String email);
}



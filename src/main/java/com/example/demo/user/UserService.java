package com.example.demo.user;


import org.springframework.stereotype.Component;

@Component

public interface UserService {
	

	public void saveUser(User user);
	public void delete(Integer id);
	public User findUserByEmail(String email);
	boolean checkIfValidOldPassword(User user, String oldPassword);
	void changeUserPassword(User user, String password);
}



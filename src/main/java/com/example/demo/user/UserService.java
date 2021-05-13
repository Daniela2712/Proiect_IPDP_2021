package com.example.demo.user;


import org.springframework.stereotype.Component;

@Component

public interface UserService {
	

	public void saveUser(User user);
	public void delete(Integer id);
}



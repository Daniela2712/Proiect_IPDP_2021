package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Transactional(rollbackFor = {RuntimeException.class})
	public void saveUser(User user) {
		User user1 = null;
		
		user1 = userRepository.save(user);
	}

}

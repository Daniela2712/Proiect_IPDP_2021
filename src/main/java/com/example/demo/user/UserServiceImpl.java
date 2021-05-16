package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Transactional(rollbackFor = {RuntimeException.class})
	public  void saveUser(User user) {
		user = userRepository.save(user);
	}
	 public void delete(Integer id) {
			userRepository.deleteById(id);
			
		}
	
	@Override
	    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
		 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        return passwordEncoder.matches(oldPassword, user.getPassword());
	    }
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
		
	}

	@Override
    public void changeUserPassword(final User user, final String password) {
		 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}

package com.example.demo.user;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Transactional(rollbackFor = {RuntimeException.class})
	public  void saveUser(User user) {
		user = userRepository.save(user);
	}
	
//	@Autowired
//    public UserServiceImpl(@Qualifier("userRepositoryImpl") UserRepository theuserRepository){
//        userRepository = theuserRepository;
//    }
//
// 
//	@Override
//    @Transactional
//    public List<User> findAllUsers() {
//        return userRepository.getAllUsers();
//    }
//
//	@Override
//    @Transactional
//    public User findUserById(Integer id) {
//        return userRepository.findUserById(id);
//    }
//
//    @Override
//    @Transactional
//    public User saveUser(User user) {
//        return userRepository.saveUser(user);
//    }
//
//	@Override
//    @Transactional
//    public Integer deleteUserById(Integer id) {
//    	userRepository.deleteUserById(id);
//        return id;
//    }
//
//
//	
}

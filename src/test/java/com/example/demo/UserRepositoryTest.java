package com.example.demo;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void CreateUser() {
		User user = new User();
		user.setFirst_name("Daniela");
		user.setLast_name("Juganaru");
		user.setUsername("Dani271");
		user.setEmail("j.daniela271@gmail.com");
		user.setPassword("1234");
		User savedUser = userRepository.save(user);
		User existUser = entityManager.find(User.class, savedUser.getId());
		assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
	}

}

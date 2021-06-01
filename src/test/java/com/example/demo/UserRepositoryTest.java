package com.example.demo;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class UserRepositoryTest {
	
	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private TestEntityManager entityManager;

	@Test
	public void CreateUser() {
		User user = new User();
		user.setFirst_name("Daniela");
		user.setLast_name("Juganaru");
		user.setEmail("j.daniela99999@gmail.com");
		user.setPassword("1234");
		user.setRole("USER");
		user.setEnabled(0);
		userRepository.save(user);
		verify(userRepository, times(1)).save(user);
	}
	
	@Test
	public void getUsersTest() {
		when(userRepository.findAll()).thenReturn(Stream
				.of(new User(), new User()).collect(Collectors.toList()));
		assertEquals(2, userService.getUsers().size());
	}

	@Test
	public void getUserbyEmailTest() {
		String email = "j.daniela5@gmail.com";
		User user= userRepository.findByEmail(email);
		assertEquals(user, userService.getUserbyEmail(email));
	}

	@Test
	public void deleteUserTest() {
		User user = userRepository.findByEmail("j.daniela2777111@gmail.com");
		userRepository.delete(user);
		verify(userRepository, times(1)).delete(user);
	}


}

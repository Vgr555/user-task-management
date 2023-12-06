package com.projectvgr.taskmanagement;

import com.projectvgr.taskmanagement.dtos.UserDTO;
import com.projectvgr.taskmanagement.entities.UserEntity;
import com.projectvgr.taskmanagement.enums.Role;
import com.projectvgr.taskmanagement.repository.UserRepository;
import com.projectvgr.taskmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Checking : ");
		List<UserDTO> users = userService.getAllUsers();
		if(users.isEmpty()){
			UserEntity userEntity = new UserEntity();
			userEntity.setId(1);
			userEntity.setName("Admin");
			userEntity.setEmail("Admin123@vgr.com");
			userEntity.setPassword(passwordEncoder.encode("Admin@123"));
			userEntity.setRole(Role.ADMIN);
			userRepository.save(userEntity);
		}
	}
}

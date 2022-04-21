package com.mall.elite;

import com.mall.elite.Entity.Role;
import com.mall.elite.Entity.User;
import com.mall.elite.Service.UserService;
import com.mall.elite.Service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

@SpringBootApplication
public class EliteApplication {
	@Autowired
	PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(EliteApplication.class, args);
	}
	@Bean
	CommandLineRunner run(UserServiceImp userService){

		return args -> {
			userService.saveRole(new Role(1L, "ROLE_USER"));
			userService.saveRole(new Role(2L, "ROLE_MANAGER"));
			userService.saveRole(new Role(3L, "ROLE_ADMIN"));
			ArrayList<Role> role = new ArrayList<>();
			userService.saveUser(new User(UUID.randomUUID(),"Jhon12",passwordEncoder.encode("123"),
					"tuongph123@gmail.com","Jhon","Bla",true,true,role));
			//userService.saveUser(new User(null,"Jhon1123","123","tuong124@gmail.com",new ArrayList<>()));

			userService.addRoleToUser("Jhon1", "ROLE_USER");
			//userService.addRoleToUser("Jhon1123","ROLE_MANAGER");
			userService.addRoleToUser("Jhon1", "ROLE_MANAGER");
		};
	}
}

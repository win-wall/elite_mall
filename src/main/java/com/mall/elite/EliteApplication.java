package com.mall.elite;

import com.mall.elite.Entity.Role;
import com.mall.elite.Entity.User;
import com.mall.elite.Service.UserService;
import com.mall.elite.Service.UserServiceImp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.HashSet;

@SpringBootApplication
public class EliteApplication {

	public static void main(String[] args) {
		SpringApplication.run(EliteApplication.class, args);
	}
	@Bean
	CommandLineRunner run(UserServiceImp userService){
		return args -> {
			userService.saveRole(new Role(1L, "ROLE_USER"));
			userService.saveRole(new Role(2L, "ROLE_MANAGER"));
			userService.saveRole(new Role(3L, "ROLE_ADMIN"));

			userService.saveUser(new User(1,"Jhon1","123","tuong@gmail.com",new ArrayList<>()));
			//userService.saveUser(new User(null,"Jhon1123","123","tuong124@gmail.com",new ArrayList<>()));

			userService.addRoleToUser("Jhon1", "ROLE_USER");
			//userService.addRoleToUser("Jhon1123","ROLE_MANAGER");
			userService.addRoleToUser("Jhon1", "ROLE_MANAGER");
		};
	}
}

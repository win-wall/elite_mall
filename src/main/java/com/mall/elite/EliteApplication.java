package com.mall.elite;

import com.mall.elite.entity.Role;
import com.mall.elite.entity.User;
import com.mall.elite.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

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
			Collection<Role> role =new ArrayList<>();

			userService.saveUser(new User(null,"Jhon123",passwordEncoder.encode("123"),
					"tuongph123@gmail.com","Jhon","Bla",true,true,role));
			//userService.saveUser(new User(null,"Jhon1123","123","tuong124@gmail.com",new ArrayList<>()));

			userService.addRoleToUser("Jhon123", "ROLE_USER");
			//userService.addRoleToUser("Jhon1123","ROLE_MANAGER");
			//userService.addRoleToUser("Jhon1", "ROLE_MANAGER");
		};
	}
}

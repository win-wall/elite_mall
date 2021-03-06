package com.mall.elite.controller;

import com.mall.elite.entity.Role;
import com.mall.elite.entity.User;
import com.mall.elite.service.UserServiceImp;
import com.mall.elite.dto.request.UserLoginRequestDto;
import com.mall.elite.dto.request.UserResigterRequestDto;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserServiceImp userService;
    @Autowired
    AuthenticationManager authenticationManager;


    @GetMapping("/users")
    public ResponseEntity<List<User>> getUser(){
        return ResponseEntity.ok().body(userService.getUser());
    }
    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }
    @PostMapping("/role/save")
    public ResponseEntity<Role> saveUser(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }
    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticationUser( @RequestBody UserLoginRequestDto userLogin){
        return ResponseEntity.ok().body(userService.login(userLogin));
    }
    @PostMapping("/resigter")
    public ResponseEntity<?> resigter(@RequestBody UserResigterRequestDto userResigterRequestDto){
        return ResponseEntity.ok().body(userService.resigter(userResigterRequestDto, "ROLE_USER"));
    }
    @GetMapping("/hello")
    public ResponseEntity<?> returnHello(){
        return ResponseEntity.ok().body("hello");
    }
}
@Data
class RoleToUserForm{
    private String username;
    private String roleName;
}

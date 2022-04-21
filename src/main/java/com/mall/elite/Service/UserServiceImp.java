package com.mall.elite.Service;

import com.mall.elite.Entity.Role;
import com.mall.elite.Entity.User;
import com.mall.elite.Repository.RoleRepository;
import com.mall.elite.Repository.UserRepository;
import com.mall.elite.Security.UserDetail;
import com.mall.elite.Security.UserDetailService;
import com.mall.elite.Security.jwt.JwtTokenProvider;
import com.mall.elite.dto.request.UserLoginRequestDto;
import com.mall.elite.dto.response.UserLoginResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserDetailService userDetailService;
    @Override
    public User saveUser(User user) {
        log.info("Saving new use {} to database", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {} to database", roleName, username);
        Optional<User> user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        Collection<Role> roles = new ArrayList<>();
        roles.add(role);
        if(user.isPresent()) {
            user.get().setRoles(roles);
            userRepository.save(user.get());
        }
    }
    @Override
    public UserLoginResponseDto login(UserLoginRequestDto userLoginDto){
        Optional<User> userOptional = userRepository.findByUsername(userLoginDto.getUsername());
        User user = userOptional.orElseThrow(()->new RuntimeException("Username or Password do not correct"));

        if(passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())){
            UserLoginResponseDto userLoginResponseDto = modelMapper.map(userLoginDto, UserLoginResponseDto.class);

            userLoginResponseDto.setTokenRefresh(tokenProvider.generateTokenFromUser(userDetailService.loadUserByUsername(user.getUsername())));
            userLoginResponseDto.setTokenAccess(tokenProvider.generateRefreshTokenFromUser(userDetailService.loadUserByUsername(user.getUsername())));
            return userLoginResponseDto;
        } else{
            throw new RuntimeException("Username or Password aren't correct");
        }
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username).get();
    }

    @Override
    public List<User> getUser() {
        log.info("Fetching all user");
        return userRepository.findAll();
    }
}

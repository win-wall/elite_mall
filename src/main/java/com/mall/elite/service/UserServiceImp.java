package com.mall.elite.service;

import com.mall.elite.entity.Role;
import com.mall.elite.entity.User;
import com.mall.elite.repository.RoleRepository;
import com.mall.elite.repository.UserRepository;
import com.mall.elite.security.UserDetailService;
import com.mall.elite.security.jwt.JwtTokenProvider;
import com.mall.elite.dto.request.UserLoginRequestDto;
import com.mall.elite.dto.request.UserResigterRequestDto;
import com.mall.elite.dto.response.UserLoginResponseDto;
import com.mall.elite.dto.response.UserResigterResponseDto;
import com.mall.elite.expections.BadRequestExpection;
import com.mall.elite.expections.NotFoundExpection;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    @Autowired
    private ModelMapper modelMapper;
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


    @Transactional
    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {} to database", roleName, username);
        Optional<Role> role = roleRepository.findByName(roleName);
        Optional<User> user = userRepository.findByUsername(username);

        User user1 = userRepository.findByUsername(username).orElseThrow(()-> new NotFoundExpection("User not found"));
        Collection<Role> roles = new ArrayList<>();
        if(role.isPresent()){
            roles.add(role.get());
        }
        else{
            throw new NotFoundExpection("Doesn't have Role. Please create a new one to add role to user: "+username);
        }
        user1.setRoles(roles);
    }

    @Override
    public UserResigterResponseDto resigter(UserResigterRequestDto userResigterRequestDto, String roleName){
        if(userRepository.findByUsernameOrEmail(userResigterRequestDto.getUsername(), userResigterRequestDto.getEmail())
                != null){
            throw new BadRequestExpection("Username or Email be already in use. Please try another one!");
        }
        Optional<Role> roleOtp = roleRepository.findByName(roleName);
        if(!roleOtp.isPresent()){
            throw new BadRequestExpection("Role is empty. Please contact to admin to add role to continue!");
        }
        userResigterRequestDto.setPassword(passwordEncoder.encode(userResigterRequestDto.getPassword()));
        User user = modelMapper.map(userResigterRequestDto, User.class);
        Role role = roleOtp.get();
        Collection<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        UserResigterResponseDto userResigterResponseDto = modelMapper.map(user, UserResigterResponseDto.class);
        log.info("save user {} to database",String.valueOf(userResigterResponseDto));
        return userResigterResponseDto;
    }
    @Override
    public UserLoginResponseDto login(UserLoginRequestDto userLoginDto){
        Optional<User> userOptional = userRepository.findByUsername(userLoginDto.getUsername());
        User user = userOptional.orElseThrow(()->new NotFoundExpection("Username is not correct!"));

        if(passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())){
            UserLoginResponseDto userLoginResponseDto = modelMapper.map(user, UserLoginResponseDto.class);

            userLoginResponseDto.setTokenRefresh(tokenProvider.generateTokenFromUser(userDetailService.loadUserByUsername(user.getUsername())));
            userLoginResponseDto.setTokenAccess(tokenProvider.generateRefreshTokenFromUser(userDetailService.loadUserByUsername(user.getUsername())));
            return userLoginResponseDto;
        } else{
            throw new NotFoundExpection("Username or Password aren't correct");
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

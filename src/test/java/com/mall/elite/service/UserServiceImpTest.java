package com.mall.elite.service;

import com.mall.elite.dto.request.UserLoginRequestDto;
import com.mall.elite.dto.request.UserResigterRequestDto;
import com.mall.elite.dto.response.UserLoginResponseDto;
import com.mall.elite.dto.response.UserResigterResponseDto;
import com.mall.elite.entity.Role;
import com.mall.elite.entity.User;
import com.mall.elite.expections.BadRequestExpection;
import com.mall.elite.expections.NotFoundExpection;
import com.mall.elite.repository.RoleRepository;
import com.mall.elite.repository.UserRepository;
import com.mall.elite.security.UserDetailService;
import com.mall.elite.security.jwt.JwtTokenProvider;
import org.aspectj.weaver.ast.Not;
import org.hibernate.annotations.NotFound;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceImpTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private JwtTokenProvider tokenProvider;
    @Mock
    private UserDetailService userDetailService;
    @Mock
    private UserResigterRequestDto userResigterRequestDto;
    @Mock
    private UserResigterResponseDto userResigterResponseDto;
    @Mock
    private UserLoginRequestDto userLoginRequestDto;
    @Mock
    private UserLoginResponseDto userLoginResponseDto;
    @InjectMocks
    private UserServiceImp userService;
    private Role role;
    private User user;
    @BeforeEach
    public void initMock(){
        role = mock(Role.class);
        user = mock(User.class);
    }
    @Test
    public void resigter_ShouldThrowBadRequestExpection_WhenUsernameOrEmailbeAlreadyInUse(){
        when(userRepository.findByUsernameOrEmail(userResigterRequestDto.getUsername(), userResigterRequestDto.getEmail()))
                .thenReturn(user);
        BadRequestExpection badRequestExpection = assertThrows(BadRequestExpection.class, ()->
                userService.resigter(userResigterRequestDto, role.getName()));
        assertThat(badRequestExpection.getMessage())
                .isEqualTo("Username or Email be already in use. Please try another one!");
    }
    @Test
    public void resigter_WhenThereAreNoUsernameOrEmailDuplicate(){
        when(userRepository.findByUsernameOrEmail(userResigterRequestDto.getUsername(), userResigterRequestDto.getEmail()))
                .thenReturn(null);
        BadRequestExpection badRequestExpection = assertThrows(BadRequestExpection.class, ()->
                userService.resigter(userResigterRequestDto, role.getName()));
        assertThat(badRequestExpection.getMessage())
                .isEqualTo("Role is empty. Please contact to admin to add role to continue!");
    }

    @Test
    public void resigter_ShouldThrowBadRequestExpection_WhenThereIsNoRole(){
        when(userRepository.findByUsernameOrEmail(userResigterRequestDto.getUsername(), userResigterRequestDto.getEmail()))
                .thenReturn(null);
        when(roleRepository.findByName(role.getName())).thenReturn(Optional.empty());
        BadRequestExpection badRequestExpection = assertThrows(BadRequestExpection.class,
                ()-> userService.resigter(userResigterRequestDto, role.getName()));
        assertThat(badRequestExpection.getMessage())
                .isEqualTo("Role is empty. Please contact to admin to add role to continue!");
    }

    @Test
    public void resigter_ShouldReturnReponseDTO_WhenSuccess(){
        when(userRepository.findByUsernameOrEmail(userResigterRequestDto.getUsername(), userResigterRequestDto.getEmail()))
                .thenReturn(null);
        when(roleRepository.findByName(role.getName())).thenReturn(Optional.of(role));
        when(modelMapper.map(userResigterRequestDto,User.class)).thenReturn(user);
        when(modelMapper.map(user,UserResigterResponseDto.class)).thenReturn(userResigterResponseDto);
        UserResigterResponseDto result = userService.resigter(userResigterRequestDto, role.getName());
        verify(userRepository).save(user);
        assertThat(userResigterResponseDto).isEqualTo(result);
    }

    @Test
    public void login_ShouldThrowNotFoundExpection_WhenThereIsNoUsername(){
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        NotFoundExpection notFoundExpection = assertThrows(NotFoundExpection.class,
                ()-> userService.login(userLoginRequestDto));
        assertThat(notFoundExpection.getMessage())
                .isEqualTo("Username is not correct!");
    }

    @Test
    public void login_ShouldThrowNotFoundExpection_WhenPasswordNotMatch(){
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())).thenReturn(false);
        NotFoundExpection notFoundExpection = assertThrows(NotFoundExpection.class,
                ()-> userService.login(userLoginRequestDto));
        assertThat(notFoundExpection.getMessage())
                .isEqualTo("Username or Password aren't correct");
    }

    @Test
    public void login_ShouldReturnResponseDTO_WhenSuccess(){
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())).thenReturn(true);
        when(modelMapper.map(user,UserLoginResponseDto.class)).thenReturn(userLoginResponseDto);
        UserLoginResponseDto result = userService.login(userLoginRequestDto);
        assertThat(userLoginResponseDto).isEqualTo(result);
    }

    @Test
    public void addRoleToUser_ShouldReturnNotFoundExpection_WhenUserCanNotFind(){
        when(roleRepository.findByName(role.getName())).thenReturn(Optional.of(role));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        NotFoundExpection notFoundExpection = assertThrows(NotFoundExpection.class,
                ()-> userService.addRoleToUser(user.getUsername(), role.getName()));
        assertThat(notFoundExpection.getMessage()).isEqualTo("User not found");
    }

    @Test
    public void addRoleToUser_ShouldReturnNotFoundExpection_WhenThereIsNoGivenRole(){
        when(roleRepository.findByName(role.getName())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        NotFoundExpection notFoundExpection = assertThrows(NotFoundExpection.class,
                ()-> userService.addRoleToUser(user.getUsername(), role.getName()));
        assertThat(notFoundExpection.getMessage()).isEqualTo("Doesn't have Role. Please create a new one to add role to user: "+user.getUsername());
    }

    @Test
    public void getUser_ShouldReturnUser_WhenSuccess() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        User result = userService.getUser(user.getUsername());
        verify(userRepository).findByUsername(user.getUsername());
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void getUsers_ShouldReturnUser_WhenSuccess() {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        List<User> result = userService.getUser();
        verify(userRepository).findAll();
        assertThat(result).isEqualTo(userList);
    }

}

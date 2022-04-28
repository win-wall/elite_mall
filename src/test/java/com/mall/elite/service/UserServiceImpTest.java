package com.mall.elite.service;

import com.mall.elite.dto.request.UserResigterRequestDto;
import com.mall.elite.dto.response.UserResigterResponseDto;
import com.mall.elite.entity.Role;
import com.mall.elite.entity.User;
import com.mall.elite.expections.BadRequestExpection;
import com.mall.elite.repository.RoleRepository;
import com.mall.elite.repository.UserRepository;
import com.mall.elite.security.UserDetailService;
import com.mall.elite.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        BadRequestExpection badRequestExpection = assertThrows(BadRequestExpection.class, ()-> userService.resigter(userResigterRequestDto, role.getName()));
        assertThat(badRequestExpection.getMessage()).isEqualTo("Username or Email be already in use. Please try another one!");
    }
    @Test
    public void resigter_WhenThereAreNoUsernameOrEmailDuplicate(){
        when(userRepository.findByUsernameOrEmail(userResigterRequestDto.getUsername(), userResigterRequestDto.getEmail()))
                .thenReturn(null);
        BadRequestExpection badRequestExpection = assertThrows(BadRequestExpection.class, ()-> userService.resigter(userResigterRequestDto, role.getName()));
        assertThat(badRequestExpection.getMessage()).isEqualTo("Role is empty. Please contact to admin to add role to continue!");
    }

    @Test
    public void resigter_ShouldThrowBadRequestExpection_WhenThereIsNoRole(){
        when(userRepository.findByUsernameOrEmail(userResigterRequestDto.getUsername(), userResigterRequestDto.getEmail()))
                .thenReturn(null);
        when(roleRepository.findByName(role.getName())).thenReturn(Optional.empty());
        BadRequestExpection badRequestExpection = assertThrows(BadRequestExpection.class, ()-> userService.resigter(userResigterRequestDto, role.getName()));
        assertThat(badRequestExpection.getMessage()).isEqualTo("Role is empty. Please contact to admin to add role to continue!");
    }

    @Test
    public void resigter_WhenHaveRole(){
        when(userRepository.findByUsernameOrEmail(userResigterRequestDto.getUsername(), userResigterRequestDto.getEmail()))
                .thenReturn(null);
        when(roleRepository.findByName(role.getName())).thenReturn(Optional.of(role));
        when(modelMapper.map(userResigterRequestDto,User.class)).thenReturn(user);
        when(modelMapper.map(user,UserResigterResponseDto.class)).thenReturn(userResigterResponseDto);
        UserResigterResponseDto result = userService.resigter(userResigterRequestDto, role.getName());
        assertThat(userResigterResponseDto).isEqualTo(result);
    }
}

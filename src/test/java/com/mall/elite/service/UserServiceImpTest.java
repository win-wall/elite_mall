package com.mall.elite.service;

import com.mall.elite.dto.request.UserResigterRequestDto;
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
    @InjectMocks
    private UserServiceImp userService;

    @BeforeEach
    public void initMock(){

    }
    @Test
    public void resigter_ShouldThrowBadRequestExpection_WhenUsernameOrEmailbeAlreadyInUse(){
        User user = mock(User.class);
        when(userRepository.findByUsernameOrEmail(userResigterRequestDto.getUsername(), userResigterRequestDto.getEmail()))
                .thenReturn(user);
        BadRequestExpection badRequestExpection = assertThrows(BadRequestExpection.class, ()-> userService.resigter(userResigterRequestDto, "ROLE_USER"));
        assertThat(badRequestExpection.getMessage()).isEqualTo("Username or Email be already in use. Please try another one!");
    }
}

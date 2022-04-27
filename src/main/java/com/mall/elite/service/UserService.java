package com.mall.elite.service;

import com.mall.elite.entity.Role;
import com.mall.elite.entity.User;
import com.mall.elite.dto.request.UserLoginRequestDto;

import com.mall.elite.dto.request.UserResigterRequestDto;
import com.mall.elite.dto.response.UserLoginResponseDto;
import com.mall.elite.dto.response.UserResigterResponseDto;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUser();
    UserLoginResponseDto login(UserLoginRequestDto userLoginDto);
    UserResigterResponseDto resigter(UserResigterRequestDto userResigterRequestDto, String roleName);
}

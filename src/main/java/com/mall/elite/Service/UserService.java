package com.mall.elite.Service;

import com.mall.elite.Entity.Role;
import com.mall.elite.Entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUser();
}

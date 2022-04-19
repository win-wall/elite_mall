package com.mall.elite.Security;

import com.mall.elite.Entity.User;
import com.mall.elite.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService  {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetail loadUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return new UserDetail(user.get());
        }

        throw new UsernameNotFoundException(username);
    }
}

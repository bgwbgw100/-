package com.example.demo.security;

import com.example.demo.user.User;
import com.example.demo.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserMapper userMapper;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User loginUser = new User();
        loginUser.setId(username);
        User resultUser = userMapper.findById(loginUser);

        List<GrantedAuthority> authorityList = new ArrayList<>();
        if(resultUser == null){
            throw new UsernameNotFoundException("userNotFound");
        }
        else if(resultUser.getLoginTry() >=5){
            throw new CustomLoginOverException("login Over");
        }

        authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));

        if(resultUser.getPower().equals("A")){
            authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new CustomUserDetails(resultUser.getId(),resultUser.getPassword(),resultUser.getName(),authorityList);

    }
}

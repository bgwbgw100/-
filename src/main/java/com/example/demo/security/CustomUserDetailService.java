package com.example.demo.security;

import com.example.demo.user.UserDTO;
import com.example.demo.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserMapper userMapper;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO loginUserDTO = new UserDTO();
        loginUserDTO.setId(username);
        UserDTO resultUserDTO = userMapper.findById(loginUserDTO);

        List<GrantedAuthority> authorityList = new ArrayList<>();
        if(resultUserDTO == null){
            throw new UsernameNotFoundException("userNotFound");
        }
        else if(resultUserDTO.getLoginTry() >=5){
            throw new CustomLoginOverException("login Over");
        }

        authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));

        if(resultUserDTO.getPower().equals("A")){
            authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new CustomUserDetails(resultUserDTO.getId(), resultUserDTO.getPassword(), resultUserDTO.getName(),authorityList);

    }
}

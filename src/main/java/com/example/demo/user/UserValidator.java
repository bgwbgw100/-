package com.example.demo.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserMapper userMapper;

    public boolean userDuplicationIdCheck(UserDTO userDTO){
        return userMapper.selectUserByIdCheck(userDTO) == 0 ;
    }

    public boolean userDuplicationEmailCheck(UserDTO userDTO){
        return userMapper.selectUserByEmailCheck(userDTO) == 0 ;
    }

}

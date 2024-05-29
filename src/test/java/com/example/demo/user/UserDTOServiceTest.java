package com.example.demo.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest

public class UserDTOServiceTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void Login(){
        UserDTO userDTO = new UserDTO();
        userDTO.setId("admin");
        userDTO.setPassword("admin" );
        UserDTO loginUserDTO = userMapper.findById(userDTO);
        String id = loginUserDTO.getId();
        assertThat(loginUserDTO.getLoginTry()).isLessThan(5);
        assertThat(id).isEqualTo(userDTO.getId());
        assertTrue(passwordEncoder.matches(userDTO.getPassword(), loginUserDTO.getPassword()));
    }
}

package com.example.demo.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest

public class UserServiceTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void Login(){
        User user = new User();
        user.setId("admin");
        user.setPassword("admin" );
        User loginUser = userMapper.findById(user);
        String id = loginUser.getId();
        assertThat(loginUser.getLoginTry()).isLessThan(5);
        assertThat(id).isEqualTo(user.getId());
        assertTrue(passwordEncoder.matches(user.getPassword(), loginUser.getPassword()));
    }
}

package com.example.demo.admin;


import com.example.demo.admin.user.AdminUserService;
import com.example.demo.user.UserDTO;
import com.example.demo.user.UserMapper;
import com.example.demo.util.CommonPagingDTO;
import com.example.demo.util.CommonPagingExecute;
import com.example.demo.util.CommonPagingExecuteImp;
import com.example.demo.util.DynamicMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AdminUserServiceTest {
    @Autowired
    AdminUserService adminUserService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    DynamicMapper dynamicMapper;


    @Test
    public void selectAll(){
        adminUserService.getAllUser(new UserDTO(), new CommonPagingDTO());


    }
}

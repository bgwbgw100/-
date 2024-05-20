package com.example.demo.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter

@Setter
public class UserTest {
    private String id;
    private String name;
    private String password;
    //권한
    private String power;
    private String email;
    private String phone;
    private String delete;
    private Date registDt;
    private Date deleteDt;
    private Date lastLogin;
    private int loginTry;
}

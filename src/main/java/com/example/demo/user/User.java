package com.example.demo.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class User {
    private String id;
    private String password;
    private String name;
    //권한
    private String power;
    private String email;
    private String phone;
    private String deleteOx;
    private Date registDt;
    private Date deleteDt;
    private Date lastLogin;
    private int loginTry;
}

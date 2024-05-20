package com.example.demo.security;

import org.springframework.security.core.userdetails.UsernameNotFoundException;



public class CustomLoginOverException extends UsernameNotFoundException {
    public CustomLoginOverException(String msg) {
        super(msg);
    }
}

package com.example.demo.admin.menu;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuStorageDTO {
    private int menuCode;
    private String menu;
    private String explanation;
    private int step;
    private int childCount;
}

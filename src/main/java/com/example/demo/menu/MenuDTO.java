package com.example.demo.menu;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDTO {
    int menuNumber;
    String name;
    int parentNumber;
    int level;
    String korName;
    String kind;
}

package com.example.demo.menu;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Valid
public class MenuDTO {
    int menuNumber;
    @NotBlank
    String name;
    int parentNumber;
    int level;
    @NotBlank
    String korName;
    String kind;
    private int step;
    private int childCount;
    private List<MenuDTO> childList;

}

package com.example.demo.admin;

import com.example.demo.menu.MenuDTO;
import com.example.demo.menu.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminMenuValidator {
    private final MenuMapper menuMapper;

    public boolean menuInsertCheck(MenuDTO menuDTO){
        if (menuDTO.getParentNumber() ==0) return true;

        MenuDTO selectMenu = menuMapper.selectOneMenuByParentNumber(menuDTO.getParentNumber());


        return selectMenu != null && selectMenu.getLevel() < 2;
    }


}

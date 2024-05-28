package com.example.demo.admin;

import com.example.demo.menu.MenuDTO;
import com.example.demo.menu.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminMenuValidator {
    private final MenuMapper menuMapper;

    public boolean menuInsertCheck(MenuDTO menuDTO){
        if (menuDTO.getParentNumber() ==0) return true;

        MenuDTO selectMenu = menuMapper.selectOneMenuByParentNumber(menuDTO.getParentNumber());

        return selectMenu != null && selectMenu.getLevel() < 2;
    }

    public boolean menuDeleteCheck(MenuDTO menuDTO){

        List<MenuDTO> menuDTOS = menuMapper.selectChildMenu(menuDTO.getMenuNumber());
        return menuDTOS.isEmpty();
    }


}

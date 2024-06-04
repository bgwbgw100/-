package com.example.demo.admin.menu;

import com.example.demo.menu.MenuDTO;
import com.example.demo.menu.MenuMap;
import com.example.demo.menu.MenuMapper;
import com.example.demo.menu.MenuService;
import com.example.demo.util.CustomTwoReturn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminMenuService {

    private final MenuMapper menuMapper;
    private final MenuService menuService;
    private final MenuStorageMapper menuStorageMapper;

    public CustomTwoReturn<List<MenuDTO>,List<MenuStorageDTO>> getMenuAll(){
        HashMap<String, MenuDTO> menuMap = MenuMap.menuMap;
        List<MenuDTO> menuList = menuMap.entrySet().stream().map(Map.Entry::getValue).toList();
        List<MenuStorageDTO> menuStorageList = menuStorageMapper.selectMenuStorageAll();

        return new CustomTwoReturn<List<MenuDTO>,List<MenuStorageDTO>>(menuList,menuStorageList);
    }



    public void addMenu(MenuDTO menuDTO){
        menuMapper.insertMenu(menuDTO);
        menuService.updateMenuMap();
    }

    public void updateMenu(MenuDTO menuDTO){
        menuMapper.updateMenu(menuDTO);
        menuService.updateMenuMap();
    }

    public void deleteMenu(MenuDTO menuDTO){
        menuMapper.deleteMenu(menuDTO);
        menuService.updateMenuMap();
    }


}

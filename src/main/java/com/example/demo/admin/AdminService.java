package com.example.demo.admin;

import com.example.demo.menu.MenuDTO;
import com.example.demo.menu.MenuMap;
import com.example.demo.menu.MenuMapper;
import com.example.demo.menu.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MenuMapper menuMapper;
    private final MenuService menuService;

    public List<MenuDTO> getMenuAll(){
        HashMap<String, MenuDTO> menuMap = MenuMap.menuMap;
        return  menuMap.entrySet().stream().map(Map.Entry::getValue).toList();
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
